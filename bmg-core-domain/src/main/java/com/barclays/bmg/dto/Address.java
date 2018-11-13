package com.barclays.bmg.dto;

public class Address {
    private String addLine1;
    private String addLine2;
    private Country country;
    private City city;
    private String zipCode;
    private State state;
    private String addrType;
    private String residingSince;

    public String getAddLine1() {
	return addLine1;
    }

    public void setAddLine1(String addLine1) {
	this.addLine1 = addLine1;
    }

    public String getAddLine2() {
	return addLine2;
    }

    public void setAddLine2(String addLine2) {
	this.addLine2 = addLine2;
    }

    public Country getCountry() {
	return country;
    }

    public void setCountry(Country country) {
	this.country = country;
    }

    public City getCity() {
	return city;
    }

    public void setCity(City city) {
	this.city = city;
    }

    public String getZipCode() {
	return zipCode;
    }

    public void setZipCode(String zipCode) {
	this.zipCode = zipCode;
    }

    public State getState() {
	return state;
    }

    public void setState(State state) {
	this.state = state;
    }

    public String getAddrType() {
	return addrType;
    }

    public void setAddrType(String addrType) {
	this.addrType = addrType;
    }

    public String getResidingSince() {
	return residingSince;
    }

    public void setResidingSince(String residingSince) {
	this.residingSince = residingSince;
    }

    public static class Country {
	private String id;

	public String getId() {
	    return id;
	}

	public void setId(String id) {
	    this.id = id;
	}

	public Country(String id) {
	    super();
	    this.id = id;
	}

    }

    public static class City {
	private String value;

	public String getValue() {
	    return value;
	}

	public void setValue(String value) {
	    this.value = value;
	}

	public City(String value) {
	    super();
	    this.value = value;
	}

    }

    public static class State {
	private String value;

	public String getValue() {
	    return value;
	}

	public void setValue(String value) {
	    this.value = value;
	}

	public State(String value) {
	    super();
	    this.value = value;
	}

    }

}
