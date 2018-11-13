package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.jsonparsers.bean.otherbarclaysfundtx.OBAFTBeneficiary;
import com.barclays.ussd.utils.jsonparsers.bean.otherbarclaysfundtx.OBAFTInitPayData;
import com.barclays.ussd.utils.jsonparsers.bean.otherbarclaysfundtx.OBAFTPayeeAcct;
import com.barclays.ussd.utils.jsonparsers.bean.otherbarclaysfundtx.OBAFTRetrievePayee;

public class GhipsDelBenfListJsonParser implements BmgBaseJsonParser {
    private static final Logger LOGGER = Logger.getLogger(GhipsDelBenfListJsonParser.class);
    private String GHIPPS_PAYEE_TYPE="GHIPPSPAY";
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	MenuItemDTO menuDTO = new MenuItemDTO();
	ObjectMapper mapper = new ObjectMapper();

	try {

	    OBAFTRetrievePayee accList = mapper.readValue(responseBuilderParamsDTO.getJsonString(), OBAFTRetrievePayee.class);
	    if (accList != null) {
		if (accList.getPayHdr() != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(accList.getPayHdr().getResCde())) {
		    // set the payee accnt list to the session
		    List<OBAFTBeneficiary> bnfLst = getBeneficiaries(accList.getPayData());
		    if (responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions() == null) {
			responseBuilderParamsDTO.getUssdSessionMgmt().setTxSessions(new HashMap<String, Object>(3));
		    }
		    responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions()
			    .put(USSDInputParamsEnum.GHIPS_DELETE_BENEF_PAYEE.getTranId(), bnfLst);

		} else if (accList.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(accList.getPayHdr().getResCde());
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
	setNextScreenSequenceNumber(menuDTO);

	return menuDTO;
    }

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
	menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo());
    }

    /**
     * @param initPayData
     * @return List<OBAFTBeneficiary>
     */
    private List<OBAFTBeneficiary> getBeneficiaries(OBAFTInitPayData initPayData) {
	List<OBAFTBeneficiary> bnfLst = null;
	if (initPayData != null && initPayData.getPayLst() != null && !initPayData.getPayLst().isEmpty()) {
	    bnfLst = new ArrayList<OBAFTBeneficiary>();
	    for (OBAFTPayeeAcct ele : initPayData.getPayLst()) {
		String payCat = ele.getPayCat();
		if (StringUtils.isNotEmpty(payCat) && GHIPPS_PAYEE_TYPE.equalsIgnoreCase(payCat)) {
		    bnfLst.addAll(ele.getBnfLst());
		}
	    }
	}
	return bnfLst;
    }
}
