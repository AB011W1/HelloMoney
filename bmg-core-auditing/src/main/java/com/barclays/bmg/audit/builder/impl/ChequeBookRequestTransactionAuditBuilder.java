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
import com.barclays.bmg.chequebook.operation.request.ChequeBookExecuteOperationRequest;
import com.barclays.bmg.chequebook.operation.response.ChequeBookExecuteOperationResponse;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.ChequeBookRequestDTO;
import com.barclays.bmg.service.utils.BMGFormatUtils;
import com.barclays.bmg.utils.DateTimeUtil;
import com.ibm.icu.text.SimpleDateFormat;

/**
 * ****************** Revision History ********************** </br> Project Name is <b>bmg-core-auditing</b> </br> The file name is
 * <b>ChequeBookRequestTransactionAuditBuilder.java</b> </br> Description is <b>V 1.2</b> </br> Updated Date is <b>Jun 6, 2013</b> </br>
 * ******************************************************
 * 
 * @author e20037686 </br> * The Class ChequeBookRequestTransactionAuditBuilder created using JRE 1.6.0_10
 */

public class ChequeBookRequestTransactionAuditBuilder extends AbstractTransactionAuditBuilder {
    /**
     * The Constant named "SCRNNAM" is created.
     */
    private final static String SCRNNAM = "ACT_CHK_BOOK_REQUEST_INPUT";
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

	screenData.setScreenId(SCRNNAM);

	FieldDataDTO field8 = new FieldDataDTO();
	field8.setFieldId(AuditConstant.SERTYP);
	field8.setValue(AuditConstant.SER_TYP_CHEQUE_BOOK);
	screenData.addField(field8);

	FieldDataDTO field9 = new FieldDataDTO();
	field9.setValue(AuditConstant.SUB_SER_TYP_CHEQUE_BOOK);
	field9.setFieldId(AuditConstant.TXNSUBTYP);
	screenData.addField(field9);

	Date txnDate = Calendar.getInstance().getTime();
	transactionAuditDTO.setTransactionDateTime(txnDate);

	ChequeBookExecuteOperationRequest request = (ChequeBookExecuteOperationRequest) args[0];
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

	    ChequeBookRequestDTO dto = request.getChequeBookRequestDTO();

	    String bkTyp = null;
	    String bkSize = null;
	    String actNo = null;
	    String brnNam = null;
	    String temp;
	    if (dto != null) {
		bkTyp = dto.getBookType();
		bkSize = dto.getBookSize();
		actNo = dto.getAccount().getAccountNumber();
		brnNam = dto.getBranchName();
	    }

	    FieldDataDTO fieldDataDTO = new FieldDataDTO();
	    fieldDataDTO.setFieldId(AuditConstant.BKTYPE);
	    fieldDataDTO.setValue(bkTyp != null ? bkTyp : AuditConstant.WHITESPACE);
	    screenData.addField(fieldDataDTO);

	    fieldDataDTO = new FieldDataDTO();
	    fieldDataDTO.setFieldId(AuditConstant.BKSIZE);
	    fieldDataDTO.setValue(bkSize != null ? bkSize : AuditConstant.WHITESPACE);
	    screenData.addField(fieldDataDTO);

	    fieldDataDTO = new FieldDataDTO();
	    fieldDataDTO.setFieldId(AuditConstant.BRNCHNAM);
	    fieldDataDTO.setValue(brnNam != null ? brnNam : AuditConstant.WHITESPACE);
	    screenData.addField(fieldDataDTO);

	    fieldDataDTO = new FieldDataDTO();
	    fieldDataDTO.setFieldId(AuditConstant.REQDT);
	    Calendar cal = DateTimeUtil.getCurrentBusinessCalendar(request.getContext());
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	    String date = sdf.format(cal.getTime());
	    fieldDataDTO.setValue(date);
	    screenData.addField(fieldDataDTO);

	    fieldDataDTO = new FieldDataDTO();
	    fieldDataDTO.setFieldId(AuditConstant.MKDFRMACCTNO);
	    fieldDataDTO.setValue(new BMGFormatUtils().maskAccount(actNo));
	    screenData.addField(fieldDataDTO);

	    FieldDataDTO field10 = new FieldDataDTO();
	    field10.setFieldId(AuditConstant.CNTRYDTTM);

