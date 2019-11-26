package com.barclays.ussd.utils.jsonparsers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;

public class REGBGetBillersCheckJsonParser implements BmgBaseJsonParser
// , ScreenSequenceCustomizer
{

    private final static Logger LOGGER = Logger.getLogger(REGBGetBillersCheckJsonParser.class);

    @Autowired
    IBillersLstService billersLstService;

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = new MenuItemDTO();
	int index = 0;
	String mobileNo=responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getMsisdn();
	List<BillersListDO> blrsLstDO = billersLstService.getBillersList(responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile()
		.getCountryCode(),mobileNo,responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId());
	if (blrsLstDO.isEmpty()) {
	    LOGGER.error(USSDExceptions.USSD_RB_BLRS_NOT_PRESENT.getUssdErrorCode() + ":" + USSDConstants.BILLERS_LIST_NOT_EXIST);
	    throw new USSDNonBlockingException(USSDExceptions.USSD_RB_BLRS_NOT_PRESENT.getUssdErrorCode(), USSDConstants.BILLERS_LIST_NOT_EXIST);
	}
	if (responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions() == null) {
	    Map<String, Object> txSessionMap = new HashMap<String, Object>(5);
	    responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessionMap);
	}
    
	LOGGER.debug(blrsLstDO.size());
	Iterator<BillersListDO> itr = blrsLstDO.iterator();
    while (itr.hasNext()) {
    	BillersListDO BillerValue = itr.next();
    	if(null!=BillerValue && (BillerValue.getBillerCategoryId().equalsIgnoreCase("NAPSA") || BillerValue.getBillerCategoryId().equalsIgnoreCase("ZRA"))) 
    	{
            itr.remove();
        }
    }
    LOGGER.debug(blrsLstDO.size());
    
	responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.REG_BILLER_GET_BILLERS.getTranId(), blrsLstDO);
	setNextScreenSequenceNumber(menuDTO);
	return menuDTO;
    }

    /**
     * @param blrsLstDO
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());
    }

    public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	List<BillersListDO> billerList = (List<BillersListDO>) ussdSessionMgmt.getTxSessions().get(
		USSDInputParamsEnum.REG_BILLER_GET_BILLERS.getTranId());
	if (billerList != null && billerList.size() == 1) {
	    userInputMap = userInputMap == null ? new HashMap<String, String>() : userInputMap;
	    userInputMap.put(USSDInputParamsEnum.REG_BILLER_GET_BILLERS.getParamName(), USSDConstants.DEFAULT_OPTION_SELECTION);
	    ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
	    seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo();

	    BillersListDO billersListDO = billerList.get(0);
	    if (StringUtils.equalsIgnoreCase(billersListDO.getBillerId(), "NWSC-4")) {
		seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo();
	    }
	}
	return seqNo;
    }
}
