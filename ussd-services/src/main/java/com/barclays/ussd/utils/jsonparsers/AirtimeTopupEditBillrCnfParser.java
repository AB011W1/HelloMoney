package com.barclays.ussd.utils.jsonparsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
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
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Beneficiery;
import com.barclays.ussd.utils.jsonparsers.bean.fundtransfer.ownfundtransfer.DelBenefConfirm;
import com.barclays.ussd.utils.jsonparsers.bean.regbiller.ValidateRegBillerBean;
import com.barclays.ussd.utils.jsonparsers.bean.regbiller.ValidateRegBillerPayData;

public class AirtimeTopupEditBillrCnfParser implements BmgBaseJsonParser {
	  private static final String AIRTIME_SAVE_BNF_SUCCESS_LABEL = "label.airtime.editbenf.success";
	    private static final Logger LOGGER = Logger.getLogger(AirtimeTopupEditBillrCnfParser.class);

	    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
		MenuItemDTO menuDTO = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
		    ValidateRegBillerBean regBillerValidateObj = mapper.readValue(responseBuilderParamsDTO.getJsonString(), ValidateRegBillerBean.class);
		   // regBillerValidateObj.getPayHdr().setResCde("00000");
		    if (regBillerValidateObj != null) {
			if (regBillerValidateObj.getPayHdr() != null
				&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(regBillerValidateObj.getPayHdr().getResCde())) {
			    menuDTO = renderMenuOnScreen(regBillerValidateObj.getPayData(), responseBuilderParamsDTO);
			} else if (regBillerValidateObj.getPayHdr() != null) {
			    LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
			    throw new USSDNonBlockingException(regBillerValidateObj.getPayHdr().getResCde());
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
	     * @param validateRegBillerPayData
	     * @param responseBuilderParamsDTO
	     * @return MenuItemDTO
	     */
	    private MenuItemDTO renderMenuOnScreen(ValidateRegBillerPayData validateRegBillerPayData, ResponseBuilderParamsDTO responseBuilderParamsDTO) {
		MenuItemDTO menuItemDTO = new MenuItemDTO();
		StringBuilder pageBody = new StringBuilder();
		USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
		if (validateRegBillerPayData != null) {

			Beneficiery bene =null;
			if (responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions() != null
				&& responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(USSDInputParamsEnum.AIRTIME_TOPUP_PAYEE_LIST.getTranId()) != null) {
			    List<Beneficiery> lstBenef = (List<Beneficiery>) responseBuilderParamsDTO.getUssdSessionMgmt().getTxSessions().get(
				    USSDInputParamsEnum.AIRTIME_TOPUP_PAYEE_LIST.getTranId());
			    if (!lstBenef.isEmpty()) {
			    	bene= lstBenef.get(Integer.parseInt(userInputMap.get(USSDInputParamsEnum.AIRTIME_TOPUP_PAYEE_LIST.getParamName())) - 1);

			    }
			}
		    String nickName = bene.getDisLbl();
		    List<String> params = new ArrayList<String>(1);
			params.add(nickName);
			String[] paramArray = params.toArray(new String[params.size()]);
			String successMessage =  responseBuilderParamsDTO.getUssdResourceBundle().getLabel(AIRTIME_SAVE_BNF_SUCCESS_LABEL, paramArray,
					new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode()));

		    pageBody.append(successMessage);
		}
		menuItemDTO.setPageBody(pageBody.toString());
		USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
		menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
		menuItemDTO.setStatus(USSDConstants.STATUS_END);
		menuItemDTO.setPaginationType(PaginationEnum.SPACED);
		setNextScreenSequenceNumber(menuItemDTO);
		return menuItemDTO;
	    }

	    @Override
	    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_ENDS.getSequenceNo());

	    }

}
