package com.barclays.bmg.airtimetopup.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BillerDTO;

public class AirTimeTopUpValidateResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = -4720547120265324636L;

    private String billerTyp;

    private BillerDTO billerDTO;

    private String billerAcctNmbr;

    /** amount */
    private String txnAmt;

    /** mobile number */
    private String mblNo;

    /**
     * @return the txnAmt
     */
    public String getTxnAmt() {
	return txnAmt;
    }

    /**
     * @param txnAmt
     *            the txnAmt to set
     */
    public void setTxnAmt(String txnAmt) {
	this.txnAmt = txnAmt;
    }

    /**
     * @return the mblNo
     */
    public String getMblNo() {
	return mblNo;
    }

    /**
     * @param mblNo
     *            the mblNo to set
     */
    public void setMblNo(String mblNo) {
	this.mblNo = mblNo;
    }

    /**
     * @return the billerTyp
     */
    public String getBillerTyp() {
	return billerTyp;
    }

    /**
     * @param billerTyp
     *            the billerTyp to set
     */
    public void setBillerTyp(String billerTyp) {
	this.billerTyp = billerTyp;
    }

    /**
     * @return the billerDTO
     */
    public BillerDTO getBillerDTO() {
	return billerDTO;
    }

    /**
     * @param billerDTO
     *            the billerDTO to set
     */
    public void setBillerDTO(BillerDTO billerDTO) {
	this.billerDTO = billerDTO;
    }

    /**
     * @return the billerAcctNmbr
     */
    public String getBillerAcctNmbr() {
	return billerAcctNmbr;
    }

    /**
     * @param billerAcctNmbr
     *            the billerAcctNmbr to set
     */
    public void setBillerAcctNmbr(String billerAcctNmbr) {
	this.billerAcctNmbr = billerAcctNmbr;
    }
}