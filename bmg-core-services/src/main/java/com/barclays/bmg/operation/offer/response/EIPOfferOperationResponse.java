package com.barclays.bmg.operation.offer.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.offer.EIPOfferDTO;
import com.barclays.bmg.dto.offer.OfferCategoryDTO;

public class EIPOfferOperationResponse extends ResponseContext {

    private List<EIPOfferDTO> eipOfferList;
    private List<OfferCategoryDTO> categoryList;

    public List<EIPOfferDTO> getEipOfferList() {
	return eipOfferList;
    }

    public void setEipOfferList(List<EIPOfferDTO> eipOfferList) {
	this.eipOfferList = eipOfferList;
    }

    public List<OfferCategoryDTO> getCategoryList() {
	return categoryList;
    }

    public void setCategoryList(List<OfferCategoryDTO> categoryList) {
	this.categoryList = categoryList;
    }

}
