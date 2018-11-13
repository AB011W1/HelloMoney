/**
 * CasaDetailJSONParser.java
 */
package com.barclays.ussd.bmg.creditcard.link;

import org.apache.log4j.Logger;

import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;

/**
 * @author BTCI This class is used for Casa Detail Parsing
 *
 */
public class CreditCardLinkJsonParser implements BmgBaseJsonParser {




    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(CreditCardLinkJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
    	return renderMenuOnScreen(responseBuilderParamsDTO);
        }

        private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
    	MenuItemDTO menuItemDTO = new MenuItemDTO();

    	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
    	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
    	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
    	menuItemDTO.setPaginationType(PaginationEnum.NOT_REQD);
    	setNextScreenSequenceNumber(menuItemDTO);
    	return menuItemDTO;
        }


    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());

    }
}
