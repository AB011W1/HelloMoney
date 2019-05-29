package com.barclays.bmg.ussd.dao.adapter;

import java.util.Map;

import com.barclays.bem.RegisterCustomerToProductOneWay.Account;
import com.barclays.bem.RegisterCustomerToProductOneWay.Customer;
import com.barclays.bem.RegisterCustomerToProductOneWay.Request;
import com.barclays.bem.RetrieveIndividualCustAcctBasic.IndividualCustomerInfo;
import com.barclays.bem.TelephoneAddress.TelephoneAddress;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.ussd.auth.service.request.RegisterCustomerToProductServiceRequest;

public class RegisterCustomerToProductPayloadAdapter {

	public Request adaptPayLoad(WorkContext workContext) {
		DAOContext daoContext = (DAOContext) workContext;
		Object[] args = daoContext.getArguments();

		Request request = new Request();
		RegisterCustomerToProductServiceRequest registerCustomerToProductServiceRequest = (RegisterCustomerToProductServiceRequest) args[0];
		Context context = registerCustomerToProductServiceRequest.getContext();

		Map<String, Object> contextMap = context.getContextMap();
		Account account = new Account();
		account.setBranchCode(contextMap.get(
		SystemParameterConstant.SERVICE_HEADER_BRANCH_CODE).toString());


		Customer customer = new Customer();

        TelephoneAddress[] addressArray=new TelephoneAddress[1];
        TelephoneAddress address =new TelephoneAddress();
        address.setUsageCode("MST");

        address.setPhoneNumber(context.getMobilePhone());
        addressArray[0]=address;
        customer.setCustomerContactDetails(addressArray);

		request.setCustomer(customer);

		request.setAccount(account);
		request.setTypeCode("020202020202020");




		return request;

	}

}
