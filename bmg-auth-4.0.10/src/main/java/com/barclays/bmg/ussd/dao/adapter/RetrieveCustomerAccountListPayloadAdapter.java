package com.barclays.bmg.ussd.dao.adapter;

import com.barclays.bem.RetrieveIndividualCustAcctList.IndividualCustomerInfo;
import com.barclays.bmg.dao.core.context.WorkContext;

public class RetrieveCustomerAccountListPayloadAdapter {

    public IndividualCustomerInfo adaptPayload(WorkContext workContext) {

	// DAOContext daoContext = (DAOContext) workContext;
	// Object[] args = daoContext.getArguments();
	// RetrieveCustomerAccountListServiceRequest request=(RetrieveCustomerAccountListServiceRequest)args[0];
	IndividualCustomerInfo individualCustomerInfo = new IndividualCustomerInfo();
	// individuaCustomerSearchInfo.setAccountNumber();

	/*
	 * 
	 * HMCustomerType hmCustomer=new HMCustomerType(); TelephoneAddress telephone=new TelephoneAddress(); Object[] args =
	 * daoContext.getArguments();
	 * 
	 * SelfRegistrationExecutionServiceRequest request=new SelfRegistrationExecutionServiceRequest(); Context context = request.getContext();
	 * request.setCustomerId(context.getCustomerId()); hmCustomer.setTelephone(telephone.setPhoneNumber(value))
	 */
	/*
	 * requestBody.setCustomerNumber(context.getCustomerId());
	 * 
	 * 
	 * Beneficiary individualBeneficiary = new Beneficiary(); individualBeneficiary.setBeneficiaryID(deleteBeneficiaryServiceRequest
	 * .getPayeeId()); BeneficiaryDTO beneficiaryDTO = deleteBeneficiaryServiceRequest .getBeneficiaryDTO();
	 * 
	 * requestBody.setAutoAuthorize("true"); requestBody.setCustomerAuthMechanismTypeCode(beneficiaryDTO .getCustomerAuthMechanismTypeCode());
	 * requestBody.setIndividualBeneficiary(individualBeneficiary); requestBody.setCustomerNumber(deleteBeneficiaryServiceRequest
	 * .getContext().getCustomerId());
	 */

	return individualCustomerInfo;
    }
}
