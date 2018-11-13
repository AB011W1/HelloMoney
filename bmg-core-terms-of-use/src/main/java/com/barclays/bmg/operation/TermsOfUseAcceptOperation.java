package com.barclays.bmg.operation;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.operation.request.TermsOfUseAcceptOperationRequest;
import com.barclays.bmg.operation.response.TermsOfUseAcceptOperationResponse;
import com.barclays.bmg.service.TermsOfUseService;
import com.barclays.bmg.service.request.TermsOfUseAcceptServiceRequest;
import com.barclays.bmg.service.response.TermsOfUseAcceptServiceResponse;

public class TermsOfUseAcceptOperation extends BMBCommonOperation {

	private TermsOfUseService termsOfUseService;

	public TermsOfUseAcceptOperationResponse acceptTermsAndCondition(
			TermsOfUseAcceptOperationRequest request) {

		TermsOfUseAcceptOperationResponse returnTermsOfUseAcceptOperationResp = new TermsOfUseAcceptOperationResponse();

		Context context = request.getContext();

		loadParameters(context, ActivityConstant.COMMON_ID,
				ActivityConstant.SEC_COMMON_ID);

		String termsOfUseVer = (String) context.getContextMap().get(
				"TERMS_OF_USE_VERSION_NO");

		TermsOfUseAcceptServiceRequest termsOfUseAcceptServiceReq = new TermsOfUseAcceptServiceRequest();
		termsOfUseAcceptServiceReq.setContext(context);
		termsOfUseAcceptServiceReq.setTermsOfUseVersionNo(termsOfUseVer);
		termsOfUseAcceptServiceReq.setAcceptFlag("Y");

		TermsOfUseAcceptServiceResponse termsOfUseServiceResp = termsOfUseService
				.acceptTermsOfUse(termsOfUseAcceptServiceReq);

		if (termsOfUseServiceResp.isSuccess()) {

			returnTermsOfUseAcceptOperationResp.setSuccess(true);
		} else {
			returnTermsOfUseAcceptOperationResp.setSuccess(false);
		}

		return returnTermsOfUseAcceptOperationResp;

	}

	public void setTermsOfUseService(TermsOfUseService termsOfUseService) {
		this.termsOfUseService = termsOfUseService;
	}

}