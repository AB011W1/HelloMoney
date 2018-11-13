package com.barclays.bmg.ibatis.dto.offer;

import java.util.Date;

public class PormoEIPOfferDto {
	/*
	 * SELECT PO.PROMO_OFFER_ID, PO.OFFER_DESC,
		OT.OFFER_TYPE_DESC,OCAT.OFFER_CATEGORY_DESC,
		PO.OFFER_START_DATE,PO.OFFER_END_DATE,
		PO.OFFER_DISCOUNT_PERCENTAGE, OL.CITY, OL.ADDRESS,
		OL.PHONE_NO, OL.LATITUDE, OL.LONGITUDE
		FROM OFFER_PROMO_OFFERS PO, OFFER_LOCATION OL, OFFER_PROMO_LOCATION_MAPPING	POL,
		OFFER_TYPE OT, OFFER_CATEGORY OCAT
		WHERE PO.PROMO_OFFER_ID=POL.PROMO_OFFER_ID
		AND POL.LOCATION_ID=OL.LOCATION_ID
		AND PO.OFFER_TYPE_ID=OT.OFFER_TYPE_ID
		AND PO.OFFER_CATEGORY_ID=OCAT.OFFER_CATEGORY_ID
		AND PO.OFFER_TYPE_ID=2
		AND PO.OFFER_SYSTEM_ID=#systemId#
		AND PO.OFFER_BUSINESS_ID=#businessId#
		AND SYSDATE&lt;=PO.OFFER_END_DATE
	 */
	private String offerId;
	private String offerDesc;
	private String offerTypeDesc;
	private String categoryDesc;
	private String discountPercent;
	private Date offerEndDate;
	private String locationId;
	private String cityName;
	private String cityAddress;
	private String phoneNo;
	private String latitude;
	private String longitude;
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
	public String getCategoryDesc() {
		return categoryDesc;
	}
	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
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
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
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


}
