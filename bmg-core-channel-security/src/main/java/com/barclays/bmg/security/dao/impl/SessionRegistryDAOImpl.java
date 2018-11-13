package com.barclays.bmg.security.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.bmg.dao.impl.BaseDAOImpl;
import com.barclays.bmg.security.constants.SqlMapSecurityConstants;
import com.barclays.bmg.security.dao.SessionRegistryDAO;
import com.barclays.bmg.security.dto.SessionInformationDTO;
import com.barclays.bmg.security.service.request.SessionRegistryServiceRequest;
import com.barclays.bmg.security.service.response.SessionRegistryListServiceResponse;
import com.barclays.bmg.security.service.response.SessionRegistryServiceResponse;

public class SessionRegistryDAOImpl extends BaseDAOImpl implements SessionRegistryDAO {

    @Override
    public SessionRegistryListServiceResponse findAllPrincipal() {
	SessionRegistryListServiceResponse sessionRegistryListServiceResponse = new SessionRegistryListServiceResponse();
	List<SessionInformationDTO> ssnInfoList = this.queryForList(SqlMapSecurityConstants.FIND_ALL_PRINCIPAL);
	sessionRegistryListServiceResponse.setSsnList(ssnInfoList);
	return sessionRegistryListServiceResponse;

    }

    @Override
    public SessionRegistryServiceResponse getSessionByID(SessionRegistryServiceRequest request) {
	Map<String, String> parameterMap = new HashMap<String, String>();
	SessionRegistryServiceResponse sessionRegistryServiceResponse = new SessionRegistryServiceResponse();
	parameterMap.put("sessionID", request.getSessionID());
	SessionInformationDTO ssnInfo = (SessionInformationDTO) this.queryForObject(SqlMapSecurityConstants.GET_SESSION_BY_ID, parameterMap);
	sessionRegistryServiceResponse.setSsnInfo(ssnInfo);
	return sessionRegistryServiceResponse;
    }

    @Override
    public SessionRegistryListServiceResponse getSessionUsedByPrincipal(SessionRegistryServiceRequest request) {
	Map<String, String> parameterMap = new HashMap<String, String>();
	SessionRegistryListServiceResponse sessionRegistryListServiceResponse = new SessionRegistryListServiceResponse();
	parameterMap.put("principalID", request.getPrincipalID());
	List<SessionInformationDTO> ssnInfoList = this.queryForList(
		SqlMapSecurityConstants.GET_SESSION_USED_BY_PRINCIPAL, parameterMap);
	sessionRegistryListServiceResponse.setSsnList(ssnInfoList);
	return sessionRegistryListServiceResponse;
    }

    @Override
    public void registerNewSession(SessionRegistryServiceRequest request) {
	Map<String, Object> parameterMap = new HashMap<String, Object>();
	parameterMap.put("sessionID", request.getSessionID());
	parameterMap.put("principalID", request.getPrincipalID());
	parameterMap.put("lastRequestDate", request.getLastRequestDate());
	parameterMap.put("expiredFlag", request.isExpiredFlag());
	this.insert(SqlMapSecurityConstants.REGISTER_NEW_SESSION, parameterMap);

    }

    @Override
    public void removeSession(SessionRegistryServiceRequest request) {
	// TODO Auto-generated method stub

    }

    @Override
    public void updateSession(SessionRegistryServiceRequest request) {
	SessionInformationDTO ssnInfo = new SessionInformationDTO();
	ssnInfo.setExpiredFlag(request.isExpiredFlag());
	ssnInfo.setLastRequestDate(request.getLastRequestDate());
	ssnInfo.setPrincipalID(request.getPrincipalID());
	ssnInfo.setSessionID(request.getSessionID());

	this.update(SqlMapSecurityConstants.UPDATE_SESSION, ssnInfo);

    }

}
