package com.barclays.bmg.audit.builder.impl;

import java.util.Calendar;
import java.util.Date;

import com.barclays.bmg.audit.dto.FieldDataDTO;
import com.barclays.bmg.audit.dto.ScreenDataDTO;
import com.barclays.bmg.audit.dto.TransactionAuditDTO;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.operation.accountdetails.request.CasaTransactionActivityOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.CasaTransactionActivityOperationResponse;
import com.barclays.bmg.operation.accountdetails.response.CasaTransactionTrnxHistoryOperationResponse;

public class CASAActivityTransactionAuditBuilder extends AbstractTransactionAuditBuilder {
    private static final String SCRNNAM_BP = "MINI_STATEMENT";

    @Override
    public TransactionAuditDTO buildFromRequest(Object[] args, Object result) {
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	ScreenDataDTO screenData = new ScreenDataDTO();

	CasaTransactionActivityOperationRequest request = (CasaTransactionActivityOperationRequest) args[0];

	Date txnDate = Calendar.getInstance().getTime();
	transactionAuditDTO.setTransactionDateTime(txnDate);
	screenData.setScreenId(SCRNNAM_BP);
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

	    String actNo = request.getAccountNo();
	    String days = request.getDays();

	    FieldDataDTO fieldDataDTO = new FieldDataDTO(AuditConstant.ACCTNO, actNo);
	    screenData.addField(fieldDataDTO);

	    fieldDataDTO = new FieldDataDTO("days", days);
	    screenData.addField(fieldDataDTO);

	    FieldDataDTO field12 = new FieldDataDTO();
	    field12.setFieldId(AuditConstant.OTHER_PARAMS);
	    if (request.getCasaAccountDTO() != null)
		field12.setValue(request.getCasaAccountDTO().toString());
	    else
		field12.setValue("");
	    screenData.addField(field12);

	    FieldDataDTO field6 = new FieldDataDTO();
	    field6.setFieldId(AuditConstant.SERTYP);
	    field6.setValue(AuditConstant.SER_TYP_MINI_STMT);
	    screenData.addField(field6);

	    FieldDataDTO field7 = new FieldDataDTO();
	    field7.setValue(AuditConstant.SUB_SER_TYP_MINI_STMT);
	    field7.setFieldId(AuditConstant.TXNSUBTYP);
	    screenData.addField(field7);

	    transactionAuditDTO.setFromAccount(actNo);

	}

	transactionAuditDTO.setReqRes(buildData(screenData));
	return transactionAuditDTO;
    }

    @Override
    public TransactionAuditDTO buildFromResponse(Object[] args, Object result) {
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	ScreenDataDTO screenData = new ScreenDataDTO();

	CasaTransactionActivityOperationRequest request = (CasaTransactionActivityOperationRequest) args[0];
	//CasaTransactionTrnxHistoryOperationResponse response = (CasaTransactionTrnxHistoryOperationResponse) result;
	CasaTransactionActivityOperationResponse response = (CasaTransactionActivityOperationResponse) result;
	Date txnDate = Calendar.getInstance().getTime();
	transactionAuditDTO.setTransactionDateTime(txnDate);
	screenData.setScreenId(SCRNNAM_BP);
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

	    FieldDataDTO field11 = new FieldDataDTO();
	    field11.setFieldId(AuditConstant.ACCTNO);
	    field11.setValue(request.getAccountNo());

	    screenData.addField(field11);

	    transactionAuditDTO.setFromAccount(request.getAccountNo());

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

	    FieldDataDTO field12 = new FieldDataDTO();
	    field12.setFieldId(AuditConstant.OTHER_PARAMS);
	    if (request.getCasaAccountDTO() != null)
		field12.setValue(request.getCasaAccountDTO().toString());
	    else
		field12.setValue("");
	    screenData.addField(field12);

	    FieldDataDTO field6 = new FieldDataDTO();
	    field6.setFieldId(AuditConstant.SERTYP);
	    field6.setValue(AuditConstant.SER_TYP_MINI_STMT);
	    screenData.addField(field6);

	    FieldDataDTO field7 = new FieldDataDTO();
	    field7.setValue(AuditConstant.SUB_SER_TYP_MINI_STMT);
	    field7.setFieldId(AuditConstant.TXNSUBTYP);
	    screenData.addField(field7);

	} else {
	    transactionAuditDTO.setTransactionStatus(AuditConstant.TXN_STATUS_FAILURE);
	}

	transactionAuditDTO.setReqRes(buildData(screenData));
	return transactionAuditDTO;
    }

}
