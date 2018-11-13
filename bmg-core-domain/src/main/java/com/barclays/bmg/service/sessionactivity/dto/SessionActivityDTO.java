package com.barclays.bmg.service.sessionactivity.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.barclays.bmg.service.sessionactivity.bean.KeyValuePairBean;

public class SessionActivityDTO implements Serializable {
    /**
	 *
	 */
    private static final long serialVersionUID = -8653969974425577002L;

    private static String FORMAT_STRING = "dd/MM/yyyy HH:mm:ss";

    private DateFormat dateFormat = new SimpleDateFormat(FORMAT_STRING);

    public static final String TXN_TYPE = "ActivityId";

    public static final String ACCT_FROM = "acctFrom";

    public static final String ACCT_TO = "acctTo";

    public static final String AMOUNT = "amount";

    public static final String STATUS = "status";

    public static final String DETAILS = "details";

    public static final String REFNO = "refNo";

    public static final String TXN_LOG_IN = "LOG_IN";

    public static final String TXN_LOG_OUT = "LOG_OUT";

    public static final String SUCCESS = "SUCCESS";

    public static final String FAIL = "FAIL";

    private transient Object retValue;

    // private transient CacheUtility cacheUtility;
    private String userId;

    private String sessionId;

    private String systemId;

    private String businessId;

    private String txnTyp;

    private Date txnTime;

    private String details;

    private List<KeyValuePairBean> txnDetails;

    private String acctFrom;

    private String acctTo;

    private BigDecimal amount;

    private String status;

    private String refNo;

    public DateFormat getDateFormat() {
	return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
	this.dateFormat = dateFormat;
    }

    public Object getRetValue() {
	return retValue;
    }

    public void setRetValue(Object retValue) {
	this.retValue = retValue;
    }

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }

    public String getSystemId() {
	return systemId;
    }

    public void setSystemId(String systemId) {
	this.systemId = systemId;
    }

    public String getBusinessId() {
	return businessId;
    }

    public void setBusinessId(String businessId) {
	this.businessId = businessId;
    }

    public String getTxnTyp() {
	return txnTyp;
    }

    public void setTxnTyp(String txnTyp) {
	this.txnTyp = txnTyp;
    }

    /**
     * @return the txnTime
     */
    public Date getTxnTime() {
	if (txnTime != null) {
	    return new Date(txnTime.getTime());
	}
	return null;
    }

    /**
     * @param txnTime
     *            the txnTime to set
     */
    public void setTxnTime(Date txnTime) {
	if (txnTime != null) {
	    this.txnTime = new Date(txnTime.getTime());
	} else {
	    this.txnTime = null;
	}
    }

    public String getDetails() {
	return details;
    }

    public void setDetails(String details) {
	this.details = details;
    }

    public List<KeyValuePairBean> getTxnDetails() {
	return txnDetails;
    }

    public void setTxnDetails(List<KeyValuePairBean> txnDetails) {
	this.txnDetails = txnDetails;
    }

    public String getAcctFrom() {
	return acctFrom;
    }

    public void setAcctFrom(String acctFrom) {
	this.acctFrom = acctFrom;
    }

    public String getAcctTo() {
	return acctTo;
    }

    public void setAcctTo(String acctTo) {
	this.acctTo = acctTo;
    }

    public BigDecimal getAmount() {
	return amount;
    }

    public void setAmount(BigDecimal amount) {
	this.amount = amount;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String getRefNo() {
	return refNo;
    }

    public void setRefNo(String refNo) {
	this.refNo = refNo;
    }

    public String getSessionId() {
	return sessionId;
    }

    public void setSessionId(String sessionId) {
	this.sessionId = sessionId;
    }

}
