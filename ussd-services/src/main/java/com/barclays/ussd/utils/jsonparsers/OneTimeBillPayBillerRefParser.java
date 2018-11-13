/**
 *
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.dao.product.impl.ComponentResDAOImpl;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.BillersListDO;
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

/**
 * @author BTCI
 *
 */
public class OneTimeBillPayBillerRefParser implements BmgBaseJsonParser {

	@Autowired
	ComponentResDAOImpl componentResDAOImpl;
	private String strWUCFieldChk;

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	return renderMenuOnScreen(responseBuilderParamsDTO);
    }

    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
    	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
     	MenuItemDTO menuItemDTO = new MenuItemDTO();
     	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
     	UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
		Locale locale = new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode());

		// WUC Change - Water Biller 19/06/2017
		String wucBillerCategory = (String)ussdSessionMgmt.getTxSessions().get("wucBillerCategory");
		if(wucBillerCategory != null && wucBillerCategory !="" ){
			if(wucBillerCategory.equalsIgnoreCase("WUC-2") && ussdSessionMgmt.getBusinessId().equalsIgnoreCase("BWBRB")){
				strWUCFieldChk = "WUCScreenSeqNo";
				menuItemDTO.setPageHeader("LBL9999");
				menuItemDTO.setPageBody(responseBuilderParamsDTO.getUssdResourceBundle().getLabel("label.enter.wuccustomernum", locale));
			}
		}else{
			strWUCFieldChk = "notWUCScreenSeqNo";
			String ubpBusinessId = componentResDAOImpl.getUBPBusinessId();
			if (ubpBusinessId.contains(ussdSessionMgmt.getBusinessId())) {
				menuItemDTO.setPageHeader("LBL9999");
				menuItemDTO.setPageBody(componentResDAOImpl.getBillerLabelByKey(
						ussdSessionMgmt.getUserTransactionDetails()
								.getUserInputMap().get(
										USSDConstants.SELECTED_BILLER_OTBP),
						ussdSessionMgmt.getBusinessId(), locale.getLanguage()));
			} else {
				String headerID = USSDUtils.getCustomerReferenceId(
						ussdResourceBundle, locale, ussdSessionMgmt
								.getUserTransactionDetails().getUserInputMap().get(
										USSDConstants.SELECTED_BILLER_OTBP));
				// CR-57
				if (ussdSessionMgmt.getBusinessId().equalsIgnoreCase(
						USSDConstants.BUSINESS_ID_ZWBRB)
						&& ussdSessionMgmt.getUserTransactionDetails()
								.getUserInputMap().get(
										USSDConstants.SELECTED_BILLER_OTBP)
								.equalsIgnoreCase("DSTVZIM-2")) {
					headerID = headerID + "OTP";
				}

				menuItemDTO.setPageHeader(headerID);
			}
		}
	menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.NOT_REQD);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
    	if(strWUCFieldChk != null && strWUCFieldChk != ""){
	    	if(strWUCFieldChk.equalsIgnoreCase("WUCScreenSeqNo")){
	    		// WUC specific screen sequence number
	    		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SEVENTEEN.getSequenceNo());
	    	}else if(strWUCFieldChk.equalsIgnoreCase("notWUCScreenSeqNo")){
	    		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIX.getSequenceNo());
	    	}
    	}
    }

}
