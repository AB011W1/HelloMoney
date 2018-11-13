/**
 * AccountSummaryJSONParser.java
 */
package com.barclays.ussd.bmg.creditcard.link;

import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.bean.UserProfile;
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
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

/**
 * @author BTCI
 *
 */
public class CreditCardLinkConfirmJsonParser implements BmgBaseJsonParser {

	private static final String CONFIRM_LABEL = "label.credit.card.link.confirm";
    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(CreditCardLinkConfirmJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
    	return renderMenuOnScreen(responseBuilderParamsDTO);
        }

        private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
    	MenuItemDTO menuItemDTO = new MenuItemDTO();
    	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	    UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
	    UserProfile userProfile = ussdSessionMgmt.getUserProfile();
	    Locale locale = new Locale(userProfile.getLanguage(), userProfile.getCountryCode());
	    String confirmLabel = ussdResourceBundle.getLabel(CONFIRM_LABEL, locale);
	    StringBuilder pageBody = new StringBuilder();
	    pageBody.append(confirmLabel);
	    menuItemDTO.setPageBody(pageBody.toString());
    	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
    	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
    	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
    	menuItemDTO.setPaginationType(PaginationEnum.NOT_REQD);
    	setNextScreenSequenceNumber(menuItemDTO);
    	return menuItemDTO;
        }


    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());

    }
}
