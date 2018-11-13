 package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.internal.nonregistered;

 import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

 @JsonIgnoreProperties(ignoreUnknown=true)
 public class IntNRFundTxConfirm
 {

   @JsonProperty
   private PayHdr payHdr;

   @JsonProperty
   private IntNRFundTxConfirmPayData payData;

   public PayHdr getPayHdr()
   {
     return this.payHdr;
   }

   public void setPayHdr(PayHdr payHdr) {
    this.payHdr = payHdr;
   }

   public IntNRFundTxConfirmPayData getPayData() {
     return this.payData;
   }

   public void setPayData(IntNRFundTxConfirmPayData payData) {
   this.payData = payData;
   }
 }