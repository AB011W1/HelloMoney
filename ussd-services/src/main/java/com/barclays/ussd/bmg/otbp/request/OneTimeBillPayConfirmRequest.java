package com.barclays.ussd.bmg.otbp.request;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.dto.BillerCreditDTO;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.BillerArea;
import com.barclays.ussd.bean.BillersListDO;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.sysprefs.services.ListValueResServiceImpl;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.BillDetails;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;
import com.barclays.ussd.utils.jsonparsers.bean.otbp.OTBPInitAccount;

public class OneTimeBillPayConfirmRequest implements BmgBaseRequestBuilder {
    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(OneTimeBillPayConfirmRequest.class);
    @Autowired
    UssdResourceBundle ussdResourceBundle;

    @Autowired
    private ListValueResServiceImpl listValueResService;
    private static final String CREDIT_CARD_ACCOUNT_TYPE = "CC";
	private static final String CREDIT_CARD_FLAG = "creditCardFlag";
	private static final String CREDIT_CARD_FLAG_VALUE = "CreditCardOT";
	private static final String CREDIT_ACTIVITY_ID = "PMT_BP_BILLPAY_ONETIME";

    public ListValueResServiceImpl getListValueResService() {
	return listValueResService;
    }

    public void setListValueResService(ListValueResServiceImpl listValueResService) {
	this.listValueResService = listValueResService;
    }

    @SuppressWarnings("unchecked")
    @Override
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {

	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, String> userInputMap = new HashMap<String, String>();
	if(null != ussdSessionMgmt)
		userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	USSDBaseRequest ussdBaseRequest = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>();
	/*String userInput = userInputMap.get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_ACNT_NOS.getParamName());
	List<OTBPInitAccount> accounts = (List<OTBPInitAccount>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_ACNT_NOS.getTranId());
	int selectedAccountIndex = Integer.parseInt(userInputMap.get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_ACNT_NOS.getParamName()));

	LOGGER.info("Creating request map");

	// request parameters
	// fromActNumber
	requestParamMap.put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_ACNT_NOS.getParamName(), accounts.get(selectedAccountIndex - 1).getActNo());
*/
	//CR-57
	String businessId = requestBuilderParamsDTO.getUssdSessionMgmt().getBusinessId();
	String refNum = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap()
	.get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_BL_REF.getParamName());
	String selectedFrmDstvType = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap()
	.get((USSDConstants.SELECTED_DSTV_BILLER_TYPE));

	if(selectedFrmDstvType != null && businessId.equalsIgnoreCase(USSDConstants.BUSINESS_ID_ZWBRB) && userInputMap.get(USSDConstants.SELECTED_BILLER_OTBP).equalsIgnoreCase("DSTVZIM-2")
			&& selectedFrmDstvType.equalsIgnoreCase("DSTV BO") &&!refNum.substring(0, 2).equalsIgnoreCase("BO") ){

		String dstvBoRef = "BO" + userInputMap.get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_BL_REF.getParamName());
		userInputMap.put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_BL_REF.getParamName(), dstvBoRef);

	}//end

	// billerRefNumber
	Map<String, Object> txSessions =requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
	String billerRefNumber = userInputMap.get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_BL_REF.getParamName());

	requestParamMap.put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_BL_REF.getParamName(), billerRefNumber);
	BillerCreditDTO billerCreditDTO = (BillerCreditDTO) txSessions.get("BillerCreditDTO");
	if(CREDIT_CARD_FLAG_VALUE.equals(txSessions.get("CreditCardOT"))){

		List<CustomerMobileRegAcct> creditCardList = (List<CustomerMobileRegAcct>) txSessions
					.get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_ENTER_CARD_LIST.getTranId());
		String userCreditSelection = userInputMap.get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_ENTER_CARD_LIST.getParamName());
		CustomerMobileRegAcct creditCard = creditCardList.get(Integer.parseInt(userCreditSelection) - 1);
		requestParamMap.put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_ENTER_CARD_LIST.getParamName(), creditCard.getActNo());
        //Added card no details to fetch only selected card
		requestParamMap.put("ccNumber", creditCard.getCrdNo());
		requestParamMap.put(CREDIT_CARD_FLAG,CREDIT_CARD_FLAG_VALUE);
	    requestParamMap.put(USSDInputParamsEnum.RETRIVE_ACCOUNT_TYPE.getParamName(), CREDIT_CARD_ACCOUNT_TYPE);
     	requestParamMap.put(USSDInputParamsEnum.ACTIVITY_ID.getParamName(), CREDIT_ACTIVITY_ID);
     	requestParamMap.put("actionCode", billerCreditDTO.getActionCode());
		requestParamMap.put("storeNumber",billerCreditDTO.getStoreNumber());

		} else
		{

			// fromActNumber
			String userInput = userInputMap.get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_ACNT_NOS.getParamName());
			List<OTBPInitAccount> accounts = (List<OTBPInitAccount>) ussdSessionMgmt.getTxSessions().get(
				USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_ACNT_NOS.getTranId());
			int  selectedAccountIndex = Integer.parseInt(userInputMap.get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_ACNT_NOS.getParamName()));

			LOGGER.info("Creating request map");

			requestParamMap.put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_ACNT_NOS.getParamName(), accounts.get(selectedAccountIndex - 1).getActNo());
		}

	// amount
	//added for probase
	BillDetails billDetails = new BillDetails();
	if(null != ussdSessionMgmt && null != ussdSessionMgmt.getTxSessions() && null!=ussdSessionMgmt.getTxSessions().get(USSDConstants.PROBASE_BILL_DETAILS)){
		billDetails = (BillDetails) ussdSessionMgmt.getTxSessions().get(USSDConstants.PROBASE_BILL_DETAILS);
	}
	BillersListDO billersListDO = null;
	if(null != ussdSessionMgmt)
		billersListDO = (BillersListDO) ussdSessionMgmt.getTxSessions().get(USSDConstants.PROBASE_BILLER_INFO);
	if("ZMBRB".equalsIgnoreCase( businessId) && null!= billDetails && null != billersListDO && ("NAPSA".equalsIgnoreCase(billersListDO.getBillerCategoryId())  || "ZRA".equalsIgnoreCase(billersListDO.getBillerCategoryId())) ){
		requestParamMap.put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_ENTER_AMT.getParamName(), String.valueOf(billDetails.getFeeAmount().getAmount().doubleValue()));
	}
	else {
		requestParamMap.put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_ENTER_AMT.getParamName(), ussdSessionMgmt.getUserTransactionDetails()
		.getUserInputMap().get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_ENTER_AMT.getParamName()));
	}
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	String transactionRemarks = ussdResourceBundle.getLabel(USSDConstants.TRANSACTION_REMARKS_BILL_PAYMENT, new Locale(ussdSessionMgmt
		.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
	requestParamMap.put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_TRAN_REMARKS.getParamName(), transactionRemarks);

	/*String areaSearchChar = userInputMap.get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_AREA_SEARCHER.getParamName());
	if (StringUtils.isNotEmpty(areaSearchChar)) {*/
	    List<BillerArea> billerArea = (List<BillerArea>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
		    .get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_AREA_CODE.getTranId());
	if(billerArea != null)
	{
	    int selectedAreaInput = Integer.parseInt(userInputMap.get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_AREA_CODE.getParamName()));

	    String selectArea = billerArea.get(selectedAreaInput - 1).getAreaName();
	    requestParamMap.put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_AREA_CODE.getParamName(), selectArea);
	}
	//}

	ussdBaseRequest.setRequestParamMap(requestParamMap);
	return ussdBaseRequest;
    }

}