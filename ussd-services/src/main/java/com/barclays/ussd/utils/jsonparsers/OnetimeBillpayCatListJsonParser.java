package com.barclays.ussd.utils.jsonparsers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.BillersListDO;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.bmg.paybills.request.OneTimeCheckBillPayBillersJsonParser;
import com.barclays.ussd.common.services.IBillersLstService;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

public class OnetimeBillpayCatListJsonParser implements BmgBaseJsonParser , ScreenSequenceCustomizer
{
	@Autowired
	IBillersLstService blrsLstService;
	private static final Logger LOGGER = Logger.getLogger(OneTimeCheckBillPayBillersJsonParser.class);

	@Override
	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
		USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		String mobileNo=responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getMsisdn();
		List<BillersListDO> catList=blrsLstService.getCategoryList(ussdSessionMgmt.getUserProfile().getCountryCode(),mobileNo,responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId());
		if (ussdSessionMgmt.getTxSessions() == null) {
			ussdSessionMgmt.setTxSessions(new HashMap<String, Object>());
		}
		ussdSessionMgmt.getTxSessions().put(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_CATLIST.getTranId(), catList);
		setNextScreenSequenceNumber(menuItemDTO);
		int index = 1;
		StringBuilder pageBody = new StringBuilder();
		for (BillersListDO ele : catList) {
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(index);
			pageBody.append(USSDConstants.DOT_SEPERATOR);
			pageBody.append(ele.getBillerCategoryNm());
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
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ONE.getSequenceNo());
	}
	//TZNBC Menu Optimization
	@SuppressWarnings("unchecked")
	public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
    	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_ONE.getSequenceNo();
    	
    			String selectedCategory="";
    			BillersListDO lisCategory= null;
				List<BillersListDO> categoryLstDO = (List<BillersListDO>) ussdSessionMgmt.getTxSessions().get(
    					USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_CATLIST.getTranId());
				if(null!=categoryLstDO) {
					lisCategory = categoryLstDO.get(Integer.parseInt(userInput) - 1);
					selectedCategory = lisCategory.getBillerCategoryId();
    				if(null !=selectedCategory && selectedCategory.equalsIgnoreCase("GovernmentPayments"))
    					seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWENTYFOUR.getSequenceNo();
				}	
    	return seqNo;
	}
}