package com.barclays.bmg.service.response;

import java.sql.Timestamp;
import java.util.Date;

import com.barclays.bmg.context.ResponseContext;

public class TransactionAbilityResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = -3699933261797650803L;
    private Timestamp cutOffTime;
    private boolean isTransactionable = true;
    private Date nextBusinessDate;

    public Timestamp getCutOffTime() {
	return cutOffTime;
    }

    public void setCutOffTime(Timestamp cutOffTime) {
	this.cutOffTime = cutOffTime;
    }

    public boolean isTransactionable() {
	return isTransactionable;
    }

    public void setTransactionable(boolean isTransactionable) {
	this.isTransactionable = isTransactionable;
    }

    public Date getNextBusinessDate() {
	return new Date(nextBusinessDate.getTime());
    }

    public void setNextBusinessDate(Date nextBusinessDate) {
	if (nextBusinessDate == null) {
	    this.nextBusinessDate = null;
	} else {
	    this.nextBusinessDate = new Date(nextBusinessDate.getTime());
	}
    }
}
