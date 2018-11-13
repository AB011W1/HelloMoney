package com.barclays.bmg.json.model;

import java.io.Serializable;

import com.barclays.bmg.dto.CustomerAccountDTO;

public abstract class AccountJSONModel implements Serializable{

	private static final long serialVersionUID = -8388560720961153574L;

	private String typ = "";

	private String actNo = "";

	private String desc = "";

	private String curr = "";

	private String mkdActNo = "";
	private String priInd;
	private String accStatus = "";
	 private AmountJSONModel netBalanceAmount;

	private AmountJSONModel currentBookBalanceAmount;

	public AccountJSONModel(CustomerAccountDTO accountDTO) {
		this.actNo = accountDTO.getAccountNumber();
		this.desc = accountDTO.getDesc();
		this.curr = accountDTO.getCurrency();
		this.priInd = accountDTO.getPriInd();
	}

	public AccountJSONModel() {

	}

	public String getMkdActNo() {
		return mkdActNo;
	}

	public void setMkdActNo(String maskedAcctNo) {
		this.mkdActNo = maskedAcctNo;
	}

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCurr() {
		return curr;
	}

	public void setCurr(String curr) {
		this.curr = curr;
	}

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	/**
	 * @return the priInd
	 */
	public String getPriInd() {
		return priInd;
	}

	/**
	 * @param priInd the priInd to set
	 */
	public void setPriInd(String priInd) {
		this.priInd = priInd;
	}
	public String getAccStatus() {
		return accStatus;
	}
	public void setAccStatus(String accStatus) {
		this.accStatus = accStatus;
	}

	/**
	 * @return the netBalanceAmount
	 */
	public AmountJSONModel getNetBalanceAmount() {
		return netBalanceAmount;
	}

	/**
	 * @param netBalanceAmount the netBalanceAmount to set
	 */
	public void setNetBalanceAmount(AmountJSONModel netBalanceAmount) {
		this.netBalanceAmount = netBalanceAmount;
	}

	/**
	 * @return the currentBookBalanceAmount
	 */
	public AmountJSONModel getCurrentBookBalanceAmount() {
		return currentBookBalanceAmount;
	}

	/**
	 * @param currentBookBalanceAmount the currentBookBalanceAmount to set
	 */
	public void setCurrentBookBalanceAmount(AmountJSONModel currentBookBalanceAmount) {
		this.currentBookBalanceAmount = currentBookBalanceAmount;
	}



}
