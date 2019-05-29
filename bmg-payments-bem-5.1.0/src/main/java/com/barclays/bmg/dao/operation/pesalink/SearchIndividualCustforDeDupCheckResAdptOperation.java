package com.barclays.bmg.dao.operation.pesalink;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.barclays.bem.BEMServiceHeader.BEMResHeader;
import com.barclays.bem.IndividualCustomerBasic.IndividualCustAdditionalInfo;
import com.barclays.bem.IndividualCustomerBasic.IndividualCustomerBasic;
import com.barclays.bem.SearchIndividualCustInformation.SearchIndividualCustomerInformationResponse;
import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dao.operation.accountservices.AbstractResAdptOperationAcct;
import com.barclays.bmg.service.response.pesalink.BankSerRes;
import com.barclays.bmg.service.response.pesalink.IndividualCustAdditionalInfoSerRes;
import com.barclays.bmg.service.response.pesalink.IndividualCustomerBasicSerRes;
import com.barclays.bmg.service.response.pesalink.SearchIndividualCustforDeDupCheckServiceResponse;


public class SearchIndividualCustforDeDupCheckResAdptOperation extends AbstractResAdptOperationAcct{

	    private static final Logger LOGGER = Logger.getLogger(SearchIndividualCustforDeDupCheckResAdptOperation.class);
		public SearchIndividualCustforDeDupCheckServiceResponse adaptResponse(WorkContext workContext, Object obj){

			DAOContext daoContext = (DAOContext) workContext;
			Object[] args = daoContext.getArguments();
			RequestContext request = (RequestContext) args[0];
			Context context = request.getContext();
			String activityId=context.getActivityId();
			List<String>  bankNameList=new ArrayList<String>();
			SearchIndividualCustforDeDupCheckServiceResponse response = new SearchIndividualCustforDeDupCheckServiceResponse();
			SearchIndividualCustomerInformationResponse bemResponse = (SearchIndividualCustomerInformationResponse)obj;
			checkRespHeader(bemResponse.getResponseHeader(), response);

			if(response.isSuccess()){

				if(bemResponse.getIndividualCustomerBasicDetails()!=null )
				{
					if(bemResponse.getIndividualCustomerBasicDetails().getIndividualCustomerBasic().length!=0)
					{

					if(activityId.equalsIgnoreCase("KITS_SENDTOPHONE_LOOKUP"))
					{
						IndividualCustomerBasic[] individualCustomerBasicArray = bemResponse.getIndividualCustomerBasicDetails().getIndividualCustomerBasic();
						List<IndividualCustomerBasicSerRes> list=populateCustomerDetailsList(individualCustomerBasicArray);
						if(!list.isEmpty() && !list.get(0).getBankSerResList().isEmpty() && !list.get(0).getBankSerResList().get(0).getBankName().isEmpty())
						{

							response.setIndividualCustomerBasicSerResList(list);
							response.setSuccess(true);

						}
						else
						{
						response.setResCde("BEMRECMOB");
						response.setSuccess(false);
						}

					}else if(activityId.equalsIgnoreCase("KITS_REGISTRATION_LOOKUP"))
					{
						//Added for new Lookup service change
						String individualCustAdditionalInfo = null;
						IndividualCustomerBasic[] individualCustomerBasicArray = bemResponse.getIndividualCustomerBasicDetails().getIndividualCustomerBasic();
						//Added for KITS enable/disable
						String isKITS = context.getValue("isKITSFLAG").toString(); //BMBContextHolder.getContext().getValue("isKITSFLAG").toString();
						if(isKITS != null)
						{
							if(isKITS.equals("Y"))
							{
								if(individualCustomerBasicArray[0] != null)
									individualCustAdditionalInfo = individualCustomerBasicArray[0].getIndividualCustAdditionalInfo().getParticularIndicator();

								if(null != individualCustAdditionalInfo && individualCustAdditionalInfo.equals("NOTREGISTERED"))
								{
									List<IndividualCustomerBasicSerRes> list=populateCustomerDetailsList(individualCustomerBasicArray);
									if(list != null)
									{
										for(IndividualCustomerBasicSerRes individualCustomerBasicSerRes: list)
										{
											for(BankSerRes bankSerRes: individualCustomerBasicSerRes.getBankSerResList() )
											{
												bankNameList.add(bankSerRes.getBankName());
											}
//											if(bankNameList.isEmpty())
//											{
//												response.setResCde("BEMREGNOBANK");
//												response.setSuccess(false);
//											}
//											else
												if(bankNameList.contains("BARCLAYS"))
											{
												response.setResCde("BEMREG");
												response.setSuccess(false);
											}else{
												response.setResMsg(bemResponse.getResponseHeader().getServiceResStatus().getServiceResDesc());
												response.setResCde(bemResponse.getResponseHeader().getServiceResStatus().getServiceResCode());
												response.setSuccess(true);
											}
										}
									}

								}
								else if(null != individualCustAdditionalInfo && individualCustAdditionalInfo.equals("REGISTERED"))
								{
									response.setResMsg("REGISTERED");
									response.setResCde("REGISTERED");
									response.setSuccess(false);
								}
							}
							else
							{
								List<IndividualCustomerBasicSerRes> list=populateCustomerDetailsList(individualCustomerBasicArray);
								if(list != null)
								{
									for(IndividualCustomerBasicSerRes individualCustomerBasicSerRes: list)
									{
										for(BankSerRes bankSerRes: individualCustomerBasicSerRes.getBankSerResList() )
										{
											bankNameList.add(bankSerRes.getBankName());
										}
//										if(bankNameList.isEmpty())
//										{
//											response.setResCde("BEMREGNOBANK");
//											response.setSuccess(false);
//										}
//										else
											if(bankNameList.contains("BARCLAYS"))
										{
											response.setResCde("BEMREG");
											response.setSuccess(false);
										}else{
											response.setResMsg(bemResponse.getResponseHeader().getServiceResStatus().getServiceResDesc());
											response.setResCde(bemResponse.getResponseHeader().getServiceResStatus().getServiceResCode());
											response.setSuccess(true);
										}
									}
								}
							}
						}
					}else if(activityId.equalsIgnoreCase("KITS_DEREGISTRATION_LOOKUP"))
					{
						IndividualCustomerBasic[] individualCustomerBasicArray = bemResponse.getIndividualCustomerBasicDetails().getIndividualCustomerBasic();
						IndividualCustAdditionalInfoSerRes custAdditionalInfoSerRes=populateCustomerRegistrationDetails(individualCustomerBasicArray);

						if(custAdditionalInfoSerRes != null)
						{
						response.setIndividualCustAdditionalInfoSerRes(custAdditionalInfoSerRes);
						response.setSuccess(true);

						}else {

						response.setResCde("BEMDEREG");
						response.setSuccess(false);

						}

						/*for(IndividualCustomerBasicSerRes individualCustomerBasicSerRes: list)
						{
							for(BankSerRes bankSerRes: individualCustomerBasicSerRes.getBankSerResList() )
							{
								bankNameList.add(bankSerRes.getBankName());
							}
							if(!bankNameList.contains("BARCLAYS"))
							{
								response.setResCde("BEMDEREG");
								response.setSuccess(false);
							}else{
								response.setResMsg(bemResponse.getResponseHeader().getServiceResStatus().getServiceResDesc());
								response.setResCde(bemResponse.getResponseHeader().getServiceResStatus().getServiceResCode());
								response.setSuccess(true);
							}
						}*/
					}
				}else if (activityId.equalsIgnoreCase("KITS_DEREGISTRATION_LOOKUP"))
						{
						response.setResMsg(bemResponse.getResponseHeader().getServiceResStatus().getServiceResDesc());
						response.setResCde("Customer details not available in MCE for given mobile number.");
						response.setSuccess(false);
						}
				else{
					response.setResMsg(bemResponse.getResponseHeader().getServiceResStatus().getServiceResDesc());
					response.setResCde("BEMRECMOB");
					response.setSuccess(false);
				}


			}
			}else{
				for (com.barclays.bem.BEMServiceHeader.Error error : bemResponse.getResponseHeader().getErrorList()) {
					response.setResMsg(bemResponse.getResponseHeader().getServiceResStatus().getServiceResDesc());
					response.setResCde("BEM"+error.getErrorCode());
					response.setSuccess(false);
				}
			}

			return response;
		}

