package com.barclays.ussd.bean;

import java.io.Serializable;

/**
 * The Class MenuItem.
 */
public class MenuItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /** The node id. */
    private String nodeId;

    /** The level. */
    private String level;

    /** The label id. */
    private String labelId;

    /** The option id. */
    private String optionId;

    /** The screen id. */
    private String screenId;

    /**
     * Gets the node id.
     * 
     * @return the node id
     */
    public String getNodeId() {
	return nodeId;
    }

    /**
     * Sets the node id.
     * 
     * @param nodeId
     *            the new node id
     */
    public void setNodeId(String nodeId) {
	this.nodeId = nodeId;
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
     *            the new level
     */
    public void setLevel(String level) {
	this.level = level;
    }

    /**
     * Gets the label id.
     * 
     * @return the label id
     */
    public String getLabelId() {
	return labelId;
    }

    /**
     * Sets the label id.
     * 
     * @param labelId
     *            the new label id
     */
    public void setLabelId(String labelId) {
	this.labelId = labelId;
    }

    /**
     * Gets the option id.
     * 
     * @return the option id
     */
    public String getOptionId() {
	return optionId;
    }

    /**
     * Sets the option id.
     * 
     * @param optionId
     *            the new option id
     */
    public void setOptionId(String optionId) {
	this.optionId = optionId;
    }

    /**
     * Sets the screen id.
     * 
     * @param screenId
     *            the screenId to set
     */
    public void setScreenId(String screenId) {
	this.screenId = screenId;
    }

    /**
     * Gets the screen id.
     * 
     * @return the screenId
     */
    public String getScreenId() {
	return screenId;
    }
}
