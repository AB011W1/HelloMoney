package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.PasswordChangeDAO;
import com.barclays.bmg.service.PasswordChangeService;
import com.barclays.bmg.service.request.PasswordChangeServiceRequest;
import com.barclays.bmg.service.response.PasswordChangeServiceResponse;

public class PasswordChangeServiceImpl implements PasswordChangeService {

    private PasswordChangeDAO passwordChangeDAO;

    @Override
    public PasswordChangeServiceResponse changePassword(PasswordChangeServiceRequest request) {

	PasswordChangeServiceResponse response = passwordChangeDAO.changePassword(request);
	return response;
    }

    public void setPasswordChangeDAO(PasswordChangeDAO passwordChangeDAO) {
	this.passwordChangeDAO = passwordChangeDAO;
    }

    public PasswordChangeDAO getPasswordChangeDAO() {
	return passwordChangeDAO;
    }

}
