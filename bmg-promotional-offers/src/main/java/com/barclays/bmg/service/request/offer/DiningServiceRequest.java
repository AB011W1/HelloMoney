package com.barclays.bmg.service.request.offer;

import com.barclays.bmg.context.RequestContext;

public class DiningServiceRequest extends RequestContext {
    private String resturantName;
    private String city;

    public String getResturantName() {
	return resturantName;
    }

    public void setResturantName(String resturantName) {
	this.resturantName = resturantName;
    }

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

}
