/**
 *
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.dao.product.impl.ComponentResDAOImpl;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.BillersListDO;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.common.services.IBillersLstService;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.SystemPreferenceValidator;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;

/**
 * @author Vishal Chhabra
 *
 */
public class MasterPassQRRefJsonParser implements BmgBaseJsonParser,
		SystemPreferenceValidator {

	private static final Logger LOGGER = Logger
			.getLogger(MasterPassQRRefJsonParser.class);

	@Autowired
	ComponentResDAOImpl componentResDAOImpl;

	@Autowired
	private IBillersLstService billersLstService;

	@Override
	public MenuItemDTO parseJsonIntoJava(
			ResponseBuilderParamsDTO responseBuilderParamsDTO)
			throws USSDNonBlockingException {
		return renderMenuOnScreen(responseBuilderParamsDTO);
	}

	private MenuItemDTO renderMenuOnScreen(
			ResponseBuilderParamsDTO responseBuilderParamsDTO)
			throws USSDNonBlockingException {

		MenuItemDTO menuItemDTO = new MenuItemDTO();

		setNextScreenSequenceNumber(menuItemDTO);
		return menuItemDTO;
	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo());
	}

	@Override
	public void validate(String userInput, USSDSessionManagement ussdSessionMgmt)
			throws USSDBlockingException, USSDNonBlockingException {
		 BillersListDO biller = this.billersLstService.getBillerInfo(ussdSessionMgmt
					.getCountryCode(), "MPQR-3", ussdSessionMgmt
					.getMsisdnNumber(), ussdSessionMgmt.getBusinessId());
		LOGGER.debug("Biller Details : "+ (null!=biller?biller.getBillerId():"IS NULL"));
		if(null != biller){
			String billerCategoryID = biller.getBillerId();
			if (billerCategoryID != null) {
				if (null != ussdSessionMgmt.getTxSessions()) {
					LOGGER.debug("IF Biller Validate :"+billerCategoryID);
					ussdSessionMgmt.getTxSessions().put(
							USSDConstants.MPQR_BILLER_INFO, billerCategoryID);
				} else {
					LOGGER.debug("ELSE Biller Validate :"+billerCategoryID);
					Map<String, Object> txSessions = new HashMap<String, Object>(5);
					txSessions
							.put(USSDConstants.MPQR_BILLER_INFO, billerCategoryID);
					ussdSessionMgmt.setTxSessions(txSessions);
				}
			}
		} else {
			throw new USSDNonBlockingException(USSDExceptions.BEM08863.getUssdErrorCode());
		}
	}
}
