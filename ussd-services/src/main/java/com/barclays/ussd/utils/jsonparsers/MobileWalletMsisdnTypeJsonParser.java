package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.SystemParameterServiceResponse;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthUserData;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;
import com.barclays.ussd.utils.jsonparsers.bean.mobilewallettopup.MobileWalletProvider;


public class MobileWalletMsisdnTypeJsonParser implements BmgBaseJsonParser,ScreenSequenceCustomizer {
	@Autowired
	private SystemParameterService systemParameterService;

	private static final String MWALLET_OWN_NUMBER = "label.mwallet.own.number";
	private static final String MWALLET_OTHER_NUMBER = "label.mwallet.other.number";
	private static final String MWALLET_GROUP_WALLET = "label.gpmwallet";
	private static final String MOBILEWALLET_OTH_NUM_SAVDBNF = "label.other.number.savdbnf";
	private static final String MOBILEWALLET_OTH_NUM_NEWBNF = "label.other.number.newbnf";
	private static final String MOBILEWALLET_OTH_NUM_EDITBNF = "label.other.number.editbnf";
	private static final String MOBILEWALLET_OTH_NUM_DELBNF = "label.other.number.delbnf";

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
		//if(!(billerId.equalsIgnoreCase("MTNMM-4")) && !(billerId.equalsIgnoreCase("MTNZMBANKTOWALLET-2"))){
			//Temporarily added as we have to show Own Number menu only for Mobile Money- MTN end

