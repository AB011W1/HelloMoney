package com.barclays.bmg.mvc.controller.auth;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.operation.TermsOfUseDetailOperation;
import com.barclays.bmg.operation.request.TermsOfUseOperationRequest;
import com.barclays.bmg.operation.response.TermsOfUseOperationResponse;

public class TermsOfUseDetailController extends BMBAbstractController {

	private TermsOfUseDetailOperation termsOfUseDetailOperation;
	private BMBJSONBuilder bmbJsonBuilder;
	private String requestUri = "";

	@Override
	protected BMBBaseResponseModel handleRequestInternal1(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		BMBBaseResponseModel returnBMBPayloadResponse = null;

		TermsOfUseOperationRequest termsOfUseOperationReq = makeRequest(request);

		TermsOfUseOperationResponse termsOfUseOperationResp = termsOfUseDetailOperation
				.retrieveTermsAndCondition(termsOfUseOperationReq);

		if (termsOfUseOperationResp.isSuccess()) {

			returnBMBPayloadResponse = (BMBBaseResponseModel) bmbJsonBuilder
					.createJSONResponse(termsOfUseOperationResp);
		} else {

			request.getRequestDispatcher("/dataserv?opCde=OP0200&serVer=2.0").forward(request, response);

		}

		return returnBMBPayloadResponse;

	}

	/**
	 * make request for otp verification
	 *
	 * @param request
	 * @return
	 */
	private TermsOfUseOperationRequest makeRequest(HttpServletRequest request) {

		TermsOfUseOperationRequest termsOfUseOperationRequest = new TermsOfUseOperationRequest();

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

		termsOfUseOperationRequest.setContext(context);

		return termsOfUseOperationRequest;

	}

	public void setTermsOfUseDetailOperation(
			TermsOfUseDetailOperation termsOfUseDetailOperation) {
		this.termsOfUseDetailOperation = termsOfUseDetailOperation;
	}

	public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
		this.bmbJsonBuilder = bmbJsonBuilder;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	public String getRequestUri() {
		return requestUri;
	}

	@Override
	protected String getActivityId() {
		// TODO Auto-generated method stub
		return null;
	}

}
