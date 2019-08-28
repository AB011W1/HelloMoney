package com.barclays.bmg.dao.accounts;

import com.barclays.bmg.service.accounts.request.AllGroupWalletAccountServiceRequest;
import com.barclays.bmg.service.accounts.response.AllGroupWalletAccountServiceResponse;

public interface AllGroupWalletAccountDAO {
	public AllGroupWalletAccountServiceResponse retrieveAllGroupWalletAccount(AllGroupWalletAccountServiceRequest request);
}
