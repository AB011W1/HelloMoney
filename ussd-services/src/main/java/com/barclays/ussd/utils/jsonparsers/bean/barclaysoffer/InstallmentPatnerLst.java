/**
 * CatPayeeLst.java
 */
package com.barclays.ussd.utils.jsonparsers.bean.barclaysoffer;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author BTCI
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstallmentPatnerLst {

    @JsonProperty
    private String offerId;

    @JsonProperty
    private String prtNam;

    @JsonProperty
    private String desc;

    @JsonProperty
    private List<OfferAddress> loc = new ArrayList<OfferAddress>();

    @JsonProperty
    private String eipOffer;

    @JsonProperty
    private String catId;

    @JsonProperty
    private String catNam;

    @JsonProperty
    private String tnc;

    @JsonProperty
    private String lstDt;

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

    public List<OfferAddress> getLoc() {
	return loc;
    }

    public void setLoc(List<OfferAddress> loc) {
	this.loc = loc;
    }

}
