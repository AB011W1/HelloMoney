package com.barclays.bmg.dto;

import java.io.Serializable;

public class TransferFacadeDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String transactionFacadeRoutineType;
    private BeneficiaryDTO beneficiary;
    private CustomerAccountDTO toAccount;
    private CustomerAccountDTO fromAccount;
    private boolean addPayeeFlag;

    /**
     * @return the addPayeeFlag
     */
    public boolean isAddPayeeFlag() {
	return addPayeeFlag;
    }

    /**
     * @param addPayeeFlag
     *            the addPayeeFlag to set
     */
    public void setAddPayeeFlag(boolean addPayeeFlag) {
	this.addPayeeFlag = addPayeeFlag;
    }

    /**
     * @return the fromAccount
     */
    public CustomerAccountDTO getFromAccount() {
	return fromAccount;
    }

    /**
     * @param fromAccount
     *            the fromAccount to set
     */
    public void setFromAccount(CustomerAccountDTO fromAccount) {
	this.fromAccount = fromAccount;
    }

    /**
     *
     */
    public TransferFacadeDTO() {
	super();
    }

    /**
     * @return the transactionFacadeRoutineType
     */
    public String getTransactionFacadeRoutineType() {
	return transactionFacadeRoutineType;
    }

    /**
     * @param transactionFacadeRoutineType
     *            the transactionFacadeRoutineType to set
     */
    public void setTransactionFacadeRoutineType(String transactionFacadeRoutineType) {
	this.transactionFacadeRoutineType = transactionFacadeRoutineType;
    }

    /**
     * @return the beneficiary
     */
    public BeneficiaryDTO getBeneficiary() {
	return beneficiary;
    }

    /**
     * @param beneficiary
     *            the beneficiary to set
     */
    public void setBeneficiary(BeneficiaryDTO beneficiary) {
	this.beneficiary = beneficiary;
    }

    /**
     * @return the toAccount
     */
    public CustomerAccountDTO getToAccount() {
	return toAccount;
    }

    /**
     * @param toAccount
     *            the toAccount to set
     */
    public void setToAccount(CustomerAccountDTO toAccount) {
	this.toAccount = toAccount;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((beneficiary == null) ? 0 : beneficiary.hashCode());
	result = prime * result + ((toAccount == null) ? 0 : toAccount.hashCode());
	if (beneficiary != null) {
	    result = prime * result + ((transactionFacadeRoutineType == null) ? 0 : transactionFacadeRoutineType.hashCode());
	}
	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	TransferFacadeDTO other = (TransferFacadeDTO) obj;
	if (null != this.transactionFacadeRoutineType && !other.getTransactionFacadeRoutineType().equals(this.transactionFacadeRoutineType)) {
	    return false;
	}
	if (beneficiary == null) {
	    if (other.beneficiary != null) {
		return false;
	    }
	} else if (!beneficiary.equals(other.beneficiary)) {
	    return false;
	}

	if (toAccount == null) {
	    if (other.toAccount != null) {
		return false;
	    }
	} else if (!toAccount.equals(other.toAccount)) {
	    return false;
	}

	if (beneficiary == null) {
	    if (transactionFacadeRoutineType == null) {
		if (other.transactionFacadeRoutineType != null) {
		    return false;
		}
	    } else if (!transactionFacadeRoutineType.equals(other.transactionFacadeRoutineType)) {
		return false;
	    }
	}
	return true;
    }

}
