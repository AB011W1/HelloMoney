package com.barclays.bmg.audit.builder.impl;

import java.util.Calendar;
import java.util.Date;

import com.barclays.bmg.audit.dto.FieldDataDTO;
import com.barclays.bmg.audit.dto.ScreenDataDTO;
import com.barclays.bmg.audit.dto.TransactionAuditDTO;
import com.barclays.bmg.cashsend.operation.request.CashSendOneTimeExecuteOperationRequest;
import com.barclays.bmg.cashsend.operation.response.CashSendOneTimeExecuteOperationResponse;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.context.Context;

public class CashSendAuditBuilder extends AbstractTransactionAuditBuilder {

    /**
     * The Constant named "SCRNNAM_BP" is created.
     */
    private static final String SCRNAME_BP = "VIEW_CASH_SEND";

    @Override
    public TransactionAuditDTO buildFromRequest(Object[] args, Object result) {

	CashSendOneTimeExecuteOperationRequest request = (CashSendOneTimeExecuteOperationRequest) args[0];
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();

	ScreenDataDTO screenData = new ScreenDataDTO();

	screenData.setScreenId(SCRNAME_BP);

	FieldDataDTO field8 = new FieldDataDTO();
	field8.setFieldId(AuditConstant.SERTYP);
	field8.setValue(AuditConstant.SER_TYP_CCD_CASH_SEND);
	screenData.addField(field8);

	FieldDataDTO field9 = new FieldDataDTO();
	field9.setFieldId(AuditConstant.TXNSUBTYP);
	field9.setValue(AuditConstant.SUB_SER_TYP_CCD_CASH_SEND);
	screenData.addField(field9);

	Date txnDate = Calendar.getInstance().getTime();
	String fromAcctNo = null; String currency = null;
	Double txnAmount = 0.0;
	if(null != request){
		fromAcctNo = request.getCashSendRequestDTO().getActNo();
		currency = request.getCashSendRequestDTO().getCurr();
		txnAmount = Double.parseDouble(request.getCashSendRequestDTO().getTxnAmt());
	}
	if(null != fromAcctNo)
		transactionAuditDTO.setFromAccount(fromAcctNo);
	if(null != currency)
		transactionAuditDTO.setTransactionCurrency(currency);
	if(null != txnAmount)
		transactionAuditDTO.setTransactionAmount(txnAmount);
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

	    FieldDataDTO field5 = new FieldDataDTO();
		field5.setFieldId(AuditConstant.MOBNO);
		field5.setValue(request.getCashSendRequestDTO().getRecipientMobileNo());
		screenData.addField(field5);

	}

	transactionAuditDTO.setReqRes(buildData(screenData));

	return transactionAuditDTO;
    }

    @Override
    public TransactionAuditDTO buildFromResponse(Object[] args, Object result) {

	CashSendOneTimeExecuteOperationResponse response = (CashSendOneTimeExecuteOperationResponse) result;
	ScreenDataDTO screenData = new ScreenDataDTO();

	FieldDataDTO field8 = new FieldDataDTO();
	field8.setFieldId(AuditConstant.SERTYP);
	field8.setValue(AuditConstant.SER_TYP_CCD_CASH_SEND);
	screenData.addField(field8);

	FieldDataDTO field9 = new FieldDataDTO();
	field9.setFieldId(AuditConstant.TXNSUBTYP);
	field9.setValue(AuditConstant.SUB_SER_TYP_CCD_CASH_SEND);
	screenData.addField(field9);

	screenData.setScreenId(SCRNAME_BP);

	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();

	Date txnDate = Calendar.getInstance().getTime();

	CashSendOneTimeExecuteOperationRequest request = (CashSendOneTimeExecuteOperationRequest) args[0];
	String fromAcctNo = null; String currency = null;
	Double txnAmount = 0.0;
	if(null != request){
		fromAcctNo = request.getCashSendRequestDTO().getActNo();
		currency = request.getCashSendRequestDTO().getCurr();
		txnAmount = Double.parseDouble(request.getCashSendRequestDTO().getTxnAmt());
	}
	if(null != fromAcctNo)
		transactionAuditDTO.setFromAccount(fromAcctNo);
	if(null != currency)
		transactionAuditDTO.setTransactionCurrency(currency);
	if(null != txnAmount)
		transactionAuditDTO.setTransactionAmount(txnAmount);
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
//reverted as per request by mandeep for reporting purpose
	    FieldDataDTO field6 = new FieldDataDTO();
		field6.setFieldId(AuditConstant.MOBNO);
		field6.setValue(request.getCashSendRequestDTO().getRecipientMobileNo());
		screenData.addField(field6);
	}

	if (response != null) {

	    if (response.isSuccess()) {
		transactionAuditDTO.setTransactionStatus(AuditConstant.TXN_STATUS_SUCCESS);

		 FieldDataDTO field6 = new FieldDataDTO();
		    field6.setFieldId(AuditConstant.ATTRIBUTE_NAME1);
		    field6.setValue("VOUCHER_ID");
		    screenData.addField(field6);

		    FieldDataDTO field7 = new FieldDataDTO();
		    field7.setFieldId(AuditConstant.ATTRIBUTE_VALUE1);
		    field7.setValue(response.getVoucherId());
		    screenData.addField(field7);


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
