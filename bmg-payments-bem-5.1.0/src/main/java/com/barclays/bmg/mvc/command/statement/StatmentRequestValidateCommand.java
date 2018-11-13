package com.barclays.bmg.mvc.command.statement;

import java.util.Calendar;

import com.barclays.bmg.context.RequestContext;

public class StatmentRequestValidateCommand extends RequestContext {

    /** Account type */
    private String actNo;
    private String fromDate;
    private String toDate;

    private Calendar currentBussCalander;

    public Calendar getCurrentBussCalander() {
	return currentBussCalander;
    }

    public void setCurrentBussCalander(Calendar currentBussCalander) {
	this.currentBussCalander = currentBussCalander;
    }

    /**
     * @return the actNo
     */
    public String getActNo() {
	return actNo;
    }

    /**
     * @param actNo
     *            the actNo to set
     */
    public void setActNo(String actNo) {
	this.actNo = actNo;
    }

    /**
     * @return the fromDate
     */
    public String getFromDate() {
	return fromDate;
    }

    /**
     * @param fromDate
     *            the fromDate to set
     */
    public void setFromDate(String fromDate) {
	this.fromDate = fromDate;
    }

    /**
     * @return the toDate
     */
    public String getToDate() {
	return toDate;
    }

    /**
     * @param toDate
     *            the toDate to set
     */
    public void setToDate(String toDate) {
	this.toDate = toDate;
    }
}