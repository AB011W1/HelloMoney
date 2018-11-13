package com.barclays.ussd.utils.jsonparsers.bean.login;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerProfile {

	@JsonProperty
	private String prefLang;

	@JsonProperty
	private String usrSta;

	@JsonProperty
	private String pinSta;

	public String getPrefLang() {
		return prefLang;
	}

	public void setPrefLang(String prefLang) {
		this.prefLang = prefLang;
	}

	public String getUsrSta() {
		return usrSta;
	}

	public void setUsrSta(String usrSta) {
		this.usrSta = usrSta;
	}

	public String getPinSta() {
		return pinSta;
	}

	public void setPinSta(String pinSta) {
		this.pinSta = pinSta;
	}

}
