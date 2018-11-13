/**
 * FDAccntPayData.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.fdrates;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FDAccntPayData {
    /**
     * custActs
     */
    @JsonProperty
    private List<FDApplySourceAccount> srcLst;

    /**
     * @return the custActs
     */
    public List<FDApplySourceAccount> getSrcLst() {
	return this.srcLst;
    }

    /**
     * @param custActs
     *            the custActs to set
     */
    public void setSrcLst(List<FDApplySourceAccount> srcLst) {
	this.srcLst = srcLst;
    }

}
