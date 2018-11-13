package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.Branch.Branch;
import com.barclays.bem.RetrieveFXRate.FxRateRequestInfo;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.fxrate.service.request.FxRateServiceRequest;

public class FxRatePayloadAdapter {

    private static final String PAYMENT_TYPE_TRANSFER = "TRANSFER";
    private static final String PAYMENT_TYPE_LOCAL = "LOCAL";
    private static final String PAYMENT_TYPE_XBORDER = "XBORDER";
    private static final String TXN_TYPE_FUND_TRANSFER_INTERNATIONAL = "INTL";
    private static final String PAYMENT_TYPE_BANK_DRAFT = "DRAFT";
    private static final String AMOUNT_TYPE_CODE_1006 = "1006";

    public FxRateRequestInfo adaptPayload(WorkContext workContext) {

	FxRateRequestInfo requestBody = new FxRateRequestInfo();
	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	FxRateServiceRequest fxRateServiceRequest = (FxRateServiceRequest) args[0];
	CustomerAccountDTO frmAcct = fxRateServiceRequest.getFrmCustActDTO();
	CustomerAccountDTO toAcct = fxRateServiceRequest.getToCustActDTO();

	requestBody.setSourceCurrencyCode(frmAcct.getCurrency());
	requestBody.setTargetCurrencyCode(toAcct.getCurrency());
	requestBody.setAmountForFXRate(fxRateServiceRequest.getTxnAmt().doubleValue());

	Branch branch = new Branch();
	branch.setBranchCode(frmAcct.getBranchCode());
	requestBody.setAccountBranch(branch);
	requestBody.setAccountNumber(frmAcct.getAccountNumber());
	requestBody.setTransactionCategoryCode(fxRateServiceRequest.getTxnType());

	if (FundTransferConstants.TXN_TYPE_OWN_FUND_TRANSFER.equals(fxRateServiceRequest.getTxnType())) {
	    // own

	    requestBody.setTransactionCategoryCode(PAYMENT_TYPE_TRANSFER);
	} else if (FundTransferConstants.TXN_TYPE_FUND_TRANSFER_INTERNAL.equals(fxRateServiceRequest.getTxnType())) {
	    // internal

	    requestBody.setTransactionCategoryCode(PAYMENT_TYPE_LOCAL);
	} else if (FundTransferConstants.TXN_TYPE_FUND_TRANSFER_EXTERNAL.equals(fxRateServiceRequest.getTxnType())) {
	    requestBody.setTransactionCategoryCode(PAYMENT_TYPE_LOCAL);
	} else if (TXN_TYPE_FUND_TRANSFER_INTERNATIONAL.equals(fxRateServiceRequest.getTxnType())) {
	    requestBody.setTransactionCategoryCode(PAYMENT_TYPE_XBORDER);
	} else if (FundTransferConstants.TXN_TYPE_BANK_DRAFT.equals(fxRateServiceRequest.getTxnType())
		|| FundTransferConstants.TXN_TYPE_MANAGERS_CHEQUE.equals(fxRateServiceRequest.getTxnType())) {
	    requestBody.setTransactionCategoryCode(PAYMENT_TYPE_BANK_DRAFT);
	}

	requestBody.setAmountTypeCode(AMOUNT_TYPE_CODE_1006);

	requestBody.setProductTypeCode(frmAcct.getProductCode());

	return requestBody;
    }
}
