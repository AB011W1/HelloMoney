package com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MobileWalletPayData {
	/**
	 * srcLst
	 */
	@JsonProperty
	private List<SrcAccount> srcLst;

	/**
	 * prvderLst
	 */
	@JsonProperty
	private List<MobileWalletProvider> prvderLst;

	/**
	 * @return the srcLst
	 */
	public List<SrcAccount> getSrcLst() {
		return srcLst;
	}

	/**
	 * @param srcLst
	 *            the srcLst to set
	 */
	public void setSrcLst(List<SrcAccount> srcLst) {
		this.srcLst = srcLst;
	}

	/**
	 * @return the prvderLst
	 */
	public List<MobileWalletProvider> getPrvderLst() {
		return prvderLst;
	}

	/**
	 * @param prvderLst
	 *            the prvderLst to set
	 */
	public void setPrvderLst(List<MobileWalletProvider> prvderLst) {
		this.prvderLst = prvderLst;
	}

}
