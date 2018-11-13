package com.barclays.bmg.audit.builder.impl;

import java.util.Calendar;
import java.util.Date;

import com.barclays.bmg.audit.dto.FieldDataDTO;
import com.barclays.bmg.audit.dto.ScreenDataDTO;
import com.barclays.bmg.audit.dto.TransactionAuditDTO;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.operation.request.fundtransfer.external.InternationalFundTransferOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.InternationalFundTransferOperationResponse;

public class InternationalFundTransferTransactionAuditBuilder extends AbstractTransactionAuditBuilder {

    private static final String PMT_FT_INTL_CONFIRM = "PMT_FT_INTL_CONFIRM";

    @Override
    public TransactionAuditDTO buildFromRequest(Object[] args, Object result) {
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	ScreenDataDTO screenData = new ScreenDataDTO();
	InternationalFundTransferOperationRequest request = (InternationalFundTransferOperationRequest) args[0];

	String toAcctNo = request.getBeneficiaryDTO().getDestinationAccountNumber();
	String fromAcctNo = request.getFrmAcct().getAccountNumber();
	Double txnAmount = request.getAmt().getAmount().doubleValue();
	// Double fxRate = request.get.getEffectiveFXRate().doubleValue();
	// Double eqAmt =
	// request.getTransactionDTO().getFxRateDTO().getEquivalentAmount().doubleValue();
	String currency = request.getFrmAcct().getCurrency();
	String txnNote = " ";
	// String txnType = request.getTxnType();
	Date txnDate = Calendar.getInstance().getTime();
	String beneName = request.getBeneficiaryDTO().getBeneficiaryName();
	boolean iBanFlg = request.getBeneficiaryDTO().isIbanFlag();
	String payReason = request.getPayRsonValue();
	String payDtls = request.getPayDtlsValue();
	transactionAuditDTO.setFromAccount(fromAcctNo);
	transactionAuditDTO.setToAccount(toAcctNo);
	transactionAuditDTO.setTransactionAmount(txnAmount);
	// transactionAuditDTO.setFxRate(fxRate);
	transactionAuditDTO.setTransactionCurrency(currency);
	transactionAuditDTO.setTransactionDateTime(txnDate);

	screenData.setScreenId(PMT_FT_INTL_CONFIRM);
	FieldDataDTO field1 = new FieldDataDTO();
	field1.setFieldId(AuditConstant.FROM_ACCOUNT);
	field1.setValue(fromAcctNo);
	screenData.addField(field1);
	transactionAuditDTO.setFromAccount(fromAcctNo);
	FieldDataDTO field2 = new FieldDataDTO();
	field2.setFieldId(AuditConstant.TO_ACCOUNT);
	try {
	    field2.setValue(toAcctNo);
	    transactionAuditDTO.setToAccount(toAcctNo);
	} catch (Exception e) {

	    field2.setValue("");
	}
	screenData.addField(field2);
	FieldDataDTO field3 = new FieldDataDTO();
	field3.setFieldId(AuditConstant.AMOUNT);
	transactionAuditDTO.setTransactionAmount(txnAmount);

	try {
	    field3.setValue(currency + " " + txnAmount);
	} catch (Exception e) {

	    field3.setValue("");
	}
	screenData.addField(field3);

	if (beneName != null) {
	    FieldDataDTO field4 = new FieldDataDTO();
	    field4.setFieldId(AuditConstant.BENEFICIARY_NAME);
	    try {
		field4.setValue(beneName);
	    } catch (Exception e) {

		field4.setValue("");
	    }
	    screenData.addField(field4);
	}

	if (request.getRem1() != null) {
	    txnNote = request.getRem1();
	}

	if (request.getRem2() != null) {
	    txnNote = " " + request.getRem2();
	}

	if (request.getRem3() != null) {
	    txnNote = " " + request.getRem3();
	}

	FieldDataDTO field5 = new FieldDataDTO();
	field5.setFieldId(AuditConstant.NOTE);
	field5.setValue(txnNote);
	screenData.addField(field5);
	/*
	 * FieldDataDTO field6 = new FieldDataDTO(); field6.setFieldId("FxRate"); try {
	 * 
	 * 
	 * field6.setValue(fxRate.toString());
	 * 
	 * } catch (Exception e) {
	 * 
	 * field6.setValue(""); } screenData.addField(field6);
	 */FieldDataDTO field7 = new FieldDataDTO();
	field7.setFieldId(AuditConstant.TRANSACTION_DATE);
	// field7.setValue(new Date().toString());
	field7.setValue(txnDate.toString());
	screenData.addField(field7);

	FieldDataDTO field8 = new FieldDataDTO();
	field8.setFieldId(AuditConstant.IBANFLAG);
	field8.setValue(Boolean.toString(iBanFlg));
	screenData.addField(field8);

	FieldDataDTO field9 = new FieldDataDTO();
	field9.setFieldId(AuditConstant.PAYMENT_REASON);
	field9.setValue(payReason);
	screenData.addField(field9);
	FieldDataDTO field10 = new FieldDataDTO();
	field10.setFieldId(AuditConstant.PAYMENT_DETAILS);
	field10.setValue(payDtls);
	screenData.addField(field10);

	transactionAuditDTO.setReqRes(buildData(screenData));

	return transactionAuditDTO;
    }

    @Override
    public TransactionAuditDTO buildFromResponse(Object[] args, Object result) {
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	ScreenDataDTO screenData = new ScreenDataDTO();
	InternationalFundTransferOperationResponse response = (InternationalFundTransferOperationResponse) result;

	InternationalFundTransferOperationRequest request = (InternationalFundTransferOperationRequest) args[0];

	Date txnDate1 = Calendar.getInstance().getTime();
	transactionAuditDTO.setTransactionDateTime(txnDate1);

	if (request != null) {

	    String fromAcctNo = request.getFrmAcct().getAccountNumber();

	    transactionAuditDTO.setFromAccount(fromAcctNo);
	    transactionAuditDTO.setToAccount(request.getBeneficiaryDTO().getDestinationAccountNumber());
	    transactionAuditDTO.setTransactionAmount(request.getTxnAmtInLCY().doubleValue());
	    transactionAuditDTO.setTransactionCurrency(request.getBeneficiaryDTO().getCurrency());

	}

	if (response != null) {
	    Date txnDate = response.getTrnDate();
	    String txnRefNo = response.getTrnRef();

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
