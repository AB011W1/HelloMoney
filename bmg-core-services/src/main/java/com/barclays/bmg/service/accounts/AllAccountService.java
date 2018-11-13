package com.barclays.bmg.service.accounts;

import com.barclays.bmg.service.accounts.request.AllAccountServiceRequest;
import com.barclays.bmg.service.accounts.response.AllAccountServiceResponse;

public interface AllAccountService {

    public AllAccountServiceResponse retrieveAllAccount(AllAccountServiceRequest request);

    public AllAccountServiceResponse retrieveAccountsFromCBS(AllAccountServiceRequest request);

    public AllAccountServiceResponse retrieveCreditCardList(AllAccountServiceRequest request);
}
