package com.barclays.ussd.auth.bean;

// TODO: Auto-generated Javadoc
/**
 * The Class USSDResponse.
 */
public class USSDResponse {

    /** The next level. */
    private Integer nextLevel;

    /** The text. */
    private String text;

    /** The value of selection. */
    private String valueOfSelection;

    /** The service id. */
    private String serviceId;

    /** The status. */
    private String status;

    /** The extra. */
    private String extra;

    /* *//** The to string. */
    /*
     * private String toString;
     */

    /**
     * Gets the next level.
     *
     * @return the next level
     */
    public Integer getNextLevel() {
	return nextLevel;
    }

    /**
     * Sets the next level.
     *
     * @param nextLevel
     *            the new next level
     */
    public void setNextLevel(Integer nextLevel) {
	this.nextLevel = nextLevel;
    }

    /**
     * Gets the text.
     *
     * @return the text
     */
    public String getText() {
	return text;
    }

    /**
     * Sets the text.
     *
     * @param text
     *            the new text
     */
    public void setText(String text) {
	this.text = text;
    }

    /**
     * Gets the value of selection.
     *
     * @return the value of selection
     */
    public String getValueOfSelection() {
	return valueOfSelection;
    }

    /**
     * Sets the value of selection.
     *
     * @param valueOfSelection
     *            the new value of selection
     */
    public void setValueOfSelection(String valueOfSelection) {
	this.valueOfSelection = valueOfSelection;
    }

    /**
     * Gets the service id.
     *
     * @return the service id
     */
    public String getServiceId() {
	return serviceId;
    }

    /**
     * Sets the service id.
     *
     * @param serviceId
     *            the new service id
     */
    public void setServiceId(String serviceId) {
	this.serviceId = serviceId;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public String getStatus() {
	return status;
    }

    /**
     * Sets the status.
     *
     * @param status
     *            the new status
     */
    public void setStatus(String status) {
	this.status = status;
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
     *            the new extra
     */
    public void setExtra(String extra) {
	this.extra = extra;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	 StringBuffer sb = new StringBuffer();
	sb.append("nextLevel:").append(	nextLevel);
	sb.append(",text:").append(text);
	sb.append(",valueOfSelection:").append(valueOfSelection);
	sb.append(",serviceId:").append(serviceId);
	sb.append(",status:").append(status);
	sb.append(",extra:").append(extra);

	return sb.toString();
    }


}
