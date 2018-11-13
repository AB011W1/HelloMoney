package com.barclays.bmg.operation.pesalink;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.request.pesalink.SearchIndividualCustforDeDupCheckOperationRequest;
import com.barclays.bmg.operation.response.pesalink.BankOpRes;
import com.barclays.bmg.operation.response.pesalink.IndividualCustAdditionalInfoOpRes;
import com.barclays.bmg.operation.response.pesalink.IndividualCustomerBasicOpRes;
import com.barclays.bmg.operation.response.pesalink.SearchIndividualCustforDeDupCheckOperationResponse;
import com.barclays.bmg.service.pesalink.SearchIndividualCustforDeDupCheckService;
import com.barclays.bmg.service.request.pesalink.SearchIndividualCustforDeDupCheckServiceRequest;
import com.barclays.bmg.service.response.pesalink.BankSerRes;
import com.barclays.bmg.service.response.pesalink.IndividualCustomerBasicSerRes;
import com.barclays.bmg.service.response.pesalink.SearchIndividualCustforDeDupCheckServiceResponse;

public class SearchIndividualCustforDeDupCheckOperation extends BMBCommonOperation{




	private SearchIndividualCustforDeDupCheckService searchIndividualCustforDeDupCheckService;


	//@AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, serviceDescription = "SD_CCD_LINK", stepId = "1", activityType = "auditCCDLink")
	public SearchIndividualCustforDeDupCheckOperationResponse retrieveCustomerInfo(SearchIndividualCustforDeDupCheckOperationRequest searchIndividualCustforDeDupCheckOperationRequest) {

		SearchIndividualCustforDeDupCheckOperationResponse searchIndividualCustforDeDupCheckOperationResponse = new SearchIndividualCustforDeDupCheckOperationResponse();
		SearchIndividualCustforDeDupCheckServiceRequest searchIndividualCustforDeDupCheckServiceRequest = new SearchIndividualCustforDeDupCheckServiceRequest();
		searchIndividualCustforDeDupCheckServiceRequest.setMobileNumber(searchIndividualCustforDeDupCheckOperationRequest.getMobileNumber());
		loadParameters(searchIndividualCustforDeDupCheckOperationRequest.getContext(), ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
		searchIndividualCustforDeDupCheckServiceRequest.setContext(searchIndividualCustforDeDupCheckOperationRequest.getContext());
		SearchIndividualCustforDeDupCheckServiceResponse searchIndividualCustforDeDupCheckServiceResponse = searchIndividualCustforDeDupCheckService.retrieveCustomerInfo(searchIndividualCustforDeDupCheckServiceRequest);

		if(searchIndividualCustforDeDupCheckServiceResponse != null){

			searchIndividualCustforDeDupCheckOperationResponse.setSuccess(searchIndividualCustforDeDupCheckServiceResponse.isSuccess());
			searchIndividualCustforDeDupCheckOperationResponse.setResCde(searchIndividualCustforDeDupCheckServiceResponse.getResCde());
			searchIndividualCustforDeDupCheckOperationResponse.setResMsg(searchIndividualCustforDeDupCheckServiceResponse.getResMsg());

			if(searchIndividualCustforDeDupCheckServiceResponse.isSuccess())
			{
			if(searchIndividualCustforDeDupCheckOperationRequest.getContext().getActivityId().equals("KITS_DEREGISTRATION_LOOKUP"))
			{

				String primaryFlag=searchIndividualCustforDeDupCheckServiceResponse.getIndividualCustAdditionalInfoSerRes().getPrimaryFlag();
				IndividualCustAdditionalInfoOpRes opRes=new IndividualCustAdditionalInfoOpRes();
				opRes.setCustomerAccountNumber(searchIndividualCustforDeDupCheckServiceResponse.getIndividualCustAdditionalInfoSerRes().getCustomerAccountNumber());
				if("true".equalsIgnoreCase(primaryFlag))
				{
					opRes.setPrimaryFlag("Yes");
				}else if ("false".equalsIgnoreCase(primaryFlag))
				{
					opRes.setPrimaryFlag("No");
				}

				searchIndividualCustforDeDupCheckOperationResponse.setIndividualCustAdditionalInfoOpRes(opRes);
				//searchIndividualCustforDeDupCheckOperationResponse.setContext(searchIndividualCustforDeDupCheckOperationRequest.getContext());

			}else if(searchIndividualCustforDeDupCheckOperationRequest.getContext().getActivityId().equals("KITS_SENDTOPHONE_LOOKUP"))
			{
				//Adding Customer name
				// Added By G01022861 on 30/09/2016
				IndividualCustomerBasicSerRes individualCustomerBasicSerRes=searchIndividualCustforDeDupCheckServiceResponse.getIndividualCustomerBasicSerResList().get(0);
				List<BankSerRes> list = individualCustomerBasicSerRes.getBankSerResList();
				// Ended
				if (list != null && list.size() > 0) {
					List<BankOpRes> bankOplList = new ArrayList<BankOpRes>();
					for (BankSerRes bankDTO : list) {

						BankOpRes bankOpRes = new BankOpRes();
						bankOpRes.setBankCode(bankDTO.getBankCode());
						bankOpRes.setBankName(bankDTO.getBankName());

						bankOplList.add(bankOpRes);
					}
					IndividualCustomerBasicOpRes opRes=new IndividualCustomerBasicOpRes();
					opRes.setBankOpResList(bankOplList);
					//Adding Customer name
					// Added By G01022861 on 30/09/2016
					opRes.setIndividualName(individualCustomerBasicSerRes.getIndividualName());
					// Ended
					List<IndividualCustomerBasicOpRes>   opList=new ArrayList<IndividualCustomerBasicOpRes>();
					opList.add(opRes);
					searchIndividualCustforDeDupCheckOperationResponse.setIndividualCustomerBasicOpResList(opList);


				}
				//searchIndividualCustforDeDupCheckOperationResponse.setContext(searchIndividualCustforDeDupCheckOperationRequest.getContext());
			}
			}
			searchIndividualCustforDeDupCheckOperationResponse.setContext(searchIndividualCustforDeDupCheckOperationRequest.getContext());
	}

		return searchIndividualCustforDeDupCheckOperationResponse;
	}

	public SearchIndividualCustforDeDupCheckService getSearchIndividualCustforDeDupCheckService() {
		return searchIndividualCustforDeDupCheckService;
	}

	public void setSearchIndividualCustforDeDupCheckService(
			SearchIndividualCustforDeDupCheckService searchIndividualCustforDeDupCheckService) {
		this.searchIndividualCustforDeDupCheckService = searchIndividualCustforDeDupCheckService;
	}






}
