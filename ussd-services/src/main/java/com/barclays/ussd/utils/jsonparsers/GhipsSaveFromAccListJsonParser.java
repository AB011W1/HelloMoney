package com.barclays.ussd.utils.jsonparsers;

import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
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
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccountDetails;

public class GhipsSaveFromAccListJsonParser implements BmgBaseJsonParser {

	private static final Logger LOGGER = Logger.getLogger(GhipsSaveFromAccListJsonParser.class);

	@SuppressWarnings("unchecked")
	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
		try{
			List<AccountDetails> lstFrmAcnt = (List<AccountDetails>) ussdSessionMgmt.getTxSessions().get(
					USSDInputParamsEnum.GHIPS_OTHER_BANK_FT_AMT.getTranId());

			if (lstFrmAcnt == null || lstFrmAcnt.isEmpty()) {
				throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_ACCTS.getBmgCode());
			}

			if (lstFrmAcnt != null && !lstFrmAcnt.isEmpty()) {
				int index = 1;
				StringBuilder pageBody = new StringBuilder();

				for (AccountDetails ele : lstFrmAcnt) {
					pageBody.append(USSDConstants.NEW_LINE);
					pageBody.append(index);
					pageBody.append(USSDConstants.DOT_SEPERATOR);
					pageBody.append(ele.getMkdActNo());
					index++;
				}

				menuItemDTO.setPageBody(pageBody.toString());
				// insert the back and home options
				USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
				menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
				menuItemDTO.setPaginationType(PaginationEnum.LISTED);
			}
			setNextScreenSequenceNumber(menuItemDTO);
		} catch (Exception e) {
			LOGGER.error("Exception : ", e);
			if (e instanceof USSDNonBlockingException) {
				throw new USSDNonBlockingException(((USSDNonBlockingException) e).getErrorCode());
			} else {
				throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
			}
		}
		return menuItemDTO;
	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIX.getSequenceNo());

	}


}
