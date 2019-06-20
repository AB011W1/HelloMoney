package com.barclays.ussd.utils.jsonparsers;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.SearchIndividualCustNQResponse;
import com.barclays.ussd.utils.jsonparsers.bean.login.SearchIndividualCustNQPayData;

public class MobileWalletConfirmEditBeneficiaryResponseParser implements BmgBaseJsonParser {

	private static final String MWALLET_EDIT_BENEFICIARY_NAME ="label.enter.editbnf.name";
	private static final String CONFIRM_LABEL = "label.confirm";
	private static final Logger LOGGER = Logger.getLogger(MobileWalletConfirmBeneficiaryResponseParser.class);

	@Override
	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO)
	throws USSDBlockingException, USSDNonBlockingException {
		MenuItemDTO menuDTO = null;
		menuDTO = renderMenuOnScreen(responseBuilderParamsDTO);
		return menuDTO;
	}

	private MenuItemDTO renderMenuOnScreen(
			ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException, USSDBlockingException {

		USSDSessionManagement ussdSessionMgmt=responseBuilderParamsDTO.getUssdSessionMgmt();
		Locale locale = new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode());
		StringBuilder pageBody = new StringBuilder();
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
		if (txSessions == null) {
			txSessions = new HashMap<String, Object>(8);
			responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
		}
		menuItemDTO.setPageHeader("LBL9999");
		String customerName=getCustomerName(responseBuilderParamsDTO,menuItemDTO);
		String name = "";
		if(customerName!=null && !customerName.equals("")){

			if(customerName.length()>50)
			{
				name = customerName.substring(0, 49);
			}
			else
			{
				name = customerName;
			}
		String[] paramArray = new String[]{name};
		String benefname = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(MWALLET_EDIT_BENEFICIARY_NAME, paramArray,
				new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
		String confirmLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(CONFIRM_LABEL, locale);
		menuItemDTO.setPageBody(benefname);
		pageBody.append(benefname);
		pageBody.append(USSDConstants.NEW_LINE).append(confirmLabel);

		txSessions.put(USSDConstants.GHIPPS_CUSTOMER_NAME,customerName);
		USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);

		menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
		menuItemDTO.setPaginationType(PaginationEnum.SPACED);
		menuItemDTO.setPageBody(pageBody.toString());
		setNextScreenSequenceNumber(menuItemDTO);
		}
		return menuItemDTO;

	}
	private String getCustomerName(ResponseBuilderParamsDTO responseBuilderParamsDTO,MenuItemDTO menuItemDTO) throws USSDNonBlockingException, USSDBlockingException{
		String customeName="";
		try{
			UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
			ObjectMapper mapper = new ObjectMapper();
			SearchIndividualCustNQResponse searchIndividualCustNQResponse = mapper.readValue(responseBuilderParamsDTO.getJsonString(),
					SearchIndividualCustNQResponse.class);
			if (searchIndividualCustNQResponse != null && searchIndividualCustNQResponse.getPayHdr() != null) {
				String resCode=searchIndividualCustNQResponse.getPayHdr().getResCde();
				USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
				String countryCode="",lang="";
				if (ussdSessionMgmt.getUserProfile() != null) {
					countryCode = ussdSessionMgmt.getUserProfile().getCountryCode();
					lang = ussdSessionMgmt.getUserProfile().getLanguage();
				}
				Locale locale = new Locale(lang, countryCode);
				if (USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(resCode)) {
					SearchIndividualCustNQPayData searchIndividualCustNQPayData=searchIndividualCustNQResponse.getPayData();
					customeName=searchIndividualCustNQPayData.getFullName();
				}else if (StringUtils.equalsIgnoreCase(USSDExceptions.BEM09113.getBmgCode(), resCode)) {
					LOGGER.error("Exception in getCustomerName:" + responseBuilderParamsDTO.getBmgOpCode());
					menuItemDTO.setPageBody(ussdResourceBundle.getLabel(resCode, locale));
					USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
				} else if (StringUtils.equalsIgnoreCase(USSDExceptions.BEM09123.getBmgCode(), resCode)) {
					LOGGER.error("Exception in getCustomerName:" + responseBuilderParamsDTO.getBmgOpCode());
					menuItemDTO.setPageBody(ussdResourceBundle.getLabel(resCode, locale));
					USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
				} else if (StringUtils.equalsIgnoreCase(USSDExceptions.BEM09126.getBmgCode(), resCode)) {
					LOGGER.error("Exception in getCustomerName:" + responseBuilderParamsDTO.getBmgOpCode());
					menuItemDTO.setPageBody(ussdResourceBundle.getLabel(resCode, locale));
					USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
				} else if (StringUtils.equalsIgnoreCase(USSDExceptions.BEM09127.getBmgCode(), resCode)) {
					LOGGER.error("Exception in getCustomerName:" + responseBuilderParamsDTO.getBmgOpCode());
					menuItemDTO.setPageBody(ussdResourceBundle.getLabel(resCode, locale));
					USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
				} else if (StringUtils.equalsIgnoreCase(USSDExceptions.BEM09128.getBmgCode(), resCode)) {
					LOGGER.error("Exception in getCustomerName:" + responseBuilderParamsDTO.getBmgOpCode());
					menuItemDTO.setPageBody(ussdResourceBundle.getLabel(resCode, locale));
					USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
				} else if (StringUtils.equalsIgnoreCase(USSDExceptions.BEM09129.getBmgCode(), resCode)) {
					LOGGER.error("Exception in getCustomerName:" + responseBuilderParamsDTO.getBmgOpCode());
					menuItemDTO.setPageBody(ussdResourceBundle.getLabel(resCode, locale));
					USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
				} else if (StringUtils.equalsIgnoreCase(USSDExceptions.BEM09131.getBmgCode(), resCode)) {
					LOGGER.error("Exception in getCustomerName:" + responseBuilderParamsDTO.getBmgOpCode());
					menuItemDTO.setPageBody(ussdResourceBundle.getLabel(resCode, locale));
					USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
				} else {
					LOGGER.error("Invalid response got from the BMG " + responseBuilderParamsDTO.getBmgOpCode());
					throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
				}
			}
		}catch (Exception e) {
			LOGGER.error("Exception : ", e);
			if (e instanceof USSDNonBlockingException) {
				throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
			} else if (e instanceof USSDBlockingException) {
				throw new USSDBlockingException(((USSDBlockingException) e).getErrCode());
			} else {
				throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
			}
		}
		return customeName;
	}
	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {

		menuItemDTO
		.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWENTY.getSequenceNo());

	}

}
