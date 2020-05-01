package com.barclays.bmg.mvc.command.billpayment;

import com.barclays.bmg.utils.BMBCommonUtility;

public class PaymentFormSubmissionCommand {

    private String amt;
    private String frActNo;
    private String pmtRem;
    private String curr;
 	private String crditCardFlag;
	private String actionCode;
	private String storeNumber;
	//Cards Migration
	private String crdList;
	private String crdNo;

    public String getCrdList() {
		return crdList;
	}

	public void setCrdList(String crdList) {
		this.crdList = crdList;
	}

	public String getCrdNo() {
		return crdNo;
	}

	public void setCrdNo(String crdNo) {
		this.crdNo = crdNo;
	}

	public String getAmt() {
	return amt;
    }

    public void setAmt(String amt) {
	this.amt = amt;
    }

    public void setAmount(String amount) {
	this.amt = amount;
    }

    public String getFrActNo() {
	return frActNo;
    }

    public void setFrActNo(String frActNo) {
	this.frActNo = frActNo;
    }

    public void setFromAccountNumber(String fromAccountNumber) {
	this.frActNo = fromAccountNumber;
    }

    public String getPmtRem() {
	pmtRem = BMBCommonUtility.convertStringToUnicode(pmtRem);
	return pmtRem;
    }

    public void setPmtRem(String pmtRem) {
	this.pmtRem = pmtRem;
    }

    public void setPaymentRemarks(String paymentRemarks) {
	this.pmtRem = paymentRemarks;
    }

    public String getCurr() {
	return curr;
    }

    public void setCurr(String curr) {
	this.curr = curr;
    }

	public String getCrditCardFlag() {
		return crditCardFlag;
	}

	public void setCrditCardFlag(String crditCardFlag) {
		this.crditCardFlag = crditCardFlag;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}

}
