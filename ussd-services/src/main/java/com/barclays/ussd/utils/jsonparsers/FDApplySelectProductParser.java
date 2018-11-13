/**
 *
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.LinkedHashMap;
import java.util.List;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.fdrates.SelectedFDProduct;

/**
 * @author BTCI
 * 
 */
public class FDApplySelectProductParser implements BmgBaseJsonParser {

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	return renderMenuOnScreen(responseBuilderParamsDTO);
    }

    /**
     * 
     * @param responseBuilderParamsDTO
     * @param tenures
     * @return
     */
    @SuppressWarnings("unchecked")
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	String selectedTenureStr = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get(
		USSDInputParamsEnum.FD_APPLY_SEL_TENURE.getTranId());
	LinkedHashMap<Integer, List<SelectedFDProduct>> avlblProductsMap = (LinkedHashMap<Integer, List<SelectedFDProduct>>) ussdSessionMgmt
		.getTxSessions().get(USSDInputParamsEnum.FD_APPLY_SEL_TENURE.getParamName());
	List<SelectedFDProduct> selectedFdProductList = avlblProductsMap.get(Integer.parseInt(selectedTenureStr) - 1);
	ussdSessionMgmt.getTxSessions().put(USSDInputParamsEnum.FD_APPLY_SEL_PROD.getTranId(), selectedFdProductList);
	StringBuilder pageBody = new StringBuilder(USSDConstants.NEW_LINE);
	String hyphen = " - ";

	int index = 1;
	for (SelectedFDProduct selectedFDProduct : selectedFdProductList) {
	    pageBody.append(index++);
	    pageBody.append(USSDConstants.DOT_SEPERATOR).append(selectedFDProduct.getSelProductDesc()).append(hyphen).append(
		    selectedFDProduct.getIntRate()).append(USSDConstants.NEW_LINE);
	}

	menuItemDTO.setPageBody(pageBody.toString());
	// insert the back and home options
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo());

    }
}
