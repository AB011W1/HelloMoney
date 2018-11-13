package com.barclays.bmg.audit.builder.impl;

import java.util.Calendar;
import java.util.Date;

import com.barclays.bmg.audit.dto.FieldDataDTO;
import com.barclays.bmg.audit.dto.ScreenDataDTO;
import com.barclays.bmg.audit.dto.TransactionAuditDTO;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.operation.request.UpdateLanguagePrefOperationRequest;
import com.barclays.bmg.operation.response.UpdateLanguagePrefOperationResponse;
import com.barclays.bmg.utils.DateTimeUtil;
import com.ibm.icu.text.SimpleDateFormat;

public class UpdateLanguagePreferenceAuditBuilder extends AbstractTransactionAuditBuilder {

    private static final String SCRNNAM = "LANG_PREF";
    private static final String DATE_FORMAT = "dd/MM/yy HH:mm:ss";

    @Override
    public TransactionAuditDTO buildFromRequest(Object[] args, Object result) {

	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	ScreenDataDTO screenData = new ScreenDataDTO();

	screenData.setScreenId(SCRNNAM);

	FieldDataDTO field1 = new FieldDataDTO();
	field1.setFieldId(AuditConstant.SERTYP);
	field1.setValue(AuditConstant.SER_TYP_LANG_PREF);
	screenData.addField(field1);

	FieldDataDTO field2 = new FieldDataDTO();
	field2.setFieldId(AuditConstant.TXNSUBTYP);
	field2.setValue(AuditConstant.SUB_SER_TYP_LANG_PREF);
	screenData.addField(field2);

	UpdateLanguagePrefOperationRequest request = (UpdateLanguagePrefOperationRequest) args[0];

	Date txnDate = Calendar.getInstance().getTime();
	transactionAuditDTO.setTransactionDateTime(txnDate);

	if (request != null) {

	    Context context = request.getContext();
	    if (context != null) {
		FieldDataDTO field3 = new FieldDataDTO();
		field3.setFieldId(AuditConstant.CUSTID);
		field3.setValue(context.getCustomerId());
		screenData.addField(field3);

		FieldDataDTO field4 = new FieldDataDTO();
		field4.setFieldId(AuditConstant.CUSTNAME);
		field4.setValue(context.getFullName());
		screenData.addField(field4);

		FieldDataDTO field5 = new FieldDataDTO();
		field5.setFieldId(AuditConstant.SERNAM);
		field5.setValue(context.getActivityId());
		screenData.addField(field5);

		FieldDataDTO field6 = new FieldDataDTO();
		field6.setFieldId(AuditConstant.SRCMOBNO);
		field6.setValue(context.getMobilePhone());
		screenData.addField(field6);

		FieldDataDTO field9 = new FieldDataDTO();
		field9.setFieldId(AuditConstant.LANGUAGE);
		if (request.getPrefLang() != null)
		    field9.setValue(request.getPrefLang().toUpperCase());
		else
		    field9.setValue("");
		screenData.addField(field9);

		FieldDataDTO field10 = new FieldDataDTO();
		field10.setFieldId(AuditConstant.TXNON);
		Calendar cal = DateTimeUtil.getCurrentBusinessCalendar(request.getContext());
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String date = sdf.format(cal.getTime());
		field10.setValue(date);
		screenData.addField(field10);

		FieldDataDTO field7 = new FieldDataDTO();
		field7.setFieldId(AuditConstant.ATTRIBUTE_NAME1);
		field7.setValue(AuditConstant.LANGUAGE);
		screenData.addField(field7);

		FieldDataDTO field8 = new FieldDataDTO();
		field8.setFieldId(AuditConstant.ATTRIBUTE_VALUE1);
		if (request.getPrefLang() != null)
		    field8.setValue(request.getPrefLang().toUpperCase());
		else
		    field8.setValue("");
		screenData.addField(field8);

	    }

	}

	transactionAuditDTO.setReqRes(buildData(screenData));

	return transactionAuditDTO;
    }

