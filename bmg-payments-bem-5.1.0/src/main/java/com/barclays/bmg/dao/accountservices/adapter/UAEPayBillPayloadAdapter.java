package com.barclays.bmg.dao.accountservices.adapter;

import java.util.Map;

import com.barclays.bem.BillPayment.BillPayment;
import com.barclays.bem.ChargeDetails.ChargeDetails;
import com.barclays.bem.OrganizationBeneficiary.OrganizationBeneficiary;
import com.barclays.bem.Product.Product;
import com.barclays.bem.TransactionAccount.TransactionAccount;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.service.request.PayBillServiceRequest;

public class UAEPayBillPayloadAdapter {

	private static final String BILLER_SALIK = "Salik";

	public BillPayment adaptPayload(WorkContext workContext){


		DAOContext daoContext = (DAOContext)workContext;

		Object[] args = daoContext.getArguments();

		PayBillServiceRequest payBillServiceRequest=
									(PayBillServiceRequest)args[0];

		BillPayment requestBody = mapDataInRequest(payBillServiceRequest);
		return requestBody;
	}

	private BillPayment mapDataInRequest(PayBillServiceRequest payBillServiceRequest){
		BillPayment billPayment = new BillPayment ();

		BeneficiaryDTO beneficiaryDTO = payBillServiceRequest.getBeneficiaryDTO();
		CustomerAccountDTO fromAcct = payBillServiceRequest.getFromAccount();


//        if (billPayment == null){
//            billPayment = new BillPayment ();
//        }
      //03, Feb 2011 Add by Kane, Li to pass bill payment outstanding balance amount to bem Start.
        if(payBillServiceRequest.getOutBalAmt()!=null){
        billPayment.setOutstandingBalanceAmount(payBillServiceRequest.getOutBalAmt().doubleValue());
        }
      //03, Feb 2011 Add by Kane, Li to pass bill payment outstanding balance amount to bem End.
        billPayment.setBillAmount(payBillServiceRequest.getBillAmount().doubleValue());
        billPayment.setBillAmountCurrencyCode(payBillServiceRequest.getContext().getLocalCurrency());
//        billPayment.setTotalBillAmount(transactionDTO.getAmount().getAmount().doubleValue());

//        billPayment.setPrimaryReferenceNumber(transactionDTO.getBeneficiary().getBillRefNo1());
//        billPayment.setSecondaryReferenceNumber(transactionDTO.getBeneficiary().getBillRefNo1());
//        billPayment.setBillDate(Calendar.getInstance());
//        //TODO:check this field
//        billPayment.setBillDueDate(Calendar.getInstance());
        //
        OrganizationBeneficiary billerDetails = new OrganizationBeneficiary ();
        billerDetails.setOrganizationCategoryCode(beneficiaryDTO.getBillerCategoryId());
        billerDetails.setOrganizationCode(beneficiaryDTO.getBillerId());
        // Added for testing purpose.
        billerDetails.setOrganizationName(beneficiaryDTO.getBillerName());
        billPayment.setBillerDetails(billerDetails);

        billPayment.setOnlineBillerFlag(beneficiaryDTO.getPresentmentFlag());
        ChargeDetails chargeDetails = new ChargeDetails();

 		if (payBillServiceRequest.getTransactionFee() != null
 				&& payBillServiceRequest.getTransactionFee().getAmount() != null) {
 			chargeDetails.setChargeAmount(payBillServiceRequest.getTransactionFee().getAmount().doubleValue());
 		}else{
 			chargeDetails.setChargeAmount(new Double(0.0));
 		}
 		if (payBillServiceRequest.getTransactionFee() != null) {
 			chargeDetails.setChargeAmountCurrencyCode(payBillServiceRequest.getTransactionFee().getCurrency());
 		}else{
 			chargeDetails.setChargeAmountCurrencyCode(payBillServiceRequest.getCurrency());
 		}
         billPayment.setChargeDetails(new ChargeDetails[]{chargeDetails});

         Product product = new Product();
         product.setProductCode(fromAcct.getProductCode());
         billPayment.setProduct(product);

        TransactionAccount fromAccount= new TransactionAccount();
        fromAccount.setAccountNumber(fromAcct.getAccountNumber());
        fromAccount.setAccountCurrencyCode(fromAcct.getCurrency());
//        chargeDetails.setChargeAccount(fromAccount);
//        chargeDetails.setChargeAmount(transactionDTO.getAmount().doubleValue());
//        chargeDetails.setChargeAmountCurrencyCode(transactionDTO.getCurrency()) ;
//        chargeDetails.setLCYChargeAmount(transactionDTO.getAmountInLCY().getAmount().doubleValue());



        billPayment.setDebitAccount(fromAccount);
        TransactionAccount transactionAccount = new TransactionAccount();
        transactionAccount.setAccountNumber(beneficiaryDTO.getDestinationAccountNumber());
        transactionAccount.setAccountCurrencyCode(beneficiaryDTO.getCurrency());

        billPayment.setPaymentChannelCode(beneficiaryDTO.getPaymentChannelId());
        billPayment.setDebitAmount(payBillServiceRequest.getBillAmount().doubleValue());

//        billPayment.setCustomerNumber("313015473");
        //fromAccount.setAccountNumber("000000310581");
//        billPayment.getBillerDetails().setOrganizationName("DEWA");
//        billPayment.getBillerDetails().setOrganizationCode("GSM");

        if (beneficiaryDTO.isMobileProvider()) {

        	billPayment.setConsumerReferenceNumber(beneficiaryDTO.getBillRefNo1() + beneficiaryDTO.getBillRefNo2());
        } else {
        	billPayment.setConsumerReferenceNumber(beneficiaryDTO.getBillRefNo1());
        }
        billPayment.setPrimaryReferenceNumber(beneficiaryDTO.getTransactionReferenceNumber());
        if(beneficiaryDTO.getPresentmentFlag()){
        	billPayment.getBillerDetails().setOrganizationName(beneficiaryDTO.getTopupService());
        }

        if (fromAcct instanceof CreditCardAccountDTO) {
        	CreditCardAccountDTO cardAccountDTO = (CreditCardAccountDTO) fromAcct;
        	billPayment.setCreditCardNumber(cardAccountDTO.getPrimary().getCardNumber());
        	billPayment.setTransactionTypeCode("DBADJ");
            billPayment.setReasonCode("IVUP");
        	billPayment.setPaymentMode("CARD");
        	String narrative = payBillServiceRequest.getTxnNote();
        	if (narrative != null) {
        		narrative = narrative.replaceAll(":BillerName", beneficiaryDTO.getBillerName());
            	narrative = narrative.replaceAll(":ConsumerNumber", billPayment.getConsumerReferenceNumber());
            	billPayment.setRemarks(narrative);
        	}

        } else if (fromAcct instanceof CASAAccountDTO) {
        	billPayment.setPaymentMode("CASA");
        }
        //START: Add by Can, Li; Pass customer full name in the request, which is in order to protect and inform customer from fraud attack

        billPayment.setInitiatingCustomerFullName(payBillServiceRequest.getContext().getFullName());
        //END: Add by Can, Li; Pass customer full name in the request, which is in order to protect and inform customer from fraud attack
        //START: Add by Li, Can; new biller integration; Sep 14, 2011;
        if(BILLER_SALIK.equals(beneficiaryDTO.getBillerId())){
        	Map<String, Object> contextMap = payBillServiceRequest.getContext().getContextMap();
        	billPayment.setConsumerPIN(beneficiaryDTO.getBillRefNo2());
        	billPayment.setChannelCode(contextMap.get(SystemParameterConstant.SALIK_PMT_CHANNEL_CODE).toString());
        	billPayment.setChannelLocationCode(contextMap.get(SystemParameterConstant.SALIK_PMT_CHANNEL_LOCATION_CODE).toString());
        }

	         return billPayment;
	}
}
