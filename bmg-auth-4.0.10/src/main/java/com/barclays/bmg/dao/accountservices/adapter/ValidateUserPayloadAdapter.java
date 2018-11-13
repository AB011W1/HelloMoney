package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.ValidateUserServiceRequest;
import com.barclays.grcb.mcfe.ssc.authentication.ValidateUserByLoginNameIgnoreCase.Request;
import com.barclays.grcb.mcfe.ssc.authentication.entity.UserInfo;

/**
 * @author E20041929
 * 
 */
public class ValidateUserPayloadAdapter {

    public Request adaptPayLoad(WorkContext workContext) {
	Request requestBody = new Request();

	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();
	ValidateUserServiceRequest validateUserServiceRequest = (ValidateUserServiceRequest) args[0];

	// requestBody.setStaticPasswordInfo(staticPassword);
	UserInfo userInfo = new UserInfo();
	userInfo.setCustomerId(validateUserServiceRequest.getCustomerId());
	userInfo.setUserName(validateUserServiceRequest.getUserName());
	userInfo.setUserID(validateUserServiceRequest.getUserID());
	userInfo.setPassword(validateUserServiceRequest.getPassword());

	requestBody.setUserInfo(userInfo);

	return requestBody;
    }
}
