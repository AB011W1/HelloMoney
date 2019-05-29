package com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SrcAccount {
    /**
     * actHlds
     */
    @JsonProperty
    private String actHlds;

    /**
     * mkdActNo
     */
    @JsonProperty
    private String mkdActNo;

    /**
     * actNo
     */
    @JsonProperty
    private String actNo;

    @JsonProperty
    private String priInd;

    /**
     * @return the actHlds
     */

    @JsonProperty
    private String bankCif;

    @JsonProperty
	private String groupWalletIndicator;


    public String getBankCif() {
		return bankCif;
	}

	public void setBankCif(String bankCif) {
		this.bankCif = bankCif;
	}

	public String getGroupWalletIndicator() {
		return groupWalletIndicator;
	}

	public void setGroupWalletIndicator(String groupWalletIndicator) {
		this.groupWalletIndicator = groupWalletIndicator;
	}

    public String getActHlds() {
	return actHlds;
    }

    /**
     * @param actHlds
     *            the actHlds to set
     */
    public void setActHlds(String actHlds) {
	this.actHlds = actHlds;
    }

    /**
     * @return the mkdActNo
     */
    public String getMkdActNo() {
	return mkdActNo;
    }

    /**
     * @param mkdActNo
     *            the mkdActNo to set
     */
    public void setMkdActNo(String mkdActNo) {
	this.mkdActNo = mkdActNo;
    }

    /**
     * @return the actNo
     */
    public String getActNo() {
	return actNo;
    }

    /**
     * @param actNo
     *            the actNo to set
     */
    public void setActNo(String actNo) {
	this.actNo = actNo;
    }

    /**
     * @return the priInd
     */
    public String getPriInd() {
	return priInd;
    }

    /**
     * @param priInd
     *            the priInd to set
     */
    public void setPriInd(String priInd) {
	this.priInd = priInd;
    }
}
