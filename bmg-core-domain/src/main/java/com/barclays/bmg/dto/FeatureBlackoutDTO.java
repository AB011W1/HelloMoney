package com.barclays.bmg.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO represents S_FEATURE_BLACKOUT_MST table.
 */

public class FeatureBlackoutDTO implements Serializable {

    private static final long serialVersionUID = 4935715346042660585L;

    private Date startDtm;

    private Date endDtm;

    private String state;

    public Date getStartDtm() {
	if (startDtm != null) {
	    return new Date(startDtm.getTime());
	}
	return null;
    }

    public void setStartDtm(Date startDtm) {
	if (startDtm != null) {
	    this.startDtm = new Date(startDtm.getTime());
	} else {
	    this.startDtm = null;
	}
    }

    public Date getEndDtm() {
	if (endDtm != null) {
	    return new Date(endDtm.getTime());
	}
	return null;
    }

    public void setEndDtm(Date endDtm) {
	if (endDtm != null) {
	    this.endDtm = new Date(endDtm.getTime());
	} else {
	    this.endDtm = null;
	}
    }

    public String getState() {
	return state;
    }

    public void setState(String state) {
	this.state = state;
    }

}
