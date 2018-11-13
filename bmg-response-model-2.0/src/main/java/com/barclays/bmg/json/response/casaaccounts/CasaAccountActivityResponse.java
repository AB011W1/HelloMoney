package com.barclays.bmg.json.response.casaaccounts;

import com.barclays.bmg.dto.AccountActivityListDTO;
import com.barclays.bmg.json.response.BMBPayloadData;

public class CasaAccountActivityResponse extends BMBPayloadData {

	private static final long serialVersionUID = -3212219974542575003L;
	private AccountActivityListDTO actActvLst;

	public AccountActivityListDTO getActActvLst() {
		return actActvLst;
	}

	public void setActActvLst(AccountActivityListDTO actActvLst) {
		this.actActvLst = actActvLst;
	}



}
