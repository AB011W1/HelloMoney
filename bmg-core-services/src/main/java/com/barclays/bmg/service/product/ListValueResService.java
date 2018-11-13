package com.barclays.bmg.service.product;

import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;
import com.barclays.bmg.service.product.response.ListValueResServiceResponse;

public interface ListValueResService {

    public ListValueResServiceResponse getListValueRes(ListValueResServiceRequest request);

    public ListValueResByGroupServiceResponse getListValueByGroup(ListValueResServiceRequest request);

    public ListValueResByGroupServiceResponse getListValueByGroupWithFilter(ListValueResServiceRequest request);

    public ListValueResByGroupServiceResponse getListValueByKey(ListValueResServiceRequest request);
}
