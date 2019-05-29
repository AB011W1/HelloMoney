package com.barclays.bmg.mvc.controller.offer;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.offer.SearchEIPOfferOperation;
import com.barclays.bmg.operation.offer.request.EIPOfferOperationRequest;
import com.barclays.bmg.operation.offer.response.EIPOfferOperationResponse;

public class SearchEIPOffersController extends BMBAbstractCommandController {

    private SearchEIPOfferOperation searchEIPOfferOperation;
    private BMBJSONBuilder bmbJsonBuilder;

    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
	    throws Exception {

	EIPOfferOperationRequest eipOfferOperReq = makeRequest(request);
	// eipOfferOperReq.setCategory(searchEipOfferCmd.getCat());
	// eipOfferOperReq.setOffer(searchEipOfferCmd.getEipOffer());
	eipOfferOperReq.setPartner("Text book Centre");// searchEipOfferCmd.getPrtNam()
	// eipOfferOperReq.setCity(searchEipOfferCmd.getCity());

	EIPOfferOperationResponse eipOfferOperResp = searchEIPOfferOperation.searchEipOffers(eipOfferOperReq);
	// finishOff(request);
	return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(eipOfferOperResp);
    }

    private EIPOfferOperationRequest makeRequest(HttpServletRequest request) {

	EIPOfferOperationRequest eipOfferRequest = new EIPOfferOperationRequest();

	Context context = new Context();

	Map<String, Object> userMap = getUserMapFromSession(request);
	context.setBusinessId((String) userMap.get(SessionConstant.SESSION_BUSINESS_ID));
	context.setCountryCode((String) userMap.get(SessionConstant.SESSION_COUNTRY_CODE));
	context.setLanguageId((String) userMap.get(SessionConstant.SESSION_LANGUAGE_ID));
	context.setSystemId((String) userMap.get(SessionConstant.SESSION_SYSTEM_ID));
	context.setCustomerId((String) userMap.get(SessionConstant.SESSION_CUSTOMER_ID));
	context.setUserId((String) userMap.get(SessionConstant.SESSION_USER_ID));

	eipOfferRequest.setContext(context);

	return eipOfferRequest;

    }

    public void setSearchEIPOfferOperation(SearchEIPOfferOperation searchEIPOfferOperation) {
	this.searchEIPOfferOperation = searchEIPOfferOperation;
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
