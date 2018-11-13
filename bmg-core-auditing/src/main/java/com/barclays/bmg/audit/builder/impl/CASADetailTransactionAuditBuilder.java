package com.barclays.bmg.audit.builder.impl;

import java.util.Calendar;
import java.util.Date;

import com.barclays.bmg.audit.dto.FieldDataDTO;
import com.barclays.bmg.audit.dto.ScreenDataDTO;
import com.barclays.bmg.audit.dto.TransactionAuditDTO;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.operation.accountdetails.request.CASADetailsOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.CASADetailsOperationResponse;

public class CASADetailTransactionAuditBuilder extends AbstractTransactionAuditBuilder {

    @Override
    public TransactionAuditDTO buildFromRequest(Object[] args, Object result) {
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	ScreenDataDTO screenData = new ScreenDataDTO();

	CASADetailsOperationRequest request = (CASADetailsOperationRequest) args[0];

	screenData.setScreenId(ActivityConstant.CASA_DETAIL_ACTIVITY_ID);

	Date txnDate = Calendar.getInstance().getTime();
	transactionAuditDTO.setTransactionDateTime(txnDate);

	FieldDataDTO field6 = new FieldDataDTO();
	field6.setFieldId(AuditConstant.SERTYP);
	field6.setValue(AuditConstant.SER_TYP_BAL_ENQUIRY);
	screenData.addField(field6);

	FieldDataDTO field7 = new FieldDataDTO();
	field7.setValue(AuditConstant.SUB_SER_TYP_BAL_ENQUIRY);
	field7.setFieldId(AuditConstant.TXNSUBTYP);
	screenData.addField(field7);

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

	    FieldDataDTO fieldDataDTO = new FieldDataDTO(AuditConstant.ACCTNO, actNo);
	    screenData.addField(fieldDataDTO);

	    transactionAuditDTO.setFromAccount(actNo);
	}

	transactionAuditDTO.setReqRes(buildData(screenData));
	return transactionAuditDTO;
    }

    @Override
    public TransactionAuditDTO buildFromResponse(Object[] args, Object result) {
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	ScreenDataDTO screenData = new ScreenDataDTO();

	CASADetailsOperationResponse response = (CASADetailsOperationResponse) result;
	CASADetailsOperationRequest request = (CASADetailsOperationRequest) args[0];
	screenData.setScreenId(ActivityConstant.CASA_DETAIL_ACTIVITY_ID);

	FieldDataDTO field18 = new FieldDataDTO();
	field18.setFieldId(AuditConstant.SERTYP);
	field18.setValue(AuditConstant.SER_TYP_BAL_ENQUIRY);
	screenData.addField(field18);

	FieldDataDTO field19 = new FieldDataDTO();
	field19.setValue(AuditConstant.SUB_SER_TYP_BAL_ENQUIRY);
	field19.setFieldId(AuditConstant.TXNSUBTYP);
	screenData.addField(field19);

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
	    String actNo = request.getAccountNo();

	    FieldDataDTO fieldDataDTO = new FieldDataDTO(AuditConstant.ACCTNO, actNo);
	    screenData.addField(fieldDataDTO);

	    transactionAuditDTO.setFromAccount(actNo);
	}

	if (response != null) {
	    CASAAccountDTO sourceObject = response.getCasaAccountDTO();

	    if (sourceObject != null) {

		FieldDataDTO field2 = new FieldDataDTO();
		field2.setFieldId("overDraftLimit");
		field2.setValue(sourceObject.getOverDraftLimit() == null ? null : sourceObject.getOverDraftLimit().toString());
		screenData.addField(field2);

		FieldDataDTO field3 = new FieldDataDTO();
		field3.setFieldId("minimumBalanceRequired");
		field3.setValue(sourceObject.getMinimumBalanceRequired() == null ? null : sourceObject.getMinimumBalanceRequired().toString());
		screenData.addField(field3);

		FieldDataDTO field4 = new FieldDataDTO();
		field4.setFieldId("eligibleAdvance");
		field4.setValue(sourceObject.getEligibleAdvance() == null ? null : sourceObject.getEligibleAdvance().toString());
		screenData.addField(field4);

		FieldDataDTO field5 = new FieldDataDTO();
		field5.setFieldId("accuredInterestOfThisYear");
		field5.setValue(sourceObject.getAccuredInterestOfThisYear() == null ? null : sourceObject.getAccuredInterestOfThisYear().toString());
		screenData.addField(field5);

		FieldDataDTO field6 = new FieldDataDTO();
		field6.setFieldId("preYearAccruedInterest");
		field6.setValue(sourceObject.getPreYearAccruedInterest() == null ? null : sourceObject.getPreYearAccruedInterest().toString());
		screenData.addField(field6);

		FieldDataDTO field7 = new FieldDataDTO();
		field7.setFieldId("withdrawalBalance");
		if (sourceObject.getWithdrawalBalance() != null) {
		    field7.setValue(sourceObject.getWithdrawalBalance().toString());
		}
		// field7.setValue(sourceObject.getWithdrawalBalance() == null ? null : sourceObject.getWithdrawalBalance().toString());
		screenData.addField(field7);

		FieldDataDTO field8 = new FieldDataDTO();
		field8.setFieldId("unclearedFunds");
		field8.setValue(sourceObject.getUnclearedFunds() == null ? null : sourceObject.getUnclearedFunds().toString());
		screenData.addField(field8);

		FieldDataDTO field9 = new FieldDataDTO();
		field9.setFieldId("onHoldAmount");
		field9.setValue(sourceObject.getOnHoldAmount() == null ? null : sourceObject.getOnHoldAmount().toString());
		screenData.addField(field9);

		FieldDataDTO field10 = new FieldDataDTO();
		field10.setFieldId("currentBalance");
		if (sourceObject.getCurrentBalance() != null) {
		    field10.setValue(sourceObject.getCurrentBalance().toString());
		}
		// field10.setValue(sourceObject.getCurrentBalance() == null ? null : sourceObject.getCurrentBalance().toString());
		screenData.addField(field10);

		FieldDataDTO field11 = new FieldDataDTO();
		field11.setFieldId("availableBalance");
		if (sourceObject.getAvailableBalance() != null) {
		    field11.setValue(sourceObject.getAvailableBalance().toString());
		}
		// field11.setValue(sourceObject.getAvailableBalance() == null ? null : sourceObject.getAvailableBalance().toString());
		screenData.addField(field11);

		FieldDataDTO field12 = new FieldDataDTO();
		field12.setFieldId("accountHolders");
		field12.setValue(sourceObject.getAccountHolders());
		screenData.addField(field12);

		FieldDataDTO field14 = new FieldDataDTO();
		field14.setFieldId("desc");
		field14.setValue(sourceObject.getDesc());
		screenData.addField(field14);
	    }

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
