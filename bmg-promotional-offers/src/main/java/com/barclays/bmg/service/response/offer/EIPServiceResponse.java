package com.barclays.bmg.service.response.offer;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.offer.EIPOfferDTO;
import com.barclays.bmg.dto.offer.OfferCategoryDTO;

public class EIPServiceResponse extends ResponseContext {

	private static final long serialVersionUID = 1139224284975514761L;

	private List<EIPOfferDTO> eipOfferList;
	private List<OfferCategoryDTO> offerCategoryList;

	public List<EIPOfferDTO> getEipOfferList() {
		return eipOfferList;
	}

	public void setEipOfferList(List<EIPOfferDTO> eipOfferList) {
		this.eipOfferList = eipOfferList;
	}

	public List<OfferCategoryDTO> getOfferCategoryList() {
		return offerCategoryList;
	}

	public void setOfferCategoryList(List<OfferCategoryDTO> offerCategoryList) {
		this.offerCategoryList = offerCategoryList;
	}



}
