package com.barclays.ussd.utils.jsonparsers.bean.login;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.bean.Amount;
import com.barclays.ussd.utils.jsonparsers.bean.balanceEnq.AvilableBalance;
import com.barclays.ussd.utils.jsonparsers.bean.balanceEnq.CurrentBalance;
import com.barclays.ussd.utils.jsonparsers.bean.balanceEnq.CurrentBookBalanceAmount;
import com.barclays.ussd.utils.jsonparsers.bean.balanceEnq.NetBalanceAmount;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerMobileRegAcct {

    @JsonProperty
    private String crdNo;

    @JsonProperty
    private String orgCode;

    @JsonProperty
    private String cardHolder;

    @JsonProperty
    private String actNo;

    @JsonProperty
    private String brnCde;

    @JsonProperty
    private String priInd;

    @JsonProperty
    private String mkdActNo;

    @JsonProperty
    private String brnNam;

    @JsonProperty
    private CurrentBalance curBal;

    @JsonProperty
    private AvilableBalance avblBal;

    @JsonProperty
    private String accStatus;

    @JsonProperty
    private String curr;

    @JsonProperty
    private NetBalanceAmount netBalanceAmount;

    @JsonProperty
    private CurrentBookBalanceAmount currentBookBalanceAmount;

    @JsonProperty
    private Amount amtOvrDue;

    @JsonProperty
    private Amount minDueAmt;

    @JsonProperty
    private Amount avlCrLmt;

    @JsonProperty
    private String pmtDueDt;

    @JsonProperty
    private Amount outstandingAmt;

    @JsonProperty
    private String mkdCrdNo;

  //For groupwallet
    @JsonProperty
    private String bankCif;

    @JsonProperty
	private String groupWalletIndicator;


    public String getBankCif() {
		return bankCif;
	}

	public void setBankCif(String bankCif) {
		this.bankCif = bankCif;
	}

	public String getGroupWalletIndicator() {
		return groupWalletIndicator;
	}

	public void setGroupWalletIndicator(String groupWalletIndicator) {
		this.groupWalletIndicator = groupWalletIndicator;
	}

    public String getAccStatus() {
	return accStatus;
    }

    public void setAccStatus(String accStatus) {
	this.accStatus = accStatus;
    }

    public String getCurr() {
	return curr;
    }

    public void setCurr(String curr) {
	this.curr = curr;
    }

    public String getActNo() {
	return actNo;
    }

    public void setActNo(String actNo) {
	this.actNo = actNo;
    }

    public String getBrnCde() {
	return brnCde;
    }

    public void setBrnCde(String brnCde) {
	this.brnCde = brnCde;
    }

    public void setPriInd(String priInd) {
	this.priInd = priInd;
    }

    public String getMkdActNo() {
	return mkdActNo;
    }

    public void setMkdActNo(String mkdActNo) {
	this.mkdActNo = mkdActNo;
    }

    public String getBrnNam() {
	return brnNam;
    }

    public void setBrnNam(String brnNam) {
	this.brnNam = brnNam;
    }

    public CurrentBalance getCurBal() {
	return curBal;
    }

    public void setCurBal(CurrentBalance curBal) {
	this.curBal = curBal;
    }

    public AvilableBalance getAvblBal() {
	return avblBal;
    }

    public void setAvblBal(AvilableBalance avblBal) {
	this.avblBal = avblBal;
    }

    public String getPriInd() {
	return priInd;
    }

    public String getOrgCode() {
	return orgCode;
    }

    public void setOrgCode(String orgCode) {
	this.orgCode = orgCode;
    }

    public String getCardHolder() {
	return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
	this.cardHolder = cardHolder;
    }

    /**
     * @return the netBalanceAmount
     */
    public NetBalanceAmount getNetBalanceAmount() {
	return netBalanceAmount;
    }

    /**
     * @param netBalanceAmount
     *            the netBalanceAmount to set
     */
    public void setNetBalanceAmount(NetBalanceAmount netBalanceAmount) {
	this.netBalanceAmount = netBalanceAmount;
    }

    /**
     * @return the currentBookBalanceAmount
     */
    public CurrentBookBalanceAmount getCurrentBookBalanceAmount() {
	return currentBookBalanceAmount;
    }

    /**
     * @param currentBookBalanceAmount
     *            the currentBookBalanceAmount to set
     */
    public void setCurrentBookBalanceAmount(CurrentBookBalanceAmount currentBookBalanceAmount) {
	this.currentBookBalanceAmount = currentBookBalanceAmount;
    }

    public Amount getAmtOvrDue() {
	return amtOvrDue;
    }

    public void setAmtOvrDue(Amount amtOvrDue) {
	this.amtOvrDue = amtOvrDue;
    }

    public Amount getMinDueAmt() {
	return minDueAmt;
    }

    public void setMinDueAmt(Amount minDueAmt) {
	this.minDueAmt = minDueAmt;
    }

    public Amount getAvlCrLmt() {
	return avlCrLmt;
    }

    public void setAvlCrLmt(Amount avlCrLmt) {
	this.avlCrLmt = avlCrLmt;
    }

    public String getPmtDueDt() {
	return pmtDueDt;
    }

    public void setPmtDueDt(String pmtDueDt) {
	this.pmtDueDt = pmtDueDt;
    }

    public Amount getOutstandingAmt() {
	return outstandingAmt;
    }

    public void setOutstandingAmt(Amount outstandingAmt) {
	this.outstandingAmt = outstandingAmt;
    }

    public String getCrdNo() {
	return crdNo;
    }

    public void setCrdNo(String crdNo) {
	this.crdNo = crdNo;
    }

    public String getMkdCrdNo() {
	return mkdCrdNo;
    }

    public void setMkdCrdNo(String mkdCrdNo) {
	this.mkdCrdNo = mkdCrdNo;
    }

}
