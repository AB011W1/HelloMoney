package com.barclays.bmg.location;

public class Location {

    String id;

    /*
     * In degrees
     */
    double latitude;

    /*
     * In degrees
     */
    double longitude;

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public double getLatitude() {
	return latitude;
    }

    public void setLatitude(double latitude) {
	this.latitude = latitude;
    }

    public double getLongitude() {
	return longitude;
    }

    public void setLongitude(double longitude) {
	this.longitude = longitude;
    }

}
