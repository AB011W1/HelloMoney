package com.barclays.ussd.utils.jsonparsers;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.BillersListDO;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.common.services.IBillersLstService;
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

public class OneTimeBillPayBillersJsonParser implements BmgBaseJsonParser, ScreenSequenceCustomizer {

    @Autowired
    IBillersLstService blrsLstService;
    private static final Logger LOGGER = Logger.getLogger(OneTimeBillPayBillersJsonParser.class);

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {

	MenuItemDTO menuDTO = null;
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	try {
	    List<BillersListDO> billerList = (List<BillersListDO>) ussdSessionMgmt.getTxSessions().get(
		    USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_BLRS_LST.getTranId());
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

    private MenuItemDTO renderMenuOnScreen(List<BillersListDO> blrsLst, ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	int index = 1;
	StringBuilder pageBody = new StringBuilder();
	for (BillersListDO ele : blrsLst) {
	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(index);
	    pageBody.append(USSDConstants.DOT_SEPERATOR);
	    pageBody.append(ele.getBillerNm());
	    index++;
	}
	menuItemDTO.setPageBody(pageBody.toString());
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
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
		    USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_BLRS_LST.getTranId());
	    BillersListDO billersListDO = blrsLstDO.get(Integer.parseInt(userInput) - 1);
	    if (StringUtils.equalsIgnoreCase(billersListDO.getBillerId(), "NWSC-4")) {
		seqId = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo();
	    }
	}
	//CR-57
	if (StringUtils.equalsIgnoreCase(USSDConstants.BUSINESS_ID_ZWBRB, businessId)) {
	    List<BillersListDO> blrsLstDO = (List<BillersListDO>) ussdSessionMgmt.getTxSessions().get(
		    USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_BLRS_LST.getTranId());
	    BillersListDO billersListDO = blrsLstDO.get(Integer.parseInt(userInput) - 1);
	    if (StringUtils.equalsIgnoreCase(billersListDO.getBillerId(), "DSTVZIM-2")) {
		seqId = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TEN.getSequenceNo();
	    }
	}
	return seqId;
    }

}
