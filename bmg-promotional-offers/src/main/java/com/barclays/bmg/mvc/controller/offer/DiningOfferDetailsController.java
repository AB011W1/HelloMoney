package com.barclays.bmg.mvc.controller.offer;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.offer.DiningOfferDetlsCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.offer.DiningOfferDetailsOperation;
import com.barclays.bmg.operation.offer.request.DiningOfferDetailsOperationRequest;
import com.barclays.bmg.operation.offer.response.DiningOfferDetailsOperationResponse;

public class DiningOfferDetailsController extends BMBAbstractCommandController {

	private DiningOfferDetailsOperation diningOfferDetlsOperation;
	private BMBJSONBuilder bmbJsonBuilder;



	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		// TODO Auto-generated method stub

		DiningOfferDetlsCommand diningOfferCmd =  (DiningOfferDetlsCommand) command;
		DiningOfferDetailsOperationRequest diningOfferDetlsOprReq = makeRequest(request);
		diningOfferDetlsOprReq.setOfferId(diningOfferCmd.getOfferId());

		DiningOfferDetailsOperationResponse diningOfferDetlsOprResp =
						diningOfferDetlsOperation.retrieveDiningOfferDetails(diningOfferDetlsOprReq);
		finishOff(request);
		return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(diningOfferDetlsOprResp);
	}


	private DiningOfferDetailsOperationRequest makeRequest(
			HttpServletRequest request) {

		DiningOfferDetailsOperationRequest diningOfferOperReq = new DiningOfferDetailsOperationRequest();

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

	@Override
	protected String getActivityId(Object command) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setDiningOfferDetlsOperation(
			DiningOfferDetailsOperation diningOfferDetlsOperation) {
		this.diningOfferDetlsOperation = diningOfferDetlsOperation;
	}

	public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
		this.bmbJsonBuilder = bmbJsonBuilder;
	}



}
