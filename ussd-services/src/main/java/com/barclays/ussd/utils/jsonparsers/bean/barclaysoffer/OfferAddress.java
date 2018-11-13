package com.barclays.ussd.utils.jsonparsers.bean.barclaysoffer;

import org.codehaus.jackson.annotate.JsonProperty;

public class OfferAddress {
    @JsonProperty
    private String addr;

    @JsonProperty
    private String phNo;

    @JsonProperty
    private String cityNam;

    @JsonProperty
    private String lati;

    @JsonProperty
    private String longi;

    public String getAddr() {
	return addr;
    }

    public void setAddr(String addr) {
	this.addr = addr;
    }

    public String getPhNo() {
	return phNo;
    }

    public void setPhNo(String phNo) {
	this.phNo = phNo;
    }

    public String getCityNam() {
	return cityNam;
    }

    public void setCityNam(String cityNam) {
	this.cityNam = cityNam;
    }

    public String getLati() {
	return lati;
    }

    public void setLati(String lati) {
	this.lati = lati;
    }

    public String getLongi() {
	return longi;
    }

    public void setLongi(String longi) {
	this.longi = longi;
    }

}
