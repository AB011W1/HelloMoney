package com.barclays.bmg.dao.impl.offer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.impl.BaseDAOImpl;
import com.barclays.bmg.dao.offer.DiningOfferDao;
import com.barclays.bmg.dto.offer.AddressDTO;
import com.barclays.bmg.dto.offer.CityDTO;
import com.barclays.bmg.dto.offer.DiningOfferDto;
import com.barclays.bmg.ibatis.dto.offer.PromotionalOfferDto;
import com.barclays.bmg.service.request.offer.DiningOfferDetailsServiceRequest;
import com.barclays.bmg.service.request.offer.DiningServiceRequest;
import com.barclays.bmg.service.response.offer.DiningOfferDetailsServiceResponse;
import com.barclays.bmg.service.response.offer.DiningServiceResponse;

public class DiningOfferDaoImpl extends BaseDAOImpl implements DiningOfferDao {

    private static final String SYSTEM_ID = "systemId";
    private static final String BUSINESS_ID = "businessId";
    private static final String OFFER_ID = "offerId";
    private static final String REST_NAME = "resturantName";
    private static final String CITY = "city";

    private static final String LOAD_ALL_DINE_OFFERS = "loadDiningOffers";
    private static final String LOAD_ALL_DINE_CITIES = "allDineCities";

    @SuppressWarnings("unchecked")
    public DiningServiceResponse retrieveAllDiningOffers(DiningServiceRequest request) {

	Context context = request.getContext();
	DiningServiceResponse response = new DiningServiceResponse();
	response.setContext(context);

	Map<String, String> paramMap = new HashMap<String, String>();

	paramMap.put(SYSTEM_ID, context.getSystemId());
	paramMap.put(BUSINESS_ID, context.getBusinessId());

	List<PromotionalOfferDto> promoOfferDtoList = null;
	try {
	    promoOfferDtoList = this.queryForList(LOAD_ALL_DINE_OFFERS, paramMap);

	    response.setDiningOfferList(mapDinningOfferDetls(promoOfferDtoList));
	    response.setSuccess(true);

	} catch (Exception e) {
	    
	    response.setSuccess(false);
	}

	return response;
    }

    @SuppressWarnings("unchecked")
    public DiningOfferDetailsServiceResponse retrieveDiningOfferDetail(DiningOfferDetailsServiceRequest request) {

	Context context = request.getContext();
	DiningOfferDetailsServiceResponse diningOfferDetlServResp = new DiningOfferDetailsServiceResponse();
	diningOfferDetlServResp.setContext(context);

	Map<String, String> paramMap = new HashMap<String, String>();

	paramMap.put(SYSTEM_ID, context.getSystemId());
	paramMap.put(BUSINESS_ID, context.getBusinessId());
	paramMap.put(OFFER_ID, request.getOfferId());

	List<PromotionalOfferDto> promoOfferDtoList = null;

	try {

	    promoOfferDtoList = this.queryForList(LOAD_ALL_DINE_OFFERS, paramMap);

	    List<DiningOfferDto> dinningOfferList = mapDinningOfferDetls(promoOfferDtoList);
	    if (dinningOfferList != null && !dinningOfferList.isEmpty()) {
		diningOfferDetlServResp.setDiningOffer(dinningOfferList.get(0));
		diningOfferDetlServResp.setSuccess(true);
	    } else {
		diningOfferDetlServResp.setSuccess(false);
	    }

	} catch (Exception e) {
	    diningOfferDetlServResp.setSuccess(false);
	}

	return diningOfferDetlServResp;
    }

    @SuppressWarnings("unchecked")
    public DiningServiceResponse searchDiningOffers(DiningServiceRequest request) {

	Context context = request.getContext();
	DiningServiceResponse diningServResp = new DiningServiceResponse();
	diningServResp.setContext(context);

	Map<String, String> paramMap = new HashMap<String, String>();
	paramMap.put(SYSTEM_ID, context.getSystemId());
	paramMap.put(BUSINESS_ID, context.getBusinessId());

	String resturantName = request.getResturantName();
	if (resturantName != null && !resturantName.isEmpty()) {
	    paramMap.put(REST_NAME, resturantName);
	}

	String city = request.getCity();
	if (city != null && !city.isEmpty()) {
	    paramMap.put(CITY, city);
	}

	List<PromotionalOfferDto> promoOfferDtoList = null;
	try {

	    promoOfferDtoList = this.queryForList(LOAD_ALL_DINE_OFFERS, paramMap);

	    diningServResp.setDiningOfferList(mapDinningOfferDetls(promoOfferDtoList));
	    diningServResp.setSuccess(true);

	} catch (Exception e) {
	    diningServResp.setSuccess(true);
	}

	return diningServResp;
    }

    @SuppressWarnings("unchecked")
    @Override
    public DiningServiceResponse retrieveAllCities(DiningServiceRequest request) {

	Context context = request.getContext();
	DiningServiceResponse response = new DiningServiceResponse();
	response.setContext(context);

	Map<String, String> paramMap = new HashMap<String, String>();

	paramMap.put(SYSTEM_ID, context.getSystemId());
	paramMap.put(BUSINESS_ID, context.getBusinessId());

	List<CityDTO> cityList = null;
	try {
	    cityList = this.queryForList(LOAD_ALL_DINE_CITIES, paramMap);

	    response.setCityList(cityList);
	    response.setSuccess(true);
	} catch (Exception e) {
	    response.setSuccess(false);
	    
	}

	return response;
    }

    private List<DiningOfferDto> mapDinningOfferDetls(List<PromotionalOfferDto> promoOfferList) {

	Map<String, DiningOfferDto> diningOfferMap = new HashMap<String, DiningOfferDto>();

	if (promoOfferList != null && !promoOfferList.isEmpty()) {

	    for (PromotionalOfferDto po : promoOfferList)
		if (diningOfferMap.isEmpty() || !diningOfferMap.containsKey(po.getOfferId())) {

		    DiningOfferDto dineOffer = new DiningOfferDto();
		    dineOffer.setOfferId(po.getOfferId());
		    dineOffer.setName(po.getResturantName());
		    dineOffer.setCusine(po.getCusine());
		    dineOffer.setDiscount(po.getDiscountPercent());
		    dineOffer.setDescription(po.getOfferDesc());
		    dineOffer.setOfferEndDate(po.getOfferEndDate());
		    dineOffer.setOfferTnc(po.getOfferTnc());

		    List<AddressDTO> addrList = new ArrayList<AddressDTO>();

		    AddressDTO addr = new AddressDTO();
		    addr.setCityName(po.getCityName());
		    addr.setLocation(po.getCityAddress());
		    addr.setPhoneNo(po.getPhoneNo());
		    addr.setLatitude(po.getLatitude());
		    addr.setLongitude(po.getLongitude());

		    addrList.add(addr);

		    dineOffer.setAddressList(addrList);

		    diningOfferMap.put(dineOffer.getOfferId(), dineOffer);
		} else if (!diningOfferMap.isEmpty() && diningOfferMap.containsKey(po.getOfferId())) {
		    DiningOfferDto dinOffer = diningOfferMap.get(po.getOfferId());

		    AddressDTO addr = new AddressDTO();
		    addr.setCityName(po.getCityName());
		    addr.setLocation(po.getCityAddress());
		    addr.setPhoneNo(po.getPhoneNo());
		    addr.setLatitude(po.getLatitude());
		    addr.setLongitude(po.getLongitude());

		    dinOffer.getAddressList().add(addr);
		}

	}

	return (new ArrayList<DiningOfferDto>(diningOfferMap.values()));
    }

}
