/**
 * AccountSummaryJSONParser.java
 */
package com.barclays.ussd.bmg.login;

import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.bean.UserProfile;
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
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;

/**
 * @author BTCI
 *
 */
public class ForgotPinInstructionJsonParser implements BmgBaseJsonParser,ScreenSequenceCustomizer {
	private static final String LABEL_FORGOT_PIN_INSTRUCTION="label.forgot.pin.instruction";

	@Resource(name = "branchCodeCountryList")
    private List<String> branchCodeCountryList;
    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(ForgotPinInstructionJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
    	return renderMenuOnScreen(responseBuilderParamsDTO);
        }

        private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
    	MenuItemDTO menuItemDTO = new MenuItemDTO();
    	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	    String forgotPinInstLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(LABEL_FORGOT_PIN_INSTRUCTION,
				new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));

	    StringBuilder pageBody = new StringBuilder();
	    pageBody.append(forgotPinInstLabel);
	    menuItemDTO.setPageBody(pageBody.toString());
    	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
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
    @Override
    public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
    String businessId = ussdSessionMgmt.getUserProfile().getBusinessId();
	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo();
	//CR#47
	if (!branchCodeCountryList.contains(businessId)) {
	    seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo();
	}
	return seqNo;
    }
}
