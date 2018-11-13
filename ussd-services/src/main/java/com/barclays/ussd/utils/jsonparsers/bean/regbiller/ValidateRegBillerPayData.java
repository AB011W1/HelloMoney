package com.barclays.ussd.utils.jsonparsers.bean.regbiller;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidateRegBillerPayData {
    @JsonProperty
    private String orgRefNo;

    @JsonProperty
    private String payeNickName;

    @JsonProperty
    private BillerDTO billerDTO;

    public String getOrgRefNo() {
	return orgRefNo;
    }

    public void setOrgRefNo(String orgRefNo) {
	this.orgRefNo = orgRefNo;
    }

    public BillerDTO getBillerDTO() {
	return billerDTO;
    }

    public void setBillerDTO(BillerDTO billerDTO) {
	this.billerDTO = billerDTO;
    }

    /**
     * @return the payeNickName
     */
    public String getPayeNickName() {
	return payeNickName;
    }

    /**
     * @param payeNickName
     *            the payeNickName to set
     */
    public void setPayeNickName(String payeNickName) {
	this.payeNickName = payeNickName;
    }

}
