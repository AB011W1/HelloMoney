package com.barclays.bmg.mvc.controller.offer;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.offer.EipOfferDetlsCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.offer.EIPOfferDetailOperation;
import com.barclays.bmg.operation.offer.request.EIPOfferDetailOperationRequest;
import com.barclays.bmg.operation.offer.response.EIPOfferDetailOperationResponse;

public class EIPOffersDetailController extends BMBAbstractCommandController {

	private EIPOfferDetailOperation eipOfferDetailOperation;
	private BMBJSONBuilder bmbJsonBuilder;

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		EipOfferDetlsCommand eipOfferDetlsCmd = (EipOfferDetlsCommand) command;

		EIPOfferDetailOperationRequest eipOfferDetlsOperReq = makeRequest(request);
		eipOfferDetlsOperReq.setOfferId(eipOfferDetlsCmd.getOfferId());

		EIPOfferDetailOperationResponse eipOfferDetlOperResp = eipOfferDetailOperation
				.retrieveAllEipOffersDetail(eipOfferDetlsOperReq);
		finishOff(request);
		return (BMBBaseResponseModel) bmbJsonBuilder
				.createJSONResponse(eipOfferDetlOperResp);
	}

	private EIPOfferDetailOperationRequest makeRequest(
			HttpServletRequest request) {

		EIPOfferDetailOperationRequest eipOfferRequest = new EIPOfferDetailOperationRequest();

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

	@Override
	protected String getActivityId(Object command) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setEipOfferDetailOperation(
			EIPOfferDetailOperation eipOfferDetailOperation) {
		this.eipOfferDetailOperation = eipOfferDetailOperation;
	}

	public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
		this.bmbJsonBuilder = bmbJsonBuilder;
	}

}
