package com.barclays.ussd.utils.jsonparsers;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.UserProfile;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.sysprefs.services.ListValueCacheDTO;
import com.barclays.ussd.sysprefs.services.ListValueResByGroupServiceResponse;
import com.barclays.ussd.sysprefs.services.ListValueResServiceImpl;
import com.barclays.ussd.sysprefs.services.ListValueResServiceRequest;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

public class FxRateGetCurrencyJsonParser implements BmgBaseJsonParser {

    @Autowired
    ListValueResServiceImpl listValueResService;
    private static final String CURR_GROUP_ID = "FX_RATE_CURR";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(FxRateGetCurrencyJsonParser.class);

    public void setListValueResService(ListValueResServiceImpl listValueResService) {
	this.listValueResService = listValueResService;
    }

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDBlockingException, USSDNonBlockingException {
	return renderMenuOnScreen(responseBuilderParamsDTO, getCurrencyList(responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile(),
		CURR_GROUP_ID, CURR_GROUP_ID));

    }

    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, List<ListValueCacheDTO> list) {
	if (responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions() == null) {
	    responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(new HashMap<String, Object>());
	}
	responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.FX_RATE_GET_CURR.getTranId(), list);

	MenuItemDTO menuItemDTO = new MenuItemDTO();
	int index = 1;
	StringBuilder pageBody = new StringBuilder();

	for (ListValueCacheDTO listValueCacheDTO : list) {
	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(index++);
	    pageBody.append(USSDConstants.DOT_SEPERATOR);
	    pageBody.append(listValueCacheDTO.getLabel());
	}
	menuItemDTO.setPageBody(pageBody.toString());
	// insert the back and home options
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    private List<ListValueCacheDTO> getCurrencyList(UserProfile userProfile, String groupId, String listValueKey) throws USSDNonBlockingException {
	ListValueResServiceRequest listValReq = new ListValueResServiceRequest(userProfile.getCountryCode(), groupId, userProfile.getLanguage(),
		listValueKey);
	ListValueResByGroupServiceResponse listValueByGroup = listValueResService.getListValueByGroup(listValReq);
	List<ListValueCacheDTO> listValueCacheDTO = listValueByGroup.getListValueCahceDTO();
	if (listValueCacheDTO == null) {
	    LOGGER.fatal("System preferences not set for" + listValReq.getListValueKey());
	    throw new USSDNonBlockingException(USSDExceptions.USSD_SYS_PREF_MISSING.getBmgCode(), USSDExceptions.USSD_SYS_PREF_MISSING
		    .getUssdErrorCode());
	}
	return listValueCacheDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());

    }
}