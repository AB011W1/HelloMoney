/* Copyright 2008 Barclays PLC */

package com.barclays.bmg.constants;

public class ActivityIdConstantBean implements java.io.Serializable {

    private static final long serialVersionUID = 929473231274832730L;

    public static final String TXN_HISTORY_ACTIVITY_ID = "TXN_HISTORY";

    public static final String CASA_DETAIL_ACTIVITY_ID = "ACT_ACCOUNT_DETAIL_CASA_DETAIL";

    public static final String CREDIT_DETAIL_ACTIVITY_ID = "ACT_ACCOUNT_DETAIL_CREDIT_DETAIL";

    public static final String TD_DETAIL_ACTIVITY_ID = "ACT_ACCOUNT_DETAIL_TD_DETAIL";

    public static final String LOAN_DETAIL_ACTIVITY_ID = "ACT_ACCOUNT_DETAIL_LOAN_DETAIL";

    public static final String INVEST_DETAIL_ACTIVITY_ID = "ACT_ACCOUNT_DETAIL_INVEST_DETAIL";

    public static final String INSUR_DETAIL_ACTIVITY_ID = "ACT_ACCOUNT_DETAIL_INSUR_DETAIL";

    public static final String ACCOUNT_DETAIL_ACTIVITY_ID = "ACT_ACCOUNT_DETAIL";

    public static final String ACCOUNT_SUMMARY_ACTIVITY_ID = "ACT_ACCOUNTSERVICE_ACCOUNTSUMMARY";

    public static final String ACCOUNT_ESAVER_ACTIVITY_ID = "ACT_ACCOUNTSERVICE_ESAVER";

    public static final String SITE_MAP_ACTIVITY_ID = "POT_SITEMAP";

    public static final String ALERT_MANAGEMENT = "CUS_ALERT_ALERTMANAGEMENT";

    public static final String CARD_TRANSACTION_DISPUTE = "CRD_DISPUTE";

    public static final String SSC_DEFAULT = "SSC_DEFAULT";

    public static final String UNBILLED_TXN_ACTIVITY_ID = "UNBILLED_TXN";

    public static final String UPDATE_LANG_ACTIVITY_ID = "UPDATE_LANGUAGE_PREF";

    /** payee maintenance start */
    public static final String PAYEE_MAINTENANCE_ACTIVITY_ID = "PMT_PAYEE_LIST";

    public static final String ADD_PAYEE_ACTIVITY_ID = "PMT_PAYEE_ADD_BP";

    public static final String ADD_MTP_PAYEE_ACTIVITY_ID = "PMT_PAYEE_ADD_MTP";

    public static final String ADD_INTERNAL_PAYEE_ACTIVITY_ID = "PMT_PAYEE_ADD_INTERNAL";

    public static final String ADD_EXTERNAL_PAYEE_ACTIVITY_ID = "PMT_PAYEE_ADD_EXTERNAL";

    public static final String UPD_INTERNAL_PAYEE_ACTIVITY_ID = "PMT_PAYEE_UPD_INTERNAL";

    public static final String UPD_EXTERNAL_PAYEE_ACTIVITY_ID = "PMT_PAYEE_UPD_EXTERNAL";

    public static final String ADD_INTERNATIONAL_PAYEE_ACTIVITY_ID = "PMT_PAYEE_ADD_INTL";

    public static final String ADD_BARCLAYCARD_PAYEE_ACTIVITY_ID = "PMT_PAYEE_ADD_BCD";

    public static final String ADD_BANKDRAFT_PAYEE_ACTIVITY_ID = "PMT_PAYEE_ADD_BKD";

    public static final String DELETE_PAYEE_ACTIVITY_ID = "PMT_PAYEE_DELETE_BP";

    public static final String DELETE_MTP_PAYEE_ACTIVITY_ID = "PMT_PAYEE_DELETE_MTP";

    public static final String DELETE_BARCLAYCARD_PAYEE_ACTIVITY_ID = "PMT_PAYEE_DELETE_BCD";

    public static final String DELETE_BANKDRAFT_PAYEE_ACTIVITY_ID = "PMT_PAYEE_DELETE_BKD";

    public static final String DELETE_INTERNAL_PAYEE_ACTIVITY_ID = "PMT_PAYEE_DELETE_INTERNAL";

    public static final String DELETE_EXTERNAL_PAYEE_ACTIVITY_ID = "PMT_PAYEE_DELETE_EXTERNAL";

