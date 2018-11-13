package com.barclays.bmg.service.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.SystemParameterDTO;

public class SystemParameterServiceResponse extends ResponseContext {
    /**
	 *
	 */
    private static final long serialVersionUID = 3214163940250841477L;
    SystemParameterDTO systemParameterDTO;

    public SystemParameterDTO getSystemParameterDTO() {
	return systemParameterDTO;
    }

    public void setSystemParameterDTO(SystemParameterDTO systemParameterDTO) {
	this.systemParameterDTO = systemParameterDTO;
    }

}
