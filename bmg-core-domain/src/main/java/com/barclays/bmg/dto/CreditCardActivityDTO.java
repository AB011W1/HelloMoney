package com.barclays.bmg.dto;

import java.math.BigDecimal;
import java.util.Date;

public class CreditCardActivityDTO extends AccountActivityDTO implements Cloneable {

    private static final long serialVersionUID = 8377298892264552134L;

    private String cardNumber;

    private String cardHolder;

    private Date statementMonth;

    private String merchantCategoryCode;

    private Integer sequenceNumber;

    // credit card payment history attributes
    private Date dueDate;

    private BigDecimal minimumPaymentDue;

    private BigDecimal totalDelinquentAmount;

    // Card Initiate Dispute Feature
    private String disputeType;

    private Date responseTrxDate;

    private String responseTrxReference;

    private String actionCode;

    private String merchantName = "";
    
	// First Vision credit card
	private String qualityInd;

	private String foreignTransCode;

	private String transactionTypeCode;

	private String logicModule;

	private String creditPlan;

	private Boolean mergedFlag;
	// First Vision
	

    /**
     * @return the actionCode
     */
    public String getActionCode() {
	return actionCode;
    }

    public String getQualityInd() {
		return qualityInd;
	}

	public void setQualityInd(String qualityInd) {
		this.qualityInd = qualityInd;
	}

	public String getForeignTransCode() {
		return foreignTransCode;
	}

	public void setForeignTransCode(String foreignTransCode) {
		this.foreignTransCode = foreignTransCode;
	}

	public String getTransactionTypeCode() {
		return transactionTypeCode;
	}

	public void setTransactionTypeCode(String transactionTypeCode) {
		this.transactionTypeCode = transactionTypeCode;
	}

	public String getLogicModule() {
		return logicModule;
	}

	public void setLogicModule(String logicModule) {
		this.logicModule = logicModule;
	}

	public String getCreditPlan() {
		return creditPlan;
	}

	public void setCreditPlan(String creditPlan) {
		this.creditPlan = creditPlan;
	}

	public Boolean getMergedFlag() {
		return mergedFlag;
	}

	public void setMergedFlag(Boolean mergedFlag) {
		this.mergedFlag = mergedFlag;
	}

	/**
     * @param actionCode
     *            the actionCode to set
     */
    public void setActionCode(String actionCode) {
	this.actionCode = actionCode;
    }

    /**
     * @return the debitTrx
     */
    public boolean isDebitTrx() {
	if (this.getCreditDebitFlag() != null && DEBIT_FLAG.endsWith(this.getCreditDebitFlag())) {
	    return true;
	} else {
	    return false;
	}
    }

    /**
     * @param debitTrx
     *            the debitTrx to set
     */
    public void setDebitTrx(boolean debitTrx) {
    }

    /**
     * @return the disputeType
     */
    public String getDisputeType() {
	return disputeType;
    }

    /**
     * @param disputeType
     *            the disputeType to set
     */
    public void setDisputeType(String disputeType) {
	this.disputeType = disputeType;
    }

    /**
     * @return the responseTrxDate
     */
    public Date getResponseTrxDate() {
	if (responseTrxDate != null) {
	    return new Date(responseTrxDate.getTime());
	}
	return null;
    }

    /**
     * @param responseTrxDate
     *            the responseTrxDate to set
     */
    public void setResponseTrxDate(Date responseTrxDate) {
	if (responseTrxDate != null) {
	    this.responseTrxDate = new Date(responseTrxDate.getTime());
	} else {
	    this.responseTrxDate = null;
	}
    }

    /**
     * @return the responseTrxReference
     */
    public String getResponseTrxReference() {
	return responseTrxReference;
    }

    /**
     * @param responseTrxReference
     *            the responseTrxReference to set
     */
    public void setResponseTrxReference(String responseTrxReference) {
	this.responseTrxReference = responseTrxReference;
    }

    public BigDecimal getTotalDelinquentAmount() {
	return totalDelinquentAmount;
    }

    public void setTotalDelinquentAmount(BigDecimal totalDelinquentAmount) {
	this.totalDelinquentAmount = totalDelinquentAmount;
    }

    public Date getDueDate() {
	if (dueDate != null) {
	    return new Date(dueDate.getTime());
	}
	return null;
    }

    public void setDueDate(Date dueDate) {
	if (dueDate != null) {
	    this.dueDate = new Date(dueDate.getTime());
	} else {
	    this.dueDate = null;
	}
    }

    public BigDecimal getMinimumPaymentDue() {
	return minimumPaymentDue;
    }

    public void setMinimumPaymentDue(BigDecimal minimumPaymentDue) {
	this.minimumPaymentDue = minimumPaymentDue;
    }

    public String getCardNumber() {
	return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
	this.cardNumber = cardNumber;
    }

    public String getMerchantCategoryCode() {
	return merchantCategoryCode;
    }

    public void setMerchantCategoryCode(String merchantCategoryCode) {
	this.merchantCategoryCode = merchantCategoryCode;
    }

    /**
     * @return the cardHolder
     */
    public String getCardHolder() {
	return cardHolder;
    }

    /**
     * @param cardHolder
     *            the cardHolder to set
     */
    public void setCardHolder(String cardHolder) {
	this.cardHolder = cardHolder;
    }

    /**
     * @return the statementMonth
     */
    public Date getStatementMonth() {
	if (statementMonth != null) {
	    return new Date(statementMonth.getTime());
	}
	return null;
    }

    /**
     * @param statementMonth
     *            the statementMonth to set
     */
    public void setStatementMonth(Date statementMonth) {
	if (statementMonth != null) {
	    this.statementMonth = new Date(statementMonth.getTime());
	} else {
	    this.statementMonth = null;
	}
    }

    /**
     * @return the sequenceNumber
     */
    public Integer getSequenceNumber() {
	return sequenceNumber;
    }

    /**
     * @param sequenceNumber
     *            the sequenceNumber to set
     */
    public void setSequenceNumber(Integer sequenceNumber) {
	this.sequenceNumber = sequenceNumber;
    }

    public String getMerchantName() {
	return merchantName;
    }

    public void setMerchantName(String merchantName) {
	this.merchantName = merchantName;
    }

    @Override
    public CreditCardActivityDTO clone() {
	CreditCardActivityDTO returnDTO = null;
	try {
	    returnDTO = (CreditCardActivityDTO) super.clone();
	} catch (CloneNotSupportedException e) {
	    returnDTO = new CreditCardActivityDTO();
	}

	returnDTO.setTransactionDate(this.getTransactionDate());
	returnDTO.setSequenceNumber(sequenceNumber);
	returnDTO.setCardNumber(cardNumber);
	returnDTO.setCardHolder(cardHolder);
	returnDTO.setDebitAmount(this.getDebitAmount());
	returnDTO.setCreditAmount(this.getCreditAmount());
	returnDTO.setTransactionAmount(this.getTransactionAmount());
	returnDTO.setCreditDebitFlag(this.getCreditDebitFlag());
	returnDTO.setTransactionParticular(this.getTransactionParticular());
	returnDTO.setTransactionPostDate(this.getTransactionPostDate());
	returnDTO.setMerchantName(this.getMerchantName());
	return returnDTO;
    }
}
