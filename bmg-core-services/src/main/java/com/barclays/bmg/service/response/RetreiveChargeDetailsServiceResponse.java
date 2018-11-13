package com.barclays.bmg.service.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.Charge;

public class RetreiveChargeDetailsServiceResponse extends ResponseContext {

    private static final long serialVersionUID = 8489938851682121891L;

    private Amount totalFeeAmount;
    private List<Charge> charges;
    private String TotalFeeAmountCurrencyCode;

    // CPB change 09/05/2017
//    private Double chargeAmount;
	private String feeGLAccount;
	private String chargeNarration;
	private Double taxAmount;
	private String taxGLAccount;
	private String ExciseDutyNarration;
	private String typeCode;
	private String value;
	private String xelerateOfflineError;
	private String xelerateOfflineDesc;

    public Amount getTotalFeeAmount() {
	return totalFeeAmount;
    }

    public void setTotalFeeAmount(Amount totalFeeAmount) {
	this.totalFeeAmount = totalFeeAmount;
    }

    public List<Charge> getCharges() {
	return charges;
    }

    public void setCharges(List<Charge> charges) {
	this.charges = charges;
    }

    public String getTotalFeeAmountCurrencyCode() {
	return TotalFeeAmountCurrencyCode;
    }

    public void setTotalFeeAmountCurrencyCode(String totalFeeAmountCurrencyCode) {
	TotalFeeAmountCurrencyCode = totalFeeAmountCurrencyCode;
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
