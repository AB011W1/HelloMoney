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

import org.apache.log4j.Logger;

import com.barclays.bmg.audit.dto.FieldDataDTO;
import com.barclays.bmg.audit.dto.ScreenDataDTO;
import com.barclays.bmg.audit.dto.TransactionAuditDTO;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.operation.request.billpayment.MakeBillPaymentOperationRequest;
import com.barclays.bmg.operation.response.billpayment.MakeBillPaymentOperationResponse;
import com.barclays.bmg.service.utils.BMGFormatUtils;
import com.barclays.bmg.utils.DateTimeUtil;
import com.ibm.icu.text.SimpleDateFormat;

public class BillPaymentTransactionAuditBuilder extends AbstractTransactionAuditBuilder {
    private final static String SCRNNAME_BP = "PMT_BP_CONFIRM";
    private final static String SCRNNAME_MTP = "PMT_BP_MTP_CONFIRM";
    private static final String DATE_FORMAT = "dd/MM/yy HH:mm:ss";
    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(BillPaymentTransactionAuditBuilder.class);
    /*
     * (non-Javadoc)
     *
     * @see com.barclays.bmg.audit.builder.BMGTransactionAuditBuilder#buildFromRequest(java.lang.Object[], java.lang.Object)
     */
    @Override
    public TransactionAuditDTO buildFromRequest(Object[] args, Object result) {
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	ScreenDataDTO screenData = new ScreenDataDTO();
	MakeBillPaymentOperationRequest request = (MakeBillPaymentOperationRequest) args[0];

	TransactionDTO transDto = request.getTransactionDTO();
	String fromAcctNo = "";
	if (transDto.getSourceAcct() != null) {
			if (transDto.getSourceAcct() instanceof CreditCardAccountDTO) {
				CreditCardAccountDTO creditCardDTO = (CreditCardAccountDTO) transDto
						.getSourceAcct();
				fromAcctNo = creditCardDTO.getPrimary().getCardNumber();
			} else {
				fromAcctNo = transDto.getSourceAcct().getAccountNumber();
			}
		}
	String txnType = transDto.getTxnType();
	String currency = transDto.getSourceAcct().getCurrency();
	Double txnAmount = transDto.getTxnAmt().getAmount().doubleValue();

	FieldDataDTO field5 = new FieldDataDTO();
	FieldDataDTO field11 = new FieldDataDTO();

	Context context = request.getContext();
	if (context != null) {
	    FieldDataDTO field4 = new FieldDataDTO();
	    field4.setFieldId(AuditConstant.CUSTID);
	    field4.setValue(context.getCustomerId());
	    screenData.addField(field4);

	    field4 = new FieldDataDTO(AuditConstant.CUSTNAME, context.getFullName());
	    screenData.addField(field4);

	    field4 = new FieldDataDTO(AuditConstant.SERNAM, context.getActivityId());
	    screenData.addField(field4);

	    FieldDataDTO field6 = new FieldDataDTO();
	    field6.setFieldId(AuditConstant.SRCMOBNO);
	    field6.setValue(context.getMobilePhone());
	    screenData.addField(field6);
	}

	LOGGER.debug("BillPaymentTransactionAuditBuilder:buildFromRequest==> fromAcctNo:" + fromAcctNo + ", txnType:" + txnType + ", currency:"
		+ currency + ", txnAmount" + txnAmount + ", transDto.getTxnNot():"+transDto.getTxnNot());
	BeneficiaryDTO beneficiary = transDto.getBeneficiaryDTO();

	if(beneficiary != null){
	    LOGGER.debug("BillPaymentTransactionAuditBuilder:buildFromRequest:BeneficiaryDTO==> benefContactNo:" + beneficiary.getContactNumber()
		    + ", billerReferenceNo:" + beneficiary.getBillRefNo() + ", billerID:" + beneficiary.getBillerId() +
		    ", billerName:"+beneficiary.getBillerName()+", beneficiaryName:"+beneficiary.getBeneficiaryName()
		    +", destinationAccountNo:"+beneficiary.getDestinationAccountNumber());
	}
	String temp = "";

	if (BillPaymentConstants.TXN_FACADE_RTN_TYPE_BILL_PAYMENT.equals(txnType)) {

	    field11.setValue(BillPaymentConstants.SER_TYP_BP);
	    field5.setValue(BillPaymentConstants.SUB_SER_TYP_BP);

	    screenData.setScreenId(SCRNNAME_BP);
	    LOGGER.debug("BillPaymentTransactionAuditBuilder:buildFromRequest:TXN_FACADE_RTN_TYPE_BILL_PAYMENT==> ServiceType:"
		    + BillPaymentConstants.SER_TYP_BP + ", SubType" + BillPaymentConstants.SUB_SER_TYP_BP + ", ScreenID:" + SCRNNAME_BP);
	} else if (BillPaymentConstants.TXN_FACADE_RTN_TYPE_MOBILE_TOPUP.equals(txnType) || AuditConstant.MWALLET.equals(txnType)) {
	    // FieldDataDTO field7 = new FieldDataDTO();

	    temp = beneficiary.getContactNumber();
	    FieldDataDTO field7 = new FieldDataDTO(AuditConstant.MOBNO, temp == null ? AuditConstant.WHITESPACE : temp);
	    screenData.addField(field7);

	    field11.setValue(BillPaymentConstants.SER_TYP_BP_MTP);
	    field5.setValue(BillPaymentConstants.SUB_SER_TYP_BP_MTP);

	    screenData.setScreenId(SCRNNAME_MTP);
	    LOGGER.debug("BillPaymentTransactionAuditBuilder:buildFromRequest:TXN_FACADE_RTN_TYPE_BILL_PAYMENT==> ServiceType:"
		    + BillPaymentConstants.SER_TYP_BP_MTP + ", SubType" + BillPaymentConstants.SUB_SER_TYP_BP_MTP + ", ScreenID:" + SCRNNAME_MTP);
	} else if (BillPaymentConstants.PAYEE_TYPE_BILL_PAYMENT_ONE_TIME.equals(txnType)) {

	    field11.setValue(BillPaymentConstants.SER_TYP_BP_ONETIME);
	    field5.setValue(BillPaymentConstants.SUB_SER_TYP_BP_ONETIME);

	    screenData.setScreenId(SCRNNAME_BP);
	    LOGGER.debug("BillPaymentTransactionAuditBuilder:buildFromRequest:TXN_FACADE_RTN_TYPE_BILL_PAYMENT==> ServiceType:"
		    + BillPaymentConstants.SER_TYP_BP_ONETIME + ", SubType" + BillPaymentConstants.SUB_SER_TYP_BP_ONETIME + ", ScreenID:" + SCRNNAME_BP);
	}

	FieldDataDTO field18 = new FieldDataDTO();
	temp = beneficiary.getBillRefNo();
	field18.setFieldId(AuditConstant.BILLNO);
	field18.setValue("");
	screenData.addField(field18);

	FieldDataDTO field6 = new FieldDataDTO();
	field6.setFieldId(AuditConstant.ATTRIBUTE_NAME1);
	field6.setValue(AuditConstant.BILLER_ID);
	screenData.addField(field6);

	temp = beneficiary.getBillerId();
	// FieldDataDTO field13 = new FieldDataDTO();
	FieldDataDTO field13 = new FieldDataDTO(AuditConstant.ATTRIBUTE_VALUE1, temp == null ? AuditConstant.WHITESPACE : temp);
	screenData.addField(field13);

	FieldDataDTO field19 = new FieldDataDTO();
	field19.setFieldId(AuditConstant.BILLER_ID);
	field19.setValue(beneficiary.getBillerId());
	screenData.addField(field19);

	FieldDataDTO field7 = new FieldDataDTO();
	field7.setFieldId(AuditConstant.BILLERNAM);
	field7.setValue(beneficiary.getBillerName());
	screenData.addField(field7);

	field11.setFieldId(AuditConstant.SERTYP);
	field5.setFieldId(AuditConstant.TXNSUBTYP);
	screenData.addField(field5);
	screenData.addField(field11);

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

	transactionAuditDTO.setFromAccount(fromAcctNo);

	FieldDataDTO field1 = new FieldDataDTO(AuditConstant.MKDFRMACCTNO, new BMGFormatUtils().maskAccount(fromAcctNo));
	screenData.addField(field1);

	temp = beneficiary.getDestinationAccountNumber();
	FieldDataDTO field2 = new FieldDataDTO();
	field2.setFieldId(AuditConstant.MKDTOACCTNO);
	field2.setValue(new BMGFormatUtils().maskAccount(temp == null ? AuditConstant.WHITESPACE : temp));
	screenData.addField(field2);

	transactionAuditDTO.setToAccount(temp == null ? AuditConstant.WHITESPACE : temp);

	Date txnDate = Calendar.getInstance().getTime();
	transactionAuditDTO.setTransactionDateTime(txnDate);

	transactionAuditDTO.setTransactionAmount(txnAmount);
	transactionAuditDTO.setTransactionCurrency(currency);

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

	FieldDataDTO field10 = new FieldDataDTO();
	field10.setFieldId(AuditConstant.CMNTS);
	field10.setValue(transDto.getTxnNot());
	screenData.addField(field10);

	transactionAuditDTO.setReqRes(buildData(screenData));

	return transactionAuditDTO;
    }