	    date = sdf.format(cal.getTime());
	    field10.setValue(date);
	    screenData.addField(field10);

	    transactionAuditDTO.setFromAccount(actNo);

	    FieldDataDTO field12 = new FieldDataDTO();
	    field12.setFieldId(AuditConstant.ADDITIONAL_INFORMATION);
	    field12.setValue(AuditConstant.BRANCH_CODE);
	    screenData.addField(field12);

	    FieldDataDTO field23 = new FieldDataDTO();
	    field23.setFieldId(AuditConstant.ADDITIONAL_ATTRIBUTE);
	    field23.setValue(request.getChequeBookRequestDTO().getBranchCode());
	    screenData.addField(field23);

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

	screenData.setScreenId(SCRNNAM);

	FieldDataDTO field8 = new FieldDataDTO();
	field8.setFieldId(AuditConstant.SERTYP);
	field8.setValue(AuditConstant.SER_TYP_CHEQUE_BOOK);
	screenData.addField(field8);

	FieldDataDTO field9 = new FieldDataDTO();
	field9.setValue(AuditConstant.SUB_SER_TYP_CHEQUE_BOOK);
	field9.setFieldId(AuditConstant.TXNSUBTYP);
	screenData.addField(field9);

	Date txnDate = Calendar.getInstance().getTime();
	transactionAuditDTO.setTransactionDateTime(txnDate);

	ChequeBookExecuteOperationResponse response = (ChequeBookExecuteOperationResponse) result;
	ChequeBookExecuteOperationRequest request = (ChequeBookExecuteOperationRequest) args[0];

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

	    ChequeBookRequestDTO dto = request.getChequeBookRequestDTO();
	    String bkTyp = null;
	    String bkSize = null;
	    String actNo = null;
	    String brnNam = null;
	    String temp;
	    if (dto != null) {
		bkTyp = dto.getBookType();
		bkSize = dto.getBookSize();
		actNo = dto.getAccount().getAccountNumber();
		brnNam = dto.getBranchName();
	    }
	    FieldDataDTO fieldDataDTO = new FieldDataDTO();
	    fieldDataDTO.setFieldId(AuditConstant.BKTYPE);
	    fieldDataDTO.setValue(bkTyp != null ? bkTyp : AuditConstant.WHITESPACE);
	    screenData.addField(fieldDataDTO);

	    fieldDataDTO = new FieldDataDTO();
	    fieldDataDTO.setFieldId(AuditConstant.BKSIZE);
	    fieldDataDTO.setValue(bkSize != null ? bkSize : AuditConstant.WHITESPACE);
	    screenData.addField(fieldDataDTO);

	    fieldDataDTO = new FieldDataDTO();
	    fieldDataDTO.setFieldId(AuditConstant.BRNCHNAM);
	    fieldDataDTO.setValue(brnNam != null ? brnNam : AuditConstant.WHITESPACE);
	    screenData.addField(fieldDataDTO);

	    fieldDataDTO = new FieldDataDTO();
	    fieldDataDTO.setFieldId(AuditConstant.MKDFRMACCTNO);
	    fieldDataDTO.setValue(new BMGFormatUtils().maskAccount(actNo));
	    screenData.addField(fieldDataDTO);

	    FieldDataDTO field10 = new FieldDataDTO();
	    field10.setFieldId(AuditConstant.CNTRYDTTM);
	    Calendar cal = DateTimeUtil.getCurrentBusinessCalendar(request.getContext());
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	    String date = sdf.format(cal.getTime());
	    field10.setValue(date);
	    screenData.addField(field10);

	    FieldDataDTO field12 = new FieldDataDTO();
	    field12.setFieldId(AuditConstant.ADDITIONAL_INFORMATION);
	    field12.setValue(AuditConstant.BRANCH_CODE);
	    screenData.addField(field12);

	    FieldDataDTO field23 = new FieldDataDTO();
	    field23.setFieldId(AuditConstant.ADDITIONAL_ATTRIBUTE);
	    field23.setValue(request.getChequeBookRequestDTO().getBranchCode());
	    screenData.addField(field23);

	    transactionAuditDTO.setFromAccount(actNo);

	}

	if (response != null) {
	    transactionAuditDTO.setTransactionRefNo(response.getTxnRefNo());
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