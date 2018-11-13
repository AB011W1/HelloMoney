package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.Beneficiary.Beneficiary;
import com.barclays.bem.RetrieveIndividualBeneficiaryDetails.IndividualBeneficiary;
import com.barclays.bem.TransactionAccount.TransactionAccount;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.service.request.RetreiveBeneficiaryDetailsServiceRequest;

public class RetreiveIndvBeneficiaryDtlsPayloadAdapter {

	public IndividualBeneficiary adaptPayload(WorkContext workContext){

		IndividualBeneficiary requestBody = new IndividualBeneficiary();

		DAOContext daoContext = (DAOContext)workContext;

		Object[] args = daoContext.getArguments();

		RetreiveBeneficiaryDetailsServiceRequest retreiveBeneficiaryDetailsServiceRequest=
									(RetreiveBeneficiaryDetailsServiceRequest)args[0];
		Context context = retreiveBeneficiaryDetailsServiceRequest.getContext();
		requestBody.setCustomerNumber(context.getCustomerId());

		  Beneficiary individualBeneficiary = new Beneficiary ();
		  individualBeneficiary.setBeneficiaryID(retreiveBeneficiaryDetailsServiceRequest.getPayeeId());

		CustomerAccountDTO destAcct = retreiveBeneficiaryDetailsServiceRequest.getDestAcct();
		if(destAcct!=null){
		TransactionAccount  beneficiaryAccountInfo = new TransactionAccount();
	    beneficiaryAccountInfo.setAccountNumber(destAcct.getAccountNumber());
	    beneficiaryAccountInfo.setAccountCurrencyCode(destAcct.getCurrency());
	    individualBeneficiary.setBeneficiaryAccountInfo(beneficiaryAccountInfo);
		}
	    // beneficiary.setBeneficiaryNickName(destAcct.get);
	    individualBeneficiary.setBeneficiaryTypeCode(retreiveBeneficiaryDetailsServiceRequest.getPayeeType());

		requestBody.setBeneficiary(individualBeneficiary);
		requestBody.setCustomerNumber(retreiveBeneficiaryDetailsServiceRequest.getContext().getCustomerId());
		return requestBody;
	}
}
