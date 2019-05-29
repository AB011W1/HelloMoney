/**
 * DelBillrPayeeLstParser.java
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.List;

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
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Beneficiery;

/**
 * @author BTCI
 *
 */
public class DelBillrPayeeLstParser implements BmgBaseJsonParser {

    private static final Logger LOGGER = Logger.getLogger(DelBillrPayeeLstParser.class);

    /*
     * (non-Javadoc)
     *
     * @see com.barclays.ussd.utils.BmgBaseJsonParser#parseJsonIntoJava(com.barclays .ussd.bmg.dto.ResponseBuilderParamsDTO)
     */
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	try {
	    List<Beneficiery> benfList = (List<Beneficiery>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.DEL_BILLERS_LIST.getTranId());
	    menuDTO = renderMenuOnScreen(benfList, responseBuilderParamsDTO);
	} catch (Exception e) {
	    LOGGER.error("Exception : ", e);
/*	    if (e instanceof USSDNonBlockingException) {
		throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
	    } else {*/
		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    //}
	}
	return menuDTO;
    }

    /**
     * @param billerLstData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(List<Beneficiery> benfList, ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	int index = 1;
	StringBuilder pageBody = new StringBuilder();
	MenuItemDTO menuItemDTO = new MenuItemDTO();

	if (benfList != null) {

	    for (Beneficiery bene : benfList) {
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(index);
		pageBody.append(USSDConstants.DOT_SEPERATOR);
		pageBody.append(bene.getDisLbl());
		index++;
	    }
	    menuItemDTO.setPageBody(pageBody.toString());

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
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo());

    }
}
