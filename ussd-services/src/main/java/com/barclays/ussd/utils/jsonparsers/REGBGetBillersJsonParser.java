package com.barclays.ussd.utils.jsonparsers;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.BillersListDO;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

public class REGBGetBillersJsonParser implements BmgBaseJsonParser, ScreenSequenceCustomizer {

    private final static Logger LOGGER = Logger.getLogger(REGBGetBillersJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	try {
	    List<BillersListDO> billerList = (List<BillersListDO>) ussdSessionMgmt.getTxSessions().get(
		    USSDInputParamsEnum.REG_BILLER_GET_BILLERS.getTranId());
	    menuDTO = renderMenuOnScreen(billerList, responseBuilderParamsDTO);
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

    /**
     * @param blrsLstDO
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(List<BillersListDO> blrsLstDO, ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	int index = 1;
	StringBuilder pageBody = new StringBuilder();
	MenuItemDTO menuItemDTO = new MenuItemDTO();

	for (BillersListDO ele : blrsLstDO) {
<<<<<<< HEAD
		//if(null!=ele && !ele.getBillerCategoryId().equalsIgnoreCase("NAPSA") && !ele.getBillerCategoryId().equalsIgnoreCase("ZRA"))
=======
		if(null!=ele && !ele.getBillerCategoryId().equalsIgnoreCase("NAPSA") && !ele.getBillerCategoryId().equalsIgnoreCase("ZRA"))
>>>>>>> ef608883eb015adad2219ef6b09065b5c5ad1488
		{
	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(index);
	    pageBody.append(USSDConstants.DOT_SEPERATOR);
	    pageBody.append(ele.getBillerNm());
	    index++;
		}
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

    public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	int seqId = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo();
	String businessId = ussdSessionMgmt.getUserProfile().getBusinessId();
	if (StringUtils.equalsIgnoreCase(USSDConstants.BUSINESS_ID_UGBRB, businessId)) {
	    List<BillersListDO> blrsLstDO = (List<BillersListDO>) ussdSessionMgmt.getTxSessions().get(
		    USSDInputParamsEnum.REG_BILLER_GET_BILLERS.getTranId());
	    BillersListDO billersListDO = blrsLstDO.get(Integer.parseInt(userInput) - 1);
	    if (StringUtils.equalsIgnoreCase(billersListDO.getBillerId(), "NWSC-4")) {
		seqId = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo();
	    }
	}

	//CR-57
	if (StringUtils.equalsIgnoreCase(USSDConstants.BUSINESS_ID_ZWBRB, businessId)) {
	    List<BillersListDO> blrsLstDO = (List<BillersListDO>) ussdSessionMgmt.getTxSessions().get(
		    USSDInputParamsEnum.REG_BILLER_GET_BILLERS.getTranId());
	    BillersListDO billersListDO = blrsLstDO.get(Integer.parseInt(userInput) - 1);
	    if (StringUtils.equalsIgnoreCase(billersListDO.getBillerId(), "DSTVZIM-2")) {
		seqId = USSDSequenceNumberEnum.SEQUENCE_NUMBER_NINE.getSequenceNo();
	    }
	}
	return seqId;
    }
}