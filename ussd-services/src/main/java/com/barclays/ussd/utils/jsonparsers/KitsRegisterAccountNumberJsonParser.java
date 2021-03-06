package com.barclays.ussd.utils.jsonparsers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthUserData;
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthenticateUserPayData;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.SearchIndividualCustInformationResponse;


public class KitsRegisterAccountNumberJsonParser implements BmgBaseJsonParser {

	 /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(KitsRegisterAccountNumberJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = new MenuItemDTO();
	ObjectMapper mapper = new ObjectMapper();
	try{

		SearchIndividualCustInformationResponse searchIndividualCustInformationResponse = mapper.readValue(responseBuilderParamsDTO.getJsonString(),
					SearchIndividualCustInformationResponse.class);

			if (searchIndividualCustInformationResponse != null) {
				if ((searchIndividualCustInformationResponse.getPayHdr() != null
					&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(searchIndividualCustInformationResponse.getPayHdr().getResCde())) ||
					(searchIndividualCustInformationResponse.getPayHdr() != null &&
							USSDExceptions.BEM001.getBmgCode().equalsIgnoreCase(searchIndividualCustInformationResponse.getPayHdr().getResCde()) )
					) {
//Retrieving account list starts
					try {
						    AuthUserData userAuthObj = (AuthUserData) responseBuilderParamsDTO.getUssdSessionMgmt().getUserAuthObj();
						    if (userAuthObj != null) {
							if (userAuthObj.getPayHdr() != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(userAuthObj.getPayHdr().getResCde())) {
							    List<CustomerMobileRegAcct> custActs = userAuthObj.getPayData().getCustActs();
							    Collections.sort(custActs, new KITSRegisterAccountSummaryCustomerAccountComparator());
							    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, userAuthObj);
							} else if (userAuthObj.getPayHdr() != null) {
							    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
							    throw new USSDNonBlockingException(userAuthObj.getPayHdr().getResCde(),true);
							} else {
							    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
							    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode(),true);
							}
						    } else {
							LOGGER.error("Invalid response got from the BMG " + responseBuilderParamsDTO.getBmgOpCode());
							throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode(),true);
						    }
						} catch (Exception e) {
						    LOGGER.error("Exception : ", e);
						    if (e instanceof USSDNonBlockingException) {
							throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode(),true);
						    } else {
							throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode(),true);
						    }
						}

//Retrieving account list ends

				}else if (searchIndividualCustInformationResponse.getPayHdr() != null
						&& (StringUtils.equalsIgnoreCase(USSDExceptions.USSD_TECH_ISSUE.getBmgCode(), searchIndividualCustInformationResponse.getPayHdr().getResCde()))) {
				    throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
				}
				else if(searchIndividualCustInformationResponse.getPayHdr().getResCde().equals("REGISTERED"))
				{
					throw new USSDNonBlockingException(USSDExceptions.BEMREG.getBmgCode(),"Customer is already registred");
				}
				else if(searchIndividualCustInformationResponse.getPayHdr() != null
						&& USSDExceptions.BEMREG.getBmgCode().equalsIgnoreCase(searchIndividualCustInformationResponse.getPayHdr().getResCde())){
					throw new USSDNonBlockingException(USSDExceptions.BEMREG.getBmgCode(),true);
				}else if(searchIndividualCustInformationResponse.getPayHdr() != null
						&& USSDExceptions.BEMDEREG.getBmgCode().equalsIgnoreCase(searchIndividualCustInformationResponse.getPayHdr().getResCde())){
					throw new USSDBlockingException(USSDExceptions.BEMDEREG.getBmgCode());
				}
				else if (searchIndividualCustInformationResponse.getPayHdr() != null) {
				    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
				    throw new USSDNonBlockingException(searchIndividualCustInformationResponse.getPayHdr().getResCde(),true);
				}
				}else {
					LOGGER.error("Invalid response got from the BMG " + responseBuilderParamsDTO.getBmgOpCode());
					throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode(),true);
				    }

	}catch(Exception e)
	{
		LOGGER.error("Exception : ", e);
	    if (e instanceof USSDNonBlockingException) {
		throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode(),true);
	    } else {
		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode(),true);
	    }
	}

	return menuDTO;
    }

    /**
     * @param responseBuilderParamsDTO
     * @param userAuthObj
     * @param warningMsg
     * @return MenuItemDTO
     * @throws USSDNonBlockingException
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, AuthUserData userAuthObj) throws USSDNonBlockingException {

		MenuItemDTO menuItemDTO = null;
		AuthenticateUserPayData acntPayData = userAuthObj.getPayData();
		USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
		AuthUserData authData= ((AuthUserData)ussdSessionMgmt.getUserAuthObj());
	    List<CustomerMobileRegAcct> acts=authData.getPayData().getCustActs();
	    List<CustomerMobileRegAcct> custActs = acntPayData.getCustActs();
		if (acntPayData != null) {
			if (acntPayData.getCustActs() != null && !acntPayData.getCustActs().isEmpty()) {
				menuItemDTO = new MenuItemDTO();
				int index = 1;
				StringBuilder pageBody = new StringBuilder();
				Map<String, Object> txSessions = new HashMap<String, Object>(acntPayData.getCustActs().size());
				txSessions.put(USSDInputParamsEnum.KITS_REG_ACCOUNT_NUM.getTranId(), acntPayData.getCustActs());
				responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
				if(acts != null && acts.size() > 0)
				{
					List<String> GpAcc=new ArrayList<String>();
					 for(int i =0;i<acts.size();i++)
					    	if(acts.get(i).getGroupWalletIndicator()!=null && acts.get(i).getGroupWalletIndicator().equals("Y"))
					    		GpAcc.add(acts.get(i).getMkdActNo());

						 for(int j=0;j<custActs.size();j++)
							 if(GpAcc.contains(custActs.get(j).getMkdActNo()))
								 custActs.remove(j);
				}
				if (custActs == null || custActs.isEmpty() || custActs.size() == 0) {
				    throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_ACCTS.getBmgCode());
				}
				for (CustomerMobileRegAcct accountDetail : custActs){//acntPayData.getCustActs()) {
					pageBody.append(USSDConstants.NEW_LINE);
					pageBody.append(index);
					pageBody.append(USSDConstants.DOT_SEPERATOR);
					pageBody.append(accountDetail.getMkdActNo());
					index++;
				}
				menuItemDTO.setPageBody(pageBody.toString());
				USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
				menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
				menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
				menuItemDTO.setPaginationType(PaginationEnum.LISTED);
			}
		}
		if(null != menuItemDTO)
			setNextScreenSequenceNumber(menuItemDTO);
		return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());
    }
}

/* This class compares the customer account w.r.t primary indicator*/
	class KITSRegisterAccountSummaryCustomerAccountComparator implements Comparator<CustomerMobileRegAcct>, Serializable {

		private static final long serialVersionUID = 1L;

		public int compare(final CustomerMobileRegAcct accountDetail1, final CustomerMobileRegAcct accountDetail2) {
			int retVal = 0;
			if (StringUtils.equalsIgnoreCase(USSDConstants.PRIMARY_INDICATOR_YES, accountDetail1.getPriInd())) {
				retVal = -1;
			} else if (StringUtils.equalsIgnoreCase(USSDConstants.PRIMARY_INDICATOR_YES, accountDetail2.getPriInd())) {
				retVal = 1;
			} else {
				retVal = 0;
			}
			return retVal;
		}

	}
