package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.AuthenticationServiceRequest;
import com.barclays.bmg.service.response.AuthenticationServiceResponse;

public interface AuthenticationChallengeDAO {

    public AuthenticationServiceResponse retrieveChallenge(AuthenticationServiceRequest request);

}
