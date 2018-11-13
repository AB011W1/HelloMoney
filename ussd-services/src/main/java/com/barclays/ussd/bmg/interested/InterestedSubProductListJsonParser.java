package com.barclays.ussd.bmg.interested;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.dto.InterestedProductDTO;
import com.barclays.ussd.dto.InterestedSubProductDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.services.dao.impl.InterestedProductDAOImpl;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

public class InterestedSubProductListJsonParser implements BmgBaseJsonParser {

    private static final Logger LOGGER = Logger.getLogger(InterestedSubProductListJsonParser.class);
    @Autowired
    InterestedProductDAOImpl interestedProductDAOImpl;

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {

	List<InterestedSubProductDTO> subPoductList = null;
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	MenuItemDTO menuDTO = null;
	try {
	    Map<String, String> userInputMap = responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
	    List<InterestedProductDTO> productList = (List<InterestedProductDTO>) responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
		    USSDInputParamsEnum.I_AM_INTERESTED_PRODUCT.getTranId());
	    String selectedProduct = productList.get(
		    Integer.parseInt(userInputMap.get(USSDInputParamsEnum.I_AM_INTERESTED_PRODUCT.getParamName())) - 1).getProductName();

	    subPoductList = interestedProductDAOImpl
		    .getSubProductList(responseBuilderParamsDTO.getUssdSessionMgmt().getBusinessId(), selectedProduct);

	    if (subPoductList != null && subPoductList.size() != 0) {
		menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, subPoductList, "");

		Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
		if (txSessions == null) {
		    txSessions = new HashMap<String, Object>(8);
		    responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
		}
		txSessions.put(USSDInputParamsEnum.I_AM_INTERESTED_SUB_PRODUCT.getTranId(), subPoductList);

	    } else {
		LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		throw new USSDNonBlockingException(USSDExceptions.USSD_NO_PRODUCT_FOUND.getBmgCode());
	    }
	} catch (Exception e) {
	    LOGGER.error("Exception : ", e);
	    if (e instanceof USSDNonBlockingException) {
		throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
	    }
	}
	return menuDTO;

    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo());
    }

    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, List<InterestedSubProductDTO> subPoductList,
	    String warningMsg) {
	StringBuilder pageBody = new StringBuilder();
	Collections.sort(subPoductList, new Comparator<InterestedSubProductDTO>() {
	    @Override
	    public int compare(InterestedSubProductDTO b1, InterestedSubProductDTO b2) {
		return b1.getSubProductName().compareTo(b2.getSubProductName());
	    }
	});
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	int index = 1;
	for (InterestedSubProductDTO interestedSubProductDTO : subPoductList) {
	    pageBody.append(USSDConstants.NEW_LINE);
	    pageBody.append(index++);
	    pageBody.append(USSDConstants.DOT_SEPERATOR);
	    pageBody.append(interestedSubProductDTO.getSubProductName());
	}
	menuItemDTO.setPageBody(pageBody.toString());
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageFooter(menuItemDTO.getPageFooter() + warningMsg);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }
}
