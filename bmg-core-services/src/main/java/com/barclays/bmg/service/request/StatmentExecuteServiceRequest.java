package com.barclays.bmg.service.request;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.CASAAccountDTO;

public class StatmentExecuteServiceRequest extends RequestContext {
    private CASAAccountDTO accountDTO;
    // private String actNo;
    private String fromDate;
    private String toDate;

    // private String brchCde;
    // private String brchNm;*/

    /**
     * @return the accountDTO
     */
    public CASAAccountDTO getAccountDTO() {
	return accountDTO;
    }

    /**
     * @param accountDTO
     *            the accountDTO to set
     */
    public void setAccountDTO(CASAAccountDTO accountDTO) {
	this.accountDTO = accountDTO;
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
