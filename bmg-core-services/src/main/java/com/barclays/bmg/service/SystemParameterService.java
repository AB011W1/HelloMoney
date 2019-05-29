package com.barclays.bmg.service;

import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.SystemParameterListServiceResponse;
import com.barclays.bmg.service.response.SystemParameterServiceResponse;

public interface SystemParameterService {

    public SystemParameterListServiceResponse getSysParamByActivityId(SystemParameterServiceRequest request);

    public SystemParameterServiceResponse getSystemParameter(SystemParameterServiceRequest request);

    public SystemParameterServiceResponse getStatusParameter(SystemParameterServiceRequest request);

    public SystemParameterListServiceResponse getCountryWiseSysParamsByParamId(SystemParameterServiceRequest request);

}