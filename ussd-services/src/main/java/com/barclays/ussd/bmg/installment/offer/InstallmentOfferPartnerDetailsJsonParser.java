package com.barclays.ussd.bmg.installment.offer;

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
import com.barclays.ussd.utils.jsonparsers.bean.barclaysoffer.BarcaysExtraInstallmentOffersLstData;
import com.barclays.ussd.utils.jsonparsers.bean.barclaysoffer.BarclaysExtraInstallmentOffers;
import com.barclays.ussd.utils.jsonparsers.bean.barclaysoffer.InstallmentPatnerLst;
import com.barclays.ussd.utils.jsonparsers.bean.barclaysoffer.OfferAddress;

public class InstallmentOfferPartnerDetailsJsonParser implements BmgBaseJsonParser {

    private static final Logger LOGGER = Logger.getLogger(BarclaysExtraInstallmentOffers.class);
    private static final String LABEL_OFFER_VALIDITY = "label.offer.validity";
    private static final String LABEL_OFFER_ADDRESS = "label.offer.address";
    private static final String LABEL_OFFER_PHONE = "label.offer.phone";
    private static final String LABEL_OFFER_DISCOUNT = "label.offer.discount";
    private static final String LABEL_OFFER_TNC = "label.offer.tnc";

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	ObjectMapper mapper = new ObjectMapper();

	try {

	    BarclaysExtraInstallmentOffers barclaysOffers = mapper.readValue(responseBuilderParamsDTO.getJsonString(),
		    BarclaysExtraInstallmentOffers.class);
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
    private MenuItemDTO renderMenuOnScreen(BarcaysExtraInstallmentOffersLstData barcaysOffersLstData,
	    ResponseBuilderParamsDTO responseBuilderParamsDTO, String warningMsg) throws USSDNonBlockingException {
	MenuItemDTO menuItemDTO = new MenuItemDTO();
	StringBuilder pageBody = new StringBuilder();
	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();

	if (barcaysOffersLstData == null || barcaysOffersLstData.getEipOfferLst() == null || barcaysOffersLstData.getEipOfferLst().isEmpty()) {
	    throw new USSDNonBlockingException(USSDExceptions.USSD_NO_RESTARANT_AVAILABLE.getBmgCode());
	}
	if (barcaysOffersLstData.getEipOfferLst() != null) {
	    int index = 1;
	    UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
	    String validityLabel = ussdResourceBundle.getLabel(LABEL_OFFER_VALIDITY, new Locale(ussdSessionMgmt.getUserProfile().getLanguage(),
		    ussdSessionMgmt.getUserProfile().getCountryCode()));
	    String addressLabel = ussdResourceBundle.getLabel(LABEL_OFFER_ADDRESS, new Locale(ussdSessionMgmt.getUserProfile().getLanguage(),
		    ussdSessionMgmt.getUserProfile().getCountryCode()));
	    String phoneLabel = ussdResourceBundle.getLabel(LABEL_OFFER_PHONE, new Locale(ussdSessionMgmt.getUserProfile().getLanguage(),
		    ussdSessionMgmt.getUserProfile().getCountryCode()));
	    String offerDiscountLabel = ussdResourceBundle.getLabel(LABEL_OFFER_DISCOUNT, new Locale(ussdSessionMgmt.getUserProfile().getLanguage(),
		    ussdSessionMgmt.getUserProfile().getCountryCode()));
	    String tncLabel = ussdResourceBundle.getLabel(LABEL_OFFER_TNC, new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt
		    .getUserProfile().getCountryCode()));

	    for (InstallmentPatnerLst partnerLst : barcaysOffersLstData.getEipOfferLst()) {
		pageBody.append(index++);
		pageBody.append(USSDConstants.DOT_SEPERATOR);
		int addIndex = 1;
		for (OfferAddress offerAdd : partnerLst.getLoc()) {
		    pageBody.append(USSDConstants.NEW_LINE);
		    pageBody.append(addressLabel).append(addIndex++);
		    pageBody.append(USSDConstants.DOT_SEPERATOR);
		    pageBody.append(offerAdd.getAddr());
		    pageBody.append(USSDConstants.NEW_LINE);
		    pageBody.append(phoneLabel + offerAdd.getPhNo());
		}

		pageBody.append(offerDiscountLabel + partnerLst.getEipOffer());
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(validityLabel + partnerLst.getLstDt());
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(tncLabel + partnerLst.getTnc());
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