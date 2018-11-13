package com.barclays.bmg.service.accountdetails.impl;

import com.barclays.bmg.dao.accountdetails.TDDetailsDAO;
import com.barclays.bmg.service.accountdetails.TDDetailsService;
import com.barclays.bmg.service.accountdetails.request.TDDetailsServiceRequest;
import com.barclays.bmg.service.accountdetails.response.TDDetailsServiceResponse;

public class TDDetailsServiceImpl implements TDDetailsService {

    private TDDetailsDAO tdDetailsDAO;

    @Override
    public TDDetailsServiceResponse retreiveTDDetails(TDDetailsServiceRequest request) {

	return tdDetailsDAO.retreiveTDDetails(request);
    }

    public TDDetailsDAO getTdDetailsDAO() {
	return tdDetailsDAO;
    }

    public void setTdDetailsDAO(TDDetailsDAO tdDetailsDAO) {
	this.tdDetailsDAO = tdDetailsDAO;
    }

}
