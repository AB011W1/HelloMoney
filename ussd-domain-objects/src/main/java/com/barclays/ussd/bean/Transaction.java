package com.barclays.ussd.bean;

import java.io.Serializable;
import java.util.Map;
import java.util.Stack;

// TODO: Auto-generated Javadoc
/**
 * The Class Transaction.
 */
public class Transaction implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** bmgRespCont. */
    private Integer currentScreenIndex = 0;

    /** The bmg resp cont. */
    private BMGResponseContainer bmgRespCont;

    /** The user input map. */
    private Map<String, String> userInputMap;

    /** The neg ui render map. */
    private Map<String, String> negUIRenderMap;

    /** The current running transaction. */
    private CurrentRunningTransaction currentRunningTransaction;

    private Stack<Integer> screenSequenceStack;

    private boolean serviceEnabled = true;

    public boolean isServiceEnabled() {
	return serviceEnabled;
    }

    public void setServiceEnabled(boolean serviceEnabled) {
	this.serviceEnabled = serviceEnabled;
    }

    /**
     * Gets the current running transaction.
     * 
     * @return the current running transaction
     */
    public CurrentRunningTransaction getCurrentRunningTransaction() {
	return currentRunningTransaction;
    }

    /**
     * Sets the current running transaction.
     * 
     * @param currentRunningTransaction
     *            the new current running transaction
     */
    public void setCurrentRunningTransaction(CurrentRunningTransaction currentRunningTransaction) {
	this.currentRunningTransaction = currentRunningTransaction;
    }

    /**
     * Gets the user input map.
     * 
     * @return the user input map
     */
    public Map<String, String> getUserInputMap() {
	return userInputMap;
    }

    /**
     * Sets the user input map.
     * 
     * @param userInputMap
     *            the user input map
     */
    public void setUserInputMap(Map<String, String> userInputMap) {
	this.userInputMap = userInputMap;
    }

    /**
     * Gets the bmg resp cont.
     * 
     * @return the bmgRespCont
     */
    public BMGResponseContainer getBmgRespCont() {
	return bmgRespCont;
    }

    /**
     * Sets the bmg resp cont.
     * 
     * @param bmgRespCont
     *            the bmgRespCont to set
     */
    public void setBmgRespCont(BMGResponseContainer bmgRespCont) {
	this.bmgRespCont = bmgRespCont;
    }

    /**
     * Gets the current screen index.
     * 
     * @return the current screen index
     */
    public Integer getCurrentScreenIndex() {
	return currentScreenIndex;
    }

    /**
     * Sets the current screen index.
     * 
     * @param currentScreenIndex
     *            the new current screen index
     */
    public void setCurrentScreenIndex(Integer currentScreenIndex) {
	this.currentScreenIndex = currentScreenIndex;
    }

    /**
     * Sets the neg ui render map.
     * 
     * @param negUIRenderMap
     *            the negUIRenderMap to set
     */
    public void setNegUIRenderMap(Map<String, String> negUIRenderMap) {
	this.negUIRenderMap = negUIRenderMap;
    }

    /**
     * Gets the neg ui render map.
     * 
     * @return the negUIRenderMap
     */
    public Map<String, String> getNegUIRenderMap() {
	return negUIRenderMap;
    }

    public Stack<Integer> getScreenSequenceStack() {
	return screenSequenceStack;
    }

    public void setScreenSequenceStack(Stack<Integer> screenSequenceStack) {
	this.screenSequenceStack = screenSequenceStack;
    }

}
