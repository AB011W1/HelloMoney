package com.barclays.bmg.dao.accountservices.adapter;

import java.util.ArrayList;
import java.util.Map;

import uk.co.barclays.www.rbb.tcvm.Common.ContactDetailsType;
import uk.co.barclays.www.rbb.tcvm.Common.CustomerInfoType;
import uk.co.barclays.www.rbb.tcvm.Common.TypeCode;
import uk.co.barclays.www.rbb.tcvm.Notification.DeliveryDetailsType;
import uk.co.barclays.www.rbb.tcvm.Notification.DynamicFieldType;
import uk.co.barclays.www.rbb.tcvm.Notification.EventListType;

import com.barclays.bmg.constants.SMSConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dao.core.proxy.util.MaskingRule;
import com.barclays.bmg.dao.core.proxy.util.MaskingRuleImpl;
import com.barclays.bmg.service.request.SendMultipleNotificationsServiceRequest;

public class SendMultipleNotificationsPayloadAdapter {
	public EventListType[] adaptPayLoad(WorkContext workContext) {
		DAOContext daoContext = (DAOContext) workContext;

		Object[] args = daoContext.getArguments();

		SendMultipleNotificationsServiceRequest request= (SendMultipleNotificationsServiceRequest)args[0];
		EventListType[] eventListTypeList=new EventListType[request.getMobileNo().length];
		if(request.getMobileNo()!=null){
		/* Get values from context */
		Map<String, Object> contextMap = request.getContext().getContextMap();
		String sourceSystemId = (String) contextMap.get(SMSConstants.SMS_SOURCE_SYSTEM_ID);
		String eventRecipientId = (String) contextMap.get(SMSConstants.SMS_EVENT_RECIPIENT_ID);
		String eventIdentifier = (String) contextMap.get(SMSConstants.SMS_EVENT_IDENTIFIER);
		String smsDeliveryMode = (String) contextMap.get(SMSConstants.SMS_DELIVERY_MODE);
		/* Ends getting values from context */

		TypeCode langTypeCode = new TypeCode();
		langTypeCode.set_value(request.getContext().getLanguageId());
		langTypeCode.setCode(request.getContext().getLanguageId());

		TypeCode typeCode = new TypeCode();
		typeCode.set_value(request.getPriority());
		typeCode.setCode(request.getPriority());

		String dynamicFields = request.getDynamicFields();
		String[] dyamicFieldsArr = null;
		String[] dynamicValues = new String[10];
		if (dynamicFields != null && !dynamicFields.isEmpty()) {
		    dyamicFieldsArr = dynamicFields.split(",");
		}

		dynamicValues[0] = request.getAccountNo();
		dynamicValues[1] = request.getTransId();
		dynamicValues[2] = request.getAmount();
		dynamicValues[3] = request.getUserRefno();

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
		}

		String mobileNos[]=request.getMobileNo();

		CustomerInfoType custinfo=new CustomerInfoType(){};
		custinfo.setBusinessId(request.getContext().getBusinessId());
		int i=0;
		for(String mobileNo:mobileNos){
		EventListType notificationEventType = new EventListType();
		notificationEventType.setDynamicField(dynamicField);
		notificationEventType.setEventId(request.getEventId());// database event id
		notificationEventType.setCustomerInfo(custinfo);
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

		contactDetails.setContactValue(mobileNo);// mobile no.
		// contactDetails.setContactValue("763211382");//mobile no.

		ContactDetailsType[] contactDetailsArr = { contactDetails };
		deliveryDetails.setContactDetails(contactDetailsArr);
		TypeCode deliveryMode = new TypeCode();
		deliveryMode.setCode(smsDeliveryMode);// 1 for SMS
		deliveryDetails.setDeliveryMode(deliveryMode);

		notificationEventType.setDeliveryDetails(deliveryDetailsArr);
		notificationEventType.setDeliveryLanguage(langTypeCode);
		notificationEventType.setEventPriority(typeCode);// database priority
		notificationEventType.setEventIdentifier(eventIdentifier);

		eventListTypeList[i]=notificationEventType;
		i++;
		}
		//
		}

		return eventListTypeList;
	}
}
