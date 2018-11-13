package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

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
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.BeneData;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.OTBnkFTExecData;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.OtBnkFTExec;

public class KEEBFTExecuteJsonParser implements BmgBaseJsonParser {


    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(EBFTExecuteJsonParser.class);

    private static final String LBL_TXN_ID = "label.transact.id";
    private static final String LBL_MZN_TXN_ID = "label.DFT.success.txnID";
    private static final String LBL_DFT_CONFIRM_TO = "label.DFT.confirm.to";
	private static final String LBL_AMOUNT = "label.amount";

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
	UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	String businessId=responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId();
	Map <String,String>userInputMap=ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();

	if (oTBnkFTExecData != null) {

	    String countryCode = "";
	    String lang = "";
	    if (ussdSessionMgmt.getUserProfile() != null) {
		countryCode = ussdSessionMgmt.getUserProfile().getCountryCode();
		lang = ussdSessionMgmt.getUserProfile().getLanguage();
	    }
	    Locale locale = new Locale(lang, countryCode);
	    menuItemDTO = new MenuItemDTO();

	    pageBody.append(USSDConstants.NEW_LINE);
	    if(businessId.equals("MZBRB"))
	    	pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle().getLabel(LBL_MZN_TXN_ID, new Locale(lang, countryCode)));
	    else
	    	pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle().getLabel(LBL_TXN_ID, new Locale(lang, countryCode)));
	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    pageBody.append(oTBnkFTExecData.getTxnResRefNo());
	    if(businessId.equals("MZBRB")){
		    addLabelToPageBody(pageBody, ussdResourceBundle.getLabel(LBL_AMOUNT, locale));
			pageBody.append(userInputMap.get("amount"));
		    addLabelToPageBody(pageBody, ussdResourceBundle.getLabel(LBL_DFT_CONFIRM_TO, locale));
			ArrayList<BeneData> benefList=(ArrayList<BeneData>)(responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get("KEBAFT001"));
			int pos=Integer.parseInt(responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().get("payId"));
			BeneData bd= benefList.get(pos-1);
			String bname=bd.getDisLbl();
			pageBody.append(bname);
		    }

	    menuItemDTO.setPageBody(pageBody.toString());
	    /*if(businessId.equals("MZBRB")){
		    pageBody.append(USSDConstants.NEW_LINE);
		    pageBody.append(ussdResourceBundle.getLabel("label.DFT.success.save.benf", locale));
		    }*/
	    // insert the back and home options
	    USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	    menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	    menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	    menuItemDTO.setStatus(USSDConstants.STATUS_END);
	}
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }
    private void addLabelToPageBody(StringBuilder pageBody, String label) {
    	pageBody.append(USSDConstants.NEW_LINE);
    	pageBody.append(label);
    	pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
        }
    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS.getSequenceNo());

    }

}
