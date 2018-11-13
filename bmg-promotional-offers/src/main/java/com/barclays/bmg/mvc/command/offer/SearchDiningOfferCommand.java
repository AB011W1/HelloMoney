package com.barclays.bmg.mvc.command.offer;

public class SearchDiningOfferCommand {

    private String restaurant;
    private String city;

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public String getRestaurant() {
	return restaurant;
    }

    public void setRestaurant(String restaurant) {
	this.restaurant = restaurant;
    }

}
