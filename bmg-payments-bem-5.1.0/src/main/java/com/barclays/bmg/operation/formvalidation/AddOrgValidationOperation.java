package com.barclays.bmg.operation.formvalidation;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.constants.AddOrgBeneficiaryConstants;
import com.barclays.bmg.constants.BeneficiaryResponseCodeConstants;
import com.barclays.bmg.constants.SystemIdConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.dto.ListValueCacheDTO;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.AddOrgBenefeciaryOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.AddOrgValidationOperationResponse;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;
import com.barclays.bmg.service.request.BillerServiceRequest;
import com.barclays.bmg.service.response.BillerServiceResponse;
import com.barclays.bmg.service.response.TransactionLimitServiceResponse;

public class AddOrgValidationOperation extends BMBPaymentsOperation {

	private final static String SYS_PARAM_BNF = "SYS_PARAM_BNF";

	public AddOrgValidationOperationResponse validateUserRerenceAccNum(
			AddOrgValidationOperationResponse addorgValidationResponse) {
		Context context = addorgValidationResponse.getContext();
		AddOrgValidationOperationResponse response = new AddOrgValidationOperationResponse();
//		String billerReferenceAccNum = addorgValidationResponse
//				.getBillerReferenceAccNum();
		response.setContext(context);
		/*
		 * loadParameters(context,ActivityConstant.COMMON_ID,
		 * ActivityConstant.SEC_COMMON_ID);
		 */
		return response;
	}

	public AddOrgValidationOperationResponse validateInputBillerId(
			AddOrgBenefeciaryOperationRequest request1) {

		AddOrgValidationOperationResponse addorgValidationResponse = new AddOrgValidationOperationResponse();
		addorgValidationResponse.setContext(request1.getContext());
		addorgValidationResponse.setSuccess(true);

		// Check for Benf Nick Name Length based on Sys Pref

		 String systemId=request1.getContext().getSystemId();
		//String systemId = SystemIdConstants.UB; // !!!!!!!!!! mocked
		 String billerNickName = request1.getBillerNickName();
		 addorgValidationResponse.setPayeNickName(billerNickName);
		if (SystemIdConstants.UB.equals(systemId)) {

			ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
			listValueResServiceRequest.setContext(request1.getContext());
			TransactionLimitServiceResponse txnLimitServiceResponse = new TransactionLimitServiceResponse();
			listValueResServiceRequest.setGroup(SYS_PARAM_BNF);
			listValueResServiceRequest.getContext().setSystemId(
					SystemIdConstants.UB); // Mocked !!!!!!!!!!!!!!!!!
			ListValueResByGroupServiceResponse listResp = listValueResService
					.getListValueByGroup(listValueResServiceRequest);

			List<ListValueCacheDTO> listValuesDTOList = null;
			if (listResp != null) {
				listValuesDTOList = listResp.getListValueCahceDTO();
			}

			if (listValuesDTOList != null && !listValuesDTOList.isEmpty()) {

				// set the transaction limit on response based on system
				// preferences

				for (ListValueCacheDTO valueresDTO : listValuesDTOList) {

					if (valueresDTO.getKey() != null
							&& SystemParameterConstant.PAYEE_NICK_LENGTH_MAX
									.equals(valueresDTO.getKey())) {

						txnLimitServiceResponse
								.setPayeeNickNameLengthMax(Integer
										.valueOf(valueresDTO.getLabel()));
						break;
					}
				}
			}


			if (StringUtils.isNotEmpty(billerNickName )
					&& billerNickName.length() > txnLimitServiceResponse
							.getPayeeNickNameLengthMax()) {
				addorgValidationResponse.setSuccess(false);
				addorgValidationResponse.setResCde(BeneficiaryResponseCodeConstants.BENEFICIARY_NICK_NAME_LENGTH);
				return addorgValidationResponse;

			}
		}


		BillerServiceRequest billerServiceRequest = new BillerServiceRequest();
		billerServiceRequest.setContext(request1.getContext());
		billerServiceRequest.setBillerCategoryId(request1.getBillerType());
		billerServiceRequest.setBusinessId(request1.getContext()
				.getBusinessId());
		billerServiceRequest.setBillerId(request1.getBillerId());
		BillerServiceResponse billerServiceResponse = null;
		try {

			billerServiceResponse = super.getBillerService()
					.getBillerForBillerId(billerServiceRequest);

		} catch (Exception e) {
			return addorgValidationResponse;
		}
		BillerDTO billerDTO = billerServiceResponse.getBillerDTO();
		if (billerDTO == null) {
			addorgValidationResponse.setErrTyp("");
			addorgValidationResponse.setResCde(AddOrgBeneficiaryConstants.BILLER_LIST_NOT_EXIST);

			addorgValidationResponse.setSuccess(false);
			addorgValidationResponse.setResMsg("Biller does not exist");
			//addorgValidationResponse.setPayeNickName(billerServiceRequest.g);
			addorgValidationResponse.setBillerDTO(null);
		} else {
			addorgValidationResponse.setBillerDTO(billerDTO);
		}

		return addorgValidationResponse;

	}

}
