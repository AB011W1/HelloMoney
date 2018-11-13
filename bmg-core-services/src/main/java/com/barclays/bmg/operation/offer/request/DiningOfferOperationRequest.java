package com.barclays.bmg.operation.offer.request;

import com.barclays.bmg.context.RequestContext;

public class DiningOfferOperationRequest extends RequestContext {

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
