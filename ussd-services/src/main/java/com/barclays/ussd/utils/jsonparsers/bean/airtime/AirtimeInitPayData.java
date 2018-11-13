/**
 *
 */
package com.barclays.ussd.utils.jsonparsers.bean.airtime;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 * 
 */
public class AirtimeInitPayData {

    @JsonProperty
    private List<Account> srcLst;

    @JsonProperty
    private List<Biller> prvderLst;

    /**
     * @return the srcLst
     */
    public List<Account> getSrcLst() {
	return srcLst;
    }

    /**
     * @param srcLst
     *            the srcLst to set
     */
    public void setSrcLst(List<Account> srcLst) {
	this.srcLst = srcLst;
    }

    /**
     * @return the prvderLst
     */
    public List<Biller> getPrvderLst() {
	return prvderLst;
    }

    /**
     * @param prvderLst
     *            the prvderLst to set
     */
    public void setPrvderLst(List<Biller> prvderLst) {
	this.prvderLst = prvderLst;
    }

}
