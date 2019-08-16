package com.barclays.bmg.dao.accounts;

import com.barclays.bmg.service.accounts.request.AllAccountServiceRequest;
import com.barclays.bmg.service.accounts.response.AllAccountServiceResponse;

public interface AllAccountDAO {

    public AllAccountServiceResponse retrieveAllAccount(AllAccountServiceRequest request);

    public AllAccountServiceResponse retrieveCreditCardList(AllAccountServiceRequest request);

}
