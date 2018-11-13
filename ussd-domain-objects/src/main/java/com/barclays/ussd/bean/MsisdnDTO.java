package com.barclays.ussd.bean;

/**
 * The Class MsisdnDTO.
 */
public class MsisdnDTO {

    /** The cclen. */
    private Integer cclen;

    /** The cclen. */
    private Integer ccvalue;

	/** The snlen. */
    private Integer snlen;

    /**
     * Gets the cclen.
     *
     * @return the cclen
     */
    public Integer getCclen() {
	return cclen;
    }

    /**
     * Sets the cclen.
     *
     * @param cclen
     *            the new cclen
     */
    public void setCclen(Integer cclen) {
	this.cclen = cclen;
    }


    public Integer getCcvalue() {
		return ccvalue;
	}

	public void setCcvalue(Integer ccvalue) {
		this.ccvalue = ccvalue;
	}
    /**
     * Gets the snlen.
     *
     * @return the snlen
     */
    public Integer getSnlen() {
	return snlen;
    }

    /**
     * Sets the snlen.
     *
     * @param snlen
     *            the new snlen
     */
    public void setSnlen(Integer snlen) {
	this.snlen = snlen;
    }

    @Override
    public String toString() {
	return new StringBuffer("cclen:").append(cclen).append("snlen:").append(snlen).toString();
    }

}
