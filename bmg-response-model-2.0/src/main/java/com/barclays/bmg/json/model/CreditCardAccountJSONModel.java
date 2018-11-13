package com.barclays.bmg.json.model;

import java.math.BigDecimal;

import com.barclays.bmg.constants.AccountConstants;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.utils.BMGFormatUtility;

public class CreditCardAccountJSONModel extends AccountJSONModel {

    private static final long serialVersionUID = 6952311859666249249L;

    private String crdNo = "";
    private String mkdCrdNo = "";
    private String pmtDueDt = "";
    private String orgCode = "";
    private String cardHolder = "";

    private AmountJSONModel crLmt;
    private AmountJSONModel avlCrLmt;
    private AmountJSONModel curBal;
    private AmountJSONModel cshLmt;
    private AmountJSONModel avlCshLmt;
    private AmountJSONModel amtOvrDue;
    private AmountJSONModel minDueAmt;
    private AmountJSONModel outstandingAmt;

    public CreditCardAccountJSONModel(CreditCardAccountDTO accountDTO) {
	super(accountDTO);
	String currency = accountDTO.getCurrency();

	this.crdNo = accountDTO.getPrimary().getCardNumber();
	this.orgCode = accountDTO.getPrimary().getCreditCardOrgCode();
	this.cardHolder = accountDTO.getPrimary().getCardHolder();

	this.crLmt = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility.getFormattedAmount(accountDTO.getCreditLimit()));
	this.avlCrLmt = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility.getFormattedAmount(accountDTO.getAvailableCreditLimit()));
	this.curBal = BMGFormatUtility.getJSONAmount(currency, getPositiveCurrentBalance(accountDTO.getCurrentBalance()));

	this.cshLmt = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility.getFormattedAmount(accountDTO.getCashLimit()));
	this.avlCshLmt = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility.getFormattedAmount(accountDTO.getAvailableCashLimit()));
	this.amtOvrDue = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility.getFormattedAmount(accountDTO.getAmountOverDue()));
	this.pmtDueDt = BMGFormatUtility.getShortDate(accountDTO.getPaymentDueDate());
	this.minDueAmt = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility.getFormattedAmount(accountDTO.getMinPaymentAmount()));
	this.outstandingAmt = BMGFormatUtility.getJSONAmount(currency, BMGFormatUtility.getFormattedAmount(accountDTO.getOutstandingBalance()));

    }

    public String getCrdNo() {
	return crdNo;
    }

    public void setCrdNo(String crdNo) {
	this.crdNo = crdNo;
    }

    private static boolean currentBalancePositive(BigDecimal bal) {
	if (bal == null) {
	    return false;
	}
	return bal.doubleValue() > 0;
    }

    private static String getPositiveCurrentBalance(BigDecimal bal) {
	if (bal == null) {
	    return null;
	}
	String strAmount = "";
	BigDecimal amount = bal.abs();

	if (currentBalancePositive(bal)) {
	    strAmount = BMGFormatUtility.getFormattedAmount(amount) + "(Cr)";
	} else {
	    strAmount = BMGFormatUtility.getFormattedAmount(amount);
	}
	return strAmount;
    }

    public AmountJSONModel getCrLmt() {
	return crLmt;
    }

    public void setCrLmt(AmountJSONModel crLmt) {
	this.crLmt = crLmt;
    }

    public AmountJSONModel getAvlCrLmt() {
	return avlCrLmt;
    }

    public void setAvlCrLmt(AmountJSONModel avlCrLmt) {
	this.avlCrLmt = avlCrLmt;
    }

    public AmountJSONModel getCurBal() {
	return curBal;
    }

    public void setCurBal(AmountJSONModel curBal) {
	this.curBal = curBal;
    }

    public AmountJSONModel getCshLmt() {
	return cshLmt;
    }

    public void setCshLmt(AmountJSONModel cshLmt) {
	this.cshLmt = cshLmt;
    }

    public AmountJSONModel getAvlCshLmt() {
	return avlCshLmt;
    }

    public void setAvlCshLmt(AmountJSONModel avlCshLmt) {
	this.avlCshLmt = avlCshLmt;
    }

    public AmountJSONModel getAmtOvrDue() {
	return amtOvrDue;
    }

    public void setAmtOvrDue(AmountJSONModel amtOvrDue) {
	this.amtOvrDue = amtOvrDue;
    }

    public AmountJSONModel getMinDueAmt() {
	return minDueAmt;
    }

    public void setMinDueAmt(AmountJSONModel minDueAmt) {
	this.minDueAmt = minDueAmt;
    }

    public String getPmtDueDt() {
	return pmtDueDt;
    }

    public void setPmtDueDt(String pmtDueDt) {
	this.pmtDueDt = pmtDueDt;
    }

    public String getMkdCrdNo() {
	return mkdCrdNo;
    }

    public void setMkdCrdNo(String mkdCrdNo) {
	this.mkdCrdNo = mkdCrdNo;
    }

    @Override
    public String getTyp() {

	return AccountConstants.CC_ACCOUNTS;
    }

    public AmountJSONModel getOutstandingAmt() {
	return outstandingAmt;
    }

    public void setOutstandingAmt(AmountJSONModel outstandingAmt) {
	this.outstandingAmt = outstandingAmt;
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

}
