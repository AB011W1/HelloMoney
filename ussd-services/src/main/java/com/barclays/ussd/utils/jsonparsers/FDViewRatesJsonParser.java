package com.barclays.ussd.utils.jsonparsers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

public class FDViewRatesJsonParser implements BmgBaseJsonParser {

    @SuppressWarnings("unchecked")
    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {

	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();

	String selectedTenure = userInputMap.get(USSDInputParamsEnum.FD_VIEW_TENURE.getParamName());
	LinkedHashMap<Integer, List<String>> productDescIntRate = (LinkedHashMap<Integer, List<String>>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.FD_VIEW_TENURE.getParamName());
	List<String> productDescIntRates = productDescIntRate.get(Integer.parseInt(selectedTenure) - 1);
	return renderMenuOnScreen(responseBuilderParamsDTO, productDescIntRates);
    }

    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, List<String> productDescIntRates) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	StringBuilder pageBody = new StringBuilder(USSDConstants.NEW_LINE);

	for (String productDescIntRate : productDescIntRates) {
	    pageBody.append(productDescIntRate).append(USSDConstants.NEW_LINE);
	}
	menuItemDTO.setPageBody(pageBody.toString());

	// insert the back and home options
	//fix for CR-82 screen valodation changes
	responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getCurrentRunningTransaction().setBackOptionReq("true");
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
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
