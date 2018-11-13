package com.barclays.bmg.audit.builder.impl;

import java.util.Calendar;
import java.util.Date;

import com.barclays.bmg.audit.dto.FieldDataDTO;
import com.barclays.bmg.audit.dto.ScreenDataDTO;
import com.barclays.bmg.audit.dto.TransactionAuditDTO;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.operation.request.fundtransfer.external.UpdateBeneficiaryOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.UpdateBeneficiaryOperationResponse;

public class UpdateBeneficiaryAuditBuilder extends AbstractTransactionAuditBuilder {

    /**
     * The Constant named "SCRNNAM_BP" is created.
     */
    private static final String SCRNNAM_BP = "EDIT_BENEFICIARY";

    @Override
    public TransactionAuditDTO buildFromRequest(Object[] args, Object result) {

	UpdateBeneficiaryOperationRequest request = (UpdateBeneficiaryOperationRequest) args[0];
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();

	ScreenDataDTO screenData = new ScreenDataDTO();

	screenData.setScreenId(SCRNNAM_BP);

	FieldDataDTO field8 = new FieldDataDTO();
	field8.setFieldId(AuditConstant.SERTYP);
	if (FundTransferConstants.PAYEE_TYPE_FUND_TRANSFER_INTERNAL.equals(request.getBeneficiaryDTO().getPayeeTypeCode())) {
	    field8.setValue(AuditConstant.SER_TYP_EDIT_BENEFICIARY_OTH);
	} else {
	    field8.setValue(AuditConstant.SER_TYP_EDIT_BENEFICIARY_DFT);
	}
	screenData.addField(field8);

	FieldDataDTO field9 = new FieldDataDTO();
	field9.setFieldId(AuditConstant.TXNSUBTYP);
	if (FundTransferConstants.PAYEE_TYPE_FUND_TRANSFER_INTERNAL.equals(request.getBeneficiaryDTO().getPayeeTypeCode())) {
	    field9.setValue(AuditConstant.SUB_SER_TYP_EDIT_BENEFICIARY_OTH);

	} else {
	    field9.setValue(AuditConstant.SUB_SER_TYP_EDIT_BENEFICIARY_DFT);
	}
	screenData.addField(field9);

	Date txnDate = Calendar.getInstance().getTime();
	transactionAuditDTO.setTransactionDateTime(txnDate);

	if (request != null) {
	    Context context = request.getContext();

	    if (context != null) {
		FieldDataDTO field1 = new FieldDataDTO();
		field1.setFieldId(AuditConstant.CUSTID);
		field1.setValue(context.getCustomerId());
		screenData.addField(field1);

		FieldDataDTO field2 = new FieldDataDTO();
		field2.setFieldId(AuditConstant.CUSTNAME);
		field2.setValue(context.getFullName());
		screenData.addField(field2);

		FieldDataDTO field3 = new FieldDataDTO();
		field3.setFieldId(AuditConstant.SERNAM);
		field3.setValue(context.getActivityId());
		screenData.addField(field3);

		FieldDataDTO field4 = new FieldDataDTO();
		field4.setFieldId(AuditConstant.SRCMOBNO);
		field4.setValue(context.getMobilePhone());
		screenData.addField(field4);
	    }

	    FieldDataDTO field6 = new FieldDataDTO();
	    field6.setFieldId(AuditConstant.ATTRIBUTE_NAME1);
	    field6.setValue(AuditConstant.BENEFICIARY_NAME);
	    screenData.addField(field6);

	    FieldDataDTO field7 = new FieldDataDTO();
	    field7.setFieldId(AuditConstant.ATTRIBUTE_VALUE1);
	    field7.setValue(request.getBeneficiaryDTO().getBeneficiaryName());
	    screenData.addField(field7);

	}

	transactionAuditDTO.setReqRes(buildData(screenData));

	return transactionAuditDTO;
    }

    @Override
    public TransactionAuditDTO buildFromResponse(Object[] args, Object result) {

	UpdateBeneficiaryOperationResponse response = (UpdateBeneficiaryOperationResponse) result;
	ScreenDataDTO screenData = new ScreenDataDTO();

	FieldDataDTO field8 = new FieldDataDTO();
	field8.setFieldId(AuditConstant.SERTYP);
	if (FundTransferConstants.PAYEE_TYPE_FUND_TRANSFER_INTERNAL.equals(response.getBeneficiaryDTO().getPayeeTypeCode())) {
	    field8.setValue(AuditConstant.SER_TYP_EDIT_BENEFICIARY_OTH);
	} else {
	    field8.setValue(AuditConstant.SER_TYP_EDIT_BENEFICIARY_DFT);
	}
	screenData.addField(field8);

	FieldDataDTO field9 = new FieldDataDTO();
	field9.setFieldId(AuditConstant.TXNSUBTYP);
	if (FundTransferConstants.PAYEE_TYPE_FUND_TRANSFER_INTERNAL.equals(response.getBeneficiaryDTO().getPayeeTypeCode())) {
	    field9.setValue(AuditConstant.SUB_SER_TYP_EDIT_BENEFICIARY_OTH);

	} else {
	    field9.setValue(AuditConstant.SUB_SER_TYP_EDIT_BENEFICIARY_DFT);
	}
	screenData.addField(field9);

	screenData.setScreenId(SCRNNAM_BP);

	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();

	Date txnDate = Calendar.getInstance().getTime();
	transactionAuditDTO.setTransactionDateTime(txnDate);

	UpdateBeneficiaryOperationRequest request = (UpdateBeneficiaryOperationRequest) args[0];
	if (request != null) {
	    Context context = request.getContext();

	    if (context != null) {
		FieldDataDTO field1 = new FieldDataDTO();
		field1.setFieldId(AuditConstant.CUSTID);
		field1.setValue(context.getCustomerId());
		screenData.addField(field1);

		FieldDataDTO field2 = new FieldDataDTO();
		field2.setFieldId(AuditConstant.CUSTNAME);
		field2.setValue(context.getFullName());
		screenData.addField(field2);

		FieldDataDTO field3 = new FieldDataDTO();
		field3.setFieldId(AuditConstant.SERNAM);
		field3.setValue(context.getActivityId());
		screenData.addField(field3);

		FieldDataDTO field4 = new FieldDataDTO();
		field4.setFieldId(AuditConstant.SRCMOBNO);
		field4.setValue(context.getMobilePhone());
		screenData.addField(field4);
	    }

	    FieldDataDTO field6 = new FieldDataDTO();
	    field6.setFieldId(AuditConstant.ATTRIBUTE_NAME1);
	    field6.setValue(AuditConstant.BENEFICIARY_NICK_NAME);
	    screenData.addField(field6);

	    FieldDataDTO field7 = new FieldDataDTO();
	    field7.setFieldId(AuditConstant.ATTRIBUTE_VALUE1);
	    field7.setValue(request.getBeneficiaryDTO().getBeneficiaryNickName());
	    screenData.addField(field7);
	}

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
