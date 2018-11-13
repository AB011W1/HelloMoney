package com.barclays.bmg.service.product.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.ComponentResCacheDTO;

public class ComponentResServiceResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = 9053913016822526862L;
    private ComponentResCacheDTO componentResCacheDTO;

    public ComponentResCacheDTO getComponentResCacheDTO() {
	return componentResCacheDTO;
    }

    public void setComponentResCacheDTO(ComponentResCacheDTO componentResCacheDTO) {
	this.componentResCacheDTO = componentResCacheDTO;
    }
}