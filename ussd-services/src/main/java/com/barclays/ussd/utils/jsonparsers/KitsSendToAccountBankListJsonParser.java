package com.barclays.ussd.utils.jsonparsers;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.auth.bean.UserProfile;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.services.dao.impl.UssdBranchLookUpDAOImpl;
import com.barclays.ussd.sysprefs.services.ListValueCacheDTO;
import com.barclays.ussd.sysprefs.services.ListValueResByGroupServiceResponse;
import com.barclays.ussd.sysprefs.services.ListValueResServiceImpl;
import com.barclays.ussd.sysprefs.services.ListValueResServiceRequest;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.SystemPreferenceConstants;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

public class KitsSendToAccountBankListJsonParser implements BmgBaseJsonParser{


	    @Autowired
	    private ListValueResServiceImpl listValueResService;
	    /** The Constant LOGGER. */
	    private static final Logger LOGGER = Logger.getLogger(KitsSendToAccountBankListJsonParser.class);
	    @Autowired
	    UssdBranchLookUpDAOImpl branchLookUpDAOImpl;


	    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {


	    	MenuItemDTO menuDTO = null;
	    	USSDSessionManagement ussdSessionMgmt =responseBuilderParamsDTO.getUssdSessionMgmt();
	    	//Getting bank code entered by the user
	    	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	    	StringBuffer bankCodeLetter = new StringBuffer();
	    	bankCodeLetter.append(userInputMap.get(USSDInputParamsEnum.KITS_STA_BANK_CODE.getParamName()));

	    	UserProfile userProfile = ussdSessionMgmt.getUserProfile();
	    	List<ListValueCacheDTO> bankDetailsList=getSystemPreferenceData(userProfile, SystemPreferenceConstants.KITS_BANK, null,bankCodeLetter.toString());

	    	Map<String,String> bankDetailsMap= new HashMap<String,String>();

	    	for(ListValueCacheDTO  listValueCacheDTO: bankDetailsList )
	    	{
	    		bankDetailsMap.put(listValueCacheDTO.getLabel(), listValueCacheDTO.getKey());
	    	}
	    	try {
	    		Set<String> keySet=bankDetailsMap.keySet();
		    	Collection<String> valueSet=bankDetailsMap.values();
		    	List<String> keyList=new ArrayList<String>(keySet);
		    	Collections.sort(keyList);

	    		if (bankDetailsMap.size() != 0) {
	    			menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, bankDetailsMap);

	    			Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
	    			if (txSessions == null) {
	    				txSessions = new HashMap<String, Object>(8);
	    			}
	    			txSessions.put(USSDInputParamsEnum.KITS_STA_BANK_CODE_LIST.getTranId(), keyList);
	    			txSessions.put("BankDeatilsMap", bankDetailsMap);
	    			responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);

	    		} else {
	    			LOGGER.error("Error while servicing USSDExceptions.USSD_BANK_CODE_LIST_INVALID.getBmgCode()");
	    			throw new USSDNonBlockingException(USSDExceptions.USSD_BANK_CODE_LIST_INVALID.getBmgCode());
	    		}
	    	} catch (Exception e) {
	    		    LOGGER.error("Exception : ", e);
	    		if (e instanceof USSDNonBlockingException) {
	    			throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
	    		} else {
	    			throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    		}
	    	}



	    	return menuDTO;
	    }

	    /**
	     * @param payData
	     * @param responseBuilderParamsDTO
	     * @return MenuItemDTO
	     */
	    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, Map<String, String> bankDetailsMap) {
	    	StringBuilder pageBody = new StringBuilder();
	    	MenuItemDTO menuItemDTO = new MenuItemDTO();
	    	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	    	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	    	int index = 1;

	    	Set<String> keySet=bankDetailsMap.keySet();
	    	Collection<String> valueSet=bankDetailsMap.values();
	    	List<String> keyList=new ArrayList<String>(keySet);
	    	Collections.sort(keyList);

	        for (Iterator i = keyList.iterator(); i.hasNext();) {
	        	pageBody.append(USSDConstants.NEW_LINE);
	    		pageBody.append(index++);
	    		pageBody.append(USSDConstants.DOT_SEPERATOR);
	    		pageBody.append(i.next());
	        }
	        menuItemDTO.setPageBody(pageBody.toString());
	    	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	    	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	    	setNextScreenSequenceNumber(menuItemDTO);
	    	return menuItemDTO;
	    }

	    @Override
	    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo());
	    }

	    private List<ListValueCacheDTO> getSystemPreferenceData(UserProfile userProfile, String groupId, String listValueKey, String bankCodeLetter) throws USSDNonBlockingException {
	    	ListValueResServiceRequest listValReq = new ListValueResServiceRequest(userProfile.getCountryCode(), groupId, userProfile.getLanguage(),listValueKey);
	    	ListValueResByGroupServiceResponse listValueByGroup = listValueResService.getListValueByGroupKits(listValReq,bankCodeLetter );
	    	List <ListValueCacheDTO> listValueCacheDTO = listValueByGroup.getListValueCahceDTO();
	    	if (listValueCacheDTO == null) {
	    	    LOGGER.fatal("System preferences not set for" + listValReq.getListValueKey());
	    	    throw new USSDNonBlockingException(USSDExceptions.USSD_BANK_CODE_LIST_INVALID.getBmgCode(), USSDExceptions.USSD_BANK_CODE_LIST_INVALID
	    		    .getUssdErrorCode());
	    	}
	    	return listValueCacheDTO;
	        }








}





