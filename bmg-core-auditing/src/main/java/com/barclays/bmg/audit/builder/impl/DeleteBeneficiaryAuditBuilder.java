package com.barclays.bmg.audit.builder.impl;

import java.util.Calendar;
import java.util.Date;

import com.barclays.bmg.audit.dto.FieldDataDTO;
import com.barclays.bmg.audit.dto.ScreenDataDTO;
import com.barclays.bmg.audit.dto.TransactionAuditDTO;
import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.operation.request.fundtransfer.external.DeleteBeneficiaryOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.DeleteBeneficiaryOperationResponse;
import com.barclays.bmg.utils.DateTimeUtil;
import com.ibm.icu.text.SimpleDateFormat;

public class DeleteBeneficiaryAuditBuilder extends AbstractTransactionAuditBuilder {

    /**
     * The Constant named "SCRNNAM_BP" is created.
     */
    private static final String SCRNNAM_BP = "DELETE_BENEFICIARY";
    private static final String SCRNNAM_BILL = "DELETE_BILLER";
    private static final String DATE_FORMAT = "dd/MM/yy HH:mm:ss";

    @Override
    public TransactionAuditDTO buildFromRequest(Object[] args, Object result) {
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	ScreenDataDTO screenData = new ScreenDataDTO();

	DeleteBeneficiaryOperationRequest request = (DeleteBeneficiaryOperationRequest) args[0];

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

		if (context.getActivityId().equals(ActivityIdConstantBean.DELETE_INTERNAL_PAYEE_ACTIVITY_ID)
			|| context.getActivityId().equals(ActivityIdConstantBean.DELETE_EXTERNAL_PAYEE_ACTIVITY_ID)) {

		    FieldDataDTO field11 = new FieldDataDTO();
		    field11.setFieldId(AuditConstant.SERTYP);
		    field11.setValue(AuditConstant.SER_TYP_DELETE_BEN);
		    screenData.addField(field11);

		    FieldDataDTO field12 = new FieldDataDTO();
		    field12.setValue(AuditConstant.SUB_SER_TYP_DELETE_BEN);
		    field12.setFieldId(AuditConstant.TXNSUBTYP);
		    screenData.addField(field12);

		    screenData.setScreenId(SCRNNAM_BP);

		} else if (context.getActivityId().equals(ActivityIdConstantBean.DELETE_PAYEE_ACTIVITY_ID)) {

		    FieldDataDTO field14 = new FieldDataDTO();
		    field14.setFieldId(AuditConstant.SERTYP);
		    field14.setValue(AuditConstant.SER_TYP_DELETE_BILLER);
		    screenData.addField(field14);

		    FieldDataDTO field15 = new FieldDataDTO();
		    field15.setValue(AuditConstant.SUB_SER_TYP_DELETE_BILLER);
		    field15.setFieldId(AuditConstant.TXNSUBTYP);
		    screenData.addField(field15);
		    screenData.setScreenId(SCRNNAM_BILL);
		}
	    }

	    FieldDataDTO field5 = new FieldDataDTO();
	    field5.setFieldId(AuditConstant.PAYID);
	    field5.setValue(request.getPayeeId());
	    screenData.addField(field5);

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

    @Override
    public TransactionAuditDTO buildFromResponse(Object[] args, Object result) {

	DeleteBeneficiaryOperationResponse response = (DeleteBeneficiaryOperationResponse) result;
	DeleteBeneficiaryOperationRequest request = (DeleteBeneficiaryOperationRequest) args[0];

	ScreenDataDTO screenData = new ScreenDataDTO();

	screenData.setScreenId(SCRNNAM_BP);

	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();

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

		if (context.getActivityId().equals(ActivityIdConstantBean.DELETE_INTERNAL_PAYEE_ACTIVITY_ID)
			|| context.getActivityId().equals(ActivityIdConstantBean.DELETE_EXTERNAL_PAYEE_ACTIVITY_ID)) {

		    FieldDataDTO field11 = new FieldDataDTO();
		    field11.setFieldId(AuditConstant.SERTYP);
		    field11.setValue(AuditConstant.SER_TYP_DELETE_BEN);
		    screenData.addField(field11);

		    FieldDataDTO field12 = new FieldDataDTO();
		    field12.setValue(AuditConstant.SUB_SER_TYP_DELETE_BEN);
		    field12.setFieldId(AuditConstant.TXNSUBTYP);
		    screenData.addField(field12);
		    screenData.setScreenId(SCRNNAM_BP);

		} else if (context.getActivityId().equals(ActivityIdConstantBean.DELETE_PAYEE_ACTIVITY_ID)) {

		    FieldDataDTO field14 = new FieldDataDTO();
		    field14.setFieldId(AuditConstant.SERTYP);
		    field14.setValue(AuditConstant.SER_TYP_DELETE_BILLER);
		    screenData.addField(field14);

		    FieldDataDTO field15 = new FieldDataDTO();
		    field15.setValue(AuditConstant.SUB_SER_TYP_DELETE_BILLER);
		    field15.setFieldId(AuditConstant.TXNSUBTYP);
		    screenData.addField(field15);
		    screenData.setScreenId(SCRNNAM_BILL);
		}
	    }

	    FieldDataDTO field5 = new FieldDataDTO();
	    field5.setFieldId(AuditConstant.PAYID);
	    field5.setValue(request.getPayeeId());
	    screenData.addField(field5);

	    FieldDataDTO field10 = new FieldDataDTO();
	    field10.setFieldId(AuditConstant.TXNON);
	    Calendar cal = DateTimeUtil.getCurrentBusinessCalendar(request.getContext());
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	    String date = sdf.format(cal.getTime());
	    field10.setValue(date);
	    screenData.addField(field10);
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

		FieldDataDTO field12 = new FieldDataDTO();
		field12.setFieldId(AuditConstant.BENEFICIARY_OBJ);
		if (response.getBeneficiaryDTO() != null)
		    field12.setValue(response.getBeneficiaryDTO().toString());
		else
		    field12.setValue("");
		screenData.addField(field12);
	    }

	} else {
	    transactionAuditDTO.setTransactionStatus(AuditConstant.TXN_STATUS_FAILURE);
	}

	transactionAuditDTO.setReqRes(buildData(screenData));

	return transactionAuditDTO;
    }

}
