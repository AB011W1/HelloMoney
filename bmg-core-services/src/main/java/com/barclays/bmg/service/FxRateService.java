package com.barclays.bmg.service;

import com.barclays.bmg.fxrate.service.request.FxRateServiceRequest;
import com.barclays.bmg.fxrate.service.response.FxRateServiceResponse;

public interface FxRateService {

    public FxRateServiceResponse retreiveFxRate(FxRateServiceRequest request);
}
