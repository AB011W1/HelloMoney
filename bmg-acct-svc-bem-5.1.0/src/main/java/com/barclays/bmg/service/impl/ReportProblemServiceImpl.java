package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.ReportProblemDAO;
import com.barclays.bmg.service.ReportProblemService;
import com.barclays.bmg.service.request.ReportProblemServiceRequest;
import com.barclays.bmg.service.response.ReportProblemServiceResponse;

public class ReportProblemServiceImpl implements ReportProblemService {

    private ReportProblemDAO reportProblemDAO;

    @Override
    public ReportProblemServiceResponse addProblem(ReportProblemServiceRequest request) {
	// TODO Auto-generated method stub
	ReportProblemServiceResponse response = reportProblemDAO.addProblem(request);
	return response;
    }

    public ReportProblemDAO getReportProblemDAO() {
	return reportProblemDAO;
    }

    public void setReportProblemDAO(ReportProblemDAO reportProblemDAO) {
	this.reportProblemDAO = reportProblemDAO;
    }

}
