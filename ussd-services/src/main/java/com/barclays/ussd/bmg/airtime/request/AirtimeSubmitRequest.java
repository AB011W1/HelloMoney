package com.barclays.ussd.bmg.airtime.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.BillDetails;

public class AirtimeSubmitRequest implements BmgBaseRequestBuilder {

    @SuppressWarnings("unchecked")
    @Override
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {
	USSDBaseRequest request = new USSDBaseRequest();

	Map<String, String> requestParamMap = new HashMap<String, String>();

	List<String> txnRefNoLst = (List<String>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
		USSDInputParamsEnum.AIRTIME_CONFIRM.getTranId());

	requestParamMap.put(USSDInputParamsEnum.AIRTIME_SUBMIT.getParamName(), txnRefNoLst.get(0));

	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	
	//Ghana data bundle change
		String transNodeId;
		if(null != requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails()  && 
				null != requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap() &&
				null != requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().get("TransNodeId"))
		{
			transNodeId = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().get("TransNodeId");
			if(null != transNodeId && transNodeId.equalsIgnoreCase("ussd0.10GHBRB")) {
			Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
			int dataBundleInput =Integer.parseInt(userInputMap.get(USSDInputParamsEnum.DATA_BUNDLE_SEL_BUNDLE_LIST.getParamName()));
			USSDSessionManagement ussdSessionMgmt=requestBuilderParamsDTO.getUssdSessionMgmt();
			BillDetails billDetails = new BillDetails();
			if(null != ussdSessionMgmt && null != ussdSessionMgmt.getTxSessions()){
				billDetails = (BillDetails) ussdSessionMgmt.getTxSessions().get("DataBundleDetails");
			}
			LinkedHashMap<String, String> bundleHashMap = billDetails.getBillInvoiceDetails().getInvoiceRefNo();
			List<String> dataBundleList = new ArrayList<String>(bundleHashMap.values());
			String Invoicereferencenumber = dataBundleList.get(dataBundleInput-1);
			requestParamMap.put("InvoiceReferenceNo", Invoicereferencenumber);
			}
			requestParamMap.put("TransNodeId", transNodeId);
		}
	request.setRequestParamMap(requestParamMap);

	return request;
    }
}
