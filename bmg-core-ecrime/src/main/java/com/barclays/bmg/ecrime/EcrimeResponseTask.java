package com.barclays.bmg.ecrime;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.barclays.bmg.type.KeyPair;

/**
 * TODO Class/Interface description.
 * 
 * @author
 */
public class EcrimeResponseTask implements Runnable {
    private EcrimeContext context;

    private List<String> maskRules;

    private final static Logger logger = Logger.getLogger(EcrimeResponseTask.class);

    private Logger ecrimeLog;

    /**
     * TODO Constructor description, including pre/post conditions and invariants.
     * 
     * @param context
     */
    public EcrimeResponseTask(EcrimeContext context, List<String> maskRules) {
	this.context = context;
	this.maskRules = maskRules;
	ecrimeLog = getLogger();
    }

    private Logger getLogger() {
	return Logger.getLogger("mcfe-ecrime");
    }

    /**
     * @see java.lang.Runnable#run()
     */
    public void run() {

	/* Changed logging for Veracode CRLF Injection issues */
	String requestURL = context.getRequestURL().replace("\r", " ").replace("\n", " ");
	logger.info(StringEscapeUtils.escapeJava("Log Ecrime Information for the requestURL=" + requestURL));
	/* Ends log change */

	StringBuilder sb = new StringBuilder();
	sb.append("OUT;");
	if (context.getResponseAttributes() != null) {
	    if (logger.isDebugEnabled()) {
		logger.debug(" context.getResponseAttributes() for the Ecrime is not Empty : " + context.getResponseAttributes());
	    }
	} else {
	    logger.debug("context.getResponseAttributes() for the Ecrime is Empty. ");
	}

	List<KeyPair> list = context.getCommonAttributes();
	for (KeyPair keypair : list) {
	    sb.append(keypair.getKey() + "=" + EcrimeUtils.getMaskValue(keypair, maskRules)).append(";");
	}
	for (KeyPair keypair : context.getResponseAttributes()) {
	    sb.append(keypair.getKey() + "=" + EcrimeUtils.getMaskValue(keypair, maskRules)).append(";");
	}
	// append Transaction Status if executed.
	if (context.getTransactionStatus() != null) {
	    sb.append("TransactionStatus=").append(context.getTransactionStatus()).append(";").append("TransactionDescription=").append(
		    context.getTransactionDescription()).append(";");
	}

	String logStr = sb.toString();

	/* Changed logging for Veracode CRLF Injection issues */
	logStr = logStr.replace("\r", " ").replace("\n", " ");
	/* Ends log change */

	logger.info(logStr);
	ecrimeLog.info(logStr);

    }

}
