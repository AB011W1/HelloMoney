/**
 *
 */
package com.barclays.ussd.utils.jsonparsers;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.dao.product.impl.ComponentResDAOImpl;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.BillersListDO;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.SystemPreferenceValidator;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;

/**
 * @author BTCI
 *
 */
public class OneTimeBillPayBillerRefParser implements BmgBaseJsonParser, SystemPreferenceValidator {

	private static final Logger LOGGER = Logger.getLogger(OneTimeBillPayBillerRefParser.class);

	@Autowired
	ComponentResDAOImpl componentResDAOImpl;
	private String strWUCFieldChk;

    @Override
    public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
	return renderMenuOnScreen(responseBuilderParamsDTO);
    }

    private MenuItemDTO renderMenuOnScreen(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDNonBlockingException {
    	USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
	 	MenuItemDTO menuItemDTO = new MenuItemDTO();
	 	USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
	 	UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
		Locale locale = new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode());

		// WUC Change - Water Biller 19/06/2017
		String wucBillerCategory = null;
		if(null != ussdSessionMgmt && null != ussdSessionMgmt.getTxSessions()){
			wucBillerCategory = (String)ussdSessionMgmt.getTxSessions().get("wucBillerCategory");
			LOGGER.debug("Check for MasterPass tranid : wucBillerCategory: "+ wucBillerCategory);
		}
		if(wucBillerCategory != null && wucBillerCategory !="" ){
			if(wucBillerCategory.equalsIgnoreCase("WUC-2") && ussdSessionMgmt.getBusinessId().equalsIgnoreCase("BWBRB")){
				strWUCFieldChk = "WUCScreenSeqNo";
				menuItemDTO.setPageHeader("LBL9999");
				menuItemDTO.setPageBody(responseBuilderParamsDTO.getUssdResourceBundle().getLabel("label.enter.wuccustomernum", locale));
			}
		}else{
			strWUCFieldChk = "notWUCScreenSeqNo";
			String ubpBusinessId = componentResDAOImpl.getUBPBusinessId();
			if (ubpBusinessId.contains(ussdSessionMgmt.getBusinessId())) {
				menuItemDTO.setPageHeader("LBL9999");

		         //masterpass QR
			    String business_id=ussdSessionMgmt.getBusinessId();
			    String tranid=ussdSessionMgmt.getUserTransactionDetails().getCurrentRunningTransaction().getTranId();
			    LOGGER.debug("Check for MasterPass tranid: "+ tranid);
			    if((business_id!=null && "TZBRB".equals(business_id))  &&  (tranid!=null && "TZ_MASTERPASS_QR_BILLER".equals(tranid))){
			    	LOGGER.debug("Biller Validate :"+ussdSessionMgmt.getTxSessions().get(USSDConstants.MPQR_BILLER_INFO));
			    	menuItemDTO.setPageBody(componentResDAOImpl.getBillerLabelByKey(
							ussdSessionMgmt.getTxSessions().get(USSDConstants.MPQR_BILLER_INFO).toString(),
									ussdSessionMgmt.getBusinessId(), locale.getLanguage()));
			    }
			    else{

			    	menuItemDTO.setPageBody(componentResDAOImpl.getBillerLabelByKey(
							ussdSessionMgmt.getUserTransactionDetails()
									.getUserInputMap().get(USSDConstants.SELECTED_BILLER_OTBP),
									ussdSessionMgmt.getBusinessId(), locale.getLanguage()));
			    }



			} else {
				String headerID = USSDUtils.getCustomerReferenceId(
						ussdResourceBundle, locale, ussdSessionMgmt
								.getUserTransactionDetails().getUserInputMap().get(
										USSDConstants.SELECTED_BILLER_OTBP));
				// CR-57
				if (ussdSessionMgmt.getBusinessId().equalsIgnoreCase(
						USSDConstants.BUSINESS_ID_ZWBRB)
						&& ussdSessionMgmt.getUserTransactionDetails()
								.getUserInputMap().get(
										USSDConstants.SELECTED_BILLER_OTBP)
								.equalsIgnoreCase("DSTVZIM-2")) {
					headerID = headerID + "OTP";
				}

				menuItemDTO.setPageHeader(headerID);
			}
		}
		menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
		menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
		menuItemDTO.setPaginationType(PaginationEnum.NOT_REQD);
		setNextScreenSequenceNumber(menuItemDTO);
		return menuItemDTO;
	}

    @Override
    public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
    	if(strWUCFieldChk != null && strWUCFieldChk != ""){
	    	if(strWUCFieldChk.equalsIgnoreCase("WUCScreenSeqNo")){
	    		// WUC specific screen sequence number
	    		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SEVENTEEN.getSequenceNo());
	    	}else if(strWUCFieldChk.equalsIgnoreCase("notWUCScreenSeqNo")){
	    		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_SIX.getSequenceNo());
	    	}
    	}
    }

	@SuppressWarnings("unchecked")
	@Override
	public void validate(String userInput, USSDSessionManagement ussdSessionMgmt)
			throws USSDBlockingException, USSDNonBlockingException {

		List<BillersListDO> billers = (List<BillersListDO>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_BLRS_LST.getTranId());
		String selectedBillerId = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap().get(USSDInputParamsEnum.ONE_TIME_BILL_PYMNT_BLRS_LST.getParamName());
		BillersListDO biller = new BillersListDO();
		if(null != selectedBillerId && null != billers){
			int pos = Integer.parseInt(selectedBillerId);
			biller = billers.get(pos-1);
		}
		if(null != biller.getRefNoValidation_1()){
			Pattern p = Pattern.compile(biller.getRefNoValidation_1());
			Matcher m = p.matcher(userInput);
			if(!m.matches()){
				LOGGER.error("Invalid Number: " + userInput);
				USSDNonBlockingException e = null;
				if(("TZBRB").equalsIgnoreCase(ussdSessionMgmt.getBusinessId()) && "DAWASCO".equalsIgnoreCase(biller.getBillerId())){
					e = new USSDNonBlockingException(USSDExceptions.USSD_INVALID_CONTROL_NO.getUssdErrorCode());
				} else if("GEPG".equalsIgnoreCase(biller.getBillAggregatorId())) {
					e = new USSDNonBlockingException(USSDExceptions.USSD_INVALID_METER_NO.getUssdErrorCode());
				}
				if(null != e){
					e.addErrorParam(userInput);
					e.setBackFlow(true);
					throw e;
				}
			}
		}
	}

}