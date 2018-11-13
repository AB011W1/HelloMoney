package com.barclays.bmg.service;

import com.barclays.bmg.service.request.RetreiveBeneficiaryDetailsServiceRequest;
import com.barclays.bmg.service.response.RetreiveBeneficiaryDetailsServiceResponse;

public interface RetreiveBeneficiaryDetailsService {

    public RetreiveBeneficiaryDetailsServiceResponse retreiveBeneficiaryDetails(RetreiveBeneficiaryDetailsServiceRequest request);
}
