package com.barclays.bmg.service.request;

import java.math.BigDecimal;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.Charge;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.FxRateDTO;

public class DomesticFundTransferServiceRequest extends RequestContext {

    private CustomerAccountDTO sourceAcct;
    private BeneficiaryDTO beneficiaryDTO;
    private FxRateDTO fxRateDTO;
    private Amount txnAmt;
    private String txnNot;
    private String txnTyp;
    private BigDecimal tranFee;

    // MakeDomesticFundTransfer for Other barclays change - CPB 30/05
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

    public String getTxnNot() {
	return txnNot;
    }

    public void setTxnNot(String txnNot) {
	this.txnNot = txnNot;
    }

    public String getTxnTyp() {
	return txnTyp;
    }

    public void setTxnTyp(String txnTyp) {
	this.txnTyp = txnTyp;
    }

    public BigDecimal getTranFee() {
	return tranFee;
    }

    public void setTranFee(BigDecimal tranFee) {
	this.tranFee = tranFee;
    }

	public Charge getChargeDTO() {
		return chargeDTO;
	}

	public void setChargeDTO(Charge chargeDTO) {
		this.chargeDTO = chargeDTO;
	}

}
