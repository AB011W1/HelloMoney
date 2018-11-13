package com.barclays.bmg.operation.response.internalfundtransfer;

import java.math.BigDecimal;
import java.util.List;

import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.operation.response.billpayment.RetreivePayeeListOperationResponse;

public class InternalFTInitOperationResponse extends RetreivePayeeListOperationResponse {

    /**
	 *
	 */
    private static final long serialVersionUID = 1404209746010956996L;
    private List<? extends CustomerAccountDTO> sourceAccts;
    private BigDecimal txnLimit;

    public List<? extends CustomerAccountDTO> getSourceAccts() {
	return sourceAccts;
    }

    public void setSourceAccts(List<? extends CustomerAccountDTO> sourceAccts) {
	this.sourceAccts = sourceAccts;
    }

    public BigDecimal getTxnLimit() {
	return txnLimit;
    }

    public void setTxnLimit(BigDecimal txnLimit) {
	this.txnLimit = txnLimit;
    }

}
