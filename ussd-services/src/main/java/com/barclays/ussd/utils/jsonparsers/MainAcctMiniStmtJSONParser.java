/**
 * CasaDetailJSONParser.java
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.common.configuration.ConfigurationManager;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.login.AuthUserData;
import com.barclays.ussd.utils.jsonparsers.bean.login.CustomerMobileRegAcct;
import com.barclays.ussd.utils.jsonparsers.bean.ministmt.AccountActivity;
import com.barclays.ussd.utils.jsonparsers.bean.ministmt.MiniStatement;
import com.barclays.ussd.utils.jsonparsers.bean.ministmt.MiniStatmentPayData;

/**
 * @author BTCI This class is used for Casa Detail Parsing. Change CasaDetailJSONParser also in case of any structural or functional changes
 * 
 */
public class MainAcctMiniStmtJSONParser implements BmgBaseJsonParser {

    private static final String DEFAULT_MINI_STMT_CNT = "10";
    private static final Logger LOGGER = Logger.getLogger(MainAcctMiniStmtJSONParser.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.ussd.utils.BmgBaseJsonParser#parseJsonIntoJava(com.barclays .ussd.bmg.dto.ResponseBuilderParamsDTO)
     */
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	String jsonString = responseBuilderParamsDTO.getJsonString();
	ObjectMapper mapper = new ObjectMapper();
	MenuItemDTO menuDTO = null;
	try {
	    MiniStatement casaDetail = mapper.readValue(jsonString, MiniStatement.class);
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

    /**
     * @param responseBuilderParamsDTO
     * @param miniStatmentPayData
     * @param warningMsg
     * @return MenuItemDTO
     * @throws USSDNonBlockingException
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, MiniStatmentPayData miniStatmentPayData,
	    String warningMsg) throws USSDNonBlockingException {
	MenuItemDTO menuItemDTO = null;

	if (miniStatmentPayData == null || miniStatmentPayData.getActActvLst() == null || miniStatmentPayData.getActActvLst().isEmpty()) {
	    throw new USSDNonBlockingException(USSDExceptions.USSD_NO_MINI_STMT_DETAILS_AVBL.getBmgCode());
	}
	CustomerMobileRegAcct acntDet = getSelectedAcctDetails(responseBuilderParamsDTO.getUssdSessionMgmt());

	if (acntDet != null) {
	    StringBuilder pageBody = new StringBuilder();
	    menuItemDTO = new MenuItemDTO();

	    pageBody.append(USSDConstants.ACNT_NO + acntDet.getMkdActNo());
	    pageBody.append(USSDConstants.NEW_LINE);
	    int i = 0;

	    for (AccountActivity ele : miniStatmentPayData.getActActvLst()) {
		if (i == Integer.parseInt(ConfigurationManager.getString(USSDConstants.MINI_STMT_DETAIL_LIMIT, DEFAULT_MINI_STMT_CNT))) {
		    break;
		}
		pageBody.append(ele.getDt()).append(USSDConstants.COMMA_SEPERATOR);

		if (ele.getCrAmt() != null && !StringUtils.isEmpty(ele.getCrAmt().getAmt())) {
		    pageBody.append(USSDConstants.CR).append(USSDConstants.COMMA_SEPERATOR);
		} else if (ele.getDrAmt() != null && !StringUtils.isEmpty(ele.getDrAmt().getAmt())) {
		    pageBody.append(USSDConstants.DR).append(USSDConstants.COMMA_SEPERATOR);
		}
		// Commented as transaction id has been descoped
		// pageBody.append(ele.getRefNo()).append(USSDConstants.COMMA_SEPERATOR);

		if (ele.getCrAmt() != null && !StringUtils.isEmpty(ele.getCrAmt().getAmt())) {
		    pageBody.append(ele.getCrAmt().getAmt()).append(USSDConstants.SINGLE_WHITE_SPACE).append(acntDet.getAvblBal().getCurr());
		} else if (ele.getDrAmt() != null && !StringUtils.isEmpty(ele.getDrAmt().getAmt())) {
		    pageBody.append(ele.getDrAmt().getAmt()).append(USSDConstants.SINGLE_WHITE_SPACE).append(acntDet.getAvblBal().getCurr());
		}
		pageBody.append(USSDConstants.NEW_LINE);
		i++;
	    }

	    menuItemDTO.setPageBody(pageBody.toString());
	    USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	    menuItemDTO.setPageFooter(menuItemDTO.getPageFooter() + warningMsg);
	    menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	    menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	    menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	} else {
	    throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
	}
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    private CustomerMobileRegAcct getSelectedAcctDetails(USSDSessionManagement ussdSessionMgmt) {
	AuthUserData userAuthData = (AuthUserData) ussdSessionMgmt.getUserAuthObj();
	List<CustomerMobileRegAcct> lstAccntDet = userAuthData.getPayData().getCustActs();
	String actNo = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get(
		USSDInputParamsEnum.MINI_STMT_MAIN_ACCT_SEL_AC.getParamName());
	for (CustomerMobileRegAcct acctDet : lstAccntDet) {
	    if (StringUtils.equalsIgnoreCase(actNo, acctDet.getActNo())) {
		return acctDet;
	    }
	}
	return null;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS.getSequenceNo());
    }
}
