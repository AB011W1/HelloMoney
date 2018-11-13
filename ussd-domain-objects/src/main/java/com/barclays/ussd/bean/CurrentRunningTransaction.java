package com.barclays.ussd.bean;

import java.io.Serializable;

import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class CurrentRunningTransaction.
 */
public class CurrentRunningTransaction implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    /* Below two fields pertain to the parent node with tag <node> */
    /** The tran node id. */
    private String tranNodeId;

    /** The tran id. */
    private String tranId;

    /* Below fields pertain to the child node with tag <transaction> */
    /** The tran data id. */
    private String tranDataId;

    /** The bmg op code. */
    private String bmgOpCode;

    /** The transaction data op code. */
    private String transactionDataOpCode;

    /** The header id. */
    private String headerId;

    /** The home option req. */
    private String homeOptionReq;

    /** The back option req. */
    private String backOptionReq;

    /** The optional flag. */
    private String optional;

    private String parentScreenId;

    private String serviceName;

    private int nextScreenSequenceNumber;

    private static final Logger LOGGER = Logger.getLogger(CurrentRunningTransaction.class);

    public String getOptional() {
	return optional;
    }

    public void setOptional(String optional) {
	this.optional = optional;
    }

    /** The type. */
    private String type;

    /** The skip node. */
    private String skipNode;

    /** The default value. */
    private String defaultValue;

    /**
     * Gets the transaction data op code.
     *
     * @return the transaction data op code
     */
    public String getTransactionDataOpCode() {
	return transactionDataOpCode;
    }

    /**
     * Sets the transaction data op code.
     *
     * @param transactionDataOpCode
     *            the new transaction data op code
     */
    public void setTransactionDataOpCode(String transactionDataOpCode) {
	this.transactionDataOpCode = transactionDataOpCode;
    }

    /**
     * Gets the bmg op code.
     *
     * @return the bmg op code
     */
    public String getBmgOpCode() {
	return bmgOpCode;
    }

    /**
     * Sets the bmg op code.
     *
     * @param bmgOpCode
     *            the new bmg op code
     */
    public void setBmgOpCode(String bmgOpCode) {
	this.bmgOpCode = bmgOpCode;
    }

    /**
     * Gets the tran id.
     *
     * @return the tran id
     */
    public String getTranId() {
	return tranId;
    }

    /**
     * Sets the tran id.
     *
     * @param tranId
     *            the new tran id
     */
    public void setTranId(String tranId) {
	this.tranId = tranId;
    }

    /**
     * Gets the tran node id.
     *
     * @return the tran node id
     */
    public String getTranNodeId() {
	return tranNodeId;
    }

    /**
     * Sets the tran node id.
     *
     * @param tranNodeId
     *            the new tran node id
     */
    public void setTranNodeId(String tranNodeId) {
	this.tranNodeId = tranNodeId;
    }

    /**
     * Gets the tran data id.
     *
     * @return the tran data id
     */
    public String getTranDataId() {
	return tranDataId;
    }

    /**
     * Sets the tran data id.
     *
     * @param tranDataId
     *            the new tran data id
     */
    public void setTranDataId(String tranDataId) {
	this.tranDataId = tranDataId;
    }

    /**
     * Gets the header id.
     *
     * @return the header id
     */
    public String getHeaderId() {
	return headerId;
    }

    /**
     * Sets the header id.
     *
     * @param headerId
     *            the new header id
     */
    public void setHeaderId(String headerId) {
	this.headerId = headerId;
    }

    /**
     * Gets the home option req.
     *
     * @return the home option req
     */
    public String getHomeOptionReq() {
	return homeOptionReq;
    }

    /**
     * Sets the home option req.
     *
     * @param homeOptionReq
     *            the new home option req
     */
    public void setHomeOptionReq(String homeOptionReq) {
	this.homeOptionReq = homeOptionReq;
    }

    /**
     * Gets the back option req.
     *
     * @return the back option req
     */
    public String getBackOptionReq() {
	return backOptionReq;
    }

    /**
     * Sets the back option req.
     *
     * @param backOptionReq
     *            the new back option req
     */
    public void setBackOptionReq(String backOptionReq) {
	this.backOptionReq = backOptionReq;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType() {
	return type;
    }

    /**
     * Sets the type.
     *
     * @param type
     *            the new type
     */
    public void setType(String type) {
	this.type = type;
    }

    /**
     * Gets the skip node.
     *
     * @return the skip node
     */
    public String getSkipNode() {
	return skipNode;
    }

    /**
     * Sets the skip node.
     *
     * @param skipNode
     *            the new skip node
     */
    public void setSkipNode(String skipNode) {
	this.skipNode = skipNode;
    }

    /**
     * Gets the default value.
     *
     * @return the default value
     */
    public String getDefaultValue() {
	return defaultValue;
    }

    /**
     * Sets the default value.
     *
     * @param defaultValue
     *            the new default value
     */
    public void setDefaultValue(String defaultValue) {
	this.defaultValue = defaultValue;
    }

    public String getParentScreenId() {
	return parentScreenId;
    }

    public void setParentScreenId(String parentScreenId) {
	this.parentScreenId = parentScreenId;
    }

    public String getServiceName() {
	return serviceName;
    }

    public void setServiceName(String serviceName) {
	this.serviceName = serviceName;
    }

    public int getNextScreenSequenceNumber() {
	return nextScreenSequenceNumber;
    }

    public void setNextScreenSequenceNumber(int nextScreenSequenceNumber) {
	this.nextScreenSequenceNumber = nextScreenSequenceNumber;
    }

    @Override
    public String toString() {


    	LOGGER.debug("backOptionReq:: {" + backOptionReq + "},  bmgOpCode:: {"
				+ bmgOpCode + "},  defaultValue:: {" + defaultValue
				+ "},  headerId:: {" + headerId + "},  homeOptionReq:: {"
				+ homeOptionReq + "},  nextScreenSequenceNumber:: {"
				+ nextScreenSequenceNumber + "},  optional:: {" + optional
				+ "},  parentScreenId:: {" + parentScreenId
				+ "},  serialVersionUID:: {" + serialVersionUID
				+ "},  serviceName:: {" + serviceName + "},  skipNode:: {"
				+ skipNode + "},  tranDataId:: {" + tranDataId
				+ "},  tranId:: {" + tranId + "},  tranNodeId:: {" + tranNodeId
				+ "},  transactionDataOpCode:: {" + transactionDataOpCode
				+ "},");

    	return super.toString();
    }
}
