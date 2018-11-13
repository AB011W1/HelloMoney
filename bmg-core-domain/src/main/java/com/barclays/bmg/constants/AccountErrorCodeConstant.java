package com.barclays.bmg.constants;

public interface AccountErrorCodeConstant {

    String ERROR_CODE_1002 = "1002";
    String ERROR_CODE_1001 = "1001";

    String SUCCESS_CODE = "00000";
    String PARTIAL_SUCCESS_CODE = "03000";

    String ACCOUNT_NO_NOT_EXIST = "7502";
    String NO_TRANX_FOUND = "06773";

    String CC_ACCOUNT_NO_NOT_EXIST = "06713";
    String CC_INVALID_STMT_DATE = "06811";
    String CC_ACCOUNT_NO_NOT_EMPTY_PRIME = "06815";
    String CC_TXN_ACTIVITY_EXCEPTION = "06002";
    String NO_TRANSACTIONS_FOUND_FOR_REQUEST = "U9043";
    String CREDIT_CARD_NO_NOT_EXIST = "0612";
    String NO_UNBILLED_TRANX_FOUND = "U9043";
    String NO_STATEMENTS_FOUND = "U9040";
    String NO_BRANCH_FOUND = "SHM009";
    String NO_ATM_FOUND = "SHM008";
    String NO_ATM_BRANCH_FOUND = "SHM007";

}
