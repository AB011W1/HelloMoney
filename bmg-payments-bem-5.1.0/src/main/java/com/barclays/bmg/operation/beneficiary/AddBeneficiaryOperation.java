package com.barclays.bmg.operation.beneficiary;

import java.util.List;

import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.constants.BeneficiaryConstants;
import com.barclays.bmg.constants.BeneficiaryResponseCodeConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.constants.SMSConstants;
import com.barclays.bmg.constants.SystemIdConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.BranchLookUpDTO;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.ListValueCacheDTO;
import com.barclays.bmg.operation.payments.BMBPaymentsOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.AddBeneficiaryOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.AddBeneficiaryFormSubmissionOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.AddBeneficiaryOperationResponse;
import com.barclays.bmg.service.AddBeneficiaryService;
import com.barclays.bmg.service.accountdetails.CASADetailsService;
import com.barclays.bmg.service.accountdetails.request.CASADetailsServiceRequest;
import com.barclays.bmg.service.accountdetails.response.CASADetailsServiceResponse;
import com.barclays.bmg.service.lookup.BranchLookUpService;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;
import com.barclays.bmg.service.request.AddBeneficiaryServiceRequest;
import com.barclays.bmg.service.request.lookup.BranchLookUpServiceRequest;
import com.barclays.bmg.service.response.AddBeneficiaryServiceResponse;
import com.barclays.bmg.service.response.TransactionLimitServiceResponse;
import com.barclays.bmg.service.response.lookup.BranchLookUpServiceResponse;
import com.barclays.bmg.ussd.auth.service.request.SMSDetailsServiceRequest;
import com.barclays.bmg.ussd.service.SMSDetailsService;

/**
 * @author BTCI
 *
 */
public class AddBeneficiaryOperation extends BMBPaymentsOperation {

	private AddBeneficiaryService addBeneficiaryService;
	private CASADetailsService casaDetailsService;
	private BranchLookUpService branchLookUpService;
	private final static String SYS_PARAM_BNF = "SYS_PARAM_BNF";

	/**
	 * The instance variable for smsDetailsService of type SMSDetailsService
	 */
	private SMSDetailsService smsDetailsService;

	public SMSDetailsService getSmsDetailsService() {
		return smsDetailsService;
	}

	public void setSmsDetailsService(SMSDetailsService smsDetailsService) {
		this.smsDetailsService = smsDetailsService;
	}

	/**
	 * @param branchLookUpService
	 */
	public void setBranchLookUpService(BranchLookUpService branchLookUpService) {
		this.branchLookUpService = branchLookUpService;
	}

	/**
	 * @param casaDetailsService
	 */
	public void setCasaDetailsService(CASADetailsService casaDetailsService) {
		this.casaDetailsService = casaDetailsService;
	}

	/**
	 * @param addBeneficiaryService
	 */
	public void setAddBeneficiaryService(
			AddBeneficiaryService addBeneficiaryService) {
		this.addBeneficiaryService = addBeneficiaryService;
	}

