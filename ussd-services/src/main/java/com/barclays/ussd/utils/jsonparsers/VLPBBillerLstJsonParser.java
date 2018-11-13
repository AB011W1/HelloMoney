/**
 * VLPBBillerLstJsonParser.java
 */
package com.barclays.ussd.utils.jsonparsers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.ussd.auth.bean.UserProfile;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.sysprefs.services.ListValueCacheDTO;
import com.barclays.ussd.sysprefs.services.ListValueResByGroupServiceResponse;
import com.barclays.ussd.sysprefs.services.ListValueResServiceImpl;
import com.barclays.ussd.sysprefs.services.ListValueResServiceRequest;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.SystemPreferenceConstants;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.jsonparsers.bean.vlpb.BillerListVO;

/**
 * @author BTCI
 * 
 */
public class VLPBBillerLstJsonParser implements BmgBaseJsonParser {

    @Autowired
    private ListValueResServiceImpl listValueResService;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(VLPBBillerLstJsonParser.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.ussd.utils.BmgBaseJsonParser#parseJsonIntoJava(com.barclays .ussd.bmg.dto.ResponseBuilderParamsDTO)
     */
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;
	try {
	    List<BillerListVO> vLPBBillerList = (List<BillerListVO>) responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
		    .get(USSDInputParamsEnum.VLPB_BILLER_LST.getTranId());
	    if (vLPBBillerList == null || vLPBBillerList.isEmpty()) {
		throw new USSDNonBlockingException(USSDExceptions.USSD_VLPB_NOT_PRESENT.getUssdErrorCode());
	    }
	    menuDTO = renderMenuOnScreen(responseBuilderParamsDTO, vLPBBillerList);
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
     * @param lstBillers
     * @return MenuItemDTO
     * @throws ParseException
     * @throws USSDBlockingException
     */
    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO, List<BillerListVO> vLPBBillerList)
	    throws USSDNonBlockingException, ParseException {
	StringBuilder pageBody = new StringBuilder();
	MenuItemDTO menuItemDTO = new MenuItemDTO();

	String sysPrefBPMin = getSystemPreferenceData(responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile(),
		SystemPreferenceConstants.SYS_PARAM_BP, SystemPreferenceConstants.LAST_PAID_BILL_LIST);
	boolean check = StringUtils.isNotEmpty(sysPrefBPMin) && StringUtils.isNumeric(sysPrefBPMin);

	if (vLPBBillerList != null && !vLPBBillerList.isEmpty()) {
	    pageBody.append(USSDConstants.NEW_LINE);
	    int index = 1;
	    for (BillerListVO ele : vLPBBillerList) {

		if (check && (index - 1) >= Integer.parseInt(sysPrefBPMin)) {
		    break;
		}

		pageBody.append(index);
		pageBody.append(USSDConstants.DOT_SEPERATOR);

		pageBody.append(ele.getBillerName());
		pageBody.append(" - ");
		String billDate = ele.getBillDate();
		// SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		String fomattedDate = sf.format(formatter.parse(billDate));
		pageBody.append(fomattedDate);
		pageBody.append(USSDConstants.NEW_LINE);
		index++;
	    }
	}
	menuItemDTO.setPageBody(pageBody.toString());
	// insert the back and home options
	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
	menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
	menuItemDTO.setPaginationType(PaginationEnum.LISTED);
	setNextScreenSequenceNumber(menuItemDTO);
	return menuItemDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo());

    }

    private String getSystemPreferenceData(UserProfile userProfile, String groupId, String listValueKey) throws USSDNonBlockingException {
	ListValueResServiceRequest listValReq = new ListValueResServiceRequest(userProfile.getCountryCode(), groupId, userProfile.getLanguage(),
		listValueKey);
	ListValueResByGroupServiceResponse listValueByGroup = listValueResService.findListValueResByGroupKey(listValReq);
	ListValueCacheDTO listValueCacheDTO = listValueByGroup.getListValueCacheDTOOneRow();
	if (listValueCacheDTO == null) {
	    LOGGER.fatal("System preferences not set for" + listValReq.getListValueKey());
	    throw new USSDNonBlockingException(USSDExceptions.USSD_SYS_PREF_MISSING.getBmgCode(),
		    USSDExceptions.USSD_SYS_PREF_MISSING.getUssdErrorCode());
	}
	return listValueCacheDTO.getLabel();
    }

}
