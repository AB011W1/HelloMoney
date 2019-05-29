package com.barclays.bmg.dao.operation.accountservices;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bem.Beneficiary.Beneficiary;
import com.barclays.bem.RetrieveIndividualCustBeneficiaryList.BeneficiaryList;
import com.barclays.bem.RetrieveIndividualCustBeneficiaryList.RetrieveIndividualCustomerBeneficiaryListResponse;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.proxy.util.MaskingMode;
import com.barclays.bmg.dao.core.proxy.util.MaskingRule;
import com.barclays.bmg.dao.core.proxy.util.MaskingRuleImpl;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.service.response.RetreivePayeeListServiceResponse;

public class RetreivePayeeListRespAdptOperation extends AbstractResAdptOperationAcct {

	public RetreivePayeeListServiceResponse adaptResponse(WorkContext workContext,Object obj){

		RetreivePayeeListServiceResponse response = new RetreivePayeeListServiceResponse();
		RetrieveIndividualCustomerBeneficiaryListResponse bemResponse = (RetrieveIndividualCustomerBeneficiaryListResponse)obj;

		response.setSuccess(checkResponseHeader(bemResponse.getResponseHeader()));

		if(checkResponseHeader(bemResponse.getResponseHeader())){
			response.setPayeeList(getPayeeList(bemResponse.getBeneficiaryList(),""));
		}
		return response;
	}


	private List<BeneficiaryDTO> getPayeeList(BeneficiaryList beneficiaryList,String businessId){

		List<BeneficiaryDTO> returnList = new ArrayList<BeneficiaryDTO>();
		if(beneficiaryList.getBeneficiary()!=null){
		for(Beneficiary beneficiary : beneficiaryList.getBeneficiary()){
			BeneficiaryDTO beneficiaryDTO = new BeneficiaryDTO();
			beneficiaryDTO.setPayeeId(beneficiary.getBeneficiaryID());
			beneficiaryDTO.setPayeeNickname(beneficiary.getBeneficiaryNickName());
			beneficiaryDTO.setBillerId(beneficiary.getEntityCode());


			if (null != beneficiary.getBeneficiaryAccountInfo()) {
				beneficiaryDTO.setDestinationAccountNumber(beneficiary.getBeneficiaryAccountInfo().getAccountNumber());
            }
			beneficiaryDTO.setPayeeTypeCode(beneficiary.getBeneficiaryTypeCode());
			beneficiaryDTO.setStatus(beneficiary.getBeneficiaryLifecycleStatusCode());
			beneficiaryDTO.setBeneficiaryName(beneficiary.getBeneficiaryName());
			if(beneficiary.getBeneficiaryBranch()!=null){

				beneficiaryDTO.setDestinationBranchCode(beneficiary.getBeneficiaryBranch().getBranchCode());
			}

            if (beneficiary.getBeneficiaryTypeCode() != null
            		&& (beneficiary.getBeneficiaryTypeCode().equals(BillPaymentConstants.PAYEE_TYPE_BILL_PAYMENT))) {
            	beneficiaryDTO.setBillRefNo1(beneficiary.getConsumerPrimaryReferenceNumber());
            	beneficiaryDTO.setBillRefNo(beneficiary.getConsumerPrimaryReferenceNumber());
            	beneficiaryDTO.setBillRefNo2(beneficiary.getConsumerSecondaryReferenceNumber());
            }


            beneficiaryDTO.setStatus(beneficiary.getBeneficiaryLifecycleStatusCode());

            returnList.add(beneficiaryDTO);
		}
		}
		return returnList;
	}
}
