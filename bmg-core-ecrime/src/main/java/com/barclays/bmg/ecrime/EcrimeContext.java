package com.barclays.bmg.ecrime;

import java.util.ArrayList;
import java.util.List;
import com.barclays.bmg.type.KeyPair;

/**
 * <p>
 * <strong>EcrimeContext</strong> contains all of the per-request state information related to the processing of a single HTTP request, and the
 * corresponding HTTP response. It is passed to, and potentially modified by, spring webflow executor or JSF Phase Listener.
 * </p>
 * <p>
 * A {@link EcrimeContext} instance is initialised with a particular request at the beginning of Ecrime Facade Filter. The instance will be cleared at
 * the end of the Ecrime Facade Filter, after which no further references to this instance are allowed. While a {@link EcrimeContext} instance is
 * active, it must not be referenced from any thread other than the one upon which the servlet container executing this web application utilizes for
 * the processing of this request.
 * </p>
 * 
 * @author
 */
public class EcrimeContext {

    private List<KeyPair> commonAttributes = new ArrayList<KeyPair>();

    private Boolean transactionStatus = null;

    private String transactionDescription;

    private List<KeyPair> requestAttributes = new ArrayList<KeyPair>();

    private List<KeyPair> responseAttributes = new ArrayList<KeyPair>();

    private Runnable requestTask;

    private Runnable responseTask;

    public List<KeyPair> getCommonAttributes() {
	return commonAttributes;
    }

    public void setCommonAttributes(List<KeyPair> commonAttributes) {
	this.commonAttributes = commonAttributes;
    }

    public Boolean getTransactionStatus() {
	return transactionStatus;
    }

    public void setTransactionStatus(Boolean transactionStatus) {
	this.transactionStatus = transactionStatus;
    }

    private String requestURL;

    public List<KeyPair> getRequestAttributes() {
	return requestAttributes;
    }

    public void setRequestAttributes(List<KeyPair> requestAttributes) {
	this.requestAttributes = requestAttributes;
    }

    public List<KeyPair> getResponseAttributes() {
	return responseAttributes;
    }

    public void setResponseAttributes(List<KeyPair> responseAttributes) {
	this.responseAttributes = responseAttributes;
    }

    /**
     * @param requestURL
     *            the requestURL to set
     */
    public void setRequestURL(String requestURL) {
	this.requestURL = requestURL;
    }

    /**
     * @return the requestURL
     */
    public String getRequestURL() {
	return requestURL;
    }

    /**
     * @param transactionDescription
     *            the transactionDescription to set
     */
    public void setTransactionDescription(String transactionDescription) {
	this.transactionDescription = transactionDescription;
    }

    /**
     * @return the transactionDescription
     */
    public String getTransactionDescription() {
	return transactionDescription;
    }

    /**
     * @param requestTask
     *            the requestTask to set
     */
    public void setRequestTask(Runnable requestTask) {
	this.requestTask = requestTask;
    }

    /**
     * @return the requestTask
     */
    public Runnable getRequestTask() {
	return requestTask;
    }

    /**
     * @param responseTask
     *            the responseTask to set
     */
    public void setResponseTask(Runnable responseTask) {
	this.responseTask = responseTask;
    }

    /**
     * @return the responseTask
     */
    public Runnable getResponseTask() {
	return responseTask;
    }
}
