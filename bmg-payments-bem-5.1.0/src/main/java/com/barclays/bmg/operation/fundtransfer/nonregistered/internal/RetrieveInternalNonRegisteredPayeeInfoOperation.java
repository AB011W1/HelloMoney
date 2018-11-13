package com.barclays.bmg.operation.fundtransfer.nonregistered.internal;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.operation.request.fundtransfer.nonregistered.internal.RetrieveInternalNonRegisteredPayeeInfoOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.nonregistered.internal.RetrieveInternalNonRegisteredPayeeInfoOperationResponse;
import com.barclays.bmg.service.accountdetails.CASADetailsService;
import com.barclays.bmg.service.accountdetails.request.CASADetailsServiceRequest;
import com.barclays.bmg.service.accountdetails.response.CASADetailsServiceResponse;

public class RetrieveInternalNonRegisteredPayeeInfoOperation extends BMBPaymentsOperation{


	private CASADetailsService casaDetailsService;

	@AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, activityId = ActivityIdConstantBean.FUND_TRANSFER_INTERNAL_NONREGISTERED_PAYEE_ACTIVITY_ID, serviceDescription = "SD_RETRIEVE_INTERNAL_NON_REGISTERED_PAYEE_DETAILS", stepId = "2", activityType="auditDomesticFundTransfer")
	public RetrieveInternalNonRegisteredPayeeInfoOperationResponse retrievePayeeInfo(RetrieveInternalNonRegisteredPayeeInfoOperationRequest request){
		Context context = request.getContext();
		RetrieveInternalNonRegisteredPayeeInfoOperationResponse response = new RetrieveInternalNonRegisteredPayeeInfoOperationResponse();
		loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID );
		response.setContext(context);

		BeneficiaryDTO beneficiaryDTO = new BeneficiaryDTO();
		response.setBeneficiaryDTO(beneficiaryDTO);

		CASAAccountDTO casaAcct = getBeneficiaryInfo(request);
		if(casaAcct !=null && (casaAcct.getAccountNumber()!= null && !casaAcct.getAccountNumber().isEmpty())){
			response.getBeneficiaryDTO().setBeneficiaryName(request.getBeneficiaryName());
			response.getBeneficiaryDTO().setDestinationBranchCode(request.getBranchCode());
			response.getBeneficiaryDTO().setDestinationAccountNumber(request.getAccountNumber());
			response.getBeneficiaryDTO().setCurrency(casaAcct.getCurrency());
			response.setCasaAccountDTO(casaAcct);
		}else{
			response.setSuccess(false);
			response.setResCde(BillPaymentResponseCodeConstants.PAYMENT_INVALID_BENEFICIARY);
		}

		if(!response.isSuccess()){
			getMessage(response);
		}
		return response;
	}

	/**
	 * @param beneficiaryDTO
	 * @param request
	 * @return
	 */
	private CASAAccountDTO getBeneficiaryInfo(RetrieveInternalNonRegisteredPayeeInfoOperationRequest request){
		CASADetailsServiceRequest casaDetailsServiceRequest = new CASADetailsServiceRequest();
		casaDetailsServiceRequest.setContext(request.getContext());
		casaDetailsServiceRequest.setAccountNo(request.getAccountNumber());
		casaDetailsServiceRequest.setBranchCode(getFormattedBranchCode(request, request.getBranchCode()));

		CASADetailsServiceResponse casaDetailsServiceResponse =
						casaDetailsService.retrieveCASADetails(casaDetailsServiceRequest);
		CASAAccountDTO destAcct = casaDetailsServiceResponse.getCasaAccountDTO();
		return destAcct;
	}


	public CASADetailsService getCasaDetailsService() {
		return casaDetailsService;
	}

	public void setCasaDetailsService(CASADetailsService casaDetailsService) {
		this.casaDetailsService = casaDetailsService;
	}



}
