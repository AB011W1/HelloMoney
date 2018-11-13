package com.barclays.ussd.utils.jsonparsers;

import java.util.Collections;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

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
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.PayeeList;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.PayeeLstData;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccountDetails;

public class GhipsOneOffFromAccListJsonParser implements BmgBaseJsonParser {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(GhipsOneOffFromAccListJsonParser.class);

	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
		MenuItemDTO menuDTO = new MenuItemDTO();
		ObjectMapper mapper = new ObjectMapper();
		try {
			PayeeList payeeList = mapper.readValue(responseBuilderParamsDTO.getJsonString(), PayeeList.class);
			if (payeeList != null) {
				if (payeeList.getPayHdr() != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(payeeList.getPayHdr().getResCde())) {
					 menuDTO = renderMenuOnScreen(payeeList, responseBuilderParamsDTO);
				} else if (payeeList.getPayHdr() != null) {
					LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
					throw new USSDNonBlockingException(payeeList.getPayHdr().getResCde());
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
	 * @param responseBuilderParamsDTO
	 * @param payeeList
	 */
	 private MenuItemDTO renderMenuOnScreen(PayeeList payeeList, ResponseBuilderParamsDTO responseBuilderParamsDTO) {
		PayeeLstData payeeLstData = payeeList.getPayData();
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		if (payeeLstData != null) {
			if (payeeLstData.getSrcLst() != null && !payeeLstData.getSrcLst().isEmpty()) {
				Collections.sort(payeeLstData.getSrcLst(), new KEBFTPayeeListCustomerAccountComparator());
				StringBuilder pageBody = new StringBuilder();
				int index = 1;
				for (AccountDetails acctDet : payeeLstData.getSrcLst()) {
					pageBody.append(USSDConstants.NEW_LINE);
					pageBody.append(index++);
					pageBody.append(USSDConstants.DOT_SEPERATOR);
					pageBody.append(acctDet.getMkdActNo());
					menuItemDTO.setPageBody(pageBody.toString());
				}
				menuItemDTO.setPageBody(pageBody.toString());
			}

			USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
			menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
			menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
			menuItemDTO.setPaginationType(PaginationEnum.LISTED);
			setNextScreenSequenceNumber(menuItemDTO);
			responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
			.put(USSDInputParamsEnum.GHIPS_TRANSFER_FROM_ACC_LIST.getTranId(), payeeLstData.getSrcLst());
		}
		return menuItemDTO;
	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SEVEN.getSequenceNo());
	}
}