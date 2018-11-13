package com.barclays.bmg.service.sessionactivity.impl;

import com.barclays.bmg.service.sessionactivity.SessionActivityPersistenceService;
import com.barclays.bmg.service.sessionactivity.dao.SessionActivityDAO;
import com.barclays.bmg.service.sessionactivity.dao.impl.SessionActivityDAOImpl;
import com.barclays.bmg.service.sessionactivity.dto.SessionActivityDTO;

public class SessionActivityPersistenceSvcImpl implements
		SessionActivityPersistenceService {

	private SessionActivityDAO sessionActivityDAO;

	@Override
	public void persistSessionActivity(SessionActivityDTO sessionActivityDTO) {
		sessionActivityDAO.insert(sessionActivityDTO);
	}

	public SessionActivityDAO getSessionActivityDAO() {
		return sessionActivityDAO;
	}

	public void setSessionActivityDAO(SessionActivityDAO sessionActivityDAO) {
		this.sessionActivityDAO = sessionActivityDAO;
	}


}
