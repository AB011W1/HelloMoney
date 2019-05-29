/**
 * USSDInputParamsEnum.java
 */
package com.barclays.ussd.utils;

// TODO: Auto-generated Javadoc
/**
 * The Enum USSDInputParamsEnum.
 *
 * @author BTCI
 */
public enum USSDInputParamsEnum {

	CALL_ME_BACK_AREA_LIST("CM001", "businessArea"),
	CALL_ME_BACK_CATEGORY_LIST("CM002", "serviceCategory"),
	CALL_ME_BACK_CATEGORY_DTLS("CM003", "serviceCategoryDetails"),

	I_AM_INTERESTED_PRODUCT("II001", "product"),
	I_AM_INTERESTED_SUB_PRODUCT("II002", "subproduct"),
	I_AM_INTERESTED_PRODUCT_DTLS("II003", "details"),

	INSTALLMENT_OFFER_PARTNER_LETTER("EI001", "partnerLetter"),
	INSTALLMENT_OFFER_PARTNER_LIST("EI002", "partner"),
	INSTALLMENT_OFFER_PARTNER_DTLS("EI003", "partnerDetails"),

	DINING_OFFER_CITY_LETTER("BD001", "cityLetter"),
	DINING_OFFER_CITY_LIST("BD002", "city"),
	DINING_OFFER_RESTAURANT_LETTER("BD003", "restaurantLetter"),
	DINING_OFFER_RESTAURANT_LIST("BD004", "restaurant"),
	DINING_OFFER_RESTAURANT_DTLS("BD005", "restaurantDetails"),
	SELECTED_RESTAURANT_NAME("", "restaurant"),

	USER_MIGRATION_MOBILE("", "mobileNo"),
	USER_MIGRATION_CHALLENGE_ID("", "challengeId"),
	USER_MIGRATION_BRANCH_SEARCHER("UM001", "searcher"),
	USER_MIGRATION_BRANCH_LIST("UM002", "branchCode"),
	USER_MIGRATION_ACCOUNT_NO("UM003", "accountNo"),
	USER_MIGRATION_INITIATION("UM004", "initiate"),
	USER_MIGRATION_DOB("UM005", "questionList"),
	USER_MIGRATION_VERIFY_DOB("UM006", "SQA"),
	USER_MIGRATION_NEW_PIN("UM007", "pass"),
	USER_MIGRATION_REENTER_PIN("UM008", "reenterPin"),
	USER_MIGRATION_SUBMIT("UM009", "submit"),

	/** The mini stmt sel ac. */

	MINI_STMT_SEL_AC_LIST("MS000", "list"),

	MINI_STMT_SEL_AC("MS001", "actNo"),
	/** The mini stmt sel branch. */
	MINI_STMT_SEL_BR("", "brnCde"),
	/** The mini stmt resp. */
	MINI_STMT_RESP("MS002", "days"),

	BAL_ENQ_AC_LIST("BE000", "actList"),
	/** The bal enq sel ac. */
	BAL_ENQ_SEL_AC("BE001", "actNo"),

	/** The bal enq acct det. */
	BAL_ENQ_ACCT_DET("BE002", "blank"),

	/** Welcome screen other account bal enq sel ac. */
	WLCME_BAL_ENQ_SEL_AC("WSBE000", "actNo"),

	/** Welcome screen other account bal enq acct det. */
	WLCME_BAL_ENQ_ACCT_DET("WSBE001", "blank"),

	/** The pay biller list. */
	PAY_BILLS_BILLER_LIST("BP005", "billerList"),

	/** The pay bills payee list. */
	PAY_BILLS_PAYEE_LIST("BP000", "payGrp"),

	PAY_BILLS_AREA_CODE("areaCode", "areaCode"),

	/** The pay bills payee id. */
	PAY_BILLS_PAYEE_ID("PayeeId", "payId"),

	/** The pay bills enter amt. */
	PAY_BILLS_ENTER_AMT("BP001", "amt"),

	/**Kadikope **/
	/** The pay bills  Credit Card. */
	PAY_BILLS_CARD("BP007", "txnCreditORCasaAC"),

	/** The pay bills Credit Card lsit. */
	PAY_BILLS_CARD_LIST("BP008", "actNo"),

	/**Kadikope **/

	/** The pay bills from acnt. */
	PAY_BILLS_FROM_ACNT("BP002", "frActNo"),

	/** The pay bills pay rmrks. */
	PAY_BILLS_PAY_RMRKS("payRemarks", "pmtRem"),

	/** The pay bills submit. */
	PAY_BILLS_SUBMIT("BP003", "blank"),

	/** The pay bills confirm. */
	PAY_BILLS_CONFIRM("BP004", "txnRefNo"),

	EXT_BANK_FT_BENEF_LIST("EBAFT000", "benfList"),
	/** The ext bank ft to ac. */
	EXT_BANK_FT_TO_AC("EBAFT001", "payId"),

	/** The ext bank serviceType. */
	EXT_BANK_FT_SERVICE_NAME("", "serviceName"),

	/** The ext bank ft enter amt. */
	EXT_BANK_FT_ENTER_AMT("EBAFT002", "blank"),

	/** The ext bank ft sel frm ac. */
	EXT_BANK_FT_SEL_FRM_AC("EBAFT003", "frActNo"),

	/** The ext bank ft payee info. */
	EXT_BANK_FT_PAYEE_INFO("EBAFT004", "curr"),

	EXT_BANK_FT_TRAN_REMARKS("", "txnNot"),

	/** The ext bank ft validate. */
	EXT_BANK_FT_VALIDATE("EBAFT005", "txnAmt"),

	/** The ext bank ft execute. */
	EXT_BANK_FT_EXECUTE("EBAFT006", "txnRefNo"),

	VIEW_BILLERS_RET_LIST("VB000", "benfList"),
	/** The view billers list. */
	VIEW_BILLERS_LIST("VB001", "payGrp"),

	/** The view biller details. */
	VIEW_BILLER_DETAILS("VB002", "payId"),

	/** The olaft payee list. */
	OLAFT_PAYEE_LIST("OLFT002", "toActNo"),

	/** The olaft source list. */
	OLAFT_SOURCE_PAYEE_LIST("OLFT000", "srcList"),

	/** The olaft source list. */
	OLAFT_SOURCE_LIST("OLFT001", "frActNo"),

	/** The olaft amount. */
	OLAFT_AMOUNT("OLFT003", "txnAmt"),

	/** The olaft validate. */
	OLAFT_VALIDATE("OLFT004", "confirm"),

	/** The olaft confirm. */
	OLAFT_CONFIRM("OLFT005", "txnRefNo"),

	/** The int ft payee list. */
	INT_FT_SOURCE_AND_PAYEE_LIST("OBAFT010", "list"),

	INT_FT_PAYEE_LIST("OBAFT011", "payId"),

	/** The int ft amount. */
	INT_FT_AMOUNT("OBAFT012", "txnAmt"),

	/** Kadikope **/
	/** The int  Credit Card. */
	INT_FT_CARD("OBAFT018", "txnCreditORCasaAC"),

