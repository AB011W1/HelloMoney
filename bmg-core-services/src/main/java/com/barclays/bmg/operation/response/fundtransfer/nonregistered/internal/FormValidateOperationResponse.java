package com.barclays.bmg.operation.response.fundtransfer.nonregistered.internal;

import java.math.BigDecimal;
import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.Charge;
import com.barclays.bmg.dto.FxRateDTO;

public class FormValidateOperationResponse extends ResponseContext {

    private static final long serialVersionUID = -3194925764328885124L;

    private FxRateDTO fxRateDTO;
    private List<Charge> charges;
    private Amount tranFee;
    private Amount txnAmt;
    private boolean scndLvlauthReq;
    private String scndLvlAuthTyp;
    private BigDecimal txnAmtInLCY;
    private String txnDt;
    private String txnNot;

    private String txnRefNo;

    // OtherBarclays FundTransfer changes for CPB 12/05/2017
    private Double chargeAmount;
	private String feeGLAccount;
	private String chargeNarration;
	private Double taxAmount;
	private String taxGLAccount;
	private String ExciseDutyNarration;
	private String typeCode;
	private String value;

    public String getTxnRefNo() {
	return txnRefNo;
    }

    public void setTxnRefNo(String txnRefNo) {
	this.txnRefNo = txnRefNo;
    }

    private BeneficiaryDTO beneficiaryDTO;
    private CASAAccountDTO casaAccountDTO;

    public BeneficiaryDTO getBeneficiaryDTO() {
	return beneficiaryDTO;
    }

    public void setBeneficiaryDTO(BeneficiaryDTO beneficiaryDTO) {
	this.beneficiaryDTO = beneficiaryDTO;
    }

    public CASAAccountDTO getCasaAccountDTO() {
	return casaAccountDTO;
    }

    public void setCasaAccountDTO(CASAAccountDTO casaAccountDTO) {
	this.casaAccountDTO = casaAccountDTO;
    }

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



}
