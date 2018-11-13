package com.barclays.bmg.operation.fundtransfer.external;

import org.springframework.transaction.annotation.Transactional;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.InternationalFundTransferOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.InternationalFundTransferOperationResponse;
import com.barclays.bmg.service.InternationalFundTransferService;
import com.barclays.bmg.service.request.InternationalFundTransferServiceRequest;
import com.barclays.bmg.service.response.InternationalFundTransferServiceResponse;

public class InternationalFundTransferOperation extends BMBPaymentsOperation {

	private InternationalFundTransferService internationalFundTransferService;

	@AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_EXECUTOR, serviceDescription = "SD_MAKE_INTL_FT", stepId = "3", activityType = "auditInternationalFundTransfer")
	public InternationalFundTransferOperationResponse makeInternationalFundTransfer(
			InternationalFundTransferOperationRequest request) {
		Context context = request.getContext();
		loadParameters(context, ActivityConstant.COMMON_ID,
				ActivityConstant.SEC_COMMON_ID);
		InternationalFundTransferOperationResponse response = new InternationalFundTransferOperationResponse();
		response.setContext(context);
		if (checkTransactionAbility(request)) {
			if (request.isAuthRequired()
					&& !BillPaymentConstants.AUTH_TYPE_NON.equals(request
							.getAuthType())) {
				response.setAuthRequired(true);
				return response;
			}
			InternationalFundTransferServiceRequest serviceRequest = makeRequest(request);
			makeInternationalFundTransfer(serviceRequest, request, response);

		} else {
			response.setSuccess(false);
			response
					.setResCde(FundTransferResponseCodeConstants.FT_TRANSACTIONABILITY_ERROR);
		}

		if (!response.isSuccess()) {
			getMessage(response);
		}
		return response;
	}

	@Transactional
	private void makeInternationalFundTransfer(
			InternationalFundTransferServiceRequest serviceRequest,
			InternationalFundTransferOperationRequest request,
			InternationalFundTransferOperationResponse response) {
		upgradeTransactionLimit(request, request.getTxnAmtInLCY());
		InternationalFundTransferServiceResponse serviceResponse = internationalFundTransferService
				.makeInternationFundTransfer(serviceRequest);
		if (serviceResponse != null && serviceResponse.isSuccess()) {
			response.setTrnDate(serviceResponse.getTrnDate());
			response.setTrnRef(serviceResponse.getTrnRef());
			response.setTxnMsg(serviceResponse.getTxnMsg());
		} else {
			response.setSuccess(false);
			if (serviceResponse != null)
				response.setResCde(serviceResponse.getResCde());
		}
	}

	private InternationalFundTransferServiceRequest makeRequest(
			InternationalFundTransferOperationRequest request) {
		InternationalFundTransferServiceRequest serviceRequest = new InternationalFundTransferServiceRequest();
		serviceRequest.setBeneficiaryDTO(request.getBeneficiaryDTO());
		serviceRequest.setChargeDescCode(request.getChargeDesc());
		serviceRequest.setRemPart1(request.getRem1());
		serviceRequest.setRemPart2(request.getRem2());
		serviceRequest.setRemPart3(request.getRem3());
		serviceRequest.setSourceAcct(request.getFrmAcct());
		serviceRequest.setTxnType(request.getTxnType());
		serviceRequest.setFxRateDTO(request.getFxRateDTO());
		serviceRequest.setTxnAmt(request.getAmt());
		serviceRequest.setPayRsonKey(request.getPayRsonKey());
		serviceRequest.setPayRsonValue(request.getPayRsonValue());
		serviceRequest.setPayDtlsKey(request.getPayDtlsKey());
		serviceRequest.setPayDtlsValue(request.getPayDtlsValue());
		serviceRequest.setContext(request.getContext());
		serviceRequest.setTxnNot(request.getTxnNot());

		return serviceRequest;
	}

	public InternationalFundTransferService getInternationalFundTransferService() {
		return internationalFundTransferService;
	}

	public void setInternationalFundTransferService(
			InternationalFundTransferService internationalFundTransferService) {
		this.internationalFundTransferService = internationalFundTransferService;
	}

}
