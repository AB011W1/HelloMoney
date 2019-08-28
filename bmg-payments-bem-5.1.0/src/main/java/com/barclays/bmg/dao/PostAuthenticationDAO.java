package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.PostAuthenticationServiceRequest;
import com.barclays.bmg.service.response.PostAuthenticationServiceResponse;

public interface PostAuthenticationDAO {

    public PostAuthenticationServiceResponse afterLoginSuccess(PostAuthenticationServiceRequest request);

}
