package com.barclays.bmg.service;

import com.barclays.bmg.service.request.ReportProblemServiceRequest;
import com.barclays.bmg.service.response.ReportProblemServiceResponse;

/**
 * @author E20027734
 * 
 */
public interface ReportProblemService {

    public ReportProblemServiceResponse addProblem(ReportProblemServiceRequest request);
}
