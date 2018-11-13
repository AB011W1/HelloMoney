package com.barclays.ussd.utils.jsonparsers.bean.fxrate;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FxRatePayData {

    @JsonProperty
    private String buyRate;

    @JsonProperty
    private String sellRate;

	public String getBuyRate() {
		return buyRate;
	}

	public void setBuyRate(String buyRate) {
		this.buyRate = buyRate;
	}

	public String getSellRate() {
		return sellRate;
	}

	public void setSellRate(String sellRate) {
		this.sellRate = sellRate;
	}


}
