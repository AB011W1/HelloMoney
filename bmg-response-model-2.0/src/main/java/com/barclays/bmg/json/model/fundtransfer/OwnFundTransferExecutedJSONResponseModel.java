package com.barclays.bmg.json.model.fundtransfer;

import com.barclays.bmg.json.response.BMBPayloadData;

public class OwnFundTransferExecutedJSONResponseModel extends BMBPayloadData {

	private static final long serialVersionUID = 4699808301606328452L;
	private String refNo = "";
	private String txnDt;
	private String txnMsg;

	public OwnFundTransferExecutedJSONResponseModel() {
		super();
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getTxnDt() {
		return txnDt;
	}

	public void setTxnDt(String txnDt) {
		this.txnDt = txnDt;
	}

	public String getTxnMsg() {
		return txnMsg;
	}

	public void setTxnMsg(String txnMsg) {
		this.txnMsg = txnMsg;
	}

}
