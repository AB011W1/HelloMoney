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

import java.util.Calendar;
import java.util.Date;

import com.barclays.bmg.audit.dto.FieldDataDTO;
import com.barclays.bmg.audit.dto.ScreenDataDTO;
import com.barclays.bmg.audit.dto.TransactionAuditDTO;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.operation.request.statement.StatementExecuteOperationRequest;
import com.barclays.bmg.operation.response.statement.StatmentExecuteOperationResponse;
import com.barclays.bmg.service.utils.BMGFormatUtils;
import com.barclays.bmg.utils.DateTimeUtil;
import com.ibm.icu.text.SimpleDateFormat;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-core-auditing</b> </br> The file name is
 * <b>BillPaymentEnquiryAuditBuilder.java</b> </br> Description is <b>V 1.2</b> </br> Updated Date is <b>Jun 6, 2013</b> </br>
 * ******************************************************
 *
 * @author e20037686 </br> * The Class BillPaymentEnquiryAuditBuilder created using JRE 1.6.0_10
 */
public class StatementRequestExecuteAuditBuilder extends AbstractTransactionAuditBuilder {
    /**
     * The Constant named "SCRNNAM_BP" is created.
     */
    private static final String SCRNNAM_BP = "STATEMENT_REQUEST";
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

	FieldDataDTO field6 = new FieldDataDTO();
	field6.setFieldId(AuditConstant.SERTYP);
	field6.setValue(BillPaymentConstants.SER_TYP_STATEMENT);
	screenData.addField(field6);

	FieldDataDTO field7 = new FieldDataDTO();
	field7.setValue(BillPaymentConstants.SUB_SER_TYP_STATEMENT);
	field7.setFieldId(AuditConstant.TXNSUBTYP);
	screenData.addField(field7);

	screenData.setScreenId(SCRNNAM_BP);

	Date txnDate = Calendar.getInstance().getTime();
	transactionAuditDTO.setTransactionDateTime(txnDate);

	StatementExecuteOperationRequest request = (StatementExecuteOperationRequest) args[0];

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

	    FieldDataDTO field12 = new FieldDataDTO();
	    field12.setFieldId(AuditConstant.OTHER_PARAMS);
	    if (request.getStatementRequestDTO() != null) {
		if (request.getStatementRequestDTO().getAccount() != null)
		    field12.setValue(request.getStatementRequestDTO().getAccount().toString());
	    } else
		field12.setValue("");
	    screenData.addField(field12);

	    FieldDataDTO field10 = new FieldDataDTO();
	    field10.setFieldId(AuditConstant.TXNON);
	    Calendar cal = DateTimeUtil.getCurrentBusinessCalendar(request.getContext());
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	    String date = sdf.format(cal.getTime());
	    field10.setValue(date);
	    screenData.addField(field10);

	    FieldDataDTO field5 = new FieldDataDTO();
	    field5.setFieldId(AuditConstant.ADDITIONAL_INFORMATION);
	    field5.setValue(AuditConstant.STATEMENT_PERIOD);
	    screenData.addField(field5);

	    FieldDataDTO field8 = new FieldDataDTO();
	    field8.setFieldId(AuditConstant.ADDITIONAL_ATTRIBUTE);
	    String statementPeriod = request.getStatementRequestDTO().getFromDate() + "-" + request.getStatementRequestDTO().getToDate();
	    field8.setValue(statementPeriod);
	    screenData.addField(field8);

	    String fromAcctNo = "";
	    try {

		if (request.getStatementRequestDTO() != null) {
		    if (request.getStatementRequestDTO().getAccount() != null) {
			fromAcctNo = request.getStatementRequestDTO().getAccount().getAccountNumber();
		    }
		}

		transactionAuditDTO.setFromAccount(fromAcctNo);
		FieldDataDTO field9 = new FieldDataDTO(AuditConstant.ACCOUNT_NUMBER, new BMGFormatUtils().maskAccount(fromAcctNo));
		screenData.addField(field9);

	    } catch (Exception ex) {

	    }

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

	FieldDataDTO field6 = new FieldDataDTO();
	field6.setFieldId(AuditConstant.SERTYP);
	field6.setValue(BillPaymentConstants.SER_TYP_STATEMENT);
	screenData.addField(field6);

	FieldDataDTO field7 = new FieldDataDTO();
	field7.setValue(BillPaymentConstants.SUB_SER_TYP_STATEMENT);
	field7.setFieldId(AuditConstant.TXNSUBTYP);
	screenData.addField(field7);

	screenData.setScreenId(SCRNNAM_BP);

	StatementExecuteOperationRequest request = (StatementExecuteOperationRequest) args[0];
	StatmentExecuteOperationResponse response = (StatmentExecuteOperationResponse) result;

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

		FieldDataDTO field5 = new FieldDataDTO();
		field5.setFieldId(AuditConstant.ADDITIONAL_INFORMATION);
		field5.setValue(AuditConstant.STATEMENT_PERIOD);
		screenData.addField(field5);

		FieldDataDTO field8 = new FieldDataDTO();
		field8.setFieldId(AuditConstant.ADDITIONAL_ATTRIBUTE);
		String statementPeriod = request.getStatementRequestDTO().getFromDate() + "-" + request.getStatementRequestDTO().getToDate();
		field8.setValue(statementPeriod);
		screenData.addField(field8);
	    }

	    String fromAcctNo = "";
	    try {

		if (request.getStatementRequestDTO() != null) {
		    if (request.getStatementRequestDTO().getAccount() != null) {
			fromAcctNo = request.getStatementRequestDTO().getAccount().getAccountNumber();
		    }
		}

		transactionAuditDTO.setFromAccount(fromAcctNo);
		FieldDataDTO field9 = new FieldDataDTO(AuditConstant.ACCOUNT_NUMBER, new BMGFormatUtils().maskAccount(fromAcctNo));
		screenData.addField(field9);

		FieldDataDTO field10 = new FieldDataDTO();
		field10.setFieldId(AuditConstant.CNTRYDTTM);
		Calendar cal = DateTimeUtil.getCurrentBusinessCalendar(request.getContext());
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String date = sdf.format(cal.getTime());
		field10.setValue(date);
		screenData.addField(field10);

	    } catch (Exception ex) {

	    }
	}

	if (response != null) {
	    if (response.isSuccess()) {

		FieldDataDTO field12 = new FieldDataDTO();
		field12.setFieldId(AuditConstant.OTHER_PARAMS);
		if (response.getActNo() != null)
		    field12.setValue(response.getActNo().toString());
		else
		    field12.setValue("");
		screenData.addField(field12);

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