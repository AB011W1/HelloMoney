package com.barclays.ussd.bmg.groupwallet.response;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
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
import com.barclays.ussd.utils.jsonparsers.bean.regbenf.internal.RegBenfIntBean;
import com.barclays.ussd.utils.jsonparsers.bean.regbenf.internal.ValidateRegBenfIntPayData;

public class GroupWalletOneoffSubmitJsonParser implements BmgBaseJsonParser {

	private static final Logger LOGGER = Logger.getLogger(GroupWalletOneoffSubmitJsonParser.class);

	@Override
	public MenuItemDTO parseJsonIntoJava(
			ResponseBuilderParamsDTO responseBuilderParamsDTO)
	throws USSDBlockingException, USSDNonBlockingException {
		// TODO Auto-generated method stub
		MenuItemDTO menuDTO = null;

		ObjectMapper mapper=new ObjectMapper();
		try {
			RegBenfIntBean otBnkFTExec = mapper.readValue(responseBuilderParamsDTO.getJsonString(), RegBenfIntBean.class);
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
			// TODO Auto-generated catch block
			LOGGER.error("JsonParseException : ", e);
		    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			LOGGER.error("JsonParseException : ", e);
		    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Exception : ", e);
		    if (e instanceof USSDNonBlockingException) {
			throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
		    } else {
			throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		    }
		}
		return menuDTO;
	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		// TODO Auto-generated method stub
		menuItemDTO
		.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS
				.getSequenceNo());
	}

	private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, ValidateRegBenfIntPayData oTBnkFTExecData) {
		MenuItemDTO menuItemDTO = null;
		StringBuilder pageBody = new StringBuilder();
		if (oTBnkFTExecData != null) {
		menuItemDTO=new MenuItemDTO();
		String payReqAccepted= getLabel(responseBuilderParamsDTO, USSDConstants.LABEL_GROUPWALLET_PAYREQUEST_ACCEPTED);

		pageBody.append(payReqAccepted);
		pageBody.append(oTBnkFTExecData.getTxnRefNo());
		USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		menuItemDTO.setPageBody(pageBody.toString());
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setPaginationType(PaginationEnum.LISTED);
		menuItemDTO.setStatus(USSDConstants.STATUS_END);
		}
		return menuItemDTO;
	}

	private String getLabel(ResponseBuilderParamsDTO responseBuilderParamsDTO,
			String label) {
		String labelValue = null;
		USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO
		.getUssdSessionMgmt();
		UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO
		.getUssdResourceBundle();
		Locale locale = new Locale(ussdSessionMgmt.getUserProfile()
				.getLanguage(), ussdSessionMgmt.getUserProfile()
				.getCountryCode());
		labelValue = ussdResourceBundle.getLabel(label, locale);
		return labelValue;
	}
}