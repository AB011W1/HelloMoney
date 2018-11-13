/**
 *
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDBlockingExceptions;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.fdrates.FDProduct;
import com.barclays.ussd.utils.jsonparsers.bean.fdrates.FDRates;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.PayHdr;

/**
 * @author BTCI
 * 
 */
public class FDViewEnterAmountParser implements BmgBaseJsonParser, ScreenSequenceCustomizer {

    private static final Logger LOGGER = Logger.getLogger(FDViewEnterAmountParser.class);

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	String jsonString = responseBuilderParamsDTO.getJsonString();
	ObjectMapper mapper = new ObjectMapper();
	MenuItemDTO menuItemDTO = null;
	List<FDProduct> fdProductDetails = null;

	try {
	    FDRates rates = mapper.readValue(jsonString, FDRates.class);
	    PayHdr payHdr = rates.getPayHdr();
	    if (payHdr != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(payHdr.getResCde())) {
		menuItemDTO = renderMenuOnScreen(responseBuilderParamsDTO);
		fdProductDetails = rates.getPayData().getIntRatesList();
		Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
		txSessions = txSessions == null ? new HashMap<String, Object>() : txSessions;

		txSessions.put(USSDInputParamsEnum.FD_VIEW_RATE_ENTER_AMT.getTranId(), fdProductDetails);
		responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessions);
	    } else if (payHdr != null) {
		LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		throw new USSDNonBlockingException(payHdr.getResCde());
	    } else {
		LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }

	} catch (JsonParseException e) {
	    LOGGER.fatal("JsonParseException : " + e.getMessage());
	    throw new USSDNonBlockingException(USSDBlockingExceptions.PARSER_ERROR.getErrorCode(), e.getMessage());
	} catch (JsonMappingException e) {
	    LOGGER.fatal("JsonMappingException : " + e.getMessage());
	    throw new USSDNonBlockingException(USSDBlockingExceptions.PARSER_ERROR.getErrorCode(), e.getMessage());
	} catch (Exception e) {
	    LOGGER.error("Exception : ", e);
	    if (e instanceof USSDNonBlockingException) {
		throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
	    } else {
		throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	    }
	}
	if (responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions() == null) {
	    responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(new HashMap<String, Object>());
	}

	return menuItemDTO;
    }

    /**
     * 
     * @param responseBuilderParamsDTO
     * @param tenures
     * @return
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();

	String countryCode = ussdSessionMgmt.getUserProfile().getCountryCode();
	String lang = ussdSessionMgmt.getUserProfile().getLanguage();
	menuItemDTO.setPageBody(new StringBuilder(responseBuilderParamsDTO.getUssdResourceBundle().getLabel(USSDConstants.FD_ENTER_AMT,
		new Locale(lang, countryCode))).toString());

	// insert the back and home options
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.SPACED);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());
    }

    @SuppressWarnings("unchecked")
    private Map<Integer, List<String>> getProductDescIntRates(USSDSessionManagement ussdSessionMgmt, List<String> tenures, String userInput) {
	Map<Integer, List<String>> productDescIntRateMap = new LinkedHashMap<Integer, List<String>>();
	Integer index = 0;
	Map<String, Integer> tenureIndex = new HashMap<String, Integer>();
	try {
	    List<FDProduct> fdProductDetails = (List<FDProduct>) ussdSessionMgmt.getTxSessions().get(
		    USSDInputParamsEnum.FD_VIEW_RATE_ENTER_AMT.getTranId());
	    Long userAmount = Long.parseLong(userInput);
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
				productDescIntRateMap.put(index, new ArrayList<String>());
			    }
			    List<String> productDescIntRates = productDescIntRateMap.get(index);
			    String prodDescIntRate = new StringBuilder(fdProduct.getProdDesc()).append(" - ").append(tenure.getIntrate()).append("%")
				    .toString();
			    if (!productDescIntRates.contains(prodDescIntRate)) {
				productDescIntRates.add(prodDescIntRate);
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
		LOGGER.info("Exception Occured in FdViewTenureJsonParser:", e);
	    }
	}
	return productDescIntRateMap;

    }

    public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	List<String> tenures = new ArrayList<String>();
	ussdSessionMgmt.getTxSessions().put(USSDInputParamsEnum.FD_VIEW_TENURE.getParamName(),
		getProductDescIntRates(ussdSessionMgmt, tenures, userInput));
	ussdSessionMgmt.getTxSessions().put(USSDInputParamsEnum.FD_VIEW_TENURE.getTranId(), tenures);
	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	List<String> tenureList = (List<String>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.FD_VIEW_TENURE.getTranId());
	if (tenureList != null && tenureList.size() == 1) {
	    userInputMap = userInputMap == null ? new HashMap<String, String>() : userInputMap;
	    userInputMap.put(USSDInputParamsEnum.FD_VIEW_TENURE.getParamName(), USSDConstants.DEFAULT_OPTION_SELECTION);
	    ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
	    seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo();
	}
	return seqNo;
    }

}
