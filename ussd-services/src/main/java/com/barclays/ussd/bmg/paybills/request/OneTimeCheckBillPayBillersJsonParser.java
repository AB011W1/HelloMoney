package com.barclays.ussd.bmg.paybills.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.BillersListDO;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.common.services.IBillersLstService;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.jsonparsers.OBADBRetrieveBenfAccountListJsonParser;

public class OneTimeCheckBillPayBillersJsonParser implements BmgBaseJsonParser
// , ScreenSequenceCustomizer
{
	@Autowired
	IBillersLstService blrsLstService;
	private static final Logger LOGGER = Logger.getLogger(OneTimeCheckBillPayBillersJsonParser.class);

	@Override
	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
		USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		String mobileNo=responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getMsisdn();
		if (ussdSessionMgmt.getTxSessions() == null) {
			ussdSessionMgmt.setTxSessions(new HashMap<String, Object>());
		}
		//TZNBC Menu Optimization - to fetch billers based on category selected
		String category="";
		if (ussdSessionMgmt.getBusinessId().equalsIgnoreCase("TZNBC")) {
			Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
			List<BillersListDO> categoryLstDO = (List<BillersListDO>) ussdSessionMgmt.getTxSessions().get(
					USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_CATLIST.getTranId());
			if(null!=categoryLstDO) { 
				BillersListDO categoryListDO = categoryLstDO.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_CATLIST
						.getParamName())) - 1);
				category= categoryListDO.getBillerCategoryId();
			}
		}
		List<BillersListDO> blrsLst ;
		if(ussdSessionMgmt.getBusinessId().equalsIgnoreCase("TZNBC"))
			blrsLst = blrsLstService.getBillerPerCategory(mobileNo, responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId(), category);
		else	
			blrsLst = blrsLstService.getBillersList(ussdSessionMgmt.getUserProfile().getCountryCode(),mobileNo,responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId());

		ussdSessionMgmt.getTxSessions().put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_BLRS_LST.getTranId(), blrsLst);
		setNextScreenSequenceNumber(menuItemDTO);
		return menuItemDTO;

	}

	public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
		int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo();
		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
		List<BillersListDO> billerList = (List<BillersListDO>) ussdSessionMgmt.getTxSessions().get(
				USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_BLRS_LST.getTranId());
		if (billerList != null && billerList.size() == 1) {
			userInputMap = userInputMap == null ? new HashMap<String, String>() : userInputMap;
			userInputMap.put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_BLRS_LST.getParamName(), USSDConstants.DEFAULT_OPTION_SELECTION);
			ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo();

			BillersListDO billersListDO = billerList.get(0);
			if (StringUtils.equalsIgnoreCase(billersListDO.getBillerId(), "NWSC-4")) {
				seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo();
			}
		}
		return seqNo;
	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());
	}
}