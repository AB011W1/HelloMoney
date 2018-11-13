package com.barclays.bmg.service;

import com.barclays.bmg.service.request.AddBeneficiaryServiceRequest;
import com.barclays.bmg.service.response.AddBeneficiaryServiceResponse;

public interface AddBeneficiaryService {

    AddBeneficiaryServiceResponse addBeneficiary(AddBeneficiaryServiceRequest addBeneficiaryServiceRequest);

}