	/** The int  Credit Card lsit. */
	INT_FT_CARD_LIST("OBAFT019", "creditcardNo"),
	/** Kadikope **/

	/** The int ft source list. */
	INT_FT_SOURCE_LIST("OBAFT013", "frActNo"),

	/** The int ft validate. */
	INT_FT_VALIDATE("OBAFT014", "validate"),

	/** The int ft confirm. */
	INT_FT_CONFIRM("OBAFT015", "txnRefNo"),

	/** The check bk src act. */
	CHECK_BK_SRC_ACT_LIST("CBR000", "list"),

	/** The check bk src act. */
	CHECK_BK_SRC_ACT("CBR001", "actNo"),

	/** The check bk size. */
	CHECK_BK_SIZE("CBR002", "bkSize"),

	CHECK_BK_BRANCH_NAME("CBR0002", "branchNameLetter"),

	CHECK_BK_BRANCH_LIST("CBR0003", "branchNameList"),

	/** The check bk validate. */
	CHECK_BK_VALIDATE("CBR003", "bkTyp"),

	/** The check bk confirm. */
	CHECK_BK_CONFIRM("CBR004", "txnRefNo"),

	/** The other barc del benf payee. */
	OTHER_BARC_DEL_BENF_LIST("OBADB000", "paylist"),
	OTHER_BARC_DEL_BENF_PAYEE("OBADB001", "payId"),

	/** The other barc del benf validate. */
	OTHER_BARC_DEL_BENF_VALIDATE("OBADB002", "payeeId"),

	/** The other barc del benf confirm. */
	OTHER_BARC_DEL_BENF_CONFIRM("OBADB003", "confirm"),

	NON_BARC_DEL_BENF_PAYEE_LIST("NBADB000", "benfList"),
	/** The non barc del benf payee. */
	NON_BARC_DEL_BENF_PAYEE("NBADB001", "payId"),

	/** The non barc del benf validate. */
	NON_BARC_DEL_BENF_VALIDATE("NBADB002", "payeeId"),

	/** The non barc del benf confirm. */
	NON_BARC_DEL_BENF_CONFIRM("NBADB003", "confirm"),

	/** The non barc del benf service name. */
	NON_BARC_DEL_BENF_SERVICE_NAME("", "serviceName"),

	STMT_REQ_SRC_ACT_LIST("SR000", "srcAcctList"),
	/** The stmt req src act. */
	STMT_REQ_SRC_ACT("SR001", "actNo"),

	/** The stmt req validate. */
	STMT_REQ_VALIDATE("SR002", "validate"),

	/** The stmt req from date. */
	STMT_REQ_FROM_DATE("fromDate", "fromDate"),

	/** The stmt req to date. */
	STMT_REQ_TO_DATE("toDate", "toDate"),

	/** The stmt req confirm. */
	STMT_REQ_CONFIRM("SR003", "txnRefNo"),

	VLPB_RETRIEVE_BILLER_LST("VLPB000", "billerList"),
	/** The vlpb biller lst. */
	VLPB_BILLER_LST("VLPB001", "transactionType"),

	/** The vlpb details. */
	VLPB_DETAILS("VLPB002", "transactionRefNo"),

	/** The reg ben int payee type. */
	REG_BEN_INT_PAYEE_TYPE("PayeeType", "payeeType"),

	/** The reg ben int acc no. */
	REG_BEN_INT_ACC_NO("RBB001", "accountNumber"),
	/*CR#48 */
	REG_BEN_INT_ACC_NUMBER("RBB008", "accountNumber"),
	/** The reg ben int branch code. */
	REG_BEN_INT_BRANCH_CODE_LETTER("RBB002", "branchCodeLetter"),
	REG_BEN_INT_BRANCH_LIST("RBB003", "branchList"),

	REG_BEN_INT_BRANCH_CODE("RBB004", "branchCode"),

	/** The reg ben int nick name. */
	REG_BEN_INT_NICK_NAME("RBB005", "beneficiaryNickName"),

	/** The reg ben int validate. */
	REG_BEN_INT_VALIDATE("RBB006", "validate"),

	/** The reg ben int confirm. */
	REG_BEN_INT_CONFIRM("RBB007", "txnRefNo"),

	LANG_PREF_RETRIEVE_LANGUAGE("LANG000", "langList"),
	/** The lang pref get lang list. */
	LANG_PREF_GET_LANG_LIST("LANG001", "prefLang"),

	/** The lang pref validate. */
	LANG_PREF_VALIDATE("LANG002", "validate"),

	/** The lang pref confirm. */
	LANG_PREF_CONFIRM("LANG003", "txnRefNo"),

	/** The lang pref get lang list. */
	CHANGE_LANGUAGE_GET_LANG_LIST("CHNGLANG001", "prefLang"),

	/** The lang pref validate. */
	CHANGE_LANGUAGE_EXECUTE("CHNGLANG002", "execute"),

	/** The reg ben ext payee type. */
	REG_BEN_EXT_PAYEE_TYPE("PayeeType", "payeeType"),

	/*
	 * Change start for CR 10/
	 *
	 * / The reg ben ext bank code.
	 */
	REG_BEN_EXT_BANK_CODE("ROTHBB001", "tempbankCode"),

	REG_BEN_EXT_BANK_CODE_LIST("ROTHBB002", "bankCode"),

	/** The reg ben ext branch code. */
	REG_BEN_EXT_BRANCH_CODE("ROTHBB003", "tempbranchCode"),

	REG_BEN_EXT_BRANCH_CODE_LIST("ROTHBB004", "branchCode"),

	/** The reg ben ext acc no. */
	REG_BEN_EXT_ACC_NO("ROTHBB005", "accountNumber"),

	/** The reg ben ext benf name. */
	REG_BEN_EXT_BENF_NAME("ROTHBB006", "beneficiaryName"),

	/** The reg ben ext nick name. */
	REG_BEN_EXT_NICK_NAME("ROTHBB007", "beneficiaryNickName"),

	/** The reg ben ext validate. */
	REG_BEN_EXT_VALIDATE("ROTHBB008", "validate"),

	/** The reg ben ext confirm. */
	REG_BEN_EXT_CONFIRM("ROTHBB009", "txnRefNo"),

	/*
	 * Change End for CR 10/
	 *
	 * / The chng pin old pass.
	 */
	CHNG_PIN_OLD_PASS("CP001", "oldPass"),

	/** The chng pin new pass. */
	CHNG_PIN_NEW_PASS("CP002", "newPass"),

	/** The chng pin cnf new pass. */
	CHNG_PIN_CNF_NEW_PASS("CP003", "confNewPass"),

	/** The chng pin cnf confirm. */
	CHNG_PIN_CNF_CONFIRM("CP004", "confirm"),

	/** The reg biller pay group. */
	REG_BILLER_PAY_GROUP("payGrp", "payGrp"),

	/** The reg biller type. */
	REG_BILLER_TYPE("billerType", "billerType"),

	/** The reg biller get billers. */
	REG_BILLER_GET_CKBILLERS("REGB000", "listOfBillers"),

	REG_BILLER_GET_BILLERS("REGB001", "billerId"),

