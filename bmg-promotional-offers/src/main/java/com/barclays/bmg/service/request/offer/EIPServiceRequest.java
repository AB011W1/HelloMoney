package com.barclays.bmg.service.request.offer;

import com.barclays.bmg.context.RequestContext;

public class EIPServiceRequest extends RequestContext {

    private String partner;
    private String city;
    private String category;
    private String offer;

    public String getPartner() {
	return partner;
    }

    public void setPartner(String partner) {
	this.partner = partner;
    }

    public String getCategory() {
	return category;
    }

    public void setCategory(String category) {
	this.category = category;
    }

    public String getOffer() {
	return offer;
    }

    public void setOffer(String offer) {
	this.offer = offer;
    }

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

}
