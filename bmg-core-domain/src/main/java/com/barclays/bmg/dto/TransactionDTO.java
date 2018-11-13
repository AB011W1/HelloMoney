package com.barclays.bmg.dto;

import java.math.BigDecimal;

public class TransactionDTO extends BaseDomainDTO {

    /**
	 *
	 */
    private static final long serialVersionUID = 2599854651137760116L;
    private CustomerAccountDTO sourceAcct;
    private BeneficiaryDTO beneficiaryDTO;
    private FxRateDTO fxRateDTO;
    private Amount txnAmt;
    private boolean scndLvlauthReq;
    private String scndLvlAuthTyp;
    private String txnNot;
    private BigDecimal txnAmtInLCY;
    private BigDecimal minAmt;
    private BigDecimal maxAmt;
    private BigDecimal intAmt;
    private BigDecimal outBalAmt;
    private String txnType;
    private String mtpService;
    private String orgCode;
    private String action;

    // CPB change MakeBillPayment 10/05/2017
    private Charge chargeDTO;

    public CustomerAccountDTO getSourceAcct() {
	return sourceAcct;
    }

    public void setSourceAcct(CustomerAccountDTO sourceAcct) {
	this.sourceAcct = sourceAcct;
    }

    public BeneficiaryDTO getBeneficiaryDTO() {
	return beneficiaryDTO;
    }

    public void setBeneficiaryDTO(BeneficiaryDTO beneficiaryDTO) {
	this.beneficiaryDTO = beneficiaryDTO;
    }

    public FxRateDTO getFxRateDTO() {
	return fxRateDTO;
    }

    public void setFxRateDTO(FxRateDTO fxRateDTO) {
	this.fxRateDTO = fxRateDTO;
    }

    public Amount getTxnAmt() {
	return txnAmt;
    }

    public void setTxnAmt(Amount txnAmt) {
	this.txnAmt = txnAmt;
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

    public String getTxnNot() {
	return txnNot;
    }

    public void setTxnNot(String txnNot) {
	this.txnNot = txnNot;
    }

    public BigDecimal getTxnAmtInLCY() {
	return txnAmtInLCY;
    }

    public void setTxnAmtInLCY(BigDecimal txnAmtInLCY) {
	this.txnAmtInLCY = txnAmtInLCY;
    }

    public String getTxnType() {
	return txnType;
    }

    public void setTxnType(String txnType) {
	this.txnType = txnType;
    }

    public BigDecimal getMinAmt() {
	return minAmt;
    }

    public void setMinAmt(BigDecimal minAmt) {
	this.minAmt = minAmt;
    }

    public BigDecimal getMaxAmt() {
	return maxAmt;
    }

    public void setMaxAmt(BigDecimal maxAmt) {
	this.maxAmt = maxAmt;
    }

    public BigDecimal getIntAmt() {
	return intAmt;
    }

    public void setIntAmt(BigDecimal intAmt) {
	this.intAmt = intAmt;
    }

    public String getMtpService() {
	return mtpService;
    }

    public void setMtpService(String mtpService) {
	this.mtpService = mtpService;
    }

    public BigDecimal getOutBalAmt() {
	return outBalAmt;
    }

    public void setOutBalAmt(BigDecimal outBalAmt) {
	this.outBalAmt = outBalAmt;
    }

    public String getOrgCode() {
	return orgCode;
    }

    public void setOrgCode(String orgCode) {
	this.orgCode = orgCode;
    }

	public Charge getChargeDTO() {
		return chargeDTO;
	}

	public void setChargeDTO(Charge chargeDTO) {
		this.chargeDTO = chargeDTO;
	}


	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}


}
