package com.barclays.bmg.service.response.offer;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.offer.CityDTO;
import com.barclays.bmg.dto.offer.DiningOfferDto;



public class DiningServiceResponse  extends ResponseContext{

	List<DiningOfferDto> diningOfferList;
	List<CityDTO> cityList;

	public List<DiningOfferDto> getDiningOfferList() {
		return diningOfferList;
	}

	public void setDiningOfferList(List<DiningOfferDto> diningOfferList) {
		this.diningOfferList = diningOfferList;
	}

	public List<CityDTO> getCityList() {
		return cityList;
	}

	public void setCityList(List<CityDTO> cityList) {
		this.cityList = cityList;
	}



}
