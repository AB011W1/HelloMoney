package com.barclays.bmg.mvc.controller.offer;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.offer.SearchDiningOfferCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.offer.SearchDiningOffersOperation;
import com.barclays.bmg.operation.offer.request.DiningOfferOperationRequest;
import com.barclays.bmg.operation.offer.response.DiningOfferOperationResponse;

public class SearchDiningOffersController extends BMBAbstractCommandController {

    private SearchDiningOffersOperation searchDiningOffersOperation;
    private BMBJSONBuilder bmbJsonBuilder;

    @Override
    protected String getActivityId(Object command) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
	    throws Exception {
	// TODO Auto-generated method stub
	SearchDiningOfferCommand diningOfferCmd = (SearchDiningOfferCommand) command;

	DiningOfferOperationRequest diningOfferOperReq = makeRequest(request);
	diningOfferOperReq.setResturantName(diningOfferCmd.getRestaurant());
	diningOfferOperReq.setCity(diningOfferCmd.getCity());

	DiningOfferOperationResponse diningOfferOperResp = searchDiningOffersOperation.searchDiningOffers(diningOfferOperReq);
	return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(diningOfferOperResp);

    }

    private DiningOfferOperationRequest makeRequest(HttpServletRequest request) {

	DiningOfferOperationRequest diningOfferOperReq = new DiningOfferOperationRequest();

	Context context = new Context();

	Map<String, Object> userMap = getUserMapFromSession(request);
	context.setBusinessId((String) userMap.get(SessionConstant.SESSION_BUSINESS_ID));
	context.setCountryCode((String) userMap.get(SessionConstant.SESSION_COUNTRY_CODE));
	context.setLanguageId((String) userMap.get(SessionConstant.SESSION_LANGUAGE_ID));
	context.setSystemId((String) userMap.get(SessionConstant.SESSION_SYSTEM_ID));
	context.setCustomerId((String) userMap.get(SessionConstant.SESSION_CUSTOMER_ID));
	context.setUserId((String) userMap.get(SessionConstant.SESSION_USER_ID));

	diningOfferOperReq.setContext(context);

	return diningOfferOperReq;
    }

    public void setSearchDiningOffersOperation(SearchDiningOffersOperation searchDiningOffersOperation) {
	this.searchDiningOffersOperation = searchDiningOffersOperation;
    }

    public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
	this.bmbJsonBuilder = bmbJsonBuilder;
    }

}
