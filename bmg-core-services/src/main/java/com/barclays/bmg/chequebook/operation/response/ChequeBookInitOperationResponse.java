package com.barclays.bmg.chequebook.operation.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.ListValueCacheDTO;

public class ChequeBookInitOperationResponse extends ResponseContext {

    private static final long serialVersionUID = 1448748212062554707L;

    List<ListValueCacheDTO> chqBkTypLst;
    List<ListValueCacheDTO> chqBkSizeLst;

    public List<ListValueCacheDTO> getChqBkTypLst() {
	return chqBkTypLst;
    }

    public void setChqBkTypLst(List<ListValueCacheDTO> chqBkTypLst) {
	this.chqBkTypLst = chqBkTypLst;
    }

    public List<ListValueCacheDTO> getChqBkSizeLst() {
	return chqBkSizeLst;
    }

    public void setChqBkSizeLst(List<ListValueCacheDTO> chqBkSizeLst) {
	this.chqBkSizeLst = chqBkSizeLst;
    }

}
