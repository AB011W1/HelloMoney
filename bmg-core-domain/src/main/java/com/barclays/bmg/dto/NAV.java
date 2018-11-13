package com.barclays.bmg.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class NAV implements Serializable {

    private static final long serialVersionUID = 1192387178914067834L;
    private Date navDate;
    private String navAmount;
    private boolean needDateFlag;

    public Date getNavDate() {
	if (navDate != null) {
	    return new Date(navDate.getTime());
	}
	return null;
    }

    public void setNavDate(Date navDate) {
	if (navDate != null) {
	    this.navDate = new Date(navDate.getTime());
	} else {
	    this.navDate = null;
	}
    }

    public String getNavAmount() {
	return navAmount;
    }

    public void setNavAmount(String navAmount) {

	this.navAmount = (BigDecimal.valueOf(new BigDecimal(navAmount).doubleValue()).setScale(4, BigDecimal.ROUND_HALF_UP)).toString();

    }

    public boolean isNeedDateFlag() {
	return needDateFlag;
    }

    public void setNeedDateFlag(boolean needDateFlag) {
	this.needDateFlag = needDateFlag;
    }

}
