package com.barclays.ussd.utils.jsonparsers;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.fxrate.FxRateDetails;
import com.barclays.ussd.utils.jsonparsers.bean.fxrate.FxRatePayData;

public class FxRateConvertJsonParser implements BmgBaseJsonParser {

    private static final String NUMERICAL_ONE = "1";
    private static final int NUMERICAL_TWO = 2;

    private static final Logger LOGGER = Logger.getLogger(FxRateConvertJsonParser.class);

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDBlockingException, USSDNonBlockingException {

	String jsonString = responseBuilderParamsDTO.getJsonString();
	ObjectMapper mapper = new ObjectMapper();
	MenuItemDTO menuDTO = null;
	try {
	    FxRateDetails casaDetail = mapper.readValue(jsonString, FxRateDetails.class);
	    if (casaDetail != null) {
		if (casaDetail.getPayHdr() != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(casaDetail.getPayHdr().getResCde())) {
		    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, casaDetail.getPayData(), "");
		} else if (casaDetail.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(casaDetail.getPayHdr().getResCde());
		} else {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
		}
	    }
	} catch (JsonParseException e) {
	    LOGGER.error("JsonParseException : ", e);
	    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	} catch (JsonMappingException e) {
	    LOGGER.error("JsonParseException : ", e);
	    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
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

    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, FxRatePayData payData, String string) {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	Map<String, Object> txSessions = responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions();
	String destCurrency = (String) txSessions.get(USSDInputParamsEnum.FX_RATE_GET_CURR.getParamName());
	String sourceCurrency = (String) txSessions.get(USSDInputParamsEnum.FX_RATE_CONVERT.getParamName());
	// StringBuilder pageBody = new StringBuilder("\n1 ").append(destCurrency);
	StringBuilder pageBody = new StringBuilder();
	String businessId = responseBuilderParamsDTO.getUssdSessionMgmt()
				.getBusinessId();
	if(businessId.equals("BWBRB")||businessId.equals("ZMBRB")||businessId.equals("GHBRB")){
	pageBody.append(NUMERICAL_ONE).append(USSDConstants.SINGLE_WHITE_SPACE).append(destCurrency);
	//String amt = payData.getEffectiveFXRate();
	String buyRate = payData.getBuyRate();
	String sellRate = payData.getSellRate();
	if(buyRate==null || buyRate.equalsIgnoreCase("null") || buyRate.equalsIgnoreCase("") || buyRate.isEmpty()){
		buyRate="NA";
	}if(sellRate==null ||sellRate.equalsIgnoreCase("null") || sellRate.equalsIgnoreCase("") || sellRate.isEmpty()){
		sellRate="NA";
	}
	//String formattedAmt = null;
	/*try {
	    double parsedAmt = Double.parseDouble(amt);
	    formattedAmt = String.format("%.4f", parsedAmt);

	} catch (NumberFormatException excp) {
	    // do nothing
	}*/

	// pageBody.append(": ").append(payData.getEffectiveFXRate()).append(" ").append(sourceCurrency);
	pageBody.append(USSDConstants.SEPERATOR_EQUALTO).append(USSDConstants.SINGLE_WHITE_SPACE).append(new BigDecimal(1/Double.parseDouble(buyRate)).setScale(NUMERICAL_TWO, BigDecimal.ROUND_FLOOR)).append(USSDConstants.SINGLE_WHITE_SPACE)
		.append(sourceCurrency).append(USSDConstants.SINGLE_WHITE_SPACE).append(USSDConstants.FOREX_BUY_RATE).append(USSDConstants.NEW_LINE);

	pageBody.append(NUMERICAL_ONE).append(USSDConstants.SINGLE_WHITE_SPACE).append(destCurrency);
	pageBody.append(USSDConstants.SEPERATOR_EQUALTO).append(USSDConstants.SINGLE_WHITE_SPACE).append(new BigDecimal(1/Double.parseDouble(sellRate)).setScale(NUMERICAL_TWO, BigDecimal.ROUND_FLOOR)).append(USSDConstants.SINGLE_WHITE_SPACE)
	.append(sourceCurrency).append(USSDConstants.SINGLE_WHITE_SPACE).append(USSDConstants.FOREX_SELL_RATE).append(USSDConstants.NEW_LINE);
	}else{
		pageBody.append(NUMERICAL_ONE).append(USSDConstants.SINGLE_WHITE_SPACE).append(destCurrency);
		//String amt = payData.getEffectiveFXRate();
		String buyRate = payData.getBuyRate();
		String sellRate = payData.getSellRate();
		if(buyRate==null || buyRate.equalsIgnoreCase("null") || buyRate.equalsIgnoreCase("") || buyRate.isEmpty()){
			buyRate="NA";
		}if(sellRate==null ||sellRate.equalsIgnoreCase("null") || sellRate.equalsIgnoreCase("") || sellRate.isEmpty()){
			sellRate="NA";
		}
		//String formattedAmt = null;
		/*try {
		    double parsedAmt = Double.parseDouble(amt);
		    formattedAmt = String.format("%.4f", parsedAmt);

		} catch (NumberFormatException excp) {
		    // do nothing
		}*/

		// pageBody.append(": ").append(payData.getEffectiveFXRate()).append(" ").append(sourceCurrency);
		pageBody.append(USSDConstants.SEPERATOR_EQUALTO).append(USSDConstants.SINGLE_WHITE_SPACE).append(buyRate).append(USSDConstants.SINGLE_WHITE_SPACE)
			.append(sourceCurrency).append(USSDConstants.SINGLE_WHITE_SPACE).append(USSDConstants.FOREX_BUY_RATE).append(USSDConstants.NEW_LINE);

		pageBody.append(NUMERICAL_ONE).append(USSDConstants.SINGLE_WHITE_SPACE).append(destCurrency);
		pageBody.append(USSDConstants.SEPERATOR_EQUALTO).append(USSDConstants.SINGLE_WHITE_SPACE).append(sellRate).append(USSDConstants.SINGLE_WHITE_SPACE)
		.append(sourceCurrency).append(USSDConstants.SINGLE_WHITE_SPACE).append(USSDConstants.FOREX_SELL_RATE).append(USSDConstants.NEW_LINE);

	}
	menuItemDTO.setPageBody(pageBody.toString());
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS.getSequenceNo());

    }
}