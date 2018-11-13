package com.barclays.bmg.dao.accountservices.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.barclays.bem.Bank.Bank;
import com.barclays.bem.Branch.Branch;
import com.barclays.bem.ChargeDetails.ChargeDetails;
import com.barclays.bem.ISOBankCode.ISOBankCode;
import com.barclays.bem.IndividualName.IndividualName;
import com.barclays.bem.InternationalFundTransfer.InternationalFundTransfer;
import com.barclays.bem.PostalAddress.PostalAddress;
import com.barclays.bem.PostalAddress.UnstructuredAddress;
import com.barclays.bem.Remitter.Remitter;
import com.barclays.bem.TransactionAccount.TransactionAccount;
import com.barclays.bem.TransactionFxRate.TransactionFxRate;
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.service.request.InternationalFundTransferServiceRequest;

public class InternationalFundTransferPayloadAdapter {

    private static final String FILTERED_REASON = "MIS";

    private static final String KEBRB_FILTERED_REASON_MIS_INV = "INV";
    private static final String KEBRB_FILTERED_REASON_MIS_RBC = "RBC";
    private static final String KEBRB_FILTERED_REASON_MIS_ROC = "ROC";

    private static final String PAYMENT = "PAYMENT";
    private static final String PAYMENT_TYPE_XBORDER = "XBORDER";
    private static final String PAYMENT_TYPE_URGENT = "URGENT";
    private static final String INTERNETPAYT = "INTERNETPAYT";

    private static final String PAYMENT_TYPE_LOCAL = "LOCAL";
    private static final String EXTERNAL_TXN_TYPE = "DT";
    private static final String KENYA_EXTERNAL_TXN_TYPE = "INT2";

    public static final String BUSINESS_DATE = "BUSINESS_DATE";
    public static final String SHORT_DATE_FORMAT_KEY = "dateFormatting_shortDateFormat";

    public InternationalFundTransfer adaptPayload(WorkContext workContext) {

	InternationalFundTransfer requestBody = new InternationalFundTransfer();

	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	InternationalFundTransferServiceRequest request = (InternationalFundTransferServiceRequest) args[0];
	mapRequest(request, requestBody);
	return requestBody;
    }

