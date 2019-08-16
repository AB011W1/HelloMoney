package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.ReportProblemServiceRequest;
import com.barclays.bmg.service.response.ReportProblemServiceResponse;

public interface ReportProblemDAO {

    public ReportProblemServiceResponse addProblem(ReportProblemServiceRequest request);
}
