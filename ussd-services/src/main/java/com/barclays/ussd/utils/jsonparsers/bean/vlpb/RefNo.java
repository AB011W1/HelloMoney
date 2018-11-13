/**
 * AmountInfo.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.vlpb;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefNo {
    /**
     * refNo
     */
    @JsonProperty
    private String refNo;

    /**
     * @return the refNo
     */
    public String getRefNo() {
	return refNo;
    }

    /**
     * @param refNo
     *            the refNo to set
     */
    public void setRefNo(String refNo) {
	this.refNo = refNo;
    }

}
