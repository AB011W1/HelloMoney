package com.barclays.bmg.audit.builder.impl;

import java.util.Calendar;
import java.util.Date;

import com.barclays.bmg.audit.dto.FieldDataDTO;
import com.barclays.bmg.audit.dto.ScreenDataDTO;
import com.barclays.bmg.audit.dto.TransactionAuditDTO;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.operation.request.fundtransfer.DomesticFundTransferExecuteOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.nonregistered.internal.RetrieveInternalNonRegisteredPayeeInfoOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.DomesticFundTransferExecuteOperationResponse;
import com.barclays.bmg.service.utils.BMGFormatUtils;
import com.barclays.bmg.utils.DateTimeUtil;
import com.ibm.icu.text.SimpleDateFormat;

public class DomesticFundTransferTransactionAuditBuilder extends AbstractTransactionAuditBuilder {
    private final static String SCRNNM_OWN = "PMT_FT_OWN_CONFIRM";
    private final static String SCRNNM_INTERNAL = "PMT_FT_INTERNAL_CONFIRM";
    private final static String SCRNNM_EXTERNAL = "PMT_FT_EXTERNAL_CONFIRM";
    private static final String DATE_FORMAT = "dd/MM/yy HH:mm:ss";

    @Override
    public TransactionAuditDTO buildFromRequest(Object[] args, Object result) {
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	ScreenDataDTO screenData = new ScreenDataDTO();
	if (args[0] instanceof RetrieveInternalNonRegisteredPayeeInfoOperationRequest) {
	    return transactionAuditDTO;
	}
	DomesticFundTransferExecuteOperationRequest request = (DomesticFundTransferExecuteOperationRequest) args[0];

	TransactionDTO dto = request.getTransactionDTO();

	String toAcctNo = dto.getBeneficiaryDTO().getDestinationAccountNumber();
	//kadikope
	String fromAcctNo = "";
	if (dto.getSourceAcct() != null) {
			if (dto.getSourceAcct() instanceof CreditCardAccountDTO) {
				CreditCardAccountDTO creditCardDTO = (CreditCardAccountDTO) dto
						.getSourceAcct();
				fromAcctNo = creditCardDTO.getPrimary().getCardNumber();
			} else {
				fromAcctNo = dto.getSourceAcct().getAccountNumber();
			}
		}
	//String fromAcctNo = dto.getSourceAcct().getAccountNumber();

	String txnType = dto.getTxnType();
	transactionAuditDTO.setFromAccount(fromAcctNo);
	transactionAuditDTO.setToAccount(toAcctNo);

	Date txnDate = Calendar.getInstance().getTime();
	transactionAuditDTO.setTransactionDateTime(txnDate);

	FieldDataDTO fieldDataDTO = new FieldDataDTO();
	fieldDataDTO.setFieldId(AuditConstant.MKDFRMACCTNO);
	fieldDataDTO.setValue(new BMGFormatUtils().maskAccount(fromAcctNo));
	screenData.addField(fieldDataDTO);

	fieldDataDTO = new FieldDataDTO();
	fieldDataDTO.setFieldId(AuditConstant.MKDTOACCTNO);
	fieldDataDTO.setValue(new BMGFormatUtils().maskAccount(toAcctNo));
	screenData.addField(fieldDataDTO);

	fieldDataDTO = new FieldDataDTO();
	fieldDataDTO.setFieldId(AuditConstant.SRCNKNAM);
	fieldDataDTO.setValue(dto.getSourceAcct().getAccountNickName());
	screenData.addField(fieldDataDTO);

	Context context = request.getContext();

	fieldDataDTO = new FieldDataDTO();
	fieldDataDTO.setFieldId(AuditConstant.CUSTID);
	fieldDataDTO.setValue(context.getCustomerId());
	screenData.addField(fieldDataDTO);

	fieldDataDTO = new FieldDataDTO();
	fieldDataDTO.setFieldId(AuditConstant.MOBNO);
	fieldDataDTO.setValue(context.getMobilePhone());
	screenData.addField(fieldDataDTO);

	fieldDataDTO = new FieldDataDTO();
	fieldDataDTO.setFieldId(AuditConstant.SERNAM);
	fieldDataDTO.setValue(context.getActivityId());
	screenData.addField(fieldDataDTO);

	FieldDataDTO field10 = new FieldDataDTO();
	field10.setFieldId(AuditConstant.CNTRYDTTM);
	Calendar cal = DateTimeUtil.getCurrentBusinessCalendar(request.getContext());
	SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	String date = sdf.format(cal.getTime());
	field10.setValue(date);
	screenData.addField(field10);

	if (FundTransferConstants.TXN_TYPE_OWN_FUND_TRANSFER.equals(txnType)) {
	    fieldDataDTO = new FieldDataDTO();
	    fieldDataDTO.setFieldId(AuditConstant.SERTYP);
	    fieldDataDTO.setValue(FundTransferConstants.SER_TYP_FD_OWN);
	    screenData.addField(fieldDataDTO);

	    fieldDataDTO = new FieldDataDTO();
	    fieldDataDTO.setFieldId(AuditConstant.TXNSUBTYP);
	    fieldDataDTO.setValue(FundTransferConstants.SER_SUB_TYP_FD_OWN);
	    screenData.addField(fieldDataDTO);

	    screenData.setScreenId(SCRNNM_OWN);
	} else if (FundTransferConstants.TXN_TYPE_FUND_TRANSFER_EXTERNAL.equals(txnType)) {
	    fieldDataDTO = new FieldDataDTO();
	    fieldDataDTO.setFieldId(AuditConstant.SERTYP);
	    fieldDataDTO.setValue(FundTransferConstants.SER_TYP_FD_EXT);
	    screenData.addField(fieldDataDTO);

	    fieldDataDTO = new FieldDataDTO();
	    fieldDataDTO.setFieldId(AuditConstant.TXNSUBTYP);
	    fieldDataDTO.setValue(FundTransferConstants.SER_SUB_TYP_FD_EXT);
	    screenData.addField(fieldDataDTO);

	    screenData.setScreenId(SCRNNM_EXTERNAL);
	} else if (FundTransferConstants.TXN_TYPE_FUND_TRANSFER_INTERNAL.equals(txnType)) {
	    fieldDataDTO = new FieldDataDTO();
	    fieldDataDTO.setFieldId(AuditConstant.SERTYP);
	    fieldDataDTO.setValue(FundTransferConstants.SER_TYP_FD_INT);
	    screenData.addField(fieldDataDTO);

	    fieldDataDTO = new FieldDataDTO();
	    fieldDataDTO.setFieldId(AuditConstant.TXNSUBTYP);
	    fieldDataDTO.setValue(FundTransferConstants.SER_SUB_TYP_FD_INT);
	    screenData.addField(fieldDataDTO);

	    screenData.setScreenId(SCRNNM_INTERNAL);
	}
	transactionAuditDTO.setReqRes(buildData(screenData));

	return transactionAuditDTO;
    }

