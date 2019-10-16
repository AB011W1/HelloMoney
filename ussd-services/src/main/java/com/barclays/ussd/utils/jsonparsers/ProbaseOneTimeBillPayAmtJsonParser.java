package com.barclays.ussd.utils.jsonparsers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.barclays.ussd.auth.bean.USSDSessionManagement;
import com.barclays.ussd.bean.BillersListDO;
import com.barclays.ussd.bean.MenuItemDTO;
import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;
import com.barclays.ussd.exception.USSDBlockingException;
import com.barclays.ussd.exception.USSDNonBlockingException;
import com.barclays.ussd.utils.BmgBaseJsonParser;
import com.barclays.ussd.utils.PaginationEnum;
import com.barclays.ussd.utils.USSDConstants;
import com.barclays.ussd.utils.USSDExceptions;
import com.barclays.ussd.utils.USSDSequenceNumberEnum;
import com.barclays.ussd.utils.USSDUtils;
import com.barclays.ussd.utils.UssdResourceBundle;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.BillDetails;
import com.barclays.ussd.utils.jsonparsers.bean.billpay.BillDetailsForControlNumber;

public class ProbaseOneTimeBillPayAmtJsonParser implements BmgBaseJsonParser {

	private static final Logger LOGGER = Logger.getLogger(ProbaseOneTimeBillPayAmtJsonParser.class);
	private static final String NPIN_NUMBER_LABEL="label.enter.bill.npinnumber";
	private static final String PRN_NUMBER_LABEL="label.enter.bill.prnnumber";
	private static final String BILL_PAYMENTDATE_LABEL="label.etoll.payment.date";
	private static final String BILL_AMOUNT_LABEL="label.enter.billAmount";
	private static final String BILL_DETAILS_LABEL="label.enter.billDetails";
	private static final String BILL_CONFIRM_LABEL = "label.confirm";

