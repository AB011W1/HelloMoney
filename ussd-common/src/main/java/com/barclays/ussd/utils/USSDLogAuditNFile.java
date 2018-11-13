/**
 * USSDLogAuditNFile.java
 */
package com.barclays.ussd.utils;

import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class USSDLogAuditNFile.
 * 
 * @author BTCI This class is used to write log, audit and ecrime data
 */
public final class USSDLogAuditNFile {
    private final static Logger LOGGER = Logger.getLogger(USSDLogAuditNFile.class);

    private USSDLogAuditNFile() {
	// TODO Auto-generated constructor stub
    }

    /**
     * This method performs logging.
     * 
     * @param details
     *            the details
     */
    public static void doLog(final String details) {
	LOGGER.info("log the details" + details);
    }

    /**
     * This method performs auditing.
     * 
     * @param details
     *            the details
     */
    public static void doAudit(final String details) {
	LOGGER.info("audit details");
    }

    /**
     * This method writes details to file.
     * 
     * @param details
     *            the details
     */
    public static void writeToFile(final String details) {
	LOGGER.info("write to file details");
    }

}
