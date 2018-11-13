package com.barclays.bmg.service.featureblackout.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.FeatureBlackoutDAO;
import com.barclays.bmg.dto.FeatureBlackoutDTO;
import com.barclays.bmg.service.featureblackout.FeatureBlackoutService;
import com.barclays.bmg.service.featureblackout.request.FeatureBlackoutServiceRequest;

public class FeatureBlackoutServiceImpl implements FeatureBlackoutService {

    private FeatureBlackoutDAO featureBlackoutDAO;

    public void setFeatureBlackoutDAO(FeatureBlackoutDAO featureBlackoutDAO) {
	this.featureBlackoutDAO = featureBlackoutDAO;
    }

    public boolean checkFeatureBlackout(FeatureBlackoutServiceRequest request) {

	Context context = request.getContext();

	Map<String, String> params = new HashMap<String, String>();

	params.put("activityId", context.getActivityId());
	params.put("businessId", context.getBusinessId());
	params.put("systemId", context.getSystemId());

	FeatureBlackoutDTO blackout = featureBlackoutDAO.findByActivityId(params);

	if (blackout == null) {
	    return false;
	}

	boolean bkFlg = false;

	Date now = new Date();
	if ("FULL".equalsIgnoreCase(blackout.getState())) {
	    if (now.after(blackout.getStartDtm()) && now.before(blackout.getEndDtm())) {
		bkFlg = true;
	    }
	} else if ("DAILY".equalsIgnoreCase(blackout.getState())) {
	    if (calToMinutes(now) > calToMinutes(blackout.getStartDtm()) && calToMinutes(now) < calToMinutes(blackout.getEndDtm())) {
		bkFlg = true;
	    }
	}
	return bkFlg;
    }

    private int calToMinutes(Date dtm) {
	Calendar c = Calendar.getInstance();
	c.setTime(dtm);
	return c.get(Calendar.HOUR) * 60 + c.get(Calendar.MINUTE);
    }

}
