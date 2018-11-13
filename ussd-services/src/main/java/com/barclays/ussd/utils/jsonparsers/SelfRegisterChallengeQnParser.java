/**
 *
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

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
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.SelfRegistrationChallengeQnResponse;
import com.barclays.ussd.utils.jsonparsers.bean.login.SelfRegistrationChallengeQnPayData;
import com.barclays.ussd.utils.jsonparsers.bean.login.SelfRegistrationQuestion;

/**
 * @author BTCI
 *
 */
public class SelfRegisterChallengeQnParser implements BmgBaseJsonParser, ScreenSequenceCustomizer {
    private static final int FIRST_QUESTION = 0;
    private static final Logger LOGGER = Logger.getLogger(SelfRegisterChallengeQnParser.class);
    private static final String DATE_OF_BIRTH = "Q5";

    @Resource(name = "branchCodeCountryList")
    private List<String> branchCodeCountryList;

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

    /**
     * @param responseBuilderParamsDTO
     * @param selfRegInitData
     * @param warningMsg
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, SelfRegistrationChallengeQnPayData selfRegInitData) throws USSDNonBlockingException {
	MenuItemDTO menuItemDTO = null;
	UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
	USSDSessionManagement ussdSessionManagement = responseBuilderParamsDTO.getUssdSessionMgmt();

	if (selfRegInitData != null) {
	    List<SelfRegistrationQuestion> questionsList = selfRegInitData.getQuestionWithPositions();
	    if (questionsList != null && !questionsList.isEmpty()) {

		menuItemDTO = new MenuItemDTO();
		StringBuilder pageBody = new StringBuilder();
		SelfRegistrationQuestion question = new SelfRegistrationQuestion();
		Map<String, Object> txSessions = ussdSessionManagement.getTxSessions();
		if (txSessions == null) {
		    txSessions = new HashMap<String, Object>(6);
		}

		if(USSDConstants.BUSINESS_ID_KEBRB.equals(ussdSessionManagement.getBusinessId())){
		    List<SelfRegistrationQuestion> validQuestionList = new ArrayList<SelfRegistrationQuestion>();
					for (SelfRegistrationQuestion ques : questionsList) {
						if (isNull(ques.getAnsPos1())
								|| isNull(ques.getAnsPos2())) {
							continue;
						}
						validQuestionList.add(ques);
					}

		    if (!validQuestionList.isEmpty()
							&& validQuestionList.size() > 1) {
						for (SelfRegistrationQuestion ques : validQuestionList) {
							if (StringUtils.equalsIgnoreCase(ques.getQuesId(),
									DATE_OF_BIRTH)) {
								validQuestionList.remove(ques);
							}
						}
					}
		    question = validQuestionList.get(USSDUtils
					.generateRandomNumber(validQuestionList.size()));
		    //Forgot Pin change
			txSessions.put(USSDInputParamsEnum.SELFREG_GET_QUESTION.getTranId(), question);
		    //txSessions.put(responseBuilderParamsDTO.getTranDataId(), question);
		}else{
			question = questionsList.get(FIRST_QUESTION);
		}

		txSessions.put(USSDInputParamsEnum.SELFREG_CHALLENGE_ID.getParamName(), question.getChallengeId());
		ussdSessionManagement.setTxSessions(txSessions);

		Object[] messageArgs = { appendSuffix(question.getAnsPos1()), appendSuffix(question.getAnsPos2()) };
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

    public String appendSuffix(String tn){
    	int no=Integer.parseInt(tn);
    	String[]suffix={"st","nd","rd","th"};
    	String finalString= "";
    	switch(no){
		case 1:finalString+=no+suffix[0];break;
		case 2:finalString+=no+suffix[1];break;
		case 3:finalString+=no+suffix[2];break;
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
			finalString+=no+suffix[3];break;
		default :
			finalString+=no+suffix[3];break;
		}
    	return finalString;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SEVEN.getSequenceNo());
    }

    @Override
    public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_SEVEN.getSequenceNo();
	String tranDataId=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
	if(tranDataId !=null && tranDataId.equalsIgnoreCase("ussd3.00")){
		seqNo=USSDSequenceNumberEnum.SEQUENCE_NUMBER_EIGHT.getSequenceNo();
	}
	return seqNo;
    }
    private boolean isNull(String str) {
    	boolean result = false;
    	if (StringUtils.equalsIgnoreCase(str, null) || StringUtils.isEmpty(str)) {
    	    result = true;
    	}
    	return result;
        }
}
