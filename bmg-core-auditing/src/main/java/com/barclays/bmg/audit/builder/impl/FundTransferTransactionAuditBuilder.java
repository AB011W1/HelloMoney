package com.barclays.bmg.audit.builder.impl;

import java.util.Calendar;
import java.util.Date;

import com.barclays.bmg.audit.dto.FieldDataDTO;
import com.barclays.bmg.audit.dto.ScreenDataDTO;
import com.barclays.bmg.audit.dto.TransactionAuditDTO;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.operation.request.fundtransfer.DomesticFundTransferExecuteOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.DomesticFundTransferExecuteOperationResponse;

public class FundTransferTransactionAuditBuilder extends AbstractTransactionAuditBuilder {

    private static final String PMT_FT_INTERNAL_CONFIRM = "PMT_FT_INTERNAL_CONFIRM";

    @Override
    public TransactionAuditDTO buildFromRequest(Object[] args, Object result) {
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	DomesticFundTransferExecuteOperationRequest request = (DomesticFundTransferExecuteOperationRequest) args[0];

	ScreenDataDTO screenData = new ScreenDataDTO();
	String fromAcctNo = request.getTransactionDTO().getSourceAcct().getAccountNumber();
	String toAcctNo = request.getTransactionDTO().getBeneficiaryDTO().getDestinationAccountNumber();
	Double txnAmount = request.getTransactionDTO().getTxnAmt().getAmount().doubleValue();
	Double fxRate = request.getTransactionDTO().getFxRateDTO().getEffectiveFXRate().doubleValue();
	// Double eqAmt = request.getTransactionDTO().getFxRateDTO()
	// .getEquivalentAmount().doubleValue();
	String currency = request.getTransactionDTO().getSourceAcct().getCurrency();
	String txnNote = request.getTransactionDTO().getTxnNot();
	String txnType = request.getTransactionDTO().getTxnType();
	Date txnDate = Calendar.getInstance().getTime();
	String beneName = request.getTransactionDTO().getBeneficiaryDTO().getBeneficiaryName();
	transactionAuditDTO.setFromAccount(fromAcctNo);
	transactionAuditDTO.setToAccount(toAcctNo);
	transactionAuditDTO.setTransactionAmount(txnAmount);
	transactionAuditDTO.setFxRate(fxRate);
	transactionAuditDTO.setTransactionCurrency(currency);
	transactionAuditDTO.setTransactionDateTime(txnDate);

	if (FundTransferConstants.TXN_TYPE_OWN_FUND_TRANSFER.equals(txnType)) {
	    screenData.setScreenId(AuditConstant.PMT_FT_OWN_CONFIRM);

	} else if (FundTransferConstants.TXN_TYPE_FUND_TRANSFER_INTERNAL.equals(txnType)) {
	    screenData.setScreenId(PMT_FT_INTERNAL_CONFIRM);
	}
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

	FieldDataDTO field5 = new FieldDataDTO();
	field5.setFieldId(AuditConstant.NOTE);
	field5.setValue(txnNote);
	screenData.addField(field5);
	FieldDataDTO field6 = new FieldDataDTO();
	field6.setFieldId(AuditConstant.FX_RATE);
	try {

	    field6.setValue(fxRate.toString());

	} catch (Exception e) {

	    field6.setValue("");
	}
	screenData.addField(field6);
	FieldDataDTO field7 = new FieldDataDTO();
	field7.setFieldId(AuditConstant.TRANSACTION_DATE);
	// field7.setValue(new Date().toString());
	field7.setValue(txnDate.toString());
	screenData.addField(field7);
	transactionAuditDTO.setReqRes(buildData(screenData));

	return transactionAuditDTO;
    }

    @Override
    public TransactionAuditDTO buildFromResponse(Object[] args, Object result) {
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	ScreenDataDTO screenData = new ScreenDataDTO();
	DomesticFundTransferExecuteOperationResponse response = (DomesticFundTransferExecuteOperationResponse) result;

	DomesticFundTransferExecuteOperationRequest request = (DomesticFundTransferExecuteOperationRequest) args[0];
	if (request != null) {
	    String fromAcctNo = request.getTransactionDTO().getSourceAcct().getAccountNumber();

	    transactionAuditDTO.setFromAccount(fromAcctNo);
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
