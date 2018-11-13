package com.barclays.bmg.dao.accountservices.adapter.ssa;


import com.barclays.bem.Bank.Bank;
import com.barclays.bem.BillPayment.BillPayment;
import com.barclays.bem.Branch.Branch;
import com.barclays.bem.ChargeDetails.ChargeDetails;
import com.barclays.bem.OrganizationBeneficiary.OrganizationBeneficiary;
import com.barclays.bem.PostalAddress.PostalAddress;
import com.barclays.bem.PostalAddress.UnstructuredAddress;
import com.barclays.bem.Product.Product;
import com.barclays.bem.TransactionAccount.TransactionAccount;
import com.barclays.bem.TransactionFxRate.TransactionFxRate;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.service.request.PayBillServiceRequest;

public class PayBillPayloadAdapter {

    private static final String PAYMENT_SUB_CATEGORY_BP = "LOCALBILLPAYT";
    private static final String PAYMENT_SUB_CATEGORY_TUP = "LOCALMOBILETUP";
    private static final String PAYMENT_SUB_CATEGORY_MW = "LOCALWALLETTUP";

    public BillPayment adaptPayload(WorkContext workContext) {

	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	PayBillServiceRequest payBillServiceRequest = (PayBillServiceRequest) args[0];

	BillPayment requestBody = mapDataInRequest(payBillServiceRequest);
	Context ctx = (Context)payBillServiceRequest.getContext();
	if(ctx.getBusinessId().equals("GHBRB") && payBillServiceRequest.getAction()!=null && payBillServiceRequest.getAction().equals("true")){
		requestBody.setActionCode(ActivityConstant.FREE_DIAL_MWALLET);
	}
	return requestBody;
    }

