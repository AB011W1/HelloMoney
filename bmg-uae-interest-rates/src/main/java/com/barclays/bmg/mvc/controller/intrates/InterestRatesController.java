package com.barclays.bmg.mvc.controller.intrates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractController;
import com.barclays.bmg.operation.intrates.InterestRateOperation;
import com.barclays.bmg.operation.request.intrates.InterestRatesOperationRequest;
import com.barclays.bmg.operation.response.intrates.InterestRatesOperationResponse;

public class InterestRatesController extends BMBAbstractController {

    private InterestRateOperation intRatesOperation;
    private BMBJSONBuilder bmbJSONBuilder;

    public void setIntRatesOperation(InterestRateOperation intRatesOperation) {
	this.intRatesOperation = intRatesOperation;
    }

    protected BMBBaseResponseModel handleRequestInternal1(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {
	Context context = createContext(httpRequest);
	// ---- Making Operation Request object
	InterestRatesOperationRequest intratesOperationRequest = makeRequest(context, httpRequest);

	intratesOperationRequest.setCategoryCode(ActivityConstant.TD_CATEGORY_CODE);

	// ---- Making Operation Response Object, calling method to retrieve
	// Interest Rates

	InterestRatesOperationResponse intRatesOperationResponse = intRatesOperation.getIntratesList(intratesOperationRequest);

	return (BMBBaseResponseModel) bmbJSONBuilder.createJSONResponse(intRatesOperationResponse);

    }

    private InterestRatesOperationRequest makeRequest(Context context, HttpServletRequest request) {

	InterestRatesOperationRequest intratesOperationRequest = new InterestRatesOperationRequest();

	context.setActivityId(ActivityConstant.INTEREST_RATES_TD);

	intratesOperationRequest.setContext(context);

	return intratesOperationRequest;

    }

    @Override
    protected String getActivityId() {

	return ActivityConstant.INTEREST_RATES_TD;
    }

    public BMBJSONBuilder getBmbJSONBuilder() {
	return bmbJSONBuilder;
    }

    public void setBmbJSONBuilder(BMBJSONBuilder bmbJSONBuilder) {
	this.bmbJSONBuilder = bmbJSONBuilder;
    }

    public InterestRateOperation getIntRatesOperation() {
	return intRatesOperation;
    }

}
