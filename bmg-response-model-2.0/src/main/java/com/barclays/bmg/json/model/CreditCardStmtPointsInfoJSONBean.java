package com.barclays.bmg.json.model;

import java.io.Serializable;

import com.barclays.bmg.dto.CreditCardStmtPointsInfoDTO;

public class CreditCardStmtPointsInfoJSONBean implements Serializable {

	private static final long serialVersionUID = 21058919675391932L;

	private String prevBal;
	private String ptsRede;
	private String ptsEarn;
	private String newBal;

	public CreditCardStmtPointsInfoJSONBean(
			CreditCardStmtPointsInfoDTO ccRewardPts) {

		super();

		if (ccRewardPts != null) {
			this.prevBal = ccRewardPts.getPrevBalance().toString();
			this.ptsRede = ccRewardPts.getPointsRedeemed().toString();
			this.ptsEarn = ccRewardPts.getPointsEarned().toString();
			this.newBal = ccRewardPts.getNewBalance().toString();
		} else {
			this.prevBal = "0";
			this.ptsRede = "0";
			this.ptsEarn = "0";
			this.newBal = "0";
		}

	}

	public CreditCardStmtPointsInfoJSONBean() {
		super();
	}

	public String getPrevBal() {
		return prevBal;
	}

	public void setPrevBal(String prevBal) {
		this.prevBal = prevBal;
	}

	public String getPtsRede() {
		return ptsRede;
	}

	public void setPtsRede(String ptsRede) {
		this.ptsRede = ptsRede;
	}

	public String getPtsEarn() {
		return ptsEarn;
	}

	public void setPtsEarn(String ptsEarn) {
		this.ptsEarn = ptsEarn;
	}

	public String getNewBal() {
		return newBal;
	}

	public void setNewBal(String newBal) {
		this.newBal = newBal;
	}

}
