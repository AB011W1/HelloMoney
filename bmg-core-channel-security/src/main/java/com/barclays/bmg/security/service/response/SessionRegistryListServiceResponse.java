package com.barclays.bmg.security.service.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.security.dto.SessionInformationDTO;

public class SessionRegistryListServiceResponse extends ResponseContext {

    List<SessionInformationDTO> ssnInfoList;

    public List<SessionInformationDTO> getSsnList() {
	return ssnInfoList;
    }

    public void setSsnList(List<SessionInformationDTO> ssnInfoList) {
	this.ssnInfoList = ssnInfoList;
    }

}
