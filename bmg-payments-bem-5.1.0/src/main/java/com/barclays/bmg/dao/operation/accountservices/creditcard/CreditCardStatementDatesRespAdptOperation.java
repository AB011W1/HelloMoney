package com.barclays.bmg.dao.operation.accountservices.creditcard;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.barclays.bem.BEMServiceHeader.BEMResHeader;
import com.barclays.bem.BEMServiceHeader.Error;
import com.barclays.bem.RetrieveCreditCardAcctStatementDates.RetrieveCreditCardAccountStatementDatesResponse;
import com.barclays.bem.RetrieveCreditCardAcctStatementDates.StatementDateInfo;
import com.barclays.bem.RetrieveCreditCardAcctStatementDates.StatementDateList;
import com.barclays.bmg.constants.AccountErrorCodeConstant;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.operation.accountservices.AbstractResAdptOperationAcct;
import com.barclays.bmg.dto.CreditCardStmtBalanceInfoDTO;
import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.bmg.service.accountdetails.response.CreditCardStatementDatesServiceResponse;
import com.barclays.bmg.utils.ConvertUtils;

public class CreditCardStatementDatesRespAdptOperation extends AbstractResAdptOperationAcct {

    public CreditCardStatementDatesServiceResponse adaptResponseForCreditCardStatementDates(WorkContext workContext, Object obj) throws Exception {

	CreditCardStatementDatesServiceResponse returnCCStmtDtResponse = new CreditCardStatementDatesServiceResponse();

	RetrieveCreditCardAccountStatementDatesResponse bemResponse = (RetrieveCreditCardAccountStatementDatesResponse) obj;

	String respCode = checkServiceResponseHeader(bemResponse.getResponseHeader());

	if (respCode.equals(ErrorCodeConstant.SUCCESS_CODE)) {

	    List<CreditCardStmtBalanceInfoDTO> stmtObjList = new ArrayList<CreditCardStmtBalanceInfoDTO>();

	    StatementDateList stmtdtList = bemResponse.getStatementDateList();

	    if (null != stmtdtList) {

		StatementDateInfo[] stmt = stmtdtList.getStatementDateInfo();
		
		int sequenceIndex=0;
		DecimalFormat numFormat = new DecimalFormat("00");
		for (StatementDateInfo statementDateInfo : stmt) {
		    if (statementDateInfo.getStatementDate() != null) {

			CreditCardStmtBalanceInfoDTO stmtObj = new CreditCardStmtBalanceInfoDTO();

			stmtObj.setStatementDate(statementDateInfo.getStatementDate());

			stmtObj.setPrevBalance(ConvertUtils.convertAmount(statementDateInfo.getStatementBeginningBalance()));
			//stmtObj.setAccountBalance(ConvertUtils.convertAmount(statementDateInfo.getStatementEndBalance()));

			stmtObj.setFeeAndCharge(ConvertUtils.convertAmount(statementDateInfo.getFeesandCharges()));
			stmtObj.setPaymentReceived(ConvertUtils.convertAmount(statementDateInfo.getPaymentsReceived()));
			stmtObj.setTotalCashWithdrawn(ConvertUtils.convertAmount(statementDateInfo.getTotalCashWithdrawn()));
			stmtObj.setTotalPurchase(ConvertUtils.convertAmount(statementDateInfo.getTotalPurchases()));
			//CR75
			stmtObj.setMinDue(ConvertUtils.convertAmount(statementDateInfo.getMinimumDueAmount()));
			stmtObj.setDueDate(statementDateInfo.getDueDate());
			stmtObj.setTotalOutsAmt(ConvertUtils.convertAmount(statementDateInfo.getStatementEndBalance()));
			 //Cards Migration Date Sequence Number
			stmtObj.setSequenceNumber(numFormat.format(sequenceIndex));
			sequenceIndex++;
			stmtObjList.add(stmtObj);

		    }
		}
		// ORCHARD CHANGES END
	    }
	    returnCCStmtDtResponse.setStatementObj(stmtObjList);
	    // }
	    returnCCStmtDtResponse.setSuccess(true);
	} else if (respCode.equals(ErrorCodeConstant.BUSINESS_ERROR)) {

	    returnCCStmtDtResponse.setSuccess(true);
	    returnCCStmtDtResponse.setStatementObj(null);
	    respCode = AccountErrorCodeConstant.SUCCESS_CODE;

	} else {
	    returnCCStmtDtResponse.setSuccess(false);
	}

	returnCCStmtDtResponse.setResCde(respCode);

	return returnCCStmtDtResponse;

    }

    /*
     * checking for error response.
     */
    private String checkServiceResponseHeader(BEMResHeader resHeader) throws Exception {

	String returnCode = "";

	if (resHeader.getServiceResStatus() != null) {
	    String resCode = resHeader.getServiceResStatus().getServiceResCode();
	    Error[] errorList = resHeader.getErrorList();

	    if (AccountErrorCodeConstant.SUCCESS_CODE.equals(resCode)) {
		returnCode = resCode;

		if (errorList != null) {

		    for (com.barclays.bem.BEMServiceHeader.Error error : errorList) {

			if (error.getErrorCode().equals(AccountErrorCodeConstant.NO_STATEMENTS_FOUND)) {
			    returnCode = AccountServiceResponseCodeConstant.NO_STATEMENTS_FOUND;
			}
		    }
		}
	    } else if (ErrorCodeConstant.BUSINESS_ERROR.equals(resCode)) {

		returnCode = ErrorCodeConstant.BUSINESS_ERROR;
		if (errorList != null) {

		    for (com.barclays.bem.BEMServiceHeader.Error error : errorList) {

			if (error.getErrorCode().equals(AccountErrorCodeConstant.NO_STATEMENTS_FOUND)) {
			    returnCode = AccountServiceResponseCodeConstant.NO_STATEMENTS_FOUND;
			}
		    }
		}
	    } else if (errorList != null) {
		for (com.barclays.bem.BEMServiceHeader.Error error : errorList) {
		    if (error.getErrorCode().equals(AccountErrorCodeConstant.CC_ACCOUNT_NO_NOT_EXIST)) {
			returnCode = AccountServiceResponseCodeConstant.ACCOUNT_ACTIVITY_INVALID_ACCOUNT_NO;
		    } else if (error.getErrorCode().equals(AccountErrorCodeConstant.CC_ACCOUNT_NO_NOT_EMPTY_PRIME)) {
			returnCode = AccountServiceResponseCodeConstant.ACCOUNT_ACTIVITY_INVALID_ACCOUNT_NO;
		    } else if (error.getErrorCode().equals(AccountErrorCodeConstant.CC_INVALID_STMT_DATE)) {
			returnCode = AccountServiceResponseCodeConstant.ACCOUNT_ACTIVITY_NO_TRANX_FOUND;
		    } else if (error.getErrorCode().equals(AccountErrorCodeConstant.NO_STATEMENTS_FOUND)) {
			returnCode = AccountServiceResponseCodeConstant.NO_STATEMENTS_FOUND;
		    } else {
			throw new BMBDataAccessException(error.getErrorCode(), error.getErrorDesc());
		    }
		}
	    }
	}

	return returnCode;
    }
}
