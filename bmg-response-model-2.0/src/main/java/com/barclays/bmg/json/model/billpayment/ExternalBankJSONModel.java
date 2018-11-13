package com.barclays.bmg.json.model.billpayment;

import java.io.Serializable;

public class ExternalBankJSONModel implements Serializable {

	private static final long serialVersionUID = -1587709167869395102L;

	private String bnkNam;
	private String swftCde;
	private String addr;
	private String cntry;
	private String clrCode;
	private String clrNetCode;
	private String brnchNam;
	private String trnsferChanl;
	private String ctrCde;

	public String getBnkNam() {
		return bnkNam;
	}

	public void setBnkNam(String bnkNam) {
		this.bnkNam = bnkNam;
	}

	public String getSwftCde() {
		return swftCde;
	}

	public void setSwftCde(String swftCde) {
		this.swftCde = swftCde;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getCntry() {
		return cntry;
	}

	public void setCntry(String cntry) {
		this.cntry = cntry;
	}

	public String getClrCode() {
		return clrCode;
	}

	public void setClrCode(String clrCode) {
		this.clrCode = clrCode;
	}

	public String getClrNetCode() {
		return clrNetCode;
	}

	public void setClrNetCode(String clrNetCode) {
		this.clrNetCode = clrNetCode;
	}

	public String getBrnchNam() {
		return brnchNam;
	}

	public void setBrnchNam(String brnchNam) {
		this.brnchNam = brnchNam;
	}

	public String getTrnsferChanl() {
		return trnsferChanl;
	}

	public void setTrnsferChanl(String trnsferChanl) {
		this.trnsferChanl = trnsferChanl;
	}

	public String getCtrCde() {
		return ctrCde;
	}

	public void setCtrCde(String ctrCde) {
		this.ctrCde = ctrCde;
	}

}
