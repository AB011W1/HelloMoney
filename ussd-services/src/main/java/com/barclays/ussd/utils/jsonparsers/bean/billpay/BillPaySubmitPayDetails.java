/**
 * BillPaySubmitData.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.billpay;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.TransactionAmt;
import com.barclays.ussd.utils.jsonparsers.bean.vlpb.RefNo;

/**
 * @author BTCI
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillPaySubmitPayDetails {

    /**
     * payNckNam
     */
    @JsonProperty
    private String payNckNam;

    @JsonProperty
    private String bilrNam;

    /**
     * txnAmt
     */
    @JsonProperty
    private TransactionAmt amt;

    @JsonProperty
    private RefNo refNo;

    /**
     * payId
     */
    @JsonProperty
    private String payId;
  //CR#61 Changes starts
    /**
     * bilrId
     */
    @JsonProperty
    private String bilrId;

    // WUC-2 Botswana Biller change - 03/07/2017
    @JsonProperty
    private RefNo wucContractRefNo;

    public String getBilrId() {
		return bilrId;
	}

	public void setBilrId(String bilrId) {
		this.bilrId = bilrId;
	}

    /**
     * @return payNckNam
     */
    public String getPayNckNam() {
	return payNckNam;
    }

    /**
     * @param payNckNam
     */
    public void setPayNckNam(String payNckNam) {
	this.payNckNam = payNckNam;
    }

    /**
     * @return amt
     */
    public TransactionAmt getTransactionAmtDetails() {
	return amt;
    }

    /**
     * @param amt
     */
    public void setAmt(TransactionAmt amt) {
	this.amt = amt;
    }

    /**
     * @return payId
     */
    public String getPayId() {
	return payId;
    }

    /**
     * @param payId
     */
    public void setPayId(String payId) {
	this.payId = payId;
    }

    /**
     * @return the bilrNam
     */
    public String getBilrNam() {
	return bilrNam;
    }

    /**
     * @param bilrNam
     *            the bilrNam to set
     */
    public void setBilrNam(String bilrNam) {
	this.bilrNam = bilrNam;
    }

    /**
     * @return the amt
     */
    public TransactionAmt getAmt() {
	return amt;
    }

    /**
     * @return the refNo
     */
    public RefNo getRefNo() {
	return refNo;
    }

    /**
     * @param refNo
     *            the refNo to set
     */
    public void setRefNo(RefNo refNo) {
	this.refNo = refNo;
    }

    /**
     * @return the wucContractRefNo
     */
	public RefNo getWucContractRefNo() {
		return wucContractRefNo;
	}

	/**
     * @param wucContractRefNo
     *            the wucContractRefNo to set
     */
	public void setWucContractRefNo(RefNo wucContractRefNo) {
		this.wucContractRefNo = wucContractRefNo;
	}



}
