package com.barclays.bmg.operation.response.fundtransfer;

import java.math.BigDecimal;
import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CustomerAccountDTO;

public class FundTransferInitOperationResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = 4710292152653529530L;
    private List<? extends CustomerAccountDTO> sourceAccountList;
    private List<? extends CustomerAccountDTO> destAccountList;
    private BigDecimal txnLimit;

    public List<? extends CustomerAccountDTO> getSourceAccountList() {
	return sourceAccountList;
    }

    public void setSourceAccountList(List<? extends CustomerAccountDTO> sourceAccountList) {
	this.sourceAccountList = sourceAccountList;
    }

    public List<? extends CustomerAccountDTO> getDestAccountList() {
	return destAccountList;
    }

    public void setDestAccountList(List<? extends CustomerAccountDTO> destAccountList) {
	this.destAccountList = destAccountList;
    }

    public BigDecimal getTxnLimit() {
	return txnLimit;
    }

    public void setTxnLimit(BigDecimal txnLimit) {
	this.txnLimit = txnLimit;
    }

}
