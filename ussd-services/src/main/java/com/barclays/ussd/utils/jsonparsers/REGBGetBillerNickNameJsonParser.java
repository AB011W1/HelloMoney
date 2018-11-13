package com.barclays.ussd.utils.jsonparsers;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

public class REGBGetBillerNickNameJsonParser implements BmgBaseJsonParser, ScreenSequenceCustomizer {
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) {

	return renderMenuOnScreen(responseBuilderParamsDTO);
    }

    /**
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SEVEN.getSequenceNo());

    }

	@Override
	public int getCustomNextScreen(String userInput,
			USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {

		String isFromSaveBiller = ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_SUBMIT.getTranId())== null?"":(String) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_SUBMIT.getTranId());
		int nextSequence = USSDSequenceNumberEnum.SEQUENCE_NUMBER_SEVEN.getSequenceNo();
		/**
		 * CR 73
		 */
		if(!isFromSaveBiller.equals(StringUtils.EMPTY) && isFromSaveBiller.equals(BillPaymentConstants.ONE_TIME_BILL_PAYMENT_SAVE_BILLER)){
			nextSequence = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWELVE.getSequenceNo();
		}
		return nextSequence;
	}

}
