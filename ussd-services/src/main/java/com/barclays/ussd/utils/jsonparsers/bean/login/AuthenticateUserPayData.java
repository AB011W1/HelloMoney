package com.barclays.ussd.utils.jsonparsers.bean.login;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticateUserPayData {
	@JsonProperty
	private CustomerProfile custProf;

	@JsonProperty
	private List<CustomerMobileRegAcct> custActs;

	public List<CustomerMobileRegAcct> getCustActs() {
		return custActs;
	}

	public void setCustActs(List<CustomerMobileRegAcct> custActs) {
		this.custActs = custActs;
	}

	public CustomerProfile getCustProf() {
		return custProf;
	}

	public void setCustProf(CustomerProfile custProf) {
		this.custProf = custProf;
	}
}
