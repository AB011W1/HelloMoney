package com.barclays.bmg.dao.intrates;

import com.barclays.bmg.service.intrates.request.InterestRatesServiceRequest;
import com.barclays.bmg.service.intrates.response.InterestRatesServiceResponse;

public interface InterestRatesDAO {

	public InterestRatesServiceResponse getIntratesList(
			InterestRatesServiceRequest request);

}
