package com.barclays.bmg.dao.adapter.fundtransfer.domestic.ssa;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.barclays.bem.Bank.Bank;
import com.barclays.bem.BillPayment.BillTransactionReferenceDetails;
import com.barclays.bem.Branch.Branch;
import com.barclays.bem.ChargeDetails.ChargeDetails;
import com.barclays.bem.DomesticFundTransfer.DomesticFundTransfer;
import com.barclays.bem.ISOBankCode.ISOBankCode;
import com.barclays.bem.IndividualBeneficiary.IndividualBeneficiary;
import com.barclays.bem.IndividualName.IndividualName;
import com.barclays.bem.MemoText.MemoText;
import com.barclays.bem.TransactionAccount.TransactionAccount;
import com.barclays.bem.TransactionFxRate.TransactionFxRate;
import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.Charge;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.FxRateDTO;
import com.barclays.bmg.service.request.DomesticFundTransferServiceRequest;
import com.barclays.bmg.utils.BMBCommonUtility;

public class DomesticFundTransferPayloadAdapter {

	private static final String PAYMENT_SUB_CATEGORY_IT = "LOCALREGPAYT";
	private static final String PAYMENT_SUB_CATEGORY_OWN = "TRANSFERTRANSFER";
	private static final String PAYMENT_SUB_CATEGORY_UR = "LOCALUNREGPAYT";
	private static final String PAYMENT_SUB_CATEGORY_EX = "PAYT";
	private static final String GHIPPS_FT_OB_TRANSACTIN_CATEGORY_CODE ="Account2Account";
	// private static final String PAYMENT_TYPE_URGENT = "URGENT";

	public DomesticFundTransfer adaptPayload(WorkContext workContext) {

		DAOContext daoContext = (DAOContext) workContext;

		Object[] args = daoContext.getArguments();

		DomesticFundTransferServiceRequest domesticFTRequest = (DomesticFundTransferServiceRequest) args[0];

		DomesticFundTransfer domesticFundTransfer=null;
		if(FundTransferConstants.TXN_TYPE_FUND_TRANSFER_GHIPPS.equals(domesticFTRequest.getTxnTyp())){
			domesticFundTransfer = adaptPayLoadForGHIPSS(domesticFTRequest);
		}else{
			domesticFundTransfer = adaptPayLoad(domesticFTRequest);
		}

		return domesticFundTransfer;
	}

