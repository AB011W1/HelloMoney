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
public class BarcaysDiningOffersLstData {

    @JsonProperty
    private List<DiningRestarantLst> offerLst;

    public List<DiningRestarantLst> getOfferLst() {
	return offerLst;
    }

    public void setOfferLst(List<DiningRestarantLst> offerLst) {
	this.offerLst = offerLst;
    }

}
