package com.barclays.bmg.mvc.controller.billpayment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.billpayment.AddOrgBeneficiaryExecutionControllerCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.beneficiary.AddOrgBeneficiaryOperation;
import com.barclays.bmg.operation.beneficiary.DeleteBeneficiaryOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.AddOrgBenefeciaryOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.DeleteBeneficiaryOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.AddOrgBeneficiaryOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.DeleteBeneficiaryOperationResponse;

/**
 * @author E20041929
 *
 */
public class AddOrgBenefeciaryExecutionController1 extends BMBAbstractCommandController {

    private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
    private BMBJSONBuilder txnOTPSecondAuthJSONBldr;
    private AddOrgBeneficiaryOperation addOrgBeneficiaryOperation;
    //CR82 eidt bene
    private DeleteBeneficiaryOperation deleteBeneficiaryOperation;
    private String activityId;

    @Override
    protected String getActivityId(Object command) {
	return activityId;
    }

    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object command, BindException errors)
	    throws Exception {
	setLastStep(httpRequest);
	AddOrgBeneficiaryExecutionControllerCommand executionControllerCommand = (AddOrgBeneficiaryExecutionControllerCommand) command;

	String orgRefNo = executionControllerCommand.getOrgRefNo();
	if (orgRefNo == null) {
	    orgRefNo = "orgRefNo";
	}
	BillerAndOrgBeneficiaryDTO userInputDetailsFromSession = (BillerAndOrgBeneficiaryDTO) getFromProcessMap(httpRequest,
		BMGProcessConstants.ADD_ORG_BENEFICIARY, orgRefNo);

	AddOrgBenefeciaryOperationRequest billerRegistrationOpRequest = new AddOrgBenefeciaryOperationRequest();

	addContext(billerRegistrationOpRequest, httpRequest);
	//CR82 Added for Edit
	boolean isEditFlow=false;
	String payeeId="";
	if(httpRequest.getParameter("isEditFlow")!=null){
		isEditFlow=Boolean.parseBoolean(httpRequest.getParameter("isEditFlow"));
		payeeId=httpRequest.getParameter("payeeId");
	}
	//CR82 Added for Edit
	if(isEditFlow){
		DeleteBeneficiaryOperationRequest deleteBeneficiaryOperationRequest = new DeleteBeneficiaryOperationRequest();
		//DeleteBeneficiaryOperationResponse responseForActivity = new DeleteBeneficiaryOperationResponse();
		addContext(deleteBeneficiaryOperationRequest, httpRequest);
		deleteBeneficiaryOperationRequest.setPayeeId(payeeId);
		Context context =deleteBeneficiaryOperationRequest.getContext();

		/*loadParameters(context, ActivityConstant.COMMON_ID,
				ActivityConstant.SEC_COMMON_ID);*/
		//responseForActivity.setContext(context);
		//Added For Audit Fix

			context
			.setActivityId(ActivityIdConstantBean.DELETE_PAYEE_ACTIVITY_ID);

		DeleteBeneficiaryOperationResponse deleteBeneficiaryOperationResponse = deleteBeneficiaryOperation
		.deleteBeneficiary(deleteBeneficiaryOperationRequest);
		if(deleteBeneficiaryOperationResponse!=null && deleteBeneficiaryOperationResponse.isSuccess()){
		prepareAndSetBeneficiaryDTO(userInputDetailsFromSession, billerRegistrationOpRequest);
		try {
			AddOrgBeneficiaryOperationResponse addOrgBeneficiaryOperationResponse = addOrgBeneficiaryOperation.registerBillers(billerRegistrationOpRequest);
		    addOrgBeneficiaryOperationResponse.setBeneficiaryDTO(billerRegistrationOpRequest.getBeneficiaryDTO());

		    /* Code starts for SMS */
		    if (addOrgBeneficiaryOperationResponse != null)
			if (addOrgBeneficiaryOperationResponse.isSuccess()) {
			    addOrgBeneficiaryOperation.sendSMSSuccessAcknowledgment(billerRegistrationOpRequest, addOrgBeneficiaryOperationResponse);
			}
		    /*else {
			    addOrgBeneficiaryOperation.sendSMSFailAcknowledgment(billerRegistrationOpRequest, addOrgBeneficiaryOperationResponse);
			}*/
		    /* Code ends for SMS */

		    return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(addOrgBeneficiaryOperationResponse);
		} catch (Exception ex) {
		    //addOrgBeneficiaryOperation.sendSMSFailAcknowledgment(billerRegistrationOpRequest, addOrgBeneficiaryOperationResponse);
		    throw ex;
		}
		}
	}

	prepareAndSetBeneficiaryDTO(userInputDetailsFromSession, billerRegistrationOpRequest);
	AddOrgBeneficiaryOperationResponse addOrgBeneficiaryOperationResponse = new AddOrgBeneficiaryOperationResponse();
	try {
		// WUC change - Botswana 21/06/2017
		Context wucContext = createContext(httpRequest);
	    if(wucContext.getBusinessId()!=null && wucContext.getBusinessId().equals("BWBRB") && httpRequest.getParameter("wucBillerRefCategory")!=null &&
	    		httpRequest.getParameter("wucBillerRefCategory").equals("WUC-2")){
	    	billerRegistrationOpRequest.getBeneficiaryDTO().setBusinessId(wucContext.getBusinessId());
	    	billerRegistrationOpRequest.getBeneficiaryDTO().setBillerId(httpRequest.getParameter("wucBillerRefCategory"));
	    	billerRegistrationOpRequest.getBeneficiaryDTO().setBillRefNo1(httpRequest.getParameter("customerBillerRef1"));
	    	billerRegistrationOpRequest.getBeneficiaryDTO().setBillRefNo2(httpRequest.getParameter("contractBillerRef2"));
	    }

	    addOrgBeneficiaryOperationResponse = addOrgBeneficiaryOperation.registerBillers(billerRegistrationOpRequest);
	    addOrgBeneficiaryOperationResponse.setBeneficiaryDTO(billerRegistrationOpRequest.getBeneficiaryDTO());

	    /* Code starts for SMS */
	    if (addOrgBeneficiaryOperationResponse != null)
		if (addOrgBeneficiaryOperationResponse.isSuccess()) {
		    addOrgBeneficiaryOperation.sendSMSSuccessAcknowledgment(billerRegistrationOpRequest, addOrgBeneficiaryOperationResponse);
		} else {
		    addOrgBeneficiaryOperation.sendSMSFailAcknowledgment(billerRegistrationOpRequest, addOrgBeneficiaryOperationResponse);
		}
	    /* Code ends for SMS */

	    return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(addOrgBeneficiaryOperationResponse);
	} catch (Exception ex) {
	    addOrgBeneficiaryOperation.sendSMSFailAcknowledgment(billerRegistrationOpRequest, addOrgBeneficiaryOperationResponse);
	    throw ex;
	}
    }

    private void prepareAndSetBeneficiaryDTO(BillerAndOrgBeneficiaryDTO billerAndOrgBeneficiaryDTO,
	    AddOrgBenefeciaryOperationRequest billerRegistrationOpRequest) {
	String billerRefAccNum = billerAndOrgBeneficiaryDTO.getBillerRefAccNum();
	BillerDTO selectedBillerDTO = billerAndOrgBeneficiaryDTO.getSelectedBillerDTO();

	BeneficiaryDTO beneficiaryDTO = new BeneficiaryDTO();
	beneficiaryDTO.setAddPayee(true);
	beneficiaryDTO.setBeneficiaryName(selectedBillerDTO.getBillerName());
	beneficiaryDTO.setBillerId(selectedBillerDTO.getBillerId());
	beneficiaryDTO.setBillerCategoryId(selectedBillerDTO.getBillerCategoryId());
	beneficiaryDTO.setPayeeNickname(billerAndOrgBeneficiaryDTO.getBillerNickName());
	beneficiaryDTO.setBillerName(selectedBillerDTO.getBillerName());

	beneficiaryDTO.setBillRefNo(billerRefAccNum);
	// beneficiaryDTO.setBusinessId(getContextFromSession(request));
	beneficiaryDTO.setPayeeId(selectedBillerDTO.getBillerId());
	billerRegistrationOpRequest.setAcctNumber(billerRefAccNum);
	billerRegistrationOpRequest.setBeneficiaryDTO(beneficiaryDTO);
	billerRegistrationOpRequest.setBillerNickName(billerAndOrgBeneficiaryDTO.getBillerNickName());
	billerRegistrationOpRequest.setAreaCode(billerAndOrgBeneficiaryDTO.getAreaCode());
    }

    public AddOrgBeneficiaryOperation getAddOrgBeneficiaryOperation() {
	return addOrgBeneficiaryOperation;
    }

    public void setAddOrgBeneficiaryOperation(AddOrgBeneficiaryOperation addOrgBeneficiaryOperation) {
	this.addOrgBeneficiaryOperation = addOrgBeneficiaryOperation;
    }

    /**
     * @param request
     * @param httpRequest
     *            add required parameters to context from session Map.
     */
    private void addContext(RequestContext request, HttpServletRequest httpRequest) {
	Context context = createContext(httpRequest);
	//CR82 Changes
	String payGrp=httpRequest.getParameter("payGrp");
	if(payGrp!=null){
		context.addEntry("payGrp", payGrp);

	}

	//CR82 Edit SMS
	String editFlow=httpRequest.getParameter("isEditFlow");
	if(editFlow!=null){
		context.addEntry("isEditFlow", editFlow);

	}

	context.setActivityId(activityId);
	request.setContext(context);
    }

    public BMBMultipleResponseJSONBuilder getBmbJSONBuilder() {
	return bmbJSONBuilder;
    }

    public void setBmbJSONBuilder(BMBMultipleResponseJSONBuilder bmbJSONBuilder) {
	this.bmbJSONBuilder = bmbJSONBuilder;
    }

    public String getActivityId() {
	return activityId;
    }

    public void setActivityId(String activityId) {
	this.activityId = activityId;
    }

    public void setTxnOTPSecondAuthJSONBldr(BMBJSONBuilder txnOTPSecondAuthJSONBldr) {
	this.txnOTPSecondAuthJSONBldr = txnOTPSecondAuthJSONBldr;
    }

    public BMBJSONBuilder getTxnOTPSecondAuthJSONBldr() {
	return txnOTPSecondAuthJSONBldr;
    }

	public DeleteBeneficiaryOperation getDeleteBeneficiaryOperation() {
		return deleteBeneficiaryOperation;
	}

	public void setDeleteBeneficiaryOperation(
			DeleteBeneficiaryOperation deleteBeneficiaryOperation) {
		this.deleteBeneficiaryOperation = deleteBeneficiaryOperation;
	}


}
