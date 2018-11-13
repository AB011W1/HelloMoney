package com.barclays.bmg.audit.builder.impl;

import java.util.Calendar;
import java.util.Date;

import com.barclays.bmg.audit.dto.FieldDataDTO;
import com.barclays.bmg.audit.dto.ScreenDataDTO;
import com.barclays.bmg.audit.dto.TransactionAuditDTO;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.ussd.auth.service.request.SMSDetailsServiceRequest;
import com.barclays.bmg.utils.DateTimeUtil;
import com.ibm.icu.text.SimpleDateFormat;

public class SMSDetailsAuditBuilder extends AbstractTransactionAuditBuilder {

    private static final String DATE_FORMAT = "dd/MM/yy HH:mm:ss";
    private final static String SCRNNAME = "SMS";

    @Override
    public TransactionAuditDTO buildFromRequest(Object[] args, Object result) {

	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	ScreenDataDTO screenData = new ScreenDataDTO();

	FieldDataDTO field6 = new FieldDataDTO();
	field6.setFieldId(AuditConstant.SERTYP);
	field6.setValue(BillPaymentConstants.SER_TYP_SMS);
	screenData.addField(field6);

	FieldDataDTO field7 = new FieldDataDTO();
	field7.setValue(BillPaymentConstants.SUB_SER_TYP_SMS);
	field7.setFieldId(AuditConstant.TXNSUBTYP);
	screenData.addField(field7);

	SMSDetailsServiceRequest request = (SMSDetailsServiceRequest) args[0];

	Date txnDate = Calendar.getInstance().getTime();
	transactionAuditDTO.setTransactionDateTime(txnDate);

	screenData.setScreenId(SCRNNAME);

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

	}

	transactionAuditDTO.setReqRes(buildData(screenData));
	return transactionAuditDTO;
    }

    @Override
    public TransactionAuditDTO buildFromResponse(Object[] args, Object result) {

	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	ScreenDataDTO screenData = new ScreenDataDTO();

	FieldDataDTO field6 = new FieldDataDTO();
	field6.setFieldId(AuditConstant.SERTYP);
	field6.setValue(BillPaymentConstants.SER_TYP_SMS);
	screenData.addField(field6);

	FieldDataDTO field7 = new FieldDataDTO();
	field7.setValue(BillPaymentConstants.SUB_SER_TYP_SMS);
	field7.setFieldId(AuditConstant.TXNSUBTYP);
	screenData.addField(field7);

	screenData.setScreenId(SCRNNAME);

	SMSDetailsServiceRequest request = (SMSDetailsServiceRequest) args[0];

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

	    transactionAuditDTO.setTransactionStatus(AuditConstant.TXN_SMS_STATUS);
	} else {
	    transactionAuditDTO.setTransactionStatus(AuditConstant.TXN_STATUS_FAILURE);
	}

	transactionAuditDTO.setReqRes(buildData(screenData));

	return transactionAuditDTO;
    }

}
