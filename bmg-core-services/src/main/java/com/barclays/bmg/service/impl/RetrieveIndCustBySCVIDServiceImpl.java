package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.RetrieveIndCustBySCVIDDAO;
import com.barclays.bmg.service.RetrieveIndCustBySCVIDService;
import com.barclays.bmg.service.request.RetrieveIndCustBySCVIDServiceRequest;
import com.barclays.bmg.service.response.RetrieveIndCustBySCVIDServiceResponse;

public class RetrieveIndCustBySCVIDServiceImpl implements RetrieveIndCustBySCVIDService {

    private RetrieveIndCustBySCVIDDAO retrieveIndCustBySCVIDDAO;

    public void setRetrieveIndCustBySCVIDDAO(RetrieveIndCustBySCVIDDAO retrieveIndCustBySCVIDDAO) {
	this.retrieveIndCustBySCVIDDAO = retrieveIndCustBySCVIDDAO;
    }

    @Override
    public RetrieveIndCustBySCVIDServiceResponse retrieveIndCustBySCVID(RetrieveIndCustBySCVIDServiceRequest request) {
	// TODO Auto-generated method stub

	RetrieveIndCustBySCVIDServiceResponse response = retrieveIndCustBySCVIDDAO.retrieveIndCustBySCVID(request);

	return response;
    }

}
