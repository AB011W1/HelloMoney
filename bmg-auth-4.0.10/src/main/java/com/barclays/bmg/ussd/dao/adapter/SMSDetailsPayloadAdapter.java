package com.barclays.bmg.ussd.dao.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import uk.co.barclays.www.rbb.tcvm.Common.ContactDetailsType;
import uk.co.barclays.www.rbb.tcvm.Common.TypeCode;
import uk.co.barclays.www.rbb.tcvm.Notification.DeliveryDetailsType;
import uk.co.barclays.www.rbb.tcvm.NotificationEventType.DynamicFieldType;
import uk.co.barclays.www.rbb.tcvm.NotificationEventType.NotificationEventType;

import com.barclays.bmg.cashsend.operation.request.CashSendOneTimeExecuteOperationRequest;
import com.barclays.bmg.cashsend.operation.response.CashSendOneTimeExecuteOperationResponse;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.CommonConstants;
import com.barclays.bmg.constants.SMSConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dao.core.proxy.util.MaskingMode;
import com.barclays.bmg.dao.core.proxy.util.MaskingRule;
import com.barclays.bmg.dao.core.proxy.util.MaskingRuleImpl;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.operation.accounts.request.ApplyProductConfirmOperationRequest;
import com.barclays.bmg.operation.accounts.request.ApplyProductConfirmOperationResponse;
import com.barclays.bmg.operation.request.ChangePasswordOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.DomesticFundTransferExecuteOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.AddBeneficiaryOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.AddOrgBenefeciaryOperationRequest;
import com.barclays.bmg.operation.response.ChangePasswordOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.DomesticFundTransferExecuteOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.AddBeneficiaryOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.AddOrgBeneficiaryOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.UpdateBeneficiaryOperationResponse;
import com.barclays.bmg.ussd.auth.operation.response.SelfRegistrationExecutionOperationResponse;
import com.barclays.bmg.ussd.auth.service.request.SMSDetailsServiceRequest;
import com.barclays.bmg.utils.DateTimeUtil;
import com.ibm.icu.text.SimpleDateFormat;

public class SMSDetailsPayloadAdapter {

    private static final String DATE_FORMAT = "dd/MM/yy HH:mm:ss";
    private static final String AIR_TIME = "AT";
    private static final String MOBILE_WALLET = "WT";
    private static final String AT_WT_EDT_FLOW = "true";

