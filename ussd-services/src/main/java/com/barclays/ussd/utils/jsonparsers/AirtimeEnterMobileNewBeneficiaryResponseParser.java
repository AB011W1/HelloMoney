/**
 *
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.dao.product.impl.ComponentResDAOImpl;
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
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.Biller;
import com.barclays.ussd.validation.USSDCompositeValidator;
import com.barclays.ussd.validation.USSDMobileLengthValidator;

/**
 * @author BTCI
 *
 */
public class AirtimeEnterMobileNewBeneficiaryResponseParser implements BmgBaseJsonParser, SystemPreferenceValidator, ScreenSequenceCustomizer {
	@Autowired
	UssdMenuBuilder ussdMenuBuilder;
	@Autowired
	ComponentResDAOImpl componentResDAOImpl;

	private static final String TRANSACTION_AIRTIME_LABEL = "label.enter.new.beneficiary.mobnum";

	@Override
	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
		return renderMenuOnScreen(responseBuilderParamsDTO);
	}

	private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException{
		USSDSessionManagement ussdSessionMgmt=responseBuilderParamsDTO.getUssdSessionMgmt();
		UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
		Locale locale = new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode());
		String airtimeMobileNoLabel = ussdResourceBundle.getLabel(TRANSACTION_AIRTIME_LABEL, locale);
		String ubpBusinessId=componentResDAOImpl.getUBPBusinessId();
		if(ubpBusinessId.contains(ussdSessionMgmt.getBusinessId())){
			ArrayList<Biller> sBlr=(ArrayList<Biller>) responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get("ATT1022");
			int loc=0;
			if(ussdSessionMgmt.getPreviousRenderedScreen().getPageHeader().equals("label.select.airtime.youroperator"))
				loc=Integer.parseInt(responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().get("billerId"));
			else
				loc=Integer.parseInt(responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().get("newbenelist2"));
			airtimeMobileNoLabel = componentResDAOImpl.getBillerLabelByKey(sBlr.get(loc-1).getBillerId(), ussdSessionMgmt.getBusinessId(),locale.getLanguage());
		}

		MenuItemDTO menuItemDTO = new MenuItemDTO();
		menuItemDTO.setPageBody(airtimeMobileNoLabel);
		//ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().setHomeOptionReq("TRUE");
		USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);

		menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
		menuItemDTO.setPaginationType(PaginationEnum.NOT_REQD);
		setNextScreenSequenceNumber(menuItemDTO);
		return menuItemDTO;
	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TEN.getSequenceNo());

	}

	@Override
	public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
		MsisdnDTO msisdnDTO = ussdMenuBuilder.getPhoneNoLength(ussdSessionMgmt.getCountryCode(), ussdSessionMgmt.getBusinessId());
		String mobileLength = Integer.toString(msisdnDTO.getSnlen());

		USSDCompositeValidator validator = new USSDCompositeValidator(new USSDMobileLengthValidator(mobileLength));
		try {
			validator.validate(userInput);
		} catch (USSDNonBlockingException e) {
			throw e;
		}
	}

	@Override
	public int getCustomNextScreen(String userInput,
			USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
		int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TEN
				.getSequenceNo();
		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();

		//TZNBC Menu Optimization - To fetch payment type from bene management list
		String paymentTypeInput=null;
		String transNodeId=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();		
			
		if(ussdSessionMgmt.getBusinessId().equalsIgnoreCase("TZNBC")) 
			paymentTypeInput=userInputMap.get(USSDInputParamsEnum.AIRTIME_TOPUP_BENE_MANAGEMENT.getParamName());

		//Ghana Menu Optimization - To fetch editbene label
		else if(ussdSessionMgmt.getBusinessId().equalsIgnoreCase("GHBRB") && null!=transNodeId && !transNodeId.equalsIgnoreCase("ussd0.10"))
			paymentTypeInput = userInputMap.get(USSDInputParamsEnum.AIRTIME_TOPUP_MSISDN_TYPE.getParamName());		

		else
			paymentTypeInput=userInputMap.get(USSDInputParamsEnum.AIRTIME_TOPUP_PAYMENT_TYPE.getParamName());

		if (null != paymentTypeInput && ((ussdSessionMgmt.getBusinessId().equalsIgnoreCase("TZNBC") && paymentTypeInput.equals("2")) 
				|| (ussdSessionMgmt.getBusinessId().equalsIgnoreCase("GHBRB") && !transNodeId.equalsIgnoreCase("ussd0.10") && paymentTypeInput.equals("5"))
				|| (!ussdSessionMgmt.getBusinessId().equalsIgnoreCase("GHBRB") && paymentTypeInput.equals("4"))
				|| (ussdSessionMgmt.getBusinessId().equalsIgnoreCase("GHBRB") && transNodeId.equalsIgnoreCase("ussd0.10") && paymentTypeInput.equals("4")))) {
			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWENTY.getSequenceNo();
		}
		return seqNo;
	}
}
