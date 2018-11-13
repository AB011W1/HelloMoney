package com.barclays.bmg.json.model.geocoding;

import java.util.List;

import com.barclays.bmg.location.POIDetails;

public class GeoDetailsJsonBean extends GeoLocationJsonBean {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String typ;
	private String nam;
	private String add;
	private String phNo;
	private String fax;
	private String custHelp;
	private String imgFileNam;
	private List<String> serv;
	private OpeningHoursJsonBean openHrs = new OpeningHoursJsonBean();

	public GeoDetailsJsonBean(POIDetails poiDetls) {
		super(poiDetls);
		this.typ = poiDetls.getType();
		this.nam = poiDetls.getName();
		this.add = poiDetls.getAddress();
		this.phNo = poiDetls.getPhoneNumber();
		this.fax = poiDetls.getFax();
		this.custHelp = poiDetls.getCustomerHelpline();
		this.imgFileNam = poiDetls.getImageFileName();
		this.serv = poiDetls.getServices();

		this.openHrs = new OpeningHoursJsonBean(poiDetls.getOpeningHours());

	}

	public GeoDetailsJsonBean() {
		super();
	}

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public String getNam() {
		return nam;
	}

	public void setNam(String nam) {
		this.nam = nam;
	}

	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public String getPhNo() {
		return phNo;
	}

	public void setPhNo(String phNo) {
		this.phNo = phNo;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getCustHelp() {
		return custHelp;
	}

	public void setCustHelp(String custHelp) {
		this.custHelp = custHelp;
	}

	public String getImgFileNam() {
		return imgFileNam;
	}

	public void setImgFileNam(String imgFileNam) {
		this.imgFileNam = imgFileNam;
	}

	public List<String> getServ() {
		return serv;
	}

	public void setServ(List<String> serv) {
		this.serv = serv;
	}

	public OpeningHoursJsonBean getOpenHrs() {
		return openHrs;
	}

	public void setOpenHrs(OpeningHoursJsonBean openHrs) {
		this.openHrs = openHrs;
	}

}
