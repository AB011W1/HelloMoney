package com.barclays.bmg.service;

import com.barclays.bmg.service.request.ApplyTDServiceRequest;
import com.barclays.bmg.service.response.ApplyTDServiceResponse;

/**
 * @author E20027734
 *
 */
public interface ApplyTDService {

	public ApplyTDServiceResponse addProblem(ApplyTDServiceRequest request);
}
