package com.barclays.bmg.audit.builder.impl;

import java.util.Calendar;
import java.util.Date;

import com.barclays.bmg.audit.dto.FieldDataDTO;
import com.barclays.bmg.audit.dto.ScreenDataDTO;
import com.barclays.bmg.audit.dto.TransactionAuditDTO;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.operation.request.bankdraft.PurchaseBankDraftOperationRequest;
import com.barclays.bmg.operation.response.bankdraft.PurchaseBankDraftOperationResponse;

public class PurchaseBankDraftTransactionAuditBuilder extends AbstractTransactionAuditBuilder {

    private static final String PMT_BANK_DRAFT_CONFIRM = "PMT_BANK_DRAFT_CONFIRM";

    @Override
    public TransactionAuditDTO buildFromRequest(Object[] args, Object result) {
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	ScreenDataDTO screenData = new ScreenDataDTO();

	PurchaseBankDraftOperationRequest request = (PurchaseBankDraftOperationRequest) args[0];

	screenData.setScreenId(PMT_BANK_DRAFT_CONFIRM);
	Date txnDate = Calendar.getInstance().getTime();
	transactionAuditDTO.setTransactionDateTime(txnDate);
	if (request != null) {

	    String delvTyp = request.getBankDraftTransactionDTO().getDeliveryType();
	    String dftTyp = request.getBankDraftTransactionDTO().getDraftType();
	    String beneName = request.getBankDraftTransactionDTO().getBeneficiaryDTO().getBeneficiaryName();
	    String payAt = request.getBankDraftTransactionDTO().getPayableAtCode();
	    String frmAcctNo = request.getBankDraftTransactionDTO().getSourceAcct().getAccountNumber();
	    Double txnAmt = request.getBankDraftTransactionDTO().getTxnAmt().getAmount().doubleValue();

	    FieldDataDTO fieldDataDTO = new FieldDataDTO(AuditConstant.DELIVERY_TYPE, delvTyp);
	    screenData.addField(fieldDataDTO);

	    fieldDataDTO = new FieldDataDTO(AuditConstant.DRAFT_TYPE, dftTyp);
	    screenData.addField(fieldDataDTO);

	    fieldDataDTO = new FieldDataDTO(AuditConstant.BENEFICIARY_NAME, beneName);
	    screenData.addField(fieldDataDTO);

	    fieldDataDTO = new FieldDataDTO(AuditConstant.PAYABLE_AT, payAt);
	    screenData.addField(fieldDataDTO);

	    fieldDataDTO = new FieldDataDTO(AuditConstant.ACCTNO, frmAcctNo);
	    screenData.addField(fieldDataDTO);
	    transactionAuditDTO.setFromAccount(frmAcctNo);

	    fieldDataDTO = new FieldDataDTO(AuditConstant.AMOUNT, txnAmt.toString());
	    screenData.addField(fieldDataDTO);

	    transactionAuditDTO.setTransactionAmount(txnAmt);
	}

	transactionAuditDTO.setReqRes(buildData(screenData));
	return transactionAuditDTO;
    }

    @Override
    public TransactionAuditDTO buildFromResponse(Object[] args, Object result) {
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	ScreenDataDTO screenData = new ScreenDataDTO();

	screenData.setScreenId(PMT_BANK_DRAFT_CONFIRM);

	Date txnDate1 = Calendar.getInstance().getTime();
	transactionAuditDTO.setTransactionDateTime(txnDate1);

	PurchaseBankDraftOperationResponse response = (PurchaseBankDraftOperationResponse) result;

	PurchaseBankDraftOperationRequest request = (PurchaseBankDraftOperationRequest) args[0];

	if (request != null) {
	    String frmAcctNo = request.getBankDraftTransactionDTO().getSourceAcct().getAccountNumber();
	    transactionAuditDTO.setFromAccount(frmAcctNo);
	}
	if (response != null) {
	    Date txnDate = response.getTransactionDate();
	    String txnRefNo = response.getTransactionRefNo();
	    FieldDataDTO field1 = new FieldDataDTO();
	    field1.setFieldId(AuditConstant.REFERENCE_NUMBER);

	    field1.setValue(txnRefNo);

	    screenData.addField(field1);

	    FieldDataDTO field2 = new FieldDataDTO();
	    field2.setFieldId(AuditConstant.TRANSACTION_DATE);
	    field2.setValue(txnDate.toString());
	    screenData.addField(field2);
	    if (response.isSuccess()) {
		transactionAuditDTO.setTransactionStatus(AuditConstant.TXN_STATUS_SUCCESS);

	    } else {
		transactionAuditDTO.setTransactionStatus(AuditConstant.TXN_STATUS_FAILURE);
		transactionAuditDTO.setErrorMessage(response.getResMsg());

		FieldDataDTO field16 = new FieldDataDTO();
		field16.setFieldId(AuditConstant.FAILURE_REASON);
		field16.setValue(response.getResMsg());
		screenData.addField(field16);

		FieldDataDTO field17 = new FieldDataDTO();
		field17.setFieldId(AuditConstant.ERRCD);
		field17.setValue(response.getResCde());
		screenData.addField(field17);

	    }

	} else {
	    transactionAuditDTO.setTransactionStatus(AuditConstant.TXN_STATUS_FAILURE);
	}

	transactionAuditDTO.setReqRes(buildData(screenData));
	return transactionAuditDTO;
    }

}