    public NotificationEventType adaptPayload(WorkContext workContext) {

	DAOContext daoContext = (DAOContext) workContext;
	Object[] args = daoContext.getArguments();
	SMSDetailsServiceRequest request = (SMSDetailsServiceRequest) args[0];

	NotificationEventType notificationEventType = new NotificationEventType();
	String businessId = request.getContext().getBusinessId();

	/* Get values from context */
	Map<String, Object> contextMap = request.getContext().getContextMap();
	String sourceSystemId = (String) contextMap.get(SMSConstants.SMS_SOURCE_SYSTEM_ID);
	String eventRecipientId = (String) contextMap.get(SMSConstants.SMS_EVENT_RECIPIENT_ID);
	String eventIdentifier = (String) contextMap.get(SMSConstants.SMS_EVENT_IDENTIFIER);
	String smsDeliveryMode = (String) contextMap.get(SMSConstants.SMS_DELIVERY_MODE);
	String smsISDCode = (String) contextMap.get(SMSConstants.SMS_ISD_CODE);
	MaskingRule masker = MaskingRuleImpl.getInstance();
	//CR82
	String payGrp ="";
	String editFlow ="";
	if(contextMap.containsKey("payGrp")){
		payGrp=contextMap.get("payGrp").toString();
	}
	if(contextMap.containsKey("isEditFlow")){
		editFlow=contextMap.get("isEditFlow").toString();
	}





	/* Ends getting values from context */

	notificationEventType.setEventId(request.getEvent());// database event id
	notificationEventType.setSourceSystemId(sourceSystemId);
	notificationEventType.setEventRecipientId(eventRecipientId);
	DeliveryDetailsType deliveryDetails = new DeliveryDetailsType();
	ContactDetailsType contactDetails = new ContactDetailsType();
	DeliveryDetailsType[] deliveryDetailsArr = { deliveryDetails };

	TypeCode contactType = new TypeCode();
	contactType.setCode("8");

	contactDetails.setContactType(contactType);
	contactDetails.setCountryCode(request.getContext().getCountryCode());
	// contactDetails.setContactValue(smsISDCode + request.getContext().getMobilePhone());//mobile no.

	contactDetails.setContactValue(request.getContext().getMobilePhone());// mobile no.
	// contactDetails.setContactValue("763211382");//mobile no.

	ContactDetailsType[] contactDetailsArr = { contactDetails };
	deliveryDetails.setContactDetails(contactDetailsArr);
	TypeCode deliveryMode = new TypeCode();
	deliveryMode.setCode(smsDeliveryMode);// 1 for SMS
	deliveryDetails.setDeliveryMode(deliveryMode);
	notificationEventType.setDeliveryDetails(deliveryDetailsArr);

	TypeCode langTypeCode = new TypeCode();
	langTypeCode.set_value(request.getContext().getLanguageId());
	langTypeCode.setCode(request.getContext().getLanguageId());
	notificationEventType.setDeliveryLanguage(langTypeCode);

	TypeCode typeCode = new TypeCode();
	typeCode.set_value(request.getPriority());
	typeCode.setCode(request.getPriority());

	notificationEventType.setEventPriority(typeCode);// database priority
	notificationEventType.setEventIdentifier(eventIdentifier);// add in database

	/* setting dynamic fields according to the event id */
	String activityId = "";
	if (request.getContext().getActivityId() != null) {
	    activityId = request.getContext().getActivityId();
	}

	String dynamicFields = request.getDynamicFields();
	String[] dyamicFieldsArr = null;
	String[] dynamicValues = new String[10];
	if (dynamicFields != null && !dynamicFields.isEmpty()) {
	    dyamicFieldsArr = dynamicFields.split(",");
	}

	if (activityId != null && !activityId.isEmpty()) {
	    if (activityId.equalsIgnoreCase(ActivityIdConstantBean.FUND_TRANSFER_EXTERNAL_PAYEE_ACTIVITY_ID) || activityId.equalsIgnoreCase(ActivityIdConstantBean.GHIPPS_FUND_TRANSFER_OTHER_BANK_ACCOUNTS)) {
		DomesticFundTransferExecuteOperationResponse domesticFundTransferExecuteOperationResponse = (DomesticFundTransferExecuteOperationResponse) request
			.getParentResponse();
		DomesticFundTransferExecuteOperationRequest domesticFundTransferExecuteOperationRequest = (DomesticFundTransferExecuteOperationRequest) request
			.getParentRequest();

		if (domesticFundTransferExecuteOperationResponse != null && domesticFundTransferExecuteOperationResponse.isSuccess()) {
		    // String txnRef = domesticFundTransferExecuteOperationResponse.getTrnRef();

		    String destinationAcct = "";
		    String sourceAcct = "";
		    BigDecimal txnAmt = new BigDecimal(0);
		    String curr = "";

		    if (domesticFundTransferExecuteOperationRequest != null
			    && domesticFundTransferExecuteOperationRequest.getTransactionDTO() != null) {
			if (domesticFundTransferExecuteOperationRequest.getTransactionDTO().getBeneficiaryDTO() != null) {
			    destinationAcct = domesticFundTransferExecuteOperationRequest.getTransactionDTO().getBeneficiaryDTO()
				    .getDestinationAccountNumber();
			    if (destinationAcct != null && !destinationAcct.isEmpty())
				destinationAcct = masker.mask(destinationAcct, MaskingMode.NUMBERPARTIAL);
			}
			sourceAcct = domesticFundTransferExecuteOperationRequest.getTransactionDTO().getSourceAcct().getAccountNumber();
			if (sourceAcct != null && !sourceAcct.isEmpty())
			    sourceAcct = masker.mask(sourceAcct, MaskingMode.NUMBERPARTIAL);
			txnAmt = domesticFundTransferExecuteOperationRequest.getTransactionDTO().getTxnAmt().getAmount();
			curr = domesticFundTransferExecuteOperationRequest.getTransactionDTO().getTxnAmt().getCurrency();
		    }

		    dynamicValues[0] = destinationAcct;
		    dynamicValues[1] = sourceAcct;
		    dynamicValues[2] = txnAmt.toString();
		    dynamicValues[3] = curr;
		    dynamicValues[4] = domesticFundTransferExecuteOperationResponse.getContext().getActivityRefNo();
		    Calendar cal = DateTimeUtil.getCurrentBusinessCalendar(domesticFundTransferExecuteOperationResponse.getContext());
		    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		    String date = sdf.format(cal.getTime());
		    dynamicValues[5] = date;
		} else {
		    // String txnRef = domesticFundTransferExecuteOperationResponse.getTrnRef();
		    String destinationAcct = "";
		    String sourceAcct = "";
		    BigDecimal txnAmt = new BigDecimal(0);
		    String curr = "";
		    if (domesticFundTransferExecuteOperationRequest != null
			    && domesticFundTransferExecuteOperationRequest.getTransactionDTO() != null) {
			if (domesticFundTransferExecuteOperationRequest.getTransactionDTO().getBeneficiaryDTO() != null) {
			    destinationAcct = domesticFundTransferExecuteOperationRequest.getTransactionDTO().getBeneficiaryDTO()
				    .getDestinationAccountNumber();
			    if (destinationAcct != null && !destinationAcct.isEmpty())
				destinationAcct = masker.mask(destinationAcct, MaskingMode.NUMBERPARTIAL);
			}
			if (domesticFundTransferExecuteOperationRequest.getTransactionDTO().getSourceAcct() != null) {
			    sourceAcct = domesticFundTransferExecuteOperationRequest.getTransactionDTO().getSourceAcct().getAccountNumber();
			    if (sourceAcct != null && !sourceAcct.isEmpty())
				sourceAcct = masker.mask(sourceAcct, MaskingMode.NUMBERPARTIAL);
			}
			if (domesticFundTransferExecuteOperationRequest.getTransactionDTO().getTxnAmt() != null) {
			    txnAmt = domesticFundTransferExecuteOperationRequest.getTransactionDTO().getTxnAmt().getAmount();
			    curr = domesticFundTransferExecuteOperationRequest.getTransactionDTO().getTxnAmt().getCurrency();
			}
		    }

		    dynamicValues[0] = destinationAcct;
		    dynamicValues[1] = sourceAcct;
		    dynamicValues[2] = txnAmt.toString();
		    dynamicValues[3] = curr;
		    dynamicValues[4] = domesticFundTransferExecuteOperationRequest.getContext().getActivityRefNo();
		    Calendar cal = DateTimeUtil.getCurrentBusinessCalendar(domesticFundTransferExecuteOperationRequest.getContext());
		    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		    String date = sdf.format(cal.getTime());
		    dynamicValues[5] = date;
		    //SHM_Sms sent for transactions CR
		    dynamicValues[6] = getCustomerCareLabel(businessId);
		}

	    } else if (activityId.equalsIgnoreCase(ActivityIdConstantBean.FUND_TRANSFER_INTERNAL_PAYEE_ACTIVITY_ID)
		    || activityId.equalsIgnoreCase(ActivityIdConstantBean.FUND_TRANSFER_INTERNAL_ONETIME_ACTIVITY_ID)) {
		DomesticFundTransferExecuteOperationResponse domesticFundTransferExecuteOperationResponse = (DomesticFundTransferExecuteOperationResponse) request
			.getParentResponse();
		DomesticFundTransferExecuteOperationRequest domesticFundTransferExecuteOperationRequest = (DomesticFundTransferExecuteOperationRequest) request
			.getParentRequest();

		if (domesticFundTransferExecuteOperationResponse != null && domesticFundTransferExecuteOperationResponse.isSuccess()) {
		    // String txnRef = domesticFundTransferExecuteOperationResponse.getTrnRef();
		    String destinationAcct = "";
		    String sourceAcct = "";
		    BigDecimal txnAmt = new BigDecimal(0);
		    String curr = "";
		   /* String mobileNumber ="";
		    mobileNumber = domesticFundTransferExecuteOperationRequest.getContext().getMobilePhone();*/
		    if (domesticFundTransferExecuteOperationRequest != null
			    && domesticFundTransferExecuteOperationRequest.getTransactionDTO() != null) {
			if (domesticFundTransferExecuteOperationRequest.getTransactionDTO().getBeneficiaryDTO() != null) {
			    destinationAcct = domesticFundTransferExecuteOperationRequest.getTransactionDTO().getBeneficiaryDTO()
				    .getDestinationAccountNumber();
			    if (destinationAcct != null && !destinationAcct.isEmpty())
				destinationAcct = masker.mask(destinationAcct, MaskingMode.NUMBERPARTIAL);
			}
			if (domesticFundTransferExecuteOperationRequest.getTransactionDTO().getSourceAcct() != null) {
				//Kadikope code for sms
				if(domesticFundTransferExecuteOperationRequest.getTransactionDTO().getSourceAcct() instanceof CreditCardAccountDTO) {
					CreditCardAccountDTO cardAccountDTO = (CreditCardAccountDTO) domesticFundTransferExecuteOperationRequest
						.getTransactionDTO().getSourceAcct();
				sourceAcct = cardAccountDTO.getPrimary().getCardNumber();
				} else {
			    sourceAcct = domesticFundTransferExecuteOperationRequest.getTransactionDTO().getSourceAcct().getAccountNumber();
				}
			   // sourceAcct = domesticFundTransferExecuteOperationRequest.getTransactionDTO().getSourceAcct().getAccountNumber();
			    if (sourceAcct != null && !sourceAcct.isEmpty())
				sourceAcct = masker.mask(sourceAcct, MaskingMode.NUMBERPARTIAL);
			}
			if (domesticFundTransferExecuteOperationRequest.getTransactionDTO().getTxnAmt() != null) {
			    txnAmt = domesticFundTransferExecuteOperationRequest.getTransactionDTO().getTxnAmt().getAmount();
			}
			if (domesticFundTransferExecuteOperationRequest.getTransactionDTO().getTxnAmt() != null) {
			    curr = domesticFundTransferExecuteOperationRequest.getTransactionDTO().getTxnAmt().getCurrency();
			}
		    }
		    dynamicValues[0] = destinationAcct;
		    dynamicValues[1] = sourceAcct;
		    dynamicValues[2] = txnAmt.toString();
		    dynamicValues[3] = curr;
		    dynamicValues[4] = domesticFundTransferExecuteOperationResponse.getContext().getActivityRefNo();
		    Calendar cal = DateTimeUtil.getCurrentBusinessCalendar(domesticFundTransferExecuteOperationResponse.getContext());
		    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		    String date = sdf.format(cal.getTime());
		    dynamicValues[5] = date;
		   /* dynamicValues[6] = mobileNumber;*/

		} else {
		    // String txnRef = domesticFundTransferExecuteOperationResponse.getTrnRef();

		    String destinationAcct = "";
		    String sourceAcct = "";
		    BigDecimal txnAmt = new BigDecimal(0);
		    String curr = "";
		    if (domesticFundTransferExecuteOperationRequest != null
			    && domesticFundTransferExecuteOperationRequest.getTransactionDTO() != null) {
			if (domesticFundTransferExecuteOperationRequest.getTransactionDTO().getBeneficiaryDTO() != null) {
			    destinationAcct = domesticFundTransferExecuteOperationRequest.getTransactionDTO().getBeneficiaryDTO()
				    .getDestinationAccountNumber();
			    if (destinationAcct != null && !destinationAcct.isEmpty())
				destinationAcct = masker.mask(destinationAcct, MaskingMode.NUMBERPARTIAL);
			}
			if (domesticFundTransferExecuteOperationRequest.getTransactionDTO().getSourceAcct() != null) {
			    sourceAcct = domesticFundTransferExecuteOperationRequest.getTransactionDTO().getSourceAcct().getAccountNumber();
			    if (sourceAcct != null && !sourceAcct.isEmpty())
				sourceAcct = masker.mask(sourceAcct, MaskingMode.NUMBERPARTIAL);
			}
			if (domesticFundTransferExecuteOperationRequest.getTransactionDTO().getTxnAmt() != null) {
			    txnAmt = domesticFundTransferExecuteOperationRequest.getTransactionDTO().getTxnAmt().getAmount();
			}
			if (domesticFundTransferExecuteOperationRequest.getTransactionDTO().getTxnAmt() != null) {
			    curr = domesticFundTransferExecuteOperationRequest.getTransactionDTO().getTxnAmt().getCurrency();
			}
		    }

		    dynamicValues[0] = destinationAcct;
		    dynamicValues[1] = sourceAcct;
		    dynamicValues[2] = txnAmt.toString();
		    dynamicValues[3] = curr;
		    dynamicValues[4] = domesticFundTransferExecuteOperationRequest.getContext().getActivityRefNo();
		    Calendar cal = DateTimeUtil.getCurrentBusinessCalendar(domesticFundTransferExecuteOperationRequest.getContext());
		    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		    String date = sdf.format(cal.getTime());
		    dynamicValues[5] = date;
		  //SHM_Sms sent for transactions CR
		    dynamicValues[6] = getCustomerCareLabel(businessId);
		}

	    } else if (activityId.equalsIgnoreCase(ActivityConstant.PMT_FT_OWN)) {
		DomesticFundTransferExecuteOperationResponse domesticFundTransferExecuteOperationResponse = (DomesticFundTransferExecuteOperationResponse) request
			.getParentResponse();
		DomesticFundTransferExecuteOperationRequest domesticFundTransferExecuteOperationRequest = (DomesticFundTransferExecuteOperationRequest) request
			.getParentRequest();

		if (domesticFundTransferExecuteOperationResponse != null && domesticFundTransferExecuteOperationResponse.isSuccess()) {
		    // String txnRef = domesticFundTransferExecuteOperationResponse.getTrnRef();
		    String destinationAcct = "";
		    String sourceAcct = "";
		    BigDecimal txnAmt = new BigDecimal(0);
		    String curr = "";
		    if (domesticFundTransferExecuteOperationRequest != null
			    && domesticFundTransferExecuteOperationRequest.getTransactionDTO() != null) {
			if (domesticFundTransferExecuteOperationRequest.getTransactionDTO().getBeneficiaryDTO() != null
				&& domesticFundTransferExecuteOperationRequest.getTransactionDTO().getBeneficiaryDTO().getDestinationAccount() != null) {
			    destinationAcct = domesticFundTransferExecuteOperationRequest.getTransactionDTO().getBeneficiaryDTO()
				    .getDestinationAccount().getAccountNumber();
			    if (destinationAcct != null && !destinationAcct.isEmpty())
				destinationAcct = masker.mask(destinationAcct, MaskingMode.NUMBERPARTIAL);
			}
			if (domesticFundTransferExecuteOperationRequest.getTransactionDTO().getSourceAcct() != null) {
				//Added for kadikope
				if(domesticFundTransferExecuteOperationRequest.getTransactionDTO().getSourceAcct() instanceof CreditCardAccountDTO) {
					CreditCardAccountDTO cardAccountDTO = (CreditCardAccountDTO) domesticFundTransferExecuteOperationRequest
						.getTransactionDTO().getSourceAcct();
				sourceAcct = cardAccountDTO.getPrimary().getCardNumber();
				} else {
			    sourceAcct = domesticFundTransferExecuteOperationRequest.getTransactionDTO().getSourceAcct().getAccountNumber();
				}

			    if (sourceAcct != null && !sourceAcct.isEmpty())
				sourceAcct = masker.mask(sourceAcct, MaskingMode.NUMBERPARTIAL);
			}
			if (domesticFundTransferExecuteOperationRequest.getTransactionDTO().getTxnAmt() != null) {
			    txnAmt = domesticFundTransferExecuteOperationRequest.getTransactionDTO().getTxnAmt().getAmount();
			}
			if (domesticFundTransferExecuteOperationRequest.getTransactionDTO().getTxnAmt() != null) {
			    curr = domesticFundTransferExecuteOperationRequest.getTransactionDTO().getTxnAmt().getCurrency();
			}
		    }
		    dynamicValues[0] = destinationAcct;
		    dynamicValues[1] = sourceAcct;
		    dynamicValues[2] = txnAmt.toString();
		    dynamicValues[3] = curr;
		    dynamicValues[4] = domesticFundTransferExecuteOperationResponse.getContext().getActivityRefNo();
		    Calendar cal = DateTimeUtil.getCurrentBusinessCalendar(domesticFundTransferExecuteOperationResponse.getContext());
		    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		    String date = sdf.format(cal.getTime());
		    dynamicValues[5] = date;

		} else {
		    // String txnRef = domesticFundTransferExecuteOperationResponse.getTrnRef();
		    String destinationAcct = "";
		    String sourceAcct = "";
		    BigDecimal txnAmt = new BigDecimal(0);
		    String curr = "";
		    if (domesticFundTransferExecuteOperationRequest != null
			    && domesticFundTransferExecuteOperationRequest.getTransactionDTO() != null) {
			if (domesticFundTransferExecuteOperationRequest.getTransactionDTO().getBeneficiaryDTO() != null
				&& domesticFundTransferExecuteOperationRequest.getTransactionDTO().getBeneficiaryDTO().getDestinationAccount() != null) {
			    destinationAcct = domesticFundTransferExecuteOperationRequest.getTransactionDTO().getBeneficiaryDTO()
				    .getDestinationAccount().getAccountNumber();
			    if (destinationAcct != null && !destinationAcct.isEmpty())
				destinationAcct = masker.mask(destinationAcct, MaskingMode.NUMBERPARTIAL);
			}
			if (domesticFundTransferExecuteOperationRequest.getTransactionDTO().getSourceAcct() != null) {
			    sourceAcct = domesticFundTransferExecuteOperationRequest.getTransactionDTO().getSourceAcct().getAccountNumber();
			    if (sourceAcct != null && !sourceAcct.isEmpty())
				sourceAcct = masker.mask(sourceAcct, MaskingMode.NUMBERPARTIAL);
			}
			if (domesticFundTransferExecuteOperationRequest.getTransactionDTO().getTxnAmt() != null) {
			    txnAmt = domesticFundTransferExecuteOperationRequest.getTransactionDTO().getTxnAmt().getAmount();
			}
			if (domesticFundTransferExecuteOperationRequest.getTransactionDTO().getTxnAmt() != null) {
			    curr = domesticFundTransferExecuteOperationRequest.getTransactionDTO().getTxnAmt().getCurrency();
			}
		    }
		    dynamicValues[0] = destinationAcct;
		    dynamicValues[1] = sourceAcct;
		    dynamicValues[2] = txnAmt.toString();
		    dynamicValues[3] = curr;
		    dynamicValues[4] = domesticFundTransferExecuteOperationRequest.getContext().getActivityRefNo();
		    Calendar cal = DateTimeUtil.getCurrentBusinessCalendar(domesticFundTransferExecuteOperationRequest.getContext());
		    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		    String date = sdf.format(cal.getTime());
		    dynamicValues[5] = date;
		    //SHM_Sms sent for transactions CR
		    dynamicValues[6] = getCustomerCareLabel(businessId);

		}

	    } else if (activityId.equalsIgnoreCase(ActivityIdConstantBean.CHANGE_PASSWORD_ACTIVITY_ID)) {

		 /* ChangePasswordOperationResponse changePwdOpResponse = (ChangePasswordOperationResponse) request.getParentResponse();
		  ChangePasswordOperationRequest changePwdOpRequest = (ChangePasswordOperationRequest) request.getParentRequest();*/
	      //SHM_Sms sent for transactions CR
		  dynamicValues[0] = getCustomerCareLabel(businessId);

		// changePwdOpResponse.get
	    }
	    //CR82 EditBenf Airtime-mWallet
	    else if (activityId.equalsIgnoreCase(ActivityIdConstantBean.ADD_PAYEE_ACTIVITY_ID) && (payGrp!=null) &&
	    		(payGrp.equalsIgnoreCase(AIR_TIME) || payGrp.equalsIgnoreCase(MOBILE_WALLET)) && (editFlow!=null) && editFlow.equalsIgnoreCase(AT_WT_EDT_FLOW) ) {
	    	AddOrgBeneficiaryOperationResponse addBenOpResponse = (AddOrgBeneficiaryOperationResponse) request.getParentResponse();
	    	AddOrgBenefeciaryOperationRequest addBenOpReq = (AddOrgBenefeciaryOperationRequest) request.getParentRequest();

	    	if (addBenOpResponse != null && addBenOpResponse.isSuccess()) {
	    		String benefName = "";
	    		if (addBenOpResponse.getBeneficiaryDTO() != null) {
	    			//billerName = addBenOpResponse.getBeneficiaryDTO().getBillerName();
	    			benefName = addBenOpReq.getBillerNickName();
	    		}

	    		dynamicValues[0] = benefName;

	    	} else {
	    		String benefName = "";
	    		if (addBenOpResponse.getBeneficiaryDTO() != null) {
	    			benefName = addBenOpReq.getBillerNickName();
	    		}
	    		// String customerCare = "(+255) 774 700 708";
	    		String customerCare = getCustomerCareLabel(businessId);
	    		dynamicValues[0] = benefName;
	    		dynamicValues[1] = customerCare;
	    		//SHM_Sms sent for transactions CR
	    		dynamicValues[2] = request.getContext().getActivityRefNo();
	    	}

	    }
	    //CR82 Adddbenf Airtime-mWallet
	    else if (activityId.equalsIgnoreCase(ActivityIdConstantBean.ADD_PAYEE_ACTIVITY_ID) && (payGrp!=null) &&
	    		(payGrp.equalsIgnoreCase(AIR_TIME) || payGrp.equalsIgnoreCase(MOBILE_WALLET))) {
			AddOrgBeneficiaryOperationResponse addBenOpResponse = (AddOrgBeneficiaryOperationResponse) request.getParentResponse();
			AddOrgBenefeciaryOperationRequest addBenOpReq = (AddOrgBenefeciaryOperationRequest) request.getParentRequest();

			if (addBenOpResponse != null && addBenOpResponse.isSuccess()) {
			    String benefName = "";
			    if (addBenOpResponse.getBeneficiaryDTO() != null) {
				//billerName = addBenOpResponse.getBeneficiaryDTO().getBillerName();
				benefName = addBenOpReq.getBillerNickName();
			    }

			    dynamicValues[0] = benefName;

			} else {
			    String benefName = "";
			    if (addBenOpResponse.getBeneficiaryDTO() != null) {
			    	benefName = addBenOpReq.getBillerNickName();
			    }
			    // String customerCare = "(+255) 774 700 708";
			    String customerCare = getCustomerCareLabel(businessId);
			    dynamicValues[0] = benefName;
			    dynamicValues[1] = customerCare;
			  //SHM_Sms sent for transactions CR
			    dynamicValues[2] = request.getContext().getActivityRefNo();
			}

		    }

	    else if (activityId.equalsIgnoreCase(ActivityIdConstantBean.ADD_PAYEE_ACTIVITY_ID)) {
		AddOrgBeneficiaryOperationResponse addBenOpResponse = (AddOrgBeneficiaryOperationResponse) request.getParentResponse();

		if (addBenOpResponse != null && addBenOpResponse.isSuccess()) {
		    String billerName = "";
		    if (addBenOpResponse.getBeneficiaryDTO() != null) {
			billerName = addBenOpResponse.getBeneficiaryDTO().getBillerName();
		    }

		    dynamicValues[0] = billerName;

		} else {
		    String billerName = "";
		    if (addBenOpResponse.getBeneficiaryDTO() != null) {
			billerName = addBenOpResponse.getBeneficiaryDTO().getBillerName();
		    }
		    // String customerCare = "(+255) 774 700 708";
		    String customerCare = getCustomerCareLabel(businessId);
		    dynamicValues[0] = billerName;
		    dynamicValues[1] = customerCare;
		  //SHM_Sms sent for transactions CR
		    dynamicValues[2] = request.getContext().getActivityRefNo();
		}

	    }

	    /*
	       * else if(activityId.equalsIgnoreCase("BILLER_REGISTRATION_ACTIVITY")) { AddOrgBeneficiaryOperationResponse addBenOpResponse =
	       * (AddOrgBeneficiaryOperationResponse) request.getParentResponse(); //AddBeneficiaryOperationRequest addBenOpRequest =
	       * (AddBeneficiaryOperationRequest) request.getParentRequest(); if(addBenOpResponse != null && addBenOpResponse.isSuccess()) { String
	       * billerName = ""; if(addBenOpResponse.getBeneficiaryDTO() != null) { billerName =
	       * addBenOpResponse.getBeneficiaryDTO().getBillerName(); }
	       *
	       * dynamicValues[0] = billerName;
	       *
	       * } else { String billerName = ""; if(addBenOpResponse.getBeneficiaryDTO() != null) { billerName =
	       * addBenOpResponse.getBeneficiaryDTO().getBillerName(); } String customerCare = "(+255) 774 700 708";//FIXME TODO
	       *
	       * dynamicValues[0] = billerName; dynamicValues[1] = customerCare; }
	       *
	       * }
	       */else if (activityId.equalsIgnoreCase(ActivityIdConstantBean.ADD_EXTERNAL_PAYEE_ACTIVITY_ID)) {
		AddBeneficiaryOperationResponse addBenOpResponse = (AddBeneficiaryOperationResponse) request.getParentResponse();
		// AddBeneficiaryOperationRequest addBenOpRequest = (AddBeneficiaryOperationRequest) request.getParentRequest();
		if (addBenOpResponse != null && addBenOpResponse.isSuccess()) {
		    String beneficiaryName = "";
		    if (addBenOpResponse.getBeneficiaryDTO() != null) {
			beneficiaryName = addBenOpResponse.getBeneficiaryDTO().getBeneficiaryName();
		    }

		    dynamicValues[0] = beneficiaryName;

		} else {
		    String beneficiaryName = "";
		    if (addBenOpResponse != null && addBenOpResponse.getBeneficiaryDTO() != null) {
			beneficiaryName = addBenOpResponse.getBeneficiaryDTO().getBeneficiaryName();
		    }
		    // String customerCare = "(+255) 774 700 708";
		    String customerCare = getCustomerCareLabel(businessId);

		    dynamicValues[0] = beneficiaryName;
		    dynamicValues[1] = customerCare;
		    //SHM_Sms sent for transactions CR
		    dynamicValues[2] = request.getContext().getActivityRefNo();
		}

	    } else if (activityId.equalsIgnoreCase(ActivityIdConstantBean.ADD_INTERNAL_PAYEE_ACTIVITY_ID)) {
		AddBeneficiaryOperationResponse addBenOpResponse = (AddBeneficiaryOperationResponse) request.getParentResponse();
		// AddBeneficiaryOperationRequest addBenOpRequest = (AddBeneficiaryOperationRequest) request.getParentRequest();
		String beneficiaryNickName = "";
		if (addBenOpResponse != null && addBenOpResponse.isSuccess()) {

		    if (addBenOpResponse.getBeneficiaryDTO() != null) {
			beneficiaryNickName = addBenOpResponse.getBeneficiaryDTO().getPayeeNickname();
		    }

		    dynamicValues[0] = beneficiaryNickName;

		} else {

		    if (addBenOpResponse != null && addBenOpResponse.getBeneficiaryDTO() != null) {
			beneficiaryNickName = addBenOpResponse.getBeneficiaryDTO().getBillerName();
		    }
		    // String customerCare = "(+255) 774 700 708";
		    String customerCare = getCustomerCareLabel(businessId);

		    dynamicValues[0] = beneficiaryNickName;
		    dynamicValues[1] = customerCare;
		    //SHM_Sms sent for transactions CR
		    dynamicValues[2] = request.getContext().getActivityRefNo();
		}
	    } else if (activityId.equalsIgnoreCase("SELF_REGIS_EXEC")) {
		SelfRegistrationExecutionOperationResponse executionOperationResponse = (SelfRegistrationExecutionOperationResponse) request
			.getParentResponse();
		// AddBeneficiaryOperationRequest addBenOpRequest = (AddBeneficiaryOperationRequest) request.getParentRequest();

		if (executionOperationResponse != null && executionOperationResponse.isSuccess()) {
		    String pin = executionOperationResponse.getPin();
		    dynamicValues[0] = pin;
		    dynamicValues[1] = "shortCode";
		    dynamicValues[2] = SMSConstants.VALIDITY;
		} else {
		    // String customerCare = "(+255) 774 700 708";
		    String customerCare = getCustomerCareLabel(businessId);

		    dynamicValues[0] = customerCare;

		}
	    }
	    // UPDATE BENEFICIARY CHANGES
	    else if (activityId.equalsIgnoreCase(ActivityIdConstantBean.UPD_EXTERNAL_PAYEE_ACTIVITY_ID)) {
		UpdateBeneficiaryOperationResponse updBenOpResponse = (UpdateBeneficiaryOperationResponse) request.getParentResponse();
		// AddBeneficiaryOperationRequest addBenOpRequest = (AddBeneficiaryOperationRequest) request.getParentRequest();
		if (updBenOpResponse != null && updBenOpResponse.isSuccess()) {
		    String beneficiaryName = "";
		    if (updBenOpResponse.getBeneficiaryDTO() != null) {
			beneficiaryName = updBenOpResponse.getBeneficiaryDTO().getBeneficiaryName();
		    }

		    dynamicValues[0] = beneficiaryName;

		} else {
		    String beneficiaryName = "";
		    if (updBenOpResponse != null && updBenOpResponse.getBeneficiaryDTO() != null) {
			beneficiaryName = updBenOpResponse.getBeneficiaryDTO().getBeneficiaryName();
		    }
		    // String customerCare = "(+255) 774 700 708";
		    String customerCare = getCustomerCareLabel(businessId);

		    dynamicValues[0] = beneficiaryName;
		    dynamicValues[1] = customerCare;
		}

	    } else if (activityId.equalsIgnoreCase(ActivityIdConstantBean.UPD_INTERNAL_PAYEE_ACTIVITY_ID)) {
		UpdateBeneficiaryOperationResponse updBenOpResponse = (UpdateBeneficiaryOperationResponse) request.getParentResponse();
		// AddBeneficiaryOperationRequest addBenOpRequest = (AddBeneficiaryOperationRequest) request.getParentRequest();

		if (updBenOpResponse != null && updBenOpResponse.isSuccess()) {
			String beneficiaryName = "";
			if (updBenOpResponse.getBeneficiaryDTO() != null) {

				if ((CommonConstants.TZBRB_BUSINESS_ID.equals(businessId))
						|| (CommonConstants.UGBRB_BUSINESS_ID.equals(businessId))
								||(CommonConstants.KEBRB_BUSINESS_ID.equals(businessId))) {
					beneficiaryName = updBenOpResponse.getBeneficiaryDTO().getBeneficiaryNickName();
				}else{
					beneficiaryName=updBenOpResponse.getBeneficiaryDTO().getBeneficiaryOldNickName();
				}
			}
			dynamicValues[0] = beneficiaryName;

		} else {
			if (updBenOpResponse != null && updBenOpResponse.getBeneficiaryDTO() != null) {
				String beneficiaryName = "";

				if ((CommonConstants.TZBRB_BUSINESS_ID.equals(businessId))
						|| (CommonConstants.UGBRB_BUSINESS_ID.equals(businessId))
						||(CommonConstants.KEBRB_BUSINESS_ID.equals(businessId))) {
					beneficiaryName = updBenOpResponse.getBeneficiaryDTO().getBillerName();
					// String customerCare = "(+255) 774 700 708";
					String customerCare = getCustomerCareLabel(businessId);
					dynamicValues[0] = beneficiaryName;
					dynamicValues[1] = customerCare;

				}else{
					beneficiaryName=updBenOpResponse.getBeneficiaryDTO().getBeneficiaryOldNickName();
					dynamicValues[0] = beneficiaryName;
				}
			}
		}
	    }else if (activityId.equalsIgnoreCase("PMT_FT_CS")){
	    	CashSendOneTimeExecuteOperationResponse cashSendOneTimeExecuteOperationResponse = (CashSendOneTimeExecuteOperationResponse) request.getParentResponse();
	    	CashSendOneTimeExecuteOperationRequest cashSendOneTimeExecuteOperationRequest=(CashSendOneTimeExecuteOperationRequest) request.getParentRequest();
	    	if (cashSendOneTimeExecuteOperationResponse != null && cashSendOneTimeExecuteOperationResponse.isSuccess()) {

	    		String accountno="";
	    		String cashsendid="";
	    		String mobileno="";
	    		String currency="";
	    		String amount="";

	    		accountno=cashSendOneTimeExecuteOperationRequest.getCashSendRequestDTO().getActNo();
	    		//Encrypting Account number
	    		  if (accountno != null && !accountno.isEmpty()){
	    			  accountno = masker.mask(accountno, MaskingMode.NUMBERPARTIAL);
	    		  }

	    		cashsendid = cashSendOneTimeExecuteOperationResponse.getVoucherId();
	    		mobileno=cashSendOneTimeExecuteOperationRequest.getCashSendRequestDTO().getRecipientMobileNo();

	    		//removeing countrycode from Mobile number
	    		if(mobileno!= null && mobileno.startsWith("+" ) && mobileno.length() ==13){
	    			mobileno=removeCountryCode(mobileno);
	    		}
	    		currency=cashSendOneTimeExecuteOperationRequest.getCashSendRequestDTO().getCurr();
	    		amount=cashSendOneTimeExecuteOperationRequest.getCashSendRequestDTO().getTxnAmt();

	    		Calendar cal = DateTimeUtil.getCurrentBusinessCalendar(cashSendOneTimeExecuteOperationRequest.getContext());
	 		    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	 		    String date = sdf.format(cal.getTime());


	 		   dynamicValues[0] = accountno;
			    dynamicValues[1] = cashsendid;
			    dynamicValues[2] = mobileno;
			    dynamicValues[3] = currency;
			    dynamicValues[4] = amount;
			    dynamicValues[5] = date;

	    	}else {
	    		String accountno="";
	    		String mobileno="";
	    		//SHM_Sms sent for transactions CR
	    		String currency="";
	    		String amount="";
	    		accountno=cashSendOneTimeExecuteOperationRequest.getCashSendRequestDTO().getActNo();
	    		//Encrypting Account number
	    		  if (accountno != null && !accountno.isEmpty()){
	    			  accountno = masker.mask(accountno, MaskingMode.NUMBERPARTIAL);
	    		  }
	    		mobileno=cashSendOneTimeExecuteOperationRequest.getCashSendRequestDTO().getRecipientMobileNo();

	    		//removeing countrycode from Mobile number
	    		if(mobileno!= null && mobileno.startsWith("+" ) && mobileno.length() ==13){
	    			mobileno=removeCountryCode(mobileno);
	    		}
	    		currency=cashSendOneTimeExecuteOperationRequest.getCashSendRequestDTO().getCurr();
	    		amount=cashSendOneTimeExecuteOperationRequest.getCashSendRequestDTO().getTxnAmt();

	    		Calendar cal = DateTimeUtil.getCurrentBusinessCalendar(cashSendOneTimeExecuteOperationRequest.getContext());
	 		    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	 		    String date = sdf.format(cal.getTime());
	    		dynamicValues[0] = accountno;
			    dynamicValues[1] = mobileno;
			    dynamicValues[3] = currency;
			    dynamicValues[4] = amount;
			    dynamicValues[5] = date;
	    	}
	    	//CR#83 Apply Product -Lead generation
	    } else if (activityId.equalsIgnoreCase("APPLY_PRODUCT")){
	    	String productName = "";
	    	String caseNumber ="";
	    	ApplyProductConfirmOperationRequest applyProductConfirmOperationRequest = (ApplyProductConfirmOperationRequest) request.getParentRequest();
	    	ApplyProductConfirmOperationResponse applyProductConfirmOperationResponse = (ApplyProductConfirmOperationResponse) request.getParentResponse();

	    	if (applyProductConfirmOperationResponse != null) {
	    		if(applyProductConfirmOperationRequest.getSubProductName().equalsIgnoreCase("")){
	    		productName = applyProductConfirmOperationRequest.getProductName();
	    		} else {
	    			productName = applyProductConfirmOperationRequest.getProductName()+" "+ applyProductConfirmOperationRequest.getSubProductName();
	    		}
	    		caseNumber = applyProductConfirmOperationResponse.getCaseNumber();

	    		dynamicValues[0] = productName;
	    		dynamicValues[1] = caseNumber;
	    	}
	    }
	}

	DynamicFieldType[] dynamicField = null;
	ArrayList<DynamicFieldType> dynamicFieldList = new ArrayList<DynamicFieldType>();

	if (dyamicFieldsArr != null && dyamicFieldsArr.length > 0 && dynamicValues != null && dynamicValues.length > 0) {
	    for (int i = 0; i < dyamicFieldsArr.length; i++) {
		DynamicFieldType value = new DynamicFieldType();
		value.setName(dyamicFieldsArr[i]);
		value.setType("String");
		value.setValue(dynamicValues[i]);
		dynamicFieldList.add(value);
	    }
	}

	/* remove empty fields from list */

	if (dynamicFieldList != null && dynamicFieldList.size() > 0) {
	    for (int i = 0; i < dynamicFieldList.size(); i++) {
		if (dynamicFieldList.get(i) != null) {
		    if (dynamicFieldList.get(i).getName() != null && dynamicFieldList.get(i).getValue() != null) {
			// Do nothing
		    } else {
			dynamicFieldList.remove(i);
		    }
		} else {
		    dynamicFieldList.remove(i);
		}
	    }
	}

	/*
	 * if(dyamicFieldsArr != null && dyamicFieldsArr.length > 0) { for (int i = 0; i < dyamicFieldsArr.length; i++) {
	 * value.setName(dyamicFieldsArr[i]); value.setType("String"); value.setValue(dynamicValues[i]);
	 *
	 * dynamicField[i] = value; } }
	 */
	if (dynamicFieldList != null && dynamicFieldList.size() > 0) {
	    dynamicField = dynamicFieldList.toArray(new DynamicFieldType[dynamicFieldList.size()]);
	    notificationEventType.setDynamicField(dynamicField);
	}

	return notificationEventType;
    }