	REG_BILLER_AREA_SEARCHER("REGB002", "searcher"),
	REG_BILLER_AREA_CODE("REGB003", "areaCode"),
	/** The reg biller get refno. */
	REG_BILLER_GET_REFNO("REGB004", "billerReferenceNum"),

	/** The reg biller nick name. */
	REG_BILLER_NICK_NAME("REGB005", "billerNickName"),

	/** The reg biller validate. */
	REG_BILLER_VALIDATE("REGB006", "validate"),

	/** The reg biller confirm. */
	REG_BILLER_CONFIRM("REGB007", "orgRefNo"),

	/** The reg biller DstvType. */
	REG_BILLER_DSTV_TYPE("REGB008", "dstvType"),

	/** The reg biller WUCType. */
	REG_WUC_BILLER_TYPE("REGB0010", "contractBillerReferenceNum"),

	/** The del billers list. */

	DEL_BILLERS_CKLIST("DBILL000", "listOfBillers"),
	DEL_BILLERS_LIST("DBILL001", "payGrp"),

	/** The del biller details. */
	DEL_BILLER_DETAILS("DBILL002", "payId"),

	/** The del biller confirm. */
	DEL_BILLER_CONFIRM("DBILL003", "payeeId"),

	// Added for Non Registered Barclays Internal Fund Transfer One Time
	/** The int nr ft source list. */
	INT_NR_FT_SOURCE_LIST("OBAFTNR015", "frActNo"),

	/** The int nr ft branch code. */
	INT_NR_FT_BRANCH_CODE("OBAFTNR012", "beneficiaryBranchCode"),

	INT_NR_FT_BRANCH_CODE_LETTER("OBAFTNR0012", "beneficiaryBranchCodeLetter"),

	/** The int nr ft to ac. */
	INT_NR_FT_TO_AC("OBAFTNR011", "beneficiaryAccountNumber"),

	/*CR#48 */
	INT_NR_FT_TO_AC_NO("OBAFTNR018", "beneficiaryAccountNumber"),
	/** The int nr ft nick name. */
	INT_NR_FT_NICK_NAME("OBAFTNR013", "beneficiaryName"),

	/** The int nr ft amount. */
	INT_NR_FT_AMOUNT("OBAFTNR014", "txnAmt"),

	/** The int nr ft validate. */
	INT_NR_FT_VALIDATE("OBAFTNR016", "validate"),

	/** The int nr ft confirm. */
	INT_NR_FT_CONFIRM("OBAFTNR017", "txnRefNo"),

	//Kadikope
	/** The int nr ft validate. */
	INT_NR_FT_CREDIT_OPTION("OBAFTNRC00", "casaOrCredit"),

	/** The int nr ft confirm. */
	INT_NR_FT_CREDIT_LIST("OBAFTNRC01", "creditCard"),


	// One Time Payment
	/** The one time bill pymnt blrs lst. */

	ONE_TIME_BILL_PYMNT_BLRS_CKLST("OTBP000", "billerList"),
	ONE_TIME_BILL_PYMNT_BLRS_LST("OTBP001", "billerId"),
	ONE_TIME_BILL_PYMNT_AREA_SEARCHER("OTBP002", "searcher"),
	ONE_TIME_BILL_PYMNT_AREA_CODE("OTBP003", "areaCode"),
	/** The one time bill pymnt bl ref. */
	ONE_TIME_BILL_PYMNT_BL_REF("OTBP004", "billerRefNumber"),

	/** The one time bill pymnt enter amt. */
	ONE_TIME_BILL_PYMNT_ENTER_AMT("OTBP005", "amt"),

	/**Kadikope **/
	/** The one time bill Credit Card. */
	ONE_TIME_BILL_PYMNT_ENTER_CARD("OTBP014", "txnCreditORCasaAC"),

	/** The one time billCredit Card lsit. */
	ONE_TIME_BILL_PYMNT_ENTER_CARD_LIST("OTBP015", "creditcardNo"),

	/** The one time bill Credit Card confirm. */
	ONE_TIME_BILL_PYMNT_ENTER_CARD_VALIDATE("OTBP016", "txnCreditconfirm"),

	/**Kadikope **/

	/** The one time bill pymnt acnt nos. */
	ONE_TIME_BILL_PYMNT_ACNT_NOS("OTBP006", "fromActNumber"),

	/** The one time bill pymnt confirm. */
	ONE_TIME_BILL_PYMNT_CONFIRM("OTBP007", "confirm"),

	/** The one time bill pymnt confirm. */
	ONE_TIME_BILL_PYMNT_TRAN_REMARKS("remarks", "remarks"),

	/** The one time bill pymnt submit. */
	ONE_TIME_BILL_PYMNT_SUBMIT("OTBP008", "txnRefNo"),

	/** The one time bill pmt. WUC change 19/06/2017 */
	ONE_TIME_BILL_PYMNT_WUC_CUSTOMER_NUMBER("OTBPWUC016", "customerRefNumber"),
	ONE_TIME_BILL_PYMNT_WUC_CONTRACT_NUMBER("OTBPWUC017", "contractRefNumber"),

	//CR-57
	ONE_TIME_BILL_PYMNT_DSTVTYPE("OTBP009", "dstvType"),
	// One Time Payment

	FD_VIEW_RATE_ENTER_AMT("FDR001", "amt"),
	FD_VIEW_TENURE("FDR002", "fdTenure"),
	FD_VIEW_RATES("FDR003", "rates"),

	FD_APPLY_SEL_AC("FDA001", "actNo"),
	FD_APPLY_ENTER_AMT("FDA002", "amt"),
	FD_APPLY_SEL_TENURE("FDA003", "tenure"),
	FD_APPLY_SEL_PROD("FDA004", "prodList"),
	FD_APPLY_CONFIRM("FDA005", "tenure"),
	FD_APPLY_VALIDATE("FDA006", "validate"),
	FD_APPLY_SUBMIT("FDA007", "txnRefNo"),

	/** The mobile wallet mnos lst. */
	MOBILE_WALLET_MNOS_SRC_LST("MWTU000", "list"),
	MOBILE_WALLET_MNOS_LST("MWTU001", "billerId"),
	MOBILE_WALLET_MNOS_LST_REF_NUM("refNmbr", "refNmbr"),

	/** The mobile wallet account number. */
	MOBILE_WALLET_ACCOUNT_NUMBER("MWTU002", "mblNo"),

	/** CR#47 implementation*/
	MOBILE_WALLET_MSISDN_TYPE("MWTU0021", "list"),

	/** The mobile wallet amount. */
	MOBILE_WALLET_AMOUNT("MWTU003", "txnAmt"),

	/** The mobile wallet Credit Card. */
	MOBILE_WALLET_CREDIT_CARD("MWTU007", "txnCreditORCasaAC"),

	/** The mobile wallet Credit Card lsit. */
	MOBILE_WALLET_CREDIT_CARD_LIST("MWTU008", "txnCreditList"),

	/** The airtime Credit Card confirm. */

	MOBILE_WALLET_CREDIT_CARD_VALIDATION("MWTU009", "txnCreditconfirm"),

