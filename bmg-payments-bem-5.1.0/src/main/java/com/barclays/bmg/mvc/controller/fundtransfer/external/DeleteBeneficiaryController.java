package com.barclays.bmg.mvc.controller.fundtransfer.external;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.ActivityIdConstantBean;
import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.BMGContextHolder;
import com.barclays.bmg.context.BMGGlobalContext;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.context.ResponseContext;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.json.model.builder.BMBMultipleResponseJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.command.billpayment.DeleteBeneficiaryCommand;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController;
import com.barclays.bmg.operation.beneficiary.DeleteBeneficiaryOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.DeleteBeneficiaryOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.DeleteBeneficiaryOperationResponse;
import com.barclays.bmg.service.RetreiveBeneficiaryDetailsService;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.request.RetreiveBeneficiaryDetailsServiceRequest;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.RetreiveBeneficiaryDetailsServiceResponse;
import com.barclays.bmg.service.response.SystemParameterListServiceResponse;

/**
 * @author BTCI
 *
 */
public class DeleteBeneficiaryController extends BMBAbstractCommandController {
	private BMBMultipleResponseJSONBuilder bmbJSONBuilder;
	private String activityId;
	private DeleteBeneficiaryOperation deleteBeneficiaryOperation;
	private RetreiveBeneficiaryDetailsService retreiveBeneficiaryDetailsService;
	private SystemParameterService systemParameterService;


	/**
	 * @param deleteBeneficiaryOperation
	 */
	public void setDeleteBeneficiaryOperation(
			DeleteBeneficiaryOperation deleteBeneficiaryOperation) {
		this.deleteBeneficiaryOperation = deleteBeneficiaryOperation;
	}

	/**
	 * @param bmbJSONBuilder
	 */
	public void setBmbJSONBuilder(BMBMultipleResponseJSONBuilder bmbJSONBuilder) {
		this.bmbJSONBuilder = bmbJSONBuilder;
	}

	/**
	 * @param activityId
	 */
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seecom.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController#
	 * getActivityId(java.lang.Object)
	 */
	@Override
	protected String getActivityId(Object command) {
		return null; // PMT_PAYEE_DELETE_INTERNAL or PMT_PAYEE_DELETE_EXTERNAL
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.barclays.bmg.mvc.controller.auth.BMBAbstractCommandController#handle1
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object,
	 * org.springframework.validation.BindException)
	 */
	@Override
	protected BMBBaseResponseModel handle1(HttpServletRequest httpRequest,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		setLastStep(httpRequest);
		DeleteBeneficiaryCommand deleteBeneficiaryCommand = (DeleteBeneficiaryCommand) command;
		DeleteBeneficiaryOperationRequest deleteBeneficiaryOperationRequest = new DeleteBeneficiaryOperationRequest();
		DeleteBeneficiaryOperationResponse responseForActivity = new DeleteBeneficiaryOperationResponse();
		addContext(deleteBeneficiaryOperationRequest, httpRequest);
		deleteBeneficiaryOperationRequest.setPayeeId(deleteBeneficiaryCommand
				.getPayeeId());
		Context context =deleteBeneficiaryOperationRequest.getContext();

		loadParameters(context, ActivityConstant.COMMON_ID,
				ActivityConstant.SEC_COMMON_ID);
		responseForActivity.setContext(context);
		//Added For Audit Fix
		BeneficiaryDTO beneficiaryDTO = getBeneficiaryActivity(deleteBeneficiaryOperationRequest,
				responseForActivity, deleteBeneficiaryOperationRequest.getPayeeId(), null);

		if (beneficiaryDTO != null
				&& FundTransferConstants.PAYEE_TYPE_FUND_TRANSFER_INTERNAL
						.equals(beneficiaryDTO.getPayeeTypeCode())) {

			context
					.setActivityId(ActivityIdConstantBean.DELETE_INTERNAL_PAYEE_ACTIVITY_ID);
		} else if(beneficiaryDTO != null
				&& FundTransferConstants.FUND_TRANSFER_EXTERNAL
						.equals(beneficiaryDTO.getPayeeTypeCode())) {
			context
					.setActivityId(ActivityIdConstantBean.DELETE_EXTERNAL_PAYEE_ACTIVITY_ID);
		}else if(beneficiaryDTO != null
				&& BillPaymentConstants.PAYEE_TYPE_BILL_PAYMENT
						.equals(beneficiaryDTO.getPayeeTypeCode())){
			context
			.setActivityId(ActivityIdConstantBean.DELETE_PAYEE_ACTIVITY_ID);
		}


		DeleteBeneficiaryOperationResponse deleteBeneficiaryOperationResponse = deleteBeneficiaryOperation
				.deleteBeneficiary(deleteBeneficiaryOperationRequest);
		return (BMBBaseResponseModel) bmbJSONBuilder
				.createMultipleJSONResponse(deleteBeneficiaryOperationResponse);
	}

	/**
	 * @param request
	 * @param httpRequest
	 *            add required parameters to context from session Map.
	 */
	private void addContext(RequestContext request,
			HttpServletRequest httpRequest) {
		Context context = createContext(httpRequest);

		context.setActivityId(activityId);
		request.setContext(context);
	}

