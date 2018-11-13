package com.barclays.bmg.dao;

import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.SystemParameterListServiceResponse;
import com.barclays.bmg.service.response.SystemParameterServiceResponse;

public interface SystemParameterDAO {

    public SystemParameterListServiceResponse getSysParamByActivityId(SystemParameterServiceRequest request);

    public SystemParameterServiceResponse getSystemParameter(SystemParameterServiceRequest request);

    public SystemParameterServiceResponse getStatusParameter(SystemParameterServiceRequest request);

}
