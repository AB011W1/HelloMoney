package com.barclays.bmg.dao.accountservices.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.barclays.bem.Branch.Branch;
import com.barclays.bem.CreditCardBasic.CreditCardBasic;
import com.barclays.bem.CreditCardPayment.CreditCardPayment;
import com.barclays.bem.CreditCardPayment.MemoLineType;
import com.barclays.bem.IndividualName.IndividualName;
import com.barclays.bem.TransactionAccount.CreditCardExpiryDateType;
import com.barclays.bem.TransactionAccount.TransactionAccount;
import com.barclays.bem.TransactionFxRate.TransactionFxRate;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.service.request.PayBillServiceRequest;
import com.barclays.bmg.utils.DateTimeUtil;

public class CreditCardPayPayloadAdapter {

    public CreditCardPayment adaptPayload(WorkContext workContext) {

	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	PayBillServiceRequest payBillServiceRequest = (PayBillServiceRequest) args[0];

	CreditCardPayment requestBody = mapDataInRequest(payBillServiceRequest);
	return requestBody;
    }

    private CreditCardPayment mapDataInRequest(PayBillServiceRequest payBillServiceRequest) {
	CreditCardPayment creditCardPayment = new CreditCardPayment();
	BeneficiaryDTO beneficiaryDTO = payBillServiceRequest.getBeneficiaryDTO();
    Context context = payBillServiceRequest.getContext();
	creditCardPayment.setDebitAccount(getDebitAccount(payBillServiceRequest.getFromAccount()));
	creditCardPayment.setDebitAmount(payBillServiceRequest.getBillAmount().doubleValue());
	creditCardPayment.setDebitBranch(getDebitBranch(payBillServiceRequest.getFromAccount()));
	creditCardPayment.setBeneficiaryBranch(getBeneficiaryBranch(beneficiaryDTO));
	creditCardPayment.setBeneficiaryAccount(getBeneficiaryAccount(beneficiaryDTO));
	creditCardPayment.setPostTo(BillPaymentConstants.POST_TO_CARD);
	creditCardPayment.setHostTransactionTypeCode(BillPaymentConstants.HOST_TRANSACTION_TYPE_CODE);

	//CR-64 Credit card Payment EBOX
	String businessId=payBillServiceRequest.getContext().getBusinessId();
	if(businessId.equalsIgnoreCase(CommonConstants.GHBRB_BUSINESS_ID)|| businessId.equalsIgnoreCase(CommonConstants.ZMBRB_BUSINESS_ID)
			|| businessId.equalsIgnoreCase(CommonConstants.BWBRB_BUSINESS_ID)|| businessId.equalsIgnoreCase(CommonConstants.TZBRB_BUSINESS_ID) ) {
		creditCardPayment.setHostTransactionTypeCode(BillPaymentConstants.HOST_TRANSACTION_TYPE_CODE_EBOX);
		creditCardPayment.setBeneficiaryBranch(getBeneficiaryBranchEbox(beneficiaryDTO,payBillServiceRequest));
		creditCardPayment.setBeneficiaryAccount(getBeneficiaryAccountEbox(beneficiaryDTO,payBillServiceRequest));
	}

	// Mapping Transaction Date and Value Date.
	Calendar today = Calendar.getInstance();
	today.setTime(getBusinessDate(payBillServiceRequest.getContext()));
	creditCardPayment.setOriginationDate(today);
	creditCardPayment.setValueDate(today);

	// Set CreditCardNumber
	CreditCardBasic card = new CreditCardBasic();
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
	// Credit Card Number
	creditCardPayment.setCreditCardNumber(card);

	// transaction type code
	creditCardPayment.setTransactionTypeCode(BillPaymentConstants.TXN_FACADE_RTN_TYPE_CREDIT_CARD);
	creditCardPayment.setNarrationText(payBillServiceRequest.getTxnNote());

	IndividualName name = new IndividualName();
	name.setFullName(beneficiaryDTO.getBeneficiaryName());// ?
	creditCardPayment.setBeneficiaryName(name);

	creditCardPayment.setDebitAccountTypeCode(payBillServiceRequest.getFromAccount().getProductCode());
	if (payBillServiceRequest.getAmtInLCY() != null) {
	    creditCardPayment.setTransferAmountLCY(payBillServiceRequest.getAmtInLCY().doubleValue());
	}

	// Passing product type code for own credit card payment
	if (beneficiaryDTO.getDestinationAccount() != null) {
	    creditCardPayment.setCreditAccountTypeCode(beneficiaryDTO.getDestinationAccount().getProductCode());// ?
	}
	//
	TransactionFxRate fXRate = new TransactionFxRate();
	// FX Rate
	fXRate.setEffectiveFXRate(new Double(1.0));
	creditCardPayment.setTransactionFxRate(fXRate);
	creditCardPayment.setCreditAmount(payBillServiceRequest.getBillAmount().doubleValue());
	creditCardPayment.setInitiatingCustomerFullName(payBillServiceRequest.getContext().getFullName());
	creditCardPayment.setToCreditCardAccountNumber(beneficiaryDTO.getCardNumber());

	if (payBillServiceRequest.getOrdCode() != null) {
	    creditCardPayment.setCreditCardAccountOrgCode(payBillServiceRequest.getOrdCode());
	}
	String PONumber = getPONumber();
	creditCardPayment.setPONumber(PONumber);

	// Credit Card Migration-Plan no. Addition
	Map<String, Object> contextMap = context.getContextMap();
	String planNumber = (String) contextMap.get(SystemParameterConstant.PLAN_NUMBER_BILL_PAYMENT);
	if (null != planNumber) {
		creditCardPayment.setPlan(planNumber);
	} else {
		creditCardPayment.setPlan("10002");
	}

	creditCardPayment.setEffectiveTxnDate(today);
	creditCardPayment.setActionCode((String) payBillServiceRequest.getContext().getContextMap().get(SystemParameterConstant.CC_ACTION_CODE));
	creditCardPayment.setStoreNumber((String) payBillServiceRequest.getContext().getContextMap().get(SystemParameterConstant.CC_STORE));

	creditCardPayment.setMemoLine(getMemoLineType(payBillServiceRequest, today, PONumber));

	// ---CARD PAYMENT CHANGES FOR ORCHARD - END

	return creditCardPayment;
    }

