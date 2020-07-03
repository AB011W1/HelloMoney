package com.barclays.ussd.utils.jsonparsers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.dto.BillerCreditDTO;
import com.barclays.bmg.service.impl.BillerServiceImpl;
import com.barclays.bmg.service.request.BillerServiceRequest;
import com.barclays.bmg.service.response.BillerServiceResponse;
import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.BillersListDO;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.ScreenSequenceCustomizer;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDInputParamsEnum;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.airtime.Biller;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.Beneficiery;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.BillDetails;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.BillDetailsForControlNumber;

public class DataBundleAirtimeJsonParser implements BmgBaseJsonParser,ScreenSequenceCustomizer{

	private static final Logger LOGGER = Logger.getLogger(DataBundleAirtimeJsonParser.class);
	private static final String DATA_BUNDLE_ACCOUNT="label.select.data.bundle.account";
	private static final String DATA_BUNDLE_SELECT="label.select.data.bundle.buy";
	
	@Autowired
    private BillerServiceImpl billerService;
	
	@Override
	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO)
			throws USSDBlockingException, USSDNonBlockingException {

		MenuItemDTO menuDTO = null;
		ObjectMapper mapper = new ObjectMapper();

		try {

			BillDetailsForControlNumber billDetailsForControlNumber = mapper.readValue(responseBuilderParamsDTO.getJsonString(), BillDetailsForControlNumber.class);
			if (billDetailsForControlNumber != null) {
				USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
				if (billDetailsForControlNumber.getPayHdr() != null
						&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(billDetailsForControlNumber.getPayHdr().getResCde())) {

					if(null != ussdSessionMgmt.getTxSessions()){

						ussdSessionMgmt.getTxSessions().put("DataBundleDetails", billDetailsForControlNumber.getPayData());
						ussdSessionMgmt.getTxSessions().put("DataBundleInvoiceDetail", billDetailsForControlNumber.getPayData().getBillInvoiceDetails().getProbaseDetails());
						Iterator it = billDetailsForControlNumber.getPayData().getBillInvoiceDetails().getProbaseDetails().entrySet().iterator();

						while (it.hasNext()) {
							Map.Entry pair = (Map.Entry)it.next();
							ussdSessionMgmt.getTxSessions().put(pair.getKey().toString(), pair.getValue().toString());
						}

					}else {
						Map<String, Object> txSessions = new HashMap<String, Object>();
						txSessions.put("DataBundleDetails", billDetailsForControlNumber.getPayData());
						txSessions.put("DataBundleInvoiceDetail", billDetailsForControlNumber.getPayData().getBillInvoiceDetails().getProbaseDetails());
						ussdSessionMgmt.setTxSessions(txSessions);
					}
					menuDTO = renderMenuOnScreen(billDetailsForControlNumber.getPayData(), responseBuilderParamsDTO);

				}else if (billDetailsForControlNumber.getPayHdr() != null) {
					LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
					throw new USSDNonBlockingException(billDetailsForControlNumber.getPayHdr().getResCde());
				} else {
					LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
					throw new USSDNonBlockingException(USSDExceptions.USSD_TECH_ISSUE.getBmgCode());
				}
			}
		}   catch (JsonParseException e) {
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
	
	private MenuItemDTO renderMenuOnScreen(BillDetails payData, ResponseBuilderParamsDTO responseBuilderParamsDTO) throws ParseException {

		MenuItemDTO menuItemDTO = new MenuItemDTO();
		StringBuilder pageBody = new StringBuilder();
		USSDSessionManagement ussdSessionMgmt=responseBuilderParamsDTO.getUssdSessionMgmt();
		UssdResourceBundle ussdResourceBundle = responseBuilderParamsDTO.getUssdResourceBundle();
		Locale locale = new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode());
		Map<String, String> userInputMap = responseBuilderParamsDTO.getUssdSessionMgmt().getUserTransactionDetails().getUserInputMap();
		String accountMobileLabel = ussdResourceBundle.getLabel(DATA_BUNDLE_ACCOUNT, locale);
		/*String dataBundleSelect =  ussdResourceBundle.getLabel(DATA_BUNDLE_SELECT, locale);*/
			
		/*String account_Mobile = null;
		if(null != ussdSessionMgmt.getTxSessions().get("label"))
			account_Mobile = ussdSessionMgmt.getTxSessions().get("label").toString();
	    if(null != account_Mobile && account_Mobile.toUpperCase().contains("MOBILE"))
	    	account_Mobile = "Mobile Number";
	    else if(null != account_Mobile && account_Mobile.toUpperCase().contains("ACCOUNT"))
	    	account_Mobile = "Account Number";*/
		
		BillDetails billDetails = new BillDetails();
		if(null != ussdSessionMgmt && null != ussdSessionMgmt.getTxSessions()){
			billDetails = (BillDetails) ussdSessionMgmt.getTxSessions().get("DataBundleDetails");
		}
		/*if(null != account_Mobile) {*/
			pageBody.append(accountMobileLabel);
			pageBody.append(USSDConstants.SPACE);
			/*pageBody.append(account_Mobile);
			pageBody.append(USSDConstants.SPACE);*/
			pageBody.append(userInputMap.get(USSDInputParamsEnum.AIRTIME_MOB_NUM.getParamName()) + ".");
			pageBody.append(USSDConstants.SPACE);
		
		
		/*pageBody.append(dataBundleSelect);*/
		pageBody.append(USSDConstants.NEW_LINE);
		if(null != billDetails)
		{
			Iterator it = billDetails.getBillInvoiceDetails().getProbaseDetails().entrySet().iterator();
			List<String> bundleLife = billDetails.getBillInvoiceDetails().getBundleLife();
			int count = 1,i=0;
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				pageBody.append(count + ". " + pair.getKey().toString() + " " + pair.getValue().toString());
				pageBody.append(USSDConstants.SPACE);
              	if(bundleLife.size() > 0)
					pageBody.append(bundleLife.get(i));
				pageBody.append(USSDConstants.NEW_LINE);
				count++;
				i++;
			}
			menuItemDTO.setPageBody(pageBody.toString());
			USSDUtils.appendHomeAndBackOption(menuItemDTO, responseBuilderParamsDTO);
			menuItemDTO.setPageFooter(menuItemDTO.getPageFooter());
			menuItemDTO.setPageHeader(responseBuilderParamsDTO.getHeaderId());
			menuItemDTO.setStatus(USSDConstants.STATUS_CONTINUE);
			menuItemDTO.setPaginationType(PaginationEnum.SPACED);
			setNextScreenSequenceNumber(menuItemDTO);
		}
		return menuItemDTO;
	}

	@Override
	public void setNextScreenSequenceNumber(MenuItemDTO menuItemDTO) {
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo());
		
	}

	@Override
	public int getCustomNextScreen(String userInput, USSDSessionManagement ussdSessionMgmt)
			throws USSDBlockingException {
		int seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_FIVE.getSequenceNo();
 		Map<String, Object> txSessions = ussdSessionMgmt.getTxSessions();
 		Map<String, String> userInputMap = ussdSessionMgmt.getUserTransactionDetails().getUserInputMap();
 		BillerServiceRequest request = new BillerServiceRequest();

 		if(BillPaymentConstants.AT_MW_SAVED_BENEF.equalsIgnoreCase(userInputMap.get("AT_MW_SAVED_BENEF"))){
			List<Beneficiery> beneficiaryList = (List<Beneficiery>) txSessions.get(USSDInputParamsEnum.AIRTIME_TOPUP_PAYEE_LIST.getTranId());
 			String selectedAirtimeTopupProvider = userInputMap.get(USSDInputParamsEnum.AIRTIME_TOPUP_PAYEE_LIST.getParamName());
 			Beneficiery airtimeTopupProviderSelected = beneficiaryList.get(Integer.parseInt(selectedAirtimeTopupProvider) - 1);
 			request.setBillerId(airtimeTopupProviderSelected.getBillerId());
 		} else {
 			List<Biller> mnoList = (List<Biller>) ussdSessionMgmt.getTxSessions().get(USSDInputParamsEnum.AIRTIME_MNO_LIST.getTranId());
			String selectedAirtimeTopupProvider = userInputMap.get(USSDInputParamsEnum.AIRTIME_MNO_LIST.getParamName());
			Biller airtimeTopupProviderSelected = mnoList.get(Integer.parseInt(selectedAirtimeTopupProvider) - 1);
			request.setBillerId(airtimeTopupProviderSelected.getBillerId());
 		}
 		request.setBusinessId(ussdSessionMgmt.getBusinessId());

 		BillerServiceResponse billerServiceResponse = billerService.getActionCodeForBillerId(request);
 		LOGGER.debug("billerServiceResponse response:"+ billerServiceResponse + "Biller Id:"+request.getBillerId());
 		if (billerServiceResponse.getBillerCreditDTO() != null) {
 			BillerCreditDTO billerCreditDTO = billerServiceResponse.getBillerCreditDTO();
 			LOGGER.debug("billerCreditDTO response:"+ billerCreditDTO);
 			txSessions.put("BillerCreditDTO",billerCreditDTO);
 			String actionCode = billerServiceResponse.getBillerCreditDTO().getActionCode();
 			if (actionCode != null && !actionCode.isEmpty()) {
 				seqNo = USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWENTYTWO.getSequenceNo();
 			}
 		}

 		return seqNo;
	}

}
