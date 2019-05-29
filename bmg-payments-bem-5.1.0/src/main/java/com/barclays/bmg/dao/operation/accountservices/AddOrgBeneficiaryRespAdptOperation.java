package com.barclays.bmg.dao.operation.accountservices;

import com.barclays.bem.AddOrganizationBeneficiary.AddOrganizationBeneficiaryResponse;
import com.barclays.bem.BEMServiceHeader.BEMResHeader;
import com.barclays.bmg.constants.AddOrgBeneficiaryConstants;
import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.service.response.AddOrgBeneficiaryServiceResponse;

public class AddOrgBeneficiaryRespAdptOperation extends AbstractResAdptOperationAcct {

    public AddOrgBeneficiaryServiceResponse adaptResponse(WorkContext workContext, Object obj) {

	AddOrgBeneficiaryServiceResponse response = new AddOrgBeneficiaryServiceResponse();

	AddOrganizationBeneficiaryResponse bemResponse = (AddOrganizationBeneficiaryResponse) obj;
	BEMResHeader responseHeader = bemResponse.getResponseHeader();
	/*
	 * if (responseHeader != null) { checkRespHeader(responseHeader, response); }else {
	 * response.setResCde(ResponseCodeConstants.EXCEPTION_RES_CODE); } if (response.isSuccess()) {
	 *
	 * response.setConsumerUniqueRefNo(responseHeader.getServiceContext() .getConsumerUniqueRefNo());
	 *
	 * if (null != bemResponse.getTransactionResponseStatus()) { response.setTxnReferenceNumber(bemResponse .getTransactionResponseStatus()
	 * .getTransactionReferenceNumber()); }
	 *
	 * }
	 */
	checkRespHeader(bemResponse.getResponseHeader(),response);
	//if (checkResponseHeader(bemResponse.getResponseHeader())) {
	if (response.isSuccess()) {
	    response.setConsumerUniqueRefNo(responseHeader.getServiceContext().getConsumerUniqueRefNo());

	    if (null != bemResponse.getTransactionResponseStatus()) {
		response.setTxnReferenceNumber(bemResponse.getTransactionResponseStatus().getTransactionReferenceNumber());
	    }

	    response.setSuccess(true);
	    return response;
	}

	response.setSuccess(false);
	return response;
    }

    private void checkRespHeader(BEMResHeader resHeader, ResponseContext response) {

	String resCode = resHeader.getServiceResStatus().getServiceResCode();
	if (ErrorCodeConstant.BUSINESS_ERROR.equals(resCode)) {
	    if (resHeader.getErrorList() != null && resHeader.getErrorList().length > 0) {

		for (com.barclays.bem.BEMServiceHeader.Error error : resHeader.getErrorList()) {
		    response.setSuccess(false);
		    response.setResMsg(error.getErrorDesc());
		    response.setResCde(error.getErrorCode());

		    if (ErrorCodeConstant.BILLREG_REFNUM_EMPTY_ERR.equals(error.getErrorCode())) {
			response.setResCde(AddOrgBeneficiaryConstants.BILLER_REFNUM_EMPTY);
		    }
		    if (ErrorCodeConstant.BILLREG_NICK_EXISTS.equals(error.getErrorCode())) {
			response.setResCde(AddOrgBeneficiaryConstants.BILLER_NICKNAMEALREADY_EXISTS);
		    }
		    if (ErrorCodeConstant.BILLREG_BILLERID_REFERENCENO_EXISTS.equals(error.getErrorCode())) {
				response.setResCde(AddOrgBeneficiaryConstants.BILLERID_REFERENCENUMBER_ALREADYEXISTS);
			}
		}
	    }
	}
	if (response.isSuccess()) {
	    response.setSuccess(super.checkResponseHeader(resHeader));
	}
    }

}
