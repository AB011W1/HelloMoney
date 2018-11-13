package com.barclays.bmg.service;

import com.barclays.bmg.service.request.InternationalFundTransferServiceRequest;
import com.barclays.bmg.service.response.InternationalFundTransferServiceResponse;

public interface InternationalFundTransferService {

    public InternationalFundTransferServiceResponse makeInternationFundTransfer(InternationalFundTransferServiceRequest request);
}
