/**
 * PayeeInfoData.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayeeInfoData {
    @JsonProperty
    private PayInfo payInfo;

    @JsonProperty
    private TransactionLimit txnLmt;

    public PayInfo getPayInfo() {
	return payInfo;
    }

    public void setPayInfo(PayInfo payInfo) {
	this.payInfo = payInfo;
    }

    public TransactionLimit getTxnLmt() {
	return txnLmt;
    }

    public void setTxnLmt(TransactionLimit txnLmt) {
	this.txnLmt = txnLmt;
    }

}
