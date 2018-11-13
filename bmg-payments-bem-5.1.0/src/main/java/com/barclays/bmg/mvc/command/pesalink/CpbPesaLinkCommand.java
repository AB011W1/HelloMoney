package com.barclays.bmg.mvc.command.pesalink;

public class CpbPesaLinkCommand {


	private String amount;
    private String accountNo;
    private String pmtRem;
    private String curr;


	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getPmtRem() {
		return pmtRem;
	}
	public void setPmtRem(String pmtRem) {
		this.pmtRem = pmtRem;
	}
	public String getCurr() {
		return curr;
	}
	public void setCurr(String curr) {
		this.curr = curr;
	}

}
