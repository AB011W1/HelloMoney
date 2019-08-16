/*
* Copyright (c) 2013 Barclays Bank Plc, All Rights Reserved.
*
* This code is confidential to Barclays Bank Plc and shall not be disclosed
* outside the Bank without the prior written permission of the Director of
* CIO
*
* In the event that such disclosure is permitted the code shall not be copied
* or distributed other than on a need-to-know basis and any recipients may be
* required to sign a confidentiality undertaking in favour of Barclays Bank
* Plc.
*/
package com.barclays.bmg.ussd.mvc.controller.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.RequestConstants;
import com.barclays.bmg.context.BMGContextHolder;
import com.barclays.bmg.context.BMGGlobalContext;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.ussd.auth.operation.request.SelfRegistrationExecutionOperationRequest;
import com.barclays.bmg.ussd.auth.operation.response.SelfRegistrationExecutionOperationResponse;
import com.barclays.bmg.ussd.mvc.command.auth.SelfRegistrationExecutionCommand;
import com.barclays.bmg.ussd.operation.SelfRegistrationExecutionOperation;

/**
* * ****************** Revision History ********************** </br> Project Name is <b>bmg-auth-4.0.10</b> </br> The file name is
* <b>SelfRegistrationExecutionController.java</b> </br> Description is <b>Initial Version</b> </br> Updated Date is <b>May 22, 2013</b> </br>
* ***********************************************************
*
* @author E20043104
*
*/
public class SelfRegistrationExecutionController extends BMBAbstractCommandController {

    /**
     * The instance variable for selfRegistrationExecutionOperation of type SelfRegistrationExecutionOperation
     */
    private SelfRegistrationExecutionOperation selfRegistrationExecutionOperation;

    /**
     * The instance variable for selfRegistrationExecutionJSONBldr of type BMBJSONBuilder
     */
    private BMBJSONBuilder selfRegistrationExecutionJSONBldr;

    /**
     * The instance variable for activityId of type String
     */
    private String activityId;

    /**
     * Entry point into controller. Handles HTTP request. Overrides handle1 method of BMBAbstractCommandController
     */
    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
                    throws Exception {

                setLastStep(request);

                SelfRegistrationExecutionCommand selfRegistrationExecutionCommand = (SelfRegistrationExecutionCommand) command;

                SelfRegistrationExecutionOperationRequest selfRegisExecutionOperationRequest = makeRequest(request);

                selfRegisExecutionOperationRequest.setPrefLang(selfRegistrationExecutionCommand.getPrefLang());
                // Start Bmg call added to minimize response time for last step
    String scvId = (String) getFromProcessMap(request, BMGProcessConstants.SELF_REGISTRATION_INIT, RequestConstants.SCV_ID);
    selfRegisExecutionOperationRequest.getContext().setCustomerId(scvId);
    String bankcif = (String) getFromProcessMap(request, BMGProcessConstants.SELF_REGISTRATION_INIT, RequestConstants.BANK_CIF);
    selfRegisExecutionOperationRequest.getContext().getContextMap().put(RequestConstants.BANK_CIF, bankcif);
    // End Bmg call added to minimize response time for last step

                SelfRegistrationExecutionOperationResponse selfRegistrationExecutionOperationResponse = selfRegistrationExecutionOperation
                .getDetailsAndRegister(selfRegisExecutionOperationRequest);

                if (selfRegistrationExecutionOperationResponse != null && selfRegistrationExecutionOperationResponse.isSuccess()) {
                   cleanProcess(request, BMGProcessConstants.SELF_REGISTRATION_INIT);
                   cleanProcess(request, BMGProcessConstants.SECOND_AUTHENTICATION);


                selfRegistrationExecutionOperation.sendSMSSuccessAcknowledgment(selfRegisExecutionOperationRequest,selfRegistrationExecutionOperationResponse);

  // Self Registration (Airtel Registration for Zambia)

                    BMGGlobalContext logContext = BMGContextHolder.getLogContext();
                    Object pdtOneWayEnbled=null;
                    if(null != logContext && null != logContext.getContextMap() && null != logContext.getContextMap().get("isRegisterCustomerToProductFlag"))
                        pdtOneWayEnbled = logContext.getContextMap().get("isRegisterCustomerToProductFlag");
                    String productOneWayEnabled= "N" ;
                    if(pdtOneWayEnbled!=null)
                                productOneWayEnabled = pdtOneWayEnbled.toString();

                    if(selfRegisExecutionOperationRequest.getMobileNo().startsWith("97") && productOneWayEnabled!=null && productOneWayEnabled.equals("Y"))
                    {
                    selfRegistrationExecutionOperation.acknowledgeRegisterCutomerToProduct(selfRegisExecutionOperationRequest);
                    }

}
                return (BMBBaseResponseModel) selfRegistrationExecutionJSONBldr.createJSONResponse(selfRegistrationExecutionOperationResponse);
    }

