package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.BEMServiceHeader.BEMResHeader;
import com.barclays.bem.BEMServiceHeader.Error;
import com.barclays.bem.RetrieveCASAAcctDetails.RetrieveCASAAccountDetailsResponse;
import com.barclays.bmg.constants.AccountErrorCodeConstant;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.bmg.mapper.CASAAccountToCoreCASAAccount;
import com.barclays.bmg.service.accountdetails.response.CASADetailsServiceResponse;

public class CASADetailsRespAdptOperation extends AbstractResAdptOperation {

    public CASADetailsServiceResponse adaptResponseForCASADetails(WorkContext workContext, Object obj) throws Exception {

	CASADetailsServiceResponse casaDetailsServiceResponse = new CASADetailsServiceResponse();

	RetrieveCASAAccountDetailsResponse retrieveCASAAccountDetailsResponse = (RetrieveCASAAccountDetailsResponse) obj;

	String respCode = checkServiceResponseHeader(retrieveCASAAccountDetailsResponse.getResponseHeader());

	if (respCode.equals(ErrorCodeConstant.SUCCESS_CODE)) {

	    CASAAccountToCoreCASAAccount casaAccountMapper = new CASAAccountToCoreCASAAccount();
	    CASAAccountDTO casaAccountDTO = casaAccountMapper.mapBean(retrieveCASAAccountDetailsResponse.getCASAAccountDetails(), null);

	    casaDetailsServiceResponse.setCasaAccountDTO(casaAccountDTO);
	    casaDetailsServiceResponse.setSuccess(true);
	} else {
	    casaDetailsServiceResponse.setSuccess(false);
	}

	casaDetailsServiceResponse.setResCde(respCode);
	return casaDetailsServiceResponse;

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
			returnCode = AccountServiceResponseCodeConstant.ACCOUNT_DETAILS_INVALID_ACCOUNT_NO;
		    } else {
			throw new BMBDataAccessException(error.getErrorCode(), error.getErrorDesc());
		    }
		}
	    }
	}
	return returnCode;
    }
}
