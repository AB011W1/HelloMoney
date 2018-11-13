package com.barclays.bmg.dao.operation.accountservices;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.barclays.bem.BEMServiceHeader.BEMResHeader;
import com.barclays.bem.BEMServiceHeader.Error;
import com.barclays.bem.RetrieveAcctTransactionHistory.AcctTransactionActivityType;
import com.barclays.bem.RetrieveAcctTransactionHistory.RetrieveAcctTransactionHistoryResponse;
import com.barclays.bem.TransactionHistory.TransactionHistory;
import com.barclays.bmg.constants.AccountErrorCodeConstant;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.AccountTrnxDTO;
import com.barclays.bmg.dto.AccountTrnxHistoryDTO;
import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.bmg.service.accountdetails.request.CASAAccountActivityServiceRequest;
import com.barclays.bmg.service.accountdetails.response.CASAAccountTrnxHistoryServiceResponse;

public class CASAAccountTrnxHistoryRespAdptOperation extends AbstractResAdptOperation {

    public CASAAccountTrnxHistoryServiceResponse adaptResponseForCASAAccountActivity(WorkContext workContext, Object obj) throws Exception {

	CASAAccountTrnxHistoryServiceResponse accttrnxHistoryServiceResponse = new CASAAccountTrnxHistoryServiceResponse();

	AcctTransactionActivityType acctTransactionActivityType = null;
	List<AccountTrnxDTO> trnsactionActivityList = new ArrayList<AccountTrnxDTO>();

	RetrieveAcctTransactionHistoryResponse acctTransactionHistoryResponse = (RetrieveAcctTransactionHistoryResponse) obj;

	String respCode = checkServiceResponseHeader(acctTransactionHistoryResponse.getResponseHeader());

	if (respCode.equals(ErrorCodeConstant.SUCCESS_CODE)) {
	    AccountTrnxHistoryDTO accountTrnxHistoryDTO = new AccountTrnxHistoryDTO();

	    acctTransactionActivityType = acctTransactionHistoryResponse.getAcctTransactionActivity();
	    for (TransactionHistory array_element : acctTransactionActivityType.getTransactionActivity()) {
		// TransactionHistory array_element = acctTransactionActivityType.getTransactionActivity()[i];
		AccountTrnxDTO accountTrnxDTO = new AccountTrnxDTO();
		accountTrnxDTO.setCreditDebitTypeCode(array_element.getCreditDebitTypeCode());
		accountTrnxDTO.setTrnxReferenceNumber(array_element.getTransactionReferenceNumber());
		accountTrnxDTO.setTransactionCurrencyAmount(BigDecimal.valueOf(array_element.getTransactionCurrencyAmount()));
		accountTrnxDTO.setTransactionCurrencyCode(array_element.getTransactionCurrencyCode());
		accountTrnxDTO.setTransactionDateTime(array_element.getTransactionDateTime());
		accountTrnxDTO.setTransactionDescriptionCode(array_element.getTransactionDescriptionCode());
		accountTrnxDTO.setTrnxReferenceNumber(array_element.getTransactionReferenceNumber());

		trnsactionActivityList.add(accountTrnxDTO);
	    }
	    accountTrnxHistoryDTO.setTrnsactionActivityList(trnsactionActivityList);

	    DAOContext daoContext = (DAOContext) workContext;
	    Object[] args = daoContext.getArguments();
	    CASAAccountActivityServiceRequest casaAccountActivityRequest = (CASAAccountActivityServiceRequest) args[0];
	    Context context = casaAccountActivityRequest.getContext();

	    accountTrnxHistoryDTO.setAccountNumber(casaAccountActivityRequest.getAccountNo());
	    accountTrnxHistoryDTO.setOpeningBalanceAmount(acctTransactionHistoryResponse.getAcctTransactionActivity().getOpeningBalanceAmount());
	    accountTrnxHistoryDTO.setClosingBalanceAmount(acctTransactionHistoryResponse.getAcctTransactionActivity().getClosingBalanceAmount());

	    accttrnxHistoryServiceResponse.setContext(context);
	    accttrnxHistoryServiceResponse.setAccountTrnxHistoryDTO(accountTrnxHistoryDTO);
	    accttrnxHistoryServiceResponse.setSuccess(true);
	    accttrnxHistoryServiceResponse.setResCde(ErrorCodeConstant.SUCCESS_CODE);

	} else {
	    accttrnxHistoryServiceResponse.setSuccess(false);

	}
	accttrnxHistoryServiceResponse.setResCde(respCode);
	return accttrnxHistoryServiceResponse;

    }

    /*
     * We are just checking for error response. No data is set from response as we only require transaction ref and date.
     */
    private String checkServiceResponseHeader(BEMResHeader resHeader) throws Exception {

	String returnCode = "";

	if (resHeader.getServiceResStatus() != null) {
	    String resCode = resHeader.getServiceResStatus().getServiceResCode();
	    Error[] errorList = resHeader.getErrorList();

	    if (AccountErrorCodeConstant.SUCCESS_CODE.equals(resCode)) {
		returnCode = resCode;
	    }

	    else if (errorList != null) {
		for (com.barclays.bem.BEMServiceHeader.Error error : errorList) {
		    if (error.getErrorCode().equals(AccountErrorCodeConstant.ACCOUNT_NO_NOT_EXIST)) {
			returnCode = AccountServiceResponseCodeConstant.ACCOUNT_ACTIVITY_INVALID_ACCOUNT_NO;
		    } else if (error.getErrorCode().equals(AccountErrorCodeConstant.NO_TRANX_FOUND)) {
			returnCode = AccountServiceResponseCodeConstant.ACCOUNT_ACTIVITY_NO_TRANX_FOUND;
		    } else {
			throw new BMBDataAccessException(error.getErrorCode(), error.getErrorDesc());
		    }
		}
	    }
	}
	return returnCode;
    }
}