	private DomesticFundTransfer adaptPayLoad(DomesticFundTransferServiceRequest domesticFTRequest) {

		DomesticFundTransfer dest = new DomesticFundTransfer();
		CustomerAccountDTO sourceAcct = domesticFTRequest.getSourceAcct();
		BeneficiaryDTO beneficiaryDTO = domesticFTRequest.getBeneficiaryDTO();
		FxRateDTO fxRateDTO = domesticFTRequest.getFxRateDTO();
		Calendar today = Calendar.getInstance();
		TransactionAccount fromAccount = new TransactionAccount();
		// FromAccount.AccountNumber
		fromAccount.setAccountNumber(sourceAcct.getAccountNumber());
		// FromAccount.Currency
		fromAccount.setAccountCurrencyCode(sourceAcct.getCurrency());
		dest.setDebitAccount(fromAccount);

		dest.setDebitAccountTypeCode(sourceAcct.getProductCode());
		if (beneficiaryDTO.getDestinationAccount() != null) {
			dest.setCreditAccountTypeCode(beneficiaryDTO.getDestinationAccount().getProductCode());
		}

		// SSA Changes Start

		Branch bb = new Branch();
		bb.setBranchCode(sourceAcct.getBranchCode());
		dest.setDebitAccountBranch(bb);

		IndividualBeneficiary beneficiary = new IndividualBeneficiary();

		Bank bank = new Bank();
		ISOBankCode isoBankCode = new ISOBankCode();
		isoBankCode.setBankCode(beneficiaryDTO.getDestinationBankCode());
		isoBankCode.setBranchCode(beneficiaryDTO.getDestinationBranchCode());
		bank.setISOBankCode(isoBankCode);

		beneficiary.setBeneficiaryBank(bank);
		dest.setBeneficiaryInfo(beneficiary);

		Branch branch = new Branch();
		branch.setBranchCode(beneficiaryDTO.getDestinationBranchCode());
		dest.setBeneficiaryBranchCode(branch);

		// fx rate
		if (fxRateDTO != null && fxRateDTO.getEffectiveFXRate() != null) {
			TransactionFxRate transactionFxRate = new TransactionFxRate();
			transactionFxRate.setEffectiveFXRate(fxRateDTO.getEffectiveFXRate().doubleValue());
			dest.setTransactionFxRate(transactionFxRate);
		} else {
			TransactionFxRate transactionFxRate = new TransactionFxRate();
			transactionFxRate.setEffectiveFXRate(new Double(1.0));
			dest.setTransactionFxRate(transactionFxRate);
		}

		if (fxRateDTO != null && fxRateDTO.getEquivalentAmount() != null) {
			dest.setDebitAmount(fxRateDTO.getEquivalentAmount().doubleValue());
		}

		/*
		 * if(FundTransferConstants.TXN_TYPE_OWN_FUND_TRANSFER.equals(domesticFTRequest.getTxnTyp())){ // own
		 * dest.setTransactionCategoryCode(PAYMENT_TYPE_TRANSFER);
		 * //dest.getBeneficiaryInfo().getBeneficiaryBank().getISOBankCode().setBankCode(null); } else
		 * if(FundTransferConstants.TXN_TYPE_FUND_TRANSFER_INTERNAL.equals(domesticFTRequest.getTxnTyp())){ // internal
		 * dest.setTransactionCategoryCode(PAYMENT_TYPE_LOCAL); }else
		 * if(FundTransferConstants.TXN_TYPE_FUND_TRANSFER_EXTERNAL.equals(domesticFTRequest.getTxnTyp())){ // external
		 * dest.setTransactionCategoryCode(PAYMENT_TYPE_LOCAL); }
		 */

		// eBox Charges Changes Start
		if (ActivityIdConstantBean.FUND_TRANSFER_OWN_ACTIVITY_ID.equals(domesticFTRequest.getContext().getActivityId())) {
			// dest.setTransactionCategoryCode(PAYMENT_TYPE_TRANSFER);
			setTxnSubCategory(domesticFTRequest, dest, PAYMENT_SUB_CATEGORY_OWN);
		} else if (ActivityIdConstantBean.FUND_TRANSFER_INTERNAL_PAYEE_ACTIVITY_ID.equals(domesticFTRequest.getContext().getActivityId())) {
			// dest.setTransactionCategoryCode(PAYMENT_TYPE_LOCAL);
			setTxnSubCategory(domesticFTRequest, dest, PAYMENT_SUB_CATEGORY_IT);
		} else if (ActivityIdConstantBean.FUND_TRANSFER_EXTERNAL_PAYEE_ACTIVITY_ID.equals(domesticFTRequest.getContext().getActivityId())) {
			// dest.setTransactionCategoryCode(PAYMENT_TYPE_LOCAL);
			setTxnSubCategory(domesticFTRequest, dest, PAYMENT_SUB_CATEGORY_EX);
		} else if (ActivityIdConstantBean.FUND_TRANSFER_INTERNAL_ONETIME_ACTIVITY_ID.equals(domesticFTRequest.getContext().getActivityId())) {
			// dest.setTransactionCategoryCode(PAYMENT_TYPE_LOCAL);
			setTxnSubCategory(domesticFTRequest, dest, PAYMENT_SUB_CATEGORY_UR);
		}
		// eBox Charges Changes End

		// SSA Changes end

		// Debit Amount
		if (fxRateDTO != null && fxRateDTO.getEquivalentAmount() != null) {
			dest.setDebitAmount(fxRateDTO.getEquivalentAmount().doubleValue());
			// Add by Li Can[Rel3] start in order to Pass debit amount value as credit amount to bem; 13 Oct 2011
			dest.setCreditAmount(domesticFTRequest.getTxnAmt().getAmount().doubleValue());
			// Add by Li Can[Rel3] end in order to Pass debit amount value as credit amount to bem; 13 Oct 2011
		} else {
			dest.setDebitAmount(domesticFTRequest.getTxnAmt().getAmount().doubleValue());
			// Add by Li Can[Rel3] start in order to Pass debit amount value as credit amount to bem; 13 Oct 2011
			dest.setCreditAmount(domesticFTRequest.getTxnAmt().getAmount().doubleValue());
			// Add by Li Can[Rel3] end in order to Pass debit amount value as credit amount to bem; 13 Oct 2011
		}
		dest.setDebitValueDate(today);
		TransactionAccount beneficiaryAccount = new TransactionAccount();

		// destinationAccountNumber
		// Add start by Li, Can to padding zero before the account number. 28Feb, 2011
		String originalAccountNumber = beneficiaryDTO.getDestinationAccountNumber();
		String newAccountNumber = "";

		// IndividualBeneficiary beneficiary = new IndividualBeneficiary();
		beneficiary.setIBANFlag(beneficiaryDTO.isIbanFlag());
		String businessId=domesticFTRequest.getContext().getBusinessId();
		if(businessId!=null && businessId.equals("MZBRB"))
			beneficiary.setNIB(beneficiaryDTO.getNib());
		dest.setBeneficiaryInfo(beneficiary);
		if (beneficiaryDTO.isIbanFlag()) {
			newAccountNumber = originalAccountNumber;
		} else {
			Map<String, Object> sysMap = domesticFTRequest.getContext().getContextMap();
			String fullLenth = (String) sysMap.get(SystemParameterConstant.SYSPARAM_FULLLENGTH_DOMESTIC_ACCOUNTNUMBER);
			newAccountNumber = BMBCommonUtility.paddingZeros(originalAccountNumber, fullLenth);
		}
		// Add by Li, Can end; IBAN Change; Feb 22 2012;
		beneficiaryAccount.setAccountNumber(newAccountNumber);
		// Add end by Li, Can to padding zero before the account number. 28Feb, 2011
		// START: Add by Li, Can in order to pass customer full name in the request, which is in order to protect and inform customer from fraud
		// attack Aug/22/2011
		dest.setInitiatingCustomerFullName(domesticFTRequest.getContext().getFullName());
		// END: Add by Li, Can in order to pass customer full name in the request, which is in order to protect and inform customer from fraud attack
		// Aug/22/2011
		// destination currency
		if (beneficiaryDTO.getCurrency() == null) {
			beneficiaryAccount.setAccountCurrencyCode(domesticFTRequest.getContext().getLocalCurrency());
		} else {
			beneficiaryAccount.setAccountCurrencyCode(beneficiaryDTO.getCurrency());
		}

		// beneficiary account
		dest.setBeneficiaryAccount(beneficiaryAccount);

		//
		IndividualName name = new IndividualName();
		name.setFullName(beneficiaryDTO.getBeneficiaryName());
		dest.setBeneficiaryName(name);

		// dest.setTransactionTypeCode(domesticFTRequest.getTxnTyp());
		if (FundTransferConstants.TXN_TYPE_FUND_TRANSFER_EXTERNAL.equals(domesticFTRequest.getTxnTyp())) {
			dest.setTransactionTypeCode(FundTransferConstants.TXN_TYPE_FUND_TRANSFER_EXTERNAL);
		} else {
			dest.setTransactionTypeCode(FundTransferConstants.TXN_TYPE_FUND_TRANSFER_INTERNAL);
		}

		// payment remark
		dest.setNarrationText(domesticFTRequest.getTxnNot());
		dest.setIsServiceChargeApplicable(false);
		if (businessId != null && (businessId.equalsIgnoreCase(CommonConstants.MZBRB_BUSINESS_ID)||
				businessId.equalsIgnoreCase(CommonConstants.KEBRB_BUSINESS_ID)|| businessId.equalsIgnoreCase(CommonConstants.TZNBC_BUSINESS_ID) )) {
			dest.setIsServiceChargeApplicable(true);
		}

			//Kadikope - BEGIN

		today.setTime(getBusinessDate(domesticFTRequest.getContext()));

		if (domesticFTRequest.getSourceAcct() instanceof CreditCardAccountDTO) {
		CreditCardAccountDTO cardAccountDTO = (CreditCardAccountDTO) domesticFTRequest.getSourceAcct();
		dest.setCreditCardNumber(cardAccountDTO.getPrimary().getCardNumber());
		dest.setFromCreditCardAccountNumber(cardAccountDTO.getAccountNumber());
		dest.setCreditCardAccountOrgCode(cardAccountDTO.getOrg());
		dest.setActionCode("FTC7");
		dest.setStoreNumber("999100001");
		dest.setMerchantCode("163");
		dest.setPONumber(domesticFTRequest.getContext().getActivityRefNo());
		dest.setMemoTextInfo(getMemoTextInfo(domesticFTRequest, today));
		}
	//Kadikope - END

		/* Regulatory Requirement Changes - 23/10/2017
		 *
		// MakeDomesticFundTransfer for Other Barclays change - CPB 30/05
		ChargeDetails chargeDetails = null;
    	if(domesticFTRequest.getChargeDTO() !=null && domesticFTRequest.getChargeDTO().getCpbMakeBillPaymentFlag().equals("setDomesticFundOthBarclaysFields")){
    		chargeDetails = new ChargeDetails();
    		chargeDetails.setChargeAmount(domesticFTRequest.getChargeDTO().getCpbChargeAmount());
    		chargeDetails.setFeeGLAccount(domesticFTRequest.getChargeDTO().getFeeGLAccount());
    		chargeDetails.setChargeNarration(domesticFTRequest.getChargeDTO().getChargeNarration());
    		chargeDetails.setTaxAmount(domesticFTRequest.getChargeDTO().getTaxAmount());
    		chargeDetails.setTaxGLAccount(domesticFTRequest.getChargeDTO().getTaxGLAccount());
    		dest.setChargeInfo(new ChargeDetails[] {chargeDetails });
    	}else if(domesticFTRequest.getChargeDTO() !=null && domesticFTRequest.getChargeDTO().getCpbMakeBillPaymentFlag().equals("xelerateOffline")){
    		chargeDetails = new ChargeDetails();
    		chargeDetails.setChargeAmount(domesticFTRequest.getChargeDTO().getCpbChargeAmount());
    		chargeDetails.setTaxAmount(domesticFTRequest.getChargeDTO().getTaxAmount());
    		dest.setChargeInfo(new ChargeDetails[] {chargeDetails });
    	}
		*/


    	/* Regulatory Requirement Changes - 23/10/2017
		 *

    	// MakeDomesticFundTransfer for other barclay A/C - BillTransactionReferenceDetails 20/09/2017
    	String activityId = domesticFTRequest.getContext().getActivityId();
    	String opCode = domesticFTRequest.getContext().getOpCde();
    	Charge cbpCharge = domesticFTRequest.getChargeDTO();
    	if(opCode!= null && (opCode.equalsIgnoreCase("OP0502") || opCode.equalsIgnoreCase("OP0570"))
    			&& domesticFTRequest.getContext().getBusinessId().equalsIgnoreCase("KEBRB")
    			&& (activityId.equals("PMT_FT_INTERNAL_PAYEE") || activityId.equals("PMT_FT_INTERNAL_ONETIME"))){

    		if(cbpCharge!=null){
    			if(domesticFTRequest.getChargeDTO().getCpbMakeBillPaymentFlag()!= null &&
    					domesticFTRequest.getChargeDTO().getCpbMakeBillPaymentFlag().equals("setDomesticFundOthBarclaysFields")){
    				BillTransactionReferenceDetails[] fundTransferReferenceDetailsArray=new BillTransactionReferenceDetails[1];

    				BillTransactionReferenceDetails fundTransferReferenceDetails1 = new BillTransactionReferenceDetails();
    				fundTransferReferenceDetails1.setTypeCode("XelerateReferenceNumber");
    				fundTransferReferenceDetails1.setValue(domesticFTRequest.getChargeDTO().getValue());

    				fundTransferReferenceDetailsArray[0]=fundTransferReferenceDetails1;

    				dest.setFundTransferReferenceDetails(fundTransferReferenceDetailsArray);
    			}
    		}

    	}
    	*/

		return dest;
	}



