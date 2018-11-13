package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.FxRateDAO;
import com.barclays.bmg.fxrate.service.request.FxRateServiceRequest;
import com.barclays.bmg.fxrate.service.response.FxRateServiceResponse;
import com.barclays.bmg.service.FxRateService;

public class FxRateServiceImpl implements FxRateService {

    private FxRateDAO fxRateDAO;

    @Override
    public FxRateServiceResponse retreiveFxRate(FxRateServiceRequest request) {

		FxRateServiceResponse response = fxRateDAO.retreiveFxRate(request);
	return response;
    }

    public FxRateDAO getFxRateDAO() {
	return fxRateDAO;
    }

    public void setFxRateDAO(FxRateDAO fxRateDAO) {
	this.fxRateDAO = fxRateDAO;
    }


}
