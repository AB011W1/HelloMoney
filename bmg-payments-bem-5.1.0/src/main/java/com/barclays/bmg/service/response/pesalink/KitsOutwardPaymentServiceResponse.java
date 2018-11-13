package com.barclays.bmg.service.response.pesalink;

import java.util.Date;
import java.util.List;

import com.barclays.bmg.context.ResponseContext;


public class KitsOutwardPaymentServiceResponse extends ResponseContext {



	private static final long serialVersionUID = 6017509621703548165L;
	private String txnRefNo;
	private Date txnDate ;
	private Double accBalPostTxn;

	public String getTxnRefNo() {
		return txnRefNo;
	}
	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}
	public Date getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}
	public Double getAccBalPostTxn() {
		return accBalPostTxn;
	}
	public void setAccBalPostTxn(Double accBalPostTxn) {
		this.accBalPostTxn = accBalPostTxn;
	}


}


