package com.barclays.bmg.operation.response.fundtransfer.external;

import java.util.List;
import java.util.Map;

import com.barclays.bmg.context.ResponseContext;

public class ExternalFundTransferDataOperationResponse extends ResponseContext {

    private static final long serialVersionUID = -6247883595919538010L;

    private List<String> currLst;
    private Map<String, String> chargeDesc;

    public List<String> getCurrLst() {
	return currLst;
    }

    public void setCurrLst(List<String> currLst) {
	this.currLst = currLst;
    }

    public void addCurrency(String currency) {
	currLst.add(currency);
    }

    public Map<String, String> getChargeDesc() {
	return chargeDesc;
    }

    public void setChargeDesc(Map<String, String> chargeDesc) {
	this.chargeDesc = chargeDesc;
    }

}