	/** The mobile wallet from account. */
	MOBILE_WALLET_FROM_ACCOUNT("MWTU004", "actNo"),

	/** The mobile wallet validate. */
	MOBILE_WALLET_VALIDATE("MWTU005", "validate"),
	MOBILE_WALLET_TRANSACTION_REMARKS("", "txnNot"),

	/** The mobile wallet confirm. */
	MOBILE_WALLET_CONFIRM("MWTU006", "txnRefNo"),

	/**CR82 */
	MOBILE_WALLET_NEW_BENE_SRC_MON("MWTU1022","newbenelist2"),
	MOBILE_WALLET_NEW_BENE_MOB_NUM("MWTU1023","mblNo"),

	MOBILE_WALLET_PAYMENT_TYPE("MWTU0022", "benelist"),
	MOBILE_WALLET_SAVE_BEN_NICK_NAME("MWTU0023", "billerNickName"),
	MOBILE_WALLET_SAVE_BEN_VALIDATE("MWTU0024", "validate"),
	MOBILE_WALLET_SAVE_BEN_CONFIRM("MWTU0025", "orgRefNo"),

	//Delete bene
	/** The del biller details. */
	MOBILE_WALLET_DEL_BILLER_DETAILS("MWTU1024", "walletpayId"),
	/** The del biller confirm. */
	MOBILE_WALLET_DEL_BILLER_CONFIRM("MWTU1025", "walletpayeeId"),

	//saved bene
	MOBILE_WALLET_BILLER_LIST("MWTU0026", "billerList"),
	MOBILE_WALLET_PAYEE_LIST("MWTU0027", "payGrp"),
	MOBILE_WALLET_BENF_DTlS("MWTU0028", "payId"),

	//Edit bene
	MOBILE_WALLET_EDIT_BEN_VALIDATE("MWTU1026", "validate1"),
	MOBILE_WALLET_EDIT_BILLER_CONFIRM("MWTU1027","orgRefNo"),

	/**CR82 End */

	/** The user verification. */
	USER_VERIFICATION("AUTH000", "pass"),
	USER_VERIFY_QUESTION("questionList", "questionList"),

	/** The user authentication. */
	USER_AUTHENTICATION_USER_NAME("", "usrNam"),
	USER_AUTHENTICATION("AUTH001", "password"),

	/** The user authentication usrnam. */
	USER_AUTHENTICATION_USRNAM("", "usrNam"),

	// Airtime Topup
	/** The airtime mno list. */
	AIRTIME_SRC_MNO_LIST("ATT000", "list"),
	AIRTIME_MNO_LIST("ATT001", "billerId"),

	/** The airtime mob num. */
	AIRTIME_MOB_NUM("ATT002", "mblNo"),

	AIRTIME_TOPUP_MSISDN_TYPE("ATT0021", "list"),

	/** The airtime amount. */
	AIRTIME_AMOUNT("ATT003", "txnAmt"),
	/**Kadikope **/
	/** The airtime Credit Card. */
	AIRTIME_CARD("ATT007", "txnCreditORCasaAC"),

	/** The airtime Credit Card lsit. */
	AIRTIME_CARD_LIST("ATT008", "creditcardNo"),

	/** The airtime Credit Card confirm. */
	AIRTIME_CARD_VALIDATE("ATT009", "txnCreditconfirm"),

	/**Kadikope **/
	/** The airtime acct list. */
	AIRTIME_ACCT_LIST("ATT004", "actNo"),

	/** The airtime confirm. */
	AIRTIME_CONFIRM("ATT005", "confirm"),

	AIRTIME_TRANSACTION_REMARKS("", "txnNot"),

	/** The airtime submit. */
	AIRTIME_SUBMIT("ATT006", "txnRefNo"),

	/**CR 82 */
	AIRTIME_TOPUP_PAYMENT_TYPE("ATT0022", "newbenelist1"),
	AIRTIME_TOPUP_SAVE_BEN_NICK_NAME("ATT0023", "billerNickName"),
	AIRTIME_TOPUP_SAVE_BEN_VALIDATE("ATT0024", "validate"),
	AIRTIME_TOPUP_SAVE_BEN_CONFIRM("ATT0025", "orgRefNo"),
	AIRTIME_TOPUP_NEW_BENE_SRC_MON("ATT1022","newbenelist2"),
	AIRTIME_TOPUP_NEW_BENE_MOB_NUM("ATT1023","mblNo"),
	AIRTIME_TOPUP_BILLER_LIST("ATT0026", "billerList"),
	AIRTIME_TOPUP_PAYEE_LIST("ATT0027", "payGrp"),
	AIRTIME_TOPUP_BENF_DTlS("ATT0028", "payId"),

	//Delete bene
	/** The del biller details. */
	AIRTIME_TOPUP_DEL_BILLER_DETAILS("ATT1024", "airtimepayId"),
	/** The del biller confirm. */
	AIRTIME_TOPUP_DEL_BILLER_CONFIRM("ATT1025", "airtimepayeeId"),
	//Edit bene
	AIRTIME_TOPUP_EDIT_BEN_VALIDATE("ATT1026", "validate"),
	AIRTIME_TOPUP_EDIT_BILLER_CONFIRM("ATT1027","orgRefNo"),
	/**CR 82 */
	/** The mini stmt main acct sel ac. */
	MINI_STMT_MAIN_ACCT_SEL_AC("MSMA000", "actNo"),

	/** The bal enq main acct sel ac. */
	BAL_ENQ_MAIN_ACCT_SEL_AC("BEMA000", "actNo"),

	// Self Registration

	// PLEASE USE THIS PARAMETER FOR GET QUESTION SELF REG INIT

	/** Self Registration account */
	SELFREG_BRANCH_SEARCHER("SLR0001", "searcher"),
	SELFREG_BRANCH("SLR001", "branchCode"),

	SELFREG_ACCOUNT("SLR002", "accountNo"),

	SELFREG_TERM_N_COND_1("SLR003", "condition"),
	SELFREG_INIT("SLR004", "mobileNumber"),

	SELFREG_GET_QUESTION("SLR005", "questionList"),

	/** The selfreg answer one. */
	SELFREG_ANSWER_ONE("SLR006", "SQA"),

	SELFREG_EXECUTE("SLR007", "execute"),
	SELFREG_LANGUAGE("prefLang", "prefLang"),

	// ADDED TO INCLUDE DEBIT CARD VALIDATION IN SELF REGISTRATION
	SELFREG_DEBITCARD_NO("SLR008", "debitCardNo"),
	SELFREG_DEBITCARD_EXPIRYDATE("SLR009", "debitCardExpiryDate"),
	SELFREG_DEBITCARD_VERIFICATION("SLR010", "debitCardVerfication"),
	// END
	SELFREG_MOBILE("", "mobileNo"),

	SELFREG_CHALLENGE_ID("", "challengeId"),

	/** The selfreg answer two. */
	SELFREG_ANSWER_TWO("", "ans2"),

	/** The selfreg execute. */

	// Two Favtor Verify
	/** The two factor init. */
	TWO_FACTOR_INIT("TFV001", "questionList"),

	/** The two factor verify. */
	TWO_FACTOR_GENERATE("TFV002", "questionList"),