		private List<IndividualCustomerBasicSerRes> populateCustomerDetailsList(IndividualCustomerBasic[] individualCustomerBasicListArray){
			if (LOGGER.isDebugEnabled()) {
				if(individualCustomerBasicListArray!=null)
				LOGGER.debug(" Entry SearchIndividualCustforDeDupCheckResAdptOperation populateCustomerDetaislList individualCustomerBasicListArray size"+individualCustomerBasicListArray.length);
			}

			List<IndividualCustomerBasicSerRes> individualCustomerBasicServiceResList= new ArrayList<IndividualCustomerBasicSerRes>();
			List<IndividualCustomerBasic> individualCustomerBasicList = null;
			if(null != individualCustomerBasicListArray)
				individualCustomerBasicList=Arrays.asList(individualCustomerBasicListArray);
			if(null != individualCustomerBasicList){
				for(IndividualCustomerBasic details: individualCustomerBasicList)
				{
					IndividualCustomerBasicSerRes individualCustomerBasicSerRes=new IndividualCustomerBasicSerRes();
					if(details.getIndividualName() != null)
						individualCustomerBasicSerRes.setIndividualName(details.getIndividualName().getFullName());
					com.barclays.bem.Bank.Bank [] bankArray =details.getCustomerBankInfo();
					if(bankArray != null)
					{
						List<com.barclays.bem.Bank.Bank> bankList=Arrays.asList(bankArray);
		                List<BankSerRes> sbankList= new ArrayList<BankSerRes>();
		                for(com.barclays.bem.Bank.Bank bankDetails:bankList)
		                {
		                	BankSerRes sbank=new BankSerRes();
		                	sbank.setBankCode(bankDetails.getISOBankCode().getBankCode());
		                	sbank.setBankName(bankDetails.getBankName());

		                	sbankList.add(sbank);
		                }
		                individualCustomerBasicSerRes.setBankSerResList(sbankList);
		                individualCustomerBasicServiceResList.add(individualCustomerBasicSerRes);
					}

				}
			}


			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(" Exit SearchIndividualCustforDeDupCheckResAdptOperation populateCustomerDetaislList individualCustomerBasicList  size"+individualCustomerBasicList.size());
			}

