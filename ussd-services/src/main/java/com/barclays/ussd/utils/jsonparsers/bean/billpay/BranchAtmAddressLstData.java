/**
 * BillPayConfirmData.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.billpay;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BranchAtmAddressLstData {

    @JsonProperty
    private List<BranchAtmAddressLst> brnLst;

    @JsonProperty
    private List<BranchAtmAddressLst> atmLst;

    public List<BranchAtmAddressLst> getBrnLst() {
	return brnLst;
    }

    public void setBrnLst(List<BranchAtmAddressLst> brnLst) {
	this.brnLst = brnLst;
    }

    public List<BranchAtmAddressLst> getAtmLst() {
	return atmLst;
    }

    public void setAtmLst(List<BranchAtmAddressLst> atmLst) {
	this.atmLst = atmLst;
    }

}
