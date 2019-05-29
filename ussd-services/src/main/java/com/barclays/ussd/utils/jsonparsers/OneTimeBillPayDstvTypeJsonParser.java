package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.log4j.Logger;
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

public class OneTimeBillPayDstvTypeJsonParser implements BmgBaseJsonParser {

	private final static Logger LOGGER = Logger.getLogger(REGBGetBillersJsonParser.class);
	private String DSTV_BO_LABEL = "label.dstv.bo";
	private String DSTV_SUBSCRIPTON_LABEL = "label.dstv.subscription";

	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
		MenuItemDTO menuDTO = null;

		try {
			List<String> dstvBillerList = new ArrayList<String>();
			USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();

			Locale locale = new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode());
			dstvBillerList.add(responseBuilderParamsDTO.getUssdResourceBundle().getLabel(DSTV_BO_LABEL, locale));
			dstvBillerList.add(responseBuilderParamsDTO.getUssdResourceBundle().getLabel(DSTV_SUBSCRIPTON_LABEL, locale));
			responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_DSTVTYPE.getTranId(), dstvBillerList);

			menuDTO = renderMenuOnScreen(dstvBillerList, responseBuilderParamsDTO);
		} catch (Exception e) {
			LOGGER.error("Exception : ", e);
/*			if (e instanceof USSDNonBlockingException) {
				throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
			} else {*/
				throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
			//}
		}
		return menuDTO;
	}

	/**
	 * @param dstvbillerLst
	 * @param responseBuilderParamsDTO
	 * @return MenuItemDTO
	 */

	private MenuItemDTO renderMenuOnScreen(List<String> dstvBillerList, ResponseBuilderParamsDTO responseBuilderParamsDTO) {
		int index = 1;
		StringBuilder pageBody = new StringBuilder();
		MenuItemDTO menuItemDTO = new MenuItemDTO();

		for (String ele : dstvBillerList) {
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(index);
			pageBody.append(USSDConstants.DOT_SEPERATOR);
			pageBody.append(ele);
			index++;

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
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo());
	}
}
