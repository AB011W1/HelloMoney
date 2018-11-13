package com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BillerDTO
{
	@JsonProperty
	private String billerId;

	@JsonProperty
	private String billerName;

	@JsonProperty
	private String billerCategoryId;

	@JsonProperty
	private String billerCategoryName;

	public String getBillerId() {
		return billerId;
	}

	public void setBillerId(String billerId) {
		this.billerId = billerId;
	}

	public String getBillerName() {
		return billerName;
	}

	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}

	public String getBillerCategoryId() {
		return billerCategoryId;
	}

	public void setBillerCategoryId(String billerCategoryId) {
		this.billerCategoryId = billerCategoryId;
	}

	public String getBillerCategoryName() {
		return billerCategoryName;
	}

	public void setBillerCategoryName(String billerCategoryName) {
		this.billerCategoryName = billerCategoryName;
	}
}
