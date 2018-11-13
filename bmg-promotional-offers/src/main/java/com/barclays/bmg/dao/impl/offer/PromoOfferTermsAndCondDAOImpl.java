package com.barclays.bmg.dao.impl.offer;

import java.util.HashMap;
import java.util.Map;

import com.barclays.bmg.dao.impl.BaseDAOImpl;
import com.barclays.bmg.dao.offer.PromoOfferTermsAndCondDAO;
import com.barclays.bmg.dto.offer.TermsAndConditionDTO;
import com.barclays.bmg.service.request.offer.TermsAndCondServiceRequest;
import com.barclays.bmg.service.response.offer.TermsAndCondServiceResponse;

public class PromoOfferTermsAndCondDAOImpl extends BaseDAOImpl implements PromoOfferTermsAndCondDAO {

	private final String OFFER_TYP = "offerType";
	private final String LOAD_TC = "loadTermsAndCondition";

	@Override
	public TermsAndCondServiceResponse retrieveTermsAndCondition(
			TermsAndCondServiceRequest request) {
		// TODO Auto-generated method stub

		TermsAndCondServiceResponse termsAndCondServiceResponse = new TermsAndCondServiceResponse();
		Map<String, String> paramMap = new HashMap<String, String>();

		String offerType = request.getOfferType();

		if("eip".equalsIgnoreCase(offerType)){
			offerType = "2";
		}else {
			offerType="1";
		}

		paramMap.put(OFFER_TYP, offerType);
		try {
			TermsAndConditionDTO termsAndConditionDTO = (TermsAndConditionDTO) this.queryForObject(LOAD_TC, paramMap);
			termsAndCondServiceResponse.setTermsAndCondition(termsAndConditionDTO.getTermsAndCondDesc());
			termsAndCondServiceResponse.setContext(request.getContext());
			termsAndCondServiceResponse.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			termsAndCondServiceResponse.setSuccess(false);
		}
		return termsAndCondServiceResponse;
	}

}
