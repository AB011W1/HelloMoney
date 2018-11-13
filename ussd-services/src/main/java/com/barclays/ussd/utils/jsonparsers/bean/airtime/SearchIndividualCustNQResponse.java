/**
 *
 */
package com.barclays.ussd.utils.jsonparsers.bean.airtime;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;
import com.barclays.ussd.utils.jsonparsers.bean.login.SearchIndividualCustNQPayData;

/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchIndividualCustNQResponse {

    @JsonProperty
    private PayHdr payHdr;

    @JsonProperty
    private SearchIndividualCustNQPayData payData;

    /**
     * @param payHdr
     *            the payHdr to set
     */
    public void setPayHdr(PayHdr payHdr) {
	this.payHdr = payHdr;
    }

    /**
     * @return the payHdr
     */
    public PayHdr getPayHdr() {
	return payHdr;
    }

	public SearchIndividualCustNQPayData getPayData() {
		return payData;
	}

	public void setPayData(SearchIndividualCustNQPayData payData) {
		this.payData = payData;
	}



}
