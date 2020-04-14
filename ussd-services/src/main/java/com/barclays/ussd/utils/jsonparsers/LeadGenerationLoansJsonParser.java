package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.dto.LeadGenProductDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.services.dao.impl.UssdBranchLocatorLookUpDAOImpl;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

public class LeadGenerationLoansJsonParser implements BmgBaseJsonParser {

	private static final Logger LOGGER = Logger.getLogger(LeadGenerationLoansJsonParser.class);
	private final static String APPLY_FOR_SUB_PRODUCT = "menu.lead.generation.sub.product";


	@Autowired
	UssdBranchLocatorLookUpDAOImpl ussdBranchLocatorLookUpDAOImpl;
	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {

		List<LeadGenProductDTO> leadGenProdDTOList = null;
		List<String> leadSubProdList = new ArrayList<String>();
		LinkedHashMap<String,String> prodSubProdNameMap=new LinkedHashMap<String,String>();
		MenuItemDTO menuDTO = null;
		Map<String, Object> txSessions = new HashMap<String, Object>();
		String selProductName="";
		String language= responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getLanguage();
		if(language.equalsIgnoreCase("EN"))
			selProductName="Loans";
		else
			selProductName="Mikopo";
		leadGenProdDTOList = ussdBranchLocatorLookUpDAOImpl.getLeadProductSubList(responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId(),selProductName,responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getLanguage());
		if(leadGenProdDTOList!=null) {
			leadSubProdList=new ArrayList<String>();
			for (LeadGenProductDTO leadGenerationDTO : leadGenProdDTOList) {
				leadSubProdList.add(leadGenerationDTO.getSubProductName());
				prodSubProdNameMap.put(leadGenerationDTO.getSubProductName(), leadGenerationDTO.getProductName());
			}
		}
		if(null != responseBuilderParamsDTO && null != leadSubProdList)
			menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, leadSubProdList, "");
		txSessions.put(USSDInputParamsEnum.LEAD_GENERATION_LOANS.getTranId(), leadSubProdList);
		txSessions.put("prodSubProdNameMap", prodSubProdNameMap);
		if(null != responseBuilderParamsDTO)
			responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);

		return menuDTO;
	}
	private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, List<String> leadSubProdList,
			String warningMsg) {
		StringBuilder pageBody = new StringBuilder();
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		int index = 1;
		for (String subProdName : leadSubProdList) {
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(index++);
			pageBody.append(USSDConstants.DOT_SEPERATOR);
			pageBody.append(subProdName);
		}
		menuItemDTO.setPageBody(pageBody.toString());
		USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		menuItemDTO.setPageFooter(menuItemDTO.getPageFooter() + warningMsg);
		menuItemDTO.setPageHeader(APPLY_FOR_SUB_PRODUCT);
		menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
		menuItemDTO.setPaginationType(PaginationEnum.LISTED);
		setNextScreenSequenceNumber(menuItemDTO);
		return menuItemDTO;
	}
	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());

	}
}