package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bean.MsisdnDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.SystemPreferenceValidator;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdMenuBuilder;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Beneficiery;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.MobileWalletProvider;


public class MobileWalletMsisdnTypeJsonParser implements BmgBaseJsonParser,ScreenSequenceCustomizer {
    @Autowired
    UssdMenuBuilder ussdMenuBuilder;
    private static final String MWALLET_OWN_NUMBER = "label.mwallet.own.number";
    private static final String MWALLET_OTHER_NUMBER = "label.mwallet.other.number";
    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	return renderMenuOnScreen(responseBuilderParamsDTO);
    }

    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	StringBuilder pageBody = new StringBuilder();
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();


	pageBody.append(USSDConstants.NEW_LINE);
	pageBody.append("1");
	pageBody.append(USSDConstants.DOT_SEPERATOR);
	pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle().getLabel(MWALLET_OWN_NUMBER,
			new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode())));
	Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
	//Temporarily added as we have to show Own Number menu only for Mobile Money- MTN start

	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	List<MobileWalletProvider> mobileWalletProvider = (List<MobileWalletProvider>) txSessions.get(USSDInputParamsEnum.MOBILE_WALLET_MNOS_LST
			.getTranId());

		String selectedMobileWalletProvider = userInputMap.get(USSDInputParamsEnum.MOBILE_WALLET_MNOS_LST.getParamName());
		MobileWalletProvider mobileWalletProviderSelected = mobileWalletProvider.get(Integer.parseInt(selectedMobileWalletProvider) - 1);
		String billerId=mobileWalletProviderSelected.getBillerId();
		pageBody.append(USSDConstants.NEW_LINE);
    	if(!(billerId.equalsIgnoreCase("MTNMM-4")) && !(billerId.equalsIgnoreCase("MTNZMBANKTOWALLET-2"))){
    		//Temporarily added as we have to show Own Number menu only for Mobile Money- MTN end

	pageBody.append("2");
	pageBody.append(USSDConstants.DOT_SEPERATOR);
	pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle().getLabel(MWALLET_OTHER_NUMBER,
			new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode())));
	//Temporarily added as we have to show Own Number menu only for Mobile Money- MTN start
    	}
    	//Temporarily added as we have to show Own Number menu only for Mobile Money- MTN end
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	List<String> optionList=new ArrayList<String>();
	optionList.add("1");
	//Temporarily added as we have to show Own Number menu only for Mobile Money- MTN start
	if(!(billerId.equalsIgnoreCase("MTNMM-4")) && !(billerId.equalsIgnoreCase("MTNZMBANKTOWALLET-2"))){
		//Temporarily added as we have to show Own Number menu only for Mobile Money- MTN end
	optionList.add("2");
	}
	txSessions.put(USSDInputParamsEnum.MOBILE_WALLET_MSISDN_TYPE.getTranId(),optionList);
	menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	menuItemDTO.setPageBody(pageBody.toString());
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_NINE.getSequenceNo());

    }

    public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
    	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_NINE.getSequenceNo();

       	if (userInput.equals("1")) {
       		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
       		userInputMap.put(USSDInputParamsEnum.MOBILE_WALLET_ACCOUNT_NUMBER.getParamName(),ussdSessionMgmt.getMsisdnNumber());
       		userInputMap.put(BillPaymentConstants.MWALLET_WON_NUMBER,BillPaymentConstants.MWALLET_WON_NUMBER);//CR82
       		ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
       		seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo();

    	}
    	return seqNo;
        }


}