    private String getCustomerCareLabel(String businessId) {
	String customerCare = CommonConstants.DEFAULT_CUST_CARE;
	if (CommonConstants.TZBRB_BUSINESS_ID.equals(businessId)) {
	    customerCare = CommonConstants.TZBRB_CUST_CARE;
	} else if (CommonConstants.UGBRB_BUSINESS_ID.equals(businessId)) {
	    customerCare = CommonConstants.UGBRB_CUST_CARE;
	} else if (CommonConstants.KEBRB_BUSINESS_ID.equals(businessId)) {
	    customerCare = CommonConstants.KEBRB_CUST_CARE;
	} else if (CommonConstants.GHBRB_BUSINESS_ID.equals(businessId)) {
	    customerCare = CommonConstants.GHBRB_CUST_CARE;
	} else if (CommonConstants.ZWBRB_BUSINESS_ID.equals(businessId)) {
	    customerCare = CommonConstants.ZWBRB_CUST_CARE;
	} else if (CommonConstants.ZMBRB_BUSINESS_ID.equals(businessId)) {
	    customerCare = CommonConstants.ZMBRB_CUST_CARE;
	} else if (CommonConstants.BWBRB_BUSINESS_ID.equals(businessId)) {
	    customerCare = CommonConstants.BWBRB_CUST_CARE;
	}else if (CommonConstants.MZBRB_BUSINESS_ID.equals(businessId)) {
	    customerCare = CommonConstants.MZBRB_CUST_CARE;
	}
	return customerCare;
    }

    private String removeCountryCode(String actualMobNumber){
    	String mobileno="";
    	if(actualMobNumber.length()>= 13){
    		mobileno=actualMobNumber.substring(4);
    	}
    	return mobileno;
    }


}
