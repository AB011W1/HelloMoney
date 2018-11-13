package com.barclays.bmg.fxrate.service.response;

import java.math.BigDecimal;

public class FxRateServiceResponse {

    private BigDecimal effFxRate;
    private BigDecimal eqAmt;

    public BigDecimal getEffFxRate() {
	return effFxRate;
    }

    public void setEffFxRate(BigDecimal effFxRate) {
	this.effFxRate = effFxRate;
    }

    public BigDecimal getEqAmt() {
	return eqAmt;
    }

    public void setEqAmt(BigDecimal eqAmt) {
	this.eqAmt = eqAmt;
    }

}
