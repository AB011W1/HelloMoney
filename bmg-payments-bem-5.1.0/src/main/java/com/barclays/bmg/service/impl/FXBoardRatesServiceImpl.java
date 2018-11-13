package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.FxBoardRatesDAO;
import com.barclays.bmg.fxrate.service.request.FXBoardRatesServiceRequest;
import com.barclays.bmg.fxrate.service.response.FXBoardRatesServiceResponse;
import com.barclays.bmg.service.FXBoardRatesService;

public class FXBoardRatesServiceImpl implements FXBoardRatesService {

    private FxBoardRatesDAO fxBoardRatesDAO;

    @Override
    public FXBoardRatesServiceResponse retreiveFxRate(FXBoardRatesServiceRequest request) {

    	FXBoardRatesServiceResponse response = fxBoardRatesDAO.retreiveFxBoardRate(request);
	return response;
    }

	public FxBoardRatesDAO getFxBoardRatesDAO() {
		return fxBoardRatesDAO;
	}

	public void setFxBoardRatesDAO(FxBoardRatesDAO fxBoardRatesDAO) {
		this.fxBoardRatesDAO = fxBoardRatesDAO;
	}




}
