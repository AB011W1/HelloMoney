package com.barclays.bmg.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/*
 * build for Disaster Recovery
 */
public class DisasterRecoveryHelper {

    private static final Logger LOGGER = Logger.getLogger(DisasterRecoveryHelper.class);

    // DR Parameter start
    private static final String FTP_EP = "FinancialTransactionProcessing_EndPoint";
    private static final String ICM_EP = "IndividualCustomerManagement_EndPoint";
    private static final String PRE_EP = "ProblemManagement_EndPoint";
    private static final String PIE_EP = "PinManagement_EndPoint";
    private static final String HMC_EP = "HelloMoneyCustomerManagement_EndPoint";
    private static final String ACM_EP = "AccountManagement_EndPoint";
    private static final String AMM_EP = "AccountMandateManagement_EndPoint";
    private static final String ACR_EP = "AccountReporting_EndPoint";
    private static final String BCM_EP = "BeneficiaryManagement_EndPoint";
    private static final String CBM_EP = "CheckBookManagement_EndPoint";
    private static final String NOS_EP = "NotificationService_EndPoint";
    private static final String THM_EP = "TacticalHelloMoney_EndPoint";
    private static final String THM_BCAG_EP = "THM_BCAG_EndPoint";
    //Added for CR#35 Debit Card
    private static final String FTCM_EP = "FinancialTransactionCardManagment_EndPoint";
    private static List<String> BEMDREnabledParams = new ArrayList<String>();

    public static final String DR_ENABLED = "DR";
    public static final String DR_KEY_PREFIX = "DR_";

    public static final String SIT_ENABLED = "SIT";
    public static final String SIT_KEY_PREFIX = "SIT_";

    public static final String SHM_DR_ENABLE_FLAG_KEY = "ChannelDB.CurrenEnv";
    public static final String THM_DR_ENABLE_FLAG_KEY = "THM.CurrenEnv";
    public static final String BCAG_DR_ENABLE_FLAG_KEY = "BCAG.CurrenEnv";
    public static final String BEM_DR_ENABLE_FLAG_KEY = "BEM.CurrenEnv";

    public static final boolean SHM_DR_FLAG = DisasterRecoveryHelper.checkHelloMoneyDRFlag();
    public static final boolean THM_DR_FLAG = DisasterRecoveryHelper.checkTacticalHelloMoneyDRFlag();
    public static final boolean BCAG_DR_FLAG = DisasterRecoveryHelper.checkBCAGDRFlag();
    public static final boolean BEM_DR_FLAG = DisasterRecoveryHelper.checkBEMDRFlag();
    public static final boolean BEM_SIT_FLAG = DisasterRecoveryHelper.checkBEMSITFlag();

    public static final String MQ_SOAP_LOG_DB_LEVEL_FLAG = "LogDB.SOAP.LOG.LEVEL";
    public static final String MQ_SOAP_LOG_DB_LEVEL_DEBUG = "DEBUG";

    public static boolean checkHelloMoneyDRFlag() {
	LOGGER.info("before fetching ChannelDB.CurrenEnv");
	return DR_ENABLED.equalsIgnoreCase(JVMFetchTool.fetchJVMParm(SHM_DR_ENABLE_FLAG_KEY));
    }

    public static boolean checkTacticalHelloMoneyDRFlag() {
	LOGGER.info("before fetching THM.CurrenEnv");
	return DR_ENABLED.equalsIgnoreCase(JVMFetchTool.fetchJVMParm(THM_DR_ENABLE_FLAG_KEY));
    }

    public static boolean checkBCAGDRFlag() {
	LOGGER.info("before fetching BCAG.CurrenEnv");
	return DR_ENABLED.equalsIgnoreCase(JVMFetchTool.fetchJVMParm(BCAG_DR_ENABLE_FLAG_KEY));
    }

    public static boolean checkBEMDRFlag() {
	LOGGER.info("before fetching BEM.CurrenEnv");
	return DR_ENABLED.equalsIgnoreCase(JVMFetchTool.fetchJVMParm(BEM_DR_ENABLE_FLAG_KEY));
    }

    public static boolean checkBEMSITFlag() {
	LOGGER.info("before fetching BEM.CurrenEnv");
	return SIT_ENABLED.equalsIgnoreCase(JVMFetchTool.fetchJVMParm(BEM_DR_ENABLE_FLAG_KEY));
    }

    public static boolean checkMQSOAPLOGLEVEL() {
	LOGGER.info("before fetching LogDB.SOAP.LOG.LEVEL");
	return MQ_SOAP_LOG_DB_LEVEL_DEBUG.equalsIgnoreCase(JVMFetchTool.fetchJVMParm(MQ_SOAP_LOG_DB_LEVEL_FLAG)) || null!=JVMFetchTool.fetchJVMParm(MQ_SOAP_LOG_DB_LEVEL_FLAG);
    }
    public static String handleDrParmeter(String param) {
	if (BEMDREnabledParams != null && BEMDREnabledParams.isEmpty()) {
	    BEMDREnabledParams.add(FTP_EP);
	    BEMDREnabledParams.add(ICM_EP);
	    BEMDREnabledParams.add(PRE_EP);
	    BEMDREnabledParams.add(PIE_EP);
	    BEMDREnabledParams.add(HMC_EP);
	    BEMDREnabledParams.add(ACM_EP);
	    BEMDREnabledParams.add(AMM_EP);
	    BEMDREnabledParams.add(ACR_EP);
	    BEMDREnabledParams.add(BCM_EP);
	    BEMDREnabledParams.add(CBM_EP);
	    BEMDREnabledParams.add(NOS_EP);
	    //Added for CR#35 Debit Card
	    BEMDREnabledParams.add(FTCM_EP);
	}

	String updatedparam = param;
	if (StringUtils.isEmpty(updatedparam) || updatedparam.startsWith(DisasterRecoveryHelper.DR_KEY_PREFIX)
		|| updatedparam.startsWith(DisasterRecoveryHelper.SIT_KEY_PREFIX)) {
	    return updatedparam;
	}
	if (BEMDREnabledParams.contains(updatedparam)) {
	    if (BEM_DR_FLAG) {
		updatedparam = DR_KEY_PREFIX + param;
	    } else if (BEM_SIT_FLAG) {
		updatedparam = SIT_KEY_PREFIX + param;
	    }

	} else if (THM_EP.equals(updatedparam)) {
	    if (THM_DR_FLAG) {
		updatedparam = DR_KEY_PREFIX + param;
	    }
	} else if (THM_BCAG_EP.equals(updatedparam)) {
	    if (BCAG_DR_FLAG) {
		updatedparam = DR_KEY_PREFIX + param;
	    }
	}

	return updatedparam;
    }
}
