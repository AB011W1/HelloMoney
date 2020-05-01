package com.barclays.bmg.dao.accountservices.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.barclays.bem.Bank.Bank;
import com.barclays.bem.BillPayment.BillPayment;
import com.barclays.bem.BillPayment.BillTransactionReferenceDetails;
import com.barclays.bem.BillPayment.MemoLineType;
import com.barclays.bem.Branch.Branch;
import com.barclays.bem.ChargeDetails.ChargeDetails;
import com.barclays.bem.Merchant.Merchant;
import com.barclays.bem.OrganizationBeneficiary.OrganizationBeneficiary;
import com.barclays.bem.PostalAddress.PostalAddress;
import com.barclays.bem.PostalAddress.UnstructuredAddress;
import com.barclays.bem.Product.Product;
import com.barclays.bem.TransactionAccount.CreditCardExpiryDateType;
import com.barclays.bem.TransactionAccount.TransactionAccount;
import com.barclays.bem.TransactionFxRate.TransactionFxRate;
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.Charge;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.service.request.PayBillServiceRequest;
import com.barclays.bmg.utils.DateTimeUtil;

public class MakePayBillPayloadAdapter {

    private static final String FORWARD_SLASH_SEPARATOR = "/";
    private static final String SINGLE_SPACE_SEPARATOR = " ";
    private static String creditorName;

    public BillPayment adaptPayload(WorkContext workContext) {
		DAOContext daoContext = (DAOContext) workContext;
		Object[] args = daoContext.getArguments();
		PayBillServiceRequest payBillServiceRequest = (PayBillServiceRequest) args[0];
		BillPayment requestBody = mapDataInRequest(payBillServiceRequest);
		return requestBody;
    }

