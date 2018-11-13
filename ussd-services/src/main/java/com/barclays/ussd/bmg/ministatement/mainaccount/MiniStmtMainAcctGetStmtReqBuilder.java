/**
 * CASADetailBuilder.java
 */
package com.barclays.ussd.bmg.ministatement.mainaccount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccountDetails;

/**
 * @author BTCI
 * 
 */
public class MiniStmtMainAcctGetStmtReqBuilder implements BmgBaseRequestBuilder {
    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(MiniStmtMainAcctGetStmtReqBuilder.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder#getRequestObject (com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO)
     */
    @SuppressWarnings("unchecked")
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	USSDBaseRequest request = new USSDBaseRequest();
	Map<String, String> requestParamMap = new HashMap<String, String>(3);

	USSDSessionManagement ussdSessionMgmt = requestBuilderParamsDTO.getUssdSessionMgmt();
	List<AccountDetails> lstAccntDet = (List<AccountDetails>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.MINI_STMT_SEL_AC.getTranId());
	if (lstAccntDet != null && !lstAccntDet.isEmpty()) {
	    request.setMsisdnNo(requestBuilderParamsDTO.getMsisdnNo());
	    requestParamMap.put(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME, requestBuilderParamsDTO.getBmgOpCode());
	    requestParamMap.put(USSDConstants.BMG_LOCAL_KE_SERVICE_VER_NAME, USSDConstants.BMG_SERVICE_VERSION_VALUE);
	    if (LOGGER.isInfoEnabled()) {
		LOGGER.info("Fetching the mini statment for main A/c No:" + ussdSessionMgmt.getUserProfile().getMainAccount().getAccountNumber());
	    }
	    requestParamMap.put(USSDInputParamsEnum.MINI_STMT_SEL_AC.getParamName(), ussdSessionMgmt.getUserProfile().getMainAccount()
		    .getAccountNumber());
	    request.setRequestParamMap(requestParamMap);
	}
	return request;
    }

}
