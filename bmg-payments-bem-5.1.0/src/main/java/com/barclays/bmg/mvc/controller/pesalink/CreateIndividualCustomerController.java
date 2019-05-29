
package com.barclays.bmg.mvc.controller.pesalink;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.pesalink.CreateIndividualCustomerOperation;
import com.barclays.bmg.operation.request.pesalink.CreateIndividualCustomerOperationRequest;
import com.barclays.bmg.operation.response.pesalink.CreateIndividualCustomerOperationResponse;
import com.barclays.bmg.utils.BMBCommonUtility;

public class CreateIndividualCustomerController extends BMBAbstractCommandController{

	private static final Logger LOGGER = Logger.getLogger(CreateIndividualCustomerController.class);
	 private String activityId;

	private BMBJSONBuilder bmbJSONBuilder;
	private CreateIndividualCustomerOperation createIndividualCustomerOperation;


    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
	    throws Exception {

    	logger.info("Entry CreateIndividualCustomerController ");
    	CreateIndividualCustomerOperationRequest createIndividualCustomerOperationRequest = makeRequest(request);

    	CreateIndividualCustomerOperationResponse createIndividualCustomerOperationResponse=createIndividualCustomerOperation.createIndividualCustomer(createIndividualCustomerOperationRequest);

    	return (BMBBaseResponseModel) bmbJSONBuilder.createJSONResponse(createIndividualCustomerOperationResponse);
    }

    /**
     * This method makeRequest has the purpose to construct the operation request.
     *
     * @param HttpRequest
     * @return RetrieveindividualCustCardListOperationRequest
     */
    private CreateIndividualCustomerOperationRequest makeRequest(HttpServletRequest request) {

    	//Getting account number from correlation Id
    	String actNoCorr = request.getParameter("accountNo");
    	String accountNumber=getAccountNumber(actNoCorr, request, BMGProcessConstants.ACCOUNTS_PROCESS);
    	if(accountNumber == null)
    		accountNumber = actNoCorr;
    	Context context = createContext(request);
    	context.setActivityId(request.getParameter("activityId"));
    	context.setActivityRefNo(BMBCommonUtility.generateRefNo());
		context.setOpCde(request.getParameter("opCde"));
    	context.getContextMap().put("mobileNumber", (String)request.getParameter("mobileNo"));
    	context.getContextMap().put("accountNumber", accountNumber);
    	context.getContextMap().put("primaryFlag", (String)request.getParameter("primaryFlag"));
    	CreateIndividualCustomerOperationRequest createIndividualCustomerOperationRequest=new CreateIndividualCustomerOperationRequest();

    	createIndividualCustomerOperationRequest.setContext(context);
    	createIndividualCustomerOperationRequest.setAccountNumber(accountNumber);
    	createIndividualCustomerOperationRequest.setMobileNumber(request.getParameter("mobileNo"));
    	createIndividualCustomerOperationRequest.setPrimaryFlag(Boolean.valueOf(request.getParameter("primaryFlag")));

    	return createIndividualCustomerOperationRequest;
    }



	public BMBJSONBuilder getBmbJSONBuilder() {
		return bmbJSONBuilder;
	}

	public void setBmbJSONBuilder(BMBJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}

	public CreateIndividualCustomerOperation getCreateIndividualCustomerOperation() {
		return createIndividualCustomerOperation;
	}

	public void setCreateIndividualCustomerOperation(
			CreateIndividualCustomerOperation createIndividualCustomerOperation) {
		this.createIndividualCustomerOperation = createIndividualCustomerOperation;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	 @Override
	    protected String getActivityId(Object command) {

		return null;
	    }


}
