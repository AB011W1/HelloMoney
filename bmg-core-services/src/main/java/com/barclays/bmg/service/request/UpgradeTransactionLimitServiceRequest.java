package com.barclays.bmg.service.request;

import java.util.Date;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.Amount;

public class UpgradeTransactionLimitServiceRequest extends RequestContext {
    private Amount amtInLCY;
    private Date txnDate;

    public Amount getAmtInLCY() {
	return amtInLCY;
    }

    public void setAmtInLCY(Amount amtInLCY) {
	this.amtInLCY = amtInLCY;
    }

    public Date getTxnDate() {
	return new Date(txnDate.getTime());
    }

    public void setTxnDate(Date txnDate) {
	if (txnDate == null) {
	    this.txnDate = null;
	} else {
	    this.txnDate = new Date(txnDate.getTime());
	}
    }

}
