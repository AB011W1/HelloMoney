package com.barclays.bmg.dao.operation.accountservices;

import org.apache.commons.lang.StringUtils;

import com.barclays.bem.PostalAddress.UnstructuredAddress;
import com.barclays.bem.RetrieveOrganizationBeneficiaryDetails.OrganizationBeneficiaryInfo;
import com.barclays.bem.RetrieveOrganizationBeneficiaryDetails.RetrieveOrganizationBeneficiaryDetailsResponse;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.service.response.RetreiveBeneficiaryDetailsServiceResponse;

public class RetreiveOrgBeneficiaryDetailsRespAdptOperation extends AbstractResAdptOperation{

	public RetreiveBeneficiaryDetailsServiceResponse adaptResponse(WorkContext workContext, Object obj){

		RetreiveBeneficiaryDetailsServiceResponse response =
								new RetreiveBeneficiaryDetailsServiceResponse();

		RetrieveOrganizationBeneficiaryDetailsResponse bemResponse = (RetrieveOrganizationBeneficiaryDetailsResponse) obj;
		response.setSuccess(checkResponseHeader(bemResponse.getResponseHeader()));
		if(checkResponseHeader(bemResponse.getResponseHeader())){
			response.setBeneficiaryDTO(getBeneficiaryInfo(bemResponse));
			response.setSuccess(true);
		}else{
			response.setSuccess(false);
		}
		return response;
	}


	private BeneficiaryDTO getBeneficiaryInfo(RetrieveOrganizationBeneficiaryDetailsResponse bemResponse){
		BeneficiaryDTO beneficiaryDTO = new BeneficiaryDTO();
		OrganizationBeneficiaryInfo orgBenInfo = bemResponse.getOrganizationBeneficiaryInfo();

		if(orgBenInfo.getOrganizationBeneficiary() != null ){
			beneficiaryDTO.setPayeeId(orgBenInfo.getOrganizationBeneficiary().getBeneficiaryID());
		}

		if(orgBenInfo.getInitiatingCustomerName()!=null){
			String fullName = orgBenInfo.getInitiatingCustomerName().getFullName();
			if(!StringUtils.isEmpty(orgBenInfo.getInitiatingCustomerName().getSalutationTypeCode())){
				fullName = orgBenInfo.getInitiatingCustomerName().getSalutationTypeCode() + fullName;
			}
			beneficiaryDTO.setBeneficiaryName(fullName);
		}

		beneficiaryDTO.setPayeeNickname(orgBenInfo.getOrganizationBeneficiary().getBeneficiaryNickName());

		beneficiaryDTO.setPayeeTypeCode(orgBenInfo.getOrganizationBeneficiary().getBeneficiaryTypeCode());
		beneficiaryDTO.setBillRefNo(orgBenInfo.getOrganizationBeneficiary().getConsumerPrimaryReferenceNumber());
		beneficiaryDTO.setBillRefNo1(orgBenInfo.getOrganizationBeneficiary().getConsumerPrimaryReferenceNumber());
		beneficiaryDTO.setBillRefNo2(orgBenInfo.getOrganizationBeneficiary().getConsumerSecondaryReferenceNumber());
		beneficiaryDTO.setStatus(orgBenInfo.getOrganizationBeneficiary().getBeneficiaryLifecycleStatusCode());
       // replacement
		beneficiaryDTO.setPaymentChannelId(orgBenInfo.getOrganizationBeneficiary().getBeneficiaryPaymentChannelTypeCode());
		beneficiaryDTO.setBillerId(orgBenInfo.getOrganizationBeneficiary().getOrganizationCode());
		beneficiaryDTO.setBillerName(orgBenInfo.getOrganizationBeneficiary().getOrganizationName());
		beneficiaryDTO.setBillerCategoryId(orgBenInfo.getOrganizationBeneficiary().getOrganizationCategoryCode());
		beneficiaryDTO.setBillerCategoryName(orgBenInfo.getOrganizationBeneficiary().getOrganizationCategoryName());

		if(orgBenInfo.getCustomerAddress()!=null){
			UnstructuredAddress address = orgBenInfo.getCustomerAddress().getUnStructuredAddress();
			if(address!=null){
				beneficiaryDTO.setDestinationAddressLine1(address.getAddressLine1());
				beneficiaryDTO.setDestinationAddressLine2(address.getAddressLine2());
				beneficiaryDTO.setDestinationAddressLine3(address.getAddressLine3());
			}
		}
		//beneficiaryDTO.setDestinationAddressLine1(orgBenInfo.getOrganizationBeneficiary().getBeneficiaryPostalAddress());
		//beneficiaryDTO.setDestinationAccountNumber(orgBenInfo.getOrganizationBeneficiary().getBeneficiaryAccountInfo().getAccountNumber());
		if(orgBenInfo.getOrganizationBeneficiary().getBillerPresentmentFlag() == null){
			beneficiaryDTO.setPresentmentFlag(false);
		}else{
		beneficiaryDTO.setPresentmentFlag(orgBenInfo.getOrganizationBeneficiary().getBillerPresentmentFlag());
		}
		return beneficiaryDTO;

	}
}
