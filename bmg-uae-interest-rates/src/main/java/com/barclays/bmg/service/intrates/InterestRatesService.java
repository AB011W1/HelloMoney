package com.barclays.bmg.service.intrates;

import com.barclays.bmg.service.intrates.request.InterestRatesServiceRequest;
import com.barclays.bmg.service.intrates.response.InterestRatesServiceResponse;

public interface InterestRatesService {

	public InterestRatesServiceResponse getIntratesList(
			InterestRatesServiceRequest request);

}