	@SuppressWarnings("unchecked")
	@Override
	public MenuItemDTO parseJsonIntoJava(ResponseBuilderParamsDTO responseBuilderParamsDTO) throws USSDBlockingException, USSDNonBlockingException {
		MenuItemDTO menuDTO = null;
		ObjectMapper mapper = new ObjectMapper();

		try {

			BillDetailsForControlNumber billDetailsForControlNumber = mapper.readValue(responseBuilderParamsDTO.getJsonString(), BillDetailsForControlNumber.class);
			if (billDetailsForControlNumber != null) {
				USSDSessionManagement ussdSessionMgmt = responseBuilderParamsDTO.getUssdSessionMgmt();
				if (billDetailsForControlNumber.getPayHdr() != null
						&& USSDExceptions.SUCCESS.getBmgCode().equalsIgnoreCase(billDetailsForControlNumber.getPayHdr().getResCde())) {

					if(null != ussdSessionMgmt.getTxSessions()){

						ussdSessionMgmt.getTxSessions().put(USSDConstants.PROBASE_BILL_DETAILS, billDetailsForControlNumber.getPayData());
						ussdSessionMgmt.getTxSessions().put(USSDConstants.PROBASE_INVOICE_DETAILS, billDetailsForControlNumber.getPayData().getBillInvoiceDetails().getProbaseDetails());
						Iterator it = billDetailsForControlNumber.getPayData().getBillInvoiceDetails().getProbaseDetails().entrySet().iterator();

						while (it.hasNext()) {
							Map.Entry pair = (Map.Entry)it.next();
							ussdSessionMgmt.getTxSessions().put(pair.getKey().toString(), pair.getValue().toString());
						}

					}else {
						Map<String, Object> txSessions = new HashMap<String, Object>();
						txSessions.put(USSDConstants.PROBASE_BILL_DETAILS, billDetailsForControlNumber.getPayData());
						txSessions.put(USSDConstants.PROBASE_INVOICE_DETAILS, billDetailsForControlNumber.getPayData().getBillInvoiceDetails().getProbaseDetails());
						ussdSessionMgmt.setTxSessions(txSessions);
					}
					menuDTO = renderMenuOnScreen(billDetailsForControlNumber.getPayData(), responseBuilderParamsDTO);

				} else if(billDetailsForControlNumber.getPayHdr() != null && billDetailsForControlNumber.getPayHdr().getResCde().equals("BEM09999")){
					BillersListDO billersListDO = (BillersListDO) ussdSessionMgmt.getTxSessions().get(USSDConstants.PROBASE_BILLER_INFO);
					if(null!=billersListDO && billersListDO.getBillerCategoryId().equalsIgnoreCase("ZRA"))
					{
						throw new USSDNonBlockingException(billDetailsForControlNumber.getPayHdr().getResCde()+"ZRA");
					}
					else
					{
						throw new USSDNonBlockingException(billDetailsForControlNumber.getPayHdr().getResCde()+"NAPSA");
					}
//				} else if(billDetailsForControlNumber.getPayHdr() != null && billDetailsForControlNumber.getPayHdr().getResCde().equalsIgnoreCase("BEM500")){
//					LOGGER.error("Error while servicing " + responseBuilderParamsDTO.getBmgOpCode());
//					throw new USSDNonBlockingException(billDetailsForControlNumber.getPayHdr().getResCde());
				} else if (billDetailsForControlNumber.getPayHdr() != null) {
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
		Locale locale = null;
		if(null != ussdSessionMgmt )
			locale = new Locale(ussdSessionMgmt.getUserProfile().getLanguage(), ussdSessionMgmt.getUserProfile().getCountryCode());

		String npinNumber = ussdResourceBundle.getLabel(NPIN_NUMBER_LABEL, locale);
		String prnNumber =  ussdResourceBundle.getLabel(PRN_NUMBER_LABEL, locale);
		String paymentDate = ussdResourceBundle.getLabel(BILL_PAYMENTDATE_LABEL, locale);
		String amountLabel = ussdResourceBundle.getLabel(BILL_AMOUNT_LABEL, locale);
		String billDetailsLabel = ussdResourceBundle.getLabel(BILL_DETAILS_LABEL, locale);
		String confirmLabel = ussdResourceBundle.getLabel(BILL_CONFIRM_LABEL, locale);

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());

		BillDetails billDetails = new BillDetails();
		if(null != ussdSessionMgmt && null != ussdSessionMgmt.getTxSessions()){
			billDetails = (BillDetails) ussdSessionMgmt.getTxSessions().get(USSDConstants.PROBASE_BILL_DETAILS);
		}
		BillersListDO billersListDO = null;
		if(null != ussdSessionMgmt)
			billersListDO = (BillersListDO) ussdSessionMgmt.getTxSessions().get(USSDConstants.PROBASE_BILLER_INFO);
		pageBody.append(billDetailsLabel);
		pageBody.append(USSDConstants.NEW_LINE);
		if(null != billDetails && null != billersListDO)
		{
			if("NAPSA".equalsIgnoreCase(billersListDO.getBillerCategoryId()))
			{
				pageBody.append(npinNumber);
			}
			else if("ZRA".equalsIgnoreCase(billersListDO.getBillerCategoryId()))
			{
				pageBody.append(prnNumber);
			}
			pageBody.append(USSDConstants.SPACE);
			pageBody.append(billDetails.getPrimaryReferenceNumber());
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(amountLabel);
			pageBody.append(USSDConstants.SPACE);
			pageBody.append(billDetails.getFeeAmount().getAmount().doubleValue() + " " + billDetails.getFeeAmount().getCurrency());
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(paymentDate);
			pageBody.append(USSDConstants.SPACE);
			pageBody.append(date);
			pageBody.append(USSDConstants.NEW_LINE);
			pageBody.append(confirmLabel);

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
		menuItemDTO.setNextScreenSequenceNumber(USSDSequenceNumberEnum.SEQUENCE_NUMBER_TWENTYONE.getSequenceNo());

	}
}