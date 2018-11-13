package com.barclays.bmg.operation.accountdetails.response;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.AccountTrnxHistoryDTO;

/**
 * Mini Stetement
 * 
 */
public class CasaTransactionTrnxHistoryOperationResponse extends ResponseContext {

    private AccountTrnxHistoryDTO accountTrnxHistoryDTO;

    public AccountTrnxHistoryDTO getAccountTrnxHistoryDTO() {
	return accountTrnxHistoryDTO;
    }

    public void setAccountTrnxHistoryDTO(AccountTrnxHistoryDTO accountTrnxHistoryDTO) {
	this.accountTrnxHistoryDTO = accountTrnxHistoryDTO;
    }

}
