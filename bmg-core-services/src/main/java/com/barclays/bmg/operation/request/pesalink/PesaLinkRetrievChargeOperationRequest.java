package com.barclays.bmg.operation.request.pesalink;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.CustomerAccountDTO;

public class PesaLinkRetrievChargeOperationRequest extends RequestContext {

	private Amount txnAmt;
	private CustomerAccountDTO frmAct;
	private String txnType;
	private String toActNo;

	public Amount getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(Amount txnAmt) {
		this.txnAmt = txnAmt;
	}

	public CustomerAccountDTO getFrmAct() {
		return frmAct;
	}

	public void setFrmAct(CustomerAccountDTO frmAct) {
		this.frmAct = frmAct;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	/**
	 * @return the toActNo
	 */
	public String getToActNo() {
		return toActNo;
	}

	/**
	 * @param toActNo
	 *            the toActNo to set
	 */
	public void setToActNo(String toActNo) {
		this.toActNo = toActNo;
	}

}
