package com.barclays.bmg.audit.builder.impl;

import java.util.Calendar;
import java.util.Date;

import com.barclays.bmg.audit.dto.FieldDataDTO;
import com.barclays.bmg.audit.dto.ScreenDataDTO;
import com.barclays.bmg.audit.dto.TransactionAuditDTO;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.operation.request.billpayment.SearchTransactionHistoryOperationRequest;
import com.barclays.bmg.operation.response.billpayment.SearchTransactionHistoryOperationResponse;
import com.barclays.bmg.utils.DateTimeUtil;
import com.ibm.icu.text.SimpleDateFormat;

public class SearchTransactionHistoryAuditBuilder extends AbstractTransactionAuditBuilder {

    private static final String SCRNNAM = "TXN_HISTORY";
    private static final String DATE_FORMAT = "dd/MM/yy HH:mm:ss";

    @Override
    public TransactionAuditDTO buildFromRequest(Object[] args, Object result) {

	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	ScreenDataDTO screenData = new ScreenDataDTO();

	screenData.setScreenId(SCRNNAM);

	FieldDataDTO field6 = new FieldDataDTO();
	field6.setFieldId(AuditConstant.SERTYP);
	field6.setValue(BillPaymentConstants.SER_TYP_VIEW_LASTPAID_BILL);
	screenData.addField(field6);

	FieldDataDTO field7 = new FieldDataDTO();
	field7.setValue(BillPaymentConstants.SUB_SER_TYP_VIEW_LASTPAID_BILL);
	field7.setFieldId(AuditConstant.TXNSUBTYP);
	screenData.addField(field7);

	Date txnDate1 = Calendar.getInstance().getTime();
	transactionAuditDTO.setTransactionDateTime(txnDate1);

	SearchTransactionHistoryOperationRequest request = (SearchTransactionHistoryOperationRequest) args[0];

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

	    FieldDataDTO field8 = new FieldDataDTO();
	    field8.setFieldId(AuditConstant.TXNON);
	    Calendar cal = DateTimeUtil.getCurrentBusinessCalendar(request.getContext());
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	    String date = sdf.format(cal.getTime());
	    field8.setValue(date);
	    screenData.addField(field8);

	    FieldDataDTO field9 = new FieldDataDTO();
	    field9.setFieldId(AuditConstant.CNTRYDTTM);
	    date = sdf.format(cal.getTime());
	    field9.setValue(date);
	    screenData.addField(field9);

	    // transactionAuditDTO.setFromAccount(request.getTransactionHistoryDTO().getFromAccount().getAccountNumber());

	    // transactionAuditDTO.setToAccount(request.getTransactionHistoryDTO().getBeneficiary().getDestinationAccountNumber());
	}

	transactionAuditDTO.setReqRes(buildData(screenData));
	return transactionAuditDTO;
    }

    @Override
    public TransactionAuditDTO buildFromResponse(Object[] args, Object result) {
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	ScreenDataDTO screenData = new ScreenDataDTO();

	screenData.setScreenId(SCRNNAM);

	FieldDataDTO field6 = new FieldDataDTO();
	field6.setFieldId(AuditConstant.SERTYP);
	field6.setValue(BillPaymentConstants.SER_TYP_VIEW_LASTPAID_BILL);
	screenData.addField(field6);

	FieldDataDTO field7 = new FieldDataDTO();
	field7.setValue(BillPaymentConstants.SUB_SER_TYP_VIEW_LASTPAID_BILL);
	field7.setFieldId(AuditConstant.TXNSUBTYP);
	screenData.addField(field7);

	Date txnDate = Calendar.getInstance().getTime();
	transactionAuditDTO.setTransactionDateTime(txnDate);

	SearchTransactionHistoryOperationRequest request = (SearchTransactionHistoryOperationRequest) args[0];
	SearchTransactionHistoryOperationResponse response = (SearchTransactionHistoryOperationResponse) result;

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

	    FieldDataDTO field8 = new FieldDataDTO();
	    field8.setFieldId(AuditConstant.TXNON);
	    Calendar cal = DateTimeUtil.getCurrentBusinessCalendar(request.getContext());
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	    String date = sdf.format(cal.getTime());
	    field8.setValue(date);
	    screenData.addField(field8);

	    FieldDataDTO field9 = new FieldDataDTO();
	    field9.setFieldId(AuditConstant.CNTRYDTTM);
	    date = sdf.format(cal.getTime());
	    field9.setValue(date);
	    screenData.addField(field9);

	    // transactionAuditDTO.setFromAccount(request.getTransactionHistoryDTO().getFromAccount().getAccountNumber());

	    // transactionAuditDTO.setToAccount(request.getTransactionHistoryDTO().getBeneficiary().getDestinationAccountNumber());
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
