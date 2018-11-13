package com.barclays.bmg.ecrime;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import com.barclays.bmg.type.KeyPair;

/**
 * TODO Class/Interface description.
 *
 * @author
 */
public class EcrimeRequestTask implements Runnable {
    private EcrimeContext context;

    private List<String> maskRules;

    private final static Logger logger = Logger.getLogger(EcrimeRequestTask.class);

    private Logger ecrimeLog;

    public EcrimeRequestTask(EcrimeContext context, List<String> maskRules) {
	this.context = context;
	this.maskRules = maskRules;
	ecrimeLog = getLogger();
    }

    private Logger getLogger() {
	// ECRIME_LOGGER
	return Logger.getLogger("mcfe-ecrime");
    }

    /**
     * @see java.lang.Runnable#run()
     */
    public void run() {
	/* Changed logging for Veracode CRLF Injection issues */
	String requestURL = context.getRequestURL().replace("\r", " ").replace("\n", " ");
	logger.info("Log Ecrime Information for the requestURL=" + requestURL);
	/* Ends log change */

	StringBuilder sb = new StringBuilder();
	sb.append("IN;");
	List<KeyPair> list = context.getCommonAttributes();
	for (KeyPair keypair : list) {
	    sb.append(keypair.getKey() + "=" + EcrimeUtils.getMaskValue(keypair, maskRules)).append(";");
	}
	for (KeyPair keypair : context.getRequestAttributes()) {
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
	MDC.put("ROUTINGKEY", "COMMON");
	logger.info(logStr);
	ecrimeLog.info(logStr);
    }

}
