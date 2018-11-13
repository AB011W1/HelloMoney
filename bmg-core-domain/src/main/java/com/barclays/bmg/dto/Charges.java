package com.barclays.bmg.dto;

import java.io.Serializable;
import java.util.List;

public class Charges implements Serializable {

    private static final long serialVersionUID = -5363578603896183244L;

    private Amount totalFeeAmount;
    private List<Charge> chargeDetails;

    public Charges() {
	super();
    }

    /**
     * @param totalFeeAmount
     * @param chargeDetails
     */
    public Charges(Amount totalFeeAmount, List<Charge> chargeDetails) {
	super();
	this.totalFeeAmount = totalFeeAmount;
	this.chargeDetails = chargeDetails;
    }

    /**
     * @return the totalFeeAmount
     */
    public Amount getTotalFeeAmount() {
	return totalFeeAmount;
    }

    /**
     * @param totalFeeAmount
     *            the totalFeeAmount to set
     */
    public void setTotalFeeAmount(Amount totalFeeAmount) {
	this.totalFeeAmount = totalFeeAmount;
    }

    /**
     * @return the chargeDetails
     */
    public List<Charge> getChargeDetails() {
	return chargeDetails;
    }

    /**
     * @param chargeDetails
     *            the chargeDetails to set
     */
    public void setChargeDetails(List<Charge> chargeDetails) {
	this.chargeDetails = chargeDetails;
    }

}
