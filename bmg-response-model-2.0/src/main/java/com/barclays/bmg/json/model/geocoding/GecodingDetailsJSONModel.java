package com.barclays.bmg.json.model.geocoding;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.json.response.BMBPayloadData;
import com.barclays.bmg.location.POIDetails;

@SuppressWarnings("unchecked")
public class GecodingDetailsJSONModel extends BMBPayloadData {


	private static final long serialVersionUID = 5635230708881864059L;
	private String imgURLPrefix;
	private List<GeoDetailsJsonBean> poiList = new ArrayList<GeoDetailsJsonBean>();

	public GecodingDetailsJSONModel(String imgURLPrefix, List<POIDetails> poiList) {
		super();
		this.imgURLPrefix = imgURLPrefix;

		if(poiList != null && !poiList.isEmpty()){
			for(POIDetails each : poiList){
				GeoDetailsJsonBean geoDetl = new GeoDetailsJsonBean(each);
				this.poiList.add(geoDetl);
			}
		}
	}

	public String getImgURLPrefix() {
		return imgURLPrefix;
	}

	public void setImgURLPrefix(String imgURLPrefix) {
		this.imgURLPrefix = imgURLPrefix;
	}

	public List getPoiList() {
		return poiList;
	}

	public void setPoiList(List poiList) {
		this.poiList = poiList;
	}

}
