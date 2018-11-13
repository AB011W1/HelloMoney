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
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

public class LeadGenerationProductSubListJsonParser implements BmgBaseJsonParser {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(ChequeBookBranchListJsonParser.class);
	private final static String APPLY_FOR_SUB_PRODUCT = "menu.lead.generation.sub.product";

	@Autowired
	UssdBranchLocatorLookUpDAOImpl ussdBranchLocatorLookUpDAOImpl;

	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
		List<LeadGenProductDTO> leadGenProdDTOList = null;
		List<String> leadSubProdList = new ArrayList<String>();
		LinkedHashMap<String,String> prodSubProdNameMap=new LinkedHashMap<String,String>();
		MenuItemDTO menuDTO = null;
		try {
			Map<String, String> userInputMap = responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
			String selProductName=userInputMap.get(USSDConstants.LEAD_GEN_PRODUCT_NAME);
			Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
			leadSubProdList = (List<String>) txSessions.get(
					USSDInputParamsEnum.LEAD_GENERATION_PROD_SUB_LST.getTranId());
			if(txSessions.get("prodSubProdNameMap")!=null){
				prodSubProdNameMap=(LinkedHashMap<String, String>) txSessions.get("prodSubProdNameMap");
			}
			if(leadSubProdList==null){
				leadGenProdDTOList = ussdBranchLocatorLookUpDAOImpl.getLeadProductSubList(responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId(),selProductName,responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getLanguage());
				if(leadGenProdDTOList!=null){
					leadSubProdList=new ArrayList<String>();
					for (LeadGenProductDTO leadGenerationDTO : leadGenProdDTOList) {
						leadSubProdList.add(leadGenerationDTO.getSubProductName());
						prodSubProdNameMap.put(leadGenerationDTO.getSubProductName(), leadGenerationDTO.getProductName());
					}
				}
			}
			menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, leadSubProdList, "");
			if (txSessions == null) {
				txSessions = new HashMap<String, Object>(8);
			}
			txSessions.put(USSDInputParamsEnum.LEAD_GENERATION_PROD_SUB_LST.getTranId(), leadSubProdList);
			txSessions.put("prodSubProdNameMap", prodSubProdNameMap);
			responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);

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
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo());

	}
}
