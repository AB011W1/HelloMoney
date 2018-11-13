package com.barclays.ussd.bmg.dinning.offer;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.barclaysoffer.BarcaysDiningOffersLstData;
import com.barclays.ussd.utils.jsonparsers.bean.barclaysoffer.BarclaysDiningOffers;
import com.barclays.ussd.utils.jsonparsers.bean.barclaysoffer.DiningRestarantLst;
import com.barclays.ussd.utils.jsonparsers.bean.barclaysoffer.OfferAddress;

public class DiningOfferDetailsJsonParser implements BmgBaseJsonParser {

    private static final String LABEL_OFFER_VALIDITY = "label.offer.validity";
    private static final String LABEL_OFFER_ADDRESS = "label.offer.address";
    private static final String LABEL_OFFER_PHONE = "label.offer.phone";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(DiningOfferDetailsJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();

	try {

	    BarclaysDiningOffers barclaysOffers = mapper.readValue(responseBuilderParamsDTO.getJsonString(), BarclaysDiningOffers.class);
	    if (barclaysOffers != null) {
		if (barclaysOffers.getPayHdr() != null
			&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(barclaysOffers.getPayHdr().getResCde())) {
		    menuDTO = renderMenuOnScreen(barclaysOffers.getPayData(), responseBuilderParamsDTO, "");

		} else if (barclaysOffers.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(barclaysOffers.getPayHdr().getResCde());
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

    /**
     * @param barcaysOffersLstData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     */
    private MenuItemDTO renderMenuOnScreen(BarcaysDiningOffersLstData barcaysOffersLstData, ResponseBuilderParamsDTO responseBuilderParamsDTO,
	    String warningMsg) throws USSDNonBlockingException {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	StringBuilder pageBody = new StringBuilder();
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();

	if (barcaysOffersLstData == null || barcaysOffersLstData.getOfferLst() == null || barcaysOffersLstData.getOfferLst().isEmpty()) {
	    throw new USSDNonBlockingException(USSDExceptions.USSD_NO_RESTARANT_AVAILABLE.getBmgCode());
	}
	if (barcaysOffersLstData.getOfferLst() != null) {
	    int index = 1;

	    UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
	    String validityLabel = ussdResourceBundle.getLabel(LABEL_OFFER_VALIDITY, new Locale(ussdSessionMgmt.getUserProfile().getLanguage(),
		    ussdSessionMgmt.getUserProfile().getCountryCode()));
	    String addressLabel = ussdResourceBundle.getLabel(LABEL_OFFER_ADDRESS, new Locale(ussdSessionMgmt.getUserProfile().getLanguage(),
		    ussdSessionMgmt.getUserProfile().getCountryCode()));
	    String phoneLabel = ussdResourceBundle.getLabel(LABEL_OFFER_PHONE, new Locale(ussdSessionMgmt.getUserProfile().getLanguage(),
		    ussdSessionMgmt.getUserProfile().getCountryCode()));

	    for (DiningRestarantLst diningRestarantLst : barcaysOffersLstData.getOfferLst()) {
		pageBody.append(index++);
		pageBody.append(USSDConstants.DOT_SEPERATOR);
		pageBody.append(diningRestarantLst.getDisct());
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(validityLabel + diningRestarantLst.getLstDt());
		int addIndex = 1;
		for (OfferAddress offerAdd : diningRestarantLst.getLoc()) {
		    pageBody.append(USSDConstants.NEW_LINE);
		    pageBody.append(addressLabel).append(addIndex++);
		    pageBody.append(USSDConstants.DOT_SEPERATOR);
		    pageBody.append(offerAdd.getAddr());
		    pageBody.append(USSDConstants.NEW_LINE);
		    pageBody.append(phoneLabel + offerAdd.getPhNo());
		}
	    }

	    menuItemDTO.setPageBody(pageBody.toString());
	    USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	    menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	    menuItemDTO.setStatus(USSDConstants.STATUS_END);
	    menuItemDTO.setPaginationType(PaginationEnum.SPACED);
	    ussdSessionMgmt.clean();
	    setNextScreenSequenceNumber(menuItemDTO);
	} else {
	    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	}
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS.getSequenceNo());
    }
}