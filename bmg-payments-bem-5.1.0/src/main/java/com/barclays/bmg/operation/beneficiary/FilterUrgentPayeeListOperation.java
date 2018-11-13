package com.barclays.bmg.operation.beneficiary;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CategorizedPayeeListDTO;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.response.fundtransfer.external.FilterUrgentPayeeListOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.FilterUrgentPayeeListOperationResponse;

public class FilterUrgentPayeeListOperation extends BMBCommonOperation{
	private static final  String INTERNATIONAL_TXN_TYPE = "INTL";

	public FilterUrgentPayeeListOperationResponse filterUrgentPayees(FilterUrgentPayeeListOperationRequest request){

		FilterUrgentPayeeListOperationResponse response = new FilterUrgentPayeeListOperationResponse();
		Context context = request.getContext();
		response.setContext(context);

		List<CategorizedPayeeListDTO> actualPayeeList = request.getCategorizedPayeeList();
		List<CategorizedPayeeListDTO> newPayeeList = new ArrayList<CategorizedPayeeListDTO>();
		List<CategorizedPayeeListDTO> urgentPayeeList = new ArrayList<CategorizedPayeeListDTO>();
		if(actualPayeeList!=null){
			for(CategorizedPayeeListDTO categorizedPayeeListDTO : actualPayeeList){
				if(INTERNATIONAL_TXN_TYPE.equals(categorizedPayeeListDTO.getTransactionFacadeRoutineType())){
					CategorizedPayeeListDTO urgentListDTO = new CategorizedPayeeListDTO();
					urgentListDTO.setPayeeCategory(categorizedPayeeListDTO.getPayeeCategory());
					urgentListDTO.setTransactionFacadeRoutineType(categorizedPayeeListDTO.getTransactionFacadeRoutineType());
					List<BeneficiaryDTO> urgentBeneList = new ArrayList<BeneficiaryDTO>();
					urgentListDTO.setPayeeList(urgentBeneList);
					List<BeneficiaryDTO> beneficiaryList = categorizedPayeeListDTO.getPayeeList();
					for(BeneficiaryDTO beneficiaryDTO : beneficiaryList){
						if(!StringUtils.isEmpty(beneficiaryDTO.getDestinationBranchCode())){
							urgentBeneList.add(beneficiaryDTO);
						}
					}
					for(BeneficiaryDTO  urgentPayee : urgentBeneList){
						if(urgentPayee!=null){
							beneficiaryList.remove(urgentPayee);
						}
					}
					if(!urgentBeneList.isEmpty()){
						urgentPayeeList.add(urgentListDTO);
					}
					newPayeeList.add(categorizedPayeeListDTO);
				}else{
					newPayeeList.add(categorizedPayeeListDTO);
				}
			}
		}
		if(request.isUrgent() && urgentPayeeList.isEmpty()){
			response.setSuccess(false);
			response
			.setResCde(BillPaymentResponseCodeConstants.NO_PAYEES_REGISTERED);
		}
		response.setCategorizedPayeeList(newPayeeList);
		response.setUrgentPayeeList(urgentPayeeList);

		if(!response.isSuccess()){
			getMessage(response);
		}
		return response;
	}
}
