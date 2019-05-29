package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.dto.LeadGenProductDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.services.dao.impl.UssdBranchLocatorLookUpDAOImpl;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

public class LeadGenerationProductListJsonParser implements BmgBaseJsonParser,ScreenSequenceCustomizer {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(ChequeBookBranchListJsonParser.class);
	private final static String APPLY_FOR_PRODUCT = "menu.lead.generation.product";

	@Autowired
	UssdBranchLocatorLookUpDAOImpl ussdBranchLocatorLookUpDAOImpl;

	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
		List<LeadGenProductDTO> leadGenProdDTOList = null;
		ArrayList<String> prodNameList = new ArrayList<String>();
		LinkedHashMap<String,String> prodSubProdNameMap=new LinkedHashMap<String,String>();
		LinkedHashSet< String> prodNameSet =new LinkedHashSet< String>();
		MenuItemDTO menuDTO = null;
		try {
			Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
			if(txSessions!=null){
				prodNameList = (ArrayList<String>) txSessions.get(USSDInputParamsEnum.LEAD_GENERATION_PROD_LST.getTranId());
				prodSubProdNameMap = (LinkedHashMap<String,String>) txSessions.get("prodSubProdNameMap");
			}
			if(prodSubProdNameMap==null || prodSubProdNameMap.size()==0){
				leadGenProdDTOList = ussdBranchLocatorLookUpDAOImpl.getLeadProductList(responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId(),responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile().getLanguage());
				if(leadGenProdDTOList!=null){
					for (LeadGenProductDTO leadGenerationDTO : leadGenProdDTOList) {
						prodSubProdNameMap.put(leadGenerationDTO.getProductName(), leadGenerationDTO.getSubProductName());
						prodNameSet.add(leadGenerationDTO.getProductName());
					}
					prodNameList.clear();
					prodNameList.addAll(prodNameSet);
				}
			}
			menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, prodNameList, "");
			if (txSessions == null) {
				txSessions = new HashMap<String, Object>(8);
			}
			txSessions.put(USSDInputParamsEnum.LEAD_GENERATION_PROD_LST.getTranId(), prodNameList);
			txSessions.put(USSDInputParamsEnum.LEAD_GENERATION_PROD_SUB_LST.getTranId(), null);
			txSessions.put("prodSubProdNameMap", prodSubProdNameMap);
			responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);

		} catch (Exception e) {
			LOGGER.error("Exception : ", e);
/*			if (e instanceof USSDNonBlockingException) {
				throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
			} else {*/
				throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
			//}
		}
		return menuDTO;
	}

	/**
	 * @param payData
	 * @param responseBuilderParamsDTO
	 * @return MenuItemDTO
	 */
	private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, ArrayList<String> prodNameList,
			String warningMsg) {
		StringBuilder pageBody = new StringBuilder();
		MenuItemDTO menuItemDTO = new MenuItemDTO();

		int index = 1;
		if (prodNameList != null && prodNameList.size() != 0) {
			for(String prodName:prodNameList){
				pageBody.append(USSDConstants.NEW_LINE);
				pageBody.append(index++);
				pageBody.append(USSDConstants.DOT_SEPERATOR);
				pageBody.append(prodName);
			}
		}
		menuItemDTO.setPageBody(pageBody.toString());
		USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		menuItemDTO.setPageFooter(menuItemDTO.getPageFooter() + warningMsg);
		menuItemDTO.setPageHeader(APPLY_FOR_PRODUCT);
		menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
		menuItemDTO.setPaginationType(PaginationEnum.LISTED);
		setNextScreenSequenceNumber(menuItemDTO);
		return menuItemDTO;
	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());
	}

	@Override
	public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
		int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo();
		LinkedHashMap<String,String> prodSubProdNameMap= (LinkedHashMap<String,String>)ussdSessionMgmt.getTxSessions().get("prodSubProdNameMap");
		if(prodSubProdNameMap!=null && prodSubProdNameMap.size()!=0){
			Object prodNameArr[]=(Object[]) prodSubProdNameMap.keySet().toArray();
			String prodName=String.valueOf(prodNameArr[Integer.parseInt(userInput)- 1]);
			String SubProdName=(String) prodSubProdNameMap.get(prodName);
			if (SubProdName== null || SubProdName.trim().equals("")) {
				seqNo =USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo();
			}
			Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
			if(userInputMap==null){
				userInputMap=new HashMap<String, String>();
			}
			userInputMap.put(USSDConstants.LEAD_GEN_PRODUCT_NAME,prodName);
			ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
		}
		return seqNo;
	}
}
