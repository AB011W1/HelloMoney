package com.barclays.ussd.bmg.probase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.BillerArea;
import com.barclays.ussd.bean.BillersListDO;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;

public class ProbaseAccountRequestBuilder implements BmgBaseRequestBuilder{

	private static final String BILLER_TYPE = "billerType";
	@Override
	public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {

		USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
		USSDBaseRequest ussdBaseRequest = new USSDBaseRequest();
    	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
    	Map<String, String> requestParamMap = new HashMap<String, String>();
    	BillersListDO biller = (BillersListDO) ussdSessionMgmt.getTxSessions().get(USSDConstants.PROBASE_BILLER_INFO);

    	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
		requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
		requestParamMap.put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_BLRS_LST.getParamName(), biller.getBillerId());

		List<BillerArea> billerArea = (List<BillerArea>) requestBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
		.get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_AREA_CODE.getTranId());
		if(billerArea != null){
			int selectedAreaInput = Integer.parseInt(userInputMap.get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_AREA_CODE.getParamName()));

			String selectArea = billerArea.get(selectedAreaInput - 1).getAreaName();
			requestParamMap.put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_AREA_CODE.getParamName(), selectArea);
		}
		ussdBaseRequest.setRequestParamMap(requestParamMap);
		return ussdBaseRequest;
	}
}