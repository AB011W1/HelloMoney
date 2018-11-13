package com.barclays.bmg.constants;

public class AuthServiceErrorCodeConstant {

    public static final String MW_HOST_RECORD_NOT_FOUND = "MW06201";
    public static final String MW_TOO_MANY_HOST_RECORDS_FOUND_BIR = "MW06203";
    public static final String MW_TOO_MANY_HOST_RECORDS_FOUND = "06203";

    public static final String MW_NO_SCV_CUST_FOUND = "MW00232";

    public static final String MW_INVALID_CUSTOMER_NUMBER = "06318";

    /**
     * show this error code instead of 06318
     */
    public static final String MW_INVALID_CUSTOMER_NUMBER_BIR = "MW06318";

    public static final String MW_CONNECT_HOST_EXCEPTION = "06001";

    public static final String MW_FINANCIAL_SERVICE_TIMEOUT = "06002";
    public static final String MW_VAS_TIMEOUT = "06702";
    // Add end by Li, Can; Change error mesage in case get remote exception for financial service; 4th April 2011
    public static final String MW_OVERRIDE_LIST_ERROR = "00240";
    public static final String MW_BRAINS_CONNECTION_ERROR = "06006";

    public static final String MW_CUSTOMER_NOT_EXIST = "7506";

    public static final String SEC_INVALID_PWD = "SEC00001";

    // SEC Security
    public static final String SEC_USER_RETURNED_TOO_MANY_RESULTS = "SEC00014";

    public static final String SEC_PASSWORD_POLICY_VIOLATION = "SEC00002";

    public static final String SEC_AUTH_SRV_CHANGE_ERROR = "SEC00003";

    public static final String SEC_AUTH_SRV_VERFIY_ERROR = "SEC00006";

    public static final String SEC_AUTH_USER_NOT_FOUND_ERROR = "SEC00004";

    public static final String SEC_REGISTRATION_LOGINNAME_EXIST = "SEC00005";

    public static final String SEC_NO_SQA_TOKEN_FOUND = "SEC00007";

    public static final String SEC_AUTH_USER_LOCKED = "SEC00008";

    public static final String SEC_NO_CUSTOMER_FOUND = "SEC01000";

    public static final String SEC_NO_CUSTOMER_FOUND_ID_INCORRECT = "SEC01001";

    public static final String SEC_NO_CUSTOMER_FOUND_DOB_INCORRECT = "SEC01002";

    public static final String SEC_NO_CUSTOMER_FOUND_ACC_INCORRECT = "SEC01003";

    public static final String SEC_NO_MOBILEPHONE_FOUND = "SEC02000";

    public static final String SEC_CUSTOMER_ACCOUNT_INTERNETBANKINGACCESSFLAG = "SEC04001";

    public static final String SEC_CUSTOMER_HIGHSEVERITYMEMOINDICATOR = "SEC04002";

    public static final String SEC_CUSTOMER_ACCOUNT_PRODUCTELIGIBILITY = "SEC04003";
    public static final String SEC_CUSTOMER_ACCOUNT_WRONG_CATEGORY_CODE = "SEC04004";
    public static final String SEC_CUSTOMER_CREDITCARD_PRODUCTELIGIBILITY = "SEC04005";
    public static final String SEC_CUSTOMER_ACCOUNT_RELATIONSHIP = "SEC04006";
    public static final String SEC_CUSTOMER_CREDITCARD_ENTITLEMENT = "SEC04007";

    public static final String SEC_CUSTOMER_NO_CREDIT_CARD_FOUND = "SEC04008";

    public static final String SEC_USER_ALREADY_EXIST = "SEC03000";
    public static final String SEC_NOT_BEEN_REGISTERED_FOR_INTERNET_BANKING = "SEC00015";

    public static final String SEC_AUTH_FAILURE = "SEC00009";

    public static final String SEC_USER_STATUS_ERROR = "SEC00010";

    public static final String SEC_USER_INVALID_USER_STATUS = "SEC00011";

    public static final String SEC_SQA_AUTH_FAILUE = "SEC00012";

    public static final String SEC_USER_DUPLICATE_ERROR = "SEC00013";
    // @Author: Liu Qingming
    // add a error code for dormant user status
    public static final String SEC_USER_DORMANT = "SEC00016";

    public static final String SEC_SEARCH_INDIVIDUAL_CUST_FOR_REGISTRATION_SERVICE_EXCEPTION = "SEC00140";

    public static final String SEC_LOAD_ALL_SQA_ERROR = "SEC00020";

    public static final String SEC_LOAD_USER_SQA_USER = "SEC00021";
    public static final String SEC_INSERT_USER_SQA = "SEC00022";
    public static final String SEC_INSERT_USER_PWD = "SEC00023";
    public static final String SEC_UPDATE_USER = "SEC00024";

    public static final String SEC_INCREASE_FAILURE_ATTEMPT_ERROR = "SEC00025";

    public static final String SEC_CLEAR_FAILURE_ATTEMPT_ERROR = "SEC00026";

    public static final String SEC_SQA_SAME_QUESION = "SEC00027";

    public static final String SEC_SQA_BLANK_ANSWER = "SEC00028";

    public static final String SEC_SQA_TOO_SHORT_ANSWER = "SEC00029";

    public static final String SEC_SQA_TOO_LONG_ANSWER = "SEC00030";

    public static final String SEC_SQA_QUESION_NUMBER_NOT_ENOUTH = "SEC00031";

    public static final String SEC_SQA_CONFIRMATION_ERROR = "SEC00032";

    public static final String SEC_SQA_DUPLICATED_ANSWER = "SEC00033";

    public static final String SEC_SQA_DUPLICATED_WITH_PASSWORD = "SEC00034";

    public static final String SEC_SQA_DUPLICATED_WITH_USERNAME = "SEC00035";

    public static final String RESP_CODE_SUCCESS = "00000";

    public static final String RESP_DESC_SUCCESS = "Success";

    public static final String RESP_DESC_TRY = "retry";

    public static final String SEC_CRYPTOMATHIC_GENERIC_ERROR = "SEC90001";

    public static final String SEC_SYSTEM_ERROR = "SEC00000";

    public static final String SEC_CRYPTOMATHIC_ERROR = "SED00001";

    public static final String RES_CODE_OTP_SERVICE_ERROR = "OTP00205";

    public static final String MW_SEARCH_INDIVIDUAL_CUST_FOR_REGISTRATION_SERVICE_EXCEPTION = "MW00140";

    public static final String SEC_INVALID_PASSWORD = "SEC00009";
    public static final String SEC_INVALID_USERNAME = "SEC00010";
    public static final String SEC_INVALID_OTP = "SEC00204";
    public static final String SEC_INVALID_SQA = "SEC00012";
    public static final String SEC_CHALLENGE_NOT_FOUND = "CHALLENGE-NOT-FOUND";
    public static final String SEC_CUSTOMER_NOT_EXIST = "7506";

}
