package com.barclays.bmg.dto.offer;

import java.util.Date;
import java.util.List;

public class EIPOfferDTO {
/*
	  {
	      "offerId": "11",
	      "prtNam": "E Max",
	      "desc": "Plan avaliable for minimum purchase AED 500",
	      "loc": "Dubai",
	      "catId": "cate001",
	      "catNam": "Electronics",
	      "eipOffer": "0% for 3     months or 6 months"
	    },*/

	private String offerId;
	private String partnerName;
	private String description;
	private String eipOffer;
	private String categoryId;
	private String categoryName;
	private List<AddressDTO> addressList;
	private Date offerEndDate;
	private String offerTnc;
	public String getOfferId() {
		return offerId;
	}
	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEipOffer() {
		return eipOffer;
	}
	public void setEipOffer(String eipOffer) {
		this.eipOffer = eipOffer;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public List<AddressDTO> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<AddressDTO> addressList) {
		this.addressList = addressList;
	}
	public Date getOfferEndDate() {
		return offerEndDate;
	}
	public void setOfferEndDate(Date offerEndDate) {
		this.offerEndDate = offerEndDate;
	}
	public String getOfferTnc() {
		return offerTnc;
	}
	public void setOfferTnc(String offerTnc) {
		this.offerTnc = offerTnc;
	}




}
