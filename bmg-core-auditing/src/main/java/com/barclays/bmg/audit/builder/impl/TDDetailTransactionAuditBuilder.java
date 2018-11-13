package com.barclays.bmg.audit.builder.impl;

import com.barclays.bmg.audit.dto.FieldDataDTO;
import com.barclays.bmg.audit.dto.ScreenDataDTO;
import com.barclays.bmg.audit.dto.TransactionAuditDTO;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.dto.TdAccountDTO;
import com.barclays.bmg.operation.accountdetails.request.TDAccountDetailsOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.TDAccountDetailsOperationResponse;
import com.barclays.bmg.utils.DateTimeUtil;

public class TDDetailTransactionAuditBuilder extends AbstractTransactionAuditBuilder {

    private static final String ACT_TD_DETAIL = "ACT_TD_DETAIL";

    @Override
    public TransactionAuditDTO buildFromRequest(Object[] args, Object result) {
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	ScreenDataDTO screenData = new ScreenDataDTO();
	TDAccountDetailsOperationRequest request = (TDAccountDetailsOperationRequest) args[0];

	screenData.setScreenId(ACT_TD_DETAIL);

	String actNo = request.getAcctNo();

	FieldDataDTO fieldDataDTO = new FieldDataDTO(AuditConstant.ACCOUNT_NUMBER, actNo);
	screenData.addField(fieldDataDTO);
	transactionAuditDTO.setReqRes(buildData(screenData));
	return transactionAuditDTO;

    }

