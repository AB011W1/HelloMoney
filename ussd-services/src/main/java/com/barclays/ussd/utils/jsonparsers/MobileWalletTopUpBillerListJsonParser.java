/**
 * BillPayBillerListJsonParser.java
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
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
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Beneficiery;

/**
 * @author BTCI This class is json parser for displaying list of billers
 *
 */
public class MobileWalletTopUpBillerListJsonParser implements BmgBaseJsonParser,ScreenSequenceCustomizer {

	private static final Logger LOGGER = Logger.getLogger(MobileWalletTopUpBillerListJsonParser.class);

	private static final String SAVE_LABEL = "label.wallet.save.mybillers";
	private static final String DELETE_LABEL = "label.airtime.delete.mybillers";
	private static final String EDIT_LABEL = "label.airtime.edit.mybillers";
	private static final String DEFAULT_LABEL = "label.mybillers";

	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {

		MenuItemDTO menuDTO = null;
		USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();

		try {

			List<Beneficiery> catzedPayLst = (List<Beneficiery>) ussdSessionMgmt.getTxSessions().get(
					USSDInputParamsEnum.MOBILE_WALLET_PAYEE_LIST.getTranId());
			menuDTO = renderMenuOnScreen(catzedPayLst, responseBuilderParamsDTO);
		} catch (Exception e) {
			LOGGER.error("Exception : ", e);
			if (e instanceof USSDNonBlockingException) {
				throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
			} else {
				throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
			}
		}
		return menuDTO;
	}

	/**
	 * @param billerLstData
	 * @param responseBuilderParamsDTO
	 * @return MenuItemDTO
	 * @throws USSDBlockingException
	 */
	private MenuItemDTO renderMenuOnScreen(List<Beneficiery> catzedPayLst, ResponseBuilderParamsDTO responseBuilderParamsDTO)
			throws USSDNonBlockingException {
		int index = 1;
		StringBuilder pageBody = new StringBuilder();
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		USSDSessionManagement ussdSessionMgmt= responseBuilderParamsDTO.getUssdSessionMgmt();
		Map<String, String> userInputMap = responseBuilderParamsDTO.getUssdSessionMgmt()
				.getUserTransactionDetails().getUserInputMap();
		//TZNBC Menu Optimization - fetch the payment type base on bene management
		String paymentTypeInput=null;
		if(ussdSessionMgmt.getBusinessId().equalsIgnoreCase("TZNBC"))
			paymentTypeInput=userInputMap.get(USSDInputParamsEnum.MOBILE_WALLET_BENE_MANAGEMENT.getParamName());
		else
			paymentTypeInput=userInputMap.get(USSDInputParamsEnum.MOBILE_WALLET_PAYMENT_TYPE.getParamName());

		if (null!=paymentTypeInput && ((ussdSessionMgmt.getBusinessId().equalsIgnoreCase("TZNBC") && paymentTypeInput.equals("3")) || paymentTypeInput.equals("5"))) {
			pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle()
					.getLabel(
							DELETE_LABEL,
							new Locale(ussdSessionMgmt.getUserProfile()
									.getLanguage(), ussdSessionMgmt
									.getUserProfile().getCountryCode())));
		}else if((paymentTypeInput==null && ussdSessionMgmt.getBusinessId().equalsIgnoreCase("TZNBC")) || (!ussdSessionMgmt.getBusinessId().equalsIgnoreCase("TZNBC") && paymentTypeInput.equals("2"))){
			pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle()
					.getLabel(
							SAVE_LABEL,
							new Locale(ussdSessionMgmt.getUserProfile()
									.getLanguage(), ussdSessionMgmt
									.getUserProfile().getCountryCode())));
		}else if(null!=paymentTypeInput && ((ussdSessionMgmt.getBusinessId().equalsIgnoreCase("TZNBC") && paymentTypeInput.equals("2")) || paymentTypeInput.equals("4"))){
			pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle()
					.getLabel(
							EDIT_LABEL,
							new Locale(ussdSessionMgmt.getUserProfile()
									.getLanguage(), ussdSessionMgmt
									.getUserProfile().getCountryCode())));
		}else{
			pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle()
					.getLabel(
							DEFAULT_LABEL,
							new Locale(ussdSessionMgmt.getUserProfile()
									.getLanguage(), ussdSessionMgmt
									.getUserProfile().getCountryCode())));
		}

		if (catzedPayLst != null && !catzedPayLst.isEmpty()) {
			for (Beneficiery ele : catzedPayLst) {
				pageBody.append(USSDConstants.NEW_LINE);
				pageBody.append(index);
				pageBody.append(USSDConstants.DOT_SEPERATOR);
				pageBody.append(ele.getDisLbl());
				index++;
			}
			menuItemDTO.setPageBody(pageBody.toString());

		}

		menuItemDTO.setPageBody(pageBody.toString());
		// insert the back and home options
		USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
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
	@Override
	public int getCustomNextScreen(String userInput,
			USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
		int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo();
		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
		
		//TZNBC Menu Optimization- fetch the input typw based on bene management
		String paymentTypeInput=null;
		if(ussdSessionMgmt.getBusinessId().equalsIgnoreCase("TZNBC"))
			paymentTypeInput=userInputMap.get(USSDInputParamsEnum.MOBILE_WALLET_PAYMENT_TYPE.getParamName());
		else 
			paymentTypeInput=userInputMap.get(USSDInputParamsEnum.MOBILE_WALLET_BENE_MANAGEMENT.getParamName());

		if (null!=paymentTypeInput && ((ussdSessionMgmt.getBusinessId().equalsIgnoreCase("TZNBC") && paymentTypeInput.equals("3")) || paymentTypeInput.equals("5"))) {
			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_SEVENTEEN.getSequenceNo();
		}
		if ((paymentTypeInput==null  && ussdSessionMgmt.getBusinessId().equalsIgnoreCase("TZNBC"))|| (!ussdSessionMgmt.getBusinessId().equalsIgnoreCase("TZNBC") && paymentTypeInput.equals("2"))) {
			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_NINETEEN.getSequenceNo();
		}		
		if (null!=paymentTypeInput && ((ussdSessionMgmt.getBusinessId().equalsIgnoreCase("TZNBC") && paymentTypeInput.equals("2")) || paymentTypeInput.equals("4"))) {
			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIFTEEN.getSequenceNo();
		}

		return seqNo;
	}
}
