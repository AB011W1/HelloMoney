package com.barclays.bmg.dao.fundtransfer;

import com.barclays.bmg.service.request.DomesticFundTransferServiceRequest;
import com.barclays.bmg.service.response.DomesticFundTransferServiceResponse;

public interface DomesticFundTransferDAO {

    public DomesticFundTransferServiceResponse makeDomesticFundTransfer(DomesticFundTransferServiceRequest request);
}
