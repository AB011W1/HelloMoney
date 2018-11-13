package com.barclays.bmg.json.model.offer;

import java.io.Serializable;

import com.barclays.bmg.dto.offer.CityDTO;


public class CityJSONBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -4147345732439160797L;

	private String cityId;
	private String cityNam;

	public CityJSONBean() {
		super();
	}

	public CityJSONBean(CityDTO city) {
		super();
		this.cityId = city.getCityId();
		this.cityNam = city.getCityName();
	}

	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCityNam() {
		return cityNam;
	}
	public void setCityNam(String cityNam) {
		this.cityNam = cityNam;
	}



}