	/**
	 * @param AddBeneficiaryOperationRequest
	 * @return AddBeneficiaryOperationResponse Operation to add internal and
	 *         external beneficiary
	 */
	@AuditSupport(auditType = AuditConstant.AUDIT_TYPE_TRANSACTION, activityState = AuditConstant.SRC_COM_EXECUTOR, serviceDescription = "SD_ADD_PAYEE", stepId = "3", activityType = "addBeneficiary")
	public AddBeneficiaryOperationResponse addBeneficiary(
			AddBeneficiaryOperationRequest request) {
		Context context = request.getContext();
		AddBeneficiaryOperationResponse response = new AddBeneficiaryOperationResponse();
		loadParameters(context, ActivityConstant.COMMON_ID,
				ActivityConstant.SEC_COMMON_ID);
		response.setContext(context);
		String systemId=request.getContext().getSystemId();

		String authType=null;
		if(systemId!=null &&SystemIdConstants.UB.equals(systemId)){
			authType = "NON"; // !!!!!!!!!!!!!!! mocked
		}else{
			authType=getAuthType(request, context.getActivityId());
		}

		if (request.isScndLvlauthReq()
				&& !BeneficiaryConstants.AUTH_TYPE_NON.equals(authType)) {
			response.setScndLvlAuthReq(true);
			request.setScndLvlAuthTyp(authType);
			return response;
		}

		BeneficiaryDTO beneficiaryDTO = request.getBeneficiaryDTO();
		AddBeneficiaryServiceRequest addBeneficiaryServiceRequest = new AddBeneficiaryServiceRequest();
		addBeneficiaryServiceRequest.setPayeeId(request.getPayeeId());
		addBeneficiaryServiceRequest.setContext(context);
		addBeneficiaryServiceRequest.setBeneficiaryDTO(beneficiaryDTO);

		/*
		 * Check for Transaction Limit
		 *
		 * Skipped !!..as system pref parameter validation for max dft & oth benf is not required any more.
		 *
		 */
		//String systemId = SystemIdConstants.UB; //!!!!!!!!!! mocked
/*		if (SystemIdConstants.UB.equals(systemId)) {

			ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
			listValueResServiceRequest.setContext(context);
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
							&& SystemParameterConstant.OTHMAX_BENEFICIARIES
									.equals(valueresDTO.getKey())) {

						txnLimitServiceResponse.setMaxOTHBenfLimit(Integer
								.valueOf(valueresDTO.getLabel()));
					} else if (valueresDTO.getKey() != null
							&& SystemParameterConstant.DFTMAX_BENEFICIARIES
									.equals(valueresDTO.getKey())) {

						txnLimitServiceResponse.setMaxDFTBenfLimit(Integer
								.valueOf(valueresDTO.getLabel()));
					}

				}
			}

			// Calculate no. of already registered payees
			List<BeneficiaryDTO> beneficiaryList = retrievePayeeList(request,
					response);
			int totIACBenfs = 0;
			int totDACBenfs = 0;

			if (beneficiaryList != null && !beneficiaryList.isEmpty()) {
				for (BeneficiaryDTO benfDTO : beneficiaryList) {

					if (FundTransferConstants.PAYEE_TYPE_FUND_TRANSFER_INTERNAL
							.equals(benfDTO.getPayeeTypeCode())) {
						totIACBenfs = totIACBenfs + 1;
					} else if (FundTransferConstants.FUND_TRANSFER_EXTERNAL
							.equals(benfDTO.getPayeeTypeCode())) {
						totDACBenfs = totDACBenfs + 1;
					}
				}
			}

			// validate payee registration based on txn limit and no. of already
			// registered payees
			if (FundTransferConstants.PAYEE_TYPE_FUND_TRANSFER_INTERNAL
					.equals(beneficiaryDTO.getPayeeTypeCode())
					&& totIACBenfs >= txnLimitServiceResponse
							.getMaxOTHBenfLimit()) {
				response.setSuccess(false);
				response
						.setResCde(BeneficiaryResponseCodeConstants.BENEFICIARY_TXN_LIMIT_COUNT);
				return response;
			} else if (FundTransferConstants.FUND_TRANSFER_EXTERNAL
					.equals(beneficiaryDTO.getPayeeTypeCode())
					&& totDACBenfs >= txnLimitServiceResponse
							.getMaxDFTBenfLimit()) {
				response.setSuccess(false);
				response
						.setResCde(BeneficiaryResponseCodeConstants.BENEFICIARY_TXN_LIMIT_COUNT);
				return response;
			}
		}*/

		AddBeneficiaryServiceResponse addBeneficiaryServiceResponse = addBeneficiaryService
				.addBeneficiary(addBeneficiaryServiceRequest);

		response.setSuccess(addBeneficiaryServiceResponse.isSuccess());
		response.setBeneficiaryDTO(beneficiaryDTO);
		if (!response.isSuccess()) {
			getMessage(response);
		}
		return response;
	}

	/**
	 * @param AddBeneficiaryOperationRequest
	 * @return AddBeneficiaryFormSubmissionOperationResponse
	 *
	 *         method validates beneficiary based on bank & branch details
	 */
	public AddBeneficiaryFormSubmissionOperationResponse validateForm(
			AddBeneficiaryOperationRequest request) {
		Context context = request.getContext();
		loadParameters(context, ActivityConstant.COMMON_ID,
				ActivityConstant.SEC_COMMON_ID);
		AddBeneficiaryFormSubmissionOperationResponse response = new AddBeneficiaryFormSubmissionOperationResponse();
		response.setContext(context);

		getBankDetails(request);

		// Check for Benf Nick Name Length based on Sys Pref

		 String systemId=request.getContext().getSystemId();
		//String systemId = SystemIdConstants.UB; //!!!!!!!!!! mocked
		if (SystemIdConstants.UB.equals(systemId)) {

			ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
			listValueResServiceRequest.setContext(context);
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

			if (request.getBeneficiaryNickName() != null
					&& request.getBeneficiaryNickName().length() > txnLimitServiceResponse
							.getPayeeNickNameLengthMax()) {
				response.setSuccess(false);
				response
						.setResCde(BeneficiaryResponseCodeConstants.BENEFICIARY_NICK_NAME_LENGTH);
				return response;

			}

		}

		/*
		 * Check whether IAC Payee Acct number is valid
		 */

		if (FundTransferConstants.PAYEE_TYPE_FUND_TRANSFER_INTERNAL
				.equals(request.getPayeeType())) {

			CASAAccountDTO casaAcct = getBeneficiaryInfo(request);
			if (casaAcct != null && casaAcct.getAccountNumber() != null
					&& !casaAcct.getAccountNumber().isEmpty()) {

				request.setBeneficiaryName(casaAcct.getAccountHolders());

			} else {
				response.setSuccess(false);
				response
						.setResCde(BeneficiaryResponseCodeConstants.INVALID_BENEFICIARY);
				return response;
			}

		}

		response.setSuccess(true);
		return response;

	}

