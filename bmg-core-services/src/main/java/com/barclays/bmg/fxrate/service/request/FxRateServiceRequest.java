package com.barclays.bmg.fxrate.service.request;

import java.math.BigDecimal;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.CustomerAccountDTO;

public class FxRateServiceRequest extends RequestContext {

    private CustomerAccountDTO frmCustActDTO;
    private CustomerAccountDTO toCustActDTO;
    private BigDecimal txnAmt;
    private String txnType;

    public CustomerAccountDTO getFrmCustActDTO() {
	return frmCustActDTO;
    }

    public void setFrmCustActDTO(CustomerAccountDTO frmCustActDTO) {
	this.frmCustActDTO = frmCustActDTO;
    }

    public CustomerAccountDTO getToCustActDTO() {
	return toCustActDTO;
    }

    public void setToCustActDTO(CustomerAccountDTO toCustActDTO) {
	this.toCustActDTO = toCustActDTO;
    }

    public BigDecimal getTxnAmt() {
	return txnAmt;
    }

    public void setTxnAmt(BigDecimal txnAmt) {
	this.txnAmt = txnAmt;
    }

    public String getTxnType() {
	return txnType;
    }

    public void setTxnType(String txnType) {
	this.txnType = txnType;
    }

}
