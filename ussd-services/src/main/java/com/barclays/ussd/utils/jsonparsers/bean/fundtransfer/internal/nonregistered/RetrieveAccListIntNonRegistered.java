 package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.internal.nonregistered;

 import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

 @JsonIgnoreProperties(ignoreUnknown=true)
 public class RetrieveAccListIntNonRegistered
 {

   @JsonProperty
   private PayHdr payHdr;

   @JsonProperty
   private AccListIntNRPayData payData;

   public PayHdr getPayHdr()
   {
      return this.payHdr;
   }
   public void setPayHdr(PayHdr payHdr) {
     this.payHdr = payHdr;
   }
   public AccListIntNRPayData getPayData() {
     return this.payData;
   }
   public void setPayData(AccListIntNRPayData payData) {
     this.payData = payData;
   }
 }