	/**
	 * @param AddBeneficiaryOperationRequest
	 * @return CASAAccountDTO
	 */
	private CASAAccountDTO getBeneficiaryInfo(
			AddBeneficiaryOperationRequest request) {
		CASADetailsServiceRequest casaDetailsServiceRequest = new CASADetailsServiceRequest();
		casaDetailsServiceRequest.setContext(request.getContext());
		casaDetailsServiceRequest.setAccountNo(request.getAccountNumber());
		casaDetailsServiceRequest.setBranchCode(getFormattedBranchCode(request,
				request.getBranchCode()));

		CASADetailsServiceResponse casaDetailsServiceResponse = casaDetailsService
				.retrieveCASADetails(casaDetailsServiceRequest);
		CASAAccountDTO destAcct = casaDetailsServiceResponse
				.getCasaAccountDTO();
		return destAcct;
	}

	/**
	 * @param AddBeneficiaryOperationRequest
	 *
	 *            method sets the bank details based on payee type
	 */
	private void getBankDetails(AddBeneficiaryOperationRequest request) {

		BranchLookUpServiceRequest branchLookUpServiceRequest = new BranchLookUpServiceRequest();
		branchLookUpServiceRequest.setContext(request.getContext());
		BranchLookUpServiceResponse branchLookUpServiceResponse = branchLookUpService
				.getAllBranches(branchLookUpServiceRequest);

		List<BranchLookUpDTO> brList = branchLookUpServiceResponse
				.getBranchList();

		if (brList != null && !brList.isEmpty()) {

			for (BranchLookUpDTO branchLookUpDTO : brList) {

				if (FundTransferConstants.PAYEE_TYPE_FUND_TRANSFER_INTERNAL
						.equals(request.getPayeeType())) {

					if (branchLookUpDTO != null
							&& branchLookUpDTO.getBranchCode() != null
							&& branchLookUpDTO.getBranchCode().equals(
									request.getBranchCode())) {

						request.setBranchName(branchLookUpDTO.getBranchName());
						break;
					}

				} else {

					if (branchLookUpDTO != null
							&& branchLookUpDTO.getBranchCode() != null
							&& branchLookUpDTO.getBranchCode().equals(
									request.getBranchCode())
							&& branchLookUpDTO.getBankCode() != null
							&& branchLookUpDTO.getBankCode().equals(
									request.getBankCode())) {

						request.setBankName(branchLookUpDTO.getBankName());
						request.setBranchName(branchLookUpDTO.getBranchName());
						break;
					}

				}

			}

		}
	}

	/**
	 * This method sendSMSSuccessAcknowledgment has the purpose
	 * to send SMS acknowledgment for successful self registration.
	 * @param addBeneficiaryOperationResponse
	 *
	 * @param SelfRegistrationExecutionOperationRequest
	 * void
	 */
	public void sendSMSSuccessAcknowledgment(AddBeneficiaryOperationRequest addBeneficiaryOperationRequest, AddBeneficiaryOperationResponse addBeneficiaryOperationResponse){

		SMSDetailsServiceRequest smsRequest = new SMSDetailsServiceRequest();
		Context context = addBeneficiaryOperationRequest.getContext();

		loadParameters(context, ActivityConstant.COMMON_ID,ActivityConstant.SEC_COMMON_ID);
		smsRequest.setContext(context);

		smsRequest.setParentResponse(addBeneficiaryOperationResponse);
		smsRequest.setParentRequest(addBeneficiaryOperationRequest);

		if(addBeneficiaryOperationRequest.getContext().getActivityId() != null &&
				addBeneficiaryOperationRequest.getContext().getActivityId().equalsIgnoreCase(ActivityIdConstantBean.ADD_INTERNAL_PAYEE_ACTIVITY_ID)) {
			smsRequest.setEvent(getSMSEvent(addBeneficiaryOperationRequest,SMSConstants.SMS_PAYEE_ADDINT_SUCCESS));
			smsRequest.setPriority(getSMSPriority(addBeneficiaryOperationRequest,SMSConstants.SMS_PAYEEADDINSUCCESS_PRIORITY));
			smsRequest.setDynamicFields(getDynamicFields(addBeneficiaryOperationRequest,SMSConstants.SMS_PAYEE_ADDINT_SUCCESS_FIELD));
		} else {
			smsRequest.setEvent(getSMSEvent(addBeneficiaryOperationRequest,SMSConstants.SMS_PAYEE_ADDEXT_SUCCESS));
			smsRequest.setPriority(getSMSPriority(addBeneficiaryOperationRequest,SMSConstants.SMS_PAYEEADDEXSUCCESS_PRIORITY));
			smsRequest.setDynamicFields(getDynamicFields(addBeneficiaryOperationRequest,SMSConstants.SMS_PAYEE_ADDEXT_FIELD_SUCCESS));
		}
		smsDetailsService.getSMSDetails(smsRequest);
	}

