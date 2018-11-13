package com.barclays.bmg.mvc.operation.bankdraft;

import java.math.BigDecimal;

import org.springframework.transaction.annotation.Transactional;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.AuditConstant;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.dto.BankDraftTransactionDTO;
import com.barclays.bmg.operation.request.bankdraft.PurchaseBankDraftOperationRequest;
import com.barclays.bmg.operation.response.bankdraft.PurchaseBankDraftOperationResponse;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.service.bankdraft.PurchaseBankDraftService;
import com.barclays.bmg.service.request.bankdraft.PurchaseBankDraftServiceRequest;
import com.barclays.bmg.service.response.bankdraft.PurchaseBankDraftServiceResponse;

public class PurchaseBankDraftOperation extends BMBPaymentsOperation {

	private PurchaseBankDraftService purchaseBankDraftService;

	@AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, serviceDescription = "SD_PURCHASE_BANK_DRAFT", stepId = "1", activityType = "auditPurchaseBankDraft")
	public PurchaseBankDraftOperationResponse purchaseBankDraft(
			PurchaseBankDraftOperationRequest purchaseBankDraftOperationRequest) {

		PurchaseBankDraftServiceRequest purchaseBankDraftServiceRequest = new PurchaseBankDraftServiceRequest();

		loadParameters(purchaseBankDraftOperationRequest.getContext(),
				ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);

		purchaseBankDraftServiceRequest
				.setContext(purchaseBankDraftOperationRequest.getContext());
		purchaseBankDraftServiceRequest
				.setBankDraftTransactionDTO(purchaseBankDraftOperationRequest
						.getBankDraftTransactionDTO());

		PurchaseBankDraftServiceResponse purchaseBankDraftServiceResponse = doExecute(purchaseBankDraftServiceRequest);
		PurchaseBankDraftOperationResponse purchaseBankDraftOperationResponse = new PurchaseBankDraftOperationResponse();
		purchaseBankDraftOperationResponse
				.setSuccess(purchaseBankDraftServiceResponse.isSuccess());
		purchaseBankDraftOperationResponse
				.setContext(purchaseBankDraftServiceResponse.getContext());
		purchaseBankDraftOperationResponse
				.setTransactionRefNo(purchaseBankDraftServiceResponse
						.getTransactionRefNo());
		purchaseBankDraftOperationResponse
				.setTransactionDate(purchaseBankDraftServiceResponse
						.getTransactionDate());
		purchaseBankDraftOperationResponse
				.setBankDraftTransactionDTO(purchaseBankDraftServiceResponse
						.getBankDraftTransactionDTO());

		purchaseBankDraftOperationResponse
				.setTxnMsg(purchaseBankDraftServiceResponse.getTxnMsg());

		return purchaseBankDraftOperationResponse;
	}

	@Transactional
	protected PurchaseBankDraftServiceResponse doExecute(
			PurchaseBankDraftServiceRequest purchaseBankDraftServiceRequest) {

		BankDraftTransactionDTO transactionDTO = purchaseBankDraftServiceRequest
				.getBankDraftTransactionDTO();

		BigDecimal amt = transactionDTO.getTxnAmtInLCY();

		if (amt == null) {
			amt = transactionDTO.getTxnAmt().getAmount();
		}

		upgradeTransactionLimit(purchaseBankDraftServiceRequest, amt);

		PurchaseBankDraftServiceResponse purchaseBankDraftServiceResponse = purchaseBankDraftService
				.purchaseBankDraft(purchaseBankDraftServiceRequest);

		return purchaseBankDraftServiceResponse;

	}

	public PurchaseBankDraftService getPurchaseBankDraftService() {
		return purchaseBankDraftService;
	}

	public void setPurchaseBankDraftService(
			PurchaseBankDraftService purchaseBankDraftService) {
		this.purchaseBankDraftService = purchaseBankDraftService;
	}

}
