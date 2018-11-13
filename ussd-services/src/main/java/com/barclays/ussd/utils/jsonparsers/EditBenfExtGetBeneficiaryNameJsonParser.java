package com.barclays.ussd.utils.jsonparsers;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
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
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.BeneData;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.PayeeList;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.PayeeLstData;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.otherbank.ToList;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.AccountDetails;


	public class EditBenfExtGetBeneficiaryNameJsonParser implements BmgBaseJsonParser {

	    /** The Constant LOGGER. */
	    private static final Logger LOGGER = Logger.getLogger(EditBenfExtGetBeneficiaryNameJsonParser.class);

	    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
		MenuItemDTO menuDTO = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
		    PayeeList payeeList = mapper.readValue(responseBuilderParamsDTO.getJsonString(), PayeeList.class);
		    if (payeeList != null) {
			if (payeeList.getPayHdr() != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(payeeList.getPayHdr().getResCde())) {
			    Collections.sort(payeeList.getPayData().getSrcLst(), new KEBFTPayeeListCustomerAccountComparator());
			    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, payeeList.getPayData(), "");
			    setOutputInSession(responseBuilderParamsDTO, payeeList);

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
	    private void setOutputInSession(ResponseBuilderParamsDTO responseBuilderParamsDTO, PayeeList payeeList) {
		PayeeLstData payeeLstData = payeeList.getPayData();
		if (payeeLstData != null) {
		    Map<String, Object> txSessionMap = new HashMap<String, Object>(5);
		    responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(txSessionMap);
		    // if (payeeLstData.getSrcLst() != null && !payeeLstData.getSrcLst().isEmpty()) {
		    // responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.EXT_BANK_FT_SEL_FRM_AC.getTranId(),
		    // payeeLstData.getSrcLst());
		    // }
		    List<ToList> lstFTto = payeeLstData.getPayLst();
		    if (lstFTto != null && !lstFTto.isEmpty()) {
			ToList toList = lstFTto.get(0);
			if (toList != null && toList.getBnfLst() != null && !toList.getBnfLst().isEmpty()) {
			    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().put(USSDInputParamsEnum.EDIT_BENF_BENEFICIARY_NAME.getTranId(),
				    toList.getBnfLst());

			}
		    }
		}

	    }

	    /**
	     * @param responseBuilderParamsDTO
	     * @param payeeLstData
	     * @param warningMsg
	     * @return MenuItemDTO
	     * @throws USSDNonBlockingException
	     */
	    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, PayeeLstData payeeLstData, String warningMsg)
		    throws USSDNonBlockingException {
		MenuItemDTO menuItemDTO = null;
		if (payeeLstData != null) {
		    List<ToList> lstFTto = payeeLstData.getPayLst();
		    if (lstFTto != null && !lstFTto.isEmpty()) {
			ToList toList = lstFTto.get(0);
			if (toList != null && toList.getBnfLst() != null && !toList.getBnfLst().isEmpty()) {
			    int index = 1;
			    StringBuilder pageBody = new StringBuilder();
			    if (StringUtils.isNotBlank(warningMsg)) {
				pageBody.append(warningMsg);
			    }
			    menuItemDTO = new MenuItemDTO();
			    for (BeneData ele : toList.getBnfLst()) {
				pageBody.append(USSDConstants.NEW_LINE);
				pageBody.append(index);
				pageBody.append(USSDConstants.DOT_SEPERATOR);
				pageBody.append(ele.getDisLbl());
				index++;
			    }
			    menuItemDTO.setPageBody(pageBody.toString());
			    // insert the back and home options
			    USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
			    menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
			    menuItemDTO.setPaginationType(PaginationEnum.LISTED);
			    setNextScreenSequenceNumber(menuItemDTO);
			} else {
			    throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_ACCTS.getBmgCode());
			}

		    } else {
			throw new USSDNonBlockingException(USSDExceptions.USSD_NO_BENF_EXISTS.getBmgCode());
		    }
		}
		return menuItemDTO;
	    }

	    @Override
	    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());

	    }
	}

	/* This class compares the customer account w.r.t primary indicator */
	class EBFTPayeeListCustomerAccountComparator implements Comparator<AccountDetails>, Serializable {
	    /**
		 *
		 */
	    private static final long serialVersionUID = 1L;

	    public int compare(final AccountDetails accountDetail1, final AccountDetails accountDetail2) {
		int retVal = 0;
		if (StringUtils.equalsIgnoreCase(USSDConstants.PRIMARY_INDICATOR_YES, accountDetail1.getPriInd())) {
		    retVal = -1;
		} else if (StringUtils.equalsIgnoreCase(USSDConstants.PRIMARY_INDICATOR_YES, accountDetail2.getPriInd())) {
		    retVal = 1;
		} else {
		    retVal = 0;
		}
		return retVal;
	    }
	}