    public static final String DELETE_INTERNATIONAL_PAYEE_ACTIVITY_ID = "PMT_PAYEE_DELETE_INTL";

    public static final String ACTIVATE_PAYEE_ACTIVITY_ID = "PMT_PAYEE_ACTIVATE_BP";

    public static final String ACTIVATE_BARCLAYCARD_PAYEE_ACTIVITY_ID = "PMT_PAYEE_ACTIVATE_BCD";

    public static final String ACTIVATE_BANKDRAFT_PAYEE_ACTIVITY_ID = "PMT_PAYEE_ACTIVATE_BKD";

    public static final String ACTIVATE_INTERNAL_PAYEE_ACTIVITY_ID = "PMT_PAYEE_ACTIVATE_INTERNAL";

    public static final String ACTIVATE_EXTERNAL_PAYEE_ACTIVITY_ID = "PMT_PAYEE_ACTIVATE_EXTERNAL";

    public static final String ACTIVATE_INTERNATIONAL_PAYEE_ACTIVITY_ID = "PMT_PAYEE_ACTIVATE_INTL";

    /** payee maintenance end */

    public static final String BANK_LOOKUP_ACTIVITY_ID = "PMT_FT_BANK_LOOKUP";

    public static final String SWIFTCD_LOOKUP_ACTIVITY_ID = "PMT_FT_SWIFT_LOOKUP";

    /** bill pay start */
    public static final String BILL_PAYMENT_FACADE = "PMT_BP_FACADE";

    public static final String BILL_PAYMENT_ONETIME_ACTIVITY_ID = "PMT_BP_BILLPAY_ONETIME";

    public static final String BILL_PAYMENT_PAYEE_ACTIVITY_ID = "PMT_BP_BILLPAY_PAYEE";

    public static final String MOBILE_TOPUP_ACTIVITY_ID = "PMT_BP_MOBILE_TOPUP_PAYEE";

    public static final String ACTIVATE_MOBILE_TOPUP_PAYEE_ACTIVITY_ID = "PMT_PAYEE_ACTIVATE_MTP";

    public static final String BARCLAY_CARD_PAYMENT_ACTIVITY_ID = "PMT_BP_BCD_PAYEE";

    public static final String BARCLAY_CARD_PAYMENT_ONETIME__ACTIVITY_ID = "PMT_BP_BCD_ONETIME";

    public static final String MOBILE_TOPUP_ONETIME_ACTIVITY_ID = "PMT_BP_MOBILE_TOPUP_ONETIME";

    public static final String MOBILE_WALLET_ONETIME_ACTIVITY_ID = "PMT_BP_MOBILE_WALLET_ONETIME";

    public static final String MOBILE_TOPUP_FACADE_ACTIVITY_ID = "MOBILE_TOPUP_FACADE";

    /** bill pay end */
    public static final String BULK_BILL_PAY = "PMT_BULK_BILLPAY";

    public static final String LOAN_REPAYMENT_ACTIVITY_ID = "PMT_FT_LOAN_REPAYMENT";

    // public static final String BILL_PAYMENT_ACTIVITY_ID = "BILL_PAY";
    public static final String BILL_PAYMENT_SUPPORT_INQUIRE_ACTIVITY_ID = "BP_SUPPORT_INQUIRE";

    public static final String BANK_SELECT_ACTIVITY_ID = "BANK_SELECT";

    public static final String FUND_TRANSFER_OWN_ACTIVITY_ID = "PMT_FT_OWN";

    public static final String FUND_TRANSFER_SI_OWN_ACTIVITY_ID = "PMT_FT_SI_OWN";

    public static final String FUND_TRANSFER_CARD_PAYMENT_ONETIME_ACTIVITY_ID = "PMT_FT_CARD_PAYMENT_OWN";

    public static final String FUND_TRANSFER_CARD_PAYMENT_PAYEE_ACTIVITY_ID = "PMT_FT_CARD_PAYMENT_PAYEE";

    public static final String FUND_TRANSFER_INTERNAL_ONETIME_ACTIVITY_ID = "PMT_FT_INTERNAL_ONETIME";

    public static final String FUND_TRANSFER_INTERNAL_PAYEE_ACTIVITY_ID = "PMT_FT_INTERNAL_PAYEE";

    public static final String FUND_TRANSFER_SI_INTERNAL_ONETIME_ACTIVITY_ID = "PMT_FT_SI_INTERNAL_ONETIME";

