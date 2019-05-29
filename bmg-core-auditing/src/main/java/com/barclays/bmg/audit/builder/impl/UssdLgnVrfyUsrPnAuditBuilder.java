package com.barclays.bmg.audit.builder.impl;

import java.util.Calendar;
import java.util.Date;

import com.barclays.bmg.audit.dto.FieldDataDTO;
import com.barclays.bmg.audit.dto.ScreenDataDTO;
import com.barclays.bmg.audit.dto.TransactionAuditDTO;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.operation.request.TacticalHelloMoneyVerifyUserOperationRequest;
import com.barclays.bmg.operation.request.UssdLgnVrfyUsrPnOperationRequest;
import com.barclays.bmg.operation.response.TacticalHelloMoneyVerifyUserOperationResponse;
import com.barclays.bmg.operation.response.UssdLgnVrfyUsrPnOperationResponse;
import com.barclays.bmg.utils.DateTimeUtil;
import com.ibm.icu.text.SimpleDateFormat;

public class UssdLgnVrfyUsrPnAuditBuilder extends AbstractTransactionAuditBuilder {
    /**
     * The Constant named "SCRNNAM_BP" is created.
     */
    private static final String SCRNNAM_BP = "LOGIN";
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

	RequestContext request = null;
	if (args[0] instanceof UssdLgnVrfyUsrPnOperationRequest) {
	    request = (UssdLgnVrfyUsrPnOperationRequest) args[0];
	} else {
	    request = (TacticalHelloMoneyVerifyUserOperationRequest) args[0];
	}

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

	    FieldDataDTO field6 = new FieldDataDTO();
	    field6.setFieldId(AuditConstant.SERTYP);
	    field6.setValue(AuditConstant.SER_TYP_LOGIN);
	    screenData.addField(field6);

	    FieldDataDTO field7 = new FieldDataDTO();
	    field7.setValue(AuditConstant.SUB_SER_TYP_LOGIN);
	    field7.setFieldId(AuditConstant.TXNSUBTYP);
	    screenData.addField(field7);

	    screenData.setScreenId(SCRNNAM_BP);

	    Date txnDate = Calendar.getInstance().getTime();
	    transactionAuditDTO.setTransactionDateTime(txnDate);

	    FieldDataDTO field8 = new FieldDataDTO();
	    field8.setFieldId(AuditConstant.ATTRIBUTE_NAME1);
	    field8.setValue(AuditConstant.MOBNO);
	    screenData.addField(field8);

	    FieldDataDTO field10 = new FieldDataDTO();
	    field10.setFieldId(AuditConstant.TXNON);
	    Calendar cal = DateTimeUtil.getCurrentBusinessCalendar(request.getContext());
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	    String date = sdf.format(cal.getTime());
	    field10.setValue(date);
	    screenData.addField(field10);

	    FieldDataDTO field9 = new FieldDataDTO();
	    field9.setFieldId(AuditConstant.ATTRIBUTE_VALUE1);
	    if (args[0] instanceof UssdLgnVrfyUsrPnOperationRequest) {
		field9.setValue(((UssdLgnVrfyUsrPnOperationRequest) args[0]).getUsrNam());
	    } else {
		field9.setValue(((TacticalHelloMoneyVerifyUserOperationRequest) args[0]).getCustMSISDN());
	    }
	    screenData.addField(field9);

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
	field6.setValue(AuditConstant.SER_TYP_LOGIN);
	screenData.addField(field6);

	FieldDataDTO field7 = new FieldDataDTO();
	field7.setValue(AuditConstant.SUB_SER_TYP_LOGIN);
	field7.setFieldId(AuditConstant.TXNSUBTYP);
	screenData.addField(field7);

	screenData.setScreenId(SCRNNAM_BP);

	RequestContext request = null;
	ResponseContext response = null;
	if (args[0] instanceof UssdLgnVrfyUsrPnOperationRequest) {
	    request = (UssdLgnVrfyUsrPnOperationRequest) args[0];
	    response = (UssdLgnVrfyUsrPnOperationResponse) result;
	} else {
	    request = (TacticalHelloMoneyVerifyUserOperationRequest) args[0];
	    response = (TacticalHelloMoneyVerifyUserOperationResponse) result;
	}

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
	    field8.setFieldId(AuditConstant.ATTRIBUTE_NAME1);
	    field8.setValue(AuditConstant.MOBNO);
	    screenData.addField(field8);

	    FieldDataDTO field9 = new FieldDataDTO();
	    field9.setFieldId(AuditConstant.ATTRIBUTE_VALUE1);
	    if (args[0] instanceof UssdLgnVrfyUsrPnOperationRequest) {
		field9.setValue(((UssdLgnVrfyUsrPnOperationRequest) args[0]).getUsrNam());
	    } else {
		field9.setValue(((TacticalHelloMoneyVerifyUserOperationRequest) args[0]).getCustMSISDN());
	    }
	    screenData.addField(field9);

	    try {

		FieldDataDTO field10 = new FieldDataDTO();
		field10.setFieldId(AuditConstant.CNTRYDTTM);
		Calendar cal = DateTimeUtil.getCurrentBusinessCalendar(request.getContext());
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String date = sdf.format(cal.getTime());
		field10.setValue(date);
		screenData.addField(field10);

	    } catch (Exception ex) {
	    	ex.printStackTrace();
	    }
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