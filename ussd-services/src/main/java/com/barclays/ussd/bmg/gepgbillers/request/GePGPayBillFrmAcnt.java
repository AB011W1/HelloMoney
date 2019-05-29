/**
 * PayBillSelFrmAcnt.java
 */
package com.barclays.ussd.bmg.gepgbillers.request;

import java.util.HashMap;
import java.util.Map;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.BillersListDO;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;

/**
 * @author BTCI This class builds request for Selecting from account
 *
 */
public class GePGPayBillFrmAcnt implements BmgBaseRequestBuilder {

    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {

    	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
    	USSDBaseRequest ussdBaseRequest = new USSDBaseRequest();
    	if(null != requestBuilderParamsDTO.getUserInput() && 0 >= Integer.parseInt(requestBuilderParamsDTO.getUserInput())){
    		throw new USSDNonBlockingException(USSDExceptions.BPY00604.getBmgCode());
    	} else {
	    	// set the biller id in the request for bmg call.
	    	BillersListDO biller = (BillersListDO) ussdSessionMgmt.getTxSessions().get(USSDConstants.GEPG_BILLER_INFO);
	    	Map<String, String> requestParamMap = new HashMap<String, String>();
	    	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	    	requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	    	requestParamMap.put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_BLRS_LST.getParamName(), biller.getBillerId());
	    	ussdBaseRequest.setRequestParamMap(requestParamMap);
    	}
		return ussdBaseRequest;
    }

}