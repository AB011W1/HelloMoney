package com.barclays.bmg.mvc.controller.fundtransfer.external;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.BMGProcessConstants;
import com.barclays.bmg.constants.BeneficiaryConstants;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.billpayment.AddBeneficiaryFormSubmissionCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.beneficiary.AddBeneficiaryOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.AddBeneficiaryOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.AddBeneficiaryFormSubmissionOperationResponse;

/**
 * @author BTCI
 *
 */
public class AddBeneficiaryFormSubmissionController extends
		BMBAbstractCommandController {
	private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
	private AddBeneficiaryOperation addBeneficiaryOperation;

	public void setBmbJSONBuilder(BMBMultipleResponseJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}

	public void setAddBeneficiaryOperation(
			AddBeneficiaryOperation addBeneficiaryOperation) {
		this.addBeneficiaryOperation = addBeneficiaryOperation;
	}


	@Override
	protected String getActivityId(Object command) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController#handle1
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object,
	 * org.springframework.validation.BindException)
	 */
	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		setFirstStep(httpRequest);
		cleanProcess(httpRequest, BMGProcessConstants.ADD_BENEFICIARY);

		AddBeneficiaryFormSubmissionCommand addBeneficiaryFormSubmissionCommand = (AddBeneficiaryFormSubmissionCommand) command;
		AddBeneficiaryOperationRequest addBeneficiaryOperationRequest = new AddBeneficiaryOperationRequest();
		Context context = addContext(addBeneficiaryOperationRequest,
				httpRequest);
		if (addBeneficiaryFormSubmissionCommand != null){
		if (FundTransferConstants.PAYEE_TYPE_FUND_TRANSFER_INTERNAL
						.equals(addBeneficiaryFormSubmissionCommand
								.getPayeeType())) {
			context
					.setActivityId(ActivityIdConstantBean.ADD_INTERNAL_PAYEE_ACTIVITY_ID);
		} else {
			context
					.setActivityId(ActivityIdConstantBean.ADD_EXTERNAL_PAYEE_ACTIVITY_ID);
		}

		addBeneficiaryOperationRequest.setContext(context);

		buildAddBeneficiaryOperationRequest(
				addBeneficiaryFormSubmissionCommand,
				addBeneficiaryOperationRequest);

		}
		AddBeneficiaryFormSubmissionOperationResponse addBeneficiaryFormSubmissionOperationResponse = addBeneficiaryOperation
				.validateForm(addBeneficiaryOperationRequest);

		if (addBeneficiaryFormSubmissionOperationResponse.isSuccess()) {
			BeneficiaryDTO beneficiaryDTO = setBeneficiaryDTOForProcess(addBeneficiaryOperationRequest);
			addBeneficiaryFormSubmissionOperationResponse
					.setBeneficiaryDTO(beneficiaryDTO);
			setIntoProcessMap(httpRequest, BMGProcessConstants.ADD_BENEFICIARY,
					BeneficiaryConstants.BENEFICIARY_DTO, beneficiaryDTO);
			setIntoProcessMap(httpRequest, BMGProcessConstants.ADD_BENEFICIARY,
					BeneficiaryConstants.TXN_REF_NO, context.getOrgRefNo());
			setIntoProcessMap(httpRequest,
					BMGProcessConstants.TXN_SECOND_LEVEL_AUTHENTICATION,
					BillPaymentConstants.SECOND_AUTH_FLOW_ID,
					BeneficiaryConstants.AB_FLOW_ID);
		}
		return (BMBBaseResponseModel) bmbJSONBuilder
				.createMultipleJSONResponse(addBeneficiaryFormSubmissionOperationResponse);
	}

	/**
	 * @param AddBeneficiaryOperationRequest
	 * @return BeneficiaryDTO
	 */
	private BeneficiaryDTO setBeneficiaryDTOForProcess(
			AddBeneficiaryOperationRequest request) {

		BeneficiaryDTO beneficiaryDTO = new BeneficiaryDTO();
		beneficiaryDTO.setBeneficiaryName(request.getBeneficiaryName());
		beneficiaryDTO.setDestinationAccountNumber(request.getAccountNumber());
		beneficiaryDTO.setPayeeTypeCode(request.getPayeeType());
		beneficiaryDTO.setPayeeNickname(request.getBeneficiaryNickName());
		beneficiaryDTO.setDestinationBankName(request.getBankName());
		beneficiaryDTO.setDestinationBranchCode(request.getBranchCode());
		beneficiaryDTO.setDestinationBankCode(request.getBankCode());
		beneficiaryDTO.setDestinationBranchName(request.getBranchName());
		String address=request.getAddress();
		if(address!=null&&address.length()>50){
			String address1=null,address2=null;
		    address1=address.substring(0, 50);
		    address2=address.substring(50);
		    beneficiaryDTO.setDestinationAddressLine1(address1);
		    beneficiaryDTO.setDestinationAddressLine2(address2);
		}else
			beneficiaryDTO.setDestinationAddressLine1(address);

		beneficiaryDTO.setCity(request.getCity());
		String nib = request.getNib();
		beneficiaryDTO.setNib(nib);
		if(nib!=null){
		String bankCode = Integer.toString(Integer.parseInt(nib.substring(0, 4)));
		String branchCode = Integer.toString(Integer.parseInt(nib.substring(4, 8)));
		String accNo = nib;
		beneficiaryDTO.setDestinationAccountNumber(accNo);
		beneficiaryDTO.setDestinationBranchCode(branchCode);
		beneficiaryDTO.setDestinationBankCode(bankCode);
		}

		return beneficiaryDTO;

	}

	/**
	 * @param request
	 * @param httpRequest
	 * @return Context
	 *
	 * Set Activity Id into context
	 */
	private Context addContext(RequestContext request,
			HttpServletRequest httpRequest) {
		Context context = createContext(httpRequest);
		request.setContext(context);
		return context;
	}

	/**
	 * @param addBeneficiaryFormSubmissionCommand
	 * @param AddBeneficiaryOperationRequest
	 * @return AddBeneficiaryOperationRequest
	 */
	private AddBeneficiaryOperationRequest buildAddBeneficiaryOperationRequest(
			AddBeneficiaryFormSubmissionCommand addBeneficiaryFormSubmissionCommand,
			AddBeneficiaryOperationRequest request) {
		request
				.setPayeeType(addBeneficiaryFormSubmissionCommand
						.getPayeeType());
		request.setBankCode(addBeneficiaryFormSubmissionCommand.getBankCode());
		request.setBankName(addBeneficiaryFormSubmissionCommand.getBankName());
		request.setBranchCode(addBeneficiaryFormSubmissionCommand
				.getBranchCode());
		request.setAccountNumber(addBeneficiaryFormSubmissionCommand
				.getAccountNumber());
		request.setBeneficiaryName(addBeneficiaryFormSubmissionCommand
				.getBeneficiaryName());
		request.setBeneficiaryNickName(addBeneficiaryFormSubmissionCommand
				.getBeneficiaryNickName());
		request.setAddress(addBeneficiaryFormSubmissionCommand.getAddress());
		request.setCity(addBeneficiaryFormSubmissionCommand.getCity());
		request.setNib(addBeneficiaryFormSubmissionCommand.getNib());
		return request;

	}

}
