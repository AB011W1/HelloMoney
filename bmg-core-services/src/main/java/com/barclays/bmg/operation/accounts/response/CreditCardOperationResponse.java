package com.barclays.bmg.operation.accounts.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CustomerAccountDTO;

public class CreditCardOperationResponse extends ResponseContext {
    /**
	 *
	 */
    private static final long serialVersionUID = 418503812176260565L;

    private List<? extends CustomerAccountDTO> creditCardList;

    public List<? extends CustomerAccountDTO> getCreditCardList() {
	return creditCardList;
    }

    public void setCreditCardList(List<? extends CustomerAccountDTO> creditCardList) {
	this.creditCardList = creditCardList;
    }

}
