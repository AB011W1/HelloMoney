package com.barclays.bmg.mvc.controller.offer;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.offer.PromoOfferTermsAndConditionCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.offer.PromoOfferTermsAndCondOperation;
import com.barclays.bmg.operation.request.TermsOfUseOperationRequest;
import com.barclays.bmg.operation.response.TermsOfUseOperationResponse;

public class PromoOfferTermsAndCondController extends
		BMBAbstractCommandController {

	PromoOfferTermsAndCondOperation operation;
	private BMBJSONBuilder bmbJsonBuilder;

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		PromoOfferTermsAndConditionCommand cmdTC = (PromoOfferTermsAndConditionCommand)command;

		TermsOfUseOperationRequest termsOfUseOperReq = makeRequest(request);
		termsOfUseOperReq.setOfferType(cmdTC.getOfrTyp());

		TermsOfUseOperationResponse  termsOfUseOperResp = operation.retrieveTermsAndCondition(termsOfUseOperReq);


		return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(termsOfUseOperResp);
	}




	private TermsOfUseOperationRequest makeRequest(
			HttpServletRequest request) {

		TermsOfUseOperationRequest termsOfUseReq = new TermsOfUseOperationRequest();

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

		termsOfUseReq.setContext(context);

		return termsOfUseReq;
	}

	public void setOperation(PromoOfferTermsAndCondOperation operation) {
		this.operation = operation;
	}

	public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
		this.bmbJsonBuilder = bmbJsonBuilder;
	}

	@Override
	protected String getActivityId(Object command) {
		// TODO Auto-generated method stub
		return null;
	}





}
