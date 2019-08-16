package com.barclays.bmg.service.accounts.impl;

import com.barclays.bmg.dao.accounts.AllGroupWalletAccountDAO;
import com.barclays.bmg.dao.product.ListValueResDAO;
import com.barclays.bmg.service.accounts.AllGroupWalletAccountService;
import com.barclays.bmg.service.accounts.request.AllGroupWalletAccountServiceRequest;
import com.barclays.bmg.service.accounts.response.AllGroupWalletAccountServiceResponse;

public class AllGroupWalletAccountServiceImpl implements
		AllGroupWalletAccountService {
	AllGroupWalletAccountDAO allGroupWalletAccountDAO;
	ListValueResDAO listValueResDAO;

	public void setAllGroupWalletAccountDAO(
			AllGroupWalletAccountDAO allGroupWalletAccountDAO) {
		this.allGroupWalletAccountDAO = allGroupWalletAccountDAO;
	}

	public void setListValueResDAO(ListValueResDAO listValueResDAO) {
		this.listValueResDAO = listValueResDAO;
	}

	public ListValueResDAO getListValueResDAO() {
		return listValueResDAO;
	}

	@Override
	public AllGroupWalletAccountServiceResponse retrieveAllGroupWalletAccount(
			AllGroupWalletAccountServiceRequest request) {
		// TODO Auto-generated method stub
		AllGroupWalletAccountServiceResponse allGroupWalletAccountServiceResponse = allGroupWalletAccountDAO
				.retrieveAllGroupWalletAccount(request);
		return allGroupWalletAccountServiceResponse;
	}

}