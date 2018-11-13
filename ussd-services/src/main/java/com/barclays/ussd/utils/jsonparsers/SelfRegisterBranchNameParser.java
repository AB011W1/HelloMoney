/**
 *
 */
package com.barclays.ussd.utils.jsonparsers;

import org.apache.log4j.Logger;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

/**
 * @author BTCI
 *
 */
public class SelfRegisterBranchNameParser implements BmgBaseJsonParser,ScreenSequenceCustomizer {

	private static final Logger TIME_AUDIT_LOGGER = Logger.getLogger("com.barclays.ussd.audit-time");

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDBlockingException {

    	// Temporary fix to disable Self registration in case of TZBRB
    	//Commented to enable TZ self reg with debit card
    	/*if (responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId().equalsIgnoreCase(USSDConstants.BUSINESS_ID_TZBRB)) {
    		TIME_AUDIT_LOGGER.debug("user tried to self register");
    	    throw new USSDBlockingException(USSDExceptions.USSD_SELFREGISTRATION_DISABLED.getBmgCode());
    	}*/
    	return renderMenuOnScreen(responseBuilderParamsDTO);
    }

    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.NOT_REQD);
	//Forgot Pin
	/*if(responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getCurrentRunningTransaction().getTranId()!=null)
	menuItemDTO.setTranId(responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId());
	*/setNextScreenSequenceNumber(menuItemDTO);


	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
    	//Forgot Pin
    	/*if(menuItemDTO.getTranId().equalsIgnoreCase("ussd3.00")){
    		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo());
    	}
    	else*/
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());
    }
    @Override
    public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
    	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo();
    	String tranDataId=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
    	if(tranDataId !=null && tranDataId.equalsIgnoreCase("ussd3.00")){
    		seqNo=USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo();
    	}
    	return seqNo;
    }
}
