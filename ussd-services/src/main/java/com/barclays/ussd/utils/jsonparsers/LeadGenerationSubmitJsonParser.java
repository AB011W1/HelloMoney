/**
 * AccountSummaryJSONParser.java
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.bean.UserProfile;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.creditcard.link.ApplyProductData;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;

/**
 * @author BTCI
 *
 */
public class LeadGenerationSubmitJsonParser implements BmgBaseJsonParser {


	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(LeadGenerationSubmitJsonParser.class);
	private static final String LABEL_LEAD_GEN_LINK_SUBMIT = "label.lead.generation.submit";
	private static final String LEAD_GEN_TIMIZA_LABEL = "label.lead.generation.timiza";

	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {

		MenuItemDTO menuItemDTO = new MenuItemDTO();
		ObjectMapper mapper = new ObjectMapper();
		try {
			ApplyProductData applyProductDataObj = mapper.readValue(responseBuilderParamsDTO.getJsonString(), ApplyProductData.class);
			if(applyProductDataObj != null){
				if (applyProductDataObj.getPayHdr() != null
						&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(applyProductDataObj.getPayHdr().getResCde())) {
					if(applyProductDataObj.getPayData()!= null && applyProductDataObj.getPayData().getCaseNumber()!=null){

						USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
						UserProfile userProfile = ussdSessionMgmt.getUserProfile();
						UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
						Locale locale = new Locale(userProfile.getLanguage(), userProfile.getCountryCode());

						String caseNumber = applyProductDataObj.getPayData().getCaseNumber();
						List<String> params = new ArrayList<String>(1);
						params.add(caseNumber);
						String[] paramArray = params.toArray(new String[params.size()]);

						Map<String, String> userInputMap = responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
						String productName = userInputMap.get(USSDConstants.LEAD_GEN_PRODUCT_NAME);
						//TZNBC Menu Optimization - Product name null check
						if (null!=productName && productName.equals(USSDConstants.LEAD_GEN_SUB_PRODUCT_NAME_TIMIZA)) {

							//pageBody.append(ussdResourceBundle.getLabel(LEAD_GEN_TIMIZA_LABEL,locale));
							//menuItemDTO.setPageBody(pageBody.toString());


							menuItemDTO.setPageBody(ussdResourceBundle.getLabel(LEAD_GEN_TIMIZA_LABEL,locale).toString());
						}else{
						String successMessage = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(LABEL_LEAD_GEN_LINK_SUBMIT, paramArray,
									new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
						StringBuilder pageBody = new StringBuilder();
						pageBody.append(successMessage);
						menuItemDTO.setPageBody(pageBody.toString());
						}
						USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
						menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
						menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
						menuItemDTO.setPaginationType(PaginationEnum.SPACED);
						setNextScreenSequenceNumber(menuItemDTO);
					}

				} else if (applyProductDataObj.getPayHdr() != null) {
					LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
					throw new USSDNonBlockingException(applyProductDataObj.getPayHdr().getResCde());
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
