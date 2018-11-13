package com.barclays.ussd.services.dao.impl;

import java.util.List;

import com.barclays.ussd.dto.UssdOfferLookUpDTO;

public interface UssdOfferLocatorLookUpDAO {

    public List<UssdOfferLookUpDTO> getCityList(String businessId, String cityLetter);

    public List<UssdOfferLookUpDTO> getPartnerList(String businessId, String partnerLetter);

    public List<UssdOfferLookUpDTO> getRestaurantList(String businessId, String cityName, String offerTypeId);
}
