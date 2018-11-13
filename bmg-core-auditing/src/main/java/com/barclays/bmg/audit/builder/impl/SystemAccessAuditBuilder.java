/*
 * Copyright (c) 2013 Barclays Bank Plc, All Rights Reserved.
 *
 * This code is confidential to Barclays Bank Plc and shall not be disclosed
 * outside the Bank without the prior written permission of the Director of
 * CIO
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favour of Barclays Bank
 * Plc.
 */
package com.barclays.bmg.audit.builder.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.barclays.bmg.audit.dto.FieldDataDTO;
import com.barclays.bmg.audit.dto.ScreenDataDTO;
import com.barclays.bmg.audit.dto.TransactionAuditDTO;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.operation.request.SessionSummaryOperationRequest;
import com.barclays.bmg.operation.response.SessionSummaryOperationResponse;
import com.barclays.bmg.service.sessionactivity.dto.SessionSummaryDTO;
import com.barclays.bmg.utils.DateTimeUtil;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-core-auditing</b> </br> The file name is
 * <b>SystemAccessAuditBuilder.java</b> </br> Description is <b>V 1.2</b> </br> Updated Date is <b>Jun 6, 2013</b> </br>
 * ******************************************************
 * 
 * @author e20037686 </br> * The Class SystemAccessAuditBuilder created using JRE 1.6.0_10
 */
public class SystemAccessAuditBuilder extends AbstractTransactionAuditBuilder {

    /**
     * The Constant named "PMT_SYSTEM_ACCESS" is created.
     */
    private static final String PMT_SYSTEM_ACCESS = "PMT_SYSTEM_ACCESS";
    private static final String DATE_FORMAT = "dd/MM/yy HH:mm:ss";

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.bmg.audit.builder.BMGTransactionAuditBuilder#buildFromRequest(java.lang.Object[], java.lang.Object)
     */
    @Override
    public TransactionAuditDTO buildFromRequest(Object[] args, Object result) {
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	ScreenDataDTO screenData = new ScreenDataDTO();
	screenData.setScreenId(PMT_SYSTEM_ACCESS);

	SessionSummaryOperationRequest request = (SessionSummaryOperationRequest) args[0];
	Context context = null;
	FieldDataDTO fieldDataDTO;
	String temp;
	try {
	    context = request.getContext();

	    FieldDataDTO field8 = new FieldDataDTO();
	    field8.setFieldId(AuditConstant.CNTRYDTTM);
	    Calendar cal = DateTimeUtil.getCurrentBusinessCalendar(request.getContext());
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	    String date = sdf.format(cal.getTime());
	    field8.setValue(date);
	    screenData.addField(field8);

	    fieldDataDTO = new FieldDataDTO();
	    fieldDataDTO.setFieldId(AuditConstant.MOBNO);
	    temp = context.getMobilePhone();
	    fieldDataDTO.setValue(temp != null ? temp : AuditConstant.WHITESPACE);
	    screenData.addField(fieldDataDTO);

	    fieldDataDTO = new FieldDataDTO();
	    fieldDataDTO.setFieldId(AuditConstant.CUSTID);
	    temp = context.getCustomerId();
	    fieldDataDTO.setValue(temp != null ? temp : AuditConstant.WHITESPACE);
	    screenData.addField(fieldDataDTO);

	    fieldDataDTO = new FieldDataDTO();
	    fieldDataDTO.setFieldId(AuditConstant.SERNAM);
	    temp = context.getActivityId();
	    fieldDataDTO.setValue(temp != null ? temp : AuditConstant.WHITESPACE);
	    screenData.addField(fieldDataDTO);

	    fieldDataDTO = new FieldDataDTO();
	    fieldDataDTO.setFieldId(AuditConstant.CUSTNAME);
	    temp = context.getFullName();
	    fieldDataDTO.setValue(temp != null ? temp : AuditConstant.WHITESPACE);
	    screenData.addField(fieldDataDTO);
	} catch (Exception e) {
	}
	transactionAuditDTO.setReqRes(buildData(screenData));
	return transactionAuditDTO;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.bmg.audit.builder.BMGTransactionAuditBuilder#buildFromResponse(java.lang.Object[], java.lang.Object)
     */
    @Override
    public TransactionAuditDTO buildFromResponse(Object[] args, Object result) {
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	ScreenDataDTO screenData = new ScreenDataDTO();
	screenData.setScreenId(PMT_SYSTEM_ACCESS);

	Context context = null;
	FieldDataDTO fieldDataDTO;
	String temp;

	context = ((SessionSummaryOperationRequest) args[0]).getContext();

	if (context != null) {

	    FieldDataDTO field8 = new FieldDataDTO();
	    field8.setFieldId(AuditConstant.CNTRYDTTM);
	    Calendar cal = DateTimeUtil.getCurrentBusinessCalendar(context);
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	    String date = sdf.format(cal.getTime());
	    field8.setValue(date);
	    screenData.addField(field8);

	    fieldDataDTO = new FieldDataDTO();
	    fieldDataDTO.setFieldId(AuditConstant.MOBNO);
	    temp = context.getMobilePhone();
	    fieldDataDTO.setValue(temp != null ? temp : AuditConstant.WHITESPACE);
	    screenData.addField(fieldDataDTO);

	    fieldDataDTO = new FieldDataDTO();
	    fieldDataDTO.setFieldId(AuditConstant.CUSTID);
	    temp = context.getCustomerId();
	    fieldDataDTO.setValue(temp != null ? temp : AuditConstant.WHITESPACE);
	    screenData.addField(fieldDataDTO);

	    fieldDataDTO = new FieldDataDTO();
	    fieldDataDTO.setFieldId(AuditConstant.SERNAM);
	    temp = context.getActivityId();
	    fieldDataDTO.setValue(temp != null ? temp : AuditConstant.WHITESPACE);
	    screenData.addField(fieldDataDTO);

	    fieldDataDTO = new FieldDataDTO();
	    fieldDataDTO.setFieldId(AuditConstant.CUSTNAME);
	    temp = context.getFullName();
	    fieldDataDTO.setValue(temp != null ? temp : AuditConstant.WHITESPACE);
	    screenData.addField(fieldDataDTO);
	}

	SessionSummaryOperationResponse response = (SessionSummaryOperationResponse) result;
	if (response != null) {
	    if (response.isSuccess()) {
		SessionSummaryDTO dto = response.getSessionSummaryDTO();
		if (dto != null) {
		    DateFormat dateFormat = new SimpleDateFormat(AuditConstant.DTFORMAT);
		    fieldDataDTO = new FieldDataDTO();
		    fieldDataDTO.setFieldId(AuditConstant.LOGINTM);
		    fieldDataDTO.setValue(dateFormat.format(dto.getLoginTime()));
		    screenData.addField(fieldDataDTO);

		    fieldDataDTO = new FieldDataDTO();
		    fieldDataDTO.setFieldId(AuditConstant.LOGOTTM);
		    fieldDataDTO.setValue(dateFormat.format(dto.getLogoutTime()));
		    screenData.addField(fieldDataDTO);
		}
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