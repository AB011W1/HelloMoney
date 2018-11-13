package com.barclays.bmg.service;

import com.barclays.bmg.service.request.RetreiveIndvCustInfoServiceRequest;
import com.barclays.bmg.service.response.RetreiveIndvCustInfoServiceResponse;

public interface RetreiveIndvCustInfoService {

    public RetreiveIndvCustInfoServiceResponse retreiveIndvCustByCCNumber(RetreiveIndvCustInfoServiceRequest request);
}
