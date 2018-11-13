/**
 * FormValResData.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.TransactionAmt;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormValResData {

    /**
     * frmAct
     */
    @JsonProperty
    private FromAccount frmAct;
    /**
     * txnAmt
     */
    @JsonProperty
    private TransactionAmt txnAmt;

    /**
     * txnRefNo
     */
    @JsonProperty
    private String txnRefNo;
    /**
     * payInfo
     */
    @JsonProperty
    private PayInfo payInfo;

    /**
     * @return the payInfo
     */
    public PayInfo getPayInfo() {
	return payInfo;
    }

    /**
     * @param payInfo
     *            the payInfo to set
     */
    public void setPayInfo(PayInfo payInfo) {
	this.payInfo = payInfo;
    }

    /**
     * @return the frmAct
     */
    public FromAccount getFrmAct() {
	return frmAct;
    }

    /**
     * @param frmAct
     *            the frmAct to set
     */
    public void setFrmAct(FromAccount frmAct) {
	this.frmAct = frmAct;
    }

    /**
     * @return the txnAmt
     */
    public TransactionAmt getTxnAmt() {
	return txnAmt;
    }

    /**
     * @param txnAmt
     *            the txnAmt to set
     */
    public void setTxnAmt(TransactionAmt txnAmt) {
	this.txnAmt = txnAmt;
    }

    /**
     * @param txnRefNo
     *            the txnRefNo to set
     */
    public void setTxnRefNo(String txnRefNo) {
	this.txnRefNo = txnRefNo;
    }

    /**
     * @return the txnRefNo
     */
    public String getTxnRefNo() {
	return txnRefNo;
    }

}
