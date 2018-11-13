package com.barclays.bmg.operation.response.statement;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CASAAccountDTO;

public class StatmentValidateOperationResponse extends ResponseContext {
    /**
	 *
	 */
    private static final long serialVersionUID = -9115345070035484766L;

    private CASAAccountDTO actNo;
    // private BranchLookUpDTO branchLookUpDTO;
    // /private String brnCde;
    // private String brnNam;*/
    private String fromDate; // start Date
    private String toDate; // End Date
    private String txnRefNo;

    /**
     * @return the actNo
     */
    public CASAAccountDTO getActNo() {
	return actNo;
    }

    /**
     * @param actNo
     *            the actNo to set
     */
    public void setActNo(CASAAccountDTO actNo) {
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

    /**
     * @return the txnRefNo
     */
    public String getTxnRefNo() {
	return txnRefNo;
    }

    /**
     * @param txnRefNo
     *            the txnRefNo to set
     */
    public void setTxnRefNo(String txnRefNo) {
	this.txnRefNo = txnRefNo;
    }

}
