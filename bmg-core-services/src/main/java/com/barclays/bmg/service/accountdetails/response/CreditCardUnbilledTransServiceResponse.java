package com.barclays.bmg.service.accountdetails.response;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CreditCardActivityDTO;

public class CreditCardUnbilledTransServiceResponse extends ResponseContext {

    private List<CreditCardActivityDTO> creditCardActivityDTOList;
    // START
    private String creditCardAccountNumber;

    public String getCreditCardAccountNumber() {
	return creditCardAccountNumber;
    }

    // END
    public void setCreditCardAccountNumber(String creditCardAccountNumber) {
	this.creditCardAccountNumber = creditCardAccountNumber;
    }

    public List<CreditCardActivityDTO> getCreditCardActivityDTOList() {
	return creditCardActivityDTOList;
    }

    public void setCreditCardActivityDTOList(List<CreditCardActivityDTO> creditCardActivityDTOList) {
	this.creditCardActivityDTOList = creditCardActivityDTOList;
    }

}
