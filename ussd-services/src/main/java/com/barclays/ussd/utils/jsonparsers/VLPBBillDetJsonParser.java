/**
 * VLPBBillDetJsonParser.java
 */
package com.barclays.ussd.utils.jsonparsers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.vlpb.VLPBBillDetData;
import com.barclays.ussd.utils.jsonparsers.bean.vlpb.VLPBBillDetail;

/**
 * @author BTCI
 * 
 */
public class VLPBBillDetJsonParser implements BmgBaseJsonParser {

    private static final Logger LOGGER = Logger.getLogger(VLPBBillDetJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();

	try {
	    VLPBBillDetail vLPBBillDetail = mapper.readValue(responseBuilderParamsDTO.getJsonString(), VLPBBillDetail.class);

	    if (vLPBBillDetail != null) {
		if (vLPBBillDetail.getPayHdr() != null
			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(vLPBBillDetail.getPayHdr().getResCde())) {
		    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, vLPBBillDetail.getPayData());
		} else if (vLPBBillDetail.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(vLPBBillDetail.getPayHdr().getResCde());
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
     * @param payData
     * @return MenuItemDTO
     * @throws ParseException
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, VLPBBillDetData payData) throws ParseException {
	StringBuilder pageBody = new StringBuilder();
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	if (payData != null && payData.getTransactionHistory() != null) {
	    String countryCode = responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getCountryCode();
	    String lang = responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getLanguage();
	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle().getLabel(USSDConstants.LBL_VLPB_BILLER_NAME,
		    new Locale(lang, countryCode)));
	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    if (payData.getTransactionHistory().getBillerInfo() != null) {
		pageBody.append(payData.getTransactionHistory().getBillerInfo().getBillerName());
	    }
	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle().getLabel(USSDConstants.LBL_VLPB_BILLER_AMT,
		    new Locale(lang, countryCode)));
	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
	    if (payData.getTransactionHistory().getAmountInfo() != null) {
		String amt = payData.getTransactionHistory().getAmountInfo().getAmt();
		String formattedAmt = null;
		try {
		    double parsedAmt = Double.parseDouble(amt);
		    formattedAmt = String.format("%.2f", parsedAmt);

		} catch (NumberFormatException excp) {
		    // do nothing
		}

		pageBody.append(formattedAmt);
		pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
		pageBody.append(payData.getTransactionHistory().getAmountInfo().getCurr());
	    }
	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(responseBuilderParamsDTO.getUssdResourceBundle().getLabel(USSDConstants.LBL_VLPB_PAY_DT, new Locale(lang, countryCode)));
	    pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);

	    String billDate = payData.getTransactionHistory().getTransactionDate();
	    // SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
	    String fomattedDate = sf.format(formatter.parse(billDate));
	    pageBody.append(fomattedDate);

	}
	menuItemDTO.setPageBody(pageBody.toString());
	// insert the back and home options ADDED NEW
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_END);
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS.getSequenceNo());

    }
}
