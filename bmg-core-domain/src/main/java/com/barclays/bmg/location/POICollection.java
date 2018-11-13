package com.barclays.bmg.location;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("restriction")
@XmlRootElement(name = "POICollection")
public class POICollection {

    private List<POIDetails> pointOfInterestList = new ArrayList<POIDetails>();

    @XmlElements( { @XmlElement(name = "pointOfInterest", type = POIDetails.class) })
    public List<POIDetails> getPointOfInterestList() {
	return pointOfInterestList;
    }

    public void setPointOfInterestList(List<POIDetails> pointOfInterestList) {
	this.pointOfInterestList = pointOfInterestList;
    }

    public void addPointOfInterest(POIDetails details) {
	this.pointOfInterestList.add(details);
    }

}
