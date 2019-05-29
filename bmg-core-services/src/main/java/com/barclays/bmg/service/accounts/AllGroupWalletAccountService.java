package com.barclays.bmg.service.accounts;

import com.barclays.bmg.service.accounts.request.AllGroupWalletAccountServiceRequest;
import com.barclays.bmg.service.accounts.response.AllGroupWalletAccountServiceResponse;

public interface AllGroupWalletAccountService {
	public AllGroupWalletAccountServiceResponse retrieveAllGroupWalletAccount(AllGroupWalletAccountServiceRequest request);
}
