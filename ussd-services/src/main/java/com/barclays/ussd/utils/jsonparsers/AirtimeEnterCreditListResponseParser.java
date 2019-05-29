/**
 *
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthUserData;
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthenticateUserPayData;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

/**
 * @author BTCI
 *
 */
public class AirtimeEnterCreditListResponseParser implements BmgBaseJsonParser {
	//private static final String TRANSACTION_AIRTIME_CREDITCARD_LABEL = "label.airtime.select.creditcard";
	   /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(AirtimeEnterCreditListResponseParser.class);

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
    	MenuItemDTO menuDTO = null;
    	ObjectMapper mapper = new ObjectMapper();

    	try{
    		 AuthUserData creditCardListObj = mapper.readValue(responseBuilderParamsDTO.getJsonString(), AuthUserData.class);
    		 if (creditCardListObj != null) {
    				if (creditCardListObj.getPayHdr() != null
    					&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(creditCardListObj.getPayHdr().getResCde())) {
    				    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, creditCardListObj);
    				} else if (creditCardListObj.getPayHdr() != null) {
    				    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
    				    throw new USSDNonBlockingException(creditCardListObj.getPayHdr().getResCde());
    				} else {
    				    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
    				    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
    				}
    			    } else {
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
return  menuDTO ;


    }

    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO,AuthUserData userAuthObj) throws USSDNonBlockingException {
    	MenuItemDTO menuItemDTO = null;
	AuthenticateUserPayData acntPayData = userAuthObj.getPayData();
	if(acntPayData!= null)
	{
		 if (acntPayData.getCustActs() != null && !acntPayData.getCustActs().isEmpty()) {
			 menuItemDTO = new MenuItemDTO();
				int index = 1;
				StringBuilder pageBody = new StringBuilder();
				Map<String, Object> txSessions =responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
				txSessions.put(USSDInputParamsEnum.AIRTIME_CARD_LIST.getTranId(), acntPayData.getCustActs());
				responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);

				USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
				Map<String, String> userInputMap = responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
				String currency= ((AuthUserData)(responseBuilderParamsDTO.getUssdSessionMgmt().getUserAuthObj())).getPayData().getCustActs().get(0).getCurr();
				String paramArray[]={userInputMap.get("txnAmt"),currency};
				String language = ussdSessionMgmt.getUserProfile().getLanguage();
				String countryCode = ussdSessionMgmt.getUserProfile().getCountryCode();
				Locale locale = new Locale(language, countryCode);
				String TRANSACTION_AIRTIME_CREDITCARD_LABEL = responseBuilderParamsDTO.getUssdResourceBundle()
					.getLabel("label.airtime.select.creditcard", paramArray, locale);
				pageBody.append(TRANSACTION_AIRTIME_CREDITCARD_LABEL);
				for (CustomerMobileRegAcct accountDetail : acntPayData.getCustActs()) {
				    pageBody.append(USSDConstants.NEW_LINE);
				    pageBody.append(index);
				    pageBody.append(USSDConstants.DOT_SEPERATOR);
				    pageBody.append(accountDetail.getMkdCrdNo());
				    index++;
				}
				menuItemDTO.setPageBody(pageBody.toString());
				USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
				// menuItemDTO.setPageFooter(warningMsg);
				menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
				menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
				menuItemDTO.setPaginationType(PaginationEnum.LISTED);
			    } else {
				throw new USSDNonBlockingException(USSDExceptions.USSD_NO_CREDIT_CARD_FOUND.getBmgCode());
			    }
		 }
	if(null != menuItemDTO)
		setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
	}

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWENTYFOUR.getSequenceNo());
    }

}
