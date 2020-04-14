/**
 *GePGControlNumberJsonParser
 */
package com.barclays.ussd.bmg.gepgbillers.response;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.BillersListDO;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.common.services.IBillersLstService;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.sysprefs.services.ListValueResServiceImpl;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.SystemPreferenceValidator;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;

/**
 *
 * @author G01156975
 * The class handles the output to be displayed on the user screen.
 *
 */
public class GePGControlNumberJsonParser implements BmgBaseJsonParser, SystemPreferenceValidator {

	@Autowired
    private IBillersLstService billersLstService;

	private static final Logger LOGGER = Logger.getLogger(GePGControlNumberJsonParser.class);
	private static final String ENTER_CONTROLNUMBER_LABEL = "label.enter.controlnumber";

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
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
		MenuItemDTO menuItemDTO = new MenuItemDTO();

		StringBuilder pageBody = new StringBuilder();
		USSDSessionManagement ussdSessionMgmt=responseBuilderParamsDTO.getUssdSessionMgmt();
		UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
		Locale locale = new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode());
		pageBody.append(ussdResourceBundle.getLabel(ENTER_CONTROLNUMBER_LABEL,locale));
		pageBody.append(USSDConstants.NEW_LINE);
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
    	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWENTYFIVE.getSequenceNo());
    }

	@Override
	public void validate(String userInput, USSDSessionManagement ussdSessionMgmt)
			throws USSDBlockingException, USSDNonBlockingException {

		BillersListDO biller = new BillersListDO();
		if(null != userInput){
			biller = this.billersLstService.getBillerInfo(ussdSessionMgmt.getCountryCode(),	"SP"+userInput.substring(2, 5)+"-8",
					ussdSessionMgmt.getMsisdnNumber(), ussdSessionMgmt.getBusinessId());
		}

		if(null != biller && null != biller.getRefNoValidation_1()){
			Pattern p = Pattern.compile(biller.getRefNoValidation_1());
			Matcher m = p.matcher(userInput);
			if(!m.matches()){
				LOGGER.error("Invalid Control Number Length: " + userInput);
				USSDNonBlockingException e = new USSDNonBlockingException(USSDExceptions.USSD_INVALID_CONTROL_NO.getUssdErrorCode());
				e.addErrorParam(userInput);
				e.setBackFlow(true);
				throw e;
			}
		} else if(null == biller){
			LOGGER.error("No Biller Present: " + userInput);
			USSDNonBlockingException e = new USSDNonBlockingException(USSDExceptions.USSD_INVALID_CONTROL_NO.getUssdErrorCode());
			e.addErrorParam(userInput);
			e.setBackFlow(true);
			throw e;
		}

		if(null != ussdSessionMgmt.getTxSessions()){
			ussdSessionMgmt.getTxSessions().put(USSDConstants.GEPG_BILLER_INFO, biller);
		}else {
			Map<String, Object> txSessions = new HashMap<String, Object>(5);
			txSessions.put(USSDConstants.GEPG_BILLER_INFO, biller);
			ussdSessionMgmt.setTxSessions(txSessions);
		}
	}

}