    public static final String FUND_TRANSFER_SI_INTERNAL_PAYEE_ACTIVITY_ID = "PMT_FT_SI_INTERNAL_PAYEE";

    public static final String FUND_TRANSFER_EXTERNAL_ONETIME_ACTIVITY_ID = "PMT_FT_EXTERNAL_ONETIME";

    public static final String FUND_TRANSFER_EXTERNAL_PAYEE_ACTIVITY_ID = "PMT_FT_EXTERNAL_PAYEE";

    public static final String FUND_TRANSFER_SI_EXTERNAL_ONETIME_ACTIVITY_ID = "PMT_FT_SI_EXTERNAL_ONETIME";

    public static final String FUND_TRANSFER_SI_EXTERNAL_PAYEE_ACTIVITY_ID = "PMT_FT_SI_EXTERNAL_PAYEE";

    public static final String FUND_TRANSFER_INTERNATIONAL_ONETIME_ACTIVITY_ID = "PMT_FT_INTL_ONETIME";

    public static final String FUND_TRANSFER_INTERNATIONAL_PAYEE_ACTIVITY_ID = "PMT_FT_INTL_PAYEE";

    public static final String FUND_TRANSFER_SI_INTERNATIONAL_ONETIME_ACTIVITY_ID = "PMT_FT_SI_INTL_ONETIME";

    public static final String FUND_TRANSFER_SI_INTERNATIONAL_PAYEE_ACTIVITY_ID = "PMT_FT_SI_INTL_PAYEE";

    public static final String FUND_TRANSFER_BANK_DRAFT_FACADE_ACTIVITY_ID = "PMT_FT_BKD_FACADE";

    public static final String FUND_TRANSFER_BANK_DRAFT_ONETIME_ACTIVITY_ID = "PMT_FT_BKD_ONETIME";

    public static final String FUND_TRANSFER_BANK_DRAFT_PAYEE_ACTIVITY_ID = "PMT_FT_BKD_PAYEE";

    public static final String CHANGE_PASSWORD_ACTIVITY_ID = "SEC_CHG_PWD";

    public static final String CHANGE_SQA_ACTIVITY_ID = "SEC_CHG_SQA";

    public static final String FORGOT_PASSWORD_ACTIVITY_ID = "SEC_FORGOT_PWD";

    public static final String FORGOT_SQA_ACTIVITY_ID = "SEC_FORGOT_SQA";

    public static final String FORGOT_USERNAME_ACTIVITY_ID = "SEC_FORGOT_UNAME";

    public static final String LOGIN_ACTIVITY_ID = "SEC_LOGIN";

    public static final String ACTIVITY_ID = "activityId";

    public static final String ONLINE_REGISTRATION_ACTIVITY_ID = "SEC_ONLN_REG";
    public static final String PREONLINE_REGISTRATION_ACTIVITY_ID = "SEC_PREONLN_REG";

    public static final String LOGOUT_ACTIVITY_ID = "SEC_LOGOUT";

    public static final String FUND_TRANSFER_FACADE = "PMT_FT_FACADE";

    // public static final String FUND_TRANSFER_INTERNAL_ACTIVITY_ID =
    // "FT_INTERNAL";

    //
    // public static final String LOAN_REPAYMENT_FT_ACTIVITY_ID =
    // "LOAN_REPAYMENT_FT";

    public static final String OPEN_TERMDEPOSIT_ACTIVITY_ID = "ACT_OPEN_TD";

    public static final String CHANGE_TERMDEPOSIT_ACTIVITY_ID = "ACT_CHANGE_TD";

    public static final String FX_RATES_ACTIVITY_ID = "CUS_FX_RATES";

    public static final String CUR_GLOSSARY_ACTIVITY_ID = "CUR_GLOSSARY";

    private static final String INT_RATES_CASA_ACTIVITY_ID = "CUS_INT_RATES_CASA";

    private static final String INT_RATES_TD_ACTIVITY_ID = "CUS_INT_RATES_TD";

    public static final String REQ_LIABILITY_LETTER = "CUS_REQ_LIABILITY_LETTER";

    public static final String VIEW_EMESSAGE = "CUS_EMESSAGE";

    public static final String REPORT_LOST_CREDIT_CARD_ACTIVITY_ID = "CRD_REPORTLOST";

    private static final String ESTATEMENT = "CUS_ESTMT";

    public static final String ORDER_PAPER_STMT = "CUS_ORDER_PAPER_STMT";

