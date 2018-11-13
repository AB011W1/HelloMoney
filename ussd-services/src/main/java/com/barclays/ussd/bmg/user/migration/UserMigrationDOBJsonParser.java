package com.barclays.ussd.bmg.user.migration;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
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
import com.barclays.ussd.utils.jsonparsers.bean.airtime.SelfRegistrationChallengeQnResponse;
import com.barclays.ussd.utils.jsonparsers.bean.login.SelfRegistrationChallengeQnPayData;
import com.barclays.ussd.utils.jsonparsers.bean.login.SelfRegistrationQuestion;

public class UserMigrationDOBJsonParser implements BmgBaseJsonParser {
    private static final int FIRST_QUESTION = 0;
    private static final Logger LOGGER = Logger.getLogger(UserMigrationDOBJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDBlockingException {

	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();

	try {
	    SelfRegistrationChallengeQnResponse selfRegInitResponse = mapper.readValue(responseBuilderParamsDTO.getJsonString(),
		    SelfRegistrationChallengeQnResponse.class);
	    if (selfRegInitResponse != null) {
		if (selfRegInitResponse.getPayHdr() != null
			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(selfRegInitResponse.getPayHdr().getResCde())) {
		    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, selfRegInitResponse.getPayData());
		    if (responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions() == null) {
			Map<String, Object> txSessionMap = new HashMap<String, Object>(5);
			responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessionMap);
		    }
		} else if (selfRegInitResponse.getPayHdr() != null
			&& (StringUtils.equalsIgnoreCase(USSDExceptions.BMB90004.getBmgCode(), selfRegInitResponse.getPayHdr().getResCde()))) {
		    throw new USSDBlockingException(USSDExceptions.BMB90004.getBmgCode());
		} else if (selfRegInitResponse.getPayHdr() != null
			&& (StringUtils.equalsIgnoreCase(USSDExceptions.USSD_TECH_ISSUE.getBmgCode(), selfRegInitResponse.getPayHdr().getResCde()))) {
		    throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		} else if (selfRegInitResponse.getPayHdr() != null
			&& (StringUtils.equalsIgnoreCase(USSDExceptions.BEM06001.getBmgCode(), selfRegInitResponse.getPayHdr().getResCde()))) {
		    throw new USSDBlockingException(USSDExceptions.BEM06001.getBmgCode());
		} else if (selfRegInitResponse.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(selfRegInitResponse.getPayHdr().getResCde());
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
	    throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	}
	return menuDTO;
    }

    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, SelfRegistrationChallengeQnPayData selfRegInitData) {
	MenuItemDTO menuItemDTO = null;
	UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
	USSDSessionManagement ussdSessionManagement = responseBuilderParamsDTO.getUssdSessionMgmt();
	if (selfRegInitData != null) {
	    List<SelfRegistrationQuestion> questionsList = selfRegInitData.getQuestionWithPositions();
	    if (questionsList != null && !questionsList.isEmpty()) {

		menuItemDTO = new MenuItemDTO();
		StringBuilder pageBody = new StringBuilder();
		SelfRegistrationQuestion question = questionsList.get(FIRST_QUESTION);
		Map<String, Object> txSessions = ussdSessionManagement.getTxSessions();
		if (txSessions == null) {
		    txSessions = new HashMap<String, Object>(6);
		}
		txSessions.put(USSDInputParamsEnum.USER_MIGRATION_CHALLENGE_ID.getParamName(), question.getChallengeId());
		ussdSessionManagement.setTxSessions(txSessions);

		Object[] messageArgs = { question.getAnsPos1(), question.getAnsPos2() };
		String quest1 = ussdResourceBundle.getLabel(question.getQuesId(), messageArgs, new Locale(ussdSessionManagement.getUserProfile()
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
	}
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIX.getSequenceNo());
    }
}