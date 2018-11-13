 package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.internal.nonregistered;

 import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccountDetails;

 @JsonIgnoreProperties(ignoreUnknown=true)
 public class AccListIntNRPayData
 {

   @JsonProperty
   private List<AccountDetails> srcLst;

   public List<AccountDetails> getSrcLst()
   {
   return this.srcLst;
   }

   public void setSrcLst(List<AccountDetails> srcLst) {
   this.srcLst = srcLst;
   }
 }
