package com.barclays.ussd.bmg.callmeback;

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
import com.barclays.ussd.dto.BusinessAreaLookUpDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.services.dao.impl.CallMeBackLookUpDAOImpl;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

public class ServiceCategoryListJsonParser implements BmgBaseJsonParser {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(ServiceCategoryListJsonParser.class);

    @Autowired
    CallMeBackLookUpDAOImpl callMeBackLookUpDAOImpl;

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	List<BusinessAreaLookUpDTO> serviceCategoryList = null;
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	MenuItemDTO menuDTO = null;
	try {
	    Map<String, String> userInputMap = responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	    String businessArea = userInputMap.get(USSDInputParamsEnum.CALL_ME_BACK_AREA_LIST.getParamName());

	    List<BusinessAreaLookUpDTO> businessAreaList = (List<BusinessAreaLookUpDTO>) ussdSessionMgmt.getTxSessions().get(
		    USSDInputParamsEnum.CALL_ME_BACK_AREA_LIST.getTranId());

	    BusinessAreaLookUpDTO selectedBusinessAreaName = businessAreaList.get(Integer.parseInt(userInputMap
		    .get(USSDInputParamsEnum.CALL_ME_BACK_AREA_LIST.getParamName())) - 1);

	    serviceCategoryList = callMeBackLookUpDAOImpl.getServiceCategoryList(responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId(),
		    selectedBusinessAreaName.getBusinessAreaName());
	    if (serviceCategoryList != null && serviceCategoryList.size() != 0) {
		menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, serviceCategoryList, "");

		Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
		if (txSessions == null) {
		    txSessions = new HashMap<String, Object>(8);
		    responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
		}
		txSessions.put(USSDInputParamsEnum.CALL_ME_BACK_CATEGORY_LIST.getTranId(), serviceCategoryList);
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
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, List<BusinessAreaLookUpDTO> categoryList,
	    String warningMsg) {
	StringBuilder pageBody = new StringBuilder();
	Collections.sort(categoryList, new Comparator<BusinessAreaLookUpDTO>() {
	    @Override
	    public int compare(BusinessAreaLookUpDTO b1, BusinessAreaLookUpDTO b2) {
		return b1.getServiceCategoryName().compareTo(b2.getServiceCategoryName());
	    }
	});
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	int index = 1;
	for (BusinessAreaLookUpDTO categoryDTO : categoryList) {
	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(index++);
	    pageBody.append(USSDConstants.DOT_SEPERATOR);
	    pageBody.append(categoryDTO.getServiceCategoryName());
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
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo());
    }
}