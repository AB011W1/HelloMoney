package com.barclays.bmg.service.impl.USSDAuthentication;

import com.barclays.bmg.dao.USSDAuthentication.UssdUpdateRecordsDao;
import com.barclays.bmg.service.USSDAuthentication.UssdUpdateRecordsService;
import com.barclays.bmg.service.request.USSDAuthentication.UpdateRecordsRequest;
import com.barclays.bmg.service.response.USSDAuthentication.AuthenticateChangeServiceResponse;

/**
 * @author Hiral Panchal 
 * This is the service class to approve/reject the records
 *
 */
public class UssdUpdateRecordsServiceImpl implements UssdUpdateRecordsService {

	private UssdUpdateRecordsDao ussdUpdateRecordsDao;

	/**
	 * @return ussdUpdateRecordsDao
	 */
	public UssdUpdateRecordsDao getUssdUpdateRecordsDao() {
		return ussdUpdateRecordsDao;
	}

	/**
	 * @param ussdUpdateRecordsDao
	 *            To set UssdUpdateRecordsDao object
	 * 
	 */
	public void setUssdUpdateRecordsDao(UssdUpdateRecordsDao ussdUpdateRecordsDao) {
		this.ussdUpdateRecordsDao = ussdUpdateRecordsDao;
	}

	
	@Override
	public AuthenticateChangeServiceResponse updateRecords(UpdateRecordsRequest updateRecordsRequest) {
		return ussdUpdateRecordsDao.updateRecords(updateRecordsRequest);
	}
}