	/** The two factor verify. */
	TWO_FACTOR_VERIFY("TFV003", "SQA"),

	/** Welcome screen Bal Enq */
	WELCOME_SCREEN_BAL_ENQ("WSMABE000", "balEnq"),

	/** The first login chng pin old pass. */
	FIRST_LOGIN_CHNG_PIN_OLD_PASS("FLCP001", "oldPass"),

	/** The first login chng pin new pass. */
	FIRST_LOGIN_CHNG_PIN_NEW_PASS("FLCP002", "newPass"),

	/** The first login chng pin cnf new pass. */
	FIRST_LOGIN_CHNG_PIN_CNF_NEW_PASS("FLCP003", "confNewPass"),

	/** The first login chng pin cnf confirm. */
	FIRST_LOGIN_CHNG_PIN_CNF_CONFIRM("FLCP004", "confirm"),

	/** HelloMoney demo */
	HELLOMONEY_DEMO("HMD000", ""),

	/** Contact Us */
	CONTACT_US("CU000", "contact"),

	ONE_TIME_CASH_SEND_MOBILE_NUM("CSOTP001", "mobileNo"),
	ONE_TIME_CASH_SEND_AMOUNT("CSOTP002", "amt"),
	ONE_TIME_CASH_SEND_CURRENCY("currency", "currency"),
	ONE_TIME_CASH_SEND_BRANCH_CODE("", "brnCde"),
	ONE_TIME_CASH_SEND_SRC_ACCT_LIST("CSOTP003", "actNo"),
	ONE_TIME_CASH_SEND_ATM_PIN("CSOTP004", "vPin"),
	ONE_TIME_CASH_SEND_REENTERED_ATM_PIN("CSOTP005", "revPin"),
	ONE_TIME_CASH_SEND_VALIDATE("CSOTP006", "validate"),
	ONE_TIME_CASH_SEND_TRANSACTION_REMARKS("", "txnNot"),
	ONE_TIME_CASH_SEND_SUBMIT("CSOTP007", "txnRefNo"),

	EDIT_BEN_INT_PAYEE_TYPE("PayeeType", "payeeType"),
	EDIT_BEN_INT_BENF_LIST("EBI000", "beneficiaryList"),
	EDIT_BEN_INT_BENF_ID("EBI001", "beneficiaryId"),
	EDIT_BEN_INT_ACC_NO("EBI002", "accountNumber"),
	EDIT_BEN_INT_BRANCH_CODE_SEARCHER("EBI003", "branchCodeLetter"),
	EDIT_BEN_INT_BRANCH_LIST("EBI004", "branchCode"),
	/*CR#48 */
	EDIT_BEN_INT_ACC_NUMBER("EBI008", "accountNumber"),
	EDIT_BEN_INT_NICK_NAME("EBI005", "beneficiaryNickName"),
	EDIT_BEN_INT_VALIDATE("EBI006", "validate"),
	EDIT_BEN_INT_CONFIRM("EBI007", "txnRefNo"),

	/** FX RATE */
	FX_RATE_GET_CURR("FXR000", "destCurrency"),
	FX_RATE_CONVERT("FXR001", "srcCurrency"),
	FX_RATE_ACCOUNT_NO("actNo", "actNo"),
	FX_RATE_BRANCH_CODE("branchCode", "branchCode"),
	FX_RATE_TRANSACTION_TYPE_CODE("tranType", "tranType"),

	/** old session */
	OLD_SESSION("OS000", "oldSession"),

	/** new session */
	NEW_SESSION("NS000", "newSession"),

	AT_A_GLANCE_CREDIT_CARD_LIST("AAG002", "list"),
	AT_A_GLANCE_CARD_LIST("AAG000", "cardNo"),
	AT_A_GLANCE_CARD_DETAILS("AAG001", "cardDtls"),
	RETRIVE_ACCOUNT_TYPE("", "accountType"),
	ACTIVITY_ID("", "activityId"),
	AUDIT_REQUIRED("", "auditRequired"),
	BUSINESS_ID("", "businessId"),

	CR_CARD_STAT_LIST("CCS000", "crdNo"),
	CR_CARD_STAT_ACCT_NO("actNo", "actNo"),
	CR_CARD_STAT_TRAN_DATE_LIST("CCS001", "tranDate"),
	CR_CARD_STAT_DETAILS("CCS002", "dtls"),

	CR_CARD_PAYMENT_LIST("CCP000", "crdList"),
	CR_CARD_PAYMENT_ACCT_NO("crActNo", "actNo"),
	CR_CARD_PAYMENT_CARD_NO("crdNo", "crdNo"),
	CR_CARD_PAYMENT_CARD_DETAILS("CCP001", "confirm"),
	CR_CARD_PAYMENT_AMOUNT("CCP002", "amt"),
	CR_CARD_PAYMENT_SRC_ACC_NO("CCP003", "frActNo"),
	CR_CARD_PAYMENT_BRANCH_CODE("", "branchCode"),
	CR_CARD_PAYMENT_REMRKS("payRemarks", "pmtRem"),
	CR_CARD_PAYMENT_VALIDATE("CCP004", "txnRefNo"),
	CR_CARD_PAYMENT_SUBMIT("CCP005", "blank"),
	CR_CARD_PAYMENT_PAYEEID("PayeeId", "payId"),
	CR_CARD_PAYMENT_GET_CURRENCY("", "curr"),
	CR_CARD_PAYMENT_TRANS_TYPE("", "payTxnTyp"),
	CR_CARD_PAYMENT_GET_ORG_CODE("", "orgCode"),
	CR_CARD_PAYMENT_GET_BENF_NAME("", "beneficiaryName"),

	EDIT_BENF_EXT_BENEF_LIST("EBE001", "beneficiaryId"),
	EDIT_BENF_EXT_BANK_CODE("EBE002", "tempbankCode"),
	EDIT_BENF_EXT_BANK_CODE_LIST("EBE003", "bankCode"),
	EDIT_BENF_EXT_BRANCH_CODE("EBE004", "tempbranchCode"),
	EDIT_BENF_EXT_BRANCH_CODE_LIST("EBE005", "branchCode"),
	EDIT_BENF_EXT_ACC_NO("EBE006", "accountNumber"),
	EDIT_BENF_EXT_BENF_NAME("EBE007", "beneficiaryName"),
	EDIT_BENF_EXT_NICK_NAME("EBE008", "beneficiaryNickName"),
	EDIT_BENF_EXT_VALIDATE("EBE009", "validate"),
	EDIT_BENF_EXT_CONFIRM("EBE010", "txnRefNo"),
	EDIT_BENF_EXT_PAYEE_TYPE("PayeeType", "payeeType"),
	EDIT_BENF_EXT_SERVICE_NAME("", "serviceName"),

	CR_CARD_UNBILLED_TRAN_CARD_LIST("CCUTD000", "crdNo"),
	CR_CARD_UNBILLED_TRAN_MERCHANT_LIST("CCUTD001", "merchant"),
	CR_CARD_UNBILLED_DETAILS("CCUTD002", "dtls"),

	//CR#46 Credit Card Link

