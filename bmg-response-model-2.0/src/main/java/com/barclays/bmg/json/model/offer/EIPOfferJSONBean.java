package com.barclays.bmg.json.model.offer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.dto.offer.AddressDTO;
import com.barclays.bmg.dto.offer.EIPOfferDTO;
//import com.barclays.bmg.jsonmodel.offer.AddressJSONBean;
import com.barclays.bmg.utils.BMGFormatUtility;

public class EIPOfferJSONBean implements Serializable{

	private static final long serialVersionUID = -645909689497031673L;

	private String offerId;
	private String prtNam;
	private String desc;
	//private String loc;
	private String eipOffer;
	private String catId;
	private String catNam;
	private String lstDt;
	private String tnc;

	private List<AddressJSONBean>  loc = new ArrayList<AddressJSONBean>();

	public EIPOfferJSONBean() {
		super();
	}

	public EIPOfferJSONBean(EIPOfferDTO eipOfferDTO) {
		super();
		this.offerId = eipOfferDTO.getOfferId();
		this.prtNam = eipOfferDTO.getPartnerName();
		this.desc = eipOfferDTO.getDescription();
//		this.loc = eipOfferDTO.getOfferLocation();
		this.eipOffer = eipOfferDTO.getEipOffer();
		this.catId = eipOfferDTO.getCategoryId();
		this.catNam = eipOfferDTO.getCategoryName();
		this.lstDt = BMGFormatUtility.getShortDate(eipOfferDTO.getOfferEndDate());
		this.tnc = eipOfferDTO.getOfferTnc();

		List<AddressDTO> addressList = eipOfferDTO.getAddressList();
		if(addressList!=null && !addressList.isEmpty()){
			for(AddressDTO adr : addressList){
				this.loc.add(new AddressJSONBean(adr));
			}

		}
	}

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}

	public String getPrtNam() {
		return prtNam;
	}

	public void setPrtNam(String prtNam) {
		this.prtNam = prtNam;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<AddressJSONBean> getLoc() {
		return loc;
	}

	public void setLoc(List<AddressJSONBean> loc) {
		this.loc = loc;
	}


	public String getEipOffer() {
		return eipOffer;
	}

	public void setEipOffer(String eipOffer) {
		this.eipOffer = eipOffer;
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

	public String getTnc() {
		return tnc;
	}

	public void setTnc(String tnc) {
		this.tnc = tnc;
	}
	public String getLstDt() {
		return lstDt;
	}

	public void setLstDt(String lstDt) {
		this.lstDt = lstDt;
	}

}
