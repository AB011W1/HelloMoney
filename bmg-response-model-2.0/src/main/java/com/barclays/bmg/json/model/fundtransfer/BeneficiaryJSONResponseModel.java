package com.barclays.bmg.json.model.fundtransfer;

import com.barclays.bmg.json.model.billpayment.AddBeneficiaryJSONModel;
import com.barclays.bmg.json.response.BMBPayloadData;

/**
 * @author BTCI
 *
 */
public class BeneficiaryJSONResponseModel extends BMBPayloadData {


	private static final long serialVersionUID = -2074444170341004576L;
	private AddBeneficiaryJSONModel beneficiaryInfo;
	private String txnRefNo;
	private String txnDate;

	/**
	 * @return AddBeneficiaryJSONModel
	 */
	public AddBeneficiaryJSONModel getBeneficiaryInfo() {
		return beneficiaryInfo;
	}

	/**
	 * @param beneficiaryInfo
	 */
	public void setBeneficiaryInfo(AddBeneficiaryJSONModel beneficiaryInfo) {
		this.beneficiaryInfo = beneficiaryInfo;
	}

	public String getTxnRefNo() {
		return txnRefNo;
	}

	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}

	/**
	 * @return String
	 */
	public String getTxnDate() {
		return txnDate;
	}

	/**
	 * @param txnDate
	 */
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

}
