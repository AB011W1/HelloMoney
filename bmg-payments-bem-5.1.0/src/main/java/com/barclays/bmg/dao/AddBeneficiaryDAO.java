package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.AddBeneficiaryServiceRequest;
import com.barclays.bmg.service.response.AddBeneficiaryServiceResponse;

public interface AddBeneficiaryDAO {

	AddBeneficiaryServiceResponse addBeneficiary(AddBeneficiaryServiceRequest addBeneficiaryServiceRequest);

}
