package com.barclays.bmg.mvc.controller.billpayment;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.AddOrgBeneficiaryConstants;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.billpayment.AddorgBenefValidateCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.formvalidation.AddOrgValidationOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.AddOrgBenefeciaryOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.AddOrgValidationOperationResponse;

/**
 * @author E20041929
 *
 */
public class AddOrgBeneficiaryFormValidationController extends BMBAbstractCommandController {

    /* private FormValidateOperation formValidateOperation; */
    private AddOrgValidationOperation addOrgValidationOperation;

    private BMBJSONBuilder txnOTPSecondAuthJSONBldr;
    private String activityId;

    private BMBMultipleResponseJSONBuilder bmbJSONBuilder;

    @Override
    protected String getActivityId(Object command) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object command, BindException errors)
	    throws Exception {
	AddorgBenefValidateCommand addOrgCommand = (AddorgBenefValidateCommand) command;
	Context context = createContext(httpRequest);
	context.setActivityId(getActivityId());
	// set account map for ecrime purpose
	setEcrimeAccountMapToSession(httpRequest, BMGProcessConstants.ADD_ORG_BENEFICIARY);
	String billerId = addOrgCommand.getBillerId();

	AddOrgBenefeciaryOperationRequest request1 = new AddOrgBenefeciaryOperationRequest();
	addContext(request1, httpRequest);
	request1.setBillerId(billerId);
	request1.setBillerNickName(addOrgCommand.getBillerNickName());
	request1.setBillerType(addOrgCommand.getBillerType());
	AddOrgValidationOperationResponse addorgValidationResponse = addOrgValidationOperation.validateInputBillerId(request1);

	Map<String, Object> contextMap = context.getContextMap();
	contextMap.put("paySer", addOrgCommand.getBillerReferenceNum());
	contextMap.put("txnNot", addOrgCommand.getBillerReferenceNum());

	BillerAndOrgBeneficiaryDTO billerAndOrgBeneficiaryDTO = new BillerAndOrgBeneficiaryDTO();
	billerAndOrgBeneficiaryDTO.setBillerId(billerId);
	billerAndOrgBeneficiaryDTO.setSelectedBillerDTO(addorgValidationResponse.getBillerDTO());
	billerAndOrgBeneficiaryDTO.setBillerRefAccNum(addOrgCommand.getBillerReferenceNum());
	billerAndOrgBeneficiaryDTO.setBillerNickName(addOrgCommand.getBillerNickName());
	billerAndOrgBeneficiaryDTO.setAreaCode(addOrgCommand.getAreaCode());
//	// WUC change - Botswana 21/06/2017
//	if(billerId.equals("WUC-2")){
//		billerAndOrgBeneficiaryDTO.setContractBillerReferenceNum(addOrgCommand.getContractBillerReferenceNum());
//	}

	String orgRefNo = context.getOrgRefNo();
	setIntoProcessMap(httpRequest, BMGProcessConstants.ADD_ORG_BENEFICIARY, orgRefNo, billerAndOrgBeneficiaryDTO);
	setIntoProcessMap(httpRequest, BMGProcessConstants.ADD_ORG_BENEFICIARY, AddOrgBeneficiaryConstants.TXN_REF_NO, orgRefNo);
	addorgValidationResponse.setOrgRefNo(orgRefNo);
	return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(addorgValidationResponse);
    }

    public BMBMultipleResponseJSONBuilder getBmbJSONBuilder() {
	return bmbJSONBuilder;
    }

    public void setBmbJSONBuilder(BMBMultipleResponseJSONBuilder bmbJSONBuilder) {
	this.bmbJSONBuilder = bmbJSONBuilder;
    }

    public void setAddOrgValidationOperation(AddOrgValidationOperation addOrgValidationOperation) {
	this.addOrgValidationOperation = addOrgValidationOperation;
    }

    public AddOrgValidationOperation getAddOrgValidationOperation() {
	return addOrgValidationOperation;
    }

    public void setActivityId(String activityId) {
	this.activityId = activityId;
    }

    public String getActivityId() {
	return activityId;
    }

    public void setTxnOTPSecondAuthJSONBldr(BMBJSONBuilder txnOTPSecondAuthJSONBldr) {
	this.txnOTPSecondAuthJSONBldr = txnOTPSecondAuthJSONBldr;
    }

    public BMBJSONBuilder getTxnOTPSecondAuthJSONBldr() {
	return txnOTPSecondAuthJSONBldr;
    }

    private Context addContext(RequestContext request, HttpServletRequest httpRequest) {
	Context context = createContext(httpRequest);
	context.setActivityId(activityId);
	request.setContext(context);
	return context;
    }

}
