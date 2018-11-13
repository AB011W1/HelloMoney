package com.barclays.bmg.mvc.controller.auth;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.operation.TermsOfUseAcceptOperation;
import com.barclays.bmg.operation.request.TermsOfUseAcceptOperationRequest;
import com.barclays.bmg.operation.response.TermsOfUseAcceptOperationResponse;

public class TermsOfUseAcceptController extends BMBAbstractController {

	private TermsOfUseAcceptOperation termsOfUseAcceptOperation;
	private BMBJSONBuilder bmbJsonBuilder;
	private String requestUri;

	@Override
	protected BMBBaseResponseModel handleRequestInternal1(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		BMBBaseResponseModel returnBMBPayloadResponse = null;

		TermsOfUseAcceptOperationRequest termsOfUseAcceptOperationReq = makeRequest(request);

		TermsOfUseAcceptOperationResponse termsOfUseAcceptOperationResp = termsOfUseAcceptOperation
				.acceptTermsAndCondition(termsOfUseAcceptOperationReq);

		if (termsOfUseAcceptOperationResp.isSuccess()) {

			request.getRequestDispatcher(requestUri).forward(request, response);

		} else {

			returnBMBPayloadResponse = (BMBBaseResponseModel) bmbJsonBuilder
					.createJSONResponse(termsOfUseAcceptOperationResp);
		}

		return returnBMBPayloadResponse;

	}

	/**
	 * make request for otp verification
	 *
	 * @param request
	 * @return
	 */
	private TermsOfUseAcceptOperationRequest makeRequest(
			HttpServletRequest request) {

		TermsOfUseAcceptOperationRequest termsOfUseAcceptOperationReq = new TermsOfUseAcceptOperationRequest();

		Context context = new Context();

		Map<String, Object> userMap = getUserMapFromSession(request);
		context.setBusinessId((String) userMap
				.get(SessionConstant.SESSION_BUSINESS_ID));
		context.setCountryCode((String) userMap
				.get(SessionConstant.SESSION_COUNTRY_CODE));
		context.setLanguageId((String) userMap
				.get(SessionConstant.SESSION_LANGUAGE_ID));
		context.setSystemId((String) userMap
				.get(SessionConstant.SESSION_SYSTEM_ID));
		context.setCustomerId((String) userMap
				.get(SessionConstant.SESSION_CUSTOMER_ID));
		context
				.setUserId((String) userMap
						.get(SessionConstant.SESSION_USER_ID));

		termsOfUseAcceptOperationReq.setContext(context);

		return termsOfUseAcceptOperationReq;

	}

	public void setTermsOfUseAcceptOperation(
			TermsOfUseAcceptOperation termsOfUseAcceptOperation) {
		this.termsOfUseAcceptOperation = termsOfUseAcceptOperation;
	}

	public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
		this.bmbJsonBuilder = bmbJsonBuilder;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	@Override
	protected String getActivityId() {
		// TODO Auto-generated method stub
		return null;
	}

}