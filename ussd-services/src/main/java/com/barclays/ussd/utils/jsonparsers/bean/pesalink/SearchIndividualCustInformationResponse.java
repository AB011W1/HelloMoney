/**
 *
 */
package com.barclays.ussd.utils.jsonparsers.bean.pesalink;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;
import com.barclays.ussd.utils.jsonparsers.bean.login.RetrieveindividualCustCardListPayData;


/**
 * @author BTCI
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchIndividualCustInformationResponse {

    @JsonProperty
    private PayHdr payHdr;


    @JsonProperty
    private SearchIndividualCustInformationPayData payData;

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

	public SearchIndividualCustInformationPayData getPayData() {
		return payData;
	}

	public void setPayData(SearchIndividualCustInformationPayData payData) {
		this.payData = payData;
	}



}
