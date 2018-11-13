package com.barclays.bmg.service;

import com.barclays.bmg.fxrate.service.request.FXBoardRatesServiceRequest;
import com.barclays.bmg.fxrate.service.response.FXBoardRatesServiceResponse;

public interface FXBoardRatesService {

    public FXBoardRatesServiceResponse retreiveFxRate(FXBoardRatesServiceRequest request);
}
