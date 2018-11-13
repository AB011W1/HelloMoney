package com.barclays.bmg.service.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.UrlBusinessMapDTO;

public class UrlBusinessMapServiceResponse extends ResponseContext {
    /**
	 *
	 */
    private static final long serialVersionUID = -1456018417893073307L;
    private UrlBusinessMapDTO urlBusinessMapDTO;

    public UrlBusinessMapDTO getUrlBusinessMapDTO() {
	return urlBusinessMapDTO;
    }

    public void setUrlBusinessMapDTO(UrlBusinessMapDTO urlBusinessMapDTO) {
	this.urlBusinessMapDTO = urlBusinessMapDTO;
    }

}
