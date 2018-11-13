package com.barclays.bmg.constants;

public interface RequestConstants {

    String FUND_TRANSFER_INTERNAL = "IT";

    String MOBILE_NO = "Mobile_Number";
    String ACCOUNT_NO = "Account_Number";
    String BRANCH_CODE = "Branch_Code";

    String SELF_REG_INIT_FLOW_ID = "Self_Registration_Init";
    String SELF_REG_EXEC_FLOW_ID = "Self_Registration_Exec";

    String SECOND_AUTH_QUESTION_ID_AND_POSITIONS = "Second_Auth_Question_Id_And_Positions";
    String SECOND_AUTH_ANS_POSITION_1 = "Second_Auth_Ans_Position_1";
    String SECOND_AUTH_ANS_POSITION_2 = "Second_Auth_Ans_Position_2";

    String SERVICE_NAME_EDIT_BENEFICIARY = "editBenf";
    String SERVICE_NAME_DELETE_BENEFICIARY = "delBenf";
    String SERVICE_NAME_FUND_TRANSFEER = "fundtxBenf";
    String VERIFY_MIGRAGTED_USER_FLOW_ID = "Verify_Migrated_User";
    String VERIFY_MIGRAGTED_USER_SCV_ID = "Verify_Migrated_User_ScvId";
    String CUSTOMER_ID = "CustomerID";
    String USER_STATUS_IN_MCE = "UserStatusInMce";

    // Bmg call added to minimize response time for confirm screen
    String SCV_ID = "ScvId";

    // Bmg call added to minimize response time for last step
    String BANK_CIF = "bankcif";

}
