package com.barclays.bmg.dao.accountservices.adapter.ssa;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.barclays.bem.Branch.Branch;
import com.barclays.bem.CreditCardBasic.CreditCardBasic;
import com.barclays.bem.CreditCardPayment.CreditCardPayment;
import com.barclays.bem.IndividualName.IndividualName;
import com.barclays.bem.TransactionAccount.CreditCardExpiryDateType;
import com.barclays.bem.TransactionAccount.TransactionAccount;
import com.barclays.bem.TransactionFxRate.TransactionFxRate;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.service.request.PayBillServiceRequest;
import com.barclays.bmg.utils.DateTimeUtil;

public class CreditCardPayPayloadAdapter {

	public CreditCardPayment adaptPayload(WorkContext workContext){



		DAOContext daoContext = (DAOContext)workContext;

		Object[] args = daoContext.getArguments();

		PayBillServiceRequest payBillServiceRequest=
									(PayBillServiceRequest)args[0];

		CreditCardPayment requestBody = mapDataInRequest(payBillServiceRequest);
		return requestBody;
	}

	private CreditCardPayment mapDataInRequest(PayBillServiceRequest payBillServiceRequest){

		CustomerAccountDTO frmAct = payBillServiceRequest.getFromAccount();
		BeneficiaryDTO beneficiaryDTO = payBillServiceRequest.getBeneficiaryDTO();
		CreditCardPayment  creditCardPayment = new CreditCardPayment ();
		Context context = payBillServiceRequest.getContext();
      TransactionAccount fromAccount= new TransactionAccount();
        //FromAccount.AccountNumber
        fromAccount.setAccountNumber(frmAct.getAccountNumber());
        //FromAccount.Currency
        fromAccount.setAccountCurrencyCode(frmAct.getCurrency());

        creditCardPayment.setDebitAccount(fromAccount);
        //Debit Amount

        creditCardPayment.setDebitAmount(payBillServiceRequest.getBillAmount().doubleValue());


        // Mapping Payee account number.
        TransactionAccount beneficiaryAccount = new TransactionAccount();
        beneficiaryAccount.setAccountCurrencyCode(beneficiaryDTO.getCurrency());
        beneficiaryAccount.setAccountNumber(beneficiaryDTO.getDestinationAccountNumber());

        // Mapping from account branch code.
        Branch fromBranch = new Branch();
        fromBranch.setBranchCode(frmAct.getBranchCode());

        // Mapping beneficiary branch code.
        Branch beneficiaryBranchCode = new Branch();
        beneficiaryBranchCode.setBranchCode(beneficiaryDTO.getDestinationBranchCode());

        creditCardPayment.setDebitBranch(fromBranch);
        creditCardPayment.setBeneficiaryBranch(beneficiaryBranchCode);
        creditCardPayment.setBeneficiaryAccount(beneficiaryAccount);
       //Commented BY ME
        //creditCardPayment.setTransferAmountLCY(transactionDTO.getAmountInLCY().getAmount().doubleValue());
        creditCardPayment.setPostTo("Card");
        creditCardPayment.setHostTransactionTypeCode("LOCALINTERNETCC");
		// Credit Card Migration-Plan no. Addition
		Map<String, Object> contextMap = context.getContextMap();
		String planNumber = (String) contextMap.get(SystemParameterConstant.PLAN_NUMBER_BILL_PAYMENT);
		if (null != planNumber) {
			creditCardPayment.setPlan(planNumber);
		} else {
			creditCardPayment.setPlan("10002");
		}

        //Mapping Transaction Date and Value Date.

        creditCardPayment.setOriginationDate(Calendar.getInstance());
        creditCardPayment.setValueDate(Calendar.getInstance());

        CreditCardBasic card= new CreditCardBasic();
        card.setCreditCardNumber(beneficiaryDTO.getCardNumber());
		// First Vision Changes for expiry date
		if (null != beneficiaryDTO && null != beneficiaryDTO.getCreditCardExpiryDate()) {
			Date date = beneficiaryDTO.getCreditCardExpiryDate();

			if (date != null) {
				CreditCardExpiryDateType creditCardExpiryDate = new CreditCardExpiryDateType();
				creditCardExpiryDate.setCardExpiryMonth(DateTimeUtil.getDayMonthYearFromDate(date, "MM"));
				creditCardExpiryDate.setCardExpiryYear(DateTimeUtil.getDayMonthYearFromDate(date, "YY"));
				card.setCreditCardExpiryDate(creditCardExpiryDate);
			}
		}
        // Credit card number
        creditCardPayment.setCreditCardNumber(card);
        //transaction type code
        creditCardPayment.setTransactionTypeCode(BillPaymentConstants.TXN_FACADE_RTN_TYPE_CREDIT_CARD);

        creditCardPayment.setNarrationText(payBillServiceRequest.getTxnNote());

        IndividualName name= new IndividualName();
        name.setFullName(beneficiaryDTO.getBeneficiaryName());
        creditCardPayment.setBeneficiaryName(name);

        creditCardPayment.setDebitAccountTypeCode(frmAct.getProductCode());
        if(payBillServiceRequest.getAmtInLCY()!=null){
        	 creditCardPayment.setTransferAmountLCY(payBillServiceRequest.getAmtInLCY().doubleValue());
        }


        // Passing product type code for own credit card payment
        if(beneficiaryDTO.getDestinationAccount()!=null){
        	creditCardPayment.setCreditAccountTypeCode(beneficiaryDTO.getDestinationAccount().getProductCode());
        }
        //Commented BY ME
        TransactionFxRate fXRate = new TransactionFxRate();
        //FX Rate
        fXRate.setEffectiveFXRate(new Double(1.0));
        creditCardPayment.setTransactionFxRate(fXRate);
        creditCardPayment.setCreditAmount(payBillServiceRequest.getBillAmount().doubleValue());
        //dest.setChargeInfo(ChargesToChargeDetails.convertToChargeDetails(payBillServiceRequest.getCharges()));*/
        return creditCardPayment;
	}
}
