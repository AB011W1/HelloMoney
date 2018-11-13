package com.barclays.bmg.operation.validateUser;


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

import com.barclays.bmg.dto.ValidateUserDTO;
import com.barclays.bmg.operation.ValidateUserOperation;
import com.barclays.bmg.operation.request.ValidateUserOperationRequest;
import com.barclays.bmg.operation.response.ValidateUserOperationResponse;
import com.barclays.bmg.service.MessageResourceService;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.impl.ValidateUserService;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.request.ValidateUserServiceRequest;
import com.barclays.bmg.service.response.ValidateUserServiceResponse;

public class ValidateUserExecuteControllerOperationTest extends BaseClass{
    private SystemParameterService systemParameterService;
 	private MessageResourceService messageResourceService;
 	private ValidateUserOperation validateUserOperation;
	private ValidateUserService validateUserService;

	@Before
	 public void setUp() throws Exception {
		validateUserOperation = new ValidateUserOperation();
		validateUserService = createMock(ValidateUserService.class);
		validateUserOperation.setValidateUserService(validateUserService);
		systemParameterService = createNiceMock(SystemParameterService.class);
		validateUserOperation.setSystemParameterService(systemParameterService);
		messageResourceService=createNiceMock(MessageResourceService.class);
		validateUserOperation.setMessageResourceService(messageResourceService);
	}

	  @Test
	  public void testValidateUserService() {
		  ValidateUserServiceResponse validateUserServiceResponse=new ValidateUserServiceResponse();
		  validateUserServiceResponse.setSuccess(true);
 		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		  expect(validateUserService.validateUserIgnoreCase(isA(ValidateUserServiceRequest.class))).andReturn(getValidateUserServiceResponseMock()).anyTimes();
 		  replay(validateUserService,systemParameterService,messageResourceService);
		  ValidateUserOperationRequest request=new ValidateUserOperationRequest();
		  request.setContext(getContextMock());
		  ValidateUserOperationResponse response=new ValidateUserOperationResponse();
		   try {
		    	response = validateUserOperation.validateUserIgnoreCase(request);
		    } catch (RuntimeException e) {
		    	  
		  }
	      assertNotNull(response);
		  assertEquals(true,response.isSuccess());
		  assertNotNull(response.getValidateUserDto());
		  verify(validateUserService,systemParameterService,messageResourceService);
	  }

	  @Test
	  public void testValidateUserServiceForWrongUser() {
		  ValidateUserServiceResponse validateUserServiceResponse=new ValidateUserServiceResponse();
		  validateUserServiceResponse.setSuccess(true);
 		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		  expect(validateUserService.validateUserIgnoreCase(isA(ValidateUserServiceRequest.class))).andReturn(getValidateUserServiceResponseMockForWrongUser()).anyTimes();
		  replay(validateUserService,systemParameterService,messageResourceService);

		  ValidateUserOperationRequest request=new ValidateUserOperationRequest();
		  request.setContext(getContextMock());
		  ValidateUserOperationResponse response=new ValidateUserOperationResponse();
		    try {
		    	response = validateUserOperation.validateUserIgnoreCase(request);
		      } catch (RuntimeException e) {
		    	  
		      }
	      assertNotNull(response);
		  assertEquals(false,response.isSuccess());
		  assertEquals(null,response.getValidateUserDto());
		  verify(validateUserService,systemParameterService,messageResourceService);
	  }

	 @After
	 public void tearDown(){
		 validateUserService = null;
		 validateUserOperation = null;
 		 messageResourceService= null;
		 systemParameterService = null;
	 }

	  protected ValidateUserServiceResponse getValidateUserServiceResponseMock() {
		ValidateUserServiceResponse response = new ValidateUserServiceResponse();
		response.setSuccess(true);
		ValidateUserDTO validateUserDTO = new ValidateUserDTO();
		validateUserDTO.setUserId("9000000008");
		response.setValidateUserDTO(validateUserDTO);
		return response;
	}
	  protected ValidateUserServiceResponse getValidateUserServiceResponseMockForWrongUser() {
		ValidateUserServiceResponse response = new ValidateUserServiceResponse();
		response.setSuccess(false);
		//ValidateUserDTO validateUserDTO = new ValidateUserDTO();
		response.setValidateUserDTO(null);
		return response;
	}

}
