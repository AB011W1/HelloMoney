package com.barclays.bmg.operation.accounts.response;

import java.math.BigDecimal;
import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.CustomerAccountDTO;

public class AccountSummaryOperationResponse extends ResponseContext {
    /**
	 *
	 */
    private static final long serialVersionUID = 418503812176260565L;
    private List<? extends CustomerAccountDTO> accountList;
    private BigDecimal totalAsset = new BigDecimal(0);
    private BigDecimal totalDebt = new BigDecimal(0);
    private BigDecimal totalNetWorth = new BigDecimal(0);

    public List<? extends CustomerAccountDTO> getAccountList() {
	return accountList;
    }

    public void setAccountList(List<? extends CustomerAccountDTO> accountList) {
	this.accountList = accountList;
    }

    public BigDecimal getTotalAsset() {
	return totalAsset;
    }

    public void setTotalAsset(BigDecimal totalAsset) {
	this.totalAsset = totalAsset;
    }

    public BigDecimal getTotalDebt() {
	return totalDebt;
    }

    public void setTotalDebt(BigDecimal totalDebt) {
	this.totalDebt = totalDebt;
    }

    public BigDecimal getTotalNetWorth() {
	return totalNetWorth;
    }

    public void setTotalNetWorth(BigDecimal totalNetWorth) {
	this.totalNetWorth = totalNetWorth;
    }

}
