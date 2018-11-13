package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.RetreiveBeneficiaryDetailsServiceRequest;
import com.barclays.bmg.service.response.RetreiveBeneficiaryDetailsServiceResponse;

public interface RetreiveIndividualBeneficiaryDetailsDAO {

	public RetreiveBeneficiaryDetailsServiceResponse retreiveIndividualBeneficiaryDetails(RetreiveBeneficiaryDetailsServiceRequest request);
}
