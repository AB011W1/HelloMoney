package com.barclays.bmg.service.product.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.EntitlementDTO;

public class EntitlementServiceResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = -654149904390492384L;
    private EntitlementDTO entitlementDTO;

    public EntitlementDTO getEntitlementDTO() {
	return entitlementDTO;
    }

    public void setEntitlementDTO(EntitlementDTO entitlementDTO) {
	this.entitlementDTO = entitlementDTO;
    }
}
