package com.barclays.bmg.mvc.controller.fundtransfer.external;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.AddOrgBeneficiaryConstants;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.BeneficiaryConstants;
import com.barclays.bmg.constants.BeneficiaryResponseCodeConstants;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CategorizedPayeeListDTO;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.billpayment.UpdateBeneficiaryFormSubmissionCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.beneficiary.RetrievePayeeListOperation;
import com.barclays.bmg.operation.beneficiary.UpdateBeneficiaryOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrievePayeeListOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.UpdateBeneficiaryOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeListOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.UpdateBeneficiaryFormSubmissionOperationResponse;

/**
 * @author BTCI
 *
 */
public class UpdateBeneficiaryFormSubmissionController extends BMBAbstractCommandController {
    private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
    private UpdateBeneficiaryOperation updateBeneficiaryOperation;
    private RetrievePayeeListOperation retrievePayeeListOperation;

    public void setBmbJSONBuilder(BMBMultipleResponseJSONBuilder bmbJSONBuilder) {
	this.bmbJSONBuilder = bmbJSONBuilder;
    }

    @Override
    protected String getActivityId(Object command) {
	return null;
    }

    @Override
    protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest, HttpServletResponse response, Object command, BindException errors)
	    throws Exception {
	setFirstStep(httpRequest);
	cleanProcess(httpRequest, BMGProcessConstants.UPDATE_BENEFICIARY);

	UpdateBeneficiaryFormSubmissionCommand updateBeneficiaryFormSubmissionCommand = (UpdateBeneficiaryFormSubmissionCommand) command;
	UpdateBeneficiaryOperationRequest updateBeneficiaryOperationRequest = new UpdateBeneficiaryOperationRequest();
	Context context = addContext(updateBeneficiaryOperationRequest, httpRequest);
	if (updateBeneficiaryFormSubmissionCommand != null) {

	    if (FundTransferConstants.PAYEE_TYPE_FUND_TRANSFER_INTERNAL.equals(updateBeneficiaryFormSubmissionCommand.getPayeeType())) {
		context.setActivityId(ActivityIdConstantBean.UPD_INTERNAL_PAYEE_ACTIVITY_ID);
	    } else {
		context.setActivityId(ActivityIdConstantBean.UPD_EXTERNAL_PAYEE_ACTIVITY_ID);
	    }

	    updateBeneficiaryOperationRequest.setContext(context);

	    buildAddBeneficiaryOperationRequest(updateBeneficiaryFormSubmissionCommand, updateBeneficiaryOperationRequest);

	}

	UpdateBeneficiaryFormSubmissionOperationResponse updateBeneficiaryFormSubmissionOperationResponse = validateUpdateBeneficiaryInternal(
		context, updateBeneficiaryFormSubmissionCommand);

	if (updateBeneficiaryFormSubmissionOperationResponse.isSuccess()) {
	    updateBeneficiaryFormSubmissionOperationResponse = updateBeneficiaryOperation.validateForm(updateBeneficiaryOperationRequest);
	}

	if (updateBeneficiaryFormSubmissionOperationResponse.isSuccess()) {
	    BeneficiaryDTO beneficiaryDTO = setBeneficiaryDTOForProcess(updateBeneficiaryOperationRequest);
	    updateBeneficiaryFormSubmissionOperationResponse.setBeneficiaryDTO(beneficiaryDTO);
	    setIntoProcessMap(httpRequest, BMGProcessConstants.UPDATE_BENEFICIARY, BeneficiaryConstants.BENEFICIARY_DTO, beneficiaryDTO);
	    setIntoProcessMap(httpRequest, BMGProcessConstants.UPDATE_BENEFICIARY, BeneficiaryConstants.TXN_REF_NO, context.getOrgRefNo());
	    setIntoProcessMap(httpRequest, BMGProcessConstants.TXN_SECOND_LEVEL_AUTHENTICATION, BillPaymentConstants.SECOND_AUTH_FLOW_ID,
		    BeneficiaryConstants.AB_FLOW_ID);
	}
	return (BMBBaseResponseModel) bmbJSONBuilder.createMultipleJSONResponse(updateBeneficiaryFormSubmissionOperationResponse);
    }

    private UpdateBeneficiaryFormSubmissionOperationResponse validateUpdateBeneficiaryInternal(Context context,
	    UpdateBeneficiaryFormSubmissionCommand updateBeneficiaryFormSubmissionCommand) {
	UpdateBeneficiaryFormSubmissionOperationResponse response = new UpdateBeneficiaryFormSubmissionOperationResponse();
	response.setContext(context);
	String activityId = context.getActivityId();
	String accountNumber = updateBeneficiaryFormSubmissionCommand.getAccountNumber();
	String beneficiaryId = updateBeneficiaryFormSubmissionCommand.getBeneficiaryId();
	String beneficiaryNickName = updateBeneficiaryFormSubmissionCommand.getBeneficiaryNickName();

	List<BeneficiaryDTO> payeeList = retrievePayeeListInternal(context);
	for (BeneficiaryDTO beneficiary : payeeList) {
	    if (!StringUtils.equalsIgnoreCase(beneficiaryId, beneficiary.getPayeeId())) {
		if (StringUtils.equalsIgnoreCase(activityId, ActivityIdConstantBean.UPD_INTERNAL_PAYEE_ACTIVITY_ID)) {
		    if (StringUtils.equalsIgnoreCase(accountNumber, beneficiary.getDestinationAccountNumber())
			    || StringUtils.equalsIgnoreCase(beneficiaryNickName, beneficiary.getPayeeNickname())) {
			response.setSuccess(false);
			response.setResCde(BeneficiaryResponseCodeConstants.PAYEE_ALREADY_REGISTERED);
			return response;
		    }
		} else if (StringUtils.equalsIgnoreCase(activityId, ActivityIdConstantBean.UPD_EXTERNAL_PAYEE_ACTIVITY_ID)) {
		    String branchCode = updateBeneficiaryFormSubmissionCommand.getBranchCode();
		    String bankCode = updateBeneficiaryFormSubmissionCommand.getBankCode();

		    if ((StringUtils.equalsIgnoreCase(accountNumber, beneficiary.getDestinationAccountNumber())
			    && StringUtils.equalsIgnoreCase(branchCode, beneficiary.getDestinationBranchCode()) && StringUtils.equalsIgnoreCase(
			    bankCode, beneficiary.getDestinationBankCode()))
			    || StringUtils.equalsIgnoreCase(beneficiaryNickName, beneficiary.getPayeeNickname())) {
			response.setSuccess(false);
			response.setResCde(BeneficiaryResponseCodeConstants.PAYEE_ALREADY_REGISTERED);
			return response;
		    }
		}

	    }

	}

	return response;
    }

    private List<BeneficiaryDTO> retrievePayeeListInternal(Context context) {
	List<BeneficiaryDTO> payeeList = null;
	RetrievePayeeListOperationResponse retrievePayeeListOperationResponse;
	RetrievePayeeListOperationRequest retrievePayeeListOperationRequest = null;
	retrievePayeeListOperationRequest = new RetrievePayeeListOperationRequest();
	retrievePayeeListOperationRequest.setContext(context);
	String activityId = context.getActivityId();

	if (StringUtils.equalsIgnoreCase(activityId, ActivityIdConstantBean.UPD_INTERNAL_PAYEE_ACTIVITY_ID)) {
	    retrievePayeeListOperationRequest.setPayGrp(FundTransferConstants.PAYEE_TYPE_INTERNAL);
	} else if (StringUtils.equalsIgnoreCase(activityId, ActivityIdConstantBean.UPD_EXTERNAL_PAYEE_ACTIVITY_ID)) {
	    retrievePayeeListOperationRequest.setPayGrp(FundTransferConstants.PAYEE_TYPE_EXTERNAL);
	}

	retrievePayeeListOperationResponse = retrievePayeeListOperation.retrievePayeeList(retrievePayeeListOperationRequest);
	List<CategorizedPayeeListDTO> categorizedPayeeList = retrievePayeeListOperationResponse.getCategorizedPayeeList();
	if (StringUtils.equalsIgnoreCase(activityId, ActivityIdConstantBean.UPD_INTERNAL_PAYEE_ACTIVITY_ID)) {
	    for (CategorizedPayeeListDTO categorizedPayee : categorizedPayeeList) {
		if (StringUtils.equalsIgnoreCase(categorizedPayee.getPayeeCategory(), AddOrgBeneficiaryConstants.PAYEE_DOMESTIC_BARCLAYS)) {
		    return categorizedPayee.getPayeeList();
		}
	    }
	} else if (StringUtils.equalsIgnoreCase(activityId, ActivityIdConstantBean.UPD_EXTERNAL_PAYEE_ACTIVITY_ID)) {
	    for (CategorizedPayeeListDTO categorizedPayee : categorizedPayeeList) {
		if (StringUtils.equalsIgnoreCase(categorizedPayee.getPayeeCategory(), AddOrgBeneficiaryConstants.PAYEE_DOMESTIC_NON_BARCLAYS)) {
		    return categorizedPayee.getPayeeList();
		}
	    }
	}
	return payeeList;
    }

    /**
     * @param AddBeneficiaryOperationRequest
     * @return BeneficiaryDTO
     */
    private BeneficiaryDTO setBeneficiaryDTOForProcess(UpdateBeneficiaryOperationRequest request) {

	BeneficiaryDTO beneficiaryDTO = new BeneficiaryDTO();
	beneficiaryDTO.setBeneficiaryName(request.getBeneficiaryName());
	beneficiaryDTO.setDestinationAccountNumber(request.getAccountNumber());
	beneficiaryDTO.setPayeeTypeCode(request.getPayeeType());
	beneficiaryDTO.setPayeeNickname(request.getBeneficiaryNickName());
	beneficiaryDTO.setBeneficiaryId(request.getBeneficiaryId());

	beneficiaryDTO.setDestinationBankName(request.getBankName());
	beneficiaryDTO.setDestinationBranchCode(request.getBranchCode());
	beneficiaryDTO.setDestinationBankCode(request.getBankCode());
	beneficiaryDTO.setDestinationBranchName(request.getBranchName());
	beneficiaryDTO.setBeneficiaryNickName(request.getBeneficiaryNickName());
	String address=request.getAddress();
	if(address !=null  && address.length()>50){
		String address1=null,address2=null;
	    address1=address.substring(0, 50);
	    address2=address.substring(50);
	    beneficiaryDTO.setDestinationAddressLine1(address1);
	    beneficiaryDTO.setDestinationAddressLine2(address2);
	}else
		beneficiaryDTO.setDestinationAddressLine1(address);

	beneficiaryDTO.setBeneficiaryOldNickName(request.getBeneficiaryOldNickName());

	return beneficiaryDTO;

    }

    /**
     * @param request
     * @param httpRequest
     * @return Context
     *
     *         Set Activity Id into context
     */
    private Context addContext(RequestContext request, HttpServletRequest httpRequest) {
	Context context = createContext(httpRequest);
	request.setContext(context);
	return context;
    }

    /**
     * @param addBeneficiaryFormSubmissionCommand
     * @param AddBeneficiaryOperationRequest
     * @return AddBeneficiaryOperationRequest
     */
    private UpdateBeneficiaryOperationRequest buildAddBeneficiaryOperationRequest(
	    UpdateBeneficiaryFormSubmissionCommand updateBeneficiaryFormSubmissionCommand, UpdateBeneficiaryOperationRequest request) {
	request.setPayeeType(updateBeneficiaryFormSubmissionCommand.getPayeeType());
	request.setBankCode(updateBeneficiaryFormSubmissionCommand.getBankCode());
	request.setBranchCode(updateBeneficiaryFormSubmissionCommand.getBranchCode());
	request.setAccountNumber(updateBeneficiaryFormSubmissionCommand.getAccountNumber());
	request.setBeneficiaryName(updateBeneficiaryFormSubmissionCommand.getBeneficiaryName());
	request.setBeneficiaryNickName(updateBeneficiaryFormSubmissionCommand.getBeneficiaryNickName());
	request.setBeneficiaryId(updateBeneficiaryFormSubmissionCommand.getBeneficiaryId());
	request.setBeneficiaryOldNickName(updateBeneficiaryFormSubmissionCommand.getBeneficiaryOldNickName());
	request.setAddress(updateBeneficiaryFormSubmissionCommand.getAddress());
	return request;

    }

    public UpdateBeneficiaryOperation getUpdateBeneficiaryOperation() {
	return updateBeneficiaryOperation;
    }

    public void setUpdateBeneficiaryOperation(UpdateBeneficiaryOperation updateBeneficiaryOperation) {
	this.updateBeneficiaryOperation = updateBeneficiaryOperation;
    }

    public void setRetrievePayeeListOperation(RetrievePayeeListOperation retrievePayeeListOperation) {
	this.retrievePayeeListOperation = retrievePayeeListOperation;
    }

}
