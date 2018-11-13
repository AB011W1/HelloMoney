package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
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
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.Account;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.AirtimeValidatePayData;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.AirtimeValidateResponse;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;
@SuppressWarnings("unchecked")
public class KitsRegisterConfirmDetailsJsonParser implements BmgBaseJsonParser {

    private static final String AMOUNT_LABEL = "label.att.amount";
    private static final String CURRENCY = "label.currency";
    private static final String TRANSACTION_AMT_LIMIT_ERROR = "BMB90011";
    private static final Logger LOGGER = Logger.getLogger(KitsRegisterConfirmDetailsJsonParser.class);

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO);

	return menuDTO;
    }


    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {

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
	    String primaryAccountLabel = ussdResourceBundle.getLabel(USSDConstants.PRIMARYACCOUNT, locale);
        String phoneNum=null;
        String accNum=null;
        String primaryAcc=null;

        List<CustomerMobileRegAcct> accList=new ArrayList<CustomerMobileRegAcct>();
        accList= (List<CustomerMobileRegAcct>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.KITS_REG_ACCOUNT_NUM.getTranId());
        int selectedAccSeq=Integer.parseInt(userInputMap.get(USSDInputParamsEnum.KITS_REG_ACCOUNT_NUM.getParamName()))-1;
        CustomerMobileRegAcct acc=accList.get(selectedAccSeq);

        pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(accNumLabel);
	    accNum = acc.getMkdActNo();
        pageBody.append(accNum);
        pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(phoneNumLabel);
	    phoneNum =ussdSessionMgmt.getMsisdnNumber();
	    pageBody.append(phoneNum);
	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(primaryAccountLabel);
	    primaryAcc=userInputMap.get(USSDInputParamsEnum.KITS_REG_PRIMARY_ACC.getParamName());
	    if(primaryAcc.equalsIgnoreCase("1"))
	    pageBody.append(getLabel(responseBuilderParamsDTO,"label.primary.account.yes"));
	    else if(primaryAcc.equalsIgnoreCase("2"))
	    pageBody.append(getLabel(responseBuilderParamsDTO,"label.primary.account.no"));


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
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo());
    }

    private String getLabel(ResponseBuilderParamsDTO responseBuilderParamsDTO, String label) {
		String labelValue = null;
		USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
		UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
		Locale locale = new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode());
		labelValue = ussdResourceBundle.getLabel(label, locale);
		return labelValue;
	    }
}
