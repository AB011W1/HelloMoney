package com.barclays.bmg.json.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.dto.InvestmentAccountDTO;
import com.barclays.bmg.json.response.BMBPayloadData;

public class InvestmentAccountDetailsJSONModel extends BMBPayloadData
		implements Serializable {

	private static final long serialVersionUID = -3705315988465277685L;

	private List<InvestmentAccountJSONModel> invActDetLst = new ArrayList<InvestmentAccountJSONModel>();

	// protected String dispNoHistFnd = "No";

	public InvestmentAccountDetailsJSONModel(
			List<InvestmentAccountDTO> accountAccList) {

		// this.invActDetLst = (List<InvestmentAccountJSONModel>) new
		// InvestmentAccountJSONModel(invAccountDTO);

		if (accountAccList != null && accountAccList.size() > 0) {
			for (InvestmentAccountDTO accountActivityDTO : accountAccList) {
				invActDetLst.add(new InvestmentAccountJSONModel(
						accountActivityDTO));
			}

		}
	}

	public InvestmentAccountDetailsJSONModel() {
		super();
	}

	public List<InvestmentAccountJSONModel> getInvActDetLst() {
		return invActDetLst;
	}

	public void setInvActDetLst(List<InvestmentAccountJSONModel> invActDetLst) {
		this.invActDetLst = invActDetLst;
	}

	// public String checkAccountFound() {
	// if (invActDetLst == null || invActDetLst.size() == 0) {
	// dispNoHistFnd = "Yes";
	// }
	// return dispNoHistFnd;
	//
	// }
	//
	// public String getDispNoHistFnd() {
	// if (actActvLst == null || actActvLst.size() == 0) {
	// dispNoHistFnd = "Yes";
	// }
	// return dispNoHistFnd;
	// }

	public void setDispNoHistFnd(String dispNoHistFnd) {

		// this.displayNoHistoryFound = displayNoHistoryFound;
	}
}
