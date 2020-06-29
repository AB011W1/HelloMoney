package com.barclays.bmg.constants;

public interface SystemParameterConstant {

    String SECOND_AUTH_TYPE_SYSPARAM_KEY = "FEATURE_AUTH_TYP_LOGIN";
    String SERVICE_HEADER_STAFF_TYPE = "SERVICE_HEADER_STAFF_TYPE";
    String SERVICE_HEADER_AUTHORIZER_ID = "SERVICE_HEADER_AUTHORIZER_ID";
    String SERVICE_HEADER_STAFF_ID = "SERVICE_HEADER_STAFF_ID";
    String SERVICE_HEADER_STAFF_ID_CC = "SERVICE_HEADER_STAFF_ID_CC";

    String SERVICE_HEADER_BRANCH_CODE = "SERVICE_HEADER_BRANCH_CODE";
    String SERVICE_HEADER_TERMINAL_ID = "SERVICE_HEADER_TERMINAL_ID";
    String SERVICE_HEADER_TZNBC_BRANCH_CODE="1";

    String SERVICE_HEADER_CUST_ACCESS_ID = "SERVICE_HEADER_CUST_ACCESS_ID";
    String SERVICE_HEADER_CORP_USER_ID = "SERVICE_HEADER_CORP_USER_ID";
    String SERVICE_HEADER_CUST_TYPE = "SERVICE_HEADER_CUST_TYPE";
    String SERVICE_HEADER_RESP_POPULATE_FLAG = "SERVICE_HEADER_RESP_POPULATE_FLAG";
    String SERVICE_HEADER_MASTER_CIF_ID = "SERVICE_HEADER_MASTER_CIF_ID";
    String SERVICE_HEADER_SERVICE_VER_NO = "SERVICE_HEADER_SERVICE_VERSION_NO";
    String SERVICE_HEADER_SERVICE_VERSION_NO_GEPG  = "SERVICE_HEADER_SERVICE_VERSION_NO_GEPG";
    // System constants for Add problem
    String ADD_PRO_STAFF_ID = "ADD_PRO_STAFF_ID";
    String ADD_PRO_PRIORITY = "ADD_PRO_PRIORITY";
    String ADD_PRO_DEPART_ID = "ADD_PRO_DEPART_ID";
    String ADD_PRO_SERVICE_TYPE_ID = "ADD_PRO_SERVICE_TYPE_ID";
    String ADD_PRO_BUSINESS_AREA = "ADD_PRO_BUSINESS_AREA";
    String ADD_PRO_CASES_TATUS = "ADD_PRO_CASES_TATUS";
    String ADD_PRO_SERVICE_CATEGORY = "ADD_PRO_SERVICE_CATEGORY";
    String ADD_PRO_ORIGIN = "ADD_PRO_ORIGIN";
    String ADD_PRO_DESCRIPTION = "ADD_PRO_DESCRIPTION";

    String SECOND_AUTH_TYPE = "FEATURE_AUTH_TYP";
    String BUSINESS_BANK_CODE = "BUSINESS_BANK_CD";
    String SERVICE_HEADER_AUTHORIZER_ID_OTP = "SERVICE_HEADER_AUTHORIZER_ID_OTP";
    String COMMON_ACTIVITY_ID = "COMMON";

    int DEFAULT_BRANCH_CODE_LENGTH = 3;
    String BRANCH_CODE_LENGTH = "BRANCH_CODE_LENGTH";

    String DISPLAY_BRANCH_CODE_FOR_ACCOUNT = "DISPLAY_BRANCH_CODE_FOR_ACCOUNT";
    String ACCOUNT_MASKING_REQUIRED = "accountMasking_maskRequired";
    String ACCOUNT_NUMBER_MASK_PATTERN = "accountNumberMaskPattern";
    String CREDIT_CARD_NUMBER_MASK_PATTERN = "creditCardNumberMaskPattern";
    String MASK_ACCOUNT_SPLIT = "MASK_ACCOUNT_SPLIT";
    String SALIK_PMT_CHANNEL_CODE = "PMT_SALIK_CHANNEL_CODE";
    String SALIK_PMT_CHANNEL_LOCATION_CODE = "PMT_SALIK_CHANNEL_LOCATION_CODE";
    String OTP_MOBILE_FORMAT = "OTP_MOBILE_FORMAT";
    String SYSPARAM_FULLLENGTH_DOMESTIC_ACCOUNTNUMBER = "FULLLENGTH_DOMESTIC_ACCOUNTNUMBER";
    String ZONE_NUMBER = "ZONE_NUMBER";