    private BillPayment mapDataInRequest(PayBillServiceRequest payBillServiceRequest) {
		BillPayment billPayment = new BillPayment();

		BeneficiaryDTO beenBeneficiaryDTO = payBillServiceRequest.getBeneficiaryDTO();
		CustomerAccountDTO fromAcct = payBillServiceRequest.getFromAccount();

		Branch bb = new Branch();
		bb.setBranchCode(fromAcct.getBranchCode());
		billPayment.setDebitAccountBranch(bb);

		// debit account(number and currency)
		TransactionAccount fromAccount = new TransactionAccount();
		fromAccount.setAccountNumber(fromAcct.getAccountNumber());
		fromAccount.setAccountCurrencyCode(fromAcct.getCurrency());
		// First Vision Changes for expiry date
		if (null != beenBeneficiaryDTO && null != beenBeneficiaryDTO.getCreditCardExpiryDate()) {
			Date date = beenBeneficiaryDTO.getCreditCardExpiryDate();

			if (date != null) {
				// cardExpiryType.setCardExpiryMonth
				CreditCardExpiryDateType creditCardExpiryDate = new CreditCardExpiryDateType();
				creditCardExpiryDate.setCardExpiryMonth(DateTimeUtil.getDayMonthYearFromDate(date, "MM"));
				creditCardExpiryDate.setCardExpiryYear(DateTimeUtil.getDayMonthYearFromDate(date, "YY"));
				fromAccount.setCreditCardExpiryDate(creditCardExpiryDate);
			}
		}
		billPayment.setDebitAccount(fromAccount);

		// amount and currency
		billPayment.setBillAmountCurrencyCode(payBillServiceRequest.getCurrency());
		billPayment.setBillAmount(payBillServiceRequest.getBillAmount().doubleValue());
		billPayment.setSecondaryReferenceNumber(payBillServiceRequest.getBeneficiaryDTO().getBillRefNo2());

		// Customer Name
		// billPayment.setInitiatingCustomerFullName(transactionDTO.getCustomerName());

		// Effective FXrate
		if (billPayment.getTransactionFxRate() == null) {
		    billPayment.setTransactionFxRate(new TransactionFxRate());
		}
		/*
		 * if (transactionDTO.getFx() != null && transactionDTO.getFx().getEffectiveFXDateTime() != null) { billPayment
		 * .getTransactionFxRate().setEffectiveFXRate(transactionDTO.getFx ().getEffectiveFXRate().doubleValue()); }
		 */

		// Charge Details
		ChargeDetails chargeDetails = new ChargeDetails();
		if (payBillServiceRequest.getTransactionFee() != null && payBillServiceRequest.getTransactionFee().getAmount() != null) {
		    chargeDetails.setChargeAmount(payBillServiceRequest.getTransactionFee().getAmount().doubleValue());
		}

		/* Regulatory Requirement Changes - 23/10/2017 - code rollBacked on 08/12/2017
		 *
		 */
		// Set fields for MakeBillPayment request - CPB 10/05
		//Added condition for pesalink 7.0.0
		Charge cbpCharge = payBillServiceRequest.getChargeDTO();
		//Commented to add CBP
		/*if(payBillServiceRequest.getContext().getActivityId().equalsIgnoreCase("KITS_PTA_BILLPAY") || payBillServiceRequest.getContext().getActivityId().equalsIgnoreCase("KITS_PTP_BILLPAY") )
		{}
		else
		{*/
			if(cbpCharge !=null){
				String billPayFields = cbpCharge.getCpbMakeBillPaymentFlag();
				if(billPayFields !=null && billPayFields.equals("setBillPayFields")){
					chargeDetails.setChargeAmount(payBillServiceRequest.getChargeDTO().getCpbChargeAmount());
					chargeDetails.setFeeGLAccount(payBillServiceRequest.getChargeDTO().getFeeGLAccount());
					chargeDetails.setChargeNarration(payBillServiceRequest.getChargeDTO().getChargeNarration());
					chargeDetails.setTaxAmount(payBillServiceRequest.getChargeDTO().getTaxAmount());
					chargeDetails.setTaxGLAccount(payBillServiceRequest.getChargeDTO().getTaxGLAccount());
				}else if(billPayFields !=null && billPayFields.equals("xelerateOffline")){
					chargeDetails.setChargeAmount(payBillServiceRequest.getChargeDTO().getCpbChargeAmount());
					chargeDetails.setTaxAmount(payBillServiceRequest.getChargeDTO().getTaxAmount());
				}
			}
			if (payBillServiceRequest.getTransactionFee() != null) {
			    chargeDetails.setChargeAmountCurrencyCode(payBillServiceRequest.getTransactionFee().getCurrency());
			}

		billPayment.setChargeDetails(new ChargeDetails[] { chargeDetails });
	//}





		Merchant merchant = new Merchant();

		if (payBillServiceRequest.getExternalBillerId() != null) {
		    merchant.setMerchantCode(payBillServiceRequest.getExternalBillerId());
		}

		billPayment.setMerchant(merchant);

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

		//Added condition for KITS
		if(payBillServiceRequest.getContext().getActivityId().equalsIgnoreCase("KITS_PTA_BILLPAY") || payBillServiceRequest.getContext().getActivityId().equalsIgnoreCase("KITS_PTP_BILLPAY") )
		{
				billPayment.setConsumerReferenceNumber(beenBeneficiaryDTO.getDestinationAccountNumber());
				billPayment.setPrimaryReferenceNumber(beenBeneficiaryDTO.getDestinationAccountNumber());
		}
		else
		{
		    billPayment.setConsumerReferenceNumber(beenBeneficiaryDTO.getBillRefNo());
			billPayment.setPrimaryReferenceNumber(beenBeneficiaryDTO.getBillRefNo());
		}

	billPayment.getBillerDetails().setOrganizationName(beenBeneficiaryDTO.getTopupService());

		if (payBillServiceRequest.getFromAccount() instanceof CreditCardAccountDTO) {
		    CreditCardAccountDTO cardAccountDTO = (CreditCardAccountDTO) payBillServiceRequest.getFromAccount();
		    billPayment.setCreditCardNumber(cardAccountDTO.getPrimary().getCardNumber());
		    billPayment.setTransactionTypeCode("BP");
		    billPayment.setReasonCode("IVUP");
		    billPayment.setPaymentMode("CARD");

		} else if (payBillServiceRequest.getFromAccount() instanceof CASAAccountDTO) {
		    billPayment.setPaymentMode("CASA");
		}

		billPayment.getBillerDetails().setBeneficiaryName(beenBeneficiaryDTO.getBillerName());

		Branch billerBranch = new Branch();
		billerBranch.setBranchCode(beenBeneficiaryDTO.getDestinationBranchCode());
		billPayment.getBillerDetails().setBeneficiaryBranch(billerBranch);

		// Set bank clearing code for SSA
		if (billPayment.getBillerDetails().getOrganizationBank() == null) {
		    billPayment.getBillerDetails().setOrganizationBank(new Bank());
		}
		billPayment.getBillerDetails().getOrganizationBank().setBankClearingCode(beenBeneficiaryDTO.getDestinationBankCode());

		String beneficiaryName = beenBeneficiaryDTO.getBeneficiaryName();
		billPayment.setInitiatingCustomerFullName(beneficiaryName);
		if (beneficiaryName == null || beneficiaryName.length() == 0) {
		    billPayment.setInitiatingCustomerFullName(payBillServiceRequest.getContext().getFullName());
		}
		
		//Added for KITS debtor name INC INC1009890417
		if(payBillServiceRequest.getContext().getActivityId().equalsIgnoreCase("KITS_PTA_BILLPAY") || payBillServiceRequest.getContext().getActivityId().equalsIgnoreCase("KITS_PTP_BILLPAY"))
		{
			billPayment.setInitiatingCustomerFullName(payBillServiceRequest.getContext().getKitsFullName());
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
		//Added as a part of CR#43
		String billerId=null;
		if(payBillServiceRequest.getBeneficiaryDTO()!=null && payBillServiceRequest.getBeneficiaryDTO().getBillerId()!=null){
			billerId=payBillServiceRequest.getBeneficiaryDTO().getBillerId();
		}
		StringBuffer transactionRemarks = createRemarks(payBillServiceRequest.getTxnNote(), beenBeneficiaryDTO.getBillRefNo(), payBillServiceRequest
			.getContext().getActivityRefNo(), billPayment.getSecondaryReferenceNumber(), payBillServiceRequest.getContext().getBusinessId(),billerId);
		if (transactionRemarks != null) {
		    billPayment.setRemarks(transactionRemarks.toString());
		}
		String isKITS = null;
		if(payBillServiceRequest.getContext().getValue("isKITSFLAG") != null)
			isKITS = payBillServiceRequest.getContext().getValue("isKITSFLAG").toString();

		BillTransactionReferenceDetails[] billTransactionReferenceDetailsArray = null;
		if(payBillServiceRequest.getContext().getActivityId().equalsIgnoreCase("KITS_PTA_BILLPAY") || payBillServiceRequest.getContext().getActivityId().equalsIgnoreCase("KITS_PTP_BILLPAY") )
		{

			if(isKITS != null)
			{
				if(isKITS.equals("Y"))
					billTransactionReferenceDetailsArray=new BillTransactionReferenceDetails[7];
				else
					billTransactionReferenceDetailsArray=new BillTransactionReferenceDetails[6];
			}
		}
		//Kits specific parameters starts
		if(payBillServiceRequest.getContext().getOpCde()!= null && payBillServiceRequest.getContext().getOpCde().equalsIgnoreCase("OP0980")
				&& payBillServiceRequest.getContext().getBusinessId().equalsIgnoreCase("KEBRB") ){
			if(payBillServiceRequest.getContext().getActivityId().equalsIgnoreCase("KITS_PTA_BILLPAY") || payBillServiceRequest.getContext().getActivityId().equalsIgnoreCase("KITS_PTP_BILLPAY") )
			{
				/* Regulatory Requirement Changes - 23/10/2017- code rollBacked on 08/12/2017
				 */
				//Change array size to add creditor name

				/*
				// Remove once Regulatory Requirement Changes revoked - 23/10/2017- code commented on 08/12/2017
				BillTransactionReferenceDetails[] billTransactionReferenceDetailsArray=new BillTransactionReferenceDetails[5];
				*/
				//Array initialization

				//Added for KITS enable/disable
				/*String isKITS = null;
				if(payBillServiceRequest.getContext().getValue("isKITSFLAG") != null)
					isKITS = payBillServiceRequest.getContext().getValue("isKITSFLAG").toString();*/
				/*BillTransactionReferenceDetails[] billTransactionReferenceDetailsArray = null;
				if(isKITS != null)
				{
					if(isKITS.equals("Y")) {
						billTransactionReferenceDetailsArray=new BillTransactionReferenceDetails[7];
					} else {
						billTransactionReferenceDetailsArray=new BillTransactionReferenceDetails[6];
					}
				}

				if(billTransactionReferenceDetailsArray == null)
					billTransactionReferenceDetailsArray=new BillTransactionReferenceDetails[7];*/

				BillTransactionReferenceDetails billTransactionReferenceDetails1=new BillTransactionReferenceDetails();
				billTransactionReferenceDetails1.setTypeCode("receivingsortcode");
				billTransactionReferenceDetails1.setValue(beenBeneficiaryDTO.getDestinationBankSortCode());

				BillTransactionReferenceDetails billTransactionReferenceDetails2=new BillTransactionReferenceDetails();
				billTransactionReferenceDetails2.setTypeCode("receivingBank");
				billTransactionReferenceDetails2.setValue(beenBeneficiaryDTO.getDestinationBankName());

				BillTransactionReferenceDetails billTransactionReferenceDetails3=new BillTransactionReferenceDetails();
				billTransactionReferenceDetails3.setTypeCode("sourceAccount");
				billTransactionReferenceDetails3.setValue(fromAccount.getAccountNumber());

				BillTransactionReferenceDetails billTransactionReferenceDetails4=new BillTransactionReferenceDetails();
				billTransactionReferenceDetails4.setTypeCode("transactionType");


				 if("KITS_PTA_BILLPAY".equals(payBillServiceRequest.getContext().getActivityId()))
		         {
					if(isKITS != null)
					{
						 if(isKITS.equals("Y"))
							 billTransactionReferenceDetails4.setValue("A2A");
						 else
							 billTransactionReferenceDetails4.setValue("P2A");
					}
		         }
				 else if("KITS_PTP_BILLPAY".equals(payBillServiceRequest.getContext().getActivityId()))
		         {
		        	 //billTransactionReferenceDetails4.setValue("P2P");
		        	 if(isKITS != null)
					{
		        		 if(isKITS.equals("Y"))
			        	 {
			        		 billTransactionReferenceDetails4.setValue("A2P");
			 	        	//Added  Creditor name in request
			 				 BillTransactionReferenceDetails billTransactionReferenceDetails7 = new BillTransactionReferenceDetails();
			 				 billTransactionReferenceDetails7.setTypeCode("CreditorName");
			 				 billTransactionReferenceDetails7.setValue(creditorName);

			 				//Added creditor name in array
			 				billTransactionReferenceDetailsArray[6] = billTransactionReferenceDetails7;
			        	 }
			        	 else
			        		 billTransactionReferenceDetails4.setValue("P2P");
					}

		         }
				BillTransactionReferenceDetails billTransactionReferenceDetails5=new BillTransactionReferenceDetails();
				billTransactionReferenceDetails5.setTypeCode("narration");
				billTransactionReferenceDetails5.setValue(beenBeneficiaryDTO.getNarration());

				billTransactionReferenceDetailsArray[0]=billTransactionReferenceDetails1;
				billTransactionReferenceDetailsArray[1]=billTransactionReferenceDetails2;
				billTransactionReferenceDetailsArray[2]=billTransactionReferenceDetails3;
				billTransactionReferenceDetailsArray[3]=billTransactionReferenceDetails4;
				billTransactionReferenceDetailsArray[4]=billTransactionReferenceDetails5;
			}

			 if("KITS_PTA_BILLPAY".equals(payBillServiceRequest.getContext().getActivityId()))
	         {
		     	billPayment.setTransactionTypeCode("PSTA");
	         }
			 else if("KITS_PTP_BILLPAY".equals(payBillServiceRequest.getContext().getActivityId()))
	         {
	        	 billPayment.setTransactionTypeCode("PSTP");
	         }


		}
		//Kits specific parameters ends
		//CBP
		/* Regulatory Requirement Changes - 23/10/2017- code rollBacked on 08/12/2017
		 */
		// MakeBillPayment specific reference details for MWallet - CPB 15/05/2017

		String activityId = payBillServiceRequest.getContext().getActivityId();

		//CBP changes
		Map<String, Object> contextMap = payBillServiceRequest.getContext().getContextMap();


		String opCode = payBillServiceRequest.getContext().getOpCde();
		BillTransactionReferenceDetails billTransactionReferenceDetails6 = null;
		if(opCode!= null && (opCode.equalsIgnoreCase("OP0603") || opCode.equalsIgnoreCase("OP0511") || opCode.equalsIgnoreCase("OP0980"))
				&& (contextMap!=null && contextMap.get(SystemParameterConstant.isCBPFLAG).equals("Y")/*payBillServiceRequest.getContext().getBusinessId().equalsIgnoreCase("KEBRB") || payBillServiceRequest.getContext().getBusinessId().equalsIgnoreCase("UGBRB")*/)
				&& (activityId.equals("PMT_BP_MOBILE_WALLET_ONETIME") || activityId.equals("KITS_PTA_BILLPAY")
					|| activityId.equals("PMT_FT_PESALINK") || activityId.equals("PMT_BP_BILLPAY_PAYEE")
					|| activityId.equals("KITS_PTP_BILLPAY") || activityId.equals("PMT_BP_BILLPAY_ONETIME"))){


			//BillTransactionReferenceDetails[] billTransactionReferenceDetailsArray=new BillTransactionReferenceDetails[1];
			//Added condition for pesalink 7.0.0
			/*if(payBillServiceRequest.getContext().getActivityId().equalsIgnoreCase("KITS_PTA_BILLPAY") || payBillServiceRequest.getContext().getActivityId().equalsIgnoreCase("KITS_PTP_BILLPAY") )
			{}
			else
			{*/
			if(cbpCharge!=null){
				if(payBillServiceRequest.getChargeDTO().getCpbMakeBillPaymentFlag()!= null &&
						payBillServiceRequest.getChargeDTO().getCpbMakeBillPaymentFlag().equals("setBillPayFields")){
					billTransactionReferenceDetails6 = new BillTransactionReferenceDetails();
					billTransactionReferenceDetails6.setTypeCode("XelerateReferenceNumber"); //payBillServiceRequest.getChargeDTO().getTypeCode()
					billTransactionReferenceDetails6.setValue(payBillServiceRequest.getChargeDTO().getValue());

					//Added for CBP without KITS
					if(payBillServiceRequest.getContext().getActivityId().equalsIgnoreCase("KITS_PTA_BILLPAY") || payBillServiceRequest.getContext().getActivityId().equalsIgnoreCase("KITS_PTP_BILLPAY") )
					{
						billTransactionReferenceDetailsArray[5]=billTransactionReferenceDetails6;
						if(payBillServiceRequest.getContext().getActivityId().equalsIgnoreCase("KITS_PTA_BILLPAY") || payBillServiceRequest.getContext().getActivityId().equalsIgnoreCase("KITS_PTP_BILLPAY") )
						{
							billTransactionReferenceDetailsArray[5]=billTransactionReferenceDetails6;

						}
						else
						{
							billTransactionReferenceDetailsArray=new BillTransactionReferenceDetails[1];
							billTransactionReferenceDetailsArray[0]=billTransactionReferenceDetails6;
						}
					}
					else
					{
						billTransactionReferenceDetailsArray=new BillTransactionReferenceDetails[1];
						billTransactionReferenceDetailsArray[0]=billTransactionReferenceDetails6;
					}
				}
			}
		}

		// GePG Based Payment
		if(("TZNBC").equalsIgnoreCase(payBillServiceRequest.getContext().getBusinessId()) &&
				payBillServiceRequest.getBeneficiaryDTO().getBillerId().endsWith("-8")) {

			BillTransactionReferenceDetails billTransactionReferenceDetails1 = new BillTransactionReferenceDetails();
			billTransactionReferenceDetails1.setTypeCode("CreditAccountNumber");
			billTransactionReferenceDetails1.setValue(beenBeneficiaryDTO.getDestinationAccountNumber());

			BillTransactionReferenceDetails billTransactionReferenceDetails2 = new BillTransactionReferenceDetails();
			billTransactionReferenceDetails2.setTypeCode("transactiondatetime");
			billTransactionReferenceDetails2.setValue(DateTimeUtil.getStringFromDate(Calendar.getInstance().getTime(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));

			if(null == billTransactionReferenceDetailsArray || billTransactionReferenceDetailsArray.length == 0){
				billTransactionReferenceDetailsArray=new BillTransactionReferenceDetails[2];
				billTransactionReferenceDetailsArray[0] = billTransactionReferenceDetails1;
				billTransactionReferenceDetailsArray[1] = billTransactionReferenceDetails2;
			} else {
				List<BillTransactionReferenceDetails> billTransactionReferenceDetails = Arrays.asList(billTransactionReferenceDetailsArray);
				billTransactionReferenceDetails.add(billTransactionReferenceDetails1);
				billTransactionReferenceDetails.add(billTransactionReferenceDetails2);
				billTransactionReferenceDetailsArray = (BillTransactionReferenceDetails[]) billTransactionReferenceDetails.toArray();
			}
		}

		billPayment.setBillTransactionReferenceDetails(billTransactionReferenceDetailsArray);

		//Kadikope - BEGIN
		Calendar today = Calendar.getInstance();
		today.setTime(getBusinessDate(payBillServiceRequest.getContext()));

		if (payBillServiceRequest.getFromAccount() instanceof CreditCardAccountDTO) {
			CreditCardAccountDTO cardAccountDTO = (CreditCardAccountDTO) payBillServiceRequest.getFromAccount();
			billPayment.setFromCreditCardAccountNumber(cardAccountDTO.getAccountNumber());
			billPayment.setCreditCardAccountOrgCode(payBillServiceRequest.getOrdCode());

			billPayment.setPONumber(payBillServiceRequest.getContext().getActivityRefNo());
			// Credit Card Migration-Plan no. Addition
			String planNumber = (String) contextMap.get(SystemParameterConstant.PLAN_NUMBER_BILL_PAYMENT);
			if (null != planNumber) {
				billPayment.setPlan(planNumber);
			} else {
				billPayment.setPlan("10002");
			}
			//Add routing indicator VP
			billPayment.setEffectiveTxnDate(today);
			billPayment.setActionCode(payBillServiceRequest.getBeneficiaryDTO().getActionCode());
			billPayment.setStoreNumber(payBillServiceRequest.getBeneficiaryDTO().getStoreNumber());
			billPayment.setMemoLine(getMemoLineType(payBillServiceRequest, today));
		}
		//Kadikope - END

		return billPayment;
    }

    private StringBuffer createRemarks(String narration, String billRefNo, String orgRefNo, String billerRefNo2, String businessId,String billerId) {
		StringBuffer remarks = new StringBuffer();
		//Added as a part of CR#43
		if(businessId.equalsIgnoreCase(CommonConstants.KEBRB_BUSINESS_ID)){
			narration="UB";
			remarks.append(narration);
			remarks.append(SINGLE_SPACE_SEPARATOR);
			remarks.append("BP:");
			if(billerId!=null && StringUtils.isNotEmpty(billerId)){
				remarks.append(billerId);
			}
			remarks.append(FORWARD_SLASH_SEPARATOR);
			remarks.append(orgRefNo);
		}else{
		narration="UB";
		remarks.append(narration);
		remarks.append(FORWARD_SLASH_SEPARATOR);
		remarks.append(orgRefNo).append(FORWARD_SLASH_SEPARATOR);
		remarks.append(billRefNo);
		//Added as a part of CR#43
		if(billerId!=null && StringUtils.isNotEmpty(billerId)){
			remarks.append(FORWARD_SLASH_SEPARATOR);
			remarks.append(billerId);
		}
		}
		return remarks;
    }

    private MemoLineType getMemoLineType(PayBillServiceRequest payBillServiceRequest, Calendar today) {
    	MemoLineType memoLine = new MemoLineType();
    	memoLine.setMemoLine1("SHM BP:" + payBillServiceRequest.getBeneficiaryDTO().getBillRefNo());
    	memoLine.setMemoLine2("POREF:" + payBillServiceRequest.getContext().getActivityRefNo());
    	memoLine.setMemoLine3("SHM-WALLET/BILL-TRAN/AMT:" + payBillServiceRequest.getBillAmount().doubleValue());
    	//memoLine.setMemoLine3("SHM: " + (String) payBillServiceRequest.getContext().getContextMap().get(SystemParameterConstant.CC_STORE));
    	memoLine.setMemoLine4("REMARKS:SHM-WALLET/BILL-TRAN ,DTE:" + getFormattedDate(today));
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

        public static void setCreditorName(String creditorNameReq)
        {
        	if(creditorNameReq != null)
        		creditorName = creditorNameReq;
        }

}
