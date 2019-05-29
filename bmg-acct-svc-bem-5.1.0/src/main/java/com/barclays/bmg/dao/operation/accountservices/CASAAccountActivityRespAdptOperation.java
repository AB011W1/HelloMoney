package com.barclays.bmg.dao.operation.accountservices;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bem.BEMServiceHeader.BEMResHeader;
import com.barclays.bem.BEMServiceHeader.Error;
import com.barclays.bem.RetrieveCASAAcctTransactionActivity.RetrieveCASAAccountTransactionActivityResponse;
import com.barclays.bmg.constants.AccountErrorCodeConstant;
import com.barclays.bmg.constants.AccountServiceResponseCodeConstant;
import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.AccountActivityDTO;
import com.barclays.bmg.dto.AccountActivityListDTO;
import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.bmg.mapper.CASAAccountActivityToCoreAccountActivity;
import com.barclays.bmg.service.accountdetails.request.CASAAccountActivityServiceRequest;
import com.barclays.bmg.service.accountdetails.response.CASAAccountActivityServiceResponse;

public class CASAAccountActivityRespAdptOperation extends AbstractResAdptOperationAcct {

    public CASAAccountActivityServiceResponse adaptResponseForCASAAccountActivity(WorkContext workContext, Object obj) throws Exception {

	CASAAccountActivityServiceResponse casaAccountActivityServiceResponse = new CASAAccountActivityServiceResponse();

	RetrieveCASAAccountTransactionActivityResponse casaAccountTransactionActivityResponse = (RetrieveCASAAccountTransactionActivityResponse) obj;

	String respCode = checkServiceResponseHeader(casaAccountTransactionActivityResponse.getResponseHeader());

	if (respCode.equals(ErrorCodeConstant.SUCCESS_CODE)) {
	    casaAccountTransactionActivityResponse.getCASAAccountTransactionList();

	    CASAAccountActivityToCoreAccountActivity mapper = new CASAAccountActivityToCoreAccountActivity();

	    AccountActivityListDTO accountActivityListDTO = mapper.mapBean(casaAccountTransactionActivityResponse.getCASAAccountTransactionList(),
		    null);

	    DAOContext daoContext = (DAOContext) workContext;
	    Object[] args = daoContext.getArguments();
	    CASAAccountActivityServiceRequest casaAccountActivityRequest = (CASAAccountActivityServiceRequest) args[0];
	    Context context = casaAccountActivityRequest.getContext();

	    int pageSize = 50;
	    int maxPages = 10;

	    try {
		pageSize = Integer.parseInt((String) context.getContextMap().get("BEM_PAGE_SIZE_CASA_ACTIVITY"));
	    } catch (Exception e) {
	    }

	    try {
		maxPages = Integer.parseInt((String) context.getContextMap().get("BEM_MAX_PAGES_CASA_ACTIVITY"));
	    } catch (Exception e) {
	    }

	    List<AccountActivityDTO> activityList = accountActivityListDTO.getActivityList();
	    List<AccountActivityDTO> activityListRes = new ArrayList<AccountActivityDTO>();

	    if (activityList != null) {
		int activityListSize = activityList.size();

		for (int i = 0; i < (pageSize * maxPages) && (i < activityListSize); i++) {
		    if (activityListSize > 0) {
			activityListRes.add(activityList.get(i));
		    } else {
			break;
		    }
		}
	    }

	    accountActivityListDTO.setActivityList(activityListRes);

	    casaAccountActivityServiceResponse.setAccountActivityListDTO(accountActivityListDTO);
	    casaAccountActivityServiceResponse.setSuccess(true);
	    casaAccountActivityServiceResponse.setResCde(ErrorCodeConstant.SUCCESS_CODE);

	} else {
	    casaAccountActivityServiceResponse.setSuccess(false);

	}
	casaAccountActivityServiceResponse.setResCde(respCode);
	return casaAccountActivityServiceResponse;

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
