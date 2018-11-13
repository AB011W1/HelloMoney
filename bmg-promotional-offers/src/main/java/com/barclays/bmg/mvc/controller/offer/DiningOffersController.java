package com.barclays.bmg.mvc.controller.offer;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractController;
import com.barclays.bmg.operation.offer.DiningOfferOperation;
import com.barclays.bmg.operation.offer.request.DiningOfferOperationRequest;
import com.barclays.bmg.operation.offer.response.DiningOfferOperationResponse;

public class DiningOffersController extends BMBAbstractController {

	private BMBJSONBuilder bmbJsonBuilder;
	private DiningOfferOperation diningOfferOperation;

	@Override
	protected String getActivityId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected BMBBaseResponseModel handleRequestInternal1(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub

		DiningOfferOperationRequest diningOfferOprReq = makeRequest(request);
		DiningOfferOperationResponse diningOfferOprResp = diningOfferOperation
				.retrieveAllDiningOffers(diningOfferOprReq);
		finishOff(request);
		return (BMBBaseResponseModel) bmbJsonBuilder
				.createJSONResponse(diningOfferOprResp);
	}

	private DiningOfferOperationRequest makeRequest(HttpServletRequest request) {

		DiningOfferOperationRequest diningOfferOperReq = new DiningOfferOperationRequest();

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

		diningOfferOperReq.setContext(context);

		return diningOfferOperReq;

	}

	public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
		this.bmbJsonBuilder = bmbJsonBuilder;
	}

	public void setDiningOfferOperation(
			DiningOfferOperation diningOfferOperation) {
		this.diningOfferOperation = diningOfferOperation;
	}

}
