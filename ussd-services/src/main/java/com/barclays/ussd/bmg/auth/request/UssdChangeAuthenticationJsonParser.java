package com.barclays.ussd.bmg.auth.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.bmg.constants.RequestConstants;
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

/**
 * Parser for the confirmation message on approval/rejection of change requests
 *
 */
public class UssdChangeAuthenticationJsonParser implements BmgBaseJsonParser {

	private static final Logger LOGGER = Logger.getLogger(UssdChangeAuthenticationJsonParser.class);
	
	private static final String AUTHREQUEST_APPROVE_CONFIRM_LABEL = "label.ussdauth.approve.confirm";
	private static final String AUTHREQUEST_REJECT_CONFIRM_LABEL = "label.ussdauth.reject.confirm";

	@Override
	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDBlockingException, USSDNonBlockingException {

		String jsonString = responseBuilderParamsDTO.getJsonString();
		LOGGER.debug("Json String: " + jsonString);
		MenuItemDTO menuDTO = null;
		ObjectMapper mapper = new ObjectMapper();

		try {
			AuthRequestDetails authRequestDetailsObj = mapper.readValue(jsonString, AuthRequestDetails.class);

			if (authRequestDetailsObj != null && authRequestDetailsObj.getPayHdr() != null && USSDExceptions.SUCCESS
					.getBmgCode().equalsIgnoreCase(authRequestDetailsObj.getPayHdr().getResCde())) {
				
				menuDTO = renderMenuOnScreen(authRequestDetailsObj.getPayData(), responseBuilderParamsDTO);
				
			} else if (authRequestDetailsObj != null && authRequestDetailsObj.getPayHdr() != null) {
				
				LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
				throw new USSDNonBlockingException(authRequestDetailsObj.getPayHdr().getResCde(), authRequestDetailsObj.getPayHdr().getResMsg());
				
			} else {
				LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
				throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
			}

		} catch (USSDNonBlockingException e) {
			LOGGER.error("USSDNonBlockingException : ", e);
			List<String> eerrorParams = new ArrayList<String>();
			eerrorParams.add(((USSDNonBlockingException) e).getErrorMsg());
			((USSDNonBlockingException) e).setErrorParams(eerrorParams);
			throw e;
		} catch (Exception e) {
			LOGGER.error("Exception : ", e);
			throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());

		}

		return menuDTO;
	}

	/**
	 * @param AuthRequestDetailsPayData
	 *            authRequestDetailsPayData, ResponseBuilderParamsDTO responseBuilderParamsDTO
	 * @return MenuItemDTO
	 * 
	 * This method is used to create the page body to display on screen
	 */
	private MenuItemDTO renderMenuOnScreen(AuthRequestDetailsPayData authRequestDetailsPayData, ResponseBuilderParamsDTO responseBuilderParamsDTO) {

		MenuItemDTO menuItemDTO = new MenuItemDTO();
		USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
		StringBuilder pageBody = new StringBuilder();
		
		UssdResourceBundle resourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
		String approveConfirmLabel = resourceBundle.getLabel(AUTHREQUEST_APPROVE_CONFIRM_LABEL, new Locale(
				ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
		String rejectConfirmLabel = resourceBundle.getLabel(AUTHREQUEST_REJECT_CONFIRM_LABEL, new Locale(
				ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
		
		if(RequestConstants.AUTH_REQUEST_APPROVE.equals(authRequestDetailsPayData.getServiceResponse())) {
			pageBody.append(approveConfirmLabel);
		} else if(RequestConstants.AUTH_REQUEST_REJECT.equals(authRequestDetailsPayData.getServiceResponse())) {
			pageBody.append(rejectConfirmLabel);
		}
		
		menuItemDTO.setPageBody(pageBody.toString());
		USSDUtils
				.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
		menuItemDTO.setPaginationType(PaginationEnum.LISTED);
		setNextScreenSequenceNumber(menuItemDTO);
		return menuItemDTO;

	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		menuItemDTO
		.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS
				.getSequenceNo());

	}

}
