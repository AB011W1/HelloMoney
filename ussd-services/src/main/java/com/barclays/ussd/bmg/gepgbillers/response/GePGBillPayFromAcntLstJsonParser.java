/**
 * BillPayFromAcntLstJsonParser.java
 */
package com.barclays.ussd.bmg.gepgbillers.response;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
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
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.AccountData;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.BillDetails;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.BillPayFrmAccntLst;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.FromAcntLst;
import com.barclays.ussd.utils.jsonparsers.bean.otbp.OTBPInitAccount;
import com.barclays.ussd.utils.jsonparsers.bean.otbp.OTBPInitPayData;
import com.barclays.ussd.utils.jsonparsers.bean.otbp.OTBPInitResponse;

/**
 * @author BTCI
 *
 */
public class GePGBillPayFromAcntLstJsonParser implements BmgBaseJsonParser {

    	/** The Constant LOGGER. */
	    private static final Logger LOGGER = Logger.getLogger(GePGBillPayFromAcntLstJsonParser.class);

	    private static final String SELECT_ACCOUNT_LABEL = "label.groupwallet.select.accnum";

	    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
			MenuItemDTO menuDTO = null;
			ObjectMapper mapper = new ObjectMapper();

			try {

				OTBPInitResponse otbpInitResponse = mapper.readValue(responseBuilderParamsDTO.getJsonString(), OTBPInitResponse.class);
			    if (otbpInitResponse != null) {
					if (otbpInitResponse.getPayHdr() != null
						&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(otbpInitResponse.getPayHdr().getResCde())) {
					    menuDTO = renderMenuOnScreen(otbpInitResponse.getPayData(), responseBuilderParamsDTO);
					    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
					    // set the from accnt list to the session
					    ussdSessionMgmt.getTxSessions().put(USSDInputParamsEnum.GePG_PAY_BILLS_FROM_ACNT.getTranId(),
					    		otbpInitResponse.getPayData().getFromAcctList());
					} else if (otbpInitResponse.getPayHdr() != null) {
					    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
					    throw new USSDNonBlockingException(otbpInitResponse.getPayHdr().getResCde());
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
     * @param initPayData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(OTBPInitPayData initPayData, ResponseBuilderParamsDTO responseBuilderParamsDTO) {
    	int index = 1;
		StringBuilder pageBody = new StringBuilder();
		USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
		Locale locale = new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode());
		String paramArray[]= new String[1];
		if(null != ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get(USSDInputParamsEnum.GePG_FULL_PARTIAL_AMOUNT.getParamName())){
			paramArray[0] = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get(USSDInputParamsEnum.GePG_FULL_PARTIAL_AMOUNT.getParamName());
		} else {
			BillDetails billDetails = (BillDetails) ussdSessionMgmt.getTxSessions().get("gePGBillDetail");
			paramArray[0] = billDetails.getFeeAmount().getAmount().toString();
		}
		String selectAccountLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(SELECT_ACCOUNT_LABEL, paramArray, locale);;

		pageBody.append(selectAccountLabel);

		MenuItemDTO menuItemDTO = new MenuItemDTO();
		if (null != initPayData && null != initPayData.getFromAcctList() && !initPayData.getFromAcctList().isEmpty()) {
		    for (OTBPInitAccount acctDet : initPayData.getFromAcctList()) {
				pageBody.append(USSDConstants.NEW_LINE);
				pageBody.append(index);
				pageBody.append(USSDConstants.DOT_SEPERATOR);
				pageBody.append(acctDet.getMkdActNo());
				index++;

				menuItemDTO.setPageBody(pageBody.toString());
		    }
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
    	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIX.getSequenceNo());
    }

}