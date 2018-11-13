package com.barclays.bmg.json.model.geocoding;

import java.io.Serializable;

import com.barclays.bmg.location.POIOpeningHours;

public class OpeningHoursJsonBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String mon;
	private String tue;
	private String wed;
	private String thu;
	private String fri;
	private String sat;
	private String sun;
	private String holidayNote;

	public OpeningHoursJsonBean() {
		super();
	}

	public OpeningHoursJsonBean(POIOpeningHours openingHours) {
		this.mon = openingHours.getMon();
		this.tue = openingHours.getTue();
		this.wed = openingHours.getWed();
		this.thu = openingHours.getThu();
		this.fri = openingHours.getFri();
		this.sat = openingHours.getSat();
		this.sun = openingHours.getSun();
		this.holidayNote = openingHours.getHolidayNote();
	}

	public String getMon() {
		return mon;
	}

	public void setMon(String mon) {
		this.mon = mon;
	}

	public String getTue() {
		return tue;
	}

	public void setTue(String tue) {
		this.tue = tue;
	}

	public String getWed() {
		return wed;
	}

	public void setWed(String wed) {
		this.wed = wed;
	}

	public String getThu() {
		return thu;
	}

	public void setThu(String thu) {
		this.thu = thu;
	}

	public String getFri() {
		return fri;
	}

	public void setFri(String fri) {
		this.fri = fri;
	}

	public String getSat() {
		return sat;
	}

	public void setSat(String sat) {
		this.sat = sat;
	}

	public String getSun() {
		return sun;
	}

	public void setSun(String sun) {
		this.sun = sun;
	}

	public String getHolidayNote() {
		return holidayNote;
	}

	public void setHolidayNote(String holidayNote) {
		this.holidayNote = holidayNote;
	}

}
