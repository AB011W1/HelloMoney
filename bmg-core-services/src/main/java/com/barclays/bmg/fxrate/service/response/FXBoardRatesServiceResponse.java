package com.barclays.bmg.fxrate.service.response;

import java.math.BigDecimal;

import com.barclays.bmg.context.ResponseContext;

public class FXBoardRatesServiceResponse extends ResponseContext{

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
