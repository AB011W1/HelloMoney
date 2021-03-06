package com.barclays.ussd.utils.jsonparsers;


import java.util.List;
import java.util.Locale;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.bmg.constants.ErrorCodeConstant;
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
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.CreateIndividualCustomerJsonResponse;

public class KitsRegisterSubmitJsonParser implements BmgBaseJsonParser{

	 private static final Logger LOGGER = Logger.getLogger(KitsRegisterSubmitJsonParser.class);


	    @Override
	    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	    	String jsonString = responseBuilderParamsDTO.getJsonString();
	    	MenuItemDTO menuDTO = null;
	    	ObjectMapper mapper = new ObjectMapper();

	    	try {
	    		CreateIndividualCustomerJsonResponse createIndividualCustomerSubmitObj = mapper.readValue(jsonString, CreateIndividualCustomerJsonResponse.class);
	    	    if (createIndividualCustomerSubmitObj != null) {
	    		if (createIndividualCustomerSubmitObj.getPayHdr() != null
	    			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(createIndividualCustomerSubmitObj.getPayHdr().getResCde())) {
	    		    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO);
	    		}else if(createIndividualCustomerSubmitObj.getPayHdr() != null
						&& ErrorCodeConstant.BUSINESS_ERROR.equals(createIndividualCustomerSubmitObj.getPayHdr().getResCde())){
					throw new USSDNonBlockingException(USSDExceptions.BUSINESS06000REG.getBmgCode(),true);
	    		}else if (createIndividualCustomerSubmitObj.getPayHdr() != null) {
	    		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
	    		    throw new USSDNonBlockingException(createIndividualCustomerSubmitObj.getPayHdr().getResCde(),true);
	    		} else {
	    		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
	    		    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode(),true);
	    		}
	    	    }
	    	} catch (JsonParseException e) {
	    	    LOGGER.error("JsonParseException : ", e);
	    	    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode(),true);
	    	} catch (JsonMappingException e) {
	    	    LOGGER.error("JsonParseException : ", e);
	    	    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode(),true);
	    	} catch (Exception e) {
	    	    LOGGER.error("Exception : ", e);
	    	    if (e instanceof USSDNonBlockingException) {
	    		throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode(),true);
	    	    } else {
	    		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode(),true);
	    	    }
	    	}

	    	return menuDTO;



	    }

	    /**
	     * @param airtimeSubmitPayData
	     * @param responseBuilderParamsDTO
	     * @return MenuItemDTO
	     */
	    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {

        StringBuilder pageBody = new StringBuilder();
		MenuItemDTO menuItemDTO = new MenuItemDTO();


		String displayMessage = getLabel(responseBuilderParamsDTO,"kits.successful.registration");
		pageBody.append(displayMessage);
		pageBody.append(USSDConstants.NEW_LINE);

		menuItemDTO.setPageBody(pageBody.toString());
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setStatus(USSDConstants.STATUS_END);
		menuItemDTO.setPaginationType(PaginationEnum.NOT_REQD);
		USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		setNextScreenSequenceNumber(menuItemDTO);
		responseBuilderParamsDTO.getUssdSessionMgmt().clean();
		return menuItemDTO;
	    }

	    private String getLabel(ResponseBuilderParamsDTO responseBuilderParamsDTO, String label) {
		String labelValue = null;
		USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
		UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
		Locale locale = new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode());
		labelValue = ussdResourceBundle.getLabel(label, locale);
		return labelValue;
	    }

	    @Override
	    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS.getSequenceNo());
	    }


}
