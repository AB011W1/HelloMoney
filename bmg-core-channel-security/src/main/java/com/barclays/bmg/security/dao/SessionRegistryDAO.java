package com.barclays.bmg.security.dao;

import com.barclays.bmg.security.service.request.SessionRegistryServiceRequest;
import com.barclays.bmg.security.service.response.SessionRegistryListServiceResponse;
import com.barclays.bmg.security.service.response.SessionRegistryServiceResponse;

public interface SessionRegistryDAO {

    public SessionRegistryListServiceResponse findAllPrincipal();

    public SessionRegistryListServiceResponse getSessionUsedByPrincipal(SessionRegistryServiceRequest request);

    public SessionRegistryServiceResponse getSessionByID(SessionRegistryServiceRequest request);

    public void registerNewSession(SessionRegistryServiceRequest request);

    public void removeSession(SessionRegistryServiceRequest request);

    public void updateSession(SessionRegistryServiceRequest request);

}
