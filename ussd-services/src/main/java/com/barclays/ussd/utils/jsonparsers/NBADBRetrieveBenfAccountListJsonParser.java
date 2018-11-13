package com.barclays.ussd.utils.jsonparsers;

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
import com.barclays.ussd.utils.jsonparsers.bean.otherbarclaysfundtx.OBAFTBeneficiary;

public class NBADBRetrieveBenfAccountListJsonParser implements BmgBaseJsonParser {
    @Autowired
    private ListValueResServiceImpl listValueResService;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(NBADBRetrieveBenfAccountListJsonParser.class);

    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = null;

	try {
	    List<OBAFTBeneficiary> benfList = (List<OBAFTBeneficiary>) responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
		    .get(USSDInputParamsEnum.NON_BARC_DEL_BENF_PAYEE.getTranId());
	    menuDTO = renderMenuOnScreen(benfList, responseBuilderParamsDTO);
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
     * @param initPayData
     * @param responseBuilderParamsDTO
     * @return MenuItemDTO
     * @throws USSDBlockingException
     */
    private MenuItemDTO renderMenuOnScreen(List<OBAFTBeneficiary> benfList, ResponseBuilderParamsDTO responseBuilderParamsDTO)
	    throws USSDNonBlockingException {
	int index = 1;
	StringBuilder pageBody = new StringBuilder();
	MenuItemDTO menuItemDTO = new MenuItemDTO();

	String sysPrefMaxBen = getSystemPreferenceData(responseBuilderParamsDTO.getUssdSessionMgmt().getUserProfile(),
		SystemPreferenceConstants.SYS_PARAM_BNF, SystemPreferenceConstants.OTHMAX_BENEFICIARIES);

	boolean check = StringUtils.isNotEmpty(sysPrefMaxBen) && StringUtils.isNumeric(sysPrefMaxBen);
	Integer maxBen = 0;
	if (check) {
	    maxBen = Integer.parseInt(sysPrefMaxBen);
	}
	if (benfList != null && !benfList.isEmpty()) {
	    for (OBAFTBeneficiary benef : benfList) {
		if ((index - 1) >= maxBen) {
		    break;
		}
		pageBody.append(USSDConstants.NEW_LINE);
		pageBody.append(index);
		pageBody.append(USSDConstants.DOT_SEPERATOR);
		pageBody.append(benef.getDisLbl());
		index++;
	    }
	} else {
	    throw new USSDNonBlockingException(USSDExceptions.USSD_NO_ELIGIBLE_ACCTS.getBmgCode());
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
