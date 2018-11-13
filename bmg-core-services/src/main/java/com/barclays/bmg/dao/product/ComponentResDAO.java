package com.barclays.bmg.dao.product;

import com.barclays.bmg.service.product.request.ComponentResServiceRequest;
import com.barclays.bmg.service.product.response.ComponentResServiceResponse;

public interface ComponentResDAO {

    public ComponentResServiceResponse getComponentResCache(ComponentResServiceRequest request);
    public String getBillerLabelByKey(String billerId, String businesId, String language);
   /* public String getBillerConfirmLabelByKey(String billerId, String businesId, String language);*/
}
