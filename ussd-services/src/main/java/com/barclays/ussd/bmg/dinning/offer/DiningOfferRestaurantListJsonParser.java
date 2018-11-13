package com.barclays.ussd.bmg.dinning.offer;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.branchlocator.BranchLocatorAreaNameListJsonParser;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.dto.UssdOfferLookUpDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.services.dao.impl.UssdBarclaysOfferLookUpDAOImpl;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

public class DiningOfferRestaurantListJsonParser implements BmgBaseJsonParser {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(BranchLocatorAreaNameListJsonParser.class);

    @Autowired
    UssdBarclaysOfferLookUpDAOImpl ussdDiningOfferLookUpDAOImpl;

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {

	List<UssdOfferLookUpDTO> restaurantList = null;
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	MenuItemDTO menuDTO = null;
	try {
	    Map<String, String> userInputMap = responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	    String restarantLetter = userInputMap.get(USSDInputParamsEnum.DINING_OFFER_RESTAURANT_LETTER.getParamName());
	    if (restarantLetter.trim().equals("1")) {
		restarantLetter = null;
	    }

	    List<UssdOfferLookUpDTO> cityNameList = (List<UssdOfferLookUpDTO>) ussdSessionMgmt.getTxSessions().get(
		    USSDInputParamsEnum.DINING_OFFER_CITY_LIST.getTranId());

	    UssdOfferLookUpDTO selectedCityName = cityNameList.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.DINING_OFFER_CITY_LIST
		    .getParamName())) - 1);

	    restaurantList = ussdDiningOfferLookUpDAOImpl.getRestaurantList(responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId(),
		    selectedCityName.getCityName(), restarantLetter);
	    if (restaurantList != null && restaurantList.size() != 0) {
		menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, restaurantList, "");

		Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
		if (txSessions == null) {
		    txSessions = new HashMap<String, Object>(8);
		    responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
		}
		txSessions.put(USSDInputParamsEnum.DINING_OFFER_RESTAURANT_LIST.getTranId(), restaurantList);
	    } else {
		LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		throw new USSDNonBlockingException(USSDExceptions.USSD_NO_RESTARANT_AVAILABLE.getBmgCode());
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
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, List<UssdOfferLookUpDTO> areaList, String warningMsg) {
	StringBuilder pageBody = new StringBuilder();
	Collections.sort(areaList, new Comparator<UssdOfferLookUpDTO>() {
	    @Override
	    public int compare(UssdOfferLookUpDTO b1, UssdOfferLookUpDTO b2) {
		return b1.getRestaurentName().compareTo(b2.getRestaurentName());
	    }
	});
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	int index = 1;
	for (UssdOfferLookUpDTO branchLookUpDTO : areaList) {
	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(index++);
	    pageBody.append(USSDConstants.DOT_SEPERATOR);
	    pageBody.append(branchLookUpDTO.getRestaurentName());
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