    /**
     * This method makeRequest has the purpose to construct the operation request.
     *
     * @param HttpServletRequest
     * @return SelfRegistrationExecutionOperationRequest
     */
    private SelfRegistrationExecutionOperationRequest makeRequest(HttpServletRequest request) {

                SelfRegistrationExecutionOperationRequest selfRegisExecutionOperationRequest = new SelfRegistrationExecutionOperationRequest();

                Context context = createContext(request);

                String mobileNo = (String) getFromProcessMap(request, BMGProcessConstants.SELF_REGISTRATION_INIT, RequestConstants.MOBILE_NO);
                String accountNo = (String) getFromProcessMap(request, BMGProcessConstants.SELF_REGISTRATION_INIT, RequestConstants.ACCOUNT_NO);
                String branchCode = (String) getFromProcessMap(request, BMGProcessConstants.SELF_REGISTRATION_INIT, RequestConstants.BRANCH_CODE);

                selfRegisExecutionOperationRequest.setMobileNo(mobileNo);
                selfRegisExecutionOperationRequest.setAccountNo(accountNo);
                selfRegisExecutionOperationRequest.setBranchCode(branchCode);
                context.setMobilePhone(mobileNo);

                context.setActivityId(getActivityId());

                selfRegisExecutionOperationRequest.setContext(context);

                return selfRegisExecutionOperationRequest;

    }

    /*
     * (non-Javadoc)
     *
     * @seecom.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController# getActivityId(java.lang.Object)
    */
    @Override
    protected String getActivityId(Object command) {
                // TODO Auto-generated method stub
                return null;
    }

    /**
     * Getter for SelfRegistrationExecutionOperation
     *
     *@param none
     *@return SelfRegistrationExecutionOperation
     */
    public SelfRegistrationExecutionOperation getSelfRegistrationExecutionOperation() {
                return selfRegistrationExecutionOperation;
    }

    /**
     * Setter for SelfRegistrationExecutionOperation
     *
     * @param SelfRegistrationExecutionOperation
     * @return void
     */
    public void setSelfRegistrationExecutionOperation(SelfRegistrationExecutionOperation selfRegistrationExecutionOperation) {
                this.selfRegistrationExecutionOperation = selfRegistrationExecutionOperation;
    }

    /**
     * Getter for BMBJSONBuilder
     *
     *@param none
     *@return BMBJSONBuilder
     */
    public BMBJSONBuilder getSelfRegistrationExecutionJSONBldr() {
                return selfRegistrationExecutionJSONBldr;
    }

    /**
     * Setter for BMBJSONBuilder
     *
     * @param BMBJSONBuilder
     * @return void
     */
    public void setSelfRegistrationExecutionJSONBldr(BMBJSONBuilder selfRegistrationExecutionJSONBldr) {
                this.selfRegistrationExecutionJSONBldr = selfRegistrationExecutionJSONBldr;
    }

    /**
     * Getter for ActivityId
     *
     *@param none
     *@return ActivityId
     */
    public String getActivityId() {
                return activityId;
    }

    /**
     * Setter for ActivityId
     *
     * @param ActivityId
     * @return void
     */
    public void setActivityId(String activityId) {
                this.activityId = activityId;
    }
}
