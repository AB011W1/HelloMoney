package com.barclays.bmg.constants;

public interface ServiceIdConstants {

    /**
     * MakeDomesticFundTransfer Request Constant.
     */
    String SERVICE_MAKE_DOMESTIC_FUND_TRANSFER = "MakeDomesticFundTransfer";
    String SERVICE_RETRIEVE_INDIVIDUAL_CUSTOMER_BENEFICIARY_LIST = "RetrieveIndividualCustBeneficiaryList";
    String SERVICE_RETRIEVE_ORGANIZATION_BENEFICIARY_DETAILS = "RetrieveOrganizationBeneficiaryDetails";
    String SERVICE_RETRIEVE_BILL_DETAILS = "UAERetrieveBillDetails";
    String SERVICE_RETRIEVE_CHARGE_DETAILS = "RetrieveChargeDetails";
    String SERVICE_MAKE_BILL_PAYMENT = "SSAMakeBillPayment";
    String SERVICE_UAE_MAKE_BILL_PAYMENT = "UAEMakeBillPayment";
    String SERVICE_MAKE_BILL_PAYMENT_FOR_ALL = "MakeBillPayment";
    String SERVICE_RETRIEVE_INDIVIDUAL_BENEFICIARY_DETAILS = "RetrieveIndividualBeneficiaryDetails";

    String SERVICE_RETRIEVE_CUSTOMER_BY_CREDITCARD_NUMBER = "RetrieveIndividualCustByCCNumber";
    String SERVICE_MAKE_CREDIT_CARD_PAYMENT = "MakeCreditCardPayment";
    String ADD_PROBLEM_SERVICE = "AddProblem";

    String SERVICE_RETRIEVE_F_X_RATE = "RetrieveFXRate";
    String SERVICE_RETRIEVE_F_X_BOARD_RATES="RetrieveFXBoardRates";

    String SERVICE_RETRIEVE_TD_DETAILS = "RetrieveTimeDepositDetails";
    String SERVICE_MAKE_INTERNATIONAL_FUND_TRANSFER = "MakeInternationalFundTransfer";
    String SERVICE_ADD_CHECK_BOOK_REQUEST = "AddCheckBookRequest";
    String SERVICE_MAKE_DOMESTIC_DEMAND_DRAFT_BY_ACCOUNT = "MakeDomesticDemandDraftByAccount";
    String SERVICE_ADD_ORGANIZATION_BENEFICIARY = "AddOrganizationBeneficiary";

    String SERVICE_DELETE_INDIVIDUAL_CUST_BENEFICIARY = "DeleteIndividualCustBeneficiary";
    String SERVICE_ADD_INDIVIDUAL_CUST_BENEFICIARY = "AddIndividualBeneficiary";
    String SERVICE_VIEW_BILL_PAYMENT_DETAILS = "ViewBillPaymentDetails";
    String SERVICE_SEARCH_FUNDS_TRANSFER_HISTORY = "SearchFundsTransferHistory";

    String SERVICE_RETRIEVE_PAPER_STATMENT_REQUEST = "OrderPaperStatement";

    String SERVICE_UPDATE_HELLO_MONEY_CUSTOMER = "UpdateHelloMoneyCustomer";
    String SERVICE_UPDATE_PIN = "UpdatePin";

    String SERVICE_VERITY_PIN = "VerifyPin";

    // Self Registration and 2FA Constants
    String SEARCH_CUSTOMER_FOR_REGISTRATION = "SearchIndividualCustByAcct";
    String RETRIEVE_CUSTOMER_ACCOUNTLIST_FOR_REGISTRATION = "RetrieveIndividualCustAcctList";
    String RETRIEVE_CUSTOMER_DETAILS_FOR_REGISTRATION = "RetrieveIndividualCustInformation";
    String ADD_DETAILS_TO_MCE_FOR_REGISTRATION = "CreateHelloMoneyCustomer";
    String UPDATE_DETAILS_TO_MCE_FOR_REGISTRATION = "UpdateHelloMoneyCustomer";
    String REISSUE_PIN_FOR_REGISTRATION = "CreatePin";
    String GENERATE_CHALLENGE_QUESTION = "RetrieveCustomerQuestionnaire";
    String TRANSACTION_SECOND_AUTHENTICATION = "TransactionSecondAuthentication";
    String SECOND_AUTHENTICATION = "UpdateIndividualCustQuestionnaireStatus";
    String SELF_REGISTRATION_INIT = "CreateHelloMoneyCustomer";
    String SELF_REGISTRATION_EXEC = "SearchIndividualCustByAcct";

    String SMS_ACKNOWLEDGEMENT = "SendNotificationOneWay";

    String SERVICE_RTRV_IND_CUST_ACCT_LST = "RetrieveIndividualCustAndAcctBasicDetails";

    String SERVICE_RTRV_IND_CUST_BY_SCVID = "RetrieveIndividualCustBySCVID";
    String SEND_CASH_SERVICE = "SendCashService";
    String ENCRYPTION_SERVICE = "EncryptCreditCardATMPIN";

    String SERVICE_ADD_PROSPECT = "AddProspect";
    //	ADDED TO INCLUDE DEBIT CARD VALIDATION IN SELF REGISTRATION
    String SERVICE_RTRV_IND_CUST_CARD_LIST="RetrieveIndividualCustCardList";

  //KITS LOOK UP API
    String SERVICE_SEARCH_INDIVIDUAL_CUST_DEDUP_CHECK="SearchIndividualCustInformation";
    String SERVICE_CREATE_INDIVIDUAL_CUSTOMER="CreateIndividualCustomer";

    //GEPG Bill Details
    String SERVICE_RETRIEVE_GEPG_BILL_DETAILS = "RetrieveBillDetails";

    //GroupWallet Services
    String RETRIEVE_NON_PERSONAL_CUST_ACCOUNT_LIST="RetrieveNonPersonalCustAcctList";
    String MANAGE_FUND_TRANSFER_STATUS = "ManageFundTransferStatus";
    String RETRIEVE_CASA_ACCOUNT_TRANSACTION_ACTIVITY = "RetrieveCASAAcctTransactionActivity";
    String SEND_MULTIPLE_NOTIFICATIONS_ONEWAY = "SendMultipleNotifications";

}
