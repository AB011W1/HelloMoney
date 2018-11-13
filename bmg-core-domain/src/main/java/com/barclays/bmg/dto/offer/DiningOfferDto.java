package com.barclays.bmg.dto.offer;

import java.util.Date;
import java.util.List;

public class DiningOfferDto {

    private String offerId;
    private String name;
    private String cusine;
    private String discount;
    private String description;
    private Date offerEndDate;
    private String offerTnc;
    private List<AddressDTO> addressList;

    public String getOfferId() {
	return offerId;
    }

    public void setOfferId(String offerId) {
	this.offerId = offerId;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getCusine() {
	return cusine;
    }

    public void setCusine(String cusine) {
	this.cusine = cusine;
    }

    public String getDiscount() {
	return discount;
    }

    public void setDiscount(String discount) {
	this.discount = discount;
    }

    public Date getOfferEndDate() {
	return offerEndDate;
    }

    public void setOfferEndDate(Date offerEndDate) {
	this.offerEndDate = offerEndDate;
    }

    public List<AddressDTO> getAddressList() {
	return addressList;
    }

    public void setAddressList(List<AddressDTO> addressList) {
	this.addressList = addressList;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getOfferTnc() {
	return offerTnc;
    }

    public void setOfferTnc(String offerTnc) {
	this.offerTnc = offerTnc;
    }

}
