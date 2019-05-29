package com.barclays.bmg.operation.response.billpayment;

import java.util.Date;
import java.util.List;

import com.barclays.bmg.context.ResponseContext;

public class ManageFundtransferStatusOperationResponse extends ResponseContext {
	private String serviceResponseCode;
	private List <String> errorList;
	private String errorCode;
	private String errorDesc;
	private String txnRefNo;
	private Date txnDt;
	private String[] mobileNos;

	public String[] getMobileNos() {
		return mobileNos;
	}
	public void setMobileNos(String[] mobileNos) {
		this.mobileNos = mobileNos;
	}
	public String getTxnRefNo() {
		return txnRefNo;
	}
	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}
	public Date getTxnDt() {
		return txnDt;
	}
	public void setTxnDt(Date txnDt) {
		this.txnDt = txnDt;
	}
	public String getServiceResponseCode() {
		return serviceResponseCode;
	}
	public void setServiceResponseCode(String serviceResponseCode) {
		this.serviceResponseCode = serviceResponseCode;
	}
	public List<String> getErrorList() {
		return errorList;
	}
	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
}
