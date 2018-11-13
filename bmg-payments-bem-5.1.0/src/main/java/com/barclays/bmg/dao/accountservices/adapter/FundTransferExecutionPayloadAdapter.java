package com.barclays.bmg.dao.accountservices.adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.barclays.bem.Bank.Bank;
import com.barclays.bem.Branch.Branch;
import com.barclays.bem.ChargeDetails.ChargeDetails;
import com.barclays.bem.DomesticFundTransfer.DomesticFundTransfer;
import com.barclays.bem.ISOBankCode.ISOBankCode;
import com.barclays.bem.IndividualBeneficiary.IndividualBeneficiary;
import com.barclays.bem.TransactionAccount.TransactionAccount;
import com.barclays.bem.TransactionFxRate.TransactionFxRate;
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.service.request.FundTransferExecuteServiceRequest;

/**
 * @author e20027734
 *  Create pay load for OWN Fund Transfer.
 */
public class FundTransferExecutionPayloadAdapter {

	private static final String PAYMENT_TYPE_TRANSFER = "TRANSFER";
	private static final String PAYMENT_TYPE_LOCAL = "LOCAL";

	public DomesticFundTransfer adaptPayload(WorkContext workContext){



		//BEMReqHeader reqHeader = new BEMReqHeader();

		DAOContext daoContext = (DAOContext)workContext;

		Object[] args = daoContext.getArguments();

		FundTransferExecuteServiceRequest fTExecuteRequest =
									(FundTransferExecuteServiceRequest)args[0];

		DomesticFundTransfer domesticFundTransfer  = adaptPayLoad(fTExecuteRequest);



		return domesticFundTransfer;
	}

	/**
	 * @param fTExecuteRequest
	 * @return
	 *  Create request for Domestic fund transfer.
	 */
	private DomesticFundTransfer adaptPayLoad(FundTransferExecuteServiceRequest fTExecuteRequest){
		DomesticFundTransfer domesticFundTransfer = new DomesticFundTransfer();

		TransactionAccount fromAccount = new TransactionAccount();
		CustomerAccountDTO fromAcct = fTExecuteRequest.getFrmAcct();
		CustomerAccountDTO toAcct = fTExecuteRequest.getToAcct();
		fromAccount.setAccountNumber(fromAcct.getAccountNumber());
		fromAccount.setAccountCurrencyCode(fromAcct.getCurrency());

		domesticFundTransfer.setDebitAccount(fromAccount);
		//HARDCODING Customer full name.
		//domesticFundTransfer.setDebitAccountTypeCode("0");
		domesticFundTransfer.setDebitAccountTypeCode(fromAcct.getProductCode());
		Branch debitAccountBranch = new Branch();
		//debitAccountBranch.setBranchCode("41");
		debitAccountBranch.setBranchCode(fromAcct.getBranchCode());
		domesticFundTransfer.setDebitAccountBranch(debitAccountBranch);
		domesticFundTransfer.setDebitAmount(new Double(fTExecuteRequest.getTxnAmount()));
		domesticFundTransfer.setDebitValueDate(Calendar.getInstance());

		TransactionAccount beneficiaryAccount = new TransactionAccount();
		beneficiaryAccount.setAccountNumber(toAcct.getAccountNumber());
		beneficiaryAccount.setAccountCurrencyCode(toAcct.getCurrency());
		domesticFundTransfer.setBeneficiaryAccount(beneficiaryAccount);

		//domesticFundTransfer.setCreditAccountTypeCode("72");
		domesticFundTransfer.setCreditAccountTypeCode(toAcct.getProductCode());


		IndividualBeneficiary individualBeneficiary = new IndividualBeneficiary();
		Bank beneficiaryBank = new Bank();
		ISOBankCode isoBankCode = new ISOBankCode();
		//isoBankCode.setBankCode("41");
		isoBankCode.setBankCode(toAcct.getBranchCode());
		beneficiaryBank.setISOBankCode(isoBankCode);
		individualBeneficiary.setBeneficiaryBank(beneficiaryBank);
		individualBeneficiary.setBeneficiaryName(fTExecuteRequest.getBeneName());
		domesticFundTransfer.setBeneficiaryInfo(individualBeneficiary);

		Branch beneficiaryBranch = new Branch();
		beneficiaryBranch.setBranchCode(toAcct.getBranchCode());
		//beneficiaryBranch.setBranchCode("41");
		domesticFundTransfer.setBeneficiaryBranchCode(beneficiaryBranch);
		domesticFundTransfer.setCreditAmount(new Double(fTExecuteRequest.getTxnAmount()));
		domesticFundTransfer.setNarrationText(fTExecuteRequest.getTxnNote());
		List<ChargeDetails> chargeList = new ArrayList<ChargeDetails>();

		domesticFundTransfer.setChargeInfo(chargeList.toArray(new ChargeDetails[0]));

		// payment type
		String strLocalCurrency = fTExecuteRequest.getContext().getLocalCurrency();
		if (fromAcct.getCurrency() != null

				&& fromAcct.getCurrency().equals(strLocalCurrency)) {
			// no need to pass payment type

			if(fTExecuteRequest.getTxnType().equals(FundTransferConstants.TXN_TYPE_FUND_TRANSFER_EXTERNAL)){
				// external

				domesticFundTransfer.setTransactionCategoryCode(PAYMENT_TYPE_LOCAL);
			}
		} else {
			if(fTExecuteRequest.getTxnType().equals(FundTransferConstants.TXN_TYPE_OWN_FUND_TRANSFER)){
				// own

				domesticFundTransfer.setTransactionCategoryCode(PAYMENT_TYPE_TRANSFER);
				domesticFundTransfer.getBeneficiaryInfo().getBeneficiaryBank().getISOBankCode().setBankCode(null);
			} else if(fTExecuteRequest.getTxnType().equals(FundTransferConstants.TXN_TYPE_FUND_TRANSFER_INTERNAL)){
				// internal

				domesticFundTransfer.setTransactionCategoryCode(PAYMENT_TYPE_LOCAL);
			} else if(fTExecuteRequest.getTxnType().equals(FundTransferConstants.TXN_TYPE_FUND_TRANSFER_EXTERNAL)){
				// external

				domesticFundTransfer.setTransactionCategoryCode(PAYMENT_TYPE_LOCAL);
			}
		}

		// Transaction Type Code
		domesticFundTransfer.setTransactionTypeCode(fTExecuteRequest.getTxnType());


		//domesticFundTransfer.setTransactionTypeCode(RequestConstants.FUND_TRANSFER_INTERNAL);
		if(fTExecuteRequest.getFxRate()!=null){
		TransactionFxRate fxRate = new TransactionFxRate();
		fxRate.setEffectiveFXRate(fTExecuteRequest.getFxRate().doubleValue());
		domesticFundTransfer.setTransactionFxRate(fxRate);
		}
		//domesticFundTransfer.setInitiatingCustomerFullName("DISPLAY NAME");
		domesticFundTransfer.setIsServiceChargeApplicable(false);
        String businessId = fTExecuteRequest.getContext().getBusinessId();
    	if (businessId != null && (businessId.equalsIgnoreCase(CommonConstants.MZBRB_BUSINESS_ID)||
    			businessId.equalsIgnoreCase(CommonConstants.KEBRB_BUSINESS_ID)|| businessId.equalsIgnoreCase(CommonConstants.TZNBC_BUSINESS_ID) )) {
    		domesticFundTransfer.setIsServiceChargeApplicable(true);
    	}
		return domesticFundTransfer;
	}
}
