package com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PayHdr
{
	@JsonProperty
	private String resId;

	@JsonProperty
	private String resCde;

	@JsonProperty
	private String resMsg;

	@JsonProperty
	private String serVer;

	public String getResId() {
		return resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
	}
	public String getResCde() {
		return resCde;
	}
	public void setResCde(String resCde) {
		this.resCde = resCde;
	}
	public String getResMsg() {
		return resMsg;
	}
	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}
	public String getSerVer() {
		return serVer;
	}
	public void setSerVer(String serVer) {
		this.serVer = serVer;
	}

}
