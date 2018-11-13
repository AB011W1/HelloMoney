package com.barclays.bmg.json.model.offer;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.dto.offer.DiningOfferDto;
import com.barclays.bmg.json.response.BMBPayloadData;

public class DiningOfferListJSONModel extends BMBPayloadData {

    /**
	 *
	 */
    private static final long serialVersionUID = 6033877886778215021L;

    // List<CityJSONBean> cityLst = new ArrayList<CityJSONBean>();

    List<DiningOfferJSONBean> offerLst = new ArrayList<DiningOfferJSONBean>();

    // List<String> cusinLst = new ArrayList<String>();

    public DiningOfferListJSONModel() {
	super();
    }

    public DiningOfferListJSONModel(List<DiningOfferDto> offerDtoLst) {
	super();

	/*
	 * if(cityLst!=null && cityLst.size()>0){ for(CityDTO city : cityLst){ this.cityLst.add(new CityJSONBean(city)); } }
	 */
	if (offerDtoLst != null && offerDtoLst.size() > 0) {
	    for (DiningOfferDto diningOffer : offerDtoLst) {
		this.offerLst.add(new DiningOfferJSONBean(diningOffer));
	    }
	}

    }

    /*
     * public List<CityJSONBean> getCityLst() { return cityLst; }
     * 
     * public void setCityLst(List<CityJSONBean> cityLst) { this.cityLst = cityLst; }
     */

    public List<DiningOfferJSONBean> getOfferLst() {
	return offerLst;
    }

    public void setOfferLst(List<DiningOfferJSONBean> offerLst) {
	this.offerLst = offerLst;
    }

    /*
     * public List<String> getCusinLst() { return cusinLst; }
     * 
     * public void setCusinLst(List<String> cusinLst) { this.cusinLst = cusinLst; }
     */

}
