package com.barclays.bmg.service.request;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.SystemParameterDTO;

public class SystemParameterServiceRequest extends RequestContext {
    private SystemParameterDTO systemParameterDTO;

    public SystemParameterDTO getSystemParameterDTO() {
	return systemParameterDTO;
    }

    public void setSystemParameterDTO(SystemParameterDTO systemParameterDTO) {
	this.systemParameterDTO = systemParameterDTO;
    }

}