	//LINK_CR_CARD_NUMBER("CCL000", "creditCardNumber"),
	LINK_CR_CARD_CONFIRM("CCL000", "txnRefNo"),
	LINK_CR_CARD_END("CCL001", "blank"),

	THIRD_PARTY_CC_PYMT_CC_NO("CCP3P001", "cardNo"),
	THIRD_PARTY_CC_VALIDATE_CC_NO("CCP3P002", "validateCardNo"),
	THIRD_PARTY_CC_PYMT_AMT("CCP3P003", "amt"),
	THIRD_PARTY_CC_PYMT_FROM_ACNT("CCP3P004", "frActNo"),
	THIRD_PARTY_CC_PYMT_RMRKS("payRemarks", "pmtRem"),
	THIRD_PARTY_CC_PYMT_VALIDATE("CCP3P005", "refNo"),
	THIRD_PARTY_CC_PYMT_CONFIRM("CCP3P006", "txnRefNo"),
	THIRD_PARTY_CC_PAYEEID("PayeeId", "payId"),
	THIRD_PARTY_CC_GET_CURRENCY("", "curr"),
	THIRD_PARTY_CC_TRANS_TYPE("", "payTxnTyp"),
	THIRD_PARTY_CC_GET_ORG_CODE("", "orgCode"),
	THIRD_PARTY_CC_GET_BENF_NAME("", "beneficiaryName"),

	USER_SELECTED_LOCATOR("", "userSelectedLocator"),
	BRANCH_LOCATOR_CITY_NAME("BL001", "cityNameLetter"),
	BRANCH_LOCATOR_CITY_LIST("BL002", "cityNameList"),
	BRANCH_LOCATOR_AREA_NAME("BL003", "areaNameLetter"),
	BRANCH_LOCATOR_AREA_LIST("BL004", "areaNameList"),
	BRANCH_LOCATOR_RESP("BL005", "address"),
	SELECTED_CITY_NAME("", "city"),
	SELECTED_AREA_NAME("", "area"),

	ATM_LOCATOR_CITY_NAME("AL001", "cityNameLetter"),
	ATM_LOCATOR_CITY_LIST("AL002", "cityNameList"),
	ATM_LOCATOR_AREA_NAME("AL003", "areaNameLetter"),
	ATM_LOCATOR_AREA_LIST("AL004", "areaNameList"),
	ATM_LOCATOR_RESP("AL005", "address"),

	//CR:73: Save Biller from Make One Time Payment
	ONE_TIME_BILL_PAYMENT_SAVE_BILLER_NICK_NAME("OTBPSB011", "billerNickName"),
	ONE_TIME_BILL_PAYMENT_SAVE_BILLER_VALIDATE("OTBPSB012", "validate"),
	ONE_TIME_BILL_PAYMENT_SAVE_BILLER_CONFIRM("OTBPSB013", "orgRefNo"),
	//Forgot Pin changes
	FORGOT_PIN_INST_CONFIRM("FOGP100", "txnRefNo"),
	FORGOT_PIN_BRANCH_SEARCHER("FOGP0001", "searcher"),

	FORGOT_PIN_BRANCH("FOGP001", "branchCode"),

	FORGOT_PIN_ACCOUNT("FOGP002", "accountNo"),

	FORGOT_PIN_TERM_N_COND_1("FOGP003", "condition"),
	FORGOT_PIN_INIT("FOGP004", "mobileNumber"),

	FORGOT_PIN_GET_QUESTION("FOGP005", "questionList"),

	/** The selfreg answer one. */
	FORGOT_PIN_ANSWER_ONE("FOGP006", "SQA"),

	FORGOT_PIN_EXECUTE("FOGP007", "execute"),
	FORGOT_PIN_LANGUAGE("prefLang", "prefLang"),

	// ADDED TO INCLUDE DEBIT CARD VALIDATION IN SELF REGISTRATION
	FORGOT_PIN_DEBITCARD_NO("FOGP008", "debitCardNo"),
	FORGOT_PIN_DEBITCARD_EXPIRYDATE("FOGP009", "debitCardExpiryDate"),
	FORGOT_PIN_DEBITCARD_VERIFICATION("FOGP010", "debitCardVerfication"),
	/** The chng pin new pass. */
	FORGOT_PIN_CHNG_PIN_NEW_PASS("FOGP011", "newPass"),

	/** The chng pin cnf new pass. */
	FORGOT_PIN_CHNG_PIN_CNF_NEW_PASS("FOGP012", "confNewPass"),

	/** The chng pin cnf confirm. */
	FORGOT_PIN_CHNG_PIN_CNF_CONFIRM("FOGP013", "confirm"),

	//CR 73: Save Benefeciary from Fund Transfer to Other Non Registered Accounts
	FUND_TRANSFER_OTHER_BARCLAYS_SAVE_BEN_INT_NICK_NAME("OBAFTNR019", "beneficiaryNickName"),
	FUND_TRANSFER_OTHER_BARCLAYS_SAVE_BEN_INT_VALIDATE("OBAFTNR020", "validate"),
	FUND_TRANSFER_OTHER_BARCLAYS_SAVE_BEN_INT_CONFIRM("OBAFTNR021", "txnRefNo"),
	//Lead Generation
	LEAD_GENERATION_PROD_LST("LG000", "prodNameList"),
	LEAD_GENERATION_PROD_SUB_LST("LG001", "prodNameList"),
	LEAD_GENERATION_CONFIRM("LG002", "txnRefNo"),
	LEAD_GENERATION_SUBMIT("LG003", "blank"),


	/*KITS Changes Starts*/
	/*Register*/
	KITS_REG_ACCOUNT_NUM("KR001", "accountNo"),
	KITS_REG_PRIMARY_ACC("KR002", "primaryAcc"),
	KITS_REG_CONFIRM("KR003", "validate"),
	KITS_REG_SUBMIT("KR004", "txnRefNo"),

	/*Deregister*/
	KITS_DEREG_ACCOUNT_NUM("KDR001", "accountNo"),
	KITS_DEREG_CONFIRM("KDR002", "validate"),
	KITS_DEREG_SUBMIT("KDR003", "txnRefNo"),

	/*Pay To Phone*/
	KITS_STP_ACCOUNT_NUM("SP001", "accountNo"),
	KITS_STP_MOBILE_NUM("SP002", "mobileNo"),
	KITS_STP_BANK_NAME("SP003", "bankName"),
	KITS_STP_AMOUNT("SP004", "amount"),
	KITS_STP_REASON("SP005", "reason"),
	KITS_STP_CONFIRM("SP006", "validate"),
	KITS_STP_SUBMIT("SP007", "txnRefNo"),

	/*Pay To Account*/
	KITS_STA_ACCOUNT_NUM("SA001", "accountNo"),
	KITS_STA_ACCOUNT_NUMBER("SA002", "accountNumber"),
	KITS_STA_BANK_CODE("SA003", "tempbankCode"),
	KITS_STA_BANK_CODE_LIST("SA004", "bankCode"),
	KITS_STA_AMOUNT("SA005", "amount"),
	KITS_STA_REASON("SA006", "reason"),
	KITS_STA_CONFIRM("SA007", "validate"),
	KITS_STA_SUBMIT("SA008", "txnRefNo"),

