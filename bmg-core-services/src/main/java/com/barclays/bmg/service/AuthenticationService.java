package com.barclays.bmg.service;

import com.barclays.bmg.service.request.AuthenticationServiceRequest;
import com.barclays.bmg.service.response.AuthenticationServiceResponse;

public interface AuthenticationService {

    public AuthenticationServiceResponse verify(AuthenticationServiceRequest request);

}
