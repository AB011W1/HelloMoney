package com.barclays.bmg.service;

import com.barclays.bmg.service.request.PasswordChangeServiceRequest;
import com.barclays.bmg.service.response.PasswordChangeServiceResponse;

public interface PasswordChangeService {

    public PasswordChangeServiceResponse changePassword(PasswordChangeServiceRequest request);

}
