package com.barclays.ussd.auth.bean;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * USSD Request which come from the http request.
 */
public class USSDRequest implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** Input entered by mobile subscriber. */
    private String input;

    /**
     * This is a bounce-back variable containing whatever was sent back as a response for the previous transaction within the same session.
     */
    private String extra;

    /** Level Id for the current Screen. */
    private String level;

    /**
     * LevelID pointing to the static node in navigation configs (not applicable to externally hosted applications).
     */
    private String currentLevel;

    /** Mobile number of subscriber. */
    private String msisdn;

    private String msisdnWithCountry;

    private String businessId;

    private String aggregator;

    private String amount;

    public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
     * Gets the input.
     *
     * @return the input
     */
    public String getInput() {
	return input;
    }

    /**
     * Sets the input.
     *
     * @param input
     *            the input to set
     */
    public void setInput(String input) {
	this.input = input;
    }

    /**
     * Gets the extra.
     *
     * @return the extra
     */
    public String getExtra() {
	return extra;
    }

    /**
     * Sets the extra.
     *
     * @param extra
     *            the extra to set
     */
    public void setExtra(String extra) {
	this.extra = extra;
    }

    /**
     * Gets the level.
     *
     * @return the level
     */
    public String getLevel() {
	return level;
    }

    /**
     * Sets the level.
     *
     * @param level
     *            the level to set
     */
    public void setLevel(String level) {
	this.level = level;
    }

    /**
     * Gets the current level.
     *
     * @return the currentLevel
     */
    public String getCurrentLevel() {
	return currentLevel;
    }

    /**
     * Sets the current level.
     *
     * @param currentLevel
     *            the currentLevel to set
     */
    public void setCurrentLevel(String currentLevel) {
	this.currentLevel = currentLevel;
    }

    /**
     * Gets the msisdn.
     *
     * @return the msisdnNumber
     */
    public String getMsisdn() {
	return msisdn;
    }

    /**
     * Sets the msisdn.
     *
     * @param msisdn
     *            the msisdnNumber to set
     */
    public void setMsisdn(String msisdn) {
	this.msisdn = msisdn;
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
     * @return the aggreegator
     */
    public String getAggregator() {
	return aggregator;
    }

    /**
     * @param aggregator
     *            the aggreegator to set
     */
    public void setAggregator(String aggregator) {
	this.aggregator = aggregator;
    }

	/**
	 * @return the msisdnWithCountry
	 */
	public String getMsisdnWithCountry() {
		return msisdnWithCountry;
	}

	/**
	 * @param msisdnWithCountry the msisdnWithCountry to set
	 */
	public void setMsisdnWithCountry(String msisdnWithCountry) {
		this.msisdnWithCountry = msisdnWithCountry;
	}

}
