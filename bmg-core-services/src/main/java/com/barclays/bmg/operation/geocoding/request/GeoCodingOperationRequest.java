package com.barclays.bmg.operation.geocoding.request;

import com.barclays.bmg.context.RequestContext;

public class GeoCodingOperationRequest extends RequestContext {

    private double latitude;
    private double longitude;
    double radiusInKM = -1;

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

    public double getRadiusInKM() {
	return radiusInKM;
    }

    public void setRadiusInKM(double radiusInKM) {
	this.radiusInKM = radiusInKM;
    }

}
