package com.barclays.bmg.operation.beneficiary;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.DeleteBeneficiaryOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.DeleteBeneficiaryOperationResponse;
import com.barclays.bmg.service.DeleteBeneficiaryService;
import com.barclays.bmg.service.request.DeleteBeneficiaryServiceRequest;
import com.barclays.bmg.service.response.DeleteBeneficiaryServiceResponse;

/**
 * @author BTCI
 *
 */
public class DeleteBeneficiaryOperation extends BMBPaymentsOperation {

	private DeleteBeneficiaryService deleteBeneficiaryService;

	/**
	 * @param deleteBeneficiaryService
	 */
	public void setDeleteBeneficiaryService(
			DeleteBeneficiaryService deleteBeneficiaryService) {
		this.deleteBeneficiaryService = deleteBeneficiaryService;
	}

	/**
	 * @param request
	 * @return DeleteBeneficiaryOperationResponse
	 * Operation to delete internal, external and organization beneficiary
	 */
	@AuditSupport(auditType = AuditConstant.AUDIT_TYPE_ACTIVITY, activityState = AuditConstant.SRC_COM_EXECUTOR, serviceDescription = "SD_DELETE_PAYEE", stepId = "1", activityType = "auditDeleteBeneficiary")
	public DeleteBeneficiaryOperationResponse deleteBeneficiary(
			DeleteBeneficiaryOperationRequest request) {
		Context context = request.getContext();
		DeleteBeneficiaryOperationResponse response = new DeleteBeneficiaryOperationResponse();
		loadParameters(context, ActivityConstant.COMMON_ID,
				ActivityConstant.SEC_COMMON_ID);
		response.setContext(context);
		BeneficiaryDTO beneficiaryDTO = getBeneficiaryDetails(request,
				response, request.getPayeeId(), null);

		if (beneficiaryDTO != null
				&& FundTransferConstants.PAYEE_TYPE_FUND_TRANSFER_INTERNAL
						.equals(beneficiaryDTO.getPayeeTypeCode())) {

			context
					.setActivityId(ActivityIdConstantBean.DELETE_INTERNAL_PAYEE_ACTIVITY_ID);
		} else if (beneficiaryDTO != null
				&& FundTransferConstants.FUND_TRANSFER_EXTERNAL
						.equals(beneficiaryDTO.getPayeeTypeCode())) {
			context
					.setActivityId(ActivityIdConstantBean.DELETE_EXTERNAL_PAYEE_ACTIVITY_ID);
		} else if (beneficiaryDTO != null
				&& BillPaymentConstants.PAYEE_TYPE_BILL_PAYMENT
						.equals(beneficiaryDTO.getPayeeTypeCode())) {
			context
					.setActivityId(ActivityIdConstantBean.DELETE_PAYEE_ACTIVITY_ID);
		}

		DeleteBeneficiaryServiceRequest deleteBeneficiaryServiceRequest = new DeleteBeneficiaryServiceRequest();
		deleteBeneficiaryServiceRequest.setPayeeId(request.getPayeeId());
		deleteBeneficiaryServiceRequest.setContext(context);
		deleteBeneficiaryServiceRequest.setBeneficiaryDTO(beneficiaryDTO);
		DeleteBeneficiaryServiceResponse deleteBeneficiaryServiceResponse = deleteBeneficiaryService
				.deleteBeneficiary(deleteBeneficiaryServiceRequest);

		response.setSuccess(deleteBeneficiaryServiceResponse.isSuccess());
		response.setBeneficiaryDTO(beneficiaryDTO);
		if (!response.isSuccess()) {
			getMessage(response);
		}
		return response;
	}

}
