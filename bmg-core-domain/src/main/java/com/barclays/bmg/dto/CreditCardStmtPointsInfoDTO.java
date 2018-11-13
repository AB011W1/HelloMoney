package com.barclays.bmg.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CreditCardStmtPointsInfoDTO implements Serializable {

    private static final long serialVersionUID = -4584358137807745013L;

    private BigDecimal prevBalance;

    private BigDecimal pointsRedeemed;

    private BigDecimal pointsEarned;

    private BigDecimal newBalance;

    public BigDecimal getPrevBalance() {
	return prevBalance;
    }

    public void setPrevBalance(BigDecimal prevBalance) {
	this.prevBalance = prevBalance;
    }

    public BigDecimal getPointsRedeemed() {
	return pointsRedeemed;
    }

    public void setPointsRedeemed(BigDecimal pointsRedeemed) {
	this.pointsRedeemed = pointsRedeemed;
    }

    public BigDecimal getPointsEarned() {
	return pointsEarned;
    }

    public void setPointsEarned(BigDecimal pointsEarned) {
	this.pointsEarned = pointsEarned;
    }

    public BigDecimal getNewBalance() {
	return newBalance;
    }

    public void setNewBalance(BigDecimal newBalance) {
	this.newBalance = newBalance;
    }

}
