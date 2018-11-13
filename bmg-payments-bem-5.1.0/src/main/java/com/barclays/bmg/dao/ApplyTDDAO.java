package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.ApplyTDServiceRequest;
import com.barclays.bmg.service.response.ApplyTDServiceResponse;

public interface ApplyTDDAO {

	public ApplyTDServiceResponse addProblem(
			ApplyTDServiceRequest request);
}