    public static final String COMMON = "COMMON";

    public static final String SEC_COMMON = "SEC_COMMON";

    public static final String REQ_BRANCH_LETTER = "CUS_REQ_BRANCH_LETTER";

    public static final String ACTIVITY_INQUIRY = "CUS_ACTIVITY_INQUIRY";

    public static final String CUS_PROFILE_PREFERENCE = "CUS_PROFILE_PREFERENCE";

    public static final String CRD_ADD_SUPPLEMENTARY_CARD = "CRD_ADD_SUP_CARD";

    public static final String CRD_REPLACE_CARD = "CRD_REPLACE_CARD";

    public static final String CRD_INSURANCE_ENQUIRY = "CRD_INSURANCE_ENQUIRY";

    public static final String CRD_ACTIVATE = "CRD_ACTIVATE";

    public static final String CRD_LIMIT = "CRD_LIMIT";

    public static final String CRD_PIN = "CRD_PIN";

    public static final String LINK_MY_ACCOUNTS = "CUS_LINK_ACCOUNT";

    public final static String PMT_FT_CASHSEND_ONETIME = "PMT_FT_CS";

    // Added for Internal One Time Non Registered Fund Transfer
    public static final String FUND_TRANSFER_INTERNAL_NONREGISTERED_PAYEE_ACTIVITY_ID = "PMT_FT_NR_INTERNAL_PAYEE";

    public final static String CREDIT_CARD_PAYMENT_ACTIVITY_ID = "PMT_BP_BCD_ONETIME";

    public static final String GHIPPS_FUND_TRANSFER_OTHER_BANK_ACCOUNTS = "GHIPPS_FT_OB_ACCOUNTS";

    public static String getFUNDTRANSFERINTERNALNONREGISTEREDPAYEEACTIVITYID() {
	return FUND_TRANSFER_INTERNAL_NONREGISTERED_PAYEE_ACTIVITY_ID;
    }

    public String getCardActivationActivityId() {
	return CRD_ACTIVATE;
    }

    public String getCreditLineMaintenanceActivityId() {
	return CRD_LIMIT;
    }

    public String getCreditCardPinRequestActiviatyId() {
	return CRD_PIN;
    }

    public String getReportLostCCActivityId() {
	return REPORT_LOST_CREDIT_CARD_ACTIVITY_ID;
    }

    public String getReqBranchLetterActivityId() {
	return REQ_BRANCH_LETTER;
    }

    public String getBulkBillPayActivityId() {
	return BULK_BILL_PAY;
    }

    // public String getLoanRepaymentFtId() {
    // return LOAN_REPAYMENT_FT_ACTIVITY_ID;
    // }

    public String getFxRatesActivityId() {
	return FX_RATES_ACTIVITY_ID;
    }

    public String getCurGlossaryActivityId() {
	return CUR_GLOSSARY_ACTIVITY_ID;
    }

    public String getOpenTdActivityId() {
	return OPEN_TERMDEPOSIT_ACTIVITY_ID;
    }

    public String getChangeTdActivityId() {
	return CHANGE_TERMDEPOSIT_ACTIVITY_ID;
    }

    public String getSwiftCodeLookUpActivityId() {
	return SWIFTCD_LOOKUP_ACTIVITY_ID;
    }

    public String getFundTransferFacadeActivityId() {
	return FUND_TRANSFER_FACADE;
    }

    public String getBillPaymentFacadeActivityId() {
	return BILL_PAYMENT_FACADE;
    }

    /**
     * @return TXN_HISTORY_ACTIVITY_ID
     */
    public String getLoanRepaymentActivityId() {
	return LOAN_REPAYMENT_ACTIVITY_ID;
    }

    /**
     * @return TXN_HISTORY_ACTIVITY_ID
     */
    public String getTxnHistoryActivityId() {
	return TXN_HISTORY_ACTIVITY_ID;
    }

    /**
     * @return ACCOUNT_SUMMARY_ACTIVITY_ID
     */
    public String getAccountSummaryActivityId() {
	return ACCOUNT_SUMMARY_ACTIVITY_ID;
    }

    /**
     * @return SSC_DEFAULT
     */
    public String getSscDefault() {
	return SSC_DEFAULT;
    }

    /**
     * @return UNBILLED_TXN_ACTIVITY_ID
     */
    public String getUnbilledTxnActivityId() {
	return UNBILLED_TXN_ACTIVITY_ID;
    }

