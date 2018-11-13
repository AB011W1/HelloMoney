package com.barclays.bmg.json.model.fundtransfer;

import java.io.Serializable;

import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.TransferFacadeDTO;

public class PayeeJSONModel implements Serializable {

    /**
	 *
	 */
    private static final long serialVersionUID = 1L;

    // This is the label that will get displayed on the screen
    private String disLbl;

    private String payId;

    private String actNo;

    private String txnTyp;

    private String areaCode;

    private String billerId;

    // The DTO provides the beneficiary information
    // private TransferFacadeDTO transferFacadeDTO;

    public String getAreaCode() {
	return areaCode;
    }

    public void setAreaCode(String areaCode) {
	this.areaCode = areaCode;
    }

    public String getDisLbl() {
	return disLbl;
    }

    public void setDisLbl(String disLbl) {
	this.disLbl = disLbl;
    }

    public String getPayId() {
	return payId;
    }

    public void setPayId(String payId) {
	this.payId = payId;
    }

    public String getActNo() {
	return actNo;
    }

    public void setActNo(String actNo) {
	this.actNo = actNo;
    }

    public String getTxnTyp() {
	return txnTyp;
    }

    public void setTxnTyp(String txnTyp) {
	this.txnTyp = txnTyp;
    }

    public void setTransferFacadeDTO(TransferFacadeDTO transferFacadeDTO) {
	txnTyp = transferFacadeDTO.getTransactionFacadeRoutineType();
	BeneficiaryDTO beneficiaryDTO = transferFacadeDTO.getBeneficiary();

	if (beneficiaryDTO != null) {
	    payId = beneficiaryDTO.getPayeeId();
	} else {
	    CustomerAccountDTO customerAccountDTO = transferFacadeDTO.getToAccount();
	    if (customerAccountDTO != null) {
		actNo = customerAccountDTO.getAccountNumber();
	    }

	}

    }

	public String getBillerId() {
		return billerId;
	}

	public void setBillerId(String billerId) {
		this.billerId = billerId;
	}

}