			return individualCustomerBasicServiceResList;
		}

		private IndividualCustAdditionalInfoSerRes populateCustomerRegistrationDetails(IndividualCustomerBasic[] individualCustomerBasicListArray){
			if (LOGGER.isDebugEnabled()) {
				if(individualCustomerBasicListArray!=null)
				LOGGER.debug(" Entry SearchIndividualCustforDeDupCheckResAdptOperation populateCustomerRegistrationDetails individualCustomerBasicListArray size"+individualCustomerBasicListArray.length);
			}

			IndividualCustomerBasic details= new IndividualCustomerBasic();
			if(null != individualCustomerBasicListArray)
				details = individualCustomerBasicListArray[0];
			IndividualCustAdditionalInfo individualCustAdditionalInfo=details.getIndividualCustAdditionalInfo();
			IndividualCustAdditionalInfoSerRes custAdditionalInfo=new IndividualCustAdditionalInfoSerRes();

			if(individualCustAdditionalInfo!=null)
			{
				custAdditionalInfo.setCustomerAccountNumber(individualCustAdditionalInfo.getCustomerAccountNumber());
				custAdditionalInfo.setPrimaryFlag(String.valueOf(individualCustAdditionalInfo.getPrimaryAccountHolderFlag()));
			}
			return custAdditionalInfo;
		}

		private void checkRespHeader(BEMResHeader resHeader, ResponseContext response) {

			String resCode = resHeader.getServiceResStatus().getServiceResCode();
			if (ErrorCodeConstant.BUSINESS_ERROR.equals(resCode)) {
				if (resHeader.getErrorList() != null && resHeader.getErrorList().length > 0) {

					for (com.barclays.bem.BEMServiceHeader.Error error : resHeader.getErrorList()) {

						response.setResMsg(error.getErrorDesc());
						response.setResCde("BEM"+error.getErrorCode());
						response.setSuccess(false);
					}
				}
			}
			if (response.isSuccess()) {
				response.setSuccess(super.checkResponseHeader(resHeader));
			}
		}

}


