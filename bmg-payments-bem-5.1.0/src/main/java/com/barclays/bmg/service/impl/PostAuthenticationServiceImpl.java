package com.barclays.bmg.service.impl;

import com.barclays.bmg.dao.PostAuthenticationDAO;
import com.barclays.bmg.service.PostAuthenticationService;
import com.barclays.bmg.service.request.PostAuthenticationServiceRequest;
import com.barclays.bmg.service.response.PostAuthenticationServiceResponse;

/**
 * post authentication service implementor
 * 
 * @author e20026338
 * 
 */
public class PostAuthenticationServiceImpl implements PostAuthenticationService {

    PostAuthenticationDAO postAuthenticationDAO;

    public PostAuthenticationServiceResponse afterLoginSuccess(PostAuthenticationServiceRequest request) {

	PostAuthenticationServiceResponse postAuthenticationServiceResponse = postAuthenticationDAO.afterLoginSuccess(request);

	return postAuthenticationServiceResponse;
    }

    public PostAuthenticationDAO getPostAuthenticationDAO() {
	return postAuthenticationDAO;
    }

    public void setPostAuthenticationDAO(PostAuthenticationDAO postAuthenticationDAO) {
	this.postAuthenticationDAO = postAuthenticationDAO;
    }

}
