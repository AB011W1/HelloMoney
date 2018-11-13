package com.barclays.ussd.bmg.requesDecisionMaker;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.bmg.factory.response.IUSSDDummyResponseFactory;
import com.barclays.ussd.common.configuration.ConfigurationManager;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.svc.httpclient.CommonHttpClientExecutor;
import com.barclays.ussd.utils.IDummyResponse;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.RetrieveAccList;

public class CallBMGAccordingRequestImpl implements CallBMGAccordingRequestInter {

    @Autowired
    CommonHttpClientExecutor commonHttpClientExecuter;

    @Autowired
    IUSSDDummyResponseFactory ussdDummyResponseFactory;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(CallBMGAccordingRequestImpl.class);

    private static final Logger TIMELOGGER = Logger.getLogger("com.barclays.ussd.audit-time");

    private static final String SUCCESS_RESCDE = "00000";

    private static final String SUCCESS = "SUCCESS";

    private static final String ERROR = "ERROR";

    private static final String FAILURE = "FAILURE";

    public String callBMGClient(USSDBaseRequest ussdBaseRequest, boolean demoModeFlag, String businessId, String countryCode)
	    throws USSDBlockingException, USSDNonBlockingException {

	String response = null;

	ObjectMapper mapper = new ObjectMapper();
	String gatewayBaseURL = ConfigurationManager.getString(USSDConstants.BMG_BASE_URL);

	boolean useLocalGateway = ConfigurationManager.getBoolean(USSDConstants.ENABLE_LOCAL_GATEWAY, false);

	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("callBMGClient method starts...");
	}

	Map<String, String> queryParamMap = ussdBaseRequest.getRequestParamMap();

	// if (1 == 1) {
	if (demoModeFlag) {
	    IDummyResponse dummyResponseBuilder = ussdDummyResponseFactory.getDummyResponseBuilderObject(businessId, countryCode);
	    response = dummyResponseBuilder.populateDummyBMGResponse(queryParamMap.get(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME));
	} else {
	    response = commonHttpClientExecuter.invokebmg(useLocalGateway, gatewayBaseURL, queryParamMap);
	    String rescCode = "";
	    String responseCode = "";
	    String responseMsg = "";

	    if (TIMELOGGER.isInfoEnabled()) {
		if (response != null && response.contains("resCde")) {
		    RetrieveAccList accList;
		    try {
			accList = mapper.readValue(response, RetrieveAccList.class);
		    } catch (JsonParseException e) {
			LOGGER.error("JsonParseException : ", e);
			throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		    } catch (JsonMappingException e) {
			LOGGER.error("JsonParseException : ", e);
			throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		    } catch (Exception e) {
			LOGGER.error("Exception : ", e);
			if (e instanceof USSDNonBlockingException) {
			    throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
			} else {
			    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
			}
		    }

		    rescCode = accList.getPayHdr().getResCde();
		    responseMsg = accList.getPayHdr().getResMsg();

		    responseCode = getStatus(rescCode);
		} else {
		    responseCode = FAILURE;
		}

		MDC.put("BMG_OPCODE", queryParamMap.get(USSDConstants.BMG_LOCAL_KE_OPCODE_PARAM_NAME) + "," + responseCode + "," + rescCode);
	    }
	}
	return response;
    }

    private String getStatus(String resCde) {
	if (SUCCESS_RESCDE.equals(resCde)) {
	    return SUCCESS;
	} else {
	    return ERROR;
	}

    }

    public void setCommonHttpClientExecuter(CommonHttpClientExecutor commonHttpClientExecuter) {
	this.commonHttpClientExecuter = commonHttpClientExecuter;
    }

}
