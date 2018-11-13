package com.barclays.bmg.audit.builder.impl;

import java.util.Calendar;
import java.util.Date;

import com.barclays.bmg.audit.dto.FieldDataDTO;
import com.barclays.bmg.audit.dto.ScreenDataDTO;
import com.barclays.bmg.audit.dto.TransactionAuditDTO;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.operation.request.fundtransfer.external.AddOrgBenefeciaryOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.AddOrgBeneficiaryOperationResponse;
import com.barclays.bmg.utils.DateTimeUtil;
import com.ibm.icu.text.SimpleDateFormat;

public class AddOrgBeneficiaryExecuteAuditBuilder extends AbstractTransactionAuditBuilder {
    /**
     * The Constant named "SCRNNAM_BP" is created.
     */
    private static final String SCRNNAM_BP = "ADD_ORG_BENEFICIARY";
    private static final String DATE_FORMAT = "dd/MM/yy HH:mm:ss";

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.bmg.audit.builder.BMGTransactionAuditBuilder#buildFromRequest(java.lang.Object[], java.lang.Object)
     */
    @Override
    public TransactionAuditDTO buildFromRequest(Object[] args, Object result) {

	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	AddOrgBenefeciaryOperationRequest request = (AddOrgBenefeciaryOperationRequest) args[0];

	ScreenDataDTO screenData = new ScreenDataDTO();

	FieldDataDTO field8 = new FieldDataDTO();
	field8.setFieldId(AuditConstant.SERTYP);
	field8.setValue(BillPaymentConstants.SER_TYP_ADD_ORG_BEN);
	screenData.addField(field8);

	FieldDataDTO field9 = new FieldDataDTO();
	field9.setFieldId(AuditConstant.TXNSUBTYP);
	field9.setValue(BillPaymentConstants.SUB_SER_TYP_ADD_ORG_BEN);
	screenData.addField(field9);

	screenData.setScreenId(SCRNNAM_BP);

	Date txnDate = Calendar.getInstance().getTime();
	transactionAuditDTO.setTransactionDateTime(txnDate);

	if (request != null) {

	    Context context = request.getContext();
	    // transactionAuditDTO.setFromAccount(request.getAcctNumber());

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
	    field5.setFieldId(AuditConstant.BILLERNAM);
	    if (request.getBeneficiaryDTO() != null)
		field5.setValue(request.getBeneficiaryDTO().getBillerName());
	    else
		field5.setValue("");
	    screenData.addField(field5);

	    FieldDataDTO field6 = new FieldDataDTO();
	    field6.setFieldId(AuditConstant.ATTRIBUTE_NAME1);
	    field6.setValue(BillPaymentConstants.BILLER_NICK_NAME);
	    screenData.addField(field6);

	    FieldDataDTO field7 = new FieldDataDTO();
	    field7.setFieldId(AuditConstant.ATTRIBUTE_VALUE1);
	    field7.setValue(request.getBillerNickName());
	    screenData.addField(field7);

	    FieldDataDTO field10 = new FieldDataDTO();
	    field10.setFieldId(AuditConstant.TXNON);
	    Calendar cal = DateTimeUtil.getCurrentBusinessCalendar(request.getContext());
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	    String date = sdf.format(cal.getTime());
	    field10.setValue(date);
	    screenData.addField(field10);
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
	field6.setValue(BillPaymentConstants.SER_TYP_ADD_ORG_BEN);
	screenData.addField(field6);

	FieldDataDTO field7 = new FieldDataDTO();
	field7.setFieldId(AuditConstant.TXNSUBTYP);
	field7.setValue(BillPaymentConstants.SUB_SER_TYP_ADD_ORG_BEN);
	screenData.addField(field7);

	screenData.setScreenId(SCRNNAM_BP);

	AddOrgBenefeciaryOperationRequest request = (AddOrgBenefeciaryOperationRequest) args[0];
	AddOrgBeneficiaryOperationResponse response = (AddOrgBeneficiaryOperationResponse) result;

	Date txnDate = Calendar.getInstance().getTime();
	transactionAuditDTO.setTransactionDateTime(txnDate);

	if (request != null) {

	    Context context = request.getContext();
	    // transactionAuditDTO.setFromAccount(request.getAcctNumber());

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

		FieldDataDTO field10 = new FieldDataDTO();
		field10.setFieldId(AuditConstant.TXNON);
		Calendar cal = DateTimeUtil.getCurrentBusinessCalendar(context);
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String date = sdf.format(cal.getTime());
		field10.setValue(date);
		screenData.addField(field10);
	    }

	    FieldDataDTO field5 = new FieldDataDTO();
	    field5.setFieldId(AuditConstant.BILLERNAM);
	    if (request.getBeneficiaryDTO() != null)
		field5.setValue(request.getBeneficiaryDTO().getBillerName());
	    else
		field5.setValue("");
	    screenData.addField(field5);

	    FieldDataDTO field18 = new FieldDataDTO();
	    field18.setFieldId(AuditConstant.ATTRIBUTE_NAME1);
	    field18.setValue(BillPaymentConstants.BILLER_NICK_NAME);
	    screenData.addField(field18);

	    FieldDataDTO field19 = new FieldDataDTO();
	    field19.setFieldId(AuditConstant.ATTRIBUTE_VALUE1);
	    if (request.getBeneficiaryDTO() != null)
		field19.setValue(request.getBeneficiaryDTO().getPayeeNickname());
	    else
		field19.setValue("");
	    screenData.addField(field19);
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