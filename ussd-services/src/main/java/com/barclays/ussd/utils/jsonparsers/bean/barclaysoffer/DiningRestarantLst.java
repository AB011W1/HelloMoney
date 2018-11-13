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
public class DiningRestarantLst {

    @JsonProperty
    private String offerId;

    @JsonProperty
    private String nam;

    @JsonProperty
    private String cusin;

    @JsonProperty
    private String disct;

    @JsonProperty
    private String lstDt;

    @JsonProperty
    private String desc;

    @JsonProperty
    private List<OfferAddress> loc = new ArrayList<OfferAddress>();

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

    public String getCusin() {
	return cusin;
    }

    public void setCusin(String cusin) {
	this.cusin = cusin;
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

    public List<OfferAddress> getLoc() {
	return loc;
    }

    public void setLoc(List<OfferAddress> loc) {
	this.loc = loc;
    }

}
