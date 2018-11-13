package com.barclays.bmg.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.dao.TacticalHelloMoneyVerifyUserDAO;
import com.barclays.bmg.service.request.TacticalHelloMoneyVerifyUserServiceRequest;
import com.barclays.bmg.service.response.TacticalHelloMoneyVerifyUserServiceResponse;

public class TacticalHelloMoneyVerifyUserDAOImpl implements TacticalHelloMoneyVerifyUserDAO {

    private static final String THM_PREFIX = "THM";
    private static final Logger LOGGER = Logger.getLogger(TacticalHelloMoneyVerifyUserDAOImpl.class);
    private ThmHttpClientExecutor thmHttpClientExecutor;
    private static final String SHUD_CHNG_PIN = "BEM08744";
    private static final String THM_IS_SUCCESS = "00";
    private static Map<String, String> errorMessageMap;

    public TacticalHelloMoneyVerifyUserDAOImpl() {
	errorMessageMap = new HashMap<String, String>();
	errorMessageMap.put("00", "CUSTOMER VALIDATION SUCCESSFUL");
	errorMessageMap.put("001", "CUSTOMER NOT FOUND");
	errorMessageMap.put("002", "CUSTOMER ALREADY SUSPENDED");
	errorMessageMap.put("003", "CUSTOMER IS ALREADY BLACK LISTED");
	errorMessageMap.put("004", "ON NEXT ATTEMPT CUSTOMER WILL BE BLACK LISTED");
	errorMessageMap.put("005", "CUSTOMER BLACK LISTED");
	errorMessageMap.put("007", "EITHER MSISDN OR PIN ARE NOT RECIEVED");
	errorMessageMap.put("008", "PIN IS INVALID");

    }

    @Override
    public TacticalHelloMoneyVerifyUserServiceResponse verifyUserWithThmPin(TacticalHelloMoneyVerifyUserServiceRequest request) {
	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Before hitting THM");
	}

	TacticalHelloMoneyVerifyUserServiceResponse response = new TacticalHelloMoneyVerifyUserServiceResponse();
	response.setContext(request.getContext());
	String thmResponse = "";
	try {

	    // thmResponse = getResponseFromString("ERROR_CODE=005");
	    thmHttpClientExecutor.setUrl(request.getThmUrl());
	    thmResponse = getResponseFromString(thmHttpClientExecutor.processHttpRequest(request.getCustMSISDN(), request.getCustPIN()));

	    LOGGER.debug(thmResponse);
	    //Forgot Pin Change
	    //Chnage as if thmResponse is null then its getting issue
	    if (THM_IS_SUCCESS.equals(thmResponse)) {
		    response.setResCde(validatePassword(request.getCustPIN()));
		    response.setResMsg(errorMessageMap.get(thmResponse));
		} else if("".equals(thmResponse)){
			response.setResCde(THM_PREFIX + ErrorCodeConstant.THM_CONNECTIVITY);
		    response.setSuccess(false);
		}
			else {
			// add these codes in DB for auditing.
		    response.setResCde(THM_PREFIX + thmResponse);
		    response.setResMsg(errorMessageMap.get(thmResponse));
		    response.setSuccess(false);
		}
	} catch (Exception e) {
	    response.setResCde(THM_PREFIX + ErrorCodeConstant.THM_CONNECTIVITY);
	    response.setSuccess(false);
	}

	return response;
    }

    /**
     * Response statuses as received from THM team. SUCCESS - ERROR_CODE=00 CUSTOMER NOT FOUND – ERROR_CODE=001 CUSTOMER ALREADY SUSPENDED –
     * ERROR_CODE=002 CUSTOMER IS ALREADY BLACK LISTED – ERROR_CODE=003 ON NEXT ATTEMPT CUSTOMER WILL BE BLACK LISTED – ERROR_CODE=004 CUSTOMER BLACK
     * LISTED – ERROR_CODE=005 EITHER MSISDN OR PIN ARE NOT RECIEVED - ERROR_CODE=007
     * */
    private String getResponseFromString(String thmResponseString) {
	return thmResponseString.substring(thmResponseString.indexOf("=") + 1);

    }

    private static String validatePassword(final String userInput) {
	String sequence = "0123456789";
	if (sequence.contains(userInput) || isSameChars(userInput.toCharArray())
		|| sequence.contains(new StringBuffer(userInput).reverse().toString())) {
	    return SHUD_CHNG_PIN;
	}
	return THM_PREFIX + ErrorCodeConstant.SUCCESS_CODE;
	// return ErrorCodeConstant.SUCCESS_CODE;
    }

    private static boolean isSameChars(char[] sequence) {
	boolean validate = true;
	int sequenceLength = sequence.length - 1;
	for (int i = 0; i < sequenceLength; i++) {
	    if (sequence[i] != sequence[i + 1]) {
		validate = false;
		break;
	    }
	}
	return validate;
    }

    public ThmHttpClientExecutor getThmHttpClientExecutor() {
	return thmHttpClientExecutor;
    }

    public void setThmHttpClientExecutor(ThmHttpClientExecutor thmHttpClientExecutor) {
	this.thmHttpClientExecutor = thmHttpClientExecutor;
    }

}
