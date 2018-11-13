package com.barclays.bmg.operation.offer.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.ListValueCacheDTO;

public class DiningSearchDataOperationResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = 7521667986424221572L;
    List<ListValueCacheDTO> listValueDtoList;

    public List<ListValueCacheDTO> getListValueDtoList() {
	return listValueDtoList;
    }

    public void setListValueDtoList(List<ListValueCacheDTO> listValueDtoList) {
	this.listValueDtoList = listValueDtoList;
    }

}