    /**
     * @return UPDATE_LANG_ACTIVITY_ID
     */
    public String getUpdateLangActivityId() {
	return UPDATE_LANG_ACTIVITY_ID;
    }

    /**
     * @return ADD_PAYEE_ACTIVITY_ID
     */
    public String getAddPayeeActivityId() {
	return ADD_PAYEE_ACTIVITY_ID;
    }

    /**
     * @return ADD_INTERNAL_PAYEE_ACTIVITY_ID
     */
    public String getAddInternalPayeeActivityId() {
	return ADD_INTERNAL_PAYEE_ACTIVITY_ID;
    }

    /**
     * @return ADD_EXTERNAL_PAYEE_ACTIVITY_ID
     */
    public String getAddInternationalPayeeActivityId() {
	return ADD_INTERNATIONAL_PAYEE_ACTIVITY_ID;
    }

    /**
     * @return ADD_EXTERNAL_PAYEE_ACTIVITY_ID
     */
    public String getAddBarclaycardPayeeActivityId() {
	return ADD_BARCLAYCARD_PAYEE_ACTIVITY_ID;
    }

    /**
     * @return ADD_EXTERNAL_PAYEE_ACTIVITY_ID
     */
    public String getAddExternalPayeeActivityId() {
	return ADD_EXTERNAL_PAYEE_ACTIVITY_ID;
    }

    /**
     * @return BANK_LOOKUP_ACTIVITY_ID
     */
    public String getBankLookupActivityId() {
	return BANK_LOOKUP_ACTIVITY_ID;
    }

    /**
     * @return BILL_PAYMENT_ONETIME_ACTIVITY_ID
     */
    public String getBillPaymentOnetimeActivityId() {
	return BILL_PAYMENT_ONETIME_ACTIVITY_ID;
    }

    /**
     * @return BILL_PAYMENT_PAYEE_ACTIVITY_ID
     */
    public String getBillPaymentPayeeActivityId() {
	return BILL_PAYMENT_PAYEE_ACTIVITY_ID;
    }

    // /**
    // * @return BILL_PAYMENT_ACTIVITY_IDs
    // */
    // public String getBillPaymentActivityId() {
    // return BILL_PAYMENT_ACTIVITY_ID;
    // }
    //
    /**
     * @return FUND_TRANSFER_INTERNAL_ACTIVITY_IDs
     */

    /**
     * @return MOBILE_TOPUP_ACTIVITY_ID
     */
    public String getMobileTopupActivityId() {
	return MOBILE_TOPUP_ACTIVITY_ID;
    }

    /**
     * @return MOBILE_TOPUP_ACTIVITY_ID
     */
    public String getBarclaycardPaymentActivityId() {
	return BARCLAY_CARD_PAYMENT_ACTIVITY_ID;
    }

    /**
     * @return MOBILE_TOPUP_ACTIVITY_ID
     */
    public String getBarclaycardPaymentOneTimeActivityId() {
	return BARCLAY_CARD_PAYMENT_ONETIME__ACTIVITY_ID;
    }

    /**
     * @return BANK_SELECT_ACTIVITY_ID
     */
    public String getBankSelectActivityId() {
	return BANK_SELECT_ACTIVITY_ID;
    }

    /**
     * @return FUND_TRANSFER_OWN_ACTIVITY_ID
     */
    public String getFundTransferOwnActivityId() {
	return FUND_TRANSFER_OWN_ACTIVITY_ID;
    }

    /**
     * @return PAYEE_MAINTENANCE_ACTIVITY_ID
     */
    public String getPayeeMaitenanceActivityId() {
	return PAYEE_MAINTENANCE_ACTIVITY_ID;
    }

    /**
     * @return CHANGE_PASSWORD_ACTIVITY_ID
     */
    public String getChangePasswordActivityId() {
	return CHANGE_PASSWORD_ACTIVITY_ID;
    }

    /**
     * @return CHANGE_SECURITY_QUESTION_ACTIVITY_ID
     */
    public String getChangeSQAActivityId() {
	return CHANGE_SQA_ACTIVITY_ID;
    }

    /**
     * @return FORGOT_PASSWORD_ACTIVITY_ID
     */
    public String getForgotPasswordActivityId() {
	return FORGOT_PASSWORD_ACTIVITY_ID;
    }

    /**
     * @return FORGOT_QUESTION_ANSWER_ACTIVITY_ID
     */
    public String getForgotSQAActivityId() {
	return FORGOT_SQA_ACTIVITY_ID;
    }

