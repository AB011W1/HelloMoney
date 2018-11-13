package com.barclays.bmg.chequebook.mvc.command;

public class ChequeBookRequestValidationCommand {
	private String actNo;
	private String bkSize;
	private String bkTyp;
	private String brnCde;
	private String brnNam;

	public String getActNo() {
		return actNo;
	}
	public void setActNo(String actNo) {
		this.actNo = actNo;
	}
	public String getBkSize() {
		return bkSize;
	}
	public void setBkSize(String bkSize) {
		this.bkSize = bkSize;
	}
	public String getBkTyp() {
		return bkTyp;
	}
	public void setBkTyp(String bkTyp) {
		this.bkTyp = bkTyp;
	}
	public String getBrnCde() {
		return brnCde;
	}
	public void setBrnCde(String brnCde) {
		this.brnCde = brnCde;
	}
	public String getBrnNam() {
		return brnNam;
	}
	public void setBrnNam(String brnNam) {
		this.brnNam = brnNam;
	}

}
