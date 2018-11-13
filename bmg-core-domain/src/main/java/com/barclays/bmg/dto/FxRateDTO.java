package com.barclays.bmg.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class FxRateDTO implements Serializable {

    /**
	 *
	 */
    private static final long serialVersionUID = 7827284401360991622L;
    private BigDecimal effectiveFXRate;
    private BigDecimal equivalentAmount;

    public BigDecimal getEffectiveFXRate() {
	return effectiveFXRate;
    }

    public void setEffectiveFXRate(BigDecimal effectiveFXRate) {
	this.effectiveFXRate = effectiveFXRate;
    }

    public BigDecimal getEquivalentAmount() {
	return equivalentAmount;
    }

    public void setEquivalentAmount(BigDecimal equivalentAmount) {
	this.equivalentAmount = equivalentAmount;
    }
}
