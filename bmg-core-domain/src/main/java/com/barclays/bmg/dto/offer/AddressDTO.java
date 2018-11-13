package com.barclays.bmg.dto.offer;

public class AddressDTO {

    private String locationId;
    private String cityId;
    private String location;
    private String cityName;
    private String phoneNo;
    private String latitude;
    private String longitude;

    public String getLocationId() {
	return locationId;
    }

    public void setLocationId(String locationId) {
	this.locationId = locationId;
    }

    public String getCityId() {
	return cityId;
    }

    public void setCityId(String cityId) {
	this.cityId = cityId;
    }

    public String getLocation() {
	return location;
    }

    public void setLocation(String location) {
	this.location = location;
    }

    public String getCityName() {
	return cityName;
    }

    public void setCityName(String cityName) {
	this.cityName = cityName;
    }

    public String getPhoneNo() {
	return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
	this.phoneNo = phoneNo;
    }

    public String getLatitude() {
	return latitude;
    }

    public void setLatitude(String latitude) {
	this.latitude = latitude;
    }

    public String getLongitude() {
	return longitude;
    }

    public void setLongitude(String longitude) {
	this.longitude = longitude;
    }

}
