package com.barclays.bmg.json.model.geocoding;

import java.io.Serializable;

import com.barclays.bmg.location.POIDetails;

public class GeoLocationJsonBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String lati;
	private String longi;

	public GeoLocationJsonBean(POIDetails poiDetls) {
		super();
		this.id = poiDetls.getId();
		this.lati = Double.toString(poiDetls.getLatitude());
		this.longi = Double.toString(poiDetls.getLongitude());

	}

	public GeoLocationJsonBean() {
		super();
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
