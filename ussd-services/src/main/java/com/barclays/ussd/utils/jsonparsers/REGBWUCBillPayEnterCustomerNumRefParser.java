/**
 *
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.Locale;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

/**
 * @author BTCI
 *
 */
public class REGBWUCBillPayEnterCustomerNumRefParser implements BmgBaseJsonParser {


    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	return renderMenuOnScreen(responseBuilderParamsDTO);
    }

    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
		USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	 	MenuItemDTO menuItemDTO = new MenuItemDTO();
	 	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		Locale locale = new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode());

		menuItemDTO.setPageHeader("LBL9999");
		menuItemDTO.setPageBody(responseBuilderParamsDTO.getUssdResourceBundle().getLabel("label.enter.wuccontractnum", locale));
		menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
		menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
		menuItemDTO.setPaginationType(PaginationEnum.NOT_REQD);
		setNextScreenSequenceNumber(menuItemDTO);
		return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	    menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIX.getSequenceNo());
    }

}