    /**
     * @return FORGOT_USERNAME_ACTIVITY_ID
     */
    public String getForgotUsernameActivityId() {
	return FORGOT_USERNAME_ACTIVITY_ID;
    }

    /**
     * @return LOGIN_ACTIVITY_ID
     */
    public String getLoginActivityId() {
	return LOGIN_ACTIVITY_ID;
    }

    /**
     * @return ACTIVITY_ID
     */
    public String getActivityId() {
	return ACTIVITY_ID;
    }

    /**
     * @return ONLINE_REGISTRATION_ACTIVITY_IDs
     */
    public String getOnlineRegistrationActivityId() {
	return ONLINE_REGISTRATION_ACTIVITY_ID;
    }

    public String getPreOnlineRegistrationActivityId() {
	return PREONLINE_REGISTRATION_ACTIVITY_ID;
    }

    /**
     * @return LOGOUT_ACTIVITY_ID
     */
    public String getLogoutActivityId() {
	return LOGOUT_ACTIVITY_ID;
    }

    /**
     * @return FUND_TRANSFER_EXTERNAL_ONETIME_ACTIVITY_ID
     */
    public String getFtExOnetimeActivityId() {
	return FUND_TRANSFER_EXTERNAL_ONETIME_ACTIVITY_ID;
    }

    /**
     * @return FUND_TRANSFER_INTERNAL_ONETIME_ACTIVITY_ID
     */
    public String getFtInOnetimeActivityId() {
	return FUND_TRANSFER_INTERNAL_ONETIME_ACTIVITY_ID;
    }

    /**
     * @return FUND_TRANSFER_EXTERNAL_PAYEE_ACTIVITY_ID
     */
    public String getFtExPayeeActivityId() {
	return FUND_TRANSFER_EXTERNAL_PAYEE_ACTIVITY_ID;
    }

    /**
     * @return FUND_TRANSFER_INTERNAL_PAYEE_ACTIVITY_ID
     */
    public String getFtInPayeeActivityId() {
	return FUND_TRANSFER_INTERNAL_PAYEE_ACTIVITY_ID;
    }

    /**
     * @return MOBILE_TOPUP_ONETIME_ACTIVITY_ID
     */
    public String getMtOnetimeActivityId() {
	return MOBILE_TOPUP_ONETIME_ACTIVITY_ID;
    }

    /**
     * @return MOBILE_TOPUP_FACADE_ACTIVITY_ID
     */
    public String getMtFacadeActivityId() {
	return MOBILE_TOPUP_FACADE_ACTIVITY_ID;
    }

    /**
     * @return DELETE_PAYEE_ACTIVITY_ID
     */
    public String getDeletePayeeActivityId() {
	return DELETE_PAYEE_ACTIVITY_ID;
    }

    /**
     * @return DELETE_PAYEE_ACTIVITY_ID
     */
    public String getDeleteBarclaycardPayeeActivityId() {
	return DELETE_BARCLAYCARD_PAYEE_ACTIVITY_ID;
    }

    /**
     * @return ACTIVATE_PAYEE_ACTIVITY_ID
     */
    public String getActivatePayeeActivityId() {
	return ACTIVATE_PAYEE_ACTIVITY_ID;
    }

    /**
     * @return ACTIVATE_PAYEE_ACTIVITY_ID
     */
    public String getActivateBarclaycardPayeeActivityId() {
	return ACTIVATE_BARCLAYCARD_PAYEE_ACTIVITY_ID;
    }

    public String getIntRatesCASAActivityId() {
	return INT_RATES_CASA_ACTIVITY_ID;
    }

    public String getIntRatesTDActivityId() {
	return INT_RATES_TD_ACTIVITY_ID;
    }

    public static String getReqLiabLetterActivityId() {
	return REQ_LIABILITY_LETTER;
    }

    public String getEMessageActivityId() {
	return VIEW_EMESSAGE;
    }

    public String getEStatementActivityId() {
	return ESTATEMENT;
    }

    public String getOrderPaperStatement() {
	return ORDER_PAPER_STMT;
    }

    public String getActivityInquiry() {
	return ACTIVITY_INQUIRY;
    }

    public String getLinkMyAccount() {
	return LINK_MY_ACCOUNTS;
    }

    public String getGhippsFundTransferOtherBankAccounts() {
    	return GHIPPS_FUND_TRANSFER_OTHER_BANK_ACCOUNTS;
    }

}
