package com.barclays.bmg.audit.builder.impl;

import java.util.Calendar;
import java.util.Date;

import com.barclays.bmg.audit.dto.FieldDataDTO;
import com.barclays.bmg.audit.dto.ScreenDataDTO;
import com.barclays.bmg.audit.dto.TransactionAuditDTO;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrievePayeeInfoOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeInfoOperationResponse;

public class RetrievePayeeInfoTransactionAuditBuilder extends AbstractTransactionAuditBuilder {

    @Override
    public TransactionAuditDTO buildFromRequest(Object[] args, Object result) {
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	ScreenDataDTO screenData = new ScreenDataDTO();
	RetrievePayeeInfoOperationRequest request = (RetrievePayeeInfoOperationRequest) args[0];

	String payId = request.getPayId();
	String payGroup = request.getPayGrp();

	Date txnDate = Calendar.getInstance().getTime();
	transactionAuditDTO.setTransactionDateTime(txnDate);

	FieldDataDTO field1 = new FieldDataDTO();
	field1.setFieldId(AuditConstant.PAYID);
	field1.setValue(payId);
	screenData.addField(field1);
	FieldDataDTO field2 = new FieldDataDTO();
	field2.setFieldId(AuditConstant.PAYTYP);
	try {
	    field2.setValue(payGroup);
	} catch (Exception e) {
	    field2.setValue(AuditConstant.WHITESPACE);
	}
	screenData.addField(field2);

	transactionAuditDTO.setReqRes(buildData(screenData));

	return transactionAuditDTO;
    }

    @Override
    public TransactionAuditDTO buildFromResponse(Object[] args, Object result) {
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	RetrievePayeeInfoOperationResponse response = (RetrievePayeeInfoOperationResponse) result;
	ScreenDataDTO screenData = new ScreenDataDTO();

	Date txnDate = Calendar.getInstance().getTime();
	transactionAuditDTO.setTransactionDateTime(txnDate);

	if (response != null) {
	    BeneficiaryDTO beneficiary = response.getBeneficiaryDTO();
	    if (beneficiary != null) {
		FieldDataDTO billHolderName = new FieldDataDTO();
		billHolderName.setFieldId(AuditConstant.BILLHOLDNAM);
		billHolderName.setValue(beneficiary.getBeneficiaryName());
		screenData.addField(billHolderName);

		FieldDataDTO billHolderAddr = new FieldDataDTO();
		billHolderAddr.setFieldId(AuditConstant.BILLHOLDADDR);
		billHolderAddr.setValue((beneficiary.getDestinationAddressLine1() == null ? AuditConstant.WHITESPACE : beneficiary
			.getDestinationAddressLine1())
			+ (beneficiary.getDestinationAddressLine2() == null ? AuditConstant.WHITESPACE : AuditConstant.WHITESPACE
				+ beneficiary.getDestinationAddressLine2())
			+ (beneficiary.getDestinationAddressLine3() == null ? AuditConstant.WHITESPACE : AuditConstant.WHITESPACE
				+ beneficiary.getDestinationAddressLine3()));
		screenData.addField(billHolderAddr);
	    }
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