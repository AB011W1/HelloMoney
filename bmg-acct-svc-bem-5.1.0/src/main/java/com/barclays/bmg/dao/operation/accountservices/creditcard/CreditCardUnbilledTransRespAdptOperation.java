package com.barclays.bmg.dao.operation.accountservices.creditcard;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bem.BEMServiceHeader.BEMResHeader;
import com.barclays.bem.BEMServiceHeader.Error;
import com.barclays.bem.RetrieveCreditCardUnbilledTransactions.RetrieveCreditCardUnbilledTransactionsResponse;
import com.barclays.bmg.constants.AccountErrorCodeConstant;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.operation.accountservices.AbstractResAdptOperation;
import com.barclays.bmg.dto.CreditCardActivityDTO;
import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.bmg.mapper.CreditCardUnbilledTransMapper;
import com.barclays.bmg.service.accountdetails.response.CreditCardUnbilledTransServiceResponse;

public class CreditCardUnbilledTransRespAdptOperation extends AbstractResAdptOperation {

    public CreditCardUnbilledTransServiceResponse adaptResponseForCreditCardUnbilledTrans(WorkContext workContext, Object obj) throws Exception {

	CreditCardUnbilledTransServiceResponse returnCCResponse = new CreditCardUnbilledTransServiceResponse();

	RetrieveCreditCardUnbilledTransactionsResponse retrieveCCUnbilledTransResp = (RetrieveCreditCardUnbilledTransactionsResponse) obj;

	String respCode = checkServiceResponseHeader(retrieveCCUnbilledTransResp.getResponseHeader());

	if (respCode.equals(ErrorCodeConstant.SUCCESS_CODE)) {

	    List<CreditCardActivityDTO> ccActivityDTOList = new ArrayList<CreditCardActivityDTO>();

	    if (retrieveCCUnbilledTransResp.getCreditCardUnbilledTransactionList() != null) {

		CreditCardUnbilledTransMapper ccUnbilledTransMapper = new CreditCardUnbilledTransMapper();
		ccActivityDTOList = ccUnbilledTransMapper.mapCollectionPrimeVPlus(retrieveCCUnbilledTransResp.getCreditCardUnbilledTransactionList()
			.getTransactionActivityList());

	    }

	    returnCCResponse.setCreditCardActivityDTOList(ccActivityDTOList);
	    returnCCResponse.setSuccess(true);
	} else if (respCode.equals(ErrorCodeConstant.BUSINESS_ERROR)) {

	    returnCCResponse.setSuccess(false);
	    returnCCResponse.setCreditCardActivityDTOList(null);

	} else {
	    returnCCResponse.setSuccess(false);
	}

	returnCCResponse.setResCde(respCode);

	return returnCCResponse;

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

			if (error.getErrorCode().equals(AccountErrorCodeConstant.NO_UNBILLED_TRANX_FOUND)) {
			    returnCode = AccountServiceResponseCodeConstant.NO_TRANSACTIONS_FOUND_FOR_REQUEST;
			}
		    }
		}

	    } else if (ErrorCodeConstant.BUSINESS_ERROR.equals(resCode)) {

		returnCode = ErrorCodeConstant.BUSINESS_ERROR;
	    } else if (errorList != null) {

		for (com.barclays.bem.BEMServiceHeader.Error error : errorList) {

		    if (error.getErrorCode().equals(AccountErrorCodeConstant.CC_ACCOUNT_NO_NOT_EXIST)) {
			returnCode = AccountServiceResponseCodeConstant.ACCOUNT_ACTIVITY_INVALID_ACCOUNT_NO;
		    } else if (error.getErrorCode().equals(AccountErrorCodeConstant.CC_ACCOUNT_NO_NOT_EMPTY_PRIME)) {
			returnCode = AccountServiceResponseCodeConstant.ACCOUNT_ACTIVITY_INVALID_ACCOUNT_NO;
		    }
		    if (error.getErrorCode().equals(AccountErrorCodeConstant.NO_UNBILLED_TRANX_FOUND)) {
			returnCode = AccountServiceResponseCodeConstant.NO_TRANSACTIONS_FOUND_FOR_REQUEST;
		    } else {
			throw new BMBDataAccessException(error.getErrorCode(), error.getErrorDesc());
		    }
		}
	    }
	}

	return returnCode;
    }

}