    private Branch getBeneficiaryBranch(BeneficiaryDTO beneficiaryDTO) {
	Branch beneficiaryBranchCode = new Branch();
	beneficiaryBranchCode.setBranchCode(beneficiaryDTO.getDestinationBranchCode());
	return beneficiaryBranchCode;
    }

    //CR-64 Credit card Payment EBOX
    private Branch getBeneficiaryBranchEbox(BeneficiaryDTO beneficiaryDTO,PayBillServiceRequest payBillServiceRequest) {
    	Branch beneficiaryBranchCode = new Branch();
    	beneficiaryBranchCode.setBranchCode((String) payBillServiceRequest.getContext().getContextMap().get(SystemParameterConstant.CREDIT_CARD_PAYMENT_COU_BRANCH_ID));
    	return beneficiaryBranchCode;
    }

    //CR-64 Credit card Payment EBOX
    private TransactionAccount getBeneficiaryAccountEbox(BeneficiaryDTO beneficiaryDTO,PayBillServiceRequest payBillServiceRequest) {
    	TransactionAccount beneficiaryAccount = new TransactionAccount();
    	beneficiaryAccount.setAccountCurrencyCode(beneficiaryDTO.getCurrency());
    	beneficiaryAccount.setAccountNumber((String) payBillServiceRequest.getContext().getContextMap().get(SystemParameterConstant.CREDIT_CARD_PAYMENT_GL_SUSPENSE_ACCOUNT));
    	return beneficiaryAccount;
    }


    private Branch getDebitBranch(CustomerAccountDTO fromAccount) {
    	Branch fromBranch = new Branch();
    	fromBranch.setBranchCode(fromAccount.getBranchCode());
    	return fromBranch;
    }

    private TransactionAccount getDebitAccount(CustomerAccountDTO frmAct) {
    	TransactionAccount fromAccount = new TransactionAccount();
    	fromAccount.setAccountNumber(frmAct.getAccountNumber());
    	fromAccount.setAccountCurrencyCode(frmAct.getCurrency());
    	return fromAccount;
    }

    private TransactionAccount getBeneficiaryAccount(BeneficiaryDTO beneficiaryDTO) {
	TransactionAccount beneficiaryAccount = new TransactionAccount();
	beneficiaryAccount.setAccountCurrencyCode(beneficiaryDTO.getCurrency());
	beneficiaryAccount.setAccountNumber(beneficiaryDTO.getDestinationAccountNumber());
	return beneficiaryAccount;
    }

    private MemoLineType getMemoLineType(PayBillServiceRequest payBillServiceRequest, Calendar today, String PONumber) {
	MemoLineType memoLine = new MemoLineType();
	memoLine.setMemoLine1("SHM-CARD-PYMT AMT: " + payBillServiceRequest.getBillAmount().doubleValue() + ", DTE: " + getFormattedDate(today));
	memoLine.setMemoLine2("POREF: " + PONumber);
	memoLine.setMemoLine3("STORE: " + (String) payBillServiceRequest.getContext().getContextMap().get(SystemParameterConstant.CC_STORE));
	memoLine.setMemoLine4("REMARKS: SHM Card Payment");
	return memoLine;
    }

    private String getPONumber() {
	String nanoTime = Long.toHexString(System.nanoTime());
	String subUnique = nanoTime.substring(nanoTime.length() - 10);
	String UniqueNo = "UB" + subUnique.toUpperCase();
	return UniqueNo;
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

}
