package com.barclays.ussd.services.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.barclays.ussd.dto.UssdOfferLookUpDTO;

public class UssdBarclaysOfferLookUpDAOImpl extends SqlMapClientDaoSupport implements UssdOfferLocatorLookUpDAO {

    private static String DINING_OFFER_CITY_LIST_LOOKUP_QUERY = "ussdRetrieveDiningCityList";
    private static String DINING_RESTAURANT_LIST_LOOKUP_QUERY = "ussdRetrieveDiningRestarantList";
    private static String INSTALLMENT_OFFER_PARTNER_LIST_LOOKUP_QUERY = "ussdRetrievePartnerList";

    @Override
    public List<UssdOfferLookUpDTO> getRestaurantList(String businessId, String cityName, String restarantLetter) {
	UssdOfferLookUpDTO ussdOfferLookUpDTO = new UssdOfferLookUpDTO();
	ussdOfferLookUpDTO.setBusinessId(businessId);
	ussdOfferLookUpDTO.setCityName(formatInput(cityName));
	ussdOfferLookUpDTO.setOfferTypeID("1");
	ussdOfferLookUpDTO.setRestaurentName(formatInput(restarantLetter));
	List<UssdOfferLookUpDTO> areaList = this.getSqlMapClientTemplate().queryForList(DINING_RESTAURANT_LIST_LOOKUP_QUERY, ussdOfferLookUpDTO);
	return areaList;
    }

    @Override
    public List<UssdOfferLookUpDTO> getCityList(String businessId, String cityLetter) {
	UssdOfferLookUpDTO ussdOfferLookUpDTO = new UssdOfferLookUpDTO();
	ussdOfferLookUpDTO.setBusinessId(businessId);
	ussdOfferLookUpDTO.setCityName(formatInput(cityLetter));
	ussdOfferLookUpDTO.setOfferTypeID("1");
	List<UssdOfferLookUpDTO> cityList = this.getSqlMapClientTemplate().queryForList(DINING_OFFER_CITY_LIST_LOOKUP_QUERY, ussdOfferLookUpDTO);
	return cityList;
    }

    @Override
    public List<UssdOfferLookUpDTO> getPartnerList(String businessId, String partnerLetter) {
	UssdOfferLookUpDTO ussdOfferCityLookUpDTO = new UssdOfferLookUpDTO();
	ussdOfferCityLookUpDTO.setBusinessId(businessId);
	ussdOfferCityLookUpDTO.setPartnerName(formatInput(partnerLetter));
	ussdOfferCityLookUpDTO.setOfferTypeID(formatInput("2"));
	List<UssdOfferLookUpDTO> partnerList = this.getSqlMapClientTemplate().queryForList(INSTALLMENT_OFFER_PARTNER_LIST_LOOKUP_QUERY,
		ussdOfferCityLookUpDTO);
	return partnerList;
    }

    private String formatInput(String value) {
	String updatedvalue = value;
	if (null == updatedvalue || updatedvalue.trim().length() == 0) {
	    return null;
	} else {
	    updatedvalue = updatedvalue.toLowerCase();
	    return new StringBuilder().append(updatedvalue).append("%").toString();
	}
    }

}
