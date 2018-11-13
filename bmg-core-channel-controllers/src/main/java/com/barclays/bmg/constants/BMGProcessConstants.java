package com.barclays.bmg.constants;

public interface BMGProcessConstants {

    String LOGIN_PROCESS_KEY = "login_process";
    String BANK_DRAFT_PROCESS_KEY = "bank_draft_process";
    String TXN_SECOND_LEVEL_AUTHENTICATION = "txn_scnd_lvl_auth_process";
    String OWN_AND_INTERNAL_FUND_TRANSFER = "own_and_Internal_FT_prcess";
    String BILL_PAYMENT = "billPayment_process";
    String KITS_PAYMENT = "kitsPayment_process";
    String CREDIT_PAYMENT="credit_process";
    String NON_BARCLAYS_FUND_TRANSFER = "non_barclays_FT_process";
    String CHEQUE_BOOK_PROCESS = "cheque_book_process";
    String ACCOUNTS_PROCESS = "accounts_process";
    String ADD_ORG_BENEFICIARY = "biller_registration_process";
    String INTERNAL_NONREGISTERED_FUND_TRANSFER = "Internal_NonRegistered_FT_prcess";
    String ADD_BENEFICIARY = "add_beneficiary_process";
    String APPLY_TD = "apply_td_process";
    String STMT_REQ_PROCESS = "statement_request_process";

    // Constants Created for Second Auth
    String SECOND_AUTHENTICATION = "Second_Auth_process";
    String SECOND_AUTH_RESPONSE = "Second_Auth_Response";

    // Constants Created Self Registration
    String SELF_REGISTRATION_INIT = "Self_Registration_Init_process";
    String SELF_REG_INIT_FLOW_ID = "SR_INIT";

    String SELF_REGISTRATION_EXEC = "Self_Registration_Exec_process";
    String SELF_REG_EXEC_FLOW_ID = "SR_EXEC";

    // Constants Created CASH SEND
    String CASH_SEND_PROCESS = "cash_send_process";
    String FX_RATE_PROCESS = "fx_rate_process";
    String UPDATE_BENEFICIARY = "update_beneficiary_process";
    String CREDIT_CARD_PAYMENT = "makeCreditCardPayment_process";

    String ATM_BRANCH_LOCATOR = "atm_branch_process";

    String VERIFY_MIGRATED_USER = "verify_migrated_user_process";
    String VERIFY_MIGRATED_USER_FLOW_ID = "VMU_INIT";

    String USER_MIGRATED_STATUS_IN_MCE = "MIGRATED";

    //	ADDED TO INCLUDE DEBIT CARD VALIDATION IN SELF REGISTRATION
    String DEBIT_CARD_AUTHENTICATION="Debit_Card_Auth_process";
    String DEBIT_CARD_RESPONSE = "Debit_Card_Auth_Response";

    // ADDED on 03/10/2016 G01022861 For KITS Biller Details
    String KITS_BILLER_ORGANIGATIONCODE_CATEGORYID="PesaLink-2";
    String KITS_BILLER_ORGANIGATIONCODE_BILLERID="PesaLink-2";
    String KITS_BILLER_ORGANIGATIONCODE_ISDCODE="254";


}