	/*KITS Changes Ends*/

	// CR85 Start

	//Register Beneficiary
	REG_BENF_BRANCH_SEARCHER("RBOBA01","tempbankCode"),
	REG_BENF_SELECT_BANK_NAME("RBOBA02","bankCode"),
	REG_BENF_ENTER_BRANCH_NAME("RBOBA03","tempbranchCode"),
	REG_BENF_SELECT_BRANCH_NAME("RBOBA04","branchCode"),
	REG_BENF_BENF_ACNO("RBOBA05","accountNumber"),
	REG_BENF_BENF_ADDRESS("RBOBA06","address"),
	REG_BENF_BENF_CITY("RBOBA07","city"),
	REG_BENF_BENF_NICK_NAME("RBOBA08","beneficiaryNickName"),
	REG_BENF_BENF_CONFIRM("RBOBA09","validate"),
	REG_BENF_BENF_SUBMIT("RBOBA10","txnRefNo"),

	//Fund Transfer Other Bank A/c
	KE_EXT_BANK_FT_BENEF_LIST("KEBAFT000", "benfList"),
	KE_EXT_BANK_FT_TO_AC("KEBAFT001", "payId"),
	KE_EXT_BANK_FT_ENTER_AMT("KEBAFT002", "amount"),
	KE_EXT_BANK_FT_SEL_FRM_AC("KEBAFT003", "frActNo"),
	KE_EXT_BANK_FT_RSO_PAYMENT("KEBAFT004", "rsonOfPayment"),
	KE_EXT_BANK_FT_PAYEE_INFO("KEBAFT005", "curr"),
	KE_EXT_BANK_FT_VALIDATE("KEBAFT006", "txnAmt"),
	KE_EXT_BANK_FT_EXECUTE("KEBAFT007", "txnRefNo"),


	//Edit Beneficiary
	EDIT_BENF_BENEFICIARY_NAME("EBOBA00","beneficiaryId"),
	EDIT_BENF_BRANCH_SEARCHER("EBOBA01","tempbankCode"),
	EDIT_BENF_SELECT_BANK_NAME("EBOBA02","bankCode"),
	EDIT_BENF_ENTER_BRANCH_NAME("EBOBA03","tempbranchCode"),
	EDIT_BENF_SELECT_BRANCH_NAME("EBOBA04","branchCode"),
	EDIT_BENF_BENF_ACNO("EBOBA05","accountNumber"),
	EDIT_BENF_BENF_ADDRESS("EBOBA06","address"),
	EDIT_BENF_BENF_NICK_NAME("EBOBA07","beneficiaryNickName"),
	EDIT_BENF_BENF_CONFIRM("EBOBA08","validate"),
	EDIT_BENF_BENF_SUBMIT("EBOBA09","txnRefNo"),

	//Delete Beneficiary
	OTHER_BANKACC_DEL_BENF_PAYEE_LIST("OBADB0", "benfList"),
	OTHER_BANKACC_DEL_BENF_PAYEE("OBADB1", "payId"),
	OTHER_BANKACC_DEL_BENF_VALIDATE("OBADB2", "payeeId"),
	OTHER_BANKACC_DEL_BENF_CONFIRM("OBADB3", "confirm"),
	//CR85 End

	//MZ DFT Start
	REG_BENF_GET_NIB_NO("RBOBA011","nib"),
	//MZ DFT End

	//GHIPS
	GHIPS_BANK_SEARCH("GHOOOBA01", "tempbankCode"),
	GHIPS_BANK_LIST("GHOOOBA02", "bankCode"),
	GHIPS_BANK_ACC_NO("GHOOOBA03", "accountNumber"),
	GHIPS_TRANSFER_AMT("GHOOOBA04", "amount"),
	GHIPS_TRANSFER_FROM_ACC_LIST("GHOOOBA05","frActNo"),
	GHIPS_PAYMENT_REASON_LIST("GHOOOBA06","rsonOfPayment"),
	GHIPS_PAYEE_INFO("GHOOOBA07","curr"),
	GHIPS_FUND_TRANSFER_CONFIRM("GHOOOBA08","txnAmt"),
	GHIPS_FUND_TRANSFER_SUBMIT("GHOOOBA09","txnRefNo"),
	GHIPS_FUND_TRANSFER_NICK_NAME("GHOOOBA10","beneficiaryNickName"),
	GHIPS_FUND_TRANSFER_NICK_NAME_CONFIRM("GHOOOBA11","validate"),
	GHIPS_FUND_TRANSFER_NICK_NAME_SUBMIT("GHOOOBA12","txnRefNo"),

	GHIPS_BENEF_LIST("GHSBOBA00", "benfList"),
	GHIPS_OTHER_BANK_FT_TO_AC("GHSBOBA01", "payId"),
	GHIPS_OTHER_BANK_FT_BENEF_INFO("GHSBOBA02", "benefInfo"),
	GHIPS_OTHER_BANK_FT_ENTER_AMT("GHSBOBA03", "amount"),
	GHIPS_OTHER_BANK_FT_AMT("GHSBOBA04", "frActNo"),
	GHIPS_OTHER_BANK_FT_PAYMENT_REASON_LIST("GHSBOBA05","rsonOfPayment"),
	GHIPS_OTHER_BANK_FT_PAYEE_INFO("GHSBOBA06","curr"),
	GHIPS_OTHER_BANK_FT_CONFIRM("GHSBOBA07", "txnAmt"),
	GHIPS_OTHER_BANK_FT_SUBMIT("GHSBOBA08", "txnRefNo"),

	GHIPS_REGISTER_BENEF_NON_BARCLAYS_BANK_SEARCH("GHRBOBA01","tempbankCode"),
	GHIPS_REGISTER_BENEF_BANK_LIST("GHRBOBA02","bankCode"),
	GHIPS_REGISTER_BENEF_BANK_ACC_NO("GHRBOBA03", "accountNumber"),
	GHIPS_REGISTER_BENEF_BANK_NICK_NAME("GHRBOBA04", "beneficiaryNickName"),
	GHIPS_REGISTER_BENEF_BANK_CONFIRM("GHRBOBA05","validate"),
	GHIPS_REGISTER_BENEF_BANK_ADD("GHRBOBA06","txnRefNo"),

	GHIPS_DELETE_BENEF_SERVICE_NAME("", "serviceName"),
	GHIPS_DELETE_BENEF_NICK_NAME_LIST("GHDBOBA00","benfList"),
	GHIPS_DELETE_BENEF_PAYEE("GHDBOBA01", "payId"),
	GHIPS_DELETE_BENEF_CONFIRM("GHDBOBA02","payeeId"),
	GHIPS_DELETE_BENEF_SUBMIT("GHDBOBA03","confirm"),

