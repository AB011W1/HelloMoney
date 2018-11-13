package com.barclays.bmg.service.accountdetails.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.AccountTrnxHistoryDTO;

public class CASAAccountTrnxHistoryServiceResponse extends ResponseContext {

    private AccountTrnxHistoryDTO accountTrnxHistoryDTO;

    public AccountTrnxHistoryDTO getAccountTrnxHistoryDTO() {
	return accountTrnxHistoryDTO;
    }

    public void setAccountTrnxHistoryDTO(AccountTrnxHistoryDTO accountTrnxHistoryDTO) {
	this.accountTrnxHistoryDTO = accountTrnxHistoryDTO;
    }

}