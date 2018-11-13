/**
 * BillPayConfirmData.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.barclaysoffer;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BarcaysExtraInstallmentOffersLstData {

    @JsonProperty
    private List<InstallmentPatnerLst> eipOfferLst;

    public List<InstallmentPatnerLst> getEipOfferLst() {
	return eipOfferLst;
    }

    public void setEipOfferLst(List<InstallmentPatnerLst> eipOfferLst) {
	this.eipOfferLst = eipOfferLst;
    }

}
