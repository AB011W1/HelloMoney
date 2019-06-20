package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.SystemParameterServiceResponse;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.dto.UssdBranchLookUpDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.services.dao.impl.UssdBranchLookUpDAOImpl;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

public class GhipsOneOffBnkSearchJsonParser implements BmgBaseJsonParser,ScreenSequenceCustomizer {
	@Autowired
	UssdBranchLookUpDAOImpl branchLookUpDAOImpl;
	@Autowired
	SystemParameterService systemParameterService;

	@Override
	public MenuItemDTO parseJsonIntoJava(
			ResponseBuilderParamsDTO responseBuilderParamsDTO)
	throws USSDBlockingException, USSDNonBlockingException {
		MenuItemDTO menuDTO = null;
		menuDTO = renderMenuOnScreen(responseBuilderParamsDTO);
		return menuDTO;
	}

	private MenuItemDTO renderMenuOnScreen(
			ResponseBuilderParamsDTO responseBuilderParamsDTO) {
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
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());
	}

	public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {

		SystemParameterDTO systemParameterDTO = new SystemParameterDTO();
		SystemParameterServiceRequest systemParameterServiceRequest = new SystemParameterServiceRequest();
		systemParameterServiceRequest.setSystemParameterDTO(systemParameterDTO);
		systemParameterDTO.setBusinessId(BMBContextHolder.getContext().getBusinessId().toString());
		systemParameterDTO.setSystemId("UB");
		systemParameterDTO.setParameterId("isGHIPS2Flag");
		String isGHIPS2Flag="";
		SystemParameterServiceResponse response = systemParameterService.getStatusParameter(systemParameterServiceRequest);
		if(response!=null && response.getSystemParameterDTO()!=null && response.getSystemParameterDTO().getParameterValue()!=null)
			isGHIPS2Flag = response.getSystemParameterDTO().getParameterValue();

		StringBuffer bankCodeLetter = new StringBuffer();
		int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo();
		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
		List<UssdBranchLookUpDTO> bankList=new ArrayList<UssdBranchLookUpDTO>();
		if(isGHIPS2Flag.equals("Y") && userInput.matches("[0-9]+")){

			systemParameterDTO.setParameterId("bankCodeGH");
			String bankCodeGH="";
			SystemParameterServiceResponse resp = systemParameterService.getStatusParameter(systemParameterServiceRequest);
			bankCodeGH=resp.getSystemParameterDTO().getParameterValue();
			String[] codeValue = bankCodeGH.split(",");
			for(int i=0; i<codeValue.length; i++)
			{
				bankCodeLetter.append(codeValue[i]+userInput);
				bankList = branchLookUpDAOImpl.getBankListGHIPS(bankCodeLetter.toString());
				if(bankList.size()>0)
				{
					break;
				}
				else
				{
					bankCodeLetter = new StringBuffer();
				}
			}
		}
		else {
			bankCodeLetter.append(userInput);
			bankList = branchLookUpDAOImpl.getBankListGHIPS(bankCodeLetter.toString());
		}
		// if (bankList != null && bankList.size() != 0) {
		Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();
		if (txSessions == null) {
			txSessions = new HashMap<String, Object>(5);
			ussdSessionMgmt.setTxSessions(txSessions);
		}
		txSessions.put(USSDInputParamsEnum.GHIPS_BANK_LIST.getTranId(), bankList);
		// }

		if (bankList != null && bankList.size() == 1) {
			userInputMap = userInputMap == null ? new HashMap<String, String>() : userInputMap;
			userInputMap.put(USSDInputParamsEnum.GHIPS_BANK_LIST.getParamName(), USSDConstants.DEFAULT_OPTION_SELECTION);
			ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
			// seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo();
		}
		return seqNo;
	}

}
