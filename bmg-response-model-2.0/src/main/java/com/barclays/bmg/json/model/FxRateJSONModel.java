package com.barclays.bmg.json.model;

import java.math.BigDecimal;

import com.barclays.bmg.json.response.BMBPayloadData;

public class FxRateJSONModel extends BMBPayloadData {

    private static final long serialVersionUID = 1L;
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
