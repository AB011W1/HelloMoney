package com.barclays.bmg.operation.response.fundtransfer;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CustomerAccountDTO;

public class InternalFTDetailsOperationResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = -8384515465108897653L;
    private String beneAcctNo;
    private String beneName;
    private String payId;
    private CustomerAccountDTO destAcct;

    public String getBeneAcctNo() {
	return beneAcctNo;
    }

    public void setBeneAcctNo(String beneAcctNo) {
	this.beneAcctNo = beneAcctNo;
    }

    public String getBeneName() {
	return beneName;
    }

    public void setBeneName(String beneName) {
	this.beneName = beneName;
    }

    public String getPayId() {
	return payId;
    }

    public void setPayId(String payId) {
	this.payId = payId;
    }

    public CustomerAccountDTO getDestAcct() {
	return destAcct;
    }

    public void setDestAcct(CustomerAccountDTO destAcct) {
	this.destAcct = destAcct;
    }
}