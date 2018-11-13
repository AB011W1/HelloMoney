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

import com.barclays.bmg.audit.dto.FieldDataDTO;
import com.barclays.bmg.audit.dto.ScreenDataDTO;
import com.barclays.bmg.audit.dto.TransactionAuditDTO;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.TransactionHistoryDTO;
import com.barclays.bmg.operation.request.billpayment.ViewTxnHistoryDetailsOperationRequest;
import com.barclays.bmg.operation.response.billpayment.ViewTxnHistoryDetailsOperationResponse;
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
public class BillPaymentEnquiryAuditBuilder extends AbstractTransactionAuditBuilder {
    /**
     * The Constant named "SCRNNAM_BP" is created.
     */
    private static final String SCRNNAM_BP = "PMT_BP_ENQUIRY";

    /**
     * The Constant named "SCRNNAM_MTP" is created.
     */
    private static final String SCRNNAM_MTP = "PMT_BP_MTP_ENQUIRY";

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
	field6.setValue(BillPaymentConstants.SER_TYP_VIEW_LASTPAID_BILL);
	screenData.addField(field6);

	FieldDataDTO field7 = new FieldDataDTO();
	field7.setValue(BillPaymentConstants.SUB_SER_TYP_VIEW_LASTPAID_BILL);
	field7.setFieldId(AuditConstant.TXNSUBTYP);
	screenData.addField(field7);

	transactionAuditDTO.setReqRes(buildData(screenData));

	return new TransactionAuditDTO();
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

	ViewTxnHistoryDetailsOperationResponse response = (ViewTxnHistoryDetailsOperationResponse) result;
	ViewTxnHistoryDetailsOperationRequest request = (ViewTxnHistoryDetailsOperationRequest) args[0];

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
	field6.setFieldId(AuditConstant.SERTYP);
	field6.setValue(BillPaymentConstants.SER_TYP_VIEW_LASTPAID_BILL);
	screenData.addField(field6);

	FieldDataDTO field7 = new FieldDataDTO();
	field7.setValue(BillPaymentConstants.SUB_SER_TYP_VIEW_LASTPAID_BILL);
	field7.setFieldId(AuditConstant.TXNSUBTYP);
	screenData.addField(field7);

	// transactionAuditDTO.setFromAccount(transDto.getFromAccount().getAccountNumber());

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

