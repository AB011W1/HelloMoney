package com.barclays.ussd.auth.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bean.NavigationOptionsDTO;
import com.barclays.ussd.common.configuration.ConfigurationManager;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.UssdResourceBundle;

// TODO: Auto-generated Javadoc
/**
 * The Class USSDResponsePagination.
 */
public class USSDResponsePagination {

    /** The Constant FIRST_PAGE_POSITION. */
    private static final int FIRST_PAGE_POSITION = 0;

    /** The Constant DEFAULT_SCRL_DWN_LEN. */
    private static final int DEFAULT_SCRL_DWN_LEN = 13;

    /** The Constant DEFAULT_SCRL_UP_LEN. */
    private static final int DEFAULT_SCRL_UP_LEN = 11;

    /** The Constant logger. */
    private static final Logger LOGGER = Logger.getLogger(USSDResponsePagination.class);

    /** The Constant DEFAULT_PAGE_BODY_SIZE. */
    private static final int DEFAULT_PAGE_BODY_SIZE = 140;

    /**
     * Handle pagination.
     *
     * @param rawMenuItemDTO
     *            the raw menu item dto
     * @param ussdSessionMgmt
     *            the ussd session mgmt
     * @param ussdResourceBundle
     *            the ussd resource bundle
     * @param lang
     *            the lang
     * @param countryCode
     *            the country code
     * @return the menu item dto
     */
    public MenuItemDTO handlePagination(MenuItemDTO rawMenuItemDTO, USSDSessionManagement ussdSessionMgmt, UssdResourceBundle ussdResourceBundle,
	    NavigationOptionsDTO navigationOptionsDTO) {
	String lang = ussdSessionMgmt.getUserProfile().getLanguage();
	String countryCode = ussdSessionMgmt.getUserProfile().getCountryCode();
	MenuItemDTO pagedMenuItemDTO = null;
	//CR-86 Back changes apply product list
	if(rawMenuItemDTO.getPageError()!= null && rawMenuItemDTO.getPageError()!="" && ussdSessionMgmt.getUserTransactionDetails()!= null &&
			ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranDataId().equalsIgnoreCase("LG000")){
		rawMenuItemDTO.setPageHeader(rawMenuItemDTO.getPageError());
		rawMenuItemDTO.setPageError("");

	}
	StringBuilder pageBodySB = new StringBuilder(rawMenuItemDTO.getPageHeader());

	if (rawMenuItemDTO.getPageBody() != null) {
	    pageBodySB.append(rawMenuItemDTO.getPageBody());
	}
	String pageBody = pageBodySB.toString();
	//CR-86 Back changes Fdrates/apply product
	if(ussdSessionMgmt.getUserTransactionDetails()!= null &&
			(ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranDataId().equalsIgnoreCase("FDR003")||
					ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranDataId().equalsIgnoreCase("LG003"))&&
			rawMenuItemDTO.getPageError()!= null && rawMenuItemDTO.getPageError()!=""){
		pageBody="";
	}


	if (pageBody == null || pageBody.trim().isEmpty()) {
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Pagination is not required...");
	    }
	    return rawMenuItemDTO;
	}

	if (LOGGER.isDebugEnabled()) {
	    LOGGER.debug("Applying pagination...");
	}
	String params[] = new String[1];
	params[0] = navigationOptionsDTO.getScrollUpOption();
	String scrollUp = ussdResourceBundle.getLabel(USSDConstants.SCROLL_UP_LBL, params, new Locale(lang, countryCode));
	params[0] = navigationOptionsDTO.getScrollDownOption();
	String scrollDown = ussdResourceBundle.getLabel(USSDConstants.SCROLL_DOWN_LBL, params, new Locale(lang, countryCode));

	int maxPageBodySize = ConfigurationManager.getInt(USSDConstants.MAX_PAGE_BODY_SIZE, DEFAULT_PAGE_BODY_SIZE);
	int scrollUpMsgLength = StringUtils.length(scrollUp) == 0 ? DEFAULT_SCRL_UP_LEN : StringUtils.length(scrollUp);
	int scrollDwnMsgLength = StringUtils.length(scrollDown) == 0 ? DEFAULT_SCRL_DWN_LEN : StringUtils.length(scrollDown);

	if (StringUtils.length(pageBody) > maxPageBodySize) {
	    int resultantPageBodySize = maxPageBodySize
		    - (scrollUpMsgLength + StringUtils.length(PaginationEnum.LISTED.getPaginationType()) + scrollDwnMsgLength);
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Splitting the response into scrollable pages...");
	    }

	    String splitExpression = rawMenuItemDTO.getPaginationType().getPaginationType();
	    // ussdSessionMgmt.isListedResponse() ? SPLIT_BY_NEWLINE_EXPR : USSDConstants.SINGLE_WHITE_SPACE;
	    String[] arrPageBody = pageBody.split(splitExpression);
	    boolean oldPageBody = true;
	    StringBuffer buffer = new StringBuffer();
	    int length = arrPageBody.length;

	    for (int index = 0; index <= length; index++) {
		if (index != length && arrPageBody[index].trim().length() == 0) {
		    continue;
		}

		if (oldPageBody && index != length) {
		    oldPageBody = appendItemsToPageBody(buffer, resultantPageBodySize, arrPageBody[index], splitExpression, index);
		}
		// intentionally we have two if blocks instead of having if-else
		if (!oldPageBody || index == length) {
		    if (index != length) {
			index--;
		    }
		    List<MenuItemDTO> pageList = ussdSessionMgmt.getPageList();
		    pagedMenuItemDTO = getPagedMenuItemDTO(rawMenuItemDTO, buffer);
		    appendScrollers(pagedMenuItemDTO, scrollUp, scrollDown, (pageList != null && !pageList.isEmpty()), index != length);
		    if (pageList == null) {
			pageList = new ArrayList<MenuItemDTO>(length);
			ussdSessionMgmt.setPageList(pageList);
		    }

		    pageList.add(pagedMenuItemDTO);
		    ussdSessionMgmt.setPaged(true);
		    oldPageBody = true;
		    buffer.setLength(0); // to clear the buffer
		}
	    }

