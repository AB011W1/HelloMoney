package com.barclays.bmg.json.response.accountsummary;

import java.util.List;

import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.json.response.BMBPayloadData;

public class AccountSummaryResponse extends BMBPayloadData {
    private static final long serialVersionUID = -7313924997595479905L;
    private List<? extends CustomerAccountDTO> accountList;

    public List<? extends CustomerAccountDTO> getAccountList() {
	return accountList;
    }

    public void setAccountList(List<? extends CustomerAccountDTO> accountList) {
	this.accountList = accountList;
    }

}
