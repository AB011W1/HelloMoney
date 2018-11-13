package com.barclays.bmg.service;

import com.barclays.bmg.service.request.RetreivePayeeListServiceRequest;
import com.barclays.bmg.service.response.RetreivePayeeListServiceResponse;

public interface RetreivePayeeListService {

    public RetreivePayeeListServiceResponse retreivePayeeList(RetreivePayeeListServiceRequest retreivePayeeListServiceRequest);
}
