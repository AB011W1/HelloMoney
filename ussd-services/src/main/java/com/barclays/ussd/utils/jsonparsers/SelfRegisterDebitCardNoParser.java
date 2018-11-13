package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.log4j.Logger;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

public class SelfRegisterDebitCardNoParser implements BmgBaseJsonParser,ScreenSequenceCustomizer {

    private static final Logger LOGGER = Logger.getLogger(SelfRegisterDebitCardNoParser.class);
    private static final String DEBIT_CARD_RAN_DIG_LABEL = "label.selfreg.debitcard.randomdigits";
    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDBlockingException, USSDNonBlockingException {
	// TODO Auto-generated method stub
	return renderMenuOnScreen(responseBuilderParamsDTO);
    }

    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO)  throws USSDNonBlockingException, USSDBlockingException {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	StringBuilder pageBody = new StringBuilder();
	List<Integer> randomPositions=new ArrayList<Integer>();
	try {
		randomPositions = USSDUtils.generateRandomDebitCardPositions();
	} catch (USSDNonBlockingException e) {
		// TODO Auto-generated catch block
		
	}

	String[] paramArray = appendSuffix(randomPositions);
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	String debitCardLabel = responseBuilderParamsDTO.getUssdResourceBundle().getLabel(DEBIT_CARD_RAN_DIG_LABEL, paramArray,
		new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));
	//	ADDED TO INCLUDE DEBIT CARD VALIDATION IN SELF REGISTRATION
    Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
if (txSessions == null) {
    txSessions = new HashMap<String, Object>(8);
    responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
}
txSessions.put(USSDInputParamsEnum.SELFREG_DEBITCARD_EXPIRYDATE.getTranId(), randomPositions);

	pageBody.append(debitCardLabel);

	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.NOT_REQD);
	menuItemDTO.setPageBody(pageBody.toString());
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }
    public String[] appendSuffix(List<Integer> randomPositions){
    	List<String> finalStringList=new ArrayList<String>();
    	for(Integer no1:randomPositions){
    	int no=no1.intValue();
    	String[]suffix={"st","nd","rd","th"};

    	switch(no){
		case 1:finalStringList.add(no+suffix[0]);break;
		case 2:finalStringList.add(no+suffix[1]);break;
		case 3:finalStringList.add(no+suffix[2]);break;
		default:
			finalStringList.add(no+suffix[3]);break;
		}
    	}
    	return finalStringList.toArray(new String[randomPositions.size()]);
    }
    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_NINE.getSequenceNo());

    }

	@Override
	public int getCustomNextScreen(String userInput,
			USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
		int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_NINE.getSequenceNo();
		String tranDataId=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranNodeId();
		if(tranDataId !=null && tranDataId.equalsIgnoreCase("ussd3.00")){
			seqNo=USSDSequenceNumberEnum.SEQUENCE_NUMBER_TEN.getSequenceNo();
		}
		return seqNo;
	}

}
