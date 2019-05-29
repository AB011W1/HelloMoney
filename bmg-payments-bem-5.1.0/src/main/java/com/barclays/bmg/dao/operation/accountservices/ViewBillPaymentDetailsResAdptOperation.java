package com.barclays.bmg.dao.operation.accountservices;

import java.math.BigDecimal;

import com.barclays.bem.ViewBillPaymentDetails.ViewBillPaymentDetailsResponse;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.BillPaymentHistory;
import com.barclays.bmg.dto.Charges;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.service.response.ViewTxnHistoryDetailsServiceResponse;

/**
 * @author BTCI
 * 
 *         Response Adapter for View Bill Payment Detail BEM Response
 */
public class ViewBillPaymentDetailsResAdptOperation extends AbstractResAdptOperationAcct {

    /**
     * @param workContext
     * @param obj
     * @return ViewTxnHistoryDetailsServiceResponse
     */
    public ViewTxnHistoryDetailsServiceResponse adaptResponse(WorkContext workContext, Object obj) {

	ViewTxnHistoryDetailsServiceResponse response = new ViewTxnHistoryDetailsServiceResponse();
	BillPaymentHistory history = null;
	ViewBillPaymentDetailsResponse bemResponse = (ViewBillPaymentDetailsResponse) obj;
	com.barclays.bem.BillPaymentHistory.BillPaymentHistory billPaymentHistory = null;
	if (bemResponse != null) {
	    if (null != bemResponse.getBillPaymentHostoryDetails()) {
		billPaymentHistory = bemResponse.getBillPaymentHostoryDetails(0);
		history = mapToBillPaymentHistory(billPaymentHistory);
	    }
	    response.setBillPaymentHistory(history);
	    if (checkResponseHeader(bemResponse.getResponseHeader())) {
		response.setSuccess(true);
	    } else {
		if (bemResponse.getResponseHeader() != null && bemResponse.getResponseHeader().getServiceResStatus() != null)
		    response.setResMsg(bemResponse.getResponseHeader().getServiceResStatus().getServiceResDesc());
		response.setSuccess(false);
	    }
	} else {
	    response.setSuccess(false);
	}
	return response;
    }

    /**
     * @param billPaymentHistory
     * @return BillPaymentHistory Converter class for BEM BillPaymentHistory to BMG BillPaymentHistory
     */
    private BillPaymentHistory mapToBillPaymentHistory(com.barclays.bem.BillPaymentHistory.BillPaymentHistory billPaymentHistory) {

	BillPaymentHistory history = new com.barclays.bmg.dto.BillPaymentHistory();
	CustomerAccountDTO fromAccount = new CustomerAccountDTO();

	fromAccount.setAccountNumber(billPaymentHistory.getDebitAccount().getAccountNumber());
	fromAccount.setCurrency(billPaymentHistory.getDebitAccount().getAccountCurrencyCode());
	fromAccount.setProductCode(billPaymentHistory.getDebitAccountTypeCode());
	if (null != billPaymentHistory.getDebitAcountBranch()) {
	    fromAccount.setBranchCode(billPaymentHistory.getDebitAcountBranch().getBranchCode());
	}
	history.setFromAccount(fromAccount);
	history.setBillerType(billPaymentHistory.getBillerTypeCode());
	history.setBillerId(billPaymentHistory.getBillerCode());
	history.setBillerName(billPaymentHistory.getBillerName());
	history.setBillReferenceNumber(billPaymentHistory.getReferenceNumber());
	history.setTransactionDate(billPaymentHistory.getTransactionDateTime().getTime());
	history.setTransactionReferenceNumber(billPaymentHistory.getTrxReferenceNumber());
	history.setStatus(billPaymentHistory.getTransactionStatusCode());

	if (null != billPaymentHistory.getDebitAmount()) {
	    Amount amount = new Amount(billPaymentHistory.getDebitAccount().getAccountCurrencyCode(), BigDecimal.valueOf(billPaymentHistory
		    .getDebitAmount()));
	    history.setAmount(amount);
	}

	if (null != billPaymentHistory.getFeeDetails()) {
	    history.setChargesDebitMode(billPaymentHistory.getFeeDetails().getChargeTypeCode());
	    if (null != billPaymentHistory.getFeeDetails().getChargeAccount()) {
		CustomerAccountDTO chargeDebitedAccount = new CustomerAccountDTO();
		chargeDebitedAccount.setAccountNumber(billPaymentHistory.getFeeDetails().getChargeAccount().getAccountNumber());
		chargeDebitedAccount.setCurrency(billPaymentHistory.getFeeDetails().getChargeAccount().getAccountCurrencyCode());
		history.setChargeDebitedAccount(chargeDebitedAccount);
	    }
	    if (null != billPaymentHistory.getFeeDetails().getChargeAmount()
		    && billPaymentHistory.getFeeDetails().getChargeAmount().doubleValue() != 0) {
		Charges charges = new Charges();
		Amount totalFeeAmount = new Amount();
		totalFeeAmount.setAmount(new BigDecimal(billPaymentHistory.getFeeDetails().getChargeAmount()));
		totalFeeAmount.setCurrency(billPaymentHistory.getFeeDetails().getChargeAmountCurrencyCode());
		charges.setTotalFeeAmount(totalFeeAmount);
		history.setCharges(charges);
	    }
	}

	if (history.getBeneficiary() == null) {
	    history.setBeneficiary(new BeneficiaryDTO());
	}

	if (null != billPaymentHistory.getInitiatingCustomerName() && null != billPaymentHistory.getInitiatingCustomerName().getFullName()) {
	    history.getBeneficiary().setBeneficiaryName(billPaymentHistory.getInitiatingCustomerName().getFullName());
	}

	if (null != billPaymentHistory.getCustomerAddress() && null != billPaymentHistory.getCustomerAddress().getUnStructuredAddress()) {
	    history.getBeneficiary().setDestinationAddressLine1(billPaymentHistory.getCustomerAddress().getUnStructuredAddress().getAddressLine1());
	    history.getBeneficiary().setDestinationAddressLine2(billPaymentHistory.getCustomerAddress().getUnStructuredAddress().getAddressLine2());
	    history.getBeneficiary().setDestinationAddressLine3(billPaymentHistory.getCustomerAddress().getUnStructuredAddress().getAddressLine3());
	}

	return history;
    }

}
