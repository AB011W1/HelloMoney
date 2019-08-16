package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.ValidateUserServiceRequest;
import com.barclays.bmg.utils.BMBCommonUtility;
import com.barclays.grcb.mcfe.ssc.authentication.AuthenticationHeader.AuthReqHeader;

public class ValidateUserHeaderAdapter {

    public AuthReqHeader buildAuthReqHeader(WorkContext workContext) {
	AuthReqHeader reqHeader = new AuthReqHeader();

	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	ValidateUserServiceRequest validateUserServiceRequest = (ValidateUserServiceRequest) args[0];

	Context context = validateUserServiceRequest.getContext();

	reqHeader.setBusinessID(context.getBusinessId());
	// reqHeader.setSystemID(context.getSystemId());
	/**
	 * Set system id as IB for login flow
	 */
	reqHeader.setSystemID(CommonConstants.IB_SYSTEM_ID);
	reqHeader.setUserID(context.getUserId());
	reqHeader.setLanguageId(context.getLanguageId());
	reqHeader.setActivityID(context.getActivityId());
	reqHeader.setActivityRefNo(BMBCommonUtility.generateRefNo());
	reqHeader.setCountryCode(context.getCountryCode());

	return reqHeader;
    }

}
