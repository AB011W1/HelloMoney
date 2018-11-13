package com.barclays.bmg.json.model;

import java.io.Serializable;

import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.CreditCardTransactionHistoryDTO;
import com.barclays.bmg.json.response.BMBPayloadData;

public class CreditCardUnbilledTransJSONBean extends BMBPayloadData implements Serializable {

    /**
	 *
	 */
    private static final long serialVersionUID = 1L;
    private CreditCardTransactionHistoryJSONModel crdDetls;

    public CreditCardUnbilledTransJSONBean(CreditCardAccountDTO ccAccDto, CreditCardTransactionHistoryDTO ccHistory) {
	super();
	String currency = ccAccDto.getCurrency();

	if (ccHistory != null) {
	    this.crdDetls = new CreditCardTransactionHistoryJSONModel(ccHistory, currency);

	}

    }

    public CreditCardUnbilledTransJSONBean() {
	super();
    }

    public CreditCardTransactionHistoryJSONModel getCrdDetls() {
	return crdDetls;
    }

    public void setCrdDetls(CreditCardTransactionHistoryJSONModel crdDetls) {
	this.crdDetls = crdDetls;
    }

}