package com.barclays.bmg.json.model.offer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.dto.offer.AddressDTO;
import com.barclays.bmg.dto.offer.DiningOfferDto;
import com.barclays.bmg.utils.BMGFormatUtility;

public class DiningOfferJSONBean implements Serializable {

    /**
	 *
	 */
    private static final long serialVersionUID = 7412582930544502134L;

    private String offerId;
    private String nam;
    // private String cusin;
    // private String loc;
    private String disct;
    private String lstDt;
    private String desc;

    private List<AddressJSONBean> loc = new ArrayList<AddressJSONBean>();

    public DiningOfferJSONBean() {
	super();
    }

    public DiningOfferJSONBean(DiningOfferDto diningOffer) {
	super();
	this.offerId = diningOffer.getOfferId();
	this.nam = diningOffer.getName();
	// this.cusin = diningOffer.getCusine();
	// this.loc = diningOffer.getLocation();
	this.disct = diningOffer.getDiscount();
	// this.cityId = diningOffer.getCityId();
	// this.cityNam = diningOffer.getCityName();
	this.lstDt = BMGFormatUtility.getShortDate(diningOffer.getOfferEndDate());
	this.desc = diningOffer.getDescription();

	List<AddressDTO> addrDTOList = diningOffer.getAddressList();
	if (addrDTOList != null && !addrDTOList.isEmpty()) {
	    for (AddressDTO addrDTO : addrDTOList) {
		loc.add(new AddressJSONBean(addrDTO));
	    }
	}
    }

    public String getOfferId() {
	return offerId;
    }

    public void setOfferId(String offerId) {
	this.offerId = offerId;
    }

    public String getNam() {
	return nam;
    }

    public void setNam(String nam) {
	this.nam = nam;
    }

    /*
     * public String getCusin() { return cusin; }
     * 
     * public void setCusin(String cusin) { this.cusin = cusin; }
     */

    public List<AddressJSONBean> getLoc() {
	return loc;
    }

    public void setLoc(List<AddressJSONBean> loc) {
	this.loc = loc;
    }

    public String getDisct() {
	return disct;
    }

    public void setDisct(String disct) {
	this.disct = disct;
    }

    public String getLstDt() {
	return lstDt;
    }

    public void setLstDt(String lstDt) {
	this.lstDt = lstDt;
    }

    public String getDesc() {
	return desc;
    }

    public void setDesc(String desc) {
	this.desc = desc;
    }

}