    @Override
    public TransactionAuditDTO buildFromResponse(Object[] args, Object result) {
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	ScreenDataDTO screenData = new ScreenDataDTO();

	screenData.setScreenId(SCRNNAM);

	FieldDataDTO field1 = new FieldDataDTO();
	field1.setFieldId(AuditConstant.SERTYP);
	field1.setValue(AuditConstant.SER_TYP_LANG_PREF);
	screenData.addField(field1);

	FieldDataDTO field2 = new FieldDataDTO();
	field2.setFieldId(AuditConstant.TXNSUBTYP);
	field2.setValue(AuditConstant.SUB_SER_TYP_LANG_PREF);
	screenData.addField(field2);

	UpdateLanguagePrefOperationRequest request = (UpdateLanguagePrefOperationRequest) args[0];
	UpdateLanguagePrefOperationResponse response = (UpdateLanguagePrefOperationResponse) result;

	Date txnDate = Calendar.getInstance().getTime();
	transactionAuditDTO.setTransactionDateTime(txnDate);

	if (request != null) {

	    Context context = request.getContext();
	    if (context != null) {
		FieldDataDTO field3 = new FieldDataDTO();
		field3.setFieldId(AuditConstant.CUSTID);
		field3.setValue(context.getCustomerId());
		screenData.addField(field3);

		FieldDataDTO field4 = new FieldDataDTO();
		field4.setFieldId(AuditConstant.CUSTNAME);
		field4.setValue(context.getFullName());
		screenData.addField(field4);

		FieldDataDTO field5 = new FieldDataDTO();
		field5.setFieldId(AuditConstant.SERNAM);
		field5.setValue(context.getActivityId());
		screenData.addField(field5);

		FieldDataDTO field6 = new FieldDataDTO();
		field6.setFieldId(AuditConstant.SRCMOBNO);
		field6.setValue(context.getMobilePhone());
		screenData.addField(field6);
	    }

	    FieldDataDTO field9 = new FieldDataDTO();
	    field9.setFieldId(AuditConstant.LANGUAGE);
	    if (request.getPrefLang() != null)
		field9.setValue(request.getPrefLang().toUpperCase());
	    else
		field9.setValue("");
	    screenData.addField(field9);

	    FieldDataDTO field10 = new FieldDataDTO();
	    field10.setFieldId(AuditConstant.TXNON);
	    Calendar cal = DateTimeUtil.getCurrentBusinessCalendar(request.getContext());
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	    String date = sdf.format(cal.getTime());
	    field10.setValue(date);
	    screenData.addField(field10);

	    FieldDataDTO field7 = new FieldDataDTO();
	    field7.setFieldId(AuditConstant.ATTRIBUTE_NAME1);
	    field7.setValue(AuditConstant.LANGUAGE);
	    screenData.addField(field7);

	    FieldDataDTO field8 = new FieldDataDTO();
	    field8.setFieldId(AuditConstant.ATTRIBUTE_VALUE1);
	    if (request.getPrefLang() != null)
		field8.setValue(request.getPrefLang().toUpperCase());
	    else
		field8.setValue("");
	    screenData.addField(field8);

	}

	if (response != null) {
	    if (response.isSuccess()) {
		transactionAuditDTO.setTransactionStatus(AuditConstant.TXN_STATUS_SUCCESS);

	    } else {
		transactionAuditDTO.setTransactionStatus(AuditConstant.TXN_STATUS_FAILURE);
		transactionAuditDTO.setErrorMessage(response.getResMsg());

		FieldDataDTO field11 = new FieldDataDTO();
		field11.setFieldId(AuditConstant.FAILURE_REASON);
		field11.setValue(response.getResMsg());
		screenData.addField(field11);

		FieldDataDTO field12 = new FieldDataDTO();
		field12.setFieldId(AuditConstant.ERRCD);
		field12.setValue(response.getResCde());
		screenData.addField(field12);
	    }
	} else {
	    transactionAuditDTO.setTransactionStatus(AuditConstant.TXN_STATUS_FAILURE);
	}
	transactionAuditDTO.setReqRes(buildData(screenData));

	return transactionAuditDTO;
    }

}
