package com.barclays.ussd.utils.jsonparsers;

import java.util.Locale;

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
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.OTBnkFTExecData;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.OtBnkFTExec;

public class GhipsOneOffBenefConfirmSuccessJsonParser implements BmgBaseJsonParser {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(GhipsOneOffBenefConfirmSuccessJsonParser.class);

	private static final String LBL_SAVE_BENEF_SUCCESS = "label.ghips.ft.save.benef.confirm.success";
	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
		MenuItemDTO menuDTO = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			OtBnkFTExec otBnkFTExec = mapper.readValue(responseBuilderParamsDTO.getJsonString(), OtBnkFTExec.class);
			if (otBnkFTExec != null) {
				if (otBnkFTExec.getPayHdr() != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(otBnkFTExec.getPayHdr().getResCde())) {
					menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, otBnkFTExec.getPayData());
				} else if (otBnkFTExec.getPayHdr() != null) {
					LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
					throw new USSDNonBlockingException(otBnkFTExec.getPayHdr().getResCde());
				} else {
					LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
					throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
				}
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
			pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle().getLabel(LBL_SAVE_BENEF_SUCCESS, new Locale(lang, countryCode)));
			pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
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
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS.getSequenceNo());

	}

}
