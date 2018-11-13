package com.barclays.bmg.dao.product;

import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;
import com.barclays.bmg.service.product.response.ListValueResServiceResponse;

public interface ListValueResDAO {

    public ListValueResServiceResponse findListValueLabel(ListValueResServiceRequest request);

    public ListValueResByGroupServiceResponse findListValueResByGroup(ListValueResServiceRequest request);

    public ListValueResByGroupServiceResponse getListValueByGroupWithFilter(ListValueResServiceRequest request);

    public ListValueResByGroupServiceResponse findListValueResByKey(ListValueResServiceRequest request);
}
