package com.barclays.bmg.service.impl.USSDAuthentication;

import com.barclays.bmg.dao.USSDAuthentication.AuthenticateChangeRequestDao;
import com.barclays.bmg.service.USSDAuthentication.AuthenticateChangeRequestService;
import com.barclays.bmg.service.request.USSDAuthentication.GetChangeRecordsRequest;
import com.barclays.bmg.service.response.USSDAuthentication.AuthenticateChangeServiceResponse;

/**
 * @author Hiral Panchal 
 * This is the service class to fetch the records for authorization
 *
 */
public class AuthenticateChangeRequestServiceImpl implements AuthenticateChangeRequestService {

	private AuthenticateChangeRequestDao authenticateChangeRequestDao;
	
	/**
	 * @return authenticateChangeRequestDao
	 */
	public AuthenticateChangeRequestDao getAuthenticateChangeRequestDao() {
		return authenticateChangeRequestDao;
	}

	/**
	 * @param authenticateChangeRequestDao
	 *            To set AuthenticateChangeRequestDao object
	 * 
	 */
	public void setAuthenticateChangeRequestDao(AuthenticateChangeRequestDao authenticateChangeRequestDao) {
		this.authenticateChangeRequestDao = authenticateChangeRequestDao;
	}

	@Override
	public AuthenticateChangeServiceResponse retrieveChangeRecords(GetChangeRecordsRequest request) {
		return authenticateChangeRequestDao.retrieveChangeRecords(request);
	}

}
