package com.barclays.bmg.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class FxBoardRatesDTO implements Serializable {

    /**
	 *
	 */
    private static final long serialVersionUID = 7827284401360991622L;
    private BigDecimal buyRate;
    private BigDecimal sellRate;
	public BigDecimal getBuyRate() {
		return buyRate;
	}
	public void setBuyRate(BigDecimal buyRate) {
		this.buyRate = buyRate;
	}
	public BigDecimal getSellRate() {
		return sellRate;
	}
	public void setSellRate(BigDecimal sellRate) {
		this.sellRate = sellRate;
	}


}
