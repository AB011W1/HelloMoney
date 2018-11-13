package com.barclays.bmg.mvc.controller.offer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractController;
import com.barclays.bmg.operation.offer.DiningSearchDataOperation;
import com.barclays.bmg.operation.offer.request.DiningSearchDataOperationRequest;
import com.barclays.bmg.operation.offer.response.DiningSearchDataOperationResponse;


/**
 * Retrieve dining search data
 * @author e20026338
 *
 */
public class DiningSearchDataController extends BMBAbstractController{

	DiningSearchDataOperation diningSearchDataOperation;
	BMBMultipleResponseJSONBuilder bmbJsonBuilder;


	@Override
	protected String getActivityId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected BMBBaseResponseModel handleRequestInternal1(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DiningSearchDataOperationRequest diningSearchDataOperationRequest = makeRequest(request);
		DiningSearchDataOperationResponse diningSearchDataOperationResponse =
									diningSearchDataOperation.retrieveSearchData(diningSearchDataOperationRequest);


		return (BMBBaseResponseModel)bmbJsonBuilder.createMultipleJSONResponse(diningSearchDataOperationResponse);
	}

	private DiningSearchDataOperationRequest makeRequest(
			HttpServletRequest request) {
		Context context = createContext(request);

		DiningSearchDataOperationRequest diningSearchDataOperationRequest =
						new DiningSearchDataOperationRequest();

		diningSearchDataOperationRequest.setContext(context);

		return diningSearchDataOperationRequest;
	}

	public DiningSearchDataOperation getDiningSearchDataOperation() {
		return diningSearchDataOperation;
	}

	public void setDiningSearchDataOperation(
			DiningSearchDataOperation diningSearchDataOperation) {
		this.diningSearchDataOperation = diningSearchDataOperation;
	}

	public BMBMultipleResponseJSONBuilder getBmbJsonBuilder() {
		return bmbJsonBuilder;
	}

	public void setBmbJsonBuilder(BMBMultipleResponseJSONBuilder bmbJsonBuilder) {
		this.bmbJsonBuilder = bmbJsonBuilder;
	}


}
