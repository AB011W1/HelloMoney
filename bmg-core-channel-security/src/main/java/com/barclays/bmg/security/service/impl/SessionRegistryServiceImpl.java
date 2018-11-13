package com.barclays.bmg.security.service.impl;

import java.util.List;

import com.barclays.bmg.security.dao.SessionRegistryDAO;
import com.barclays.bmg.security.dto.SessionInformationDTO;
import com.barclays.bmg.security.service.SessionRegistryService;
import com.barclays.bmg.security.service.request.SessionRegistryServiceRequest;
import com.barclays.bmg.security.service.response.SessionRegistryListServiceResponse;
import com.barclays.bmg.security.service.response.SessionRegistryServiceResponse;

public class SessionRegistryServiceImpl implements SessionRegistryService {

    SessionRegistryDAO sessionRegistryDAO;

    @Override
    public SessionRegistryListServiceResponse findAllPrincipal() {
	SessionRegistryListServiceResponse sessionRegistryListServiceResponse = sessionRegistryDAO.findAllPrincipal();
	return sessionRegistryListServiceResponse;
    }

    @Override
    public SessionRegistryServiceResponse getSessionByID(SessionRegistryServiceRequest request) {
	SessionRegistryServiceResponse sessionRegistryServiceResponse = sessionRegistryDAO.getSessionByID(request);
	return sessionRegistryServiceResponse;
    }

    @Override
    public SessionRegistryListServiceResponse getSessionUsedByPrincipal(SessionRegistryServiceRequest request) {
	SessionRegistryListServiceResponse sessionRegistryListServiceResponse = sessionRegistryDAO.getSessionUsedByPrincipal(request);
	return sessionRegistryListServiceResponse;
    }

    @Override
    public void registerNewSession(SessionRegistryServiceRequest request) {
	SessionRegistryListServiceResponse sessionRegistryListServiceResponse = sessionRegistryDAO.getSessionUsedByPrincipal(request);

	List<SessionInformationDTO> sessions = sessionRegistryListServiceResponse.getSsnList();

	for (Object o : sessions) {
	    SessionRegistryServiceRequest sessionRegistryServiceRequest = new SessionRegistryServiceRequest();
	    SessionInformationDTO session = (SessionInformationDTO) o;
	    sessionRegistryServiceRequest.setExpiredFlag(true);
	    sessionRegistryServiceRequest.setLastRequestDate(session.getLastRequestDate());
	    sessionRegistryServiceRequest.setPrincipalID(session.getPrincipalID());
	    sessionRegistryServiceRequest.setSessionID(session.getSessionID());
	    sessionRegistryDAO.updateSession(sessionRegistryServiceRequest);
	}
	sessionRegistryDAO.registerNewSession(request);

    }

    @Override
    public void removeSession(SessionRegistryServiceRequest request) {
	sessionRegistryDAO.removeSession(request);

    }

    @Override
    public void updateSession(SessionRegistryServiceRequest request) {
	sessionRegistryDAO.updateSession(request);

    }

    public SessionRegistryDAO getSessionRegistryDAO() {
	return sessionRegistryDAO;
    }

    public void setSessionRegistryDAO(SessionRegistryDAO sessionRegistryDAO) {
	this.sessionRegistryDAO = sessionRegistryDAO;
    }

}
