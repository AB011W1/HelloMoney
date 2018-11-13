package com.barclays.bmg.operation.accountdetails;

import java.util.List;
import java.util.Map;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CreditCardActivityDTO;
import com.barclays.bmg.dto.CreditCardTransactionHistoryDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.operation.accountdetails.request.CreditCardUnbilledTransOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.CreditCardUnbilledTransOperationResponse;
import com.barclays.bmg.service.accountdetails.CreditCardDetailsService;
import com.barclays.bmg.service.accountdetails.request.CreditCardUnbilledTransServiceRequest;
import com.barclays.bmg.service.accountdetails.response.CreditCardUnbilledTransServiceResponse;
import com.barclays.bmg.service.accounts.AllAccountService;
import com.barclays.bmg.service.accounts.request.AllAccountServiceRequest;
import com.barclays.bmg.service.accounts.response.AllAccountServiceResponse;

/**
 * @author BMB Team
 * 
 */

public class CreditCardUnbilledTransOperation extends AbstractCreditCardOperation {

    private AllAccountService allAccountService; // ORCHARD CHANGES
    private CreditCardDetailsService creditCardDetailsService;

    /**
     * 1. retrieve the creditcard account details 2. retrieve creditcard unbilled transactions
     * 
     * @param request
     * @return
     * 
     */
    @AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, serviceDescription = "SD_RETRIEVE_CCD_UNBILLED_TRANS", stepId = "1", activityType = "auditCCDUnbilledTrans")
    public CreditCardUnbilledTransOperationResponse retrieveCreditCardUnbilledTrans(CreditCardUnbilledTransOperationRequest request) {

	CreditCardUnbilledTransOperationResponse returnCCUnbilledTransOperationResp = new CreditCardUnbilledTransOperationResponse();
	boolean respSuccessFlg = false;
	String respCode = "";
	String orgCode = "";
	Context context = request.getContext();

	super.loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID, context.getActivityId());
	Map<String, Object> contextMap = context.getContextMap();

	AllAccountServiceRequest allAccountServiceRequest = new AllAccountServiceRequest();
	allAccountServiceRequest.setContext(context);
	allAccountServiceRequest.setAccountNo(request.getAccountNo());
	// Retrive all account List from BEM
	AllAccountServiceResponse allAccountServiceResp = allAccountService.retrieveCreditCardList(allAccountServiceRequest);
	// ORCHARD CHANGES END

	CreditCardAccountDTO creditCardAccountDTO = null;
	CreditCardTransactionHistoryDTO primaryCardHistory = null;
	List<CreditCardTransactionHistoryDTO> replacementCardGroupedHistoryList = null;
	List<CreditCardTransactionHistoryDTO> supplimentryCardGroupedHistoryList = null;
	CreditCardTransactionHistoryDTO selectedCardHistory = null;
	List<CreditCardActivityDTO> activityList = null;
	if (allAccountServiceResp != null) {

	    respSuccessFlg = allAccountServiceResp.isSuccess();
	    respCode = allAccountServiceResp.getResCde();
	    if (respSuccessFlg) {

		String reqAccNo = request.getAccountNo();

		List<? extends CustomerAccountDTO> customerAccountDTOList = allAccountServiceResp.getAccountList();

		for (CustomerAccountDTO customerAccountDTO : customerAccountDTOList) {

		    if (customerAccountDTO instanceof CreditCardAccountDTO) {

			String accNo = customerAccountDTO.getAccountNumber();

			if (accNo.equalsIgnoreCase(reqAccNo)) {

			    creditCardAccountDTO = (CreditCardAccountDTO) customerAccountDTO;

			    if (creditCardAccountDTO.getPrimary() != null) {

				orgCode = creditCardAccountDTO.getPrimary().getCreditCardOrgCode();

			    }
			    break;

			}
		    }
		}

		/*
		 * List<CreditCardDTO> addSupplimentrylist = null;
		 * 
		 * addSupplimentrylist = getAddSupplimentry(creditCardAccountDTO, context, orgCode, reqAccNo);
		 * 
		 * if (addSupplimentrylist != null) {
		 * 
		 * for (int i = 0; i < addSupplimentrylist.size(); i++) { creditCardAccountDTO.addSupplementary(addSupplimentrylist.get(i)); } }
		 */

		CreditCardUnbilledTransServiceRequest ccUnbilledTransServiceReq = new CreditCardUnbilledTransServiceRequest();
		ccUnbilledTransServiceReq.setContext(context);
		ccUnbilledTransServiceReq.setAccountNo(request.getAccountNo());

		CreditCardUnbilledTransServiceResponse ccUnbilledTransServiceResp = creditCardDetailsService
			.retrieveCreditCardUnbilledTrans(ccUnbilledTransServiceReq);

		if (ccUnbilledTransServiceResp != null) {
		    respSuccessFlg = ccUnbilledTransServiceResp.isSuccess();
		    respCode = ccUnbilledTransServiceResp.getResCde();

		    if (respSuccessFlg) {

			if (ccUnbilledTransServiceResp != null && ccUnbilledTransServiceResp.getCreditCardActivityDTOList() != null) {
			    activityList = ccUnbilledTransServiceResp.getCreditCardActivityDTOList();

			    if (activityList != null) {

				primaryCardHistory = getPrimaryCardHistoryList(creditCardAccountDTO, activityList);
				replacementCardGroupedHistoryList = getReplacementCardGroupedHistoryList(creditCardAccountDTO, activityList);
				supplimentryCardGroupedHistoryList = getSupplimentryCardGroupedHistoryList(creditCardAccountDTO, activityList);
				selectedCardHistory = getSelectedCardHistory(request.getCardNo(), primaryCardHistory,
					replacementCardGroupedHistoryList, supplimentryCardGroupedHistoryList, true);

			    } else {

				primaryCardHistory = getPrimaryCardHistoryListWithoutActivityList(creditCardAccountDTO);
				selectedCardHistory = getSelectedCardHistory(request.getCardNo(), primaryCardHistory,
					replacementCardGroupedHistoryList, supplimentryCardGroupedHistoryList, false);

			    }
			} else {

			    primaryCardHistory = getPrimaryCardHistoryListWithoutActivityList(creditCardAccountDTO);
			    selectedCardHistory = getSelectedCardHistory(request.getCardNo(), primaryCardHistory, replacementCardGroupedHistoryList,
				    supplimentryCardGroupedHistoryList, false);

			}

		    }
		} else {

		    primaryCardHistory = getPrimaryCardHistoryListWithoutActivityList(creditCardAccountDTO);
		    selectedCardHistory = getSelectedCardHistory(request.getCardNo(), primaryCardHistory, replacementCardGroupedHistoryList,
			    supplimentryCardGroupedHistoryList, false);

		}

	    }
	}
	// }

	if (respCode.equals(AccountServiceResponseCodeConstant.NO_TRANSACTIONS_FOUND_FOR_REQUEST)) {
	    respCode = AccountServiceResponseCodeConstant.NO_TRANSACTIONS_FOUND_FOR_REQUEST;
	    respSuccessFlg = false;
	}
	if (respCode.equals(AccountServiceResponseCodeConstant.ACCOUNT_DETAILS_INVALID_ACCOUNT_NO)) {
	    respCode = AccountServiceResponseCodeConstant.ACCOUNT_ACTIVITY_INVALID_ACCOUNT_NO;
	}

	if (selectedCardHistory != null && !request.getCardNo().equals(selectedCardHistory.getCardNumber())) {

	    respCode = AccountServiceResponseCodeConstant.ACCOUNT_ACTIVITY_INVALID_CRD_NO;
	    respSuccessFlg = false;
	}

	returnCCUnbilledTransOperationResp.setSuccess(respSuccessFlg);
	returnCCUnbilledTransOperationResp.setResCde(respCode);
	returnCCUnbilledTransOperationResp.setContext(context);
	returnCCUnbilledTransOperationResp.setCreditCardAccountDTO(creditCardAccountDTO);
	returnCCUnbilledTransOperationResp.setCreditCardHistory(selectedCardHistory);
	returnCCUnbilledTransOperationResp.setActivityList(activityList);

	if (!returnCCUnbilledTransOperationResp.isSuccess()) {
	    getMessage(returnCCUnbilledTransOperationResp);
	}

	return returnCCUnbilledTransOperationResp;

    }

    public void setAllAccountService(AllAccountService allAccountService) {
	this.allAccountService = allAccountService;
    }

    public void setCreditCardDetailsService(CreditCardDetailsService creditCardDetailsService) {
	this.creditCardDetailsService = creditCardDetailsService;
    }

    // ORCHARD CHANGES END

}