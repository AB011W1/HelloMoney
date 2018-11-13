package com.barclays.ussd.utils.jsonparsers;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
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
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.OTBnkFTExecData;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.OtBnkFTExec;

public class GhipsOneOffFundTransferSubmitJsonParser implements BmgBaseJsonParser {


	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(GhipsOneOffFundTransferSubmitJsonParser.class);

	private static final String LBL_TXN_ID = "label.transact.id";
	private static final String LBL_SAVE_BENEF_OPTION = "label.ghips.ft.save.benef.opt";
	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
		MenuItemDTO menuDTO = new MenuItemDTO();
		menuDTO.setPageHeader("LBL9999");
		ObjectMapper mapper = new ObjectMapper();
		try {
			OtBnkFTExec otBnkFTExec = mapper.readValue(responseBuilderParamsDTO.getJsonString(), OtBnkFTExec.class);
			if (otBnkFTExec != null && otBnkFTExec.getPayHdr() != null) {
				UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
				USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
				String countryCode="",lang="";
				if (ussdSessionMgmt.getUserProfile() != null) {
					countryCode = ussdSessionMgmt.getUserProfile().getCountryCode();
					lang = ussdSessionMgmt.getUserProfile().getLanguage();
				}
				Locale locale = new Locale(lang, countryCode);
				String resCode=otBnkFTExec.getPayHdr().getResCde();
				if (USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(resCode)) {
					menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, otBnkFTExec.getPayData());
				} else if (StringUtils.equalsIgnoreCase("BEM7502", resCode)) {
					LOGGER.error("Account number does not exist: " + responseBuilderParamsDTO.getBmgOpCode());
					menuDTO.setPageBody(ussdResourceBundle.getLabel(resCode, locale));
					USSDUtils.appendHomeAndBackOption(menuDTO, responseBuilderParamsDTO);
				} else if (StringUtils.equalsIgnoreCase(USSDExceptions.BEM09113.getBmgCode(), resCode)) {
					LOGGER.error("Exception in getCustomerName:" + responseBuilderParamsDTO.getBmgOpCode());
					menuDTO.setPageBody(ussdResourceBundle.getLabel(resCode, locale));
					USSDUtils.appendHomeAndBackOption(menuDTO, responseBuilderParamsDTO);
				} else if (StringUtils.equalsIgnoreCase(USSDExceptions.BEM09123.getBmgCode(), resCode)) {
					LOGGER.error("Exception in getCustomerName:" + responseBuilderParamsDTO.getBmgOpCode());
					menuDTO.setPageBody(ussdResourceBundle.getLabel(resCode, locale));
					USSDUtils.appendHomeAndBackOption(menuDTO, responseBuilderParamsDTO);
				} else if (StringUtils.equalsIgnoreCase(USSDExceptions.BEM09126.getBmgCode(), resCode)) {
					LOGGER.error("Exception in getCustomerName:" + responseBuilderParamsDTO.getBmgOpCode());
					menuDTO.setPageBody(ussdResourceBundle.getLabel(resCode, locale));
					USSDUtils.appendHomeAndBackOption(menuDTO, responseBuilderParamsDTO);
				} else if (StringUtils.equalsIgnoreCase(USSDExceptions.BEM09127.getBmgCode(), resCode)) {
					LOGGER.error("Exception in getCustomerName:" + responseBuilderParamsDTO.getBmgOpCode());
					menuDTO.setPageBody(ussdResourceBundle.getLabel(resCode, locale));
					USSDUtils.appendHomeAndBackOption(menuDTO, responseBuilderParamsDTO);
				} else if (StringUtils.equalsIgnoreCase(USSDExceptions.BEM09128.getBmgCode(), resCode)) {
					LOGGER.error("Exception in getCustomerName:" + responseBuilderParamsDTO.getBmgOpCode());
					menuDTO.setPageBody(ussdResourceBundle.getLabel(resCode, locale));
					USSDUtils.appendHomeAndBackOption(menuDTO, responseBuilderParamsDTO);
				} else if (StringUtils.equalsIgnoreCase(USSDExceptions.BEM09129.getBmgCode(), resCode)) {
					LOGGER.error("Exception in getCustomerName:" + responseBuilderParamsDTO.getBmgOpCode());
					menuDTO.setPageBody(ussdResourceBundle.getLabel(resCode, locale));
					USSDUtils.appendHomeAndBackOption(menuDTO, responseBuilderParamsDTO);
				} else if (StringUtils.equalsIgnoreCase(USSDExceptions.BEM09131.getBmgCode(), resCode)) {
					LOGGER.error("Exception in getCustomerName:" + responseBuilderParamsDTO.getBmgOpCode());
					menuDTO.setPageBody(ussdResourceBundle.getLabel(resCode, locale));
					USSDUtils.appendHomeAndBackOption(menuDTO, responseBuilderParamsDTO);
				} else {
					LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
					throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
				}
			}else{
				LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
				throw new USSDNonBlockingException(otBnkFTExec.getPayHdr().getResCde());
			}
		} catch (JsonParseException e) {
			LOGGER.error("JsonParseException : ", e);
			throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		} catch (JsonMappingException e) {
			LOGGER.error("JsonParseException : ", e);
			throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
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
	 * @param responseBuilderParamsDTO
	 * @param oTBnkFTExecData
	 * @param warningMsg
	 * @return MenuItemDTO
	 */
	private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, OTBnkFTExecData oTBnkFTExecData) {
		MenuItemDTO menuItemDTO = null;
		StringBuilder pageBody = new StringBuilder();
		USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
		if (oTBnkFTExecData != null) {

			String countryCode = "";
			String lang = "";
			if (ussdSessionMgmt.getUserProfile() != null) {
				countryCode = ussdSessionMgmt.getUserProfile().getCountryCode();
				lang = ussdSessionMgmt.getUserProfile().getLanguage();
			}
			menuItemDTO = new MenuItemDTO();
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle().getLabel(LBL_TXN_ID, new Locale(lang, countryCode)));
			pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
			pageBody.append(oTBnkFTExecData.getTxnResRefNo());
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle().getLabel(LBL_SAVE_BENEF_OPTION, new Locale(lang, countryCode)));
			menuItemDTO.setPageBody(pageBody.toString());
			USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
			menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
			menuItemDTO.setPaginationType(PaginationEnum.LISTED);
			menuItemDTO.setStatus(USSDConstants.STATUS_END);
		}
		setNextScreenSequenceNumber(menuItemDTO);
		return menuItemDTO;
	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ELEVEN.getSequenceNo());

	}

}
