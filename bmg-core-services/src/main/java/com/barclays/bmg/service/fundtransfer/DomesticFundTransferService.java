package com.barclays.bmg.service.fundtransfer;

import com.barclays.bmg.service.request.DomesticFundTransferServiceRequest;
import com.barclays.bmg.service.response.DomesticFundTransferServiceResponse;

public interface DomesticFundTransferService {

    public DomesticFundTransferServiceResponse makeDomesticFundTransfer(DomesticFundTransferServiceRequest request);
}
