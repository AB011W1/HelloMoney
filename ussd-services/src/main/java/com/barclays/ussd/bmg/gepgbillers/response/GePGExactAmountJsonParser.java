/**
 *
 */
package com.barclays.ussd.bmg.gepgbillers.response;


import java.util.Locale;

import org.apache.log4j.Logger;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.BillersListDO;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.BillDetails;

/**
 *
 * @author G01156975
 * The class handles the output to be displayed on the user screen.
 *
 */
public class GePGExactAmountJsonParser implements BmgBaseJsonParser {

	private static final Logger LOGGER = Logger.getLogger(GePGExactAmountJsonParser.class);
	private static final String BILL_DETAILS_LABEL = "label.enter.billDetails";
	private static final String BILL_CONTROLNUMBER_LABEL = "label.enter.bill.controlnumber";
	private static final String BILL_AMOUNT_LABEL = "label.enter.billAmount";
	private static final String BILL_CONFIRM_LABEL = "label.confirm";
	private static final String BILLER_NAME_LABEL = "label.billername";

	/**
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
	@Override
	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
		MenuItemDTO menuDTO = null;
		try {
		    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO);
		} catch (Exception e) {
		    LOGGER.error("Exception : ", e);
		    //if (e instanceof USSDNonBlockingException) {
		    	//throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
		   // } else {
		    	throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		   // }
		}
		return menuDTO;
    }

	/**
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
		MenuItemDTO menuItemDTO = new MenuItemDTO();

		StringBuilder pageBody = new StringBuilder();
		USSDSessionManagement ussdSessionMgmt=responseBuilderParamsDTO.getUssdSessionMgmt();
		UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
		Locale locale = null;
		if(null != ussdSessionMgmt)
			locale = new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode());

		BillDetails billDetails = new BillDetails();
		if(null != ussdSessionMgmt && null != ussdSessionMgmt.getTxSessions()){
			billDetails = (BillDetails) ussdSessionMgmt.getTxSessions().get(USSDConstants.GePG_BILL_DETAIL);
		}
		BillersListDO billersListDO = null;
		if(null != ussdSessionMgmt)
			billersListDO = (BillersListDO) ussdSessionMgmt.getTxSessions().get(USSDConstants.GEPG_BILLER_INFO);

		if(null != billDetails) {
			pageBody.append(ussdResourceBundle.getLabel(BILL_DETAILS_LABEL,locale));
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(ussdResourceBundle.getLabel(BILLER_NAME_LABEL,locale));
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(null != billersListDO ? billersListDO.getBillerNm():"");
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(ussdResourceBundle.getLabel(BILL_CONTROLNUMBER_LABEL,locale));
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(billDetails.getPrimaryReferenceNumber());
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(ussdResourceBundle.getLabel(BILL_AMOUNT_LABEL,locale));
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(billDetails.getFeeAmount().getAmount() + " " + billDetails.getFeeAmount().getCurrency());
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(ussdResourceBundle.getLabel(BILL_CONFIRM_LABEL,locale));
		}

		menuItemDTO.setPageBody(pageBody.toString());
		USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
		setNextScreenSequenceNumber(menuItemDTO);
		return menuItemDTO;
    }

    /**
     * @param MenuItemDTO
     */
    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
    	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo());
    }
}