			pageBody.append("2");
			pageBody.append(USSDConstants.DOT_SEPERATOR);
			pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle().getLabel(MWALLET_OTHER_NUMBER,
					new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode())));
			//Temporarily added as we have to show Own Number menu only for Mobile Money- MTN start
		
		//Temporarily added as we have to show Own Number menu only for Mobile Money- MTN end
		USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		List<String> optionList=new ArrayList<String>();
		optionList.add("1");
		//Temporarily added as we have to show Own Number menu only for Mobile Money- MTN start
		//Code commented to include Other Number MTN for ZM and UG
		//if(!(billerId.equalsIgnoreCase("MTNMM-4")) && !(billerId.equalsIgnoreCase("MTNZMBANKTOWALLET-2"))){
			//Temporarily added as we have to show Own Number menu only for Mobile Money- MTN end
		optionList.add("2");
		//}

		// Groupwallet Start
		String business_id=ussdSessionMgmt.getBusinessId();
		SystemParameterServiceRequest request=new SystemParameterServiceRequest();
		SystemParameterDTO dto=new SystemParameterDTO();
		dto.setBusinessId(business_id);
		dto.setActivityId("COMMON");
		dto.setParameterId("groupWalletEnabled");
		dto.setSystemId("UB");
		request.setSystemParameterDTO(dto);
		SystemParameterServiceResponse response= systemParameterService.getSystemParameter(request);
		String enabledStatus = "N";
		if(response.getSystemParameterDTO()!=null)
			enabledStatus=response.getSystemParameterDTO().getParameterValue();

		AuthUserData adata=(AuthUserData)ussdSessionMgmt.getUserAuthObj();
		String groupWalletIndicator = "N";
		List<CustomerMobileRegAcct> accList=adata.getPayData().getCustActs();
		for(CustomerMobileRegAcct acc:accList)
			if(acc.getGroupWalletIndicator()!=null && acc.getGroupWalletIndicator().equals("Y")){
				groupWalletIndicator="Y";
				break;
			}
		if(business_id!=null && business_id.equals("TZNBC")
				&& groupWalletIndicator.equals("Y")
				&& enabledStatus!=null
				&& enabledStatus.equals("Y")){
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append("3");
			pageBody.append(USSDConstants.DOT_SEPERATOR);
			pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle().getLabel(MWALLET_GROUP_WALLET,
					new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode())));
			optionList.add("3");
		}
		// Groupwallet End
		
		//Ghana Menu Optimization- to include Bene management along with own and other number
		if(business_id!=null && business_id.equalsIgnoreCase("GHBRB")) {
			//saved bene
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append("3");
			pageBody.append(USSDConstants.DOT_SEPERATOR);
			pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle().getLabel(MOBILEWALLET_OTH_NUM_SAVDBNF,
					new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode())));
			optionList.add("3");
			//new bene
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append("4");
			pageBody.append(USSDConstants.DOT_SEPERATOR);
			pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle().getLabel(MOBILEWALLET_OTH_NUM_NEWBNF,
					new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode())));
			optionList.add("4");
			//edit bene
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append("5");
			pageBody.append(USSDConstants.DOT_SEPERATOR);
			pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle().getLabel(MOBILEWALLET_OTH_NUM_EDITBNF,
					new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode())));	
			optionList.add("5");
			//delete bene
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append("6");
			pageBody.append(USSDConstants.DOT_SEPERATOR);
			pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle().getLabel(MOBILEWALLET_OTH_NUM_DELBNF,
					new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode())));						
			optionList.add("6");
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
		SystemParameterDTO systemParameterDTO = new SystemParameterDTO();
		SystemParameterServiceRequest systemParameterServiceRequest = new SystemParameterServiceRequest();
		systemParameterServiceRequest.setSystemParameterDTO(systemParameterDTO);
		systemParameterDTO.setBusinessId(BMBContextHolder.getContext().getBusinessId().toString());
		systemParameterDTO.setSystemId("UB");
		systemParameterDTO.setParameterId("isGHIPS2Flag");
		String isGHIPS2Flag="";
		SystemParameterServiceResponse response = systemParameterService.getStatusParameter(systemParameterServiceRequest);
		if(response!=null && response.getSystemParameterDTO()!=null && response.getSystemParameterDTO().getParameterValue()!=null)
			isGHIPS2Flag = response.getSystemParameterDTO().getParameterValue();

		//TZNBC Menu Optimization
		//Ghana Menu Optimization - other number one off flow
		if ((ussdSessionMgmt.getBusinessId().equalsIgnoreCase("GHBRB") ||ussdSessionMgmt.getBusinessId().equalsIgnoreCase("TZNBC")) && userInput.equals("2"))
			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo();

		if ((USSDConstants.BUSINESS_ID_GHBRB.equalsIgnoreCase(ussdSessionMgmt.getBusinessId())) && userInput.equals("1") && ("Y").equals(isGHIPS2Flag))
		{
			Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
			userInputMap.put(USSDInputParamsEnum.MOBILE_WALLET_ACCOUNT_NUMBER.getParamName(),ussdSessionMgmt.getMsisdnNumber());
			userInputMap.put(BillPaymentConstants.MWALLET_WON_NUMBER,BillPaymentConstants.MWALLET_WON_NUMBER);//CR82
			ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOURTYTHREE.getSequenceNo();

		}
		else if (userInput.equals("1")) {
			Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
			userInputMap.put(USSDInputParamsEnum.MOBILE_WALLET_ACCOUNT_NUMBER.getParamName(),ussdSessionMgmt.getMsisdnNumber());
			userInputMap.put(BillPaymentConstants.MWALLET_WON_NUMBER,BillPaymentConstants.MWALLET_WON_NUMBER);//CR82
			ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo();

		} else if (userInput.equals("3") && !ussdSessionMgmt.getBusinessId().equalsIgnoreCase("GHBRB")){
			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWENTYFIVE.getSequenceNo();
		}
		
		//Ghana Menu Optimization -bene management flow
    	if (USSDConstants.BUSINESS_ID_GHBRB.equalsIgnoreCase(ussdSessionMgmt.getBusinessId())){
    		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
    		//saved benefiicary
    		if (userInput.equals("3")) {
    			userInputMap.put(USSDInputParamsEnum.MOBILE_WALLET_ACCOUNT_NUMBER.getParamName(),ussdSessionMgmt.getMsisdnNumber());
    			userInputMap.put(BillPaymentConstants.AT_MW_SAVED_BENEF,BillPaymentConstants.AT_MW_SAVED_BENEF);//CR82
    			ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
    			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THIRTEEN.getSequenceNo();
    		}
    		//new beneficiary
    		if (userInput.equals("4")) {
    			userInputMap.put(USSDInputParamsEnum.MOBILE_WALLET_ACCOUNT_NUMBER.getParamName(),ussdSessionMgmt.getMsisdnNumber());
    			ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
    			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIFTEEN.getSequenceNo();
    		}
    		//Edit Beneficiary
    		if (userInput.equals("5")) {
    			userInputMap.put(USSDInputParamsEnum.MOBILE_WALLET_ACCOUNT_NUMBER.getParamName(),ussdSessionMgmt.getMsisdnNumber());
    			ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
    			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THIRTEEN.getSequenceNo();
    		}
    		//DeleteBeneficary
    		if (userInput.equals("6")) {
    			userInputMap.put(USSDInputParamsEnum.MOBILE_WALLET_ACCOUNT_NUMBER.getParamName(),ussdSessionMgmt.getMsisdnNumber());
    			ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
    			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THIRTEEN.getSequenceNo();
    		}  			
    	}
		return seqNo;
	}
}