    @Override
    public TransactionAuditDTO buildFromResponse(Object[] args, Object result) {
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	ScreenDataDTO screenData = new ScreenDataDTO();
	MakeBillPaymentOperationResponse response = (MakeBillPaymentOperationResponse) result;
	MakeBillPaymentOperationRequest request = (MakeBillPaymentOperationRequest) args[0];

	TransactionDTO transDto = request.getTransactionDTO();

	String fromAcctNo = "";
	if (transDto.getSourceAcct() != null) {
			if (transDto.getSourceAcct() instanceof CreditCardAccountDTO) {
				CreditCardAccountDTO creditCardDTO = (CreditCardAccountDTO) transDto
						.getSourceAcct();
				fromAcctNo = creditCardDTO.getPrimary().getCardNumber();
			} else {
				fromAcctNo = transDto.getSourceAcct().getAccountNumber();
			}
		}

	//String fromAcctNo = transDto.getSourceAcct().getAccountNumber();
	String currency = transDto.getSourceAcct().getCurrency();
	Double txnAmount = transDto.getTxnAmt().getAmount().doubleValue();
	String txnType = transDto.getTxnType();

	FieldDataDTO field5 = new FieldDataDTO();
	FieldDataDTO field11 = new FieldDataDTO();
	Context context = request.getContext();

	if (context != null) {
	    FieldDataDTO field4 = new FieldDataDTO();
	    field4.setFieldId(AuditConstant.CUSTID);
	    field4.setValue(context.getCustomerId());
	    screenData.addField(field4);

	    field4 = new FieldDataDTO(AuditConstant.CUSTNAME, context.getFullName());
	    screenData.addField(field4);

	    field4 = new FieldDataDTO(AuditConstant.SERNAM, context.getActivityId());
	    screenData.addField(field4);

	    FieldDataDTO field6 = new FieldDataDTO();
	    field6.setFieldId(AuditConstant.SRCMOBNO);
	    field6.setValue(context.getMobilePhone());
	    screenData.addField(field6);
	}

	LOGGER.debug("BillPaymentTransactionAuditBuilder:buildFromResponse==> fromAcctNo:" + fromAcctNo + ", txnType:" + txnType + ", currency:"
		+ currency + ", txnAmount" + txnAmount + ", transDto.getTxnNot():"+transDto.getTxnNot());

	BeneficiaryDTO beneficiary = transDto.getBeneficiaryDTO();
	String temp = "";

	if(beneficiary != null){
	    LOGGER.debug("BillPaymentTransactionAuditBuilder:buildFromResponse:BeneficiaryDTO==> benefContactNo:" + beneficiary.getContactNumber()
		    + ", billerReferenceNo:" + beneficiary.getBillRefNo() + ", billerID:" + beneficiary.getBillerId() +
		    ", billerName:"+beneficiary.getBillerName()+", beneficiaryName:"+beneficiary.getBeneficiaryName()
		    +", destinationAccountNo:"+beneficiary.getDestinationAccountNumber());
	}

	if (BillPaymentConstants.TXN_FACADE_RTN_TYPE_BILL_PAYMENT.equals(txnType)) {

	    field11.setValue(BillPaymentConstants.SER_TYP_BP);
	    field5.setValue(BillPaymentConstants.SUB_SER_TYP_BP);

	    screenData.setScreenId(SCRNNAME_BP);

	    LOGGER.debug("BillPaymentTransactionAuditBuilder:buildFromResponse:TXN_FACADE_RTN_TYPE_BILL_PAYMENT==> ServiceType:"
		    + BillPaymentConstants.SER_TYP_BP + ", SubType" + BillPaymentConstants.SUB_SER_TYP_BP + ", ScreenID:" + SCRNNAME_BP);

	} else if (BillPaymentConstants.TXN_FACADE_RTN_TYPE_MOBILE_TOPUP.equals(txnType) || AuditConstant.MWALLET.equals(txnType)) {
	    // FieldDataDTO field7 = new FieldDataDTO();

	    temp = beneficiary.getContactNumber();
	    FieldDataDTO field7 = new FieldDataDTO(AuditConstant.MOBNO, temp == null ? AuditConstant.WHITESPACE : temp);
	    screenData.addField(field7);

	    field11.setValue(BillPaymentConstants.SER_TYP_BP_MTP);
	    field5.setValue(BillPaymentConstants.SUB_SER_TYP_BP_MTP);

	    screenData.setScreenId(SCRNNAME_MTP);
	    LOGGER.debug("BillPaymentTransactionAuditBuilder:buildFromRequest:TXN_FACADE_RTN_TYPE_MOBILE_TOPUP==> ServiceType:"
		    + BillPaymentConstants.SER_TYP_BP_MTP + ", SubType" + BillPaymentConstants.SUB_SER_TYP_BP_MTP + ", ScreenID:" + SCRNNAME_MTP);

	} else if (BillPaymentConstants.PAYEE_TYPE_BILL_PAYMENT_ONE_TIME.equals(txnType)) {

	    field11.setValue(BillPaymentConstants.SER_TYP_BP_ONETIME);
	    field5.setValue(BillPaymentConstants.SUB_SER_TYP_BP_ONETIME);

	    screenData.setScreenId(SCRNNAME_BP);
	    LOGGER.debug("BillPaymentTransactionAuditBuilder:buildFromResponse:PAYEE_TYPE_BILL_PAYMENT_ONE_TIME==> ServiceType:"
		    + BillPaymentConstants.SER_TYP_BP_ONETIME + ", SubType" + BillPaymentConstants.SUB_SER_TYP_BP_ONETIME + ", ScreenID:" + SCRNNAME_BP);
	}

	FieldDataDTO field7 = new FieldDataDTO();
	field7.setFieldId(AuditConstant.BILLERNAM);
	field7.setValue(beneficiary.getBillerName());
	screenData.addField(field7);

	FieldDataDTO field18 = new FieldDataDTO();
	temp = beneficiary.getBillRefNo();
	field18.setFieldId(AuditConstant.BILLNO);
	field18.setValue("");
	screenData.addField(field18);

	FieldDataDTO field19 = new FieldDataDTO();
	field19.setFieldId(AuditConstant.BILLER_ID);
	field19.setValue(beneficiary.getBillerId());
	screenData.addField(field19);

	FieldDataDTO field6 = new FieldDataDTO();
	field6.setFieldId(AuditConstant.ATTRIBUTE_NAME1);
	field6.setValue(AuditConstant.BILLER_ID);
	screenData.addField(field6);

	temp = beneficiary.getBillerId();
	// FieldDataDTO field13 = new FieldDataDTO();
	FieldDataDTO field13 = new FieldDataDTO(AuditConstant.ATTRIBUTE_VALUE1, temp == null ? AuditConstant.WHITESPACE : temp);
	screenData.addField(field13);

	field11.setFieldId(AuditConstant.SERTYP);
	field5.setFieldId(AuditConstant.TXNSUBTYP);
	screenData.addField(field5);
	screenData.addField(field11);

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

	transactionAuditDTO.setFromAccount(fromAcctNo);

	FieldDataDTO field1 = new FieldDataDTO(AuditConstant.MKDFRMACCTNO, new BMGFormatUtils().maskAccount(fromAcctNo));
	screenData.addField(field1);

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

	if (BillPaymentConstants.TXN_FACADE_RTN_TYPE_BILL_PAYMENT.equals(txnType)) {
	    screenData.setScreenId(SCRNNAME_BP);
	} else if (BillPaymentConstants.TXN_FACADE_RTN_TYPE_MOBILE_TOPUP.equals(txnType) || AuditConstant.MWALLET.equals(txnType)) {
	    screenData.setScreenId(SCRNNAME_MTP);
	}
	transactionAuditDTO.setFromAccount(transDto.getSourceAcct().getAccountNumber());

	temp = beneficiary.getDestinationAccountNumber();
	FieldDataDTO field2 = new FieldDataDTO();
	field2.setFieldId(AuditConstant.MKDTOACCTNO);
	field2.setValue(new BMGFormatUtils().maskAccount(temp == null ? AuditConstant.WHITESPACE : temp));
	screenData.addField(field2);

	transactionAuditDTO.setToAccount(temp == null ? AuditConstant.WHITESPACE : temp);

	Date txnDate = Calendar.getInstance().getTime();
	transactionAuditDTO.setTransactionDateTime(txnDate);

	if (response != null) {
	    transactionAuditDTO.setTransactionRefNo(response.getTrnRef());
	    if (response.isSuccess()) {
		transactionAuditDTO.setTransactionAmount(txnAmount);
		transactionAuditDTO.setTransactionCurrency(currency);
		transactionAuditDTO.setTransactionStatus(AuditConstant.TXN_STATUS_SUCCESS);
	    } else {
		FieldDataDTO field17 = new FieldDataDTO();
		field17.setFieldId(AuditConstant.ERRCD);
		field17.setValue(response.getResCde());
		screenData.addField(field17);

		transactionAuditDTO.setTransactionStatus(AuditConstant.TXN_STATUS_FAILURE);
		transactionAuditDTO.setErrorMessage(response.getResMsg());

		FieldDataDTO field16 = new FieldDataDTO();
		field16.setFieldId(AuditConstant.FAILURE_REASON);
		field16.setValue(response.getResMsg());
		screenData.addField(field16);

	    }

	} else {
	    transactionAuditDTO.setTransactionStatus(AuditConstant.TXN_STATUS_FAILURE);
	}
	transactionAuditDTO.setReqRes(buildData(screenData));

	return transactionAuditDTO;
    }
}