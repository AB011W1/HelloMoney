package com.barclays.bmg.service.intrates.impl;

import com.barclays.bmg.dao.intrates.InterestRatesDAO;
import com.barclays.bmg.service.intrates.InterestRatesService;
import com.barclays.bmg.service.intrates.request.InterestRatesServiceRequest;
import com.barclays.bmg.service.intrates.response.InterestRatesServiceResponse;

public class InterestRatesServiceImpl implements InterestRatesService {

	private InterestRatesDAO intRatesDAO;

	public InterestRatesServiceResponse getIntratesList(
			InterestRatesServiceRequest request) {

		return intRatesDAO.getIntratesList(request);

	}

	public void setIntRatesDAO(InterestRatesDAO intRatesDAO) {
		this.intRatesDAO = intRatesDAO;
	}

}
