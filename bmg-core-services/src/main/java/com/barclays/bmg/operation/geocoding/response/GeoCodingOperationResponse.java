package com.barclays.bmg.operation.geocoding.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;

public class GeoCodingOperationResponse extends ResponseContext {
    /**
	 *
	 */
    private static final long serialVersionUID = -4525118494904034462L;
    private String imageURLPrefix;
    private List filteredLocations;

    public String getImageURLPrefix() {
	return imageURLPrefix;
    }

    public void setImageURLPrefix(String imageURLPrefix) {
	this.imageURLPrefix = imageURLPrefix;
    }

    public List getFilteredLocations() {
	return filteredLocations;
    }

    public void setFilteredLocations(List filteredLocations) {
	this.filteredLocations = filteredLocations;
    }

}
