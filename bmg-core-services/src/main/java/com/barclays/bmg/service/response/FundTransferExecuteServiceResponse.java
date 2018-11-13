package com.barclays.bmg.service.response;

import java.util.Date;

import com.barclays.bmg.context.ResponseContext;

public class FundTransferExecuteServiceResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = -4424068390536397768L;
    private Date trnDate;
    private String trnRef;

    public Date getTrnDate() {
	return new Date(trnDate.getTime());
    }

    public void setTrnDate(Date trnDate) {
	if (trnDate == null) {
	    this.trnDate = null;
	} else {
	    this.trnDate = new Date(trnDate.getTime());
	}
    }

    public String getTrnRef() {
	return trnRef;
    }

    public void setTrnRef(String trnRef) {
	this.trnRef = trnRef;
    }

}
