package com.barclays.bmg.constants;

public interface AuthResponseCodeConstants {

    String AUTH_SUCCESS = "00000";
    String AUTH_WARNING = "00001";
    String AUTH_INVALID_SQA = "ATH00102";
    String AUTH_INVALID_OTP = "ATH00104";
    String INVALID_MOBILE_NUMBER = "ATH00108";
    String OTP_EMPTY = "ATH00109";
    String SQA_EMPTY = "ATH00110";
    String AUTH_OTP_EXPIRED = "ATH00111";
    String AUTH_INVALID_USERNAME = "ATH00113"; // Changed code from ATH00112 to ATH00113
    String AUTH_INVALID_PASSWORD = "ATH00113";
    String SECOND_AUTH_INVALID = "ATH00114";
    String SESSION_EXPIRED = "ATH00115";
    String USERNAME_EMPTY = "ATH00117";
    String PASSWORD_EMPTY = "ATH00118";
    String USER_LOCKED = "ATH00119";
    String NO_MOBILE_ERROR = "ATH00120";
    String USER_INACTIVE = "ATH00121";
    String AUTH_CUSTOMER_NOT_EXIST = "ATH00122";
    String SEC_LOGIN_LGN_WARNING = "ATH00123";
    String PASSWORD_EXPIRED = "ATH00124";
    // Challenge Question with Position
    String AUTH_INVALID_MOBILE_NO = "ATH00125";
    String AUTH_INVALID_SCV_ID = "ATH00126";
    String AUTH_CHANGE_PWD = "ATH00200";
    String AUTH_INVALID_CREDENTIALS = "ATH00113";
    String MOBILE_NO_EMPTY = "ATH00130";
}
