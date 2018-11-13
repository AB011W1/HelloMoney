package com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MobileWalletFrmAcntLstData {
	/**
	 * fromAcctList
	 */
	@JsonProperty
	private List<MobileWalletAcntDetails> fromAcctList;

	/**
	 * @return the fromAcctList
	 */
	public List<MobileWalletAcntDetails> getFromAcctList() {
		return fromAcctList;
	}

	/**
	 * @param fromAcctList
	 *            the fromAcctList to set
	 */
	public void setFromAcctList(List<MobileWalletAcntDetails> fromAcctList) {
		this.fromAcctList = fromAcctList;
	}

}
