package com.barclays.bmg.dao.operation;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.operation.accountdetails.request.ApplyTDAddProblemOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.ApplyTDValidationOperationResponse;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.service.request.ApplyTDServiceRequest;
import com.barclays.bmg.service.response.ApplyTDServiceResponse;

public class ApplyTDAddProbmlemOperation extends BMBPaymentsOperation{

	public ApplyTDServiceResponse applyTDAddProblem(ApplyTDAddProblemOperationRequest applTDAddPrbRequest,
			                             ApplyTDValidationOperationResponse applyTDValidationOperationResponse) {

		Context context = applTDAddPrbRequest.getContext();

		loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID,ActivityConstant.OPEN_TERMDEPOSIT_ACTIVITY_ID );

		CustomerAccountDTO sourceCustomerAccountDTO = applyTDValidationOperationResponse.getSourceCustomerAccountDTO();
		context.setCustomerId(sourceCustomerAccountDTO.getCustomerId());

		ApplyTDServiceRequest applyTDServiceRequest = new ApplyTDServiceRequest();
        setNoteDetails(applTDAddPrbRequest,applyTDServiceRequest);
		applyTDServiceRequest.setAccountNumber(applyTDValidationOperationResponse.getAcctNo());
		applyTDServiceRequest.setContext(applTDAddPrbRequest.getContext());
		ApplyTDServiceResponse applyTDServiceResponse = super.getApplyTDAddProblemService().addProblem(applyTDServiceRequest);

		return applyTDServiceResponse;
	}

	private void setNoteDetails(ApplyTDAddProblemOperationRequest source,ApplyTDServiceRequest dest) {
		dest.setNoteDescription(source.getNoteDescription());
		dest.setSubject(source.getSubject());
		dest.setNotesId(source.getNotesId());
		dest.setObjectId(source.getObjectId());
	}

}
