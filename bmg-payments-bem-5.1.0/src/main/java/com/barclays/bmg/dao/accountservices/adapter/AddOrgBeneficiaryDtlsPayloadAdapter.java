package com.barclays.bmg.dao.accountservices.adapter;

import com.barclays.bem.AddOrganizationBeneficiary.AddOrganizationBeneficiary;
import com.barclays.bem.OrganizationBeneficiary.OrganizationBeneficiary;
import com.barclays.bem.TransactionAccount.TransactionAccount;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.service.request.AddOrganizationBeneficiaryServiceRequest;

public class AddOrgBeneficiaryDtlsPayloadAdapter {

    public AddOrganizationBeneficiary adaptPayload(WorkContext workContext) {

	AddOrganizationBeneficiary requestBody = new AddOrganizationBeneficiary();

	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	AddOrganizationBeneficiaryServiceRequest addOrgBeneficiaryDetailsServiceRequest = (AddOrganizationBeneficiaryServiceRequest) args[0];
	Context context = addOrgBeneficiaryDetailsServiceRequest.getContext();
	requestBody.setCustomerNumber(context.getCustomerId());
	requestBody.setAutoAuthorize("true");
	requestBody = mapBean(addOrgBeneficiaryDetailsServiceRequest, requestBody);
	return requestBody;
    }

    public AddOrganizationBeneficiary mapBean(AddOrganizationBeneficiaryServiceRequest addOrgBeneficiaryDetailsServiceRequest,
	    AddOrganizationBeneficiary dest) {
	AddOrganizationBeneficiary updateddest = dest;
	BeneficiaryDTO beneficiaryDTO = addOrgBeneficiaryDetailsServiceRequest.getBeneficiaryDTO();
	if (null == updateddest) {
	    updateddest = new AddOrganizationBeneficiary();
	}

	OrganizationBeneficiary organizationBeneficiary = new OrganizationBeneficiary();
	organizationBeneficiary.setConsumerSecondaryReferenceNumber(addOrgBeneficiaryDetailsServiceRequest.getAreaCode());
	organizationBeneficiary.setBeneficiaryNickName(addOrgBeneficiaryDetailsServiceRequest.getBeneficiaryNickName());
	// String billerCategoryId = beneficiaryDTO.getBillerCategoryId();
	// if( billerCategoryId!= null && billerCategoryId.equalsIgnoreCase("BarclaycardBill")){
	//CR82 changes
	if(addOrgBeneficiaryDetailsServiceRequest.getContext().getContextMap().containsKey("payGrp")){
		organizationBeneficiary.setBeneficiaryTypeCode(addOrgBeneficiaryDetailsServiceRequest.getContext().getContextMap().get("payGrp").toString());
	}else
	organizationBeneficiary.setBeneficiaryTypeCode("BP");
	// }
	organizationBeneficiary.setConsumerPrimaryReferenceNumber(beneficiaryDTO.getBillRefNo());
	//WUC change - Botswana 21/06/2017
	if(beneficiaryDTO.getBusinessId() !=null && beneficiaryDTO.getBusinessId().equals("BWBRB") && beneficiaryDTO.getBillerId().equals("WUC-2")){
		String billRefNo2 = beneficiaryDTO.getBillRefNo2();
		if (billRefNo2 != null || billRefNo2 != ""){
			organizationBeneficiary.setConsumerSecondaryReferenceNumber(beneficiaryDTO.getBillRefNo2());
		}
	}

	organizationBeneficiary.setOrganizationName(beneficiaryDTO.getBillerName());
	organizationBeneficiary.setOrganizationCategoryCode(beneficiaryDTO.getBillerCategoryId());
	organizationBeneficiary.setOrganizationCode(beneficiaryDTO.getBillerId());// MU_Reve_Auth
	organizationBeneficiary.setBillerReferenceNumberText(beneficiaryDTO.getBillRefNo());

	TransactionAccount beneficiaryAccountInfo = new TransactionAccount();
	beneficiaryAccountInfo.setAccountNumber(beneficiaryDTO.getTransactionReferenceNumber());
	organizationBeneficiary.setBeneficiaryAccountInfo(beneficiaryAccountInfo);

	if (beneficiaryDTO.isMobileProvider()) {
	    organizationBeneficiary.setConsumerPrimaryReferenceNumber(beneficiaryDTO.getBillRefNo1());

	} else {
	    organizationBeneficiary.setConsumerPrimaryReferenceNumber(beneficiaryDTO.getBillRefNo());
	}

	if (null != beneficiaryDTO.getOnlineBillerFlag()) {
	    organizationBeneficiary.setBillAggregatorSupportFlag("Y".equals(beneficiaryDTO.getOnlineBillerFlag()) ? true : false);
	} else {
	    organizationBeneficiary.setBillAggregatorSupportFlag(false);
	}

	updateddest.setOrganizationBeneficiary(organizationBeneficiary);
	updateddest.setAutoAuthorize("true");
	updateddest.setCustomerAuthMechanismTypeCode(beneficiaryDTO.getCustomerAuthMechanismTypeCode());

	return updateddest;
    }
}