	private BeneficiaryDTO getBeneficiaryActivity(RequestContext request, ResponseContext response, String payId, String payGrp){
		RetreiveBeneficiaryDetailsServiceRequest retreiveBeneficiaryDetailsServiceRequest =
			new RetreiveBeneficiaryDetailsServiceRequest();

    	retreiveBeneficiaryDetailsServiceRequest.setContext(request.getContext());
    	retreiveBeneficiaryDetailsServiceRequest.setPayeeId(payId);
    	retreiveBeneficiaryDetailsServiceRequest.setPayeeType(payGrp);

    	RetreiveBeneficiaryDetailsServiceResponse retreiveBeneficiaryDetailsServiceResponse =
			retreiveBeneficiaryDetailsService.retreiveBeneficiaryDetails(retreiveBeneficiaryDetailsServiceRequest);
    	BeneficiaryDTO beneficiaryDTO = null;
    	if(retreiveBeneficiaryDetailsServiceResponse!=null && retreiveBeneficiaryDetailsServiceResponse.isSuccess()
				&& retreiveBeneficiaryDetailsServiceResponse.getBeneficiaryDTO()!=null){
    		beneficiaryDTO = retreiveBeneficiaryDetailsServiceResponse.getBeneficiaryDTO();
    		checkForMobileBiller(request, beneficiaryDTO);
    	}else{
    		response.setSuccess(false);
    		response.setResCde(BillPaymentResponseCodeConstants.PAYMENT_INVALID_BENEFICIARY);
    	}

    	return beneficiaryDTO;

	}

	/**
	  * Check if beneficiary is mobile provider.
	 * @param request
	 * @param beneficiaryDTO
	 */
	private void checkForMobileBiller(RequestContext request,BeneficiaryDTO beneficiaryDTO){
		 Map<String,Object> sysMap = request.getContext().getContextMap();
		 String mobileBillers= (String)sysMap.get("PMT_MOBILEBILLERS");
		 if (mobileBillers != null) {
				String[] billerIds = mobileBillers.split(",");
				if (beneficiaryDTO.getBillerId() != null) {
					String billerId = beneficiaryDTO.getBillerId();
					for (int i = 0; i < billerIds.length; i++) {
						if (billerId.trim().equals(billerIds[i])) {
							beneficiaryDTO.setMobileProvider(true);
						}
					}
				}


			}
	 }

	protected void loadParameters(Context context, String... activities) {

		SystemParameterDTO systemParameterDTO = new SystemParameterDTO();

		systemParameterDTO.setBusinessId(context.getBusinessId());
		systemParameterDTO.setSystemId(context.getSystemId());

		SystemParameterServiceRequest systemParameterServiceRequest = new SystemParameterServiceRequest();
		systemParameterServiceRequest.setSystemParameterDTO(systemParameterDTO);

		SystemParameterListServiceResponse systemParameterListServiceResponse;
		List<SystemParameterDTO> systemParameterDTOList;
		Map<String, Object> systemParameterMap = new HashMap<String, Object>();

		for (int i = 0; i < activities.length; i++) {

			systemParameterServiceRequest.getSystemParameterDTO()
					.setActivityId(activities[i]);
			systemParameterListServiceResponse = systemParameterService
					.getSysParamByActivityId(systemParameterServiceRequest);
			systemParameterDTOList = systemParameterListServiceResponse
					.getSystemParameterDTOList();

			for (SystemParameterDTO eachSPDto : systemParameterDTOList) {
				systemParameterMap.put(eachSPDto.getParameterId(), eachSPDto
						.getParameterValue());
			}
		}


		//Changes for Tanzania Rel-2 only - SQA Overlapping on OTP against Rel-1

		if(context.getBusinessId().equals("TZBRB") && ("2.0").equals(context.getServiceVersion())) {
			systemParameterMap.put(SystemParameterConstant.SECOND_AUTH_TYPE_SYSPARAM_KEY,"SQA");
		}
		context.setContextMap(systemParameterMap);

		BMGGlobalContext bmggloContext = new BMGGlobalContext();
		bmggloContext.setActivityId(context.getActivityId());
		bmggloContext.setBusinessId(context.getBusinessId());
		bmggloContext.setSystemId(context.getSystemId());
		bmggloContext.setContextMap(systemParameterMap);

		BMGContextHolder.setLogContext(bmggloContext);

	}
	public RetreiveBeneficiaryDetailsService getRetreiveBeneficiaryDetailsService() {
		return retreiveBeneficiaryDetailsService;
	}

	public void setRetreiveBeneficiaryDetailsService(
			RetreiveBeneficiaryDetailsService retreiveBeneficiaryDetailsService) {
		this.retreiveBeneficiaryDetailsService = retreiveBeneficiaryDetailsService;
	}

	@Override
	public SystemParameterService getSystemParameterService() {
		return systemParameterService;
	}

	@Override
	public void setSystemParameterService(
			SystemParameterService systemParameterService) {
		this.systemParameterService = systemParameterService;
	}
}
