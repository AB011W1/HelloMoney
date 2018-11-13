package com.barclays.bmg.json.model.offer;

import com.barclays.bmg.dto.offer.DiningOfferDto;
import com.barclays.bmg.json.response.BMBPayloadData;

public class DiningOfferDetailsJSONModel extends BMBPayloadData {

	/**
	 *
	 */

	private static final long serialVersionUID = 1L;

	private DiningOfferJSONBean dineOfferDetl;

	public DiningOfferDetailsJSONModel(){
		super();
	}

	public DiningOfferDetailsJSONModel(DiningOfferDto diningOfferDto){
		super();
		this.dineOfferDetl = new DiningOfferJSONBean(diningOfferDto);
	}

	public DiningOfferJSONBean getDineOfferDetl() {
		return dineOfferDetl;
	}

	public void setDineOfferDetl(DiningOfferJSONBean dineOfferDetl) {
		this.dineOfferDetl = dineOfferDetl;
	}



}
