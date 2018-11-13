package com.barclays.bmg.operation.response.intrates;

import java.util.List;

import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.IntrateDTO;

public class InterestRatesOperationResponse extends ResponseContext {

    /**
	 *
	 */
    private static final long serialVersionUID = -2577271605869570829L;
    private List<IntrateDTO> IntrateDTOList;
    private Amount minAmount;
    private Amount maxAmount;

    public List<IntrateDTO> getIntrateDTOList() {
	return IntrateDTOList;
    }

    public void setIntrateDTOList(List<IntrateDTO> intrateDTOList) {
	IntrateDTOList = intrateDTOList;
    }

    public Amount getMinAmount() {
	return minAmount;
    }

    public void setMinAmount(Amount minAmount) {
	this.minAmount = minAmount;
    }

    public Amount getMaxAmount() {
	return maxAmount;
    }

    public void setMaxAmount(Amount maxAmount) {
	this.maxAmount = maxAmount;
    }

}
