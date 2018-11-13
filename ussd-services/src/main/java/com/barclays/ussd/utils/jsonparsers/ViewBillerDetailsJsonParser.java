/**
 * ViewBillerDetailsJsonParser.java
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.dao.product.impl.ComponentResDAOImpl;
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
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;
import com.barclays.ussd.utils.jsonparsers.bean.viewbillers.BillerDetailData;
import com.barclays.ussd.utils.jsonparsers.bean.viewbillers.BillerDetails;
import com.barclays.ussd.utils.jsonparsers.bean.viewbillers.ViewBillerDetails;

/**
 * @author BTCI
 *
 */
public class ViewBillerDetailsJsonParser implements BmgBaseJsonParser {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(ViewBillerDetailsJsonParser.class);

    @Autowired
	ComponentResDAOImpl componentResDAOImpl;

    private static final String BILLERNAME_LABEL = "label.billername";
    private static final String BILLERTYPE_LABEL = "label.billertype";
    private static final String BILLER_NICKNAME_LABEL = "label.biller.nickname";
    private static final String AREA_NAME_LABEL = "label.area.name";
 // WUC Botswana Biller change - 28/06/2017
    private static final String LABEL_CUSTOMER_REFERENCE_NUMBER = "label.wuc.customer.refNum";
    private static final String LABEL_CONTRACT_REFERENCE_NUMBER = "label.wuc.contract.refNum";

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();

	try {
	    ViewBillerDetails viewBillerDet = mapper.readValue(responseBuilderParamsDTO.getJsonString(), ViewBillerDetails.class);

	    if (viewBillerDet != null) {
		PayHdr payHdr = viewBillerDet.getPayHdr();
		if (payHdr != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(payHdr.getResCde())) {
		    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, viewBillerDet.getPayData(), "");
		} else if (payHdr != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(payHdr.getResCde());
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
     * @param billerDetdata
     * @param warningMsg
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, BillerDetailData billerDetdata, String warningMsg) {
	MenuItemDTO menuItemDTO = null;
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();

	if (billerDetdata != null) {
	    BillerDetails pay = billerDetdata.getPay();
	    menuItemDTO = new MenuItemDTO();
	    if (pay != null) {
		StringBuilder pageBody = new StringBuilder();

		UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
		UserProfile userProfile = ussdSessionMgmt.getUserProfile();
		Locale locale = new Locale(userProfile.getLanguage(), userProfile.getCountryCode());
		String areaNameLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(AREA_NAME_LABEL, locale);
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(ussdResourceBundle.getLabel(BILLER_NICKNAME_LABEL, locale));
		pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
		pageBody.append(pay.getPayNckNam());

		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(ussdResourceBundle.getLabel(BILLERNAME_LABEL, locale));
		pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
		pageBody.append(pay.getBilrNam());

		String billerRefLabel = null;
		String wucCustomerRefID = null;
	    String wucContractRefID = null;
		/*if(ussdSessionMgmt.getBusinessId().equals("KEBRB"))
			billerRefLabel = componentResDAOImpl.getBillerConfirmLabelByKey(pay.getBilrId(), ussdSessionMgmt.getBusinessId(), locale.getLanguage());
		else*/
		String ubpBusinessId=componentResDAOImpl.getUBPBusinessId();
    	if(ubpBusinessId.contains(ussdSessionMgmt.getBusinessId()) && !pay.getBilrId().equals("WUC-2")){
    		billerRefLabel = componentResDAOImpl.getBillerLabelByKey(pay.getBilrId(), ussdSessionMgmt.getBusinessId(), locale.getLanguage());
			if(billerRefLabel.startsWith("Enter"))
				billerRefLabel=Character.toUpperCase(billerRefLabel.charAt("Enter".length()+1))+billerRefLabel.substring("Enter".length()+2)+":";
			else if(billerRefLabel.startsWith("enter"))
				billerRefLabel=Character.toUpperCase(billerRefLabel.charAt("enter".length()+1))+billerRefLabel.substring("enter".length()+2)+":";
			else if(billerRefLabel.startsWith("Introduza o")  )
				billerRefLabel=Character.toUpperCase(billerRefLabel.charAt("Introduza o".length()+1))+billerRefLabel.substring("Introduza o".length()+2)+":";
			else if(billerRefLabel.startsWith("introduza o"))
				billerRefLabel=Character.toUpperCase(billerRefLabel.charAt("introduza o".length()+1))+billerRefLabel.substring("introduza o".length()+2)+":";
			else
				billerRefLabel=billerRefLabel+":";
    	}
		else
			billerRefLabel = ussdResourceBundle.getLabel(USSDUtils.getConfCustRefId(responseBuilderParamsDTO.getUssdResourceBundle(),
					locale, pay.getBilrId()), locale);

    	// WUC Botswana Biller change - 28/06/2017
    	 if(ussdSessionMgmt.getBusinessId().equals("BWBRB") && pay.getBilrId().equals("WUC-2")){
    		 wucCustomerRefID = ussdResourceBundle.getLabel(LABEL_CUSTOMER_REFERENCE_NUMBER, locale);
    		 wucContractRefID = ussdResourceBundle.getLabel(LABEL_CONTRACT_REFERENCE_NUMBER, locale);

    		pageBody.append(USSDConstants.NEW_LINE);
     		pageBody.append(wucCustomerRefID);
 			pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
 			pageBody.append(pay.getRefNo().getRefNo());

 			pageBody.append(USSDConstants.NEW_LINE);
      		pageBody.append(wucContractRefID);
  			pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
  			pageBody.append(pay.getWucContractRefNo().getRefNo());


    	 }else{
    		pageBody.append(USSDConstants.NEW_LINE);
    		pageBody.append(billerRefLabel);
			pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
			pageBody.append(pay.getRefNo().getRefNo());
    	 }

		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(ussdResourceBundle.getLabel(BILLERTYPE_LABEL, locale));
		pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
		pageBody.append(pay.getBilrTyp());

		if(StringUtils.isNotEmpty(pay.getBillerAreaCode()) && !pay.getBilrId().equals("WUC-2"))
	    {
			String selectArea = pay.getBillerAreaCode();
		    if (StringUtils.isNotEmpty(selectArea)) {
				pageBody.append(USSDConstants.NEW_LINE);
				pageBody.append(areaNameLabel);
				pageBody.append(USSDConstants.SINGLE_WHITE_SPACE);
				pageBody.append(selectArea);
		    }
	    }
		menuItemDTO.setPageBody(pageBody.toString());
	    }
	    menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	    menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	    USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	    menuItemDTO.setPageFooter(warningMsg + menuItemDTO.getPageFooter());
	    menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	    setNextScreenSequenceNumber(menuItemDTO);
	}
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS.getSequenceNo());

    }

}
