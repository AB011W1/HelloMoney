package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
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
import com.barclays.ussd.utils.jsonparsers.bean.airtime.Account;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.AirtimeValidatePayData;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.AirtimeValidateResponse;
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthUserData;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.SearchIndividualCustInformationResponse;
@SuppressWarnings("unchecked")
public class KitsDeregisterConfirmDetailsJsonParser implements BmgBaseJsonParser {

    private static final String AMOUNT_LABEL = "label.att.amount";
    private static final String CURRENCY = "label.currency";
    private static final String TRANSACTION_AMT_LIMIT_ERROR = "BMB90011";
    private static final Logger LOGGER = Logger.getLogger(KitsDeregisterConfirmDetailsJsonParser.class);

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
    	MenuItemDTO menuDTO = null;
    	ObjectMapper mapper = new ObjectMapper();

    	try{

    			SearchIndividualCustInformationResponse searchIndividualCustInformationResponse = mapper.readValue(responseBuilderParamsDTO.getJsonString(),
    					SearchIndividualCustInformationResponse.class);
    			String accountNumber = null;
 	    	    String primaryFlag = null;
    			if (searchIndividualCustInformationResponse != null) {
    				if (searchIndividualCustInformationResponse.getPayHdr() != null
    					&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(searchIndividualCustInformationResponse.getPayHdr().getResCde())) {

    					if (searchIndividualCustInformationResponse.getPayData() != null )
    						{accountNumber = searchIndividualCustInformationResponse.getPayData().getCustomerAccountNumber();
    						primaryFlag = searchIndividualCustInformationResponse.getPayData().getPrimaryFlag();
    		    		    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, accountNumber, primaryFlag);
    						}

    				}else if (searchIndividualCustInformationResponse.getPayHdr() != null
    						&& (StringUtils.equalsIgnoreCase(USSDExceptions.USSD_TECH_ISSUE.getBmgCode(), searchIndividualCustInformationResponse.getPayHdr().getResCde()))) {
    				    throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
    				}else if(searchIndividualCustInformationResponse.getPayHdr() != null
    						&& USSDExceptions.BEMDEREG.getBmgCode().equalsIgnoreCase(searchIndividualCustInformationResponse.getPayHdr().getResCde())){
    					throw new USSDBlockingException(USSDExceptions.BEMDEREG.getBmgCode());
    				}
    				else if (searchIndividualCustInformationResponse.getPayHdr() != null) {
    				    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
    				    throw new USSDNonBlockingException(searchIndividualCustInformationResponse.getPayHdr().getResCde());
    				}
    				}else {
    					LOGGER.error("Invalid response got from the BMG " + responseBuilderParamsDTO.getBmgOpCode());
    					throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
    				    }

    	}catch(Exception e)
    	{
    		LOGGER.error("Exception : ", e);
    	    if (e instanceof USSDNonBlockingException) {
    		throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
    	    } else {
    		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
    	    }
    	}

    	return menuDTO;
        }


    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, String accountNumber, String primaryFlag) {

	    MenuItemDTO menuItemDTO = null;

	    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();

	    Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	    menuItemDTO = new MenuItemDTO();
	    StringBuilder pageBody = new StringBuilder();
	    UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
	    String language = ussdSessionMgmt.getUserProfile().getLanguage();
	    String countryCode = ussdSessionMgmt.getUserProfile().getCountryCode();
	    Locale locale = new Locale(language, countryCode);
	    String confirmLabel = ussdResourceBundle.getLabel(USSDConstants.LBL_CONFIRM, locale);
	    String phoneNumLabel = ussdResourceBundle.getLabel(USSDConstants.PHONENUMBER, locale);
	    String accNumLabel = ussdResourceBundle.getLabel(USSDConstants.ACCNUMBER, locale);
	    String primaryFalgLabel="Primary Flag:";
        String phoneNum=null;

        Map<String, Object> txSessions = new HashMap<String, Object>();
        txSessions.put("accountNumber",(String)accountNumber);
        txSessions.put("primaryFlag",(String)primaryFlag);
        ussdSessionMgmt.setTxSessions(txSessions);

        pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(accNumLabel);
        pageBody.append(accountNumber);
        pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(primaryFalgLabel);
        pageBody.append(primaryFlag);
        pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(phoneNumLabel);
	    phoneNum =ussdSessionMgmt.getMsisdnNumber();
	    pageBody.append(phoneNum);

	    if (!responseBuilderParamsDTO.isErrorneousPage()) {
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(confirmLabel);
	    }

	    USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	    menuItemDTO.setPageBody(pageBody.toString());
	    menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
	    menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	    menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	    menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	    setNextScreenSequenceNumber(menuItemDTO);

	    return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());
    }
}
