package com.barclays.bmg.json.model.lookup;

import java.io.Serializable;

public class BranchJSONModel implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -2118926772489522842L;

	private String bnkNam;
	private String cty;
	private String brnNam;
	private String bnkCde;
	private String brnCde;


	public String getBnkNam() {
		return bnkNam;
	}
	public void setBnkNam(String bnkNam) {
		this.bnkNam = bnkNam;
	}
	public String getCty() {
		return cty;
	}
	public void setCty(String cty) {
		this.cty = cty;
	}
	public String getBrnNam() {
		return brnNam;
	}
	public void setBrnNam(String brnNam) {
		this.brnNam = brnNam;
	}
	public String getBnkCde() {
		return bnkCde;
	}
	public void setBnkCde(String bnkCde) {
		this.bnkCde = bnkCde;
	}
	public String getBrnCde() {
		return brnCde;
	}
	public void setBrnCde(String brnCde) {
		this.brnCde = brnCde;
	}


}
