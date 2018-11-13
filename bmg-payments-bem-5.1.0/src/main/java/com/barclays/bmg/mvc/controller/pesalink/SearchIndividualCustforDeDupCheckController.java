
package com.barclays.bmg.mvc.controller.pesalink;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.pesalink.SearchIndividualCustforDeDupCheckOperation;
import com.barclays.bmg.operation.request.pesalink.SearchIndividualCustforDeDupCheckOperationRequest;
import com.barclays.bmg.operation.response.pesalink.SearchIndividualCustforDeDupCheckOperationResponse;
import com.barclays.bmg.utils.BMBCommonUtility;

public class SearchIndividualCustforDeDupCheckController extends BMBAbstractCommandController{

	private static final Logger LOGGER = Logger.getLogger(SearchIndividualCustforDeDupCheckController.class);
	 private String activityId;

	private BMBJSONBuilder bmbJSONBuilder;
	private SearchIndividualCustforDeDupCheckOperation searchIndividualCustforDeDupCheckOperation;


    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
	    throws Exception {

    	logger.info("Entry SearchIndividualCustforDeDupCheckController ");
    	SearchIndividualCustforDeDupCheckOperationRequest searchIndividualCustforDeDupCheckOperationRequest = makeRequest(request);

    	SearchIndividualCustforDeDupCheckOperationResponse searchIndividualCustforDeDupCheckOperationResponse=searchIndividualCustforDeDupCheckOperation.retrieveCustomerInfo(searchIndividualCustforDeDupCheckOperationRequest);


    	return (BMBBaseResponseModel) bmbJSONBuilder.createJSONResponse(searchIndividualCustforDeDupCheckOperationResponse);
    }

    /**
     * This method makeRequest has the purpose to construct the operation request.
     *
     * @param HttpRequest
     * @return RetrieveindividualCustCardListOperationRequest
     */
    private SearchIndividualCustforDeDupCheckOperationRequest makeRequest(HttpServletRequest request) {

    	Context context = createContext(request);
    	context.getContextMap().put("inputMobileNumber", (String)request.getParameter("mobileNo"));
    	context.setActivityId(request.getParameter("activityId"));
    	context.setActivityRefNo(BMBCommonUtility.generateRefNo());
		context.setOpCde(request.getParameter("opCde"));
    	SearchIndividualCustforDeDupCheckOperationRequest searchIndividualCustforDeDupCheckOperationRequest=new SearchIndividualCustforDeDupCheckOperationRequest();

    	searchIndividualCustforDeDupCheckOperationRequest.setContext(context);
    	searchIndividualCustforDeDupCheckOperationRequest.setMobileNumber(request.getParameter("mobileNo"));
    	return searchIndividualCustforDeDupCheckOperationRequest;
    }




	public BMBJSONBuilder getBmbJSONBuilder() {
		return bmbJSONBuilder;
	}

	public void setBmbJSONBuilder(BMBJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}

	public SearchIndividualCustforDeDupCheckOperation getSearchIndividualCustforDeDupCheckOperation() {
		return searchIndividualCustforDeDupCheckOperation;
	}

	public void setSearchIndividualCustforDeDupCheckOperation(
			SearchIndividualCustforDeDupCheckOperation searchIndividualCustforDeDupCheckOperation) {
		this.searchIndividualCustforDeDupCheckOperation = searchIndividualCustforDeDupCheckOperation;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	 @Override
	    protected String getActivityId(Object command) {
		// TODO Auto-generated method stub
		return null;
	    }


}
