package com.barclays.bmg.dto;

public class ComponentResCacheDTO extends BaseDTO {

    /** TODO Comment for <code>serialVersionUID</code>. */

    private static final long serialVersionUID = 80279107602460707L;

    private String languageId;

    private String labelValue;

    private String toolTip;

    private String helpText;

    private Integer length;

    /**
     * added by Martin
     */
    private boolean required;

    private String format;

    private String messageKey;

    private String screenId;

    private String systemId;

    private String businessId;

    private String componentKey;

    private String fieldName;

    private int displayOrder;

    private String section;

    private boolean rendered;

    private boolean hasHelpText;

    private String key;

    public String getBusinessid() {
		return businessid;
	}

	public void setBusinessid(String businessid) {
		this.businessid = businessid;
	}

	public String getBillerid() {
		return billerid;
	}

	public void setBillerid(String billerid) {
		this.billerid = billerid;
	}

	private String businessid;

    private String billerid;

    public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	private String refNoMessageKey1;
    public String getRefNoMessageKey1() {
		return refNoMessageKey1;
	}

	public void setRefNoMessageKey1(String refNoMessageKey1) {
		this.refNoMessageKey1 = refNoMessageKey1;
	}

	/**
     * @return the componentKey
     */
    public String getComponentKey() {
	return componentKey;
    }

    /**
     * @param componentKey
     *            the componentKey to set
     */
    public void setComponentKey(String componentKey) {
	this.componentKey = componentKey;
    }

    /**
     * @return the fieldName
     */
    public String getFieldName() {
	return fieldName;
    }

    /**
     * @param fieldName
     *            the fieldName to set
     */
    public void setFieldName(String fieldName) {
	this.fieldName = fieldName;
    }

    /**
     * @return the displayOrder
     */
    public int getDisplayOrder() {
	return displayOrder;
    }

    /**
     * @param displayOrder
     *            the displayOrder to set
     */
    public void setDisplayOrder(int displayOrder) {
	this.displayOrder = displayOrder;
    }

    /**
     * @return the section
     */
    public String getSection() {
	return section;
    }

    /**
     * @param section
     *            the section to set
     */
    public void setSection(String section) {
	this.section = section;
    }

    public boolean isRequired() {
	return required;
    }

    public void setRequired(boolean required) {
	this.required = required;
    }

    /**
     * @return the languageId
     */
    public String getLanguageId() {
	return languageId;
    }

    /**
     * @param languageId
     *            the languageId to set
     */
    public void setLanguageId(String languageId) {
	this.languageId = languageId;
    }

    /**
     * @return the labelValue
     */
    public String getLabelValue() {
	return labelValue;
    }

    /**
     * @param labelValue
     *            the labelValue to set
     */
    public void setLabelValue(String labelValue) {
	this.labelValue = labelValue;
    }

    /**
     * @return the toolTip
     */
    public String getToolTip() {
	return toolTip;
    }

    /**
     * @param toolTip
     *            the toolTip to set
     */
    public void setToolTip(String toolTip) {
	this.toolTip = toolTip;
    }

    /**
     * @return the helpText
     */
    public String getHelpText() {
	return helpText;
    }

    /**
     * @param helpText
     *            the helpText to set
     */
    public void setHelpText(String helpText) {
	this.helpText = helpText;
    }

    /**
     * @return the length
     */
    public Integer getLength() {
	return length;
    }

    /**
     * @param length
     *            the length to set
     */
    public void setLength(Integer length) {
	this.length = length;
    }

    public String getFormat() {
	return format;
    }

    public void setFormat(String format) {
	this.format = format;
    }

    /**
     * @return the screenId
     */
    public String getScreenId() {
	return screenId;
    }

    /**
     * @param screenId
     *            the screenId to set
     */
    public void setScreenId(String screenId) {
	this.screenId = screenId;
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

    public String getMessageKey() {
	return messageKey;
    }

    public void setMessageKey(String messageKey) {
	this.messageKey = messageKey;
    }

    /**
     * @param rendered
     *            the rendered to set
     */
    public void setRendered(boolean rendered) {
	this.rendered = rendered;
    }

    /**
     * @return the rendered
     */
    public boolean isRendered() {
	return rendered;
    }

    /**
     * @param hasHelpText
     *            the hasHelpText to set
     */
    public void setHasHelpText(boolean hasHelpText) {
	this.hasHelpText = hasHelpText;
    }

    /**
     * @return the hasHelpText
     */
    public boolean isHasHelpText() {
	return hasHelpText;
    }

}