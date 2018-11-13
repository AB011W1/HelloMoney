package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;

import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.fdrates.FDProduct;
import com.barclays.ussd.utils.jsonparsers.bean.fdrates.SelectedFDProduct;

public class FDApplyEnterTenureParser implements BmgBaseJsonParser {
    private static final Logger LOGGER = Logger.getLogger(FDApplyEnterTenureParser.class);

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	List<String> tenures = new ArrayList<String>();

	responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.FD_APPLY_SEL_TENURE.getParamName(),
		getProductDescIntRates(responseBuilderParamsDTO, tenures));
	responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.FD_APPLY_SEL_TENURE.getTranId(), tenures);
	return renderMenuOnScreen(responseBuilderParamsDTO, tenures);
    }

    /**
     * 
     * @param responseBuilderParamsDTO
     * @param tenures
     * @param tenureIndex
     * @return
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, List<String> tenures) throws USSDNonBlockingException {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	StringBuilder pageBody = new StringBuilder();
	int index = 1;
	pageBody.append(USSDConstants.NEW_LINE);
	if (tenures != null && !tenures.isEmpty()) {
	    for (String tenure : tenures) {
		pageBody.append(index++).append(USSDConstants.DOT_SEPERATOR).append(tenure).append(USSDConstants.NEW_LINE);
	    }
	    menuItemDTO.setPageBody(pageBody.toString());
	} else {
	    throw new USSDNonBlockingException(USSDExceptions.FD_RATES_NO_TENURE.getUssdErrorCode());
	}
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    /**
     * Build a map for storing tenure index and the list of productDTO in case of multiple tenures.
     * 
     * @param responseBuilderParamsDTO
     * @param payData
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<Integer, List<SelectedFDProduct>> getProductDescIntRates(ResponseBuilderParamsDTO responseBuilderParamsDTO, List<String> tenures) {
	Map<Integer, List<SelectedFDProduct>> productDescIntRateMap = new LinkedHashMap<Integer, List<SelectedFDProduct>>();
	Integer index = 0;
	Map<String, Integer> tenureIndex = new HashMap<String, Integer>();
	List<String> avlblProducts = new ArrayList<String>();
	try {
	    List<FDProduct> fdProductDetails = (List<FDProduct>) responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
		    USSDInputParamsEnum.FD_APPLY_ENTER_AMT.getTranId());
	    Integer userAmount = Integer.parseInt(responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap().get(
		    USSDInputParamsEnum.FD_APPLY_ENTER_AMT.getTranId()));
	    int matchingAmtTenures = 0;
	    int totalTenures = 0;
	    int totalProducts = 0;
	    int matchingProducts = 0;
	    for (FDProduct fdProduct : fdProductDetails) {
		List<com.barclays.ussd.utils.jsonparsers.bean.fdrates.InterestRate> interestRates = fdProduct.getIntRateSlab();
		for (com.barclays.ussd.utils.jsonparsers.bean.fdrates.InterestRate interestRate : interestRates) {
		    String frmAmt = interestRate.getFrom().getAmt();
		    Double fromAmount = null;
		    Double toAmount = null;
		    if (frmAmt != null) {
			frmAmt = frmAmt.replaceAll(",", "");
			fromAmount = Double.parseDouble(frmAmt);
		    }

		    String toAmt = interestRate.getTo().getAmt();
		    if (toAmt != null) {
			toAmt = toAmt.replaceAll(",", "");
			toAmount = Double.parseDouble(toAmt);
		    }

		    boolean inRange = false;
		    if (fromAmount != null && toAmount != null) {
			inRange = GenericValidator.isInRange(userAmount, fromAmount, toAmount);
			if (inRange) {
			    matchingProducts++;
			}
		    }

		    for (com.barclays.ussd.utils.jsonparsers.bean.fdrates.Tenure tenure : interestRate.getTenure()) {
			totalTenures++;
			if (inRange) {
			    matchingAmtTenures++;
			    String tenureStr = new StringBuilder().append(tenure.getTenMonth()).append(" M ").append(tenure.getTenDay()).append(" D")
				    .toString();
			    if (!tenures.contains(tenureStr)) {
				tenures.add(tenureStr);
				tenureIndex.put(tenureStr, tenures.indexOf(tenureStr));
			    }
			    index = tenureIndex.get(tenureStr);

			    if (!productDescIntRateMap.containsKey(index)) {
				productDescIntRateMap.put(index, new ArrayList<SelectedFDProduct>());
			    }
			    List<SelectedFDProduct> productDescIntRate = productDescIntRateMap.get(index);
			    String avlblProdCheckString = fdProduct.getProdCode() + tenure.getIntrate();
			    if (!avlblProducts.contains(avlblProdCheckString)) {
				avlblProducts.add(avlblProdCheckString);
				productDescIntRate.add(new SelectedFDProduct(tenureStr, fdProduct.getProdDesc(), fdProduct.getProdCode(), tenure
					.getIntrate(), String.valueOf(userAmount), tenure.getTenMonth(), tenure.getTenDay()));
			    }
			}
		    }
		    totalProducts++;
		}
	    }

	    LOGGER.debug("matchingProducts: " + matchingProducts);
	    LOGGER.debug("matchingAmtTenures: " + matchingAmtTenures);
	    LOGGER.debug("totalProducts: " + totalProducts);
	    LOGGER.debug("totalTenures: " + totalTenures);
	} catch (Exception e) {
	    // 
	    if (LOGGER.isInfoEnabled()) {
		LOGGER.info("Exception Occured in FDApplyEnterTenureParser:", e);
	    }
	}
	return productDescIntRateMap;

    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FOUR.getSequenceNo());

    }
}