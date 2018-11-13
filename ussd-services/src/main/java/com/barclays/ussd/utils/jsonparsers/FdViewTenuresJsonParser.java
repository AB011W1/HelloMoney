package com.barclays.ussd.utils.jsonparsers;

import java.util.List;
import org.apache.log4j.Logger;

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

public class FdViewTenuresJsonParser implements BmgBaseJsonParser {
    private static final Logger LOGGER = Logger.getLogger(FdViewTenuresJsonParser.class);

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {

	List<String> tenures = (List<String>) responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
		.get(USSDInputParamsEnum.FD_VIEW_TENURE.getTranId());
	return renderMenuOnScreen(responseBuilderParamsDTO, tenures);
    }

    /**
     * 
     * @param responseBuilderParamsDTO
     * @param tenures
     * @param tenureIndex
     * @return
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, List<String> tenures) throws USSDNonBlockingException {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	StringBuilder pageBody = new StringBuilder();
	int index = 1;
	pageBody.append(USSDConstants.NEW_LINE);
	if (tenures != null && !tenures.isEmpty()) {
	    for (String tenure : tenures) {
		pageBody.append(index++).append(USSDConstants.DOT_SEPERATOR).append(tenure).append(USSDConstants.NEW_LINE);
	    }
	    menuItemDTO.setPageBody(pageBody.toString());
	} else {
	    throw new USSDNonBlockingException(USSDExceptions.FD_RATES_NO_TENURE.getUssdErrorCode());
	}
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    /**
     * Build a map for storing tenure index and the list of productDTO in case of multiple tenures.
     * 
     * @param responseBuilderParamsDTO
     * @param payData
     * @return
     */

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo());

    }
}