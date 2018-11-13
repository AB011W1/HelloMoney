package com.barclays.bmg.constants;

public interface ApplyTDResponseCodeConstant {

    String SUCCESS_CODE = "00000";
    String BEM_SERVICE_EXCEPTION = "BMB90004";
    String WARNING_CODE = "00001";
    String FCR_NO_ACCOUNT_EXCEPTION = "06754";

    // ACCOUNT ACTIVITY
    String SOURCE_ACCOUNT_LIST_RETURNED = "ATD0001";
    String SOURCE_ACCOUNT_LIST_NOT_RETURNED = "ATD0000";
    String APPLY_TD_INIT_SUCCESS = "Source account lits is returned to client";

    String APPLY_TD_INIT_NOT_SUCCESS = "List source account list is failed ";

    String ACT_ACTDETAIL_NOSTATEMENTDATE = "FTR00531";

    public String APPLY_TD_INCORRECT_TENURE = "APTD0003";
    public String APPLY_TD_INCORRECT_AMOUNT = "APTD0002";
    public String APPLY_TD_INCORRECT_ACCNUM = "APTD0001";

    String FCR_HOST_ERROR = "AAC00409";
    String PRIME_HOST_ERROR = "AAC00410";
    String TELESCO_HOST_ERROR = "AAC00411";

    String APPLY_TD_DAYS_LIMIT = "ATD90010";

}
