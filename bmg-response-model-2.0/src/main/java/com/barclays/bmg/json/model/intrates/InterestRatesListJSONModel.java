package com.barclays.bmg.json.model.intrates;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.json.response.BMBPayloadData;

public class InterestRatesListJSONModel extends BMBPayloadData implements Serializable {

    /**
	 *
	 */
    private static final long serialVersionUID = -4173227024974049371L;

    private List<InterestRatesJSONModel> intRatesList = new ArrayList<InterestRatesJSONModel>();

    protected String dispNoHistFnd = "No";

    /*
     * public InterestRatesListJSONModel(List<IntrateDTO> intrateDTOList) {
     * 
     * if (intrateDTOList != null && intrateDTOList.size() > 0) {
     * 
     * for (IntrateDTO intratesDTOList : intrateDTOList) { intRatesList.add(new InterestRatesJSONModel(intratesDTOList)); } } }
     */

    public List<InterestRatesJSONModel> getIntRatesList() {
	return intRatesList;
    }

    public void setIntRatesList(List<InterestRatesJSONModel> intRatesList) {
	this.intRatesList = intRatesList;
    }

    public String getDispNoHistFnd() {
	return dispNoHistFnd;
    }

    public void setDispNoHistFnd(String dispNoHistFnd) {
	this.dispNoHistFnd = dispNoHistFnd;
    }

}
