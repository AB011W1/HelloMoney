package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.AddOrganizationBeneficiaryServiceRequest;
import com.barclays.bmg.service.response.AddOrgBeneficiaryServiceResponse;

public interface AddOrganizationBeneficiaryDAO {

	public AddOrgBeneficiaryServiceResponse addOrganizationBeneficiary(AddOrganizationBeneficiaryServiceRequest request);
}