    private MemoText getMemoTextInfo(DomesticFundTransferServiceRequest domesticFTRequest, Calendar today) {
    	MemoText memotextinfo = new MemoText();
    	memotextinfo.setLine1("SHM CARD TO CASA:" + domesticFTRequest.getBeneficiaryDTO().getDestinationAccountNumber() );
    	memotextinfo.setLine2("POREF:" + domesticFTRequest.getContext().getActivityRefNo());
    	memotextinfo.setLine3("SHM CARD TO CASA:" + domesticFTRequest.getTxnAmt().getAmount().doubleValue() );
    	memotextinfo.setLine4("SHM CARD TO CASA:" + getFormattedDate(today));
    	return memotextinfo;
        }


        // ---To format the date in MemoLine date...
        private String getFormattedDate(Calendar today) {
    	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    	String formatted = format1.format(today.getTime());
    	return formatted;

        }

        // -- To synup V+ date in test env..
        private Date getBusinessDate(Context context) {

    	Date returnDate = null;
    	try {
    	    String businessDate = (String) context.getContextMap().get(SystemParameterConstant.BUSINESS_DATE);
    	    String dateFormatKey = (String) context.getContextMap().get(SystemParameterConstant.SHORT_DATE_FORMAT_KEY);
    	    DateFormat df = new SimpleDateFormat(dateFormatKey);
    	    if (businessDate == null || businessDate.equals("")) {
    		returnDate = new Date();
    	    } else {
    		returnDate = df.parse(businessDate);
    	    }
    	} catch (Exception e) {
    	    returnDate = new Date();
    	}
    	return returnDate;
        }

	/**
	 * Set txn sub category for LCY-LCY transaction
	 *
	 * @param domesticFTRequest
	 * @param dest
	 */
	private void setTxnSubCategory(DomesticFundTransferServiceRequest domesticFTRequest, DomesticFundTransfer dest, String paymentCode) {
		CustomerAccountDTO sourceAcct = domesticFTRequest.getSourceAcct();
		BeneficiaryDTO beneficiaryDTO = domesticFTRequest.getBeneficiaryDTO();

		String fromCurrency = sourceAcct.getCurrency();

		String localCurrency = domesticFTRequest.getContext().getLocalCurrency();
		String toCurrency = beneficiaryDTO.getCurrency() != null ? beneficiaryDTO.getCurrency() : localCurrency;

		if (localCurrency != null) {
			if ((localCurrency.equalsIgnoreCase(fromCurrency)) && (localCurrency.equalsIgnoreCase(toCurrency))) {
				if (FundTransferConstants.TXN_TYPE_FUND_TRANSFER_EXTERNAL.equals(domesticFTRequest.getTxnTyp())) {
					dest.setTransactionCategoryCode("LOCAL");
				}
				dest.setTransactionSubCategoryCode(paymentCode);
			}
		}
	}

	private DomesticFundTransfer adaptPayLoadForGHIPSS(DomesticFundTransferServiceRequest domesticFTRequest) {

		DomesticFundTransfer dest = new DomesticFundTransfer();
		CustomerAccountDTO sourceAcct = domesticFTRequest.getSourceAcct();
		BeneficiaryDTO beneficiaryDTO = domesticFTRequest.getBeneficiaryDTO();
		Calendar today = Calendar.getInstance();
		// FromAccount Details:
		TransactionAccount fromAccount = new TransactionAccount();
		String branchCode=sourceAcct.getBranchCode();
		for(int i=branchCode.length();i<3;i++){
			branchCode="0"+branchCode;
		}
		fromAccount.setAccountNumber(branchCode+sourceAcct.getAccountNumber());
		fromAccount.setAccountTypeCode("");
		fromAccount.setAccountCurrencyCode(sourceAcct.getCurrency());
		fromAccount.setBankClearingCode("03");
		dest.setDebitAccount(fromAccount);
		dest.setDebitAccountTypeCode(sourceAcct.getProductCode());
		if(beneficiaryDTO==null){
			beneficiaryDTO=new BeneficiaryDTO();
		}
		if (beneficiaryDTO.getDestinationAccount() != null) {
			dest.setCreditAccountTypeCode(beneficiaryDTO.getDestinationAccount().getProductCode());
		}
		Branch bb = new Branch();
		bb.setBranchCode(sourceAcct.getBranchCode());
		dest.setDebitAccountBranch(bb);

		// To Account Details (Beneficiary Details)
		IndividualBeneficiary beneficiary = new IndividualBeneficiary();
		Bank bank = new Bank();
		ISOBankCode isoBankCode = new ISOBankCode();
		isoBankCode.setBankCode(beneficiaryDTO.getDestinationBankCode());
		if(!FundTransferConstants.TXN_TYPE_FUND_TRANSFER_GHIPPS.equals(domesticFTRequest.getTxnTyp())){
			isoBankCode.setBranchCode(beneficiaryDTO.getDestinationBranchCode());
		}
		bank.setISOBankCode(isoBankCode);
		bank.setBankName(beneficiaryDTO.getDestinationBankName());
		beneficiary.setBeneficiaryBank(bank);
		dest.setBeneficiaryInfo(beneficiary);
		if(!FundTransferConstants.TXN_TYPE_FUND_TRANSFER_GHIPPS.equals(domesticFTRequest.getTxnTyp())){
			Branch branch = new Branch();
			branch.setBranchCode(beneficiaryDTO.getDestinationBranchCode());
			dest.setBeneficiaryBranchCode(branch);
		}
		dest.setTransactionCategoryCode(GHIPPS_FT_OB_TRANSACTIN_CATEGORY_CODE);

		// Credit Amount
		dest.setCreditAmount(domesticFTRequest.getTxnAmt().getAmount().doubleValue());
		dest.setDebitValueDate(today);
		TransactionAccount beneficiaryAccount = new TransactionAccount();
		String originalAccountNumber = beneficiaryDTO.getDestinationAccountNumber();
		Map<String, Object> sysMap = domesticFTRequest.getContext().getContextMap();
		String fullLenth = (String) sysMap.get(SystemParameterConstant.SYSPARAM_FULLLENGTH_DOMESTIC_ACCOUNTNUMBER);
		String newAccountNumber = BMBCommonUtility.paddingZeros(originalAccountNumber, fullLenth);
		beneficiaryAccount.setAccountNumber(newAccountNumber);
		beneficiaryAccount.setAccountTypeCode("");
		dest.setInitiatingCustomerFullName(domesticFTRequest.getContext().getFullName());
		if (beneficiaryDTO.getCurrency() == null) {
			beneficiaryAccount.setAccountCurrencyCode(domesticFTRequest.getContext().getLocalCurrency());
		} else {
			beneficiaryAccount.setAccountCurrencyCode(beneficiaryDTO.getCurrency());
		}
		dest.setBeneficiaryAccount(beneficiaryAccount);

		IndividualName name = new IndividualName();
		name.setFullName(beneficiaryDTO.getBeneficiaryName());
		dest.setBeneficiaryName(name);
		dest.setTransactionTypeCode(FundTransferConstants.TXN_TYPE_FUND_TRANSFER_GHIPPS);
		dest.setNarrationText(domesticFTRequest.getTxnNot());
		ChargeDetails chargeDetails = new ChargeDetails();
		chargeDetails.setChargeTypeCode("OUR");
		chargeDetails.setChargeAmount(0.0);
		dest.setChargeInfo(new ChargeDetails[] {chargeDetails });
		return dest;
	}
}
