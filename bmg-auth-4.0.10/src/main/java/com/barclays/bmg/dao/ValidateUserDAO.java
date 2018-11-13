package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.ValidateUserServiceRequest;
import com.barclays.bmg.service.response.ValidateUserServiceResponse;

public interface ValidateUserDAO {

    public ValidateUserServiceResponse validateUserIgnoreCase(ValidateUserServiceRequest request);

}
