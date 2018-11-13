package com.barclays.ussd.sysprefs.services;


import java.util.List;

public class ListValueResByGroupServiceResponse  {

	private List<ListValueCacheDTO> listValueCahceDTO;
    private ListValueCacheDTO listValueCacheDTOOneRow;
	public List<ListValueCacheDTO> getListValueCahceDTO() {
		return listValueCahceDTO;
	}

	public void setListValueCahceDTO(List<ListValueCacheDTO> listValueCahceDTO) {
		this.listValueCahceDTO = listValueCahceDTO;
	}

	public void setListValueCacheDTOOneRow(ListValueCacheDTO listValueCacheDTOOneRow) {
		this.listValueCacheDTOOneRow = listValueCacheDTOOneRow;
	}

	public ListValueCacheDTO getListValueCacheDTOOneRow() {
		return listValueCacheDTOOneRow;
	}
}
