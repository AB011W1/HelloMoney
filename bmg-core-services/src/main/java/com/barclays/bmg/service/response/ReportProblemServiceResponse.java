package com.barclays.bmg.service.response;

import com.barclays.bmg.context.ResponseContext;

public class ReportProblemServiceResponse extends ResponseContext {

    private static final long serialVersionUID = 6017509621703548169L;

    private String caseNumber;

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	public String getCaseNumber() {
		return caseNumber;
	}


}