    @Override
    public TransactionAuditDTO buildFromResponse(Object[] args, Object result) {
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	ScreenDataDTO screenData = new ScreenDataDTO();

	TDAccountDetailsOperationResponse response = (TDAccountDetailsOperationResponse) result;

	screenData.setScreenId(ACT_TD_DETAIL);

	if (response != null) {

	    TdAccountDTO sourceObject = response.getTdAccountDTO();

	    FieldDataDTO field1 = new FieldDataDTO();
	    field1.setFieldId(AuditConstant.ACCOUNT_NUMBER);
	    field1.setValue(sourceObject.getAccountNumber());
	    screenData.addField(field1);

	    FieldDataDTO field2 = new FieldDataDTO();
	    field2.setFieldId(AuditConstant.INTEREST_RATE);
	    field2
		    .setValue(sourceObject.getDepositDTO().getInterestRate() == null ? null : sourceObject.getDepositDTO().getInterestRate()
			    .toString());
	    screenData.addField(field2);

	    FieldDataDTO field3 = new FieldDataDTO();
	    field3.setFieldId(AuditConstant.MATURITY_AMOUNT);
	    field3.setValue(sourceObject.getDepositDTO().getMaturityAmount() == null ? null : sourceObject.getDepositDTO().getMaturityAmount()
		    .toString());
	    screenData.addField(field3);

	    FieldDataDTO field4 = new FieldDataDTO();
	    field4.setFieldId(AuditConstant.MATURITY_DATE);
	    field4.setValue(sourceObject.getDepositDTO().getMaturityDate() == null ? null : DateTimeUtil.getStringFromDate(sourceObject
		    .getDepositDTO().getMaturityDate(), "yyyy-MM-dd"));
	    screenData.addField(field4);

	    FieldDataDTO field5 = new FieldDataDTO();
	    field5.setFieldId(AuditConstant.TENURE_TERM);
	    field5.setValue(sourceObject.getDepositDTO().getTenureTerm().getTenureValue());
	    screenData.addField(field5);

	    FieldDataDTO field6 = new FieldDataDTO();
	    field6.setFieldId(AuditConstant.VALUE_DATE);
	    field6.setValue(sourceObject.getDepositDTO().getValueDate() == null ? null : DateTimeUtil.getStringFromDate(sourceObject.getDepositDTO()
		    .getValueDate(), "yyyy-MM-dd"));
	    screenData.addField(field6);

	    FieldDataDTO field7 = new FieldDataDTO();
	    field7.setFieldId(AuditConstant.AVAILABLE_FOR_REDEMPTION);
	    field7.setValue(sourceObject.getDepositDTO().getAvailableForRedemption() == null ? null : sourceObject.getDepositDTO()
		    .getAvailableForRedemption().toString());
	    screenData.addField(field7);

	    FieldDataDTO field8 = new FieldDataDTO();
	    field8.setFieldId(AuditConstant.INTEREST_PAID_TO_DATE);
	    field8.setValue(sourceObject.getDepositDTO().getInterestPaidToDate() == null ? null : sourceObject.getDepositDTO()
		    .getInterestPaidToDate().toString());
	    screenData.addField(field8);

	    FieldDataDTO field9 = new FieldDataDTO();
	    field9.setFieldId(AuditConstant.LAST_INTEREST_PAYMENT_DATE);
	    field9.setValue(sourceObject.getDepositDTO().getLastInterestPaymentDate() == null ? null : DateTimeUtil.getStringFromDate(sourceObject
		    .getDepositDTO().getLastInterestPaymentDate(), "yyyy-MM-dd"));
	    screenData.addField(field9);

	    FieldDataDTO field10 = new FieldDataDTO();
	    field10.setFieldId(AuditConstant.LIEN_AMOUNT);
	    field10.setValue(sourceObject.getDepositDTO().getLienAmount() == null ? null : sourceObject.getDepositDTO().getLienAmount().toString());
	    screenData.addField(field10);

	    FieldDataDTO field11 = new FieldDataDTO();
	    field11.setFieldId(AuditConstant.NEXT_INTEREST_PAYMENT_DATE);
	    field11.setValue(sourceObject.getDepositDTO().getNextInterestPaymentDate() == null ? null : DateTimeUtil.getStringFromDate(sourceObject
		    .getDepositDTO().getNextInterestPaymentDate(), "yyyy-MM-dd"));
	    screenData.addField(field11);

	    FieldDataDTO field12 = new FieldDataDTO();
	    field12.setFieldId(AuditConstant.PROJECTED_INTEREST_AMOUNT);
	    field12.setValue(sourceObject.getDepositDTO().getProjectedInterestAmount() == null ? null : sourceObject.getDepositDTO()
		    .getProjectedInterestAmount().toString());
	    screenData.addField(field12);

	    FieldDataDTO field13 = new FieldDataDTO();
	    field13.setFieldId(AuditConstant.TD_PRINCIPAL_BALANCE);
	    field13.setValue(sourceObject.getDepositDTO().getTdPrincipalBalance() == null ? null : sourceObject.getDepositDTO()
		    .getTdPrincipalBalance().toString());
	    screenData.addField(field13);

	    FieldDataDTO field14 = new FieldDataDTO();
	    field14.setFieldId(AuditConstant.YEAR_TO_DATE_TAX);
	    field14.setValue(sourceObject.getDepositDTO().getYearToDateTax() == null ? null : sourceObject.getDepositDTO().getYearToDateTax()
		    .toString());
	    screenData.addField(field14);

	    FieldDataDTO field15 = new FieldDataDTO();
	    field15.setFieldId(AuditConstant.RENEWAL);
	    field15.setValue(sourceObject.getDepositDTO().getRenewal().getDescription());
	    screenData.addField(field15);

	    FieldDataDTO field16 = new FieldDataDTO();
	    field16.setFieldId(AuditConstant.RENEWAL);
	    field16.setValue(sourceObject.getDesc());
	    screenData.addField(field16);

	    if (response.isSuccess()) {
		transactionAuditDTO.setTransactionStatus(AuditConstant.TXN_STATUS_SUCCESS);

	    } else {
		transactionAuditDTO.setTransactionStatus(AuditConstant.TXN_STATUS_FAILURE);
		transactionAuditDTO.setErrorMessage(response.getResMsg());

		FieldDataDTO field17 = new FieldDataDTO();
		field17.setFieldId(AuditConstant.FAILURE_REASON);
		field17.setValue(response.getResMsg());
		screenData.addField(field17);

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
