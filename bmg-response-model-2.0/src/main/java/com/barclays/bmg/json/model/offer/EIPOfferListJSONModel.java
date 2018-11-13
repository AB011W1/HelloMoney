package com.barclays.bmg.json.model.offer;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.dto.offer.EIPOfferDTO;
import com.barclays.bmg.dto.offer.OfferCategoryDTO;
import com.barclays.bmg.json.response.BMBPayloadData;

public class EIPOfferListJSONModel extends BMBPayloadData {

    private static final long serialVersionUID = 8392559109418537247L;
    private List<OfferCategoryJSONBean> catLst = new ArrayList<OfferCategoryJSONBean>();
    private List<EIPOfferJSONBean> eipOfferLst = new ArrayList<EIPOfferJSONBean>();

    // protected String dispNoHistFnd = "No";

    public EIPOfferListJSONModel(List<OfferCategoryDTO> offers, List<EIPOfferDTO> eipOfrLst) {

	if (offers != null && offers.size() > 0) {
	    for (OfferCategoryDTO of : offers) {
		this.catLst.add(new OfferCategoryJSONBean(of));
	    }
	}
	if (eipOfrLst != null && eipOfrLst.size() > 0) {
	    for (EIPOfferDTO eipOfferDTO : eipOfrLst) {
		this.eipOfferLst.add(new EIPOfferJSONBean(eipOfferDTO));
	    }

	}
    }

    public List<OfferCategoryJSONBean> getCatLst() {
	return catLst;
    }

    public void setCatLst(List<OfferCategoryJSONBean> catLst) {
	this.catLst = catLst;
    }

    public List<EIPOfferJSONBean> getEipOfferLst() {
	return eipOfferLst;
    }

    public void setEipOfferLst(List<EIPOfferJSONBean> eipOfferLst) {
	this.eipOfferLst = eipOfferLst;
    }

}
