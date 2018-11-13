package com.barclays.bmg.operation.response.fundtransfer.external;

import java.math.BigDecimal;
import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.Charge;
import com.barclays.bmg.dto.FxRateDTO;

public class FormValidateOperationResponse extends ResponseContext {

    private static final long serialVersionUID = -2768434281999925012L;

    private FxRateDTO fxRateDTO;
    private List<Charge> charges;
    private Amount tranFee;
    private Amount txnAmt;
    private boolean scndLvlauthReq;
    private String scndLvlAuthTyp;
    private BigDecimal txnAmtInLCY;
    private String txnDt;
    private String txnNot;
    private String mobileNo = "";

    // MWallet changes for CPB 12/05/2017
    private Double chargeAmount;
	private String feeGLAccount;
	private String chargeNarration;
	private Double taxAmount;
	private String taxGLAccount;
	private String ExciseDutyNarration;
	private String typeCode;
	private String value;

	// Xelerate offline error codes
	private String xelerateOfflineError;
	private String xelerateOfflineDesc;

    public FxRateDTO getFxRateDTO() {
	return fxRateDTO;
    }

    public void setFxRateDTO(FxRateDTO fxRateDTO) {
	this.fxRateDTO = fxRateDTO;
    }

    public List<Charge> getCharges() {
	return charges;
    }

    public void setCharges(List<Charge> charges) {
	this.charges = charges;
    }

    public Amount getTranFee() {
	return tranFee;
    }

    public void setTranFee(Amount tranFee) {
	this.tranFee = tranFee;
    }

    public boolean isScndLvlauthReq() {
	return scndLvlauthReq;
    }

    public void setScndLvlauthReq(boolean scndLvlauthReq) {
	this.scndLvlauthReq = scndLvlauthReq;
    }

    public String getScndLvlAuthTyp() {
	return scndLvlAuthTyp;
    }

    public void setScndLvlAuthTyp(String scndLvlAuthTyp) {
	this.scndLvlAuthTyp = scndLvlAuthTyp;
    }

    public Amount getTxnAmt() {
	return txnAmt;
    }

    public void setTxnAmt(Amount txnAmt) {
	this.txnAmt = txnAmt;
    }

    public BigDecimal getTxnAmtInLCY() {
	return txnAmtInLCY;
    }

    public void setTxnAmtInLCY(BigDecimal txnAmtInLCY) {
	this.txnAmtInLCY = txnAmtInLCY;
    }

    public void setTxnDt(String txnDt) {
	this.txnDt = txnDt;
    }

    public String getTxnDt() {
	return txnDt;
    }

    public void setTxnNot(String txnNot) {
	this.txnNot = txnNot;
    }

    public String getTxnNot() {
	return txnNot;
    }

    public String getMobileNo() {
	return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
	this.mobileNo = mobileNo;
    }

	public Double getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(Double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public String getFeeGLAccount() {
		return feeGLAccount;
	}

	public void setFeeGLAccount(String feeGLAccount) {
		this.feeGLAccount = feeGLAccount;
	}

	public String getChargeNarration() {
		return chargeNarration;
	}

	public void setChargeNarration(String chargeNarration) {
		this.chargeNarration = chargeNarration;
	}

	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public String getTaxGLAccount() {
		return taxGLAccount;
	}

	public void setTaxGLAccount(String taxGLAccount) {
		this.taxGLAccount = taxGLAccount;
	}

	public String getExciseDutyNarration() {
		return ExciseDutyNarration;
	}

	public void setExciseDutyNarration(String exciseDutyNarration) {
		ExciseDutyNarration = exciseDutyNarration;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getXelerateOfflineError() {
		return xelerateOfflineError;
	}

	public void setXelerateOfflineError(String xelerateOfflineError) {
		this.xelerateOfflineError = xelerateOfflineError;
	}

	public String getXelerateOfflineDesc() {
		return xelerateOfflineDesc;
	}

	public void setXelerateOfflineDesc(String xelerateOfflineDesc) {
		this.xelerateOfflineDesc = xelerateOfflineDesc;
	}

}
