package com.barclays.bmg.constants;

public interface ErrorCodeConstant {

    String MW_MESSAGE_PREFIX = "COM_MW_";
    String PREFIX_FINANCIAL_SERVICE = "FN_";

    String ERROR_CODE_1002 = "1002";
    String ERROR_CODE_1001 = "1001";

    String TXN_PARTIAL_UPDATE = "06004";
    String PARTIAL_SUCCESS_CODE = "03000";

    String TXN_SUSPECT = "06008";
    // CHECK added by me .. Need to cross check
    String TXN_INSUFFICIENT_FUND = "06000";
    String SUCCESS_CODE = "00000";
    String SUBMITTED_TXN_RES_CODE = "00001";
    String MW_FINANCIAL_SERVICE_TIMEOUT = "06002";
    String MW_CONNECT_HOST_EXCEPTION = "06001";
    String VAS_INVALID_ACCT_NO = "06713";
    String BUSINESS_ERROR = "06000";
    String MW_VAS_TIMEOUT = "06702";
    String BP_BCD_PRIME_06878 = "06878";
    String ERRCODE_ADDBENEF_06698 = "06698";

    String UBP_ERR_CODE = "09999";

    String MCFE_CONTEXT = "MCFE_CONTEXT";

    String MSG_CATEGORY_ERROR = "ERROR";

    String MSG_CATEGORY_WARN = "WARN";

    String MSG_CATEGORY_FATAL = "FATAL";

    String MSG_CATEGORY_INFO = "INFO";

    /**
     * The "Informational" severity. Used to indicate a successful operation or result.
     */
    public static final String INFO = "Info";

    /**
     * The "Warning" severity. Used to indicate there is a minor problem, or to inform the message receiver of possible misuse, or to indicate a
     * problem may arise in the future.
     */
    public static final String WARNING = "Warning";

    /**
     * The "Error" severity. Used to indicate a significant problem like a business rule violation.
     */
    public static final String RECOVERABLE_ERROR = "RecoverableError";

    /**
     * The "Fatal" severity. Used to indicate a fatal problem like a system error.
     */
    public static final String UNRECOVERABLE_ERROR = "UnrecoverableError";

    public static final String RELOGIN_NEEDED_ERROR = "ReLoginNeededError";

    public static final String BILLREG_REFNUM_EMPTY_ERR = "06673";
    public static final String BILLREG_BUSINESS_ERR = "06000";
    public static final String BILLREG_NICK_EXISTS = "06696";
    public static final String BILLREG_BILLERID_REFERENCENO_EXISTS = "06697";

    String BEM_MESSAGE_PREFIX = "BEM";

    String CRYPTO_ERR_CHANGE_PWD = "200";
    String CRYPTO_ERR_CHANGE_PWD_BEM = "08732";

    String CRYPTO_UNKNOWN_EXCEPTION = "06001";
    String CRYPTO_SHOULD_CHANGE_VALIDATION_PASSED = "9430";
    String CRYPTO_SHOULD_CHANGE_VALIDATION_FAILED_ERR = "9431";

    String THM_CONNECTIVITY = "009";
}
