package com.barclays.ussd.bmg.creditcard.link;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.bean.UserProfile;
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

public class CreditCardLinkSubmitJsonParser implements BmgBaseJsonParser
// , ScreenSequenceCustomizer
{
	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(CreditCardLinkSubmitJsonParser.class);
	private static final String LABEL_CREDIT_CARD_LINK_SUCCESSFULLY = "label.credit.card.link.successfully";
	private static final String LABEL_CREDIT_CARD_LINK_CASE_NUMBER = "label.credit.card.link.casenumber";

	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {

		MenuItemDTO menuItemDTO = new MenuItemDTO();
		ObjectMapper mapper = new ObjectMapper();
		try {
			CreditCardLinkData CreditCardLinkDataObj = mapper.readValue(responseBuilderParamsDTO.getJsonString(), CreditCardLinkData.class);
			if(CreditCardLinkDataObj != null){
				if (CreditCardLinkDataObj.getPayHdr() != null
						&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(CreditCardLinkDataObj.getPayHdr().getResCde())) {
					if(CreditCardLinkDataObj.getPayData()!= null && CreditCardLinkDataObj.getPayData().getCaseNumber()!=null){

						String caseNumber = CreditCardLinkDataObj.getPayData().getCaseNumber();
						USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();

						List<String> params = new ArrayList<String>(1);
						params.add(caseNumber);
						String[] paramArray = params.toArray(new String[params.size()]);
						String successMessage =  responseBuilderParamsDTO.getUssdResourceBundle().getLabel(LABEL_CREDIT_CARD_LINK_SUCCESSFULLY, paramArray,
							new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));

						StringBuilder pageBody = new StringBuilder();
						pageBody.append(successMessage);
						menuItemDTO.setPageBody(pageBody.toString());
						USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
						menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
						menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
						menuItemDTO.setPaginationType(PaginationEnum.SPACED);
						setNextScreenSequenceNumber(menuItemDTO);
					}

				} else if (CreditCardLinkDataObj.getPayHdr() != null) {
					LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
					throw new USSDNonBlockingException(CreditCardLinkDataObj.getPayHdr().getResCde());
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
		return menuItemDTO;
	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS.getSequenceNo());

	}



}