    public void mapRequest(InternationalFundTransferServiceRequest request, InternationalFundTransfer requestBody) {

	Context context = request.getContext();
	if (requestBody.getDebitAccountNumberInfo() == null) {
	    requestBody.setDebitAccountNumberInfo(new TransactionAccount());
	}
	CustomerAccountDTO fromAcct = request.getSourceAcct();
	BeneficiaryDTO beneficiaryDTO = request.getBeneficiaryDTO();

	requestBody.getDebitAccountNumberInfo().setAccountNumber(fromAcct.getAccountNumber());
	requestBody.getDebitAccountNumberInfo().setAccountCurrencyCode(fromAcct.getCurrency());
	requestBody.setProductCode(fromAcct.getProductCode());
	requestBody.setRemarks(request.getTxnNot());

	IndividualName individualName = new IndividualName();
	if (beneficiaryDTO != null) {
	    individualName.setFullName(beneficiaryDTO.getBeneficiaryName());
	}
	requestBody.setBeneficiaryName_IndividualBeneficiary(individualName);
	if (beneficiaryDTO != null) {
	    requestBody.setBeneficiaryName(beneficiaryDTO.getBeneficiaryName());
	}

	if (beneficiaryDTO != null) {
	    requestBody.setIBANFlag(beneficiaryDTO.getDestinationAccountIbanFlg());
	}
	if (requestBody.getBeneficiaryPostalAddress_IndividualBeneficiary() == null) {
	    requestBody.setBeneficiaryPostalAddress_IndividualBeneficiary(new PostalAddress());
	}
	if (requestBody.getBeneficiaryPostalAddress_IndividualBeneficiary().getUnStructuredAddress() == null) {
	    requestBody.getBeneficiaryPostalAddress_IndividualBeneficiary().setUnStructuredAddress(new UnstructuredAddress());
	}

	Bank beneficiaryBank = requestBody.getBeneficiaryBank();
	if (beneficiaryBank == null) {
	    beneficiaryBank = new Bank();
	}

	if (beneficiaryBank.getBankAddress() == null) {
	    beneficiaryBank.setBankAddress(new PostalAddress());
	}
	if (beneficiaryBank.getBankAddress().getUnStructuredAddress() == null) {
	    beneficiaryBank.getBankAddress().setUnStructuredAddress(new UnstructuredAddress());
	}
	if (beneficiaryBank.getISOBankCode() == null) {
	    beneficiaryBank.setISOBankCode(new ISOBankCode());
	}

	requestBody.setBeneficiaryBank(beneficiaryBank);

	Bank intermediaryBank = requestBody.getIntermediatoryBankInfo();
	if (intermediaryBank == null) {
	    intermediaryBank = new Bank();
	}
	if (intermediaryBank.getBankAddress() == null)
	    intermediaryBank.setBankAddress(new PostalAddress());
	if (intermediaryBank.getBankAddress().getUnStructuredAddress() == null)
	    intermediaryBank.getBankAddress().setUnStructuredAddress(new UnstructuredAddress());
	if (intermediaryBank.getISOBankCode() == null)
	    intermediaryBank.setISOBankCode(new ISOBankCode());

	requestBody.setIntermediatoryBankInfo(intermediaryBank);

	if (beneficiaryDTO != null) {
	    if (requestBody.getBeneficiaryAccountInfo() == null) {
		requestBody.setBeneficiaryAccountInfo(new TransactionAccount());
	    }

	    requestBody.getBeneficiaryPostalAddress_IndividualBeneficiary().getUnStructuredAddress().setAddressLine1(
		    beneficiaryDTO.getDestinationAddressLine1());
	    requestBody.getBeneficiaryPostalAddress_IndividualBeneficiary().getUnStructuredAddress().setAddressLine2(
		    beneficiaryDTO.getDestinationAddressLine2());
	    requestBody.getBeneficiaryPostalAddress_IndividualBeneficiary().getUnStructuredAddress().setCountryCode(
		    beneficiaryDTO.getDestinationIsoCountryCode());

	    if (requestBody.getBeneficiaryAccountInfo() != null) {
		requestBody.getBeneficiaryAccountInfo().setAccountNumber(beneficiaryDTO.getDestinationAccountNumber());
	    }

	    if (beneficiaryDTO.getDestinationAccountIbanFlg() == true) {
		requestBody.setIBANAccountNumber(beneficiaryDTO.getDestinationAccountNumber());
	    }

	    beneficiaryBank.getBankAddress().getUnStructuredAddress().setAddressLine1(beneficiaryDTO.getDestinationBankAddressLine1());
	    beneficiaryBank.getBankAddress().getUnStructuredAddress().setAddressLine2(beneficiaryDTO.getDestinationBankAddressLine2());
	    beneficiaryBank.getBankAddress().getUnStructuredAddress().setCountryCode(beneficiaryDTO.getDestinationBankIsoCountryCode());

	    beneficiaryBank.setBankSWIFTCode(beneficiaryDTO.getDestinationBankSwiftCode());

	    if (beneficiaryDTO.getDestinationBankName() != null && beneficiaryDTO.getDestinationBankName().length() > 34) {
		beneficiaryBank.setBankName(beneficiaryDTO.getDestinationBankName().substring(0, 34));
	    } else {
		beneficiaryBank.setBankName(beneficiaryDTO.getDestinationBankName());
	    }
	    beneficiaryBank.setBranchName(beneficiaryDTO.getDestinationBranchName());
	    // beneficiaryBank.getISOBankCode().setCountryCode(source.getBeneficiary().getDestinationBankIsoCountryCode());
	    requestBody.setClearingCode(beneficiaryDTO.getDestinationBankClearingNetCode());

	    if (!beneficiaryDTO.getDestinationAccountIbanFlg()) {
		// String clearingCode = "//" +
		// source.getBeneficiary().getDestinationBankClearingNetCode() +
		// source.getBeneficiary().getDestinationBankClearingCode();
		// beneficiaryBank.setBankClearingCode(clearingCode);
		beneficiaryBank.setBankClearingNetworkCode(beneficiaryDTO.getDestinationBankClearingNetCode());
		beneficiaryBank.setBankClearingCode(beneficiaryDTO.getDestinationBankClearingCode());
	    }

	    intermediaryBank.setBankSWIFTCode(beneficiaryDTO.getIntermediaryBankSwiftCode());
	    if (beneficiaryDTO.getIntermediaryBankName() != null && beneficiaryDTO.getIntermediaryBankName().length() > 34) {
		intermediaryBank.setBankName(beneficiaryDTO.getIntermediaryBankName().substring(0, 34));
	    } else {
		intermediaryBank.setBankName(beneficiaryDTO.getIntermediaryBankName());
	    }

	    intermediaryBank.getBankAddress().getUnStructuredAddress().setAddressLine1(beneficiaryDTO.getIntermediaryBankAddressLine1());
	    intermediaryBank.getBankAddress().getUnStructuredAddress().setAddressLine2(beneficiaryDTO.getIntermediaryBankAddressLine2());
	    intermediaryBank.getBankAddress().getUnStructuredAddress().setCountryCode(beneficiaryDTO.getIntermediaryBankIsoCountryCode());

	    // intermediaryBank.getISOBankCode().setCountryCode(source.getBeneficiary().getIntermediaryBankIsoCountryCode());
	    /*
	     * if(beneficiaryDTO.getIntermediaryBankClearingNetCode()!=null && !"".equals(beneficiaryDTO.getIntermediaryBankClearingNetCode())) {
	     */
	    // String clearingCode = "//" +
	    // source.getBeneficiary().getIntermediaryBankClearingNetCode() +
	    // source.getBeneficiary().getIntermediaryBankClearingCode();
	    // intermediaryBank.setBankClearingCode(clearingCode);
	    intermediaryBank.setBankClearingNetworkCode(beneficiaryDTO.getIntermediaryBankClearingNetCode());
	    intermediaryBank.setBankClearingCode(beneficiaryDTO.getIntermediaryBankClearingCode());
	    /* } */

	}// end (source.getBeneficiary() != null)
	if (requestBody.getBeneficiaryAccountInfo() == null) {
	    requestBody.setBeneficiaryAccountInfo(new TransactionAccount());
	}
	if (request.getTxnAmt() != null) {
	    requestBody.getBeneficiaryAccountInfo().setAccountCurrencyCode(request.getTxnAmt().getCurrency());
	    // dest.setTransferAmount(source.getAmount().getAmount().doubleValue());
	    if (request.getTxnAmt().getAmount() != null) {
		requestBody.setCreditAmount(request.getTxnAmt().getAmount().doubleValue());
		requestBody.setDebitAmount(request.getTxnAmt().getAmount().doubleValue());
	    }
	}

	// currency code
	// dest.setTransactionCurrencyCode(source.getAmount().getCurrency());
	// Remit info

	Remitter remitter = new Remitter();
	IndividualName name = new IndividualName();
	name.setFullName(context.getFullName());
	remitter.setRemitterName(name);
	if (remitter.getPostalAddress() == null) {
	    remitter.setPostalAddress(new PostalAddress());
	}
	if (remitter.getPostalAddress().getUnStructuredAddress() == null) {
	    remitter.getPostalAddress().setUnStructuredAddress(new UnstructuredAddress());
	}
	remitter.getPostalAddress().getUnStructuredAddress().setAddressLine1(request.getRemPart1());
	remitter.getPostalAddress().getUnStructuredAddress().setAddressLine2(request.getRemPart2());
	remitter.getPostalAddress().getUnStructuredAddress().setAddressLine3(request.getRemPart3());
	remitter.getPostalAddress().getUnStructuredAddress().setAddressLine4(request.getRemPart4());

	requestBody.setRemitter(remitter);

	requestBody.setChargeModeCode(request.getChargeDescCode());

	// Current Branch
	requestBody.getBeneficiaryBank().getISOBankCode().setBranchCode(fromAcct.getBranchCode());
	requestBody.setTransactionTypeCode(request.getTxnType());
	// Transaction date
	Calendar c = Calendar.getInstance();

	// c.setTime(new Date());
	// Change to take business date if set in DB otherwise take as sysdate. Temp fix for SIT env.
	c.setTime(getBusinessDate(context));

	requestBody.setDebitValueDate(c);

	// SSA CHANGES START ..

	// from branch
	Branch bb = new Branch();
	bb.setBranchCode(fromAcct.getBranchCode());
	requestBody.setDebitAccountBranch(bb);

	// set bank code and branch code
	ISOBankCode isoBankCode = beneficiaryBank.getISOBankCode();
	if (beneficiaryDTO != null && beneficiaryDTO.getDestinationBankCode() != null && beneficiaryDTO.getDestinationBranchCode() != null) {
	    isoBankCode.setBankCode(beneficiaryDTO.getDestinationBankCode());
	    isoBankCode.setBranchCode(beneficiaryDTO.getDestinationBranchCode());
	}

	TransactionFxRate transactionFxRate = new TransactionFxRate();
	if (request.getFxRateDTO() != null) {

	    transactionFxRate.setEffectiveFXRate(request.getFxRateDTO().getEffectiveFXRate().doubleValue());
	} else {
	    transactionFxRate.setEffectiveFXRate(new Double(1.0));
	}
	requestBody.setTransactionFxRate(transactionFxRate);

	ChargeDetails chargeDetails = new ChargeDetails();
	chargeDetails.setChargeTypeCode(request.getChargeDescCode());

	requestBody.setChargeInfo(new ChargeDetails[] { chargeDetails });
	if (FundTransferConstants.TXN_TYPE_FUND_TRANSFER_EXTERNAL.equals(request.getTxnType())) {
	    // payment reason
	    String paymentReason = request.getPayRsonKey();
	    requestBody.setTransactionCategoryCode(paymentReason);

	    // payment detail

	    String paymentDetailValue = request.getPayDtlsValue();

	    // payment desc
	    String paymentDescription = request.getPayRsonValue();

	    if (null != paymentDescription && FILTERED_REASON.equals(paymentReason)) {
		paymentDetailValue = paymentDetailValue + paymentDescription;
	    }

	    // CHANGES TO ONLY KENYA SPECIFIC AS MIS VALUE HAS CHANGED FOR KEBRB
	    if ((KEBRB_FILTERED_REASON_MIS_INV.equals(paymentReason) || KEBRB_FILTERED_REASON_MIS_RBC.equals(paymentReason) || KEBRB_FILTERED_REASON_MIS_ROC
		    .equals(paymentReason))) {

		if (null != request.getTxnNot()) {
		    paymentDetailValue = paymentDetailValue + request.getTxnNot();
		} else {
		    paymentDetailValue = paymentDetailValue + paymentDescription;
		}
	    }

	    requestBody.getRemitter().getPostalAddress().getUnStructuredAddress().setAddressLine1(paymentDetailValue);
	}

	String txnType = request.getTxnType();

	// payment type and sub type
	String isoBankCountryCode = "";
	if (beneficiaryDTO != null) {
	    beneficiaryDTO.getDestinationBankIsoCountryCode();
	}
	String countryCode = context.getCountryCode();
	if (EXTERNAL_TXN_TYPE.equals(txnType)) {
	    // external (non-barclays) FT
	    requestBody.setTransactionCategoryCode(PAYMENT_TYPE_LOCAL);

	    // Haibo: SSA SIT defect 633 - For GHBRB/MUBOB domestic Non Barclays
	    // transfer, we should pass Payment Type & Sun Payment Type as LOCAL
	    // & INTERNETPAYT
	    requestBody.setTransactionSubCategoryCode(INTERNETPAYT);

	    // Defect- 5002 for setting transaction type specific to Kenya .
	    // Need to set -
	    // <p610:TransactionTypeCode>INT2</p610:TransactionTypeCode>
	    if (CommonConstants.KEBRB_BUSINESS_ID.equals(context.getBusinessId())) {
		requestBody.setTransactionTypeCode(KENYA_EXTERNAL_TXN_TYPE);
	    } else {
		requestBody.setTransactionTypeCode(EXTERNAL_TXN_TYPE);
	    }
	} else {
	    // urgent & international FT
	    if (isoBankCountryCode.equals(countryCode)) {
		// urgent FT
		requestBody.setTransactionCategoryCode(PAYMENT_TYPE_URGENT);

		// Haibo: SSA SIT defect 574 - For urgent payment, we should
		// pass Payment Type & Sun Payment Type as URGENT & PAYMENT
		requestBody.setTransactionSubCategoryCode(PAYMENT);

	    } else {
		// IFT
		requestBody.setTransactionCategoryCode(PAYMENT_TYPE_XBORDER);
		requestBody.setTransactionSubCategoryCode(PAYMENT + request.getChargeDescCode());
	    }
	    // Transaction Type
	    requestBody.setTransactionTypeCode(request.getTxnType());
	}
    }

    // Added getBusinessDate as temp fix for FCC date on SIT env only. It will
    // not invoked on production as business date is set null on Prod.
    private Date getBusinessDate(Context context) {

	Date returnDate = null;

	try {

	    String businessDate = (String) context.getContextMap().get(BUSINESS_DATE);

	    String dateFormatKey = (String) context.getContextMap().get(SHORT_DATE_FORMAT_KEY);

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
}
