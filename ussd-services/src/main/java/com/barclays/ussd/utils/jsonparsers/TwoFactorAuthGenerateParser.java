package com.barclays.ussd.utils.jsonparsers;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bean.TwoFactorQuestion;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.twofactauth.TwoFactorResponse;

public class TwoFactorAuthGenerateParser implements BmgBaseJsonParser {

    private static final Logger LOGGER = Logger.getLogger(TwoFactorAuthInitParser.class);
    private static final int FIRST_QUESTION = 0;

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDBlockingException, USSDNonBlockingException {
	MenuItemDTO menuDTO = null;

	ObjectMapper mapper = new ObjectMapper();
	try {
	    TwoFactorResponse twoFactorResponse = mapper.readValue(responseBuilderParamsDTO.getJsonString(), TwoFactorResponse.class);
	    if (twoFactorResponse != null) {
		if (twoFactorResponse.getPayHdr() != null
			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(twoFactorResponse.getPayHdr().getResCde())) {

		    menuDTO = renderMenuOnScreen(twoFactorResponse.getPayData().getQuestionWithPositions().get(FIRST_QUESTION),
			    responseBuilderParamsDTO);

		    if (responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions() == null) {
			Map<String, Object> txSessionMap = new HashMap<String, Object>(5);
			responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessionMap);
		    }
		    // set the question list into session
		    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.TWO_FACTOR_GENERATE.getTranId(),
			    twoFactorResponse.getPayData().getQuestionWithPositions().get(FIRST_QUESTION));
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
	return menuDTO;
    }

    private MenuItemDTO renderMenuOnScreen(TwoFactorQuestion twoFactQuestion, ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = null;
	UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
	USSDSessionManagement ussdSessionManagement = responseBuilderParamsDTO.getUssdSessionMgmt();
	if (twoFactQuestion != null) {

	    menuItemDTO = new MenuItemDTO();
	    StringBuilder pageBody = new StringBuilder();
	    if (ussdSessionManagement.getTxSessions() == null) {
		Map<String, Object> txSessions = new HashMap<String, Object>(6);
		txSessions.put(USSDInputParamsEnum.SELFREG_CHALLENGE_ID.getParamName(), twoFactQuestion.getChallengeId());
		ussdSessionManagement.setTxSessions(txSessions);
	    }
	    Object[] messageArgs = { twoFactQuestion.getAnsPos1(), twoFactQuestion.getAnsPos2() };
	    String quest1 = ussdResourceBundle.getLabel(twoFactQuestion.getQuesId(), messageArgs, new Locale(ussdSessionManagement.getUserProfile()
		    .getLanguage(), ussdSessionManagement.getUserProfile().getCountryCode()));

	    pageBody.append(quest1);
	    menuItemDTO.setPageBody(pageBody.toString());

	    // insert the back and home options
	    USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	    menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
	    menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	    menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	    menuItemDTO.setPaginationType(PaginationEnum.SPACED);
	    setNextScreenSequenceNumber(menuItemDTO);
	}
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo());

    }

}
