package com.barclays.bmg.json.model.billpayment;

import java.io.Serializable;

public class DeliveryTypeJSONModel implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 94435079304150462L;

	private String delTypCde;
	private String delTypLbl;
	public String getDelTypCde() {
		return delTypCde;
	}
	public void setDelTypCde(String delTypCde) {
		this.delTypCde = delTypCde;
	}
	public String getDelTypLbl() {
		return delTypLbl;
	}
	public void setDelTypLbl(String delTypLbl) {
		this.delTypLbl = delTypLbl;
	}



}
