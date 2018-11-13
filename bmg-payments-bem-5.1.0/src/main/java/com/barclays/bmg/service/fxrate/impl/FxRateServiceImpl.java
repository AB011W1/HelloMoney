package com.barclays.bmg.service.fxrate.impl;

import com.barclays.bmg.dao.FxRateDAO;
import com.barclays.bmg.service.fxrate.FxRateService;

public class FxRateServiceImpl implements FxRateService {
    private FxRateDAO fxRateDAO;

    public FxRateDAO getFxRateDAO() {
	return fxRateDAO;
    }

    public void setFxRateDAO(FxRateDAO fxRateDAO) {
	this.fxRateDAO = fxRateDAO;
    }
}
