package com.barclays.bmg.operation.beneficiary;

import java.util.List;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrievePayeeInfoOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrievePayeeInfoOperationResponse;
import com.barclays.bmg.service.accountdetails.CASADetailsService;
import com.barclays.bmg.service.accountdetails.request.CASADetailsServiceRequest;
import com.barclays.bmg.service.accountdetails.response.CASADetailsServiceResponse;

public class RetrievePayeeInfoOperation extends BMBPaymentsOperation{


	private CASADetailsService casaDetailsService;


	@AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_SSC, activityId = "PMT_PAYEE_LIST", serviceDescription = "SD_RETRIEVE_ORG_BENE_DETAILS", stepId = "2", activityType="auditPayeeInfo")
	public RetrievePayeeInfoOperationResponse retrievePayeeInfo(RetrievePayeeInfoOperationRequest request){
		Context context = request.getContext();
		RetrievePayeeInfoOperationResponse response = new RetrievePayeeInfoOperationResponse();
		loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID );
		response.setContext(context);

		BeneficiaryDTO beneficiaryDTO = getBeneficiaryDetails(request, response, request.getPayId(),request.getPayGrp());


			List<? extends CustomerAccountDTO > allAccounts = getAllAccounts(request, response);

			List<? extends CustomerAccountDTO>  sourceAccts = allAccounts;
//			List<? extends CustomerAccountDTO>  sourceAccts = getSourceAccountsList(allAccounts, request);

			if(sourceAccts !=null && !sourceAccts.isEmpty()){
				if(beneficiaryDTO !=null){
					response.setBeneficiaryDTO(beneficiaryDTO);

					if(FundTransferConstants.PAYEE_TYPE_FUND_TRANSFER_INTERNAL.equals(beneficiaryDTO.getPayeeTypeCode())){
						CASAAccountDTO casaAcct = getBeneficiaryInfo(beneficiaryDTO, request,allAccounts);
						if(casaAcct !=null){
							response.getBeneficiaryDTO().setCurrency(casaAcct.getCurrency());
							response.setCasaAccountDTO(casaAcct);
						}else{
							response.setSuccess(false);
							response.setResCde(BillPaymentResponseCodeConstants.PAYMENT_INVALID_BENEFICIARY);
						}

					}
				}else{
					response.setSuccess(false);
					response.setResCde(BillPaymentResponseCodeConstants.PAYMENT_INVALID_BENEFICIARY);
				}

			}else{
				response.setSuccess(false);
				response.setResCde(BillPaymentResponseCodeConstants.NO_ACCOUNTS_FOR_SUMMARY);
			}
		if(!response.isSuccess()){
			getMessage(response);
		}
		return response;
	}

	/**
	 * @param beneficiaryDTO
	 * @param request
	 * @return
	 */
	private CASAAccountDTO getBeneficiaryInfo(BeneficiaryDTO beneficiaryDTO, RequestContext request ,	List<? extends CustomerAccountDTO > allAccounts){
		CASADetailsServiceRequest casaDetailsServiceRequest = new CASADetailsServiceRequest();
		casaDetailsServiceRequest.setContext(request.getContext());
		casaDetailsServiceRequest.setAccountNo(beneficiaryDTO.getDestinationAccountNumber());
		casaDetailsServiceRequest.setBranchCode(getFormattedBranchCode(request, beneficiaryDTO.getDestinationBranchCode()));

		CASADetailsServiceResponse casaDetailsServiceResponse =
						casaDetailsService.retrieveCASADetails(casaDetailsServiceRequest);
		CASAAccountDTO destAcct = casaDetailsServiceResponse.getCasaAccountDTO();
		return destAcct;
	}



	public CASADetailsService getCasaDetailsService() {
		return casaDetailsService;
	}

	public void setCasaDetailsService(CASADetailsService casaDetailsService) {
		this.casaDetailsService = casaDetailsService;
	}



}
