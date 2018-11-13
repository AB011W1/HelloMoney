package com.barclays.bmg.operation.applyTD;



import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.barclays.bmg.operation.BaseClass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.barclays.bmg.dao.operation.ApplyTDAddProbmlemOperation;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.operation.accountdetails.request.ApplyTDAddProblemOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.ApplyTDValidationOperationResponse;
import com.barclays.bmg.service.ApplyTDService;
import com.barclays.bmg.service.MessageResourceService;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.request.ApplyTDServiceRequest;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.ApplyTDServiceResponse;

public class ApplyTDExecuteControllerOperationTest extends BaseClass{

    private SystemParameterService systemParameterService;
 	private MessageResourceService messageResourceService;
	private ApplyTDAddProbmlemOperation applyTDAddProbmlemOperation;
	private ApplyTDService applyTDService;

	@Before
	 public void setUp() throws Exception {
		  applyTDAddProbmlemOperation =new  ApplyTDAddProbmlemOperation();
		  applyTDService = createMock(ApplyTDService.class);
		  applyTDAddProbmlemOperation.setApplyTDAddProblemService(applyTDService);
          systemParameterService = createNiceMock(SystemParameterService.class);
		  applyTDAddProbmlemOperation.setSystemParameterService(systemParameterService);
		  messageResourceService=createNiceMock(MessageResourceService.class);
		  applyTDAddProbmlemOperation.setMessageResourceService(messageResourceService);
	}

	  @Test
	  public void testApplyTDAddProblem() {

		  ApplyTDServiceResponse applyTDServiceResponse=new ApplyTDServiceResponse();
		  applyTDServiceResponse.setSuccess(true);
		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		  expect(applyTDService.addProblem(isA(ApplyTDServiceRequest.class))).andReturn(getApplyTDServiceResponseMock()).anyTimes();
 		  replay( systemParameterService,applyTDService);
 		  ApplyTDAddProblemOperationRequest request=new ApplyTDAddProblemOperationRequest();
		  request.setContext(getContextMock());
		  ApplyTDValidationOperationResponse applyTDValidationOperationResponse = getApplyTDValidationResponseMock();

		  ApplyTDServiceResponse response = new ApplyTDServiceResponse();
		  try {
			  response = applyTDAddProbmlemOperation.applyTDAddProblem(request, applyTDValidationOperationResponse);

		  } catch (RuntimeException e) {
		    	  
		  }
	      assertNotNull(response);
		  assertEquals(true,response.isSuccess());
		  assertNotNull(response.getTransactionRefNum());
		  verify(systemParameterService,applyTDService);
	  }

	@Test
	  public void testApplyTDAddProblemForWrongCustomerAccNum() {

		  ApplyTDServiceResponse applyTDServiceResponse=new ApplyTDServiceResponse();
		  applyTDServiceResponse.setSuccess(true);
		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		  expect(applyTDService.addProblem(isA(ApplyTDServiceRequest.class))).andReturn(getApplyTDServiceResponseMockForWrongAccNo()).anyTimes();
		  replay( systemParameterService,applyTDService);
		  ApplyTDAddProblemOperationRequest request=new ApplyTDAddProblemOperationRequest();
		  request.setContext(getContextMock());
		  ApplyTDValidationOperationResponse applyTDValidationOperationResponse = getApplyTDValidationResponseMock();

		  ApplyTDServiceResponse response = new ApplyTDServiceResponse();
		  try {
			  response = applyTDAddProbmlemOperation.applyTDAddProblem(request, applyTDValidationOperationResponse);
		  } catch (RuntimeException e) {
		    	  
		  }
	      assertNotNull(response);
		  assertEquals(false,response.isSuccess());
		  assertEquals(null,response.getTransactionRefNum());
		  verify(systemParameterService,applyTDService);
	  }

	 @After
	 public void tearDown() {
		    applyTDService = null;
			applyTDAddProbmlemOperation = null;
 		 	messageResourceService= null;
			systemParameterService = null;
	 }
     protected ApplyTDServiceResponse getApplyTDServiceResponseMock(){
		    ApplyTDServiceResponse applyTDServiceResponse =new ApplyTDServiceResponse();
	    	applyTDServiceResponse.setSuccess(true);
	    	applyTDServiceResponse.setTransactionRefNum("101010");
	    	applyTDServiceResponse.setServiceStatus("success");
	    	return applyTDServiceResponse;
	 }
	 protected ApplyTDServiceResponse getApplyTDServiceResponseMockForWrongAccNo(){
		    ApplyTDServiceResponse applyTDServiceResponse =new ApplyTDServiceResponse();
	    	applyTDServiceResponse.setSuccess(false);
	    	applyTDServiceResponse.setTransactionRefNum(null);
	    	applyTDServiceResponse.setServiceStatus("failure");
	    	return applyTDServiceResponse;
	 }
	  private ApplyTDValidationOperationResponse getApplyTDValidationResponseMock() {
			ApplyTDValidationOperationResponse applyTDValidationOperationResponse = new ApplyTDValidationOperationResponse();
			CustomerAccountDTO customerAccountDTO = new CustomerAccountDTO();
			customerAccountDTO.setAccountNumber("9000000008");
			customerAccountDTO.setCustomerId("150031796");
			customerAccountDTO.setBranchCode("MUBRB");
			applyTDValidationOperationResponse
					.setSourceCustomerAccountDTO(customerAccountDTO);
			applyTDValidationOperationResponse.setAcctNo("9000000008");
			return applyTDValidationOperationResponse;
		}
}
