package com.barclays.ussd.utils.jsonparsers.bean.fdrates;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tenure {

    @JsonProperty
    private String tenType;

    @JsonProperty
    private String tenMonth;

    @JsonProperty
    private String tenDay;

    @JsonProperty
    private String intrate;

    public Tenure() {

    }

    public Tenure(String tenType, String tenMonth, String tenDay, String intrate) {
	this.tenType = tenType;
	this.tenMonth = tenMonth;
	this.tenDay = tenDay;
	this.intrate = intrate;
    }

    public String getTenType() {
	return tenType;
    }

    public void setTenType(String tenType) {
	this.tenType = tenType;
    }

    /**
     * @return the tenMonth
     */
    public String getTenMonth() {
	return tenMonth;
    }

    /**
     * @param tenMonth
     *            the tenMonth to set
     */
    public void setTenMonth(String tenMonth) {
	this.tenMonth = tenMonth;
    }

    /**
     * @return the tenDay
     */
    public String getTenDay() {
	return tenDay;
    }

    /**
     * @param tenDay
     *            the tenDay to set
     */
    public void setTenDay(String tenDay) {
	this.tenDay = tenDay;
    }

    /**
     * @return the intrate
     */
    public String getIntrate() {
	return intrate;
    }

    /**
     * @param intrate
     *            the intrate to set
     */
    public void setIntrate(String intrate) {
	this.intrate = intrate;
    }
}
