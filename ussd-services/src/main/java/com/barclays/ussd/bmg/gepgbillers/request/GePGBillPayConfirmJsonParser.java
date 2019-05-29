/**
 * PayBillSubmit.java
 */
package com.barclays.ussd.bmg.gepgbillers.request;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.BillDetails;
import com.barclays.ussd.utils.jsonparsers.bean.otbp.OTBPInitAccount;

/**
 * @author BTCI This class builds request for Confirmation screen, bill pay
 *
 */
public class GePGBillPayConfirmJsonParser implements BmgBaseRequestBuilder {
	/** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(GePGBillPayConfirmJsonParser.class);

    @Autowired
    UssdResourceBundle ussdResourceBundle;

    @SuppressWarnings("unchecked")
	public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {

    	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
    	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
    	USSDBaseRequest ussdBaseRequest = new USSDBaseRequest();
    	Map<String, String> requestParamMap = new HashMap<String, String>();
    	Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();
    	String businessId = ussdSessionMgmt.getBusinessId();
    	BillDetails billDetails = (BillDetails) txSessions.get(USSDConstants.GePG_BILL_DETAIL);
    	String primaryRefNum = null;
    	if(null != userInputMap)
    		primaryRefNum = userInputMap.get(USSDInputParamsEnum.GePG_CONTROL_NUMBER.getParamName());
    	String selectedFrmDstvType = null;
    	if(null != userInputMap)
    		selectedFrmDstvType = userInputMap.get((USSDConstants.SELECTED_DSTV_BILLER_TYPE));

    	if(selectedFrmDstvType != null && USSDConstants.BUSINESS_ID_ZWBRB.equalsIgnoreCase(businessId)
    			&& (null != userInputMap && "DSTVZIM-2".equalsIgnoreCase(userInputMap.get(USSDConstants.SELECTED_BILLER_OTBP)))
    			&& (null != primaryRefNum && ("DSTV BO").equalsIgnoreCase(selectedFrmDstvType) &&!("BO").equalsIgnoreCase(primaryRefNum.substring(0, 2)))){

    		primaryRefNum = "BO" + primaryRefNum;

    	}//end

    	LOGGER.info("Creating request map");
    	// billerRefNumber
    	requestParamMap.put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_BL_REF.getParamName(), primaryRefNum);

		// fromActNumber
		List<OTBPInitAccount> accounts = (List<OTBPInitAccount>) txSessions.get(USSDInputParamsEnum.GePG_PAY_BILLS_FROM_ACNT.getTranId());
		if(null != userInputMap)
			requestParamMap.put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_ACNT_NOS.getParamName(), accounts.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.GePG_PAY_BILLS_FROM_ACNT.getParamName())) - 1).getActNo());

    	// amount
		if(null != userInputMap && null != billDetails && ( USSDConstants.GePG_PARTIAL_BILL_PAYMENT_TYPE.equalsIgnoreCase(billDetails.getPaymentType()) ||
				USSDConstants.GePG_FULL_BILL_PAYMENT_TYPE.equalsIgnoreCase(billDetails.getPaymentType()))){
			requestParamMap.put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_ENTER_AMT.getParamName(), userInputMap.get(USSDInputParamsEnum.GePG_FULL_PARTIAL_AMOUNT.getParamName()));
		} else if(null != billDetails){
			requestParamMap.put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_ENTER_AMT.getParamName(), billDetails.getFeeAmount().getAmount().toString());
		}
    	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
    	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);

    	String transactionRemarks = ussdResourceBundle.getLabel(USSDConstants.TRANSACTION_REMARKS_BILL_PAYMENT, new Locale(ussdSessionMgmt
    		.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
    	requestParamMap.put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_TRAN_REMARKS.getParamName(), transactionRemarks);

    	//Instead of Area code, we are passing the reference number returned in the Bill Details to identify the Payment gateway id GePG
	    if (null != billDetails){
    		requestParamMap.put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_AREA_CODE.getParamName(), billDetails.getSecondaryReferenceNumber());
    	}

    	ussdBaseRequest.setRequestParamMap(requestParamMap);
    	return ussdBaseRequest;

    }

}
