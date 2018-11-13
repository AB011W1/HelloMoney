package com.barclays.bmg.service.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.SystemParameterDTO;

public class SystemParameterListServiceResponse extends ResponseContext {
    /**
	 *
	 */
    private static final long serialVersionUID = 4066285917983862629L;
    private List<SystemParameterDTO> systemParameterDTOList;

    public List<SystemParameterDTO> getSystemParameterDTOList() {
	return systemParameterDTOList;
    }

    public void setSystemParameterDTOList(List<SystemParameterDTO> systemParameterDTOList) {
	this.systemParameterDTOList = systemParameterDTOList;
    }

}
