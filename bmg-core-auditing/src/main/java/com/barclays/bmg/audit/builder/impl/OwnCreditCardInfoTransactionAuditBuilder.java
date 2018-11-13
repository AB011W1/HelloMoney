package com.barclays.bmg.audit.builder.impl;

import java.util.Calendar;
import java.util.Date;

import com.barclays.bmg.audit.dto.FieldDataDTO;
import com.barclays.bmg.audit.dto.ScreenDataDTO;
import com.barclays.bmg.audit.dto.TransactionAuditDTO;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.operation.request.fundtransfer.external.MergeOwnCreditcardInfoOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.MergeOwnCreditcardInfoOperationResponse;

public class OwnCreditCardInfoTransactionAuditBuilder extends AbstractTransactionAuditBuilder {

    @Override
    public TransactionAuditDTO buildFromRequest(Object[] args, Object result) {
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	ScreenDataDTO screenData = new ScreenDataDTO();

	MergeOwnCreditcardInfoOperationRequest request = (MergeOwnCreditcardInfoOperationRequest) args[0];

	String toAcctNo = request.getCustomerAccountDTO().getAccountNumber();
	Date txnDate = Calendar.getInstance().getTime();
	transactionAuditDTO.setTransactionDateTime(txnDate);
	FieldDataDTO field3 = new FieldDataDTO();
	field3.setFieldId(AuditConstant.TO_ACCOUNT);
	try {
	    field3.setValue(toAcctNo);
	} catch (Exception e) {

	    field3.setValue("");
	}
	screenData.addField(field3);

	transactionAuditDTO.setReqRes(buildData(screenData));

	return transactionAuditDTO;
    }

    @Override
    public TransactionAuditDTO buildFromResponse(Object[] args, Object result) {
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	ScreenDataDTO screenData = new ScreenDataDTO();
	// MergeOwnCreditcardInfoOperationRequest request =
	// (MergeOwnCreditcardInfoOperationRequest)args[0];
	MergeOwnCreditcardInfoOperationResponse response = (MergeOwnCreditcardInfoOperationResponse) result;
	Date txnDate = Calendar.getInstance().getTime();
	transactionAuditDTO.setTransactionDateTime(txnDate);
	if (response != null) {
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
