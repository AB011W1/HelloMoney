package com.barclays.bmg.operation.offer.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.offer.DiningOfferDto;

public class DiningOfferOperationResponse extends ResponseContext {
    private List<DiningOfferDto> diningOfferList;

    public List<DiningOfferDto> getDiningOfferList() {
	return diningOfferList;
    }

    public void setDiningOfferList(List<DiningOfferDto> diningOfferList) {
	this.diningOfferList = diningOfferList;
    }

}
