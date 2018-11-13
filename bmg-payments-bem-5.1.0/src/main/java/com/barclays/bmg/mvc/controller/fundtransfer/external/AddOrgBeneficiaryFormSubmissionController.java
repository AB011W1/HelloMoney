package com.barclays.bmg.mvc.controller.fundtransfer.external;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.billpayment.AddOrgBeneficiaryFormSubmissionCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.beneficiary.AddOrgBeneficiaryOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.AddOrgBenefeciaryOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.AddOrgBeneficiaryOperationResponse;

/**
 * @author E20041929 Controller used for display biller list coming from local
 *         database to clients
 *
 */
public class AddOrgBeneficiaryFormSubmissionController extends
		BMBAbstractCommandController {
	private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
	private String activityId;
	private AddOrgBeneficiaryOperation addOrgBeneficiaryOperation;

	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

 		setFirstStep(httpRequest);
		cleanProcess(httpRequest, BMGProcessConstants.ADD_ORG_BENEFICIARY);
		AddOrgBeneficiaryFormSubmissionCommand billRegCommand = new AddOrgBeneficiaryFormSubmissionCommand();
		AddOrgBenefeciaryOperationRequest billerRegistrationOperationRequest = new AddOrgBenefeciaryOperationRequest();
		Context context = addContext(billerRegistrationOperationRequest,
				httpRequest);
		billerRegistrationOperationRequest.setContext(context);
		buildBillerRegistrationOperationRequest(billRegCommand,
				billerRegistrationOperationRequest);
		AddOrgBeneficiaryOperationResponse billRegFormSubmissionOperationResponse = new AddOrgBeneficiaryOperationResponse();
		billRegFormSubmissionOperationResponse.setSuccess(true);
		///presently hardcoing category id BarclaycardBill
		billerRegistrationOperationRequest.setBillerType(httpRequest.getParameter("billerType"));
        List<BillerDTO> allBillerList = getAddOrgBeneficiaryOperation().getBillPaymentsBillerList(billerRegistrationOperationRequest);
        billRegFormSubmissionOperationResponse.setBillerList(allBillerList);
		return (BMBBaseResponseModel) bmbJSONBuilder
				.createMultipleJSONResponse(billRegFormSubmissionOperationResponse);
	}

	private Map<String, BillerDTO> convertToMap(List<BillerDTO> allBillerList) {
		Map<String, BillerDTO> billersDTOIdMap = new HashMap<String, BillerDTO>();
		if (allBillerList != null)
			for (BillerDTO billerDTO : allBillerList) {
				if (billerDTO != null)
					billersDTOIdMap.put(billerDTO.getBillerId(), billerDTO);
			}
		return billersDTOIdMap;
	}

	private Context addContext(RequestContext request,
			HttpServletRequest httpRequest) {
		Context context = createContext(httpRequest);
		context.setActivityId(activityId);
		request.setContext(context);
		return context;
	}

	private AddOrgBenefeciaryOperationRequest buildBillerRegistrationOperationRequest(
			AddOrgBeneficiaryFormSubmissionCommand billRegCommand,
			AddOrgBenefeciaryOperationRequest billerRegistrationOperationRequest) {
		billerRegistrationOperationRequest.setAcctNumber(billRegCommand
				.getBillerAccNum());
		billerRegistrationOperationRequest.setBillerId(billRegCommand
				.getBillerId());
		billerRegistrationOperationRequest.setBillerType(billRegCommand
				.getBillerType());

		return billerRegistrationOperationRequest;

	}

	public void setBmbJSONBuilder(BMBMultipleResponseJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	@Override
	protected String getActivityId(Object command) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setAddOrgBeneficiaryOperation(
			AddOrgBeneficiaryOperation addOrgBeneficiaryOperation) {
		this.addOrgBeneficiaryOperation = addOrgBeneficiaryOperation;
	}

	public AddOrgBeneficiaryOperation getAddOrgBeneficiaryOperation() {
		return addOrgBeneficiaryOperation;
	}

}
