package com.barclays.bmg.service.product.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.ListValueCacheDTO;

public class ListValueResByGroupServiceResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = -7922875244042151202L;
    private List<ListValueCacheDTO> listValueCahceDTO;

    public List<ListValueCacheDTO> getListValueCahceDTO() {
	return listValueCahceDTO;
    }

    public void setListValueCahceDTO(List<ListValueCacheDTO> listValueCahceDTO) {
	this.listValueCahceDTO = listValueCahceDTO;
    }
}
