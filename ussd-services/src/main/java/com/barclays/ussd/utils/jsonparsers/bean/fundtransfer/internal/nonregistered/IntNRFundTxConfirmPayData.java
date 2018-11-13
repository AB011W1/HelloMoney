 package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.internal.nonregistered;

 import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

 @JsonIgnoreProperties(ignoreUnknown=true)
 public class IntNRFundTxConfirmPayData
 {

   @JsonProperty
   private String txnRefNo;

   @JsonProperty
   private String txnDtTm;

   public String getRefNo()
   {
     return this.txnRefNo;
   }

   public void setRefNo(String refNo) {
   this.txnRefNo = refNo;
   }

   public String getTxnDt() {
   return this.txnDtTm;
   }

   public void setTxnDt(String txnDt) {
    this.txnDtTm = txnDt;
   }
 }