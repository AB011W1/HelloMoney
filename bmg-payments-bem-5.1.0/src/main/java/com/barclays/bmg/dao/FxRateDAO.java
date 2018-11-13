package com.barclays.bmg.dao;

import com.barclays.bmg.fxrate.service.request.FxRateServiceRequest;
import com.barclays.bmg.fxrate.service.response.FxRateServiceResponse;



public interface FxRateDAO {

	public FxRateServiceResponse retreiveFxRate(FxRateServiceRequest request);
}
