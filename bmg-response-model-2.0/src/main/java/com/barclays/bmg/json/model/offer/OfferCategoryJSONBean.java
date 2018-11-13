package com.barclays.bmg.json.model.offer;

import java.io.Serializable;

import com.barclays.bmg.dto.offer.OfferCategoryDTO;


public class OfferCategoryJSONBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -241354340436451860L;
	/**
	 *
	 */


	private String catId;
	private String catNam;

	public OfferCategoryJSONBean() {
		super();
	}

	public OfferCategoryJSONBean(OfferCategoryDTO offer) {
		super();
		this.catId = offer.getCategoryId();
		this.catNam = offer.getCatgoryName();
	}

	public String getCatId() {
		return catId;
	}
	public void setCatId(String catId) {
		this.catId = catId;
	}
	public String getCatNam() {
		return catNam;
	}
	public void setCatNam(String catNam) {
		this.catNam = catNam;
	}



}
