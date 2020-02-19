/**
 *
 */
package com.barclays.ussd.utils.jsonparsers;

/**
 * @author BTCI
 *
 */

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.internal.nonregistered.IntNRFundTxConfirm;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.internal.nonregistered.IntNRFundTxConfirmPayData;

public class InternalNonRegFundTransferConfirmJsonParser implements BmgBaseJsonParser,ScreenSequenceCustomizer {
    private static final String TRUE = "true";
    private static final String NAVIGATE_MAIN = "label.navigate.main";
    private static final String NAVIGATEBACK_LABEL = "label.navigate.main.back";
    private static final String TRANSID_LABEL = "label.transaction.id";
    private static final Logger LOGGER = Logger.getLogger(InternalNonRegFundTransferConfirmJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();
	try {
		if(txSessions == null){
			txSessions = new HashMap<String, Object>();
		}
	    IntNRFundTxConfirm intNRFundTxConfirmObj = mapper.readValue(responseBuilderParamsDTO.getJsonString(), IntNRFundTxConfirm.class);
	    if (intNRFundTxConfirmObj != null) {
		if ((intNRFundTxConfirmObj.getPayHdr() != null)
			&& (USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(intNRFundTxConfirmObj.getPayHdr().getResCde()))) {

			//CR73
			txSessions.put(USSDInputParamsEnum.INT_NR_FT_CONFIRM.getTranId(),FundTransferConstants.FUND_TRANSFER_OTHER_BARCLAYS_SAVE_BILLER);
			ussdSessionMgmt.setTxSessions(txSessions);

		    menuDTO = renderMenuOnScreen(intNRFundTxConfirmObj.getPayData(), responseBuilderParamsDTO);
		} else if (intNRFundTxConfirmObj.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(intNRFundTxConfirmObj.getPayHdr().getResCde());
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

    private MenuItemDTO renderMenuOnScreen(IntNRFundTxConfirmPayData intNRFundTxConfirmPayData, ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	StringBuilder pageBody = new StringBuilder();
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	if (intNRFundTxConfirmPayData != null) {
	    String txIdLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(TRANSID_LABEL,
		    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(txIdLabel);
	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(intNRFundTxConfirmPayData.getRefNo());
	    pageBody.append(USSDConstants.NEW_LINE);
	    String saveABenefeciaryLabel = getLabel(responseBuilderParamsDTO, USSDConstants.USSD_FTOBA_SAVE_BENEFECIARY);//CR73
	    pageBody.append(saveABenefeciaryLabel);
	    menuItemDTO.setPageBody(pageBody.toString());
	    menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	}
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageBody(pageBody.toString());
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	setNextScreenSequenceNumber(menuItemDTO);

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
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_NINE.getSequenceNo());

    }

	@Override
	public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt)
			throws USSDBlockingException {
		int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_NINE.getSequenceNo();
		String nibNo = null;
		if(null != ussdSessionMgmt.getTxSessions().get(
				USSDInputParamsEnum.REG_BENF_GET_NIB_NO.getParamName()))
			nibNo = ussdSessionMgmt.getTxSessions().get(
				USSDInputParamsEnum.REG_BENF_GET_NIB_NO.getParamName()).toString();
		if(nibNo != null)
			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THIRTEEN.getSequenceNo();
		return seqNo;
	}
}
