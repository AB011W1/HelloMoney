package com.barclays.ussd.utils.jsonparsers;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.dao.product.impl.ComponentResDAOImpl;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;

public class REGBGetRefNoJsonParser implements BmgBaseJsonParser {

	@Autowired
	ComponentResDAOImpl componentResDAOImpl;
	private String strWUCFieldChk;

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	return renderMenuOnScreen(responseBuilderParamsDTO);
    }

    /**
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	//REG_BILLER_GET_BILLERS
    	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
    	MenuItemDTO menuItemDTO = new MenuItemDTO();
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);

	UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
	Locale locale = new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode());
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);


	// WUC Change - Water Biller 20/06/2017
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
									USSDConstants.SELECTED_BILLER_REGB),
					ussdSessionMgmt.getBusinessId(), locale.getLanguage()));
		} else {
			String headerID = USSDUtils.getCustomerReferenceId(
					ussdResourceBundle, locale, ussdSessionMgmt
							.getUserTransactionDetails().getUserInputMap().get(
									USSDConstants.SELECTED_BILLER_REGB));
			menuItemDTO.setPageHeader(headerID);
		}
	}
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
    	if(strWUCFieldChk != null && strWUCFieldChk != ""){
	    	if(strWUCFieldChk.equalsIgnoreCase("WUCScreenSeqNo")){
	    		// WUC specific screen sequence number
	    		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TEN.getSequenceNo());
	    	}else if(strWUCFieldChk.equalsIgnoreCase("notWUCScreenSeqNo")){
	    		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIX.getSequenceNo());
	    	}
    	}
    }

}
