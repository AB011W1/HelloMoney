 package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.internal.nonregistered;

 import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

 @JsonIgnoreProperties(ignoreUnknown=true)
 public class IntNRFundTxValidate
 {

   @JsonProperty
   private PayHdr payHdr;

   @JsonProperty
   private IntNRFundTxValidatePayData payData;

   public PayHdr getPayHdr()
   {
     return this.payHdr;
   }

   public void setPayHdr(PayHdr payHdr) {
    this.payHdr = payHdr;
   }

   public IntNRFundTxValidatePayData getPayData() {
     return this.payData;
   }

   public void setPayData(IntNRFundTxValidatePayData payData) {
    this.payData = payData;
   }
 }