	//GHIPS ENDS
	//FreeDialUSSD AirTimeTopUp STARTS
	FREE_DIAL_SRC_MNO_LIST("FDU000", "list"),
	FREE_DIAL_MNO_LIST("FDU001", "billerId"),
	FREE_DIAL_MOB_NUM("", "mblNo"),
	FREE_DIAL_AMOUNT("FDU002", "txnAmt"),
	FREE_DIAL_ACCT_LIST("FDU003", "actNo"),
	FREE_DIAL_CONFIRM("FDU004", "confirm"),
	FREE_DIAL_TRANSACTION_REMARKS("", "txnNot"),
	FREE_DIAL_SUBMIT("FDU005", "txnRefNo"),
	//FreeDialUSSD AirTimeTopUp End

	//Group Wallet starts
	//MobileWallet
	//One-Off
	/*GROUP_WALLET_OPTIONS_LIST("MWGP0022", "txnRefNo"),
	GROUP_WALLET_MOBILE_NUMBER("MWGP0023","mblNo"),
	GROUP_WALLET_AMOUNT("MWGP0024","txnAmt"),
	GROUP_WALLET_SELECT_ACC("MWGP0025","frActNo"),
	GROUP_WALLET_ONEOFF_CONFIRM("MWGP0026","confirm"),
	GROUP_WALLET_ONEOFF_SUBMIT("MWGP0027","submit"),
	//Authorize
	SELECT_ACC_TRAN_RETRIVAL("MWGP0028","accno"),
	SELECT_TRANSACTION("MWGP0029","tranNo"),
	SELECT_TRAN_STATUS("MWGP0030","status"),
	TRANSACTION_SUBMITTED("MWGP0031","txnRefNo"),
	//Transaction History
	SELECT_ACC_TRANSACTION_HISTORY("MWGP0032","accNo"),
	SELECT_TRAN_FOR_HISTORY("MWGP0033","txnRefNo"),
	VIEW_HISTORY("MWGP0034","history"),

	//Fund Transfer
	//One-Off
	FT_GW_OPTIONS_LIST("FTGP0022", "txnRefNo"),
	FT_GW_MOBILE_NUMBER("FTGP0023","mblNo"),
	FT_GW_AMOUNT("FTGP0024","txnAmt"),
	FT_GW_SELECT_ACC("FTGP0025","frActNo"),
	FT_GW_ONEOFF_CONFIRM("FTGP0026","confirm"),
	FT_GW_ONEOFF_SUBMIT("FTGP0027","submit"),
	//Authorize
	FT_GW_ACC_TRAN_RETRIVAL("FTGP0028","accno"),
	FT_GW_TRANSACTION("FTGP0029","tranNo"),
	FT_GW_TRAN_STATUS("FTGP0030","status"),
	FT_GW_TRANSACTION_SUBMITTED("FTGP0031","txnRefNo"),
	//Transaction History
	FT_GW_ACC_TRANSACTION_HISTORY("FTGP0032","accNo"),
	FT_GW_TRAN_FOR_HISTORY("FTGP0033","txnRefNo"),
	FT_GW_VIEW_HISTORY("FTGP0034","history"),

	//Group Wallet ends
*/
	//GePG Starts

//Group Wallet starts
//MobileWallet
//One-Off
GROUP_WALLET_OPTIONS_LIST("MWGP0022", "txnRefNo"),
GROUP_WALLET_MOBILE_NUMBER("MWGP0023","mblNo"),
GROUP_WALLET_AMOUNT("MWGP0024","txnAmt"),
GROUP_WALLET_SELECT_ACC("MWGP0025","frActNo"),
GROUP_WALLET_ONEOFF_CONFIRM("MWGP0026","confirm"),
GROUP_WALLET_ONEOFF_SUBMIT("MWGP0027","submit"),
//Authorize
SELECT_ACC_TRAN_RETRIVAL("MWGP0028","accno"),
SELECT_TRANSACTION("MWGP0029","tranNo"),
GET_TRANSACTION_DETAILS("MWGP0035","tranNoSelectedDetails"),
SELECT_TRAN_STATUS("MWGP0030","status"),
TRANSACTION_SUBMITTED("MWGP0031","txnRefNo"),
//Transaction History
SELECT_ACC_TRANSACTION_HISTORY("MWGP0032","accNo"),
SELECT_TRAN_FOR_HISTORY("MWGP0033","txnRefNo"),
VIEW_HISTORY("MWGP0034","history"),

//Fund Transfer
//One-Off
FT_GW_OPTIONS_LIST("FTGP0022", "txnRefNo"),
FT_GW_MOBILE_NUMBER("FTGP0023","mblNo"),
FT_GW_AMOUNT("FTGP0024","txnAmt"),
FT_GW_SELECT_ACC("FTGP0025","frActNo"),
FT_GW_ONEOFF_CONFIRM("FTGP0026","confirm"),
FT_GW_ONEOFF_SUBMIT("FTGP0027","submit"),
//Authorize
FT_GW_ACC_TRAN_RETRIVAL("FTGP0028","accno"),
FT_GW_TRANSACTION("FTGP0029","tranNo"),
FT_GW_TRAN_STATUS("FTGP0030","status"),
FT_GW_TRANSACTION_SUBMITTED("FTGP0031","txnRefNo"),
//Transaction History
FT_GW_ACC_TRANSACTION_HISTORY("FTGP0032","accNo"),
FT_GW_TRAN_FOR_HISTORY("FTGP0033","txnRefNo"),
FT_GW_VIEW_HISTORY("FTGP0034","history"),
GET_FUND_TRAN_DETAILS("FTGP0035","tranNoSelectedDetails"),

//Group Wallet ends

	//GePG Starts

	GePG_CONTROL_NUMBER("GEPGB001", "ctrlNo"),
	GePG_BILLDETAILS("GEPGB002", "billDetails"),
	GePG_EXACT_AMOUNT("GEPGB003", "exactAmount"),
	GePG_FULL_PARTIAL_AMOUNT("GEPGB004", "fullPartialAmount"),
	GePG_PAY_BILLS_FROM_ACNT("GEPGB005", "frActNo"),
	GePG_PAY_BILLS_CONFIRM("GEPGB006", "blank"),

	GePG_PAY_BILLS_SUBMIT("GEPGB007", "txnRefNo"),

	//GePG Ends

	//MasterPassQR Biller
	MASTER_QR_BILLER_DETAILS("MPQR000","details")

;
/** tranId. */
private String tranId;

/** paramName. */
private String paramName;

/**
 * Instantiates a new uSSD input params enum.
 *
 * @param tranid
 *            the tranid
 * @param paramName
 *            the param name
 */
private USSDInputParamsEnum(final String tranid, final String paramName) {
    tranId = tranid;
    this.paramName = paramName;
}

/**
 * Gets the tran id.
 *
 * @return the tranId
 */
public String getTranId() {
    return tranId;
}

/**
 * Gets the param name.
 *
 * @return the paramName
 */
public String getParamName() {
    return paramName;
}

/**
 * Gets the ussd param for tran.
 *
 * @param tranid
 *            the tranid
 * @return String This method returns param name based on transaction id
 */
public static String getUssdParamForTran(final String tranid) {
    for (final USSDInputParamsEnum ele : USSDInputParamsEnum.values()) {
	if (ele.getTranId().equalsIgnoreCase(tranid)) {
	    return ele.getParamName();
	}
    }
    return null;
}
}
