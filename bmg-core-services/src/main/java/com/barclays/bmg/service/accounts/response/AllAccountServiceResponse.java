package com.barclays.bmg.service.accounts.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CustomerAccountDTO;

public class AllAccountServiceResponse extends ResponseContext {
    /**
	 *
	 */
    private static final long serialVersionUID = -8373700959850087079L;
    private List<? extends CustomerAccountDTO> accountList;

    public List<? extends CustomerAccountDTO> getAccountList() {
	return accountList;
    }

    public void setAccountList(List<? extends CustomerAccountDTO> accountList) {
	this.accountList = accountList;
    }
}