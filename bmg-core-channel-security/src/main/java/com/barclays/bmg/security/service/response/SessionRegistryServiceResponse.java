package com.barclays.bmg.security.service.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.security.dto.SessionInformationDTO;

public class SessionRegistryServiceResponse extends ResponseContext {

    private SessionInformationDTO ssnInfo;

    public SessionInformationDTO getSsnInfo() {
	return ssnInfo;
    }

    public void setSsnInfo(SessionInformationDTO ssnInfo) {
	this.ssnInfo = ssnInfo;
    }

}
