package com.barclays.bmg.airtimetopup.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.dto.CASAAccountDTO;

public class AirTimeTopUpValidateServiceResopnse extends ResponseContext {

    private CASAAccountDTO account;

    private BillerDTO billerDTO;

    private String txnRefNo;

    private String mblNmbr;

    private String amount;

    /**
     * @return the account
     */
    public CASAAccountDTO getAccount() {
	return account;
    }

    /**
     * @param account
     *            the account to set
     */
    public void setAccount(CASAAccountDTO account) {
	this.account = account;
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
     * @return the txnRefNo
     */
    public String getTxnRefNo() {
	return txnRefNo;
    }

    /**
     * @param txnRefNo
     *            the txnRefNo to set
     */
    public void setTxnRefNo(String txnRefNo) {
	this.txnRefNo = txnRefNo;
    }

    /**
     * @return the mblNmbr
     */
    public String getMblNmbr() {
	return mblNmbr;
    }

    /**
     * @param mblNmbr
     *            the mblNmbr to set
     */
    public void setMblNmbr(String mblNmbr) {
	this.mblNmbr = mblNmbr;
    }

    /**
     * @return the amount
     */
    public String getAmount() {
	return amount;
    }

    /**
     * @param amount
     *            the amount to set
     */
    public void setAmount(String amount) {
	this.amount = amount;
    }

}
