package com.barclays.bmg.constants;

public interface SessionConstant {

    String VERSION_RELEASE_ONE = "1.0";
    String VERSION_RELEASE_TWO = "2.0";

    String FIRST_STEP = "firstStep";
    String LAST_STEP = "lastStep";
    String PROCESS_REF_NO = "processRefNo";
    String STEP_ID = "stepId";

    String SESSION_PROCESS = "process";
    String SESSION_USER = "user";
    String SESSION_ACTIVITY = "activity";
    String SESSION_BUSINESS_ID = "businessId";
    String SESSION_USER_ID = "userId";
    String SESSION_LANGUAGE_ID = "languageId";
    String SESSION_SYSTEM_ID = "systemId";
    String SESSION_CUSTOMER_ID = "customerId";
    String SESSION_COUNTRY_CODE = "countryCode";
    String SESSION_CHALLENGE_ID = "challengeId";
    String SESSION_MOBILE_PHONE = "mobilePhone";
    String SESSION_QUESTION_ID = "questionId";
    String SESSION_ID = "sessionId";
    String SESSION_LOCAL_CURRENCY = "localCurrency";
    String SESSION_PP_MAP = "ppMap";
    String SESSION_CUSTOMER_DTO = "customerDTO";

    String SESSION_AUTH_REQUIRED = "authorisationRequired";
    String SESSION_AUTH_TYPE = "authorisationType";
    String SESSION_SMS_PARAMS = "smsParams";
    String SESSION_FULL_NAME = "fullName";
    String SESSION_LOGIN_ID = "loginId";
    String SESSION_LAST_LOGIN_TIME = "lastLoginTime";
    String DEVICE_ID = "BMB_DEVICE_ID";
    String DEVICE_OS_NAME = "BMB_DEVICE_OS_NAME";
    String DEVICE_OS_VESRION = "BMB_DEVICE_OS_VESRION";
    String DEVICE_MODEL_NAME = "BMB_DEVICE_MODEL_NAME";
    String APPLICATION_VERSION = "BMB_APPLICATION_VERSION";
    String SUCCESS_URL = "SUCCESS_URL";
    String USER_NAME = "userName";
    String LEFT_WARNING_DAYS = "leftWarningDays";
    String LEFT_WARNING_TXN_DAYS = "leftWarningTXNDays";
    String IS_NEED_CHANGE_PWD = "isNeedChangePWD";
    String IS_NEED_WARNING_CHAHGE_PWD = "isNeedWarningChangePWD";
    String IS_NEED_CHANGE_TXN_PWD = "isNeedChangeTXNPWD";
    String IS_NEED_WARNING_CHG_TXN_PWD = "isNeedWarningChangeTXNPassword";
    String IS_NEED_CHANGE_SQA = "isNeedChangeSQA";
    String SESSION_SECOND_AUTH_TYPE = "sessionSecondAuthType";

    String SESSION_BMG_SERVICE_VERSION = "BMG_SERVICE_VERSION";
    String SESSION_TIMEZONE_OFFSET = "TIMEZONE_OFFSET";
    String SESSION_PIN_STATUS = "PIN_STATUS";
    String SESSION_USR_STATUS = "USER_STATUS";
    String SESSION_ORGCODE = "SESSION_ORGCODE";

    //Changes for caching of account list & reduce one call to enhance performance
    String SESSION_ACCOUNT_LIST = "SESSION_ACCOUNT_LIST";
    
    //Auth Request
    String SESSION_DOCUMENT_LIST = "SESSION_DOCUMENT_LIST";
    String SESSION_AUTH_ACTIVITY_ID = "SESSION_AUTH_ACTIVITY_ID";
    
    //Added for KITS debtor name INC INC1009890417
    String SESSION_KITS_FULL_NAME = "SESSION_KITS_FULL_NAME";
}
