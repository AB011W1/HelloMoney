package com.barclays.bmg.json.model.offer;

import java.io.Serializable;

import com.barclays.bmg.dto.offer.AddressDTO;

public class AddressJSONBean implements Serializable {

	private static final long serialVersionUID = 6020967097394159352L;

	private String addr;
	private String phNo;
	private String cityNam;
	private String lati;
	private String longi;

	public AddressJSONBean() {
		super();
	}

	public AddressJSONBean(AddressDTO adr) {
		super();
		this.addr = adr.getLocation();
		this.phNo = adr.getPhoneNo();
		//this.cityId = adr.get;
		this.cityNam = adr.getCityName();
		this.lati = adr.getLatitude();
		this.longi = adr.getLongitude();
	}

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
