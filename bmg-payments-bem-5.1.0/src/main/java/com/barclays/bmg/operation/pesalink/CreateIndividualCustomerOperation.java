package com.barclays.bmg.operation.pesalink;


import com.barclays.bmg.audit.annotation.AuditSupport;
import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.AuditConstant;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.request.pesalink.CreateIndividualCustomerOperationRequest;
import com.barclays.bmg.operation.response.pesalink.CreateIndividualCustomerOperationResponse;
import com.barclays.bmg.service.pesalink.CreateIndividualCustomerService;
import com.barclays.bmg.service.request.pesalink.CreateIndividualCustomerServiceRequest;
import com.barclays.bmg.service.response.pesalink.CreateIndividualCustomerServiceResponse;


public class CreateIndividualCustomerOperation extends BMBCommonOperation{


	private CreateIndividualCustomerService createIndividualCustomerService;



	//@AuditSupport(auditType = AuditConstant.AUDIT_TYPE_ACTIVITY, activityState = AuditConstant.SRC_COM_UB, serviceDescription = "SD_KITS_REGISTRATION", stepId = "1", activityType = "auditKitsRegistration")
	public CreateIndividualCustomerOperationResponse createIndividualCustomer(CreateIndividualCustomerOperationRequest createIndividualCustomerOperationRequest) {

		CreateIndividualCustomerOperationResponse createIndividualCustomerOperationResponse = new CreateIndividualCustomerOperationResponse();
		CreateIndividualCustomerServiceRequest createIndividualCustomerServiceRequest = new CreateIndividualCustomerServiceRequest();
		createIndividualCustomerServiceRequest.setMobileNumber(createIndividualCustomerOperationRequest.getMobileNumber());
		createIndividualCustomerServiceRequest.setAccountNumber(createIndividualCustomerOperationRequest.getAccountNumber());
		createIndividualCustomerServiceRequest.setPrimaryFlag(createIndividualCustomerOperationRequest.getPrimaryFlag());
		//Loading Request Parameter
		loadParameters(createIndividualCustomerOperationRequest.getContext(), ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID);
		createIndividualCustomerServiceRequest.setContext(createIndividualCustomerOperationRequest.getContext());
		CreateIndividualCustomerServiceResponse createIndividualCustomerServiceResponse = createIndividualCustomerService.createIndividualCustomer(createIndividualCustomerServiceRequest);

		if(createIndividualCustomerServiceResponse != null){

			if(createIndividualCustomerServiceResponse.isSuccess())
			{
			createIndividualCustomerOperationResponse.setTxnRefNo(createIndividualCustomerServiceResponse.getTxnRefNo());
			}
			createIndividualCustomerOperationResponse.setSuccess(createIndividualCustomerServiceResponse.isSuccess());
			createIndividualCustomerOperationResponse.setResCde(createIndividualCustomerServiceResponse.getResCde());
			createIndividualCustomerOperationResponse.setResMsg(createIndividualCustomerServiceResponse.getResMsg());

		}
		return createIndividualCustomerOperationResponse;


	}

	public CreateIndividualCustomerService getCreateIndividualCustomerService() {
		return createIndividualCustomerService;
	}

	public void setCreateIndividualCustomerService(
			CreateIndividualCustomerService createIndividualCustomerService) {
		this.createIndividualCustomerService = createIndividualCustomerService;
	}

}
