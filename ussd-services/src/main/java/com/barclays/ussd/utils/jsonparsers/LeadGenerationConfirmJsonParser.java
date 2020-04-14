/**
 * AccountSummaryJSONParser.java
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.bean.UserProfile;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;

import oracle.net.aso.l;

/**
 * @author BTCI
 *
 */
public class LeadGenerationConfirmJsonParser implements BmgBaseJsonParser {

	private static final String LEAD_GEN_CONFIRM_LABEL = "label.lead.generation.link.confirm";

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(LeadGenerationConfirmJsonParser.class);

	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
		return renderMenuOnScreen(responseBuilderParamsDTO);
	}

	private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
		UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
		UserProfile userProfile = ussdSessionMgmt.getUserProfile();
		Locale locale = new Locale(userProfile.getLanguage(), userProfile.getCountryCode());
		Map<String, String> userInputMap = responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
		//TZNBC Menu Optimization
		String productName="";
		String subProductName="";
		String loanSubProduct="";
		String language= responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getLanguage();
		productName=userInputMap.get(USSDConstants.LEAD_GEN_PRODUCT_NAME);
		subProductName=userInputMap.get(USSDConstants.LEAD_GEN_SUB_PRODUCT_NAME);
		List<String> loanList= (List<String>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.LEAD_GENERATION_LOANS.getTranId());
		if(null!=loanList) {
			loanSubProduct = loanList.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.LEAD_GENERATION_LOANS.getParamName())) - 1);		
			if(null!=loanSubProduct)
				if(null!=language && language.equalsIgnoreCase("EN"))
					productName="Loans";
				else
					productName="Mikopo";
				subProductName=loanSubProduct;
		}
		if(subProductName !=null && !subProductName.equals("")){
			productName=productName+"-"+subProductName;
		}
		StringBuilder pageBody = new StringBuilder();
		pageBody.append(ussdResourceBundle.getLabel(LEAD_GEN_CONFIRM_LABEL , locale)+ " "+productName);
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
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo());

	}
}
