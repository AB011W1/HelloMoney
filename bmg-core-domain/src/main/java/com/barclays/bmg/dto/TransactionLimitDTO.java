/**
 *
 */
package com.barclays.bmg.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/* *************************** Revision History *********************************
 * Version        Author          Date                     Description
 * 0.1            Jason        27 Sep 2009                  Initial version
 *
 *
 ********************************************************************************/

/**
 * TODO Class/Interface description.
 * 
 * @author Jason
 */
public class TransactionLimitDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2L;

    private String customerId;
    private String systemId;
    private String businessId;
    private String currency;
    private String transactionLimitType;
    private Date transactionDate;
    private BigDecimal transactionLimit;
    private BigDecimal dailyLimit;
    private BigDecimal thresholdLimit;
    private BigDecimal avalidDailyLimit;

    /**
     * @return the customerId
     */
    public String getCustomerId() {
	return customerId;
    }

    /**
     * @param customerId
     *            the customerId to set
     */
    public void setCustomerId(String customerId) {
	this.customerId = customerId;
    }

    /**
     * @return the systemId
     */
    public String getSystemId() {
	return systemId;
    }

    /**
     * @param systemId
     *            the systemId to set
     */
    public void setSystemId(String systemId) {
	this.systemId = systemId;
    }

    /**
     * @return the businessId
     */
    public String getBusinessId() {
	return businessId;
    }

    /**
     * @param businessId
     *            the businessId to set
     */
    public void setBusinessId(String businessId) {
	this.businessId = businessId;
    }

    /**
     * @return the currency
     */
    public String getCurrency() {
	return currency;
    }

    /**
     * @param currency
     *            the currency to set
     */
    public void setCurrency(String currency) {
	this.currency = currency;
    }

    /**
     * @return the transactionLimitType
     */
    public String getTransactionLimitType() {
	return transactionLimitType;
    }

    /**
     * @param transactionLimitType
     *            the transactionLimitType to set
     */
    public void setTransactionLimitType(String transactionLimitType) {
	this.transactionLimitType = transactionLimitType;
    }

    /**
     * @return the transactionDate
     */
    public Date getTransactionDate() {
	if (transactionDate != null) {
	    return new Date(transactionDate.getTime());
	}
	return null;
    }

    /**
     * @param transactionDate
     *            the transactionDate to set
     */
    public void setTransactionDate(Date transactionDate) {
	if (transactionDate != null) {
	    this.transactionDate = new Date(transactionDate.getTime());
	} else {
	    this.transactionDate = null;
	}
    }

    /**
     * @return the transactionLimit
     */
    public BigDecimal getTransactionLimit() {
	return transactionLimit;
    }

    /**
     * @param transactionLimit
     *            the transactionLimit to set
     */
    public void setTransactionLimit(BigDecimal transactionLimit) {
	this.transactionLimit = transactionLimit;
    }

    /**
     * @return the dailyLimit
     */
    public BigDecimal getDailyLimit() {
	return dailyLimit;
    }

    /**
     * @param dailyLimit
     *            the dailyLimit to set
     */
    public void setDailyLimit(BigDecimal dailyLimit) {
	this.dailyLimit = dailyLimit;
    }

    /**
     * @return the thresholdLimit
     */
    public BigDecimal getThresholdLimit() {
	return thresholdLimit;
    }

    /**
     * @param thresholdLimit
     *            the thresholdLimit to set
     */
    public void setThresholdLimit(BigDecimal thresholdLimit) {
	this.thresholdLimit = thresholdLimit;
    }

    /**
     * @return the avalidDailyLimit
     */
    public BigDecimal getAvalidDailyLimit() {
	return avalidDailyLimit;
    }

    /**
     * @param avalidDailyLimit
     *            the avalidDailyLimit to set
     */
    public void setAvalidDailyLimit(BigDecimal avalidDailyLimit) {
	this.avalidDailyLimit = avalidDailyLimit;
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
	result = prime * result + ((avalidDailyLimit == null) ? 0 : avalidDailyLimit.hashCode());
	result = prime * result + ((businessId == null) ? 0 : businessId.hashCode());
	result = prime * result + ((currency == null) ? 0 : currency.hashCode());
	result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
	result = prime * result + ((dailyLimit == null) ? 0 : dailyLimit.hashCode());
	result = prime * result + ((systemId == null) ? 0 : systemId.hashCode());
	result = prime * result + ((thresholdLimit == null) ? 0 : thresholdLimit.hashCode());
	result = prime * result + ((transactionDate == null) ? 0 : transactionDate.hashCode());
	result = prime * result + ((transactionLimit == null) ? 0 : transactionLimit.hashCode());
	result = prime * result + ((transactionLimitType == null) ? 0 : transactionLimitType.hashCode());
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
	TransactionLimitDTO other = (TransactionLimitDTO) obj;
	if (avalidDailyLimit == null) {
	    if (other.avalidDailyLimit != null) {
		return false;
	    }
	} else if (!avalidDailyLimit.equals(other.avalidDailyLimit)) {
	    return false;
	}
	if (businessId == null) {
	    if (other.businessId != null) {
		return false;
	    }
	} else if (!businessId.equals(other.businessId)) {
	    return false;
	}
	if (currency == null) {
	    if (other.currency != null) {
		return false;
	    }
	} else if (!currency.equals(other.currency)) {
	    return false;
	}
	if (customerId == null) {
	    if (other.customerId != null) {
		return false;
	    }
	} else if (!customerId.equals(other.customerId)) {
	    return false;
	}
	if (dailyLimit == null) {
	    if (other.dailyLimit != null) {
		return false;
	    }
	} else if (!dailyLimit.equals(other.dailyLimit)) {
	    return false;
	}
	if (systemId == null) {
	    if (other.systemId != null) {
		return false;
	    }
	} else if (!systemId.equals(other.systemId)) {
	    return false;
	}
	if (thresholdLimit == null) {
	    if (other.thresholdLimit != null) {
		return false;
	    }
	} else if (!thresholdLimit.equals(other.thresholdLimit)) {
	    return false;
	}
	if (transactionDate == null) {
	    if (other.transactionDate != null) {
		return false;
	    }
	} else if (!transactionDate.equals(other.transactionDate)) {
	    return false;
	}
	if (transactionLimit == null) {
	    if (other.transactionLimit != null) {
		return false;
	    }
	} else if (!transactionLimit.equals(other.transactionLimit)) {
	    return false;
	}
	if (transactionLimitType == null) {
	    if (other.transactionLimitType != null) {
		return false;
	    }
	} else if (!transactionLimitType.equals(other.transactionLimitType)) {
	    return false;
	}
	return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("thresholdLimit");
	sb.append(thresholdLimit);
	sb.append("transactionLimit");
	sb.append(transactionLimit);
	sb.append("dailyLimit");
	sb.append(dailyLimit);
	sb.append("avalidDailyLimit");
	sb.append(avalidDailyLimit);
	return sb.toString();
    }

}
