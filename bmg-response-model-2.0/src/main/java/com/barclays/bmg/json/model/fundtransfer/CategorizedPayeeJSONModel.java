package com.barclays.bmg.json.model.fundtransfer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategorizedPayeeJSONModel implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String payCat;

	private List<PayeeJSONModel> bnfLst = new ArrayList<PayeeJSONModel>();



	public String getPayCat() {
		return payCat;
	}
	public void setPayCat(String payCat) {
		this.payCat = payCat;
	}

	public List<PayeeJSONModel> getBnfLst() {
		return bnfLst;
	}
	public void setBnfLst(List<PayeeJSONModel> bnfLst) {
		this.bnfLst = bnfLst;
	}


	public void add(PayeeJSONModel beneficiaryBean)
	{
		bnfLst.add(beneficiaryBean);

	}

}