	    // Return the first page
	    ussdSessionMgmt.setCurrentPagePosition(FIRST_PAGE_POSITION);
	    pagedMenuItemDTO = ussdSessionMgmt.getPageList().get(FIRST_PAGE_POSITION);
	} else {
	    if (LOGGER.isDebugEnabled()) {
		LOGGER.debug("Response can be populated in a single screen hence pagination is not required...");
	    }
	    pagedMenuItemDTO = rawMenuItemDTO;
	}

	return pagedMenuItemDTO;
    }

    public MenuItemDTO getPagedMenuItemDTO(MenuItemDTO rawMenuItemDTO, StringBuffer buffer) {
	MenuItemDTO pagedMenuItemDTO = new MenuItemDTO();
	BeanUtils.copyProperties(rawMenuItemDTO, pagedMenuItemDTO);
	pagedMenuItemDTO.setPageHeader("");
	if (buffer.indexOf(USSDConstants.NEW_LINE) == 0) {
	    pagedMenuItemDTO.setPageBody(buffer.substring(2));
	} else {
	    pagedMenuItemDTO.setPageBody(buffer.toString());
	}
	return pagedMenuItemDTO;
    }

    /**
     * Append scrollers.
     *
     * @param pagedMenuItemDTO
     *            the buffer
     * @param scrollUp
     *            the scroll up
     * @param scrollDown
     *            the scroll down
     * @param pageList
     *            the page list
     * @param index
     *            the index
     * @param arrSize
     *            the arr size
     */
    private void appendScrollers(MenuItemDTO pagedMenuItemDTO, String scrollUp, String scrollDown, boolean appendUp, boolean appendDown) {
	// String pageFooter = pagedMenuItemDTO.getPageFooter();
	StringBuffer scrollers = new StringBuffer();

	// if (pageFooter != null && StringUtils.isNotEmpty(pageFooter)) {
	//
	// }

	if (appendUp) {
	    scrollers.append(USSDConstants.COMMA_SEPERATOR);
	    scrollers.append(scrollUp);
	}

	if (appendDown) {
	    scrollers.append(USSDConstants.COMMA_SEPERATOR);
	    scrollers.append(scrollDown);
	}
	pagedMenuItemDTO.setScrollers(scrollers.toString());

    }

    /*
     * This method checks and appends items into the buffer if it is within the permissible length limits
     */
    /**
     * Append items to page body.
     *
     * @param buffer
     *            the buffer
     * @param resultantPageBodySize
     *            the resultant page body size
     * @param splittedBody
     *            the splitted body
     * @param splitExpression
     *            the split expression
     * @param index
     * @return true, if successful
     */
    private boolean appendItemsToPageBody(StringBuffer buffer, int resultantPageBodySize, String splittedBody, String splitExpression, int index) {
	if (StringUtils.length(buffer.toString() + splitExpression + splittedBody) < resultantPageBodySize) {
	    if (USSDConstants.SINGLE_WHITE_SPACE.equalsIgnoreCase(splitExpression) && index != 0) {
		buffer.append(USSDConstants.SINGLE_WHITE_SPACE);
	    } else {
		buffer.append(USSDConstants.NEW_LINE);
	    }
	    buffer.append(splittedBody);
	    return true;
	}
	return false;
    }

    /**
     * Handle scrolling.
     *
     * @param ussdSessionMgmt
     *            the ussd session mgmt
     * @param userInput
     *            the user input
     * @param ussdResourceBundle
     * @return the menu item dto
     */
    public MenuItemDTO handleScrolling(USSDSessionManagement ussdSessionMgmt, String userInput, UssdResourceBundle ussdResourceBundle,
	    NavigationOptionsDTO navigationOptions) {
	int currentPagePosition = ussdSessionMgmt.getCurrentPagePosition();
	List<MenuItemDTO> pageList = ussdSessionMgmt.getPageList();
	MenuItemDTO requestedDTO = null;
	// Clear the error from the requested screen
	pageList.get(currentPagePosition).setPageError(StringUtils.EMPTY);

	if (userInput.equalsIgnoreCase(navigationOptions.getScrollUpOption()) && currentPagePosition != 0) {
	    ussdSessionMgmt.setCurrentPagePosition(currentPagePosition - 1);
	    requestedDTO = pageList.get(currentPagePosition - 1);
	} else if (userInput.equalsIgnoreCase(navigationOptions.getScrollDownOption()) && currentPagePosition < (pageList.size() - 1)) {
	    ussdSessionMgmt.setCurrentPagePosition(currentPagePosition + 1);
	    requestedDTO = pageList.get(currentPagePosition + 1);
	    requestedDTO.setPageError(StringUtils.EMPTY);
	} else {
	    requestedDTO = pageList.get(currentPagePosition);
	    StringBuffer pageError = new StringBuffer(ussdResourceBundle.getLabel(USSDExceptions.USSD_INVALID_OPT_SELECTED.getUssdErrorCode(),
		    new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode())));
	    requestedDTO.setPageError(pageError.append(USSDConstants.NEW_LINE).toString());
	}
	return requestedDTO;
    }
}
