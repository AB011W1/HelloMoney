package com.barclays.bmg.operation.accountdetails;

import java.text.ParseException;
import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.operation.accountdetails.request.CreditCardActivityTransOperationRequest;
import com.barclays.bmg.service.accountdetails.CreditCardDetailsService;
import com.barclays.bmg.service.accountdetails.request.CreditCardAccountActivityServiceRequest;
import com.barclays.bmg.service.accountdetails.response.CreditCardTransActivityServiceResponse;
import com.barclays.bmg.service.accounts.AllAccountService;

public class CreditCardActivityTransOperation extends AbstractCreditCardOperation {

	private CreditCardDetailsService creditCardDetailsService;
	private AllAccountService allAccountService;

	public AllAccountService getAllAccountService() {
		return allAccountService;
	}

	public void setAllAccountService(AllAccountService allAccountService) {
		this.allAccountService = allAccountService;
	}

	public CreditCardDetailsService getCreditCardDetailsService() {
		return creditCardDetailsService;
	}

	public void setCreditCardDetailsService(CreditCardDetailsService creditCardDetailsService) {
		this.creditCardDetailsService = creditCardDetailsService;
	}

	@SuppressWarnings("deprecation")
	@AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, serviceDescription = "SD_RETRIEVE_CCD_STATEMENT_TRANS", stepId = "1", activityType = "auditCCDStmtTrans")
	public CreditCardTransActivityServiceResponse retrieveCreditCardActivityTrans(
			CreditCardActivityTransOperationRequest request) throws ParseException {
		Context context = request.getContext();

		loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID, context.getActivityId());

		loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID, context.getActivityId());

		CreditCardAccountActivityServiceRequest ccActivityServiceReq = new CreditCardAccountActivityServiceRequest();
		ccActivityServiceReq.setContext(context);
		ccActivityServiceReq.setAccountNumber(request.getAccountNumber());
		ccActivityServiceReq.setStatementTrxFlag(true);
		ccActivityServiceReq.setStatementDate(request.getStatementDate());
		CreditCardTransActivityServiceResponse ccStmtDatesServiceResp = creditCardDetailsService
				.retrieveCreditCardAccountActivity(ccActivityServiceReq);

		return ccStmtDatesServiceResp;
	}
}