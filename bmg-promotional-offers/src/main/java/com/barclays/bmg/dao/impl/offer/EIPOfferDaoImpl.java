package com.barclays.bmg.dao.impl.offer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.impl.BaseDAOImpl;
import com.barclays.bmg.dao.offer.EIPOfferDao;
import com.barclays.bmg.dto.offer.AddressDTO;
import com.barclays.bmg.dto.offer.EIPOfferDTO;
import com.barclays.bmg.dto.offer.OfferCategoryDTO;
import com.barclays.bmg.ibatis.dto.offer.PromotionalOfferDto;
import com.barclays.bmg.service.request.offer.EIPOfferDetailServiceRequest;
import com.barclays.bmg.service.request.offer.EIPServiceRequest;
import com.barclays.bmg.service.response.offer.EIPOfferDetailServiceResponse;
import com.barclays.bmg.service.response.offer.EIPServiceResponse;

public class EIPOfferDaoImpl extends BaseDAOImpl implements EIPOfferDao {

    private static final String SYSTEM_ID = "systemId";
    private static final String BUSINESS_ID = "businessId";
    private static final String OFFER_ID = "offerId";
    private static final String CATEGORY = "catgoryId";
    private static final String PARTNER = "partnerName";
    private static final String OFFER = "eipOffer";
    private static final String CITY = "city";
    private static final String LOAD_ALL_EIP_OFFERS = "loadEIPOffers";
    private static final String LOAD_CATEGORIES = "loadEIPCategorylist";

    @SuppressWarnings("unchecked")
    public EIPServiceResponse retrieveAllEipOffers(EIPServiceRequest request) {
	// TODO Auto-generated method stub

	Context context = request.getContext();
	EIPServiceResponse response = new EIPServiceResponse();
	response.setContext(context);

	Map<String, String> paramMap = new HashMap<String, String>();

	paramMap.put(SYSTEM_ID, context.getSystemId());
	paramMap.put(BUSINESS_ID, context.getBusinessId());

	List<PromotionalOfferDto> promoOfferDtoList = null;
	try {
	    promoOfferDtoList = this.queryForList(LOAD_ALL_EIP_OFFERS, paramMap);

	    response.setEipOfferList(mapEIPOfferDetls(promoOfferDtoList));
	    response.setSuccess(true);

	} catch (Exception e) {
	    
	    response.setSuccess(false);
	}

	response.setContext(request.getContext());

	return response;
    }

    @SuppressWarnings("unchecked")
    public EIPOfferDetailServiceResponse retrieveEipOfferDetails(EIPOfferDetailServiceRequest request) {

	Context context = request.getContext();
	EIPOfferDetailServiceResponse response = new EIPOfferDetailServiceResponse();
	response.setContext(context);

	Map<String, String> paramMap = new HashMap<String, String>();

	paramMap.put(SYSTEM_ID, context.getSystemId());
	paramMap.put(BUSINESS_ID, context.getBusinessId());
	paramMap.put(OFFER_ID, request.getOfferId());

	List<PromotionalOfferDto> promoOfferDtoList = null;
	try {
	    promoOfferDtoList = this.queryForList(LOAD_ALL_EIP_OFFERS, paramMap);

	    List<EIPOfferDTO> eipOfferList = mapEIPOfferDetls(promoOfferDtoList);
	    if (eipOfferList != null && !eipOfferList.isEmpty()) {
		response.setEipOffer(eipOfferList.get(0));
		response.setSuccess(true);
	    } else {
		response.setSuccess(false);
	    }
	} catch (Exception e) {
	    
	    response.setSuccess(false);
	}

	return response;
    }

    @SuppressWarnings("unchecked")
    public EIPServiceResponse searchEipOffers(EIPServiceRequest request) {

	Context context = request.getContext();
	EIPServiceResponse response = new EIPServiceResponse();
	response.setContext(context);

	Map<String, String> paramMap = new HashMap<String, String>();

	paramMap.put(SYSTEM_ID, context.getSystemId());
	paramMap.put(BUSINESS_ID, context.getBusinessId());

	String partner = request.getPartner();
	if (partner != null && !partner.isEmpty()) {
	    paramMap.put(PARTNER, partner);
	}

	List<PromotionalOfferDto> promoOfferDtoList = null;
	try {
	    promoOfferDtoList = this.queryForList(LOAD_ALL_EIP_OFFERS, paramMap);

	    response.setEipOfferList(mapEIPOfferDetls(promoOfferDtoList));
	    response.setSuccess(true);

	} catch (Exception e) {
	    
	    response.setSuccess(false);
	}

	return response;
    }

    @SuppressWarnings("unchecked")
    @Override
    public EIPServiceResponse retrieveAllCategories(EIPServiceRequest request) {
	Context context = request.getContext();
	EIPServiceResponse response = new EIPServiceResponse();
	response.setContext(context);

	List<OfferCategoryDTO> offerCategoryList = null;
	Map<String, String> paramMap = new HashMap<String, String>();

	paramMap.put(BUSINESS_ID, context.getBusinessId());
	try {
	    offerCategoryList = this.queryForList(LOAD_CATEGORIES, paramMap);

	    response.setOfferCategoryList(offerCategoryList);
	    response.setSuccess(true);

	} catch (Exception e) {
	    
	    response.setSuccess(false);
	}

	return response;
    }

    private List<EIPOfferDTO> mapEIPOfferDetls(List<PromotionalOfferDto> promoOfferList) {

	Map<String, EIPOfferDTO> eipOfferMap = new HashMap<String, EIPOfferDTO>();

	if (promoOfferList != null && !promoOfferList.isEmpty()) {

	    for (PromotionalOfferDto po : promoOfferList) {
		if (eipOfferMap.isEmpty() || !eipOfferMap.containsKey(po.getOfferId())) {

		    EIPOfferDTO eipOffer = new EIPOfferDTO();
		    eipOffer.setOfferId(po.getOfferId());
		    eipOffer.setCategoryId(po.getCatgoryId());
		    eipOffer.setPartnerName(po.getPartnerName());
		    eipOffer.setDescription(po.getOfferDesc());
		    eipOffer.setCategoryName(po.getCatgoryDesc());
		    eipOffer.setOfferEndDate(po.getOfferEndDate());
		    eipOffer.setEipOffer(po.getEipOffer());
		    eipOffer.setOfferTnc(po.getOfferTnc());

		    List<AddressDTO> addrList = new ArrayList<AddressDTO>();

		    AddressDTO addr = new AddressDTO();
		    addr.setCityName(po.getCityName());
		    addr.setLocation(po.getCityAddress());
		    addr.setPhoneNo(po.getPhoneNo());
		    addr.setLatitude(po.getLatitude());
		    addr.setLongitude(po.getLongitude());

		    addrList.add(addr);

		    eipOffer.setAddressList(addrList);

		    eipOfferMap.put(eipOffer.getOfferId(), eipOffer);
		} else if (!eipOfferMap.isEmpty() && eipOfferMap.containsKey(po.getOfferId())) {
		    EIPOfferDTO eipOffer = eipOfferMap.get(po.getOfferId());

		    AddressDTO addr = new AddressDTO();
		    addr.setCityName(po.getCityName());
		    addr.setLocation(po.getCityAddress());
		    addr.setPhoneNo(po.getPhoneNo());
		    addr.setLatitude(po.getLatitude());
		    addr.setLongitude(po.getLongitude());

		    eipOffer.getAddressList().add(addr);
		}

	    }
	}
	return (new ArrayList<EIPOfferDTO>(eipOfferMap.values()));
    }

}