    @Override
    public TransactionAuditDTO buildFromResponse(Object[] args, Object result) {
	TransactionAuditDTO transactionAuditDTO = new TransactionAuditDTO();
	ScreenDataDTO screenData = new ScreenDataDTO();
	DomesticFundTransferExecuteOperationResponse response = (DomesticFundTransferExecuteOperationResponse) result;
	DomesticFundTransferExecuteOperationRequest request = (DomesticFundTransferExecuteOperationRequest) args[0];
	FieldDataDTO fieldDataDTO = new FieldDataDTO();

	if (request != null) {

	    TransactionDTO dto = request.getTransactionDTO();

	    String toAcctNo = dto.getBeneficiaryDTO().getDestinationAccountNumber();
	    //kadikope
		String fromAcctNo = "";
		if (dto.getSourceAcct() != null) {
				if (dto.getSourceAcct() instanceof CreditCardAccountDTO) {
					CreditCardAccountDTO creditCardDTO = (CreditCardAccountDTO) dto
							.getSourceAcct();
					fromAcctNo = creditCardDTO.getPrimary().getCardNumber();
				} else {
					fromAcctNo = dto.getSourceAcct().getAccountNumber();
				}
			}

	   // String fromAcctNo = dto.getSourceAcct().getAccountNumber();
	    Double txnAmount = dto.getTxnAmt().getAmount().doubleValue();

	    String currency = dto.getTxnAmt().getCurrency();
	    String txnType = dto.getTxnType();
	    transactionAuditDTO.setFromAccount(fromAcctNo);
	    transactionAuditDTO.setToAccount(toAcctNo);
	    transactionAuditDTO.setTransactionAmount(txnAmount);
	    transactionAuditDTO.setTransactionCurrency(currency);

	    Date txnDate = Calendar.getInstance().getTime();
	    transactionAuditDTO.setTransactionDateTime(txnDate);

	    fieldDataDTO.setFieldId(AuditConstant.MKDFRMACCTNO);
	    fieldDataDTO.setValue(new BMGFormatUtils().maskAccount(fromAcctNo));
	    screenData.addField(fieldDataDTO);

	    fieldDataDTO = new FieldDataDTO();
	    fieldDataDTO.setFieldId(AuditConstant.MKDTOACCTNO);
	    fieldDataDTO.setValue(new BMGFormatUtils().maskAccount(toAcctNo));
	    screenData.addField(fieldDataDTO);

	    fieldDataDTO = new FieldDataDTO();
	    fieldDataDTO.setFieldId(AuditConstant.SRCNKNAM);
	    fieldDataDTO.setValue(dto.getSourceAcct().getAccountNickName());
	    screenData.addField(fieldDataDTO);

	    Context context = request.getContext();

	    fieldDataDTO = new FieldDataDTO();
	    fieldDataDTO.setFieldId(AuditConstant.CUSTID);
	    fieldDataDTO.setValue(context.getCustomerId());
	    screenData.addField(fieldDataDTO);

	    fieldDataDTO = new FieldDataDTO();
	    fieldDataDTO.setFieldId(AuditConstant.MOBNO);
	    fieldDataDTO.setValue(context.getMobilePhone());
	    screenData.addField(fieldDataDTO);

	    fieldDataDTO = new FieldDataDTO();
	    fieldDataDTO.setFieldId(AuditConstant.SERNAM);
	    fieldDataDTO.setValue(context.getActivityId());
	    screenData.addField(fieldDataDTO);

	    FieldDataDTO field10 = new FieldDataDTO();
	    field10.setFieldId(AuditConstant.CNTRYDTTM);
	    Calendar cal = DateTimeUtil.getCurrentBusinessCalendar(request.getContext());
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	    String date = sdf.format(cal.getTime());
	    field10.setValue(date);
	    screenData.addField(field10);

	    if (FundTransferConstants.TXN_TYPE_OWN_FUND_TRANSFER.equals(txnType)) {
		fieldDataDTO = new FieldDataDTO();
		fieldDataDTO.setFieldId(AuditConstant.SERTYP);
		fieldDataDTO.setValue(FundTransferConstants.SER_TYP_FD_OWN);
		screenData.addField(fieldDataDTO);

		fieldDataDTO = new FieldDataDTO();
		fieldDataDTO.setFieldId(AuditConstant.TXNSUBTYP);
		fieldDataDTO.setValue(FundTransferConstants.SER_SUB_TYP_FD_OWN);
		screenData.addField(fieldDataDTO);

		screenData.setScreenId(SCRNNM_OWN);
	    } else if (FundTransferConstants.TXN_TYPE_FUND_TRANSFER_EXTERNAL.equals(txnType)) {
		fieldDataDTO = new FieldDataDTO();
		fieldDataDTO.setFieldId(AuditConstant.SERTYP);
		fieldDataDTO.setValue(FundTransferConstants.SER_TYP_FD_EXT);
		screenData.addField(fieldDataDTO);

		fieldDataDTO = new FieldDataDTO();
		fieldDataDTO.setFieldId(AuditConstant.TXNSUBTYP);
		fieldDataDTO.setValue(FundTransferConstants.SER_SUB_TYP_FD_EXT);
		screenData.addField(fieldDataDTO);

		screenData.setScreenId(SCRNNM_EXTERNAL);
	    } else if (FundTransferConstants.TXN_TYPE_FUND_TRANSFER_INTERNAL.equals(txnType)) {
		fieldDataDTO = new FieldDataDTO();
		fieldDataDTO.setFieldId(AuditConstant.SERTYP);
		fieldDataDTO.setValue(FundTransferConstants.SER_TYP_FD_INT);
		screenData.addField(fieldDataDTO);

		fieldDataDTO = new FieldDataDTO();
		fieldDataDTO.setFieldId(AuditConstant.TXNSUBTYP);
		fieldDataDTO.setValue(FundTransferConstants.SER_SUB_TYP_FD_INT);
		screenData.addField(fieldDataDTO);

		screenData.setScreenId(SCRNNM_INTERNAL);
	    }
	}
	if (response != null) {
	    transactionAuditDTO.setTransactionRefNo(response.getTrnRef());
	    if (response.isSuccess()) {
		transactionAuditDTO.setTransactionStatus(AuditConstant.TXN_STATUS_SUCCESS);

		/*
		 * FieldDataDTO field18 = new FieldDataDTO(); field18.setFieldId(AuditConstant.ATTRIBUTE_NAME1);
		 * field18.setValue("Transaction Message"); screenData.addField(field18);
		 *
		 *
		 * FieldDataDTO field19 = new FieldDataDTO(); field19.setFieldId(AuditConstant.ATTRIBUTE_VALUE1);
		 * field18.setValue(response.getTxnMsg()); screenData.addField(field19);
		 */
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