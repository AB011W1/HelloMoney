package com.absa.ussd.bmg.databundle;

import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.bean.BillersListDO;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.Biller;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Payee;

public class DatatBundleAirtimeRequestBuilder implements BmgBaseRequestBuilder{

	@Override
	public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO)
			throws USSDNonBlockingException, USSDBlockingException {
		
		USSDBaseRequest request = new USSDBaseRequest();
		Map<String, String> userInputMap = requestBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
		Map<String, String> requestParamMap = new HashMap<String, String>();
		Biller biller = (Biller) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get("BundleBiller");
		
		request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
		request.setOpCde(requestBuilderParamsDTO.getBmgOpCode());

		if (null != userInputMap) {
			requestParamMap.put(USSDConstants.GEPG_BMG_CONTROL_NUMBER_PARAM_NAME, userInputMap.get("billerRefNumber"));
		}
		if(null != biller){
				requestParamMap.put(USSDConstants.GEPG_BMG_BILLER_ID_PARAM_NAME, biller.getBillerId());
				requestParamMap.put(USSDConstants.GEPG_BMG_BILLER_NM_PARAM_NAME, biller.getBillerName());
		}
		else if(null != requestBuilderParamsDTO && null != requestBuilderParamsDTO.getUssdSessionMgmt() 
				&& null != requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
				&& null != requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(USSDInputParamsEnum.AIRTIME_TOPUP_BENF_DTlS.getTranId())) {
			Payee payee=(Payee)requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(USSDInputParamsEnum.AIRTIME_TOPUP_BENF_DTlS.getTranId());
			requestParamMap.put(USSDConstants.GEPG_BMG_BILLER_ID_PARAM_NAME, payee.getBilrId());
			requestParamMap.put(USSDConstants.GEPG_BMG_BILLER_NM_PARAM_NAME, payee.getBilrNam());
		}
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
		request.setRequestParamMap(requestParamMap);

		return request;
	}

}
