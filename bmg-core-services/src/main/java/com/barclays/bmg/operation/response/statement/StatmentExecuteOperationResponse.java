package com.barclays.bmg.operation.response.statement;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CustomerAccountDTO;

public class StatmentExecuteOperationResponse extends ResponseContext {
    /**
	 *
	 */
    private static final long serialVersionUID = -9115345070035484766L;

    private CustomerAccountDTO actNo;
    /*
     * private String brnCde; private String brnNam;
     */
    private String fromDate; // start Date
    private String toDate; // End Date
    private String refNo;

    /**
     * @return the refNo
     */
    public String getRefNo() {
	return refNo;
    }

    /**
     * @param refNo
     *            the refNo to set
     */
    public void setRefNo(String refNo) {
	this.refNo = refNo;
    }

    /**
     * @return the actNo
     */
    public CustomerAccountDTO getActNo() {
	return actNo;
    }

    /**
     * @param actNo
     *            the actNo to set
     */
    public void setActNo(CustomerAccountDTO actNo) {
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
