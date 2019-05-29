package com.barclays.ussd.utils.jsonparsers;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.services.dao.impl.UssdBranchLookUpDAOImpl;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.SystemPreferenceValidator;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.BankJsonRes;
import com.barclays.ussd.utils.jsonparsers.bean.pesalink.SearchIndividualCustInformationResponse;

public class KitsSendToPhoneBankListJsonParser implements BmgBaseJsonParser,SystemPreferenceValidator{


	    @Resource(name = "branchCodeCountryList")
	    private List<String> branchCodeCountryList;

	    /** The Constant LOGGER. */
	    private static final Logger LOGGER = Logger.getLogger(KitsSendToPhoneBankListJsonParser.class);
	    @Autowired
	    UssdBranchLookUpDAOImpl branchLookUpDAOImpl;

	    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	    	MenuItemDTO menuDTO = null;
	    	ObjectMapper mapper = new ObjectMapper();

	    	try{

	    			SearchIndividualCustInformationResponse searchIndividualCustInformationResponse = mapper.readValue(responseBuilderParamsDTO.getJsonString(),
	    					SearchIndividualCustInformationResponse.class);

	    			if (searchIndividualCustInformationResponse != null) {
	    				if (searchIndividualCustInformationResponse.getPayHdr() != null
	    					&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(searchIndividualCustInformationResponse.getPayHdr().getResCde())) {

	    					if (searchIndividualCustInformationResponse.getPayData() != null && searchIndividualCustInformationResponse.getPayData().getBankResList()!=null
	    							&&  !searchIndividualCustInformationResponse.getPayData().getBankResList().isEmpty())
	    					{
	    				    	List<BankJsonRes> bankDetailsList=searchIndividualCustInformationResponse.getPayData().getBankResList();

	    				    	Map<String,String> bankDetailsMap= new HashMap<String,String>();

	    				    	for(BankJsonRes  details: bankDetailsList )
	    				    	{
	    				    		bankDetailsMap.put(details.getBankName(), details.getBankCode());
	    				    	}

	    				    		Set<String> keySet=bankDetailsMap.keySet();
	    					    	List<String> keyList=new ArrayList<String>(keySet);
	    					    	Collections.sort(keyList);

	    				    		if (keyList.size() != 0) {
	    				    			menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, keyList);

	    				    			Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
	    				    			if (txSessions == null) {
	    				    				txSessions = new HashMap<String, Object>(8);
	    				    			}
	    				    			List bankDetailList = new ArrayList();
		    					    	bankDetailList.add(searchIndividualCustInformationResponse.getPayData().getIndividualName());
		    					    	bankDetailList.add(keyList);
	    				    			txSessions.put(USSDInputParamsEnum.KITS_STP_BANK_NAME.getTranId(), bankDetailList);
	    				    			txSessions.put("BankDeatilsMap", bankDetailsMap);
	    				    			responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
	    				    		}

	    					}
	    				}else if (searchIndividualCustInformationResponse.getPayHdr() != null
	    						&& (StringUtils.equalsIgnoreCase(USSDExceptions.USSD_TECH_ISSUE.getBmgCode(), searchIndividualCustInformationResponse.getPayHdr().getResCde()))) {
	    				    throw new USSDBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    				}else if(searchIndividualCustInformationResponse.getPayHdr() != null
	    						&& USSDExceptions.BEMDEREG.getBmgCode().equalsIgnoreCase(searchIndividualCustInformationResponse.getPayHdr().getResCde())){
	    					throw new USSDBlockingException(USSDExceptions.BEMDEREG.getBmgCode());
	    				}
	    				//06/10/2016 G01022861
	    				else if(searchIndividualCustInformationResponse.getPayHdr() != null
	    						&& USSDExceptions.BEM001.getBmgCode().equalsIgnoreCase(searchIndividualCustInformationResponse.getPayHdr().getResCde())){
	    					throw new USSDNonBlockingException(USSDExceptions.BEMRECMOB.getBmgCode(),true);
	    				}
	    				else if (searchIndividualCustInformationResponse.getPayHdr() != null) {
	    				    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
	    				    throw new USSDNonBlockingException(searchIndividualCustInformationResponse.getPayHdr().getResCde(),true);
	    				}
	    				}else {
	    					LOGGER.error("Invalid response got from the BMG " + responseBuilderParamsDTO.getBmgOpCode());
	    					throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode(),true);
	    				    }

	    	}catch(Exception e)
	    	{
	    		LOGGER.error("Exception : ", e);
	    	    if (e instanceof USSDNonBlockingException) {
	    		throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode(),true);
	    	    } else {
	    		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode(),true);
	    	    }
	    	}

	    	return menuDTO;
	        }

	    /**
	     * @param payData
	     * @param responseBuilderParamsDTO
	     * @return MenuItemDTO
	     */
	    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, List<String> branchList) {
	    	StringBuilder pageBody = new StringBuilder();
	    	MenuItemDTO menuItemDTO = new MenuItemDTO();
	    	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	    	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	    	int index = 1;
	    	for (String branchLookUpDTO : branchList) {
	    		pageBody.append(USSDConstants.NEW_LINE);
	    		pageBody.append(index++);
	    		pageBody.append(USSDConstants.DOT_SEPERATOR);
	    		pageBody.append(branchLookUpDTO);
	    	}

	    	menuItemDTO.setPageBody(pageBody.toString());
	    	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	    	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	    	setNextScreenSequenceNumber(menuItemDTO);
	    	return menuItemDTO;
	    }

	    @Override
	    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo());
	    }

	    @Override
	    public void validate(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException, USSDNonBlockingException {
	    	Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();


	    		List banknameList=(List) txSessions.get(USSDInputParamsEnum.KITS_STP_BANK_NAME.getTranId());
	    		if(((List)banknameList.get(1)).size()<Integer.parseInt(userInput))
	    		{USSDNonBlockingException e= new USSDNonBlockingException();
				LOGGER.error(USSDExceptions.USSD_USER_INPUT_INVALID.getUssdErrorCode(), e);
			    e.setErrorCode(USSDExceptions.USSD_USER_INPUT_INVALID.getUssdErrorCode());
			    e.setKitsFlow(true);
			    throw e;

	    		}

	    	}
	    }







