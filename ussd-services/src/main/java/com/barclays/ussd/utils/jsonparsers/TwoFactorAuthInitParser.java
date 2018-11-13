package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bean.TwoFactorQuestion;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.twofactauth.TwoFactorResponse;

public class TwoFactorAuthInitParser implements BmgBaseJsonParser {

    private static final Logger LOGGER = Logger.getLogger(TwoFactorAuthInitParser.class);

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDBlockingException, USSDNonBlockingException {
	MenuItemDTO menuDTO = new MenuItemDTO();

	ObjectMapper mapper = new ObjectMapper();
	try {
	    TwoFactorResponse twoFactorResponse = mapper.readValue(responseBuilderParamsDTO.getJsonString(), TwoFactorResponse.class);
	    if (twoFactorResponse != null) {
		if (twoFactorResponse.getPayHdr() != null
			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(twoFactorResponse.getPayHdr().getResCde())) {

		    setTwoFactQuesListToSession(twoFactorResponse.getPayData().getQuestionWithPositions(), responseBuilderParamsDTO);
		} else if (twoFactorResponse.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(twoFactorResponse.getPayHdr().getResCde());
		} else {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		}
	    } else {
		LOGGER.error("Invalid response got from the BMG " + responseBuilderParamsDTO.getBmgOpCode());
		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }
	} catch (Exception e) {
	    LOGGER.error("Exception : ", e);
	    if (e instanceof USSDNonBlockingException) {
		throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
	    } else {
		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }
	}
	setNextScreenSequenceNumber(menuDTO);
	return menuDTO;
    }

    private void setTwoFactQuesListToSession(List<TwoFactorQuestion> twoFactQuestionList, ResponseBuilderParamsDTO responseBuilderParamsDTO)
	    throws USSDNonBlockingException {
	USSDSessionManagement ussdSessionManagement = responseBuilderParamsDTO.getUssdSessionMgmt();

	// set the question list into session
	// ussdSessionManagement.getTxSessions().put(USSDInputParamsEnum.TWO_FACTOR_VERIFY.getTranId(), twoFactorResponse.getPayData());
	if (twoFactQuestionList != null && !twoFactQuestionList.isEmpty()) {
	    List<TwoFactorQuestion> validQuestionList = new ArrayList<TwoFactorQuestion>();
	    for (TwoFactorQuestion ques : twoFactQuestionList) {
		if (isNull(ques.getAnsPos1()) || isNull(ques.getAnsPos2())) {
		    continue;
		}
		validQuestionList.add(ques);
	    }
	    if (validQuestionList == null || validQuestionList.size() == 0) {
		throw new USSDNonBlockingException(USSDExceptions.USSD_TWO_FACT_EXCEP_FAIL.getBmgCode());
	    } else {
		Map<String, Object> txSessionMap = ussdSessionManagement.getTxSessions();
		if (txSessionMap == null) {
		    txSessionMap = new HashMap<String, Object>(5);
		}
		txSessionMap.put(USSDInputParamsEnum.TWO_FACTOR_INIT.getTranId(), validQuestionList.get(USSDUtils
			.generateRandomNumber(validQuestionList.size())));
		ussdSessionManagement.setTxSessions(txSessionMap);
	    }
	}
    }

    private boolean isNull(String str) {
	boolean result = false;
	if (StringUtils.equalsIgnoreCase(str, null) || StringUtils.isEmpty(str)) {
	    result = true;
	}
	return result;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo());

    }
}
