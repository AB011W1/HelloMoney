/**
 * BillerDetailData.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.viewbillers;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillerDetailData {
    /**
     * pay
     */
    @JsonProperty
    private BillerDetails pay;

    /**
     * @param pay
     *            the pay to set
     */
    public void setPay(final BillerDetails pay) {
	this.pay = pay;
    }

    /**
     * @return the pay
     */
    public BillerDetails getPay() {
	return pay;
    }

}