	/**
	 * This method sendSMSFailAcknowledgment has the purpose
	 * to send SMS acknowledgment for self registration failure.
	 * @param addBeneficiaryOperationResponse
	 *
	 * @param SelfRegistrationExecutionOperationRequest
	 * void
	 */
	public void sendSMSFailAcknowledgment(AddBeneficiaryOperationRequest request, AddBeneficiaryOperationResponse addBeneficiaryOperationResponse) {

		SMSDetailsServiceRequest smsRequest = new SMSDetailsServiceRequest();
		Context context = request.getContext();

		loadParameters(context, ActivityConstant.COMMON_ID,ActivityConstant.SEC_COMMON_ID);
		smsRequest.setContext(context);

		smsRequest.setParentResponse(addBeneficiaryOperationResponse);
		smsRequest.setParentRequest(request);

		if(request.getContext().getActivityId() != null &&
				request.getContext().getActivityId().equalsIgnoreCase(ActivityIdConstantBean.ADD_INTERNAL_PAYEE_ACTIVITY_ID)) {
			smsRequest.setEvent(getSMSEvent(request,SMSConstants.SMS_PAYEE_ADDINT_FAIL));
			smsRequest.setPriority(getSMSPriority(request,SMSConstants.SMS_PAYEEADDINFAIL_PRIORITY));
			smsRequest.setDynamicFields(getDynamicFields(request,SMSConstants.SMS_PAYEE_ADDINT_FAIL_FIELD));
		} else {
			smsRequest.setEvent(getSMSEvent(request,SMSConstants.SMS_PAYEE_ADDEXT_FAIL));
			smsRequest.setPriority(getSMSPriority(request,SMSConstants.SMS_PAYEEADDEXTFAIL_PRIORITY));
			smsRequest.setDynamicFields(getDynamicFields(request,SMSConstants.SMS_PAYEE_ADDEXT_FIELD_FAIL));
		}
		smsDetailsService.getSMSDetails(smsRequest);
	}

	/**
	 * This method getSMSEvent has the purpose
	 * to get event, for which SMS has to be sent.
	 *
	 * @param  SelfRegistrationExecutionOperationRequest
	 * @return String
	 */
	private String getSMSEvent(AddBeneficiaryOperationRequest addBeneficiaryOperationRequest,String smsKey) {

		ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
		listValueResServiceRequest.setContext(addBeneficiaryOperationRequest.getContext());
		listValueResServiceRequest.setListValueKey(smsKey);
		ListValueResByGroupServiceResponse listValueResByGroupServiceResponse = listValueResService.getListValueByKey(listValueResServiceRequest);

		String event=listValueResByGroupServiceResponse.getListValueCahceDTO().get(0).getLabel();
		return event;
	}

	private String getDynamicFields(AddBeneficiaryOperationRequest addBeneficiaryOperationRequest,String smsKey) {

		ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
		listValueResServiceRequest.setContext(addBeneficiaryOperationRequest.getContext());
		listValueResServiceRequest.setListValueKey(smsKey);
		ListValueResByGroupServiceResponse listValueResByGroupServiceResponse = listValueResService.getListValueByKey(listValueResServiceRequest);

		String event=listValueResByGroupServiceResponse.getListValueCahceDTO().get(0).getLabel();
		return event;
	}

	/**
	 * This method getSMSPriority has the purpose
	 * to get the priority of the SMS to be sent.
	 *
	 * @param  SelfRegistrationExecutionOperationRequest
	 * @return String
	 */
	private String getSMSPriority(AddBeneficiaryOperationRequest addBeneficiaryOperationRequest,String smsKey) {

		ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
		listValueResServiceRequest.setContext(addBeneficiaryOperationRequest.getContext());
		listValueResServiceRequest.setListValueKey(smsKey);
		ListValueResByGroupServiceResponse listValueResByGroupServiceResponse = listValueResService.getListValueByKey(listValueResServiceRequest);

		String priority=listValueResByGroupServiceResponse.getListValueCahceDTO().get(0).getLabel();
		return priority;

	}

}
