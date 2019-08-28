package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.PostAuthenticationServiceRequest;
import com.barclays.grcb.mcfe.ssc.authentication.AuthenticationHeader.AuthReqHeader;

public class PostAuthenticationHeaderAdapter {

    public AuthReqHeader buildAuthReqHeader(WorkContext workContext) {
	AuthReqHeader reqHeader = new AuthReqHeader();

	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	PostAuthenticationServiceRequest authenticationServiceRequest = (PostAuthenticationServiceRequest) args[0];

	Context context = authenticationServiceRequest.getContext();

	reqHeader.setBusinessID(context.getBusinessId());
	// reqHeader.setSystemID(context.getSystemId());
	/**
	 * Set system id as IB for login flow as BOC don't have user for MB
	 */
	reqHeader.setSystemID("IB");
	reqHeader.setUserID(context.getUserId());
	reqHeader.setLanguageId(context.getLanguageId());
	reqHeader.setActivityID(context.getActivityId());
	// reqHeader.setActivityRefNo(workContext.getActivityRefNo());
	reqHeader.setCountryCode(context.getCountryCode());
	// reqHeader.setBusinessID("GHBRB");
	// reqHeader.setSystemID("IB");
	// reqHeader.setUserID("ghpct49");
	// reqHeader.setLanguageId("EN");
	// reqHeader.setActivityID("SEC_LOGIN");
	// reqHeader.setActivityRefNo("111111111111");
	// reqHeader.setCountryCode("GH");

	return reqHeader;
    }

}
