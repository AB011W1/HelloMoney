package com.barclays.bmg.json.model.offer;

import com.barclays.bmg.dto.offer.EIPOfferDTO;
import com.barclays.bmg.json.response.BMBPayloadData;


public class EIPOfferDetailJSONModel extends BMBPayloadData{

	private static final long serialVersionUID = -645909689497031673L;

	private EIPOfferJSONBean eipOfrDet;

	public EIPOfferDetailJSONModel() {
		super();
	}

	public EIPOfferDetailJSONModel(EIPOfferDTO eipOfrDto) {
		super();
		this.eipOfrDet = new EIPOfferJSONBean(eipOfrDto);
	}

	public EIPOfferJSONBean getEipOfrDet() {
		return eipOfrDet;
	}

	public void setEipOfrDet(EIPOfferJSONBean eipOfrDet) {
		this.eipOfrDet = eipOfrDet;
	}

}