    private BillPayment mapDataInRequest(PayBillServiceRequest payBillServiceRequest) {
	BillPayment billPayment = new BillPayment();

	billPayment.setActionCode(payBillServiceRequest.getBeneficiaryDTO().getActionCode());

	BeneficiaryDTO beenBeneficiaryDTO = payBillServiceRequest.getBeneficiaryDTO();

	CustomerAccountDTO fromAcct = payBillServiceRequest.getFromAccount();

	Branch bb = new Branch();
	bb.setBranchCode(fromAcct.getBranchCode());
	billPayment.setDebitAccountBranch(bb);

	// debit account(number and currency)
	TransactionAccount fromAccount = new TransactionAccount();
	fromAccount.setAccountNumber(fromAcct.getAccountNumber());
	fromAccount.setAccountCurrencyCode(fromAcct.getCurrency());
	billPayment.setDebitAccount(fromAccount);

	// amount and currency
	billPayment.setBillAmountCurrencyCode(payBillServiceRequest.getCurrency());
	billPayment.setBillAmount(payBillServiceRequest.getBillAmount().doubleValue());

	// remarks
	String narrative = payBillServiceRequest.getTxnNote();
	String billRefNo1 = beenBeneficiaryDTO.getBillRefNo1();
	// if (narrative != null) {
	// narrative = narrative.replaceAll(":BillerName", beenBeneficiaryDTO.getBillerName());
	// narrative = narrative.replaceAll(":ConsumerNumber", billRefNo1);
	// billPayment.setRemarks(narrative);
	// }

	//Static narration not required. Needed generic narration having primary reference number
	//billPayment.setRemarks(narrative);

	// Customer Name
	// billPayment.setInitiatingCustomerFullName(transactionDTO.getCustomerName());

	// Effective FXrate
	if (billPayment.getTransactionFxRate() == null) {
	    billPayment.setTransactionFxRate(new TransactionFxRate());
	}
	/*
	 * if (transactionDTO.getFx() != null && transactionDTO.getFx().getEffectiveFXDateTime() != null) {
	 * billPayment.getTransactionFxRate().setEffectiveFXRate(transactionDTO.getFx().getEffectiveFXRate().doubleValue()); }
	 */

	// Charge Details
	ChargeDetails chargeDetails = new ChargeDetails();
	if (payBillServiceRequest.getTransactionFee() != null && payBillServiceRequest.getTransactionFee().getAmount() != null) {
	    chargeDetails.setChargeAmount(payBillServiceRequest.getTransactionFee().getAmount().doubleValue());
	}
	if (payBillServiceRequest.getTransactionFee() != null) {
	    chargeDetails.setChargeAmountCurrencyCode(payBillServiceRequest.getTransactionFee().getCurrency());
	}
	billPayment.setChargeDetails(new ChargeDetails[] { chargeDetails });

	// product type code
	Product product = new Product();
	product.setProductCode(fromAcct.getProductCode());
	billPayment.setProduct(product);
	billPayment.setDebitAccountTypeCode(fromAcct.getProductCode());

	// biller category code and biller code
	OrganizationBeneficiary billerDetails = new OrganizationBeneficiary();
	billerDetails.setOrganizationCategoryCode(beenBeneficiaryDTO.getBillerCategoryId());
	billerDetails.setOrganizationCode(beenBeneficiaryDTO.getBillerId());
	billPayment.setBillerDetails(billerDetails);

	// Online Biller Flag
	billPayment.setOnlineBillerFlag(beenBeneficiaryDTO.getPresentmentFlag());

	TransactionAccount transactionAccount = new TransactionAccount();
	transactionAccount.setAccountNumber(beenBeneficiaryDTO.getDestinationAccountNumber());
	transactionAccount.setAccountCurrencyCode(beenBeneficiaryDTO.getCurrency());
	billPayment.getBillerDetails().setBeneficiaryAccountInfo(transactionAccount);

	billPayment.setPaymentChannelCode(beenBeneficiaryDTO.getPaymentChannelId());
	billPayment.setDebitAmount(payBillServiceRequest.getBillAmount().doubleValue());

	if (beenBeneficiaryDTO.isMobileProvider()) {

	    billPayment.setConsumerReferenceNumber(billRefNo1 + beenBeneficiaryDTO.getBillRefNo2());
	} else {
	    billPayment.setConsumerReferenceNumber(billRefNo1);
	}
	billPayment.setPrimaryReferenceNumber(billRefNo1);
	if (billRefNo1 == null || billRefNo1.length() == 0) {
	    billPayment.setPrimaryReferenceNumber(beenBeneficiaryDTO.getBillRefNo());
	}
	// WUC change - Botswana 20/06/2017
	if(beenBeneficiaryDTO.getBusinessId() !=null && beenBeneficiaryDTO.getBusinessId().equals("BWBRB") && beenBeneficiaryDTO.getBillerId().equals("WUC-2")){
		String billRefNo2 = beenBeneficiaryDTO.getBillRefNo2();
		if (billRefNo2 != null || billRefNo2 != ""){
			billPayment.setSecondaryReferenceNumber(billRefNo2);
		}
	}
	billPayment.setRemarks(billPayment.getPrimaryReferenceNumber());// changed as per requirement from UBP: 04 Aug 2015

	billPayment.getBillerDetails().setOrganizationName(beenBeneficiaryDTO.getTopupService());

	if (payBillServiceRequest.getFromAccount() instanceof CreditCardAccountDTO) {
	    CreditCardAccountDTO cardAccountDTO = (CreditCardAccountDTO) payBillServiceRequest.getFromAccount();
	    billPayment.setCreditCardNumber(cardAccountDTO.getPrimary().getCardNumber());
	    billPayment.setTransactionTypeCode("DBADJ");
	    billPayment.setReasonCode("IVUP");
	    billPayment.setPaymentMode("CARD");

	} else if (payBillServiceRequest.getFromAccount() instanceof CASAAccountDTO) {
	    billPayment.setPaymentMode("CASA");
	}

	billPayment.getBillerDetails().setBeneficiaryName(beenBeneficiaryDTO.getBillerName());
	// branch
	Branch billerBranch = new Branch();
	billerBranch.setBranchCode(beenBeneficiaryDTO.getDestinationBranchCode());
	billPayment.getBillerDetails().setBeneficiaryBranch(billerBranch);

	// Set bank clearing code for SSA
	if (billPayment.getBillerDetails().getOrganizationBank() == null) {
	    billPayment.getBillerDetails().setOrganizationBank(new Bank());
	}
	billPayment.getBillerDetails().getOrganizationBank().setBankClearingCode(beenBeneficiaryDTO.getDestinationBankCode());

	// Add By Tony, for Bill Holder Name & Address
	// Set Bill Holder Name
	String beneficiaryName = beenBeneficiaryDTO.getBeneficiaryName();
	billPayment.setInitiatingCustomerFullName(beneficiaryName);

	if (beneficiaryName == null || beneficiaryName.length() == 0) {
	    billPayment.setInitiatingCustomerFullName(payBillServiceRequest.getContext().getFullName());
	}
	// Set Bill Holder Address
	PostalAddress customerAddress = billPayment.getCustomerAddress();
	if (customerAddress == null) {
	    customerAddress = new PostalAddress();
	    billPayment.setCustomerAddress(customerAddress);
	}
	UnstructuredAddress unstructuredAddress = billPayment.getCustomerAddress().getUnStructuredAddress();
	if (unstructuredAddress == null) {
	    unstructuredAddress = new UnstructuredAddress();
	    billPayment.getCustomerAddress().setUnStructuredAddress(unstructuredAddress);
	}
	billPayment.getCustomerAddress().getUnStructuredAddress().setAddressLine1(beenBeneficiaryDTO.getDestinationAddressLine1());
	billPayment.getCustomerAddress().getUnStructuredAddress().setAddressLine2(beenBeneficiaryDTO.getDestinationAddressLine2());
	billPayment.getCustomerAddress().getUnStructuredAddress().setAddressLine3(beenBeneficiaryDTO.getDestinationAddressLine3());

	// Add End

	// eBox Charges Changes Start
	if (ActivityConstant.BILL_PAYMENT_PAYEE_ACTIVITY_ID.equals(payBillServiceRequest.getContext().getActivityId())
		|| ActivityIdConstantBean.BILL_PAYMENT_ONETIME_ACTIVITY_ID.equals(payBillServiceRequest.getContext().getActivityId())) {
	    billPayment.setTransactionSubCategoryCode(PAYMENT_SUB_CATEGORY_BP);
	} else if (ActivityConstant.MOBILE_TOPUP_PAYEE_ACTIVITY_ID.equals(payBillServiceRequest.getContext().getActivityId())) {
	    billPayment.setTransactionSubCategoryCode(PAYMENT_SUB_CATEGORY_TUP);
	} else if (ActivityConstant.MOBILE_WALLET_PAYEE_ACTIVITY_ID.equals(payBillServiceRequest.getContext().getActivityId())) {
	    billPayment.setTransactionSubCategoryCode(PAYMENT_SUB_CATEGORY_MW);
	}

	String actionCode=payBillServiceRequest.getBeneficiaryDTO().getActionCode();
	if(actionCode!=null && actionCode.equals("FREEDIALAIRTEL")){
		billPayment.setTransactionSubCategoryCode(PAYMENT_SUB_CATEGORY_MW);
	}
	// eBox Charges Changes End

	return billPayment;
    }
}
