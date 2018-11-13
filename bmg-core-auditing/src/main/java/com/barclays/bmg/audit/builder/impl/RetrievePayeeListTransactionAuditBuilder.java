package com.barclays.bmg.audit.builder.impl;

import java.util.Calendar;
import java.util.Date;

import com.barclays.bmg.audit.dto.FieldDataDTO;
import com.barclays.bmg.audit.dto.ScreenDataDTO;
import com.barclays.bmg.audit.dto.TransactionAuditDTO;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrievePayeeListOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeListOperationResponse;

public class RetrievePayeeListTransactionAuditBuilder extends AbstractTransactionAuditBuilder {

    private final static String SCRNNAME_BP = "PAYEE_LIST_FT";
    private final static String SCRNNAME_MTP = "PAYEE_LIST_BILLER";

    @Override
    public TransactionAuditDTO buildFromRequest(Object[] args, Object result) {

	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	ScreenDataDTO screenData = new ScreenDataDTO();
	Date txnDate = Calendar.getInstance().getTime();
	transactionAuditDTO.setTransactionDateTime(txnDate);
	RetrievePayeeListOperationRequest request = (RetrievePayeeListOperationRequest) args[0];
	;
	if (request != null) {
	    Context context = request.getContext();
	    if (context.getActivityId().equals(ActivityIdConstantBean.FUND_TRANSFER_EXTERNAL_PAYEE_ACTIVITY_ID)
		    || context.getActivityId().equals(ActivityIdConstantBean.FUND_TRANSFER_INTERNAL_PAYEE_ACTIVITY_ID)
		    || context.getActivityId().equals(ActivityIdConstantBean.FUND_TRANSFER_OWN_ACTIVITY_ID)) {

		screenData.setScreenId(SCRNNAME_BP);

		FieldDataDTO field1 = new FieldDataDTO();
		field1.setFieldId(AuditConstant.SERTYP);
		field1.setValue(AuditConstant.SER_TYP_PAYEE_LIST_FT);
		screenData.addField(field1);

		FieldDataDTO field2 = new FieldDataDTO();
		field2.setValue(AuditConstant.SUB_SER_TYP_PAYEE_LIST_FT);
		field2.setFieldId(AuditConstant.TXNSUBTYP);
		screenData.addField(field2);
	    } else if (context.getActivityId().equals(ActivityConstant.BILL_PAYMENT_ACTIVITY_ID)) {

		screenData.setScreenId(SCRNNAME_MTP);

		FieldDataDTO field1 = new FieldDataDTO();
		field1.setFieldId(AuditConstant.SERTYP);
		field1.setValue(BillPaymentConstants.SER_TYP_VIEW_MY_BILLER);
		screenData.addField(field1);

		FieldDataDTO field2 = new FieldDataDTO();
		field2.setValue(BillPaymentConstants.SUB_SER_TYP_VIEW_MY_BILLER);
		field2.setFieldId(AuditConstant.TXNSUBTYP);
		screenData.addField(field2);
	    }
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
	transactionAuditDTO.setReqRes(buildData(screenData));

	return transactionAuditDTO;
    }

    @Override
    public TransactionAuditDTO buildFromResponse(Object[] args, Object result) {
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	RetrievePayeeListOperationResponse response = (RetrievePayeeListOperationResponse) result;
	RetrievePayeeListOperationRequest request = (RetrievePayeeListOperationRequest) args[0];
	;
	ScreenDataDTO screenData = new ScreenDataDTO();

	if (request != null) {
	    Context context = request.getContext();
	    if (context.getActivityId().equals(ActivityIdConstantBean.FUND_TRANSFER_EXTERNAL_PAYEE_ACTIVITY_ID)
		    || context.getActivityId().equals(ActivityIdConstantBean.FUND_TRANSFER_INTERNAL_PAYEE_ACTIVITY_ID)
		    || context.getActivityId().equals(ActivityIdConstantBean.FUND_TRANSFER_OWN_ACTIVITY_ID)) {

		screenData.setScreenId(SCRNNAME_BP);

		FieldDataDTO field1 = new FieldDataDTO();
		field1.setFieldId(AuditConstant.SERTYP);
		field1.setValue(AuditConstant.SER_TYP_PAYEE_LIST_FT);
		screenData.addField(field1);

		FieldDataDTO field2 = new FieldDataDTO();
		field2.setValue(AuditConstant.SUB_SER_TYP_PAYEE_LIST_FT);
		field2.setFieldId(AuditConstant.TXNSUBTYP);
		screenData.addField(field2);
	    } else if (context.getActivityId().equals(ActivityConstant.BILL_PAYMENT_ACTIVITY_ID)) {

		screenData.setScreenId(SCRNNAME_MTP);

		FieldDataDTO field1 = new FieldDataDTO();
		field1.setFieldId(AuditConstant.SERTYP);
		field1.setValue(BillPaymentConstants.SER_TYP_VIEW_MY_BILLER);
		screenData.addField(field1);

		FieldDataDTO field2 = new FieldDataDTO();
		field2.setValue(BillPaymentConstants.SUB_SER_TYP_VIEW_MY_BILLER);
		field2.setFieldId(AuditConstant.TXNSUBTYP);
		screenData.addField(field2);
	    }

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
