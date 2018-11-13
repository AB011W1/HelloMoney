package com.barclays.bmg.service;

import com.barclays.bmg.service.request.PostAuthenticationServiceRequest;
import com.barclays.bmg.service.response.PostAuthenticationServiceResponse;

public interface PostAuthenticationService {

    public PostAuthenticationServiceResponse afterLoginSuccess(PostAuthenticationServiceRequest request);

}
