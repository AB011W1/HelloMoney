package com.barclays.bmg.service.response;

import java.util.Map;

import com.barclays.bmg.context.ResponseContext;

public class RetrieveIndCustBySCVIDServiceResponse extends ResponseContext {

    private Map<String, String> ppMap;
    private String firstName;
    public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	private String lastName;

    public Map<String, String> getPpMap() {
	return ppMap;
    }

    public void setPpMap(Map<String, String> ppMap) {
	this.ppMap = ppMap;
    }

}
