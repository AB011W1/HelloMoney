package com.barclays.bmg.json.model;

import com.barclays.bmg.json.response.BMBPayloadData;

public class ApplyProductConfirmationJsonModel extends BMBPayloadData{

	private static final long serialVersionUID = 4269573888549715073L;

	private String caseNumber;

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}
	public String getCaseNumber() {
		return caseNumber;
	}


}
