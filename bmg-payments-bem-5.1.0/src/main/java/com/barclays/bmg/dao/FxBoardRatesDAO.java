package com.barclays.bmg.dao;

import com.barclays.bmg.fxrate.service.request.FXBoardRatesServiceRequest;
import com.barclays.bmg.fxrate.service.response.FXBoardRatesServiceResponse;



public interface FxBoardRatesDAO {

	public FXBoardRatesServiceResponse retreiveFxBoardRate(FXBoardRatesServiceRequest request);
}
