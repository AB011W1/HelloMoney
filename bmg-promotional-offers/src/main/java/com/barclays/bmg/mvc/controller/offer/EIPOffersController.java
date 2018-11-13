package com.barclays.bmg.mvc.controller.offer;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractController;
import com.barclays.bmg.operation.offer.EIPOfferOperation;
import com.barclays.bmg.operation.offer.request.EIPOfferOperationRequest;
import com.barclays.bmg.operation.offer.response.EIPOfferOperationResponse;

public class EIPOffersController extends BMBAbstractController {

	private EIPOfferOperation eipOfferOperation;
	private BMBJSONBuilder bmbJsonBuilder;

	@Override
	protected BMBBaseResponseModel handleRequestInternal1(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		EIPOfferOperationRequest eipOfferOperationRequest = makeRequest(request);

		EIPOfferOperationResponse eipOfferOperationResponse = eipOfferOperation
				.retrieveAllEipOffers(eipOfferOperationRequest);
		finishOff(request);
		return (BMBBaseResponseModel) bmbJsonBuilder
				.createJSONResponse(eipOfferOperationResponse);

	}

	private EIPOfferOperationRequest makeRequest(HttpServletRequest request) {

		EIPOfferOperationRequest eipOfferRequest = new EIPOfferOperationRequest();

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

		eipOfferRequest.setContext(context);

		return eipOfferRequest;

	}

	public void setEipOfferOperation(EIPOfferOperation eipOfferOperation) {
		this.eipOfferOperation = eipOfferOperation;
	}

	public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
		this.bmbJsonBuilder = bmbJsonBuilder;
	}

	@Override
	protected String getActivityId() {
		// TODO Auto-generated method stub
		return null;
	}

}