	// transactionAuditDTO.setTransactionAmount(transDto.getAmount().getAmount().doubleValue());
	// transactionAuditDTO.setTransactionCurrency(transDto.getFromAccount().getCurrency());
	screenData.setScreenId(SCRNNAM_BP);
	if (response != null) {
	    BeneficiaryDTO beneficiary = response.getTransactionHistoryDTO().getBeneficiary();
	    String temp;
	    if (beneficiary != null) {
		FieldDataDTO field5 = new FieldDataDTO();
		field5.setFieldId(AuditConstant.MKDTOACCTNO);
		temp = beneficiary.getDestinationAccountNumber();

		field5.setValue(new BMGFormatUtils().maskAccount(temp != null ? temp : AuditConstant.WHITESPACE));
		screenData.addField(field5);

		// transactionAuditDTO.setToAccount(temp!=null ? temp : AuditConstant.WHITESPACE);

		FieldDataDTO field81 = new FieldDataDTO();
		field81.setFieldId(AuditConstant.OPERATOR);
		temp = beneficiary.getBillerName();
		field81.setValue(temp != null ? temp : AuditConstant.WHITESPACE);
		screenData.addField(field81);

		FieldDataDTO field91 = new FieldDataDTO();
		field91.setFieldId(AuditConstant.MOBNO);
		temp = beneficiary.getContactNumber();
		field91.setValue(temp != null ? temp : AuditConstant.WHITESPACE);
		screenData.addField(field91);

		FieldDataDTO field10 = new FieldDataDTO();
		field10.setFieldId(AuditConstant.BILLERNAM);
		temp = beneficiary.getBillerName();
		field10.setValue(temp != null ? temp : AuditConstant.WHITESPACE);
		screenData.addField(field10);

		FieldDataDTO field11 = new FieldDataDTO();
		field11.setFieldId(AuditConstant.BILLNO);
		temp = beneficiary.getBillRefNo();
		field11.setValue(temp != null ? temp : AuditConstant.WHITESPACE);
		screenData.addField(field11);

		FieldDataDTO field12 = new FieldDataDTO();
		field12.setFieldId(AuditConstant.BILLHOLDNAM);
		temp = beneficiary.getBeneficiaryName();
		field12.setValue(temp != null ? temp : AuditConstant.WHITESPACE);
		screenData.addField(field12);

		FieldDataDTO field13 = new FieldDataDTO();
		field13.setFieldId(AuditConstant.BILLHOLDADDR);
		field13.setValue((beneficiary.getDestinationAddressLine1() == null ? AuditConstant.WHITESPACE : beneficiary
			.getDestinationAddressLine1())
			+ (beneficiary.getDestinationAddressLine2() == null ? AuditConstant.WHITESPACE : AuditConstant.WHITESPACE
				+ beneficiary.getDestinationAddressLine2())
			+ (beneficiary.getDestinationAddressLine3() == null ? AuditConstant.WHITESPACE : AuditConstant.WHITESPACE
				+ beneficiary.getDestinationAddressLine3()));
		screenData.addField(field13);

		FieldDataDTO field18 = new FieldDataDTO();
		field18.setFieldId(AuditConstant.ATTRIBUTE_NAME1);
		field18.setValue(AuditConstant.BILLERNAM);
		screenData.addField(field18);

		// FieldDataDTO field19 = new FieldDataDTO();
		temp = beneficiary.getBillerName();
		FieldDataDTO field19 = new FieldDataDTO(AuditConstant.ATTRIBUTE_VALUE1, temp == null ? AuditConstant.WHITESPACE : temp);
		screenData.addField(field19);

	    }

	    TransactionHistoryDTO transDto = response.getTransactionHistoryDTO();
	    String txnType = transDto.getTransactionType();

	    if (BillPaymentConstants.TXN_FACADE_RTN_TYPE_BILL_PAYMENT.equals(txnType))
		screenData.setScreenId(SCRNNAM_BP);
	    else if (BillPaymentConstants.TXN_FACADE_RTN_TYPE_MOBILE_TOPUP.equals(txnType) || AuditConstant.MWALLET.equals(txnType))
		screenData.setScreenId(SCRNNAM_MTP);
	    else
		screenData.setScreenId(SCRNNAM_BP);

	    temp = request.getTransactionRefNo();
	    transactionAuditDTO.setTransactionRefNo(temp != null ? temp : AuditConstant.WHITESPACE);
	    if (response.isSuccess()) {
		transactionAuditDTO.setTransactionStatus(AuditConstant.TXN_STATUS_SUCCESS);
	    } else {
		FieldDataDTO field17 = new FieldDataDTO();
		field17.setFieldId(AuditConstant.ERRCD);
		temp = response.getResCde();
		field17.setValue(temp != null ? temp : AuditConstant.WHITESPACE);
		screenData.addField(field17);

		transactionAuditDTO.setTransactionStatus(AuditConstant.TXN_STATUS_FAILURE);
		temp = response.getResMsg();
		transactionAuditDTO.setErrorMessage(temp != null ? temp : AuditConstant.WHITESPACE);

		FieldDataDTO field16 = new FieldDataDTO();
		field16.setFieldId(AuditConstant.FAILURE_REASON);
		field16.setValue(response.getResMsg());
		screenData.addField(field16);

		FieldDataDTO field18 = new FieldDataDTO();
		field18.setFieldId(AuditConstant.ERRCD);
		field18.setValue(response.getResCde());
		screenData.addField(field18);
	    }

	} else {
	    transactionAuditDTO.setTransactionStatus(AuditConstant.TXN_STATUS_FAILURE);
	}
	transactionAuditDTO.setReqRes(buildData(screenData));

	return transactionAuditDTO;
    }
}