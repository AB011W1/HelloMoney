package com.barclays.ussd.bmg.atmlocator;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.dto.UssdBranchLocatorLookUpDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.services.dao.impl.UssdAtmLocatorLookUpDAOImpl;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

public class AtmLocatorAreaNameListJsonParser implements BmgBaseJsonParser {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(AtmLocatorAreaNameListJsonParser.class);
    @Autowired
    UssdAtmLocatorLookUpDAOImpl ussdAtmLocatorLookUpDAOImpl;

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	List<UssdBranchLocatorLookUpDTO> areaList = null;
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	MenuItemDTO menuDTO = null;
	try {
	    Map<String, String> userInputMap = responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	    String areaLetter = userInputMap.get(USSDInputParamsEnum.ATM_LOCATOR_AREA_NAME.getParamName());
	    if (areaLetter.trim().equals("1")) {
		areaLetter = null;
	    }

	    List<UssdBranchLocatorLookUpDTO> cityNameList = (List<UssdBranchLocatorLookUpDTO>) ussdSessionMgmt.getTxSessions().get(
		    USSDInputParamsEnum.ATM_LOCATOR_CITY_LIST.getTranId());

	    UssdBranchLocatorLookUpDTO selectedCityName = cityNameList.get(Integer.parseInt(userInputMap
		    .get(USSDInputParamsEnum.ATM_LOCATOR_CITY_LIST.getParamName())) - 1);

	    areaList = ussdAtmLocatorLookUpDAOImpl.getAreaList(responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId(), selectedCityName
		    .getCityName(), areaLetter);
	    if (areaList != null && areaList.size() != 0) {
		menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, areaList, "");

		Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
		if (txSessions == null) {
		    txSessions = new HashMap<String, Object>(8);
		    responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
		}
		txSessions.put(USSDInputParamsEnum.ATM_LOCATOR_AREA_LIST.getTranId(), areaList);
		txSessions.put(USSDInputParamsEnum.SELECTED_CITY_NAME.getParamName(), selectedCityName.getCityName());
	    } else {
		LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		throw new USSDNonBlockingException(USSDExceptions.USSD_NO_CITY_FOUND.getBmgCode());
	    }
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
     * @param payData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, List<UssdBranchLocatorLookUpDTO> areaList,
	    String warningMsg) {
	StringBuilder pageBody = new StringBuilder();
	Collections.sort(areaList, new Comparator<UssdBranchLocatorLookUpDTO>() {
	    @Override
	    public int compare(UssdBranchLocatorLookUpDTO b1, UssdBranchLocatorLookUpDTO b2) {
		return b1.getBranchName().compareTo(b2.getBranchName());
	    }
	});
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	int index = 1;
	for (UssdBranchLocatorLookUpDTO branchLookUpDTO : areaList) {
	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(index++);
	    pageBody.append(USSDConstants.DOT_SEPERATOR);
	    pageBody.append(branchLookUpDTO.getBranchName());
	}
	menuItemDTO.setPageBody(pageBody.toString());
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageFooter(menuItemDTO.getPageFooter() + warningMsg);
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
