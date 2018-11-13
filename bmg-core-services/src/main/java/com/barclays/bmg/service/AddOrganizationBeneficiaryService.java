package com.barclays.bmg.service;

import com.barclays.bmg.service.request.AddOrganizationBeneficiaryServiceRequest;
import com.barclays.bmg.service.response.AddOrgBeneficiaryServiceResponse;

public interface AddOrganizationBeneficiaryService {

    public AddOrgBeneficiaryServiceResponse addOrganizationBeneficiary(AddOrganizationBeneficiaryServiceRequest request);
}
