package com.barclays.bmg.service;

import com.barclays.bmg.service.request.ValidateUserServiceRequest;
import com.barclays.bmg.service.response.ValidateUserServiceResponse;

public interface ValidateUserService {

    public ValidateUserServiceResponse validateUserIgnoreCase(ValidateUserServiceRequest request);
}
