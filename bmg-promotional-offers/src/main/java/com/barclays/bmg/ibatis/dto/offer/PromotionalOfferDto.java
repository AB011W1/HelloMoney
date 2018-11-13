package com.barclays.bmg.ibatis.dto.offer;

import java.util.Date;

public class PromotionalOfferDto {

    /*
     * SELECT PO.PROMO_OFFER_ID, PO.OFFER_DESC, OT.OFFER_TYPE_DESC, PO.OFFER_START_DATE,PO.OFFER_END_DATE, PO.OFFER_RESTAURENT_NAME,PO.OFFER_CUISINE,
     * PO.OFFER_DISCOUNT_PERCENTAGE, UPPER(OL.CITY) CITY, OL.ADDRESS, OL.PHONE_NO, OL.LATITUDE, OL.LONGITUDE FROM PROMO_OFFERS PO, OFFER_LOCATION OL,
     * PROMO_OFFER_LOCATION POL, OFFER_TYPE OT WHERE PO.PROMO_OFFER_ID=POL.PROMO_OFFER_ID AND POL.LOCATION_ID=OL.LOCATION_ID AND
     * PO.OFFER_TYPE_ID=OT.OFFER_TYPE_ID AND PO.OFFER_TYPE_ID=1 AND PO.OFFER_SYSTEM_ID=#systemId# AND PO.OFFER_BUSINESS_ID=#businessId# AND
     * SYSDATE&lt;=PO.OFFER_END_DATE
     */
    private String offerId;
    private String offerDesc;
    private String offerTypeDesc;
    private String resturantName;
    private String cusine;
    private String discountPercent;
    private Date offerEndDate;
    private String locationId;
    private String cityName;
    private String cityAddress;
    private String phoneNo;
    private String latitude;
    private String longitude;
    private String partnerName;
    private String eipOffer;
    private String catgoryId;
    private String catgoryDesc;
    private String offerTnc;

    public String getOfferId() {
	return offerId;
    }

    public void setOfferId(String offerId) {
	this.offerId = offerId;
    }

    public String getOfferDesc() {
	return offerDesc;
    }

    public void setOfferDesc(String offerDesc) {
	this.offerDesc = offerDesc;
    }

    public String getOfferTypeDesc() {
	return offerTypeDesc;
    }

    public void setOfferTypeDesc(String offerTypeDesc) {
	this.offerTypeDesc = offerTypeDesc;
    }

    public String getResturantName() {
	return resturantName;
    }

    public void setResturantName(String resturantName) {
	this.resturantName = resturantName;
    }

    public String getCusine() {
	return cusine;
    }

    public void setCusine(String cusine) {
	this.cusine = cusine;
    }

    public String getDiscountPercent() {
	return discountPercent;
    }

    public void setDiscountPercent(String discountPercent) {
	this.discountPercent = discountPercent;
    }

    public Date getOfferEndDate() {
	return offerEndDate;
    }

    public void setOfferEndDate(Date offerEndDate) {
	this.offerEndDate = offerEndDate;
    }

    public String getCityName() {
	return cityName;
    }

    public void setCityName(String cityName) {
	this.cityName = cityName;
    }

    public String getCityAddress() {
	return cityAddress;
    }

    public void setCityAddress(String cityAddress) {
	this.cityAddress = cityAddress;
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

    public String getLocationId() {
	return locationId;
    }

    public void setLocationId(String locationId) {
	this.locationId = locationId;
    }

    public String getPartnerName() {
	return partnerName;
    }

    public void setPartnerName(String partnerName) {
	this.partnerName = partnerName;
    }

    public String getEipOffer() {
	return eipOffer;
    }

    public String getOfferTnc() {
	return offerTnc;
    }

    public void setOfferTnc(String offerTnc) {
	this.offerTnc = offerTnc;
    }

    public void setEipOffer(String eipOffer) {
	this.eipOffer = eipOffer;
    }

    public String getCatgoryId() {
	return catgoryId;
    }

    public void setCatgoryId(String catgoryId) {
	this.catgoryId = catgoryId;
    }

    public String getCatgoryDesc() {
	return catgoryDesc;
    }

    public void setCatgoryDesc(String catgoryDesc) {
	this.catgoryDesc = catgoryDesc;
    }

}
