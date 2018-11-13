/**
 * DelBillrPayeeLstParser.java
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Beneficiery;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.BillerList;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.CatPayeeLst;

/**
 * @author BTCI
 * 
 */
public class DelBillrPayeeRetrLstParser implements BmgBaseJsonParser
// , ScreenSequenceCustomizer
{

    private static final Logger LOGGER = Logger.getLogger(DelBillrPayeeLstParser.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.ussd.utils.BmgBaseJsonParser#parseJsonIntoJava(com.barclays .ussd.bmg.dto.ResponseBuilderParamsDTO)
     */
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {

	MenuItemDTO menuDTO = new MenuItemDTO();
	ObjectMapper mapper = new ObjectMapper();

	BillerList billerLst = null;
	try {
	    billerLst = mapper.readValue(responseBuilderParamsDTO.getJsonString(), BillerList.class);

	    if (billerLst != null) {
		if (billerLst.getPayHdr() != null && USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(billerLst.getPayHdr().getResCde())) {
		    // menuDTO = renderMenuOnScreen(billerLst.getPayData(), responseBuilderParamsDTO);
		    USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();

		    for (CatPayeeLst ele : billerLst.getPayData().getCatzedPayLst()) {
			if (USSDConstants.BILL_PAYMENT_PAY_CATEGORY.equalsIgnoreCase(ele.getPayCat())) {
			    if (ussdSessionMgmt.getTxSessions() == null) {
				ussdSessionMgmt.setTxSessions(new HashMap<String, Object>(5));
			    }
			    ussdSessionMgmt.getTxSessions().put(USSDInputParamsEnum.DEL_BILLERS_LIST.getTranId(), ele.getBnfLst());
			}
		    }
		} else if (billerLst.getPayHdr() != null) {
		    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
		    throw new USSDNonBlockingException(billerLst.getPayHdr().getResCde());
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

    public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt) throws USSDBlockingException {
	int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWO.getSequenceNo();
	Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
	List<Beneficiery> benfList = (List<Beneficiery>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.DEL_BILLERS_LIST.getTranId());
	if (benfList != null && benfList.size() == 1) {
	    userInputMap = userInputMap == null ? new HashMap<String, String>() : userInputMap;
	    userInputMap.put(USSDInputParamsEnum.DEL_BILLERS_LIST.getParamName(), USSDConstants.DEFAULT_OPTION_SELECTION);
	    ussdSessionMgmt.getUserTransactionDetails().setUserInputMap(userInputMap);
	    seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_THREE.getSequenceNo();
	}
	return seqNo;
    }

}