    public static final String BUSINESS_DATE = "BUSINESS_DATE";
    public static final String SHORT_DATE_FORMAT_KEY = "dateFormatting_shortDateFormat";
    // System params for Bill Pay

    String BILLPAY_MAX_AMT = "BILLPAY_MAX_AMT";
    String BILLPAY_MIN_AMT = "BILLPAY_MIN_AMT";
    String BILLPAY_MAX_DAYAMT = "BILLPAY_MAX_DAYAMT";
    String BILLPAY_MAX_TXS = "BILLPAY_MAX_TXS";
    String POOL_AC_BILL_PAY = "POOL_AC_BILL_PAY";
    String LAST_PAID_BILL_LIST = "LAST_PAID_BILL_LIST";

    // System params for FT own
    String FTOWN_MAX_AMT = "FTOWN_MAX_AMT";
    String FTOWN_MIN_AMT = "FTOWN_MIN_AMT";
    String FTOWN_MAX_DAYAMT = "FTOWN_MAX_DAYAMT";
    String FTOWN_MAX_TXS = "FTOWN_MAX_TXS";

    // System params for FT Others
    String FTOTH_MAX_TXS = "FTOTH_MAX_TXS";
    String FTOTH_MIN_AMT = "FTOTH_MIN_AMT";
    String FTOTH_MAX_AMT = "FTOTH_MAX_AMT";
    String FTOTH_MAX_DAYAMT = "FTOTH_MAX_DAYAMT";

    // System params for DFT
    String FTDFT_MAX_AMT = "FTDFT_MAX_AMT";
    String FTDFT_MAX_TXS = "FTDFT_MAX_TXS";
    String FTDFT_MIN_AMT = "FTDFT_MIN_AMT";
    String FTDFT_MAX_DAYAMT = "FTDFT_MAX_DAYAMT";

    // System params for M Wallet
    String MWALLET_MAX_AMT = "MWALLET_MAX_AMT";
    String MWALLET_MIN_AMT = "MWALLET_MIN_AMT";
    String MWALLET_MAX_DAYAMT = "MWALLET_MAX_DAYAMT";
    String MWALLET_MAX_TXS = "MWALLET_MAX_TXS";

    // System params for Mobile TopUp
    String MIN_TOPUP_AMT = "MIN_TOPUP_AMT";
    String MAX_TOPUP_AMT = "MAX_TOPUP_AMT";
    String TOPUP_MAX_DAYAMT = "TOPUP_MAX_DAYAMT";
    String TOPUP_MAX_TXS = "TOPUP_MAX_TXS";

    // System params for Beneficiary
    // String OTHMAX_BENEFICIARIES="OTHMAX_BENEFICIARIES";
    // String DFTMAX_BENEFICIARIES="DFTMAX_BENEFICIARIES";
    // String MAX_BILLER_SIZE="MAX_BILLER_SIZE";

    String VIEW_MAX_BENFCR = "VIEW_MAX_BENFCR";
    String VIEW_MAX_BILLER_SIZE = "VIEW_MAX_BILLER_SIZE";
    String PAYEE_NICK_LENGTH_MAX = "PAYEE_NICK_LENGTHMAX";

    // System params for TD

    String MIN_DAYS_TD = "MIN_DAYS_TD";
    String MAX_DAYS_TD = "MAX_DAYS_TD";

    // Apply TD

    String APPLY_TD_USER_ID = "APPLY_TD_USER_ID";
    String APPLY_TD_SERVICE_TYPE = "APPLY_TD_SERVICE_TYPE";

    // Added for self registration account types
    String ACCOUNTTYPE_JOINTAND = "SELFREG_ACCOUNTTYPE_JOINTAND";//J defect #2046
    String ACCOUNTTYPE_MANYAND = "SELFREG_ACCOUNTTYPE_MANYAND";//M defect #2046
    String ACCOUNTTYPE_SOLO = "SELFREG_ACCOUNTTYPE_SOLO";// S
    String ACCOUNTTYPE_JOINTOR = "SELFREG_ACCOUNTTYPE_JOINTOR";// O
    String ACCOUNTTYPE_MANYOR = "SELFREG_ACCOUNTTYPE_MANYOR";// T
    String ACCOUNTTYPE_JOINTANDFIRST = "SELFREG_ACCOUNTTYPE_JOINTANDFIRST";// JAF //Changes for CR06

    String ACCOUNTTYPE_JOINTORFIRST = "SELFREG_ACCOUNTTYPE_JOINTORFIRST";// JOF //Changes for CR06
    String ACCOUNTTYPE_JOINTOROTHER = "SELFREG_ACCOUNTTYPE_JOINTOROTHER";// JOO //Changes for CR06

  //Added so as to involve self registration for AUS customers in KE start
    String SELFREG_ACCOUNTTYPE_AUS = "SELFREG_ACCOUNTTYPE_AUS";// AUS account type to enable AUS customers in Kenya
    String ACCOUNTTYPE_AUS = "ACCOUNTTYPE_AUS";// AUS account type to enable AUS customers in Kenya

    // System params for M Wallet
    String CS_MAX_AMT = "CS_MAX_AMT";
    String CS_MIN_AMT = "CS_MIN_AMT";
    String CS_MAX_DAYAMT = "CS_MAX_DAYAMT";
    String CS_MAX_TXS = "CS_MAX_TXS";

    // Added for CR011 - Handling of various account status and currency for various functionalities
    String CURRENCY_CODE_USD = "CURRENCY_CODE_USD";
    String CURRENCY_CODE_EURO = "CURRENCY_CODE_EURO";
    String CURRENCY_CODE_POUND = "CURRENCY_CODE_POUND";
    String CASA_ACCOUNT_STATUS_ACTIVE = "CASA_ACCOUNT_STATUS_ACTIVE";
    String CASA_ACCOUNT_STATUS_BLOCKED = "CASA_ACCOUNT_STATUS_BLOCKED";
    String CASA_ACCOUNT_STATUS_SUSPENDED = "CASA_ACCOUNT_STATUS_SUSPENDED";
    String CASA_ACCOUNT_STATUS_DORMANT = "CASA_ACCOUNT_STATUS_DORMANT";
    String CASA_ACCOUNT_TYPE_CURRENT = "CASA_ACCOUNT_TYPE_CURRENT";
    String CASA_ACCOUNT_TYPE_SAVING = "CASA_ACCOUNT_TYPE_SAVING";

    String VISIONPLUS_PRIME = "VISIONPLUS_PRIME";
    String VISIONPLUS_PRIME_VALUE = "VISIONPLUS";

    String CREDIT_CARD_ATAGLANCE_BLOCK_CODE = "CREDIT_CARD_ATAGLANCE_BLOCK_CODE";
    String CREDIT_CARD_ATAGLANCE_ACCOUNT_BLOCK_CODE = "CREDIT_CARD_ATAGLANCE_ACCOUNT_BLOCK_CODE";

    String CREDIT_CARD_STATEMENT_BLOCK_CODE = "CREDIT_CARD_STATEMENT_BLOCK_CODE";
    String CREDIT_CARD_STATEMENT_ACCOUNT_BLOCK_CODE = "CREDIT_CARD_STATEMENT_ACCOUNT_BLOCK_CODE";

    String CREDIT_CARD_PAYMENT_BLOCK_CODE = "CREDIT_CARD_PAYMENT_BLOCK_CODE";
    String CREDIT_CARD_PAYMENT_ACCOUNT_BLOCK_CODE = "CREDIT_CARD_PAYMENT_ACCOUNT_BLOCK_CODE";

    String CC_ACTION_CODE = "CC_ACTION_CODE";
    String CC_STORE = "CC_STORE";

    // to allow RAND currency
    String CURRENCY_CODE_RAND = "CURRENCY_CODE_RAND";

    /** credit card statmentlist. */
    public static final String CC_STATEMENT_LIST = "CC_STATEMENT_LIST";
    /** The sys param CC. */

    public static final String SYS_PARAM_CC = "SYS_PARAM_CC";
    public static final String SYS_PARAM_CCOWN = "SYS_PARAM_CCOWN";
    public static final String CCOWN_MAX_AMT = "CCOWN_MAX_AMT";
    public static final String CCOWN_MIN_AMT = "CCOWN_MIN_AMT";
    public static final String CCOWN_MAX_DAYAMT = "CCOWN_MAX_DAYAMT";
    public static final String CCOWN_MAX_TXS = "CCOWN_MAX_TXS";

    public static final String SHM_USSD_SERVICE_ENABLER = "SHM_USSD_SERVICE_ENABLER";

    //CR-64 Credit card Payment EBOX
    public static final String CREDIT_CARD_PAYMENT_GL_SUSPENSE_ACCOUNT = "CREDIT_CARD_PAYMENT_GL_SUSPENSE_ACCOUNT";
    public static final String CREDIT_CARD_PAYMENT_COU_BRANCH_ID = "CREDIT_CARD_PAYMENT_COU_BRANCH_ID";

    public static final String SYS_PARAM_GHIPPS = "SYS_PARAM_GHIPPS";
    public static final String FTRT_MAX_AMT = "FTRT_MAX_AMT";
    public static final String FTRT_MAX_TXS = "FTRT_MAX_TXS";
    public static final String FTRT_MIN_AMT = "FTRT_MIN_AMT";
    public static final String FTRT_MAX_DAYAMT = "FTRT_MAX_DAYAMT";

    // Service Version No. for MakeBillPayment and FundTransfer of CBP 30/08/2017
    String SERVICE_HEADER_SERVICE_VER_NO_CBP = "SERVICE_HEADER_SERVICE_VERSION_NO_CBP";
    String isCBPFLAG="isCBPFLAG";

	String isLoanRepayment = "isLoanRepayment";

	//Service Version No. for SSAMakeBillPayment GHIPS2
	String SERVICE_HEADER_SERVICE_VER_NO_GHIPPS2 = "SERVICE_HEADER_SERVICE_VERSION_NO_GHIPPS2";
	String isGHIPS2Flag="isGHIPS2Flag";

	//Service Version No. for SSAMakeBillPayment Probase
	String SERVICE_HEADER_SERVICE_VER_NO_PROBASE = "SERVICE_HEADER_SERVICE_VER_NO_PROBASE";
	String isProbaseFlag="isProbaseFlag";
	
	 //Set welcome banner in customerDTO for INC INC0063990
    String LOGIN_ANNOUNCE_FLAG = "LOGIN_ANNOUNCE_FLAG";
    String LOGIN_ANNOUNCE_L1 = "LOGIN_ANNOUNCE_L1";

    //Parameters for USSD Sybrin Auth Request
    public static final String AUTH_REQUEST_LOGIN_USERNAME = "USSDAUTHENTICATIONUSERNAME";
    public static final String AUTH_REQUEST_LOGIN_PASSWORD = "USSDAUTHENTICATIONPASSWORD";
    public static final String AUTH_REQUEST_ENTITY_ID = "USSDAUTHENTICATIONENTITYID";
    public static final String AUTH_REQUEST_PROCESS_ID = "USSDAUTHENTICATIONPROCESSID";
    public static final String AUTH_REQUEST_LOCK_RECORDS = "USSDAUTHENTICATIONLOCKRECORDS";
    public static final String AUTH_REQUEST_SERVICE_ENDPOINT = "USSDAUTHENTICATION_SERVICE_ENDPOINT";
    
    //Credit Card Migration-Plan no. Addition
    String PLAN_NUMBER_BILL_PAYMENT="PLANNUMBERBILLPAYMENT";
    String PLAN_NUMBER_FUND_TRANSFER="PLANNUMBERFUNDTRANSFER";
    String TRANSACTION_SUPRESS_LOAN = "TRANSACTION_SUPRESS_LOAN";
    String TRANSACTION_FOREIGN_FEES_CODE = "TRANSACTION_FOREIGN_FEES_CODE";
    String IS_CC_TRX_MERGE_REQD = "IS_CC_TRX_MERGE_REQD";
    String TRX_SUPRS_CC_ROW_COUNT = "TRX_SUPRS_CC_ROW_COUNT";
    String TRX_SUPRS_CREDIT_CARD = "TRX_SUPRS_CREDIT_CARD";
    String CREDIT_CARD_DISABLED_ALL = "CREDIT_CARD_DISABLED_ALL";
    
}
