package com.barclays.ussd.bmg.registerbenf.external;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

public class RegisterBenfExtGetBenfNameJsonParser implements BmgBaseJsonParser,ScreenSequenceCustomizer {
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuDTO = null;
	menuDTO = renderMenuOnScreen(responseBuilderParamsDTO);
	return menuDTO;
    }

    /**
     * @param payData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.NOT_REQD);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	// Refer to sequencer.properties to set the below value
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SEVEN.getSequenceNo());
    }

	@Override
	public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt)
			throws USSDBlockingException {
		// TODO Auto-generated method stub
		String businessId = ussdSessionMgmt.getBusinessId();
		int seqNo=USSDSequenceNumberEnum.SEQUENCE_NUMBER_SEVEN.getSequenceNo();
		String transNodeId=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
		if(businessId.equalsIgnoreCase("ZMBRB") && transNodeId.equals("ussd4.3.3.2") || 
				businessId.equalsIgnoreCase("BWBRB") && transNodeId.equals("ussd0.3.3.2") || 
				businessId.equalsIgnoreCase("TZBRB") && transNodeId.equals("ussd0.3.3.2")) {
			seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_NINE.getSequenceNo();
		}
		
		return seqNo;
	}

}
