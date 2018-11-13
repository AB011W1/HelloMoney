package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.PasswordChangeServiceRequest;
import com.barclays.bmg.service.response.PasswordChangeServiceResponse;

public interface PasswordChangeDAO {

    public PasswordChangeServiceResponse changePassword(PasswordChangeServiceRequest request);

}
