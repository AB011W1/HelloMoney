package com.barclays.ussd.xmlresponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

// TODO: Auto-generated Javadoc
/**
 * The Class USSDResponse.
 */


@XmlRootElement(name = "BODY")
@XmlType(propOrder = {"text", "status", "extra"})
@XmlAccessorType(XmlAccessType.FIELD)
public class USSDXMLResponseBody {

    /** The next level. */
	//@XmlElement(name = "nextLevel")
	@XmlTransient
    private Integer nextLevel;

    /** The text. */
	@XmlElement(name = "DISPLAYTEXT")
    private String text;

    /** The value of selection. */
	//@XmlElement(name = "valueOfSelection")
	@XmlTransient
    private String valueOfSelection;

    /** The service id. */
	//@XmlElement(name = "serviceId")
	@XmlTransient
    private String serviceId;

    /** The status. */
	@XmlElement(name = "STATUS")
    private String status;

    /** The extra. */
	@XmlElement(name = "EXTRA")
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

}
