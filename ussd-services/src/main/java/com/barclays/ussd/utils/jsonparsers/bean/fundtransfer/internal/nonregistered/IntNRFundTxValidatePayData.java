 package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.internal.nonregistered;

 import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccountDetails;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.TransactionAmt;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

 @JsonIgnoreProperties(ignoreUnknown=true)
 public class IntNRFundTxValidatePayData
 {

   @JsonProperty
   private AccountDetails frmAct;

   @JsonProperty
   private CustomerMobileRegAcct creditcard;

   @JsonProperty
   private String toActNo;

   @JsonProperty
   private String mkdActNo;

   @JsonProperty
   private String beneAccountNumber;

   @JsonProperty
   private TransactionAmt txnAmt;

   @JsonProperty
   private String txnRefNo;

   // MakeDomesticFundTransferRequest Fields - CBP 30/05/2017
   @JsonProperty
   private TransactionAmt txnChargeAmt;
   @JsonProperty
	private String feeGLAccount;
   @JsonProperty
	private String chargeNarration;
   @JsonProperty
	private Double taxAmount;
   @JsonProperty
	private String taxGLAccount;
   @JsonProperty
	private String ExciseDutyNarration;
   @JsonProperty
	private String typeCode;
   @JsonProperty
	private String value;

   public AccountDetails getFrActNo()
   {
      return this.frmAct;
   }

   public void setFrActNo(AccountDetails frAct) {
     this.frmAct = frAct;
   }

   public String getFrMskActNo()
   {
	   return this.mkdActNo;
   }

   public void setFrMskActNo(String frMskActNo) {
	   this.mkdActNo = frMskActNo;
   }

   public TransactionAmt getTxnAmt()
   {
	   return this.txnAmt;
   }

   public void setTxnAmt(TransactionAmt txnAmt) {
       this.txnAmt = txnAmt;
   }

   public String getTxnRefNo() {
	   return this.txnRefNo;
   }

   public void setTxnRefNo(String txnRefNo) {
      this.txnRefNo = txnRefNo;
   }

   public String getToActNo() {
      return this.toActNo;
   }

   public void setToActNo(String toActNo) {
      this.toActNo = toActNo;
   }

   public String getToMskActNo() {
      return this.beneAccountNumber;
   }

   public void setToMskActNo(String toMskActNo) {
     this.beneAccountNumber = toMskActNo;
   }

   public CustomerMobileRegAcct getCreditcard() {
	return creditcard;
   }

   public void setCreditcard(CustomerMobileRegAcct creditcard) {
	this.creditcard = creditcard;
   }

	public TransactionAmt getTxnChargeAmt() {
		return txnChargeAmt;
	}

	public void setTxnChargeAmt(TransactionAmt txnChargeAmt) {
		this.txnChargeAmt = txnChargeAmt;
	}

	public String getFeeGLAccount() {
		return feeGLAccount;
	}

	public void setFeeGLAccount(String feeGLAccount) {
		this.feeGLAccount = feeGLAccount;
	}

	public String getChargeNarration() {
		return chargeNarration;
	}

	public void setChargeNarration(String chargeNarration) {
		this.chargeNarration = chargeNarration;
	}

	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public String getTaxGLAccount() {
		return taxGLAccount;
	}

	public void setTaxGLAccount(String taxGLAccount) {
		this.taxGLAccount = taxGLAccount;
	}

	public String getExciseDutyNarration() {
		return ExciseDutyNarration;
	}

	public void setExciseDutyNarration(String exciseDutyNarration) {
		ExciseDutyNarration = exciseDutyNarration;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


 }

