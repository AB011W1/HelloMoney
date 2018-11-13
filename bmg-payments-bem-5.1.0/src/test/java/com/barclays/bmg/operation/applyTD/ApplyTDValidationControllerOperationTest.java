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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.barclays.bmg.dao.operation.ApplyTDValidateOperation;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.operation.accountdetails.request.ApplyTDValidationOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.ApplyTDValidationOperationResponse;
import com.barclays.bmg.service.MessageResourceService;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.accounts.AllAccountService;
import com.barclays.bmg.service.accounts.request.AllAccountServiceRequest;
import com.barclays.bmg.service.accounts.response.AllAccountServiceResponse;
import com.barclays.bmg.service.product.response.ProductEligibilityListServiceResponse;
import com.barclays.bmg.service.request.MessageResourceServiceRequest;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;

public class ApplyTDValidationControllerOperationTest extends BaseClass{

    private SystemParameterService systemParameterService;
 	private MessageResourceService messageResourceService;
 	private ApplyTDValidateOperation applyTDValidateOperation;

 	private AllAccountService allAccountService;

	@Before
	 public void setUp() throws Exception {
		applyTDValidateOperation = new ApplyTDValidateOperation();
		allAccountService = createMock(AllAccountService.class);
 		applyTDValidateOperation.setAllAccountService(allAccountService);

		systemParameterService = createNiceMock(SystemParameterService.class);
		applyTDValidateOperation.setSystemParameterService(systemParameterService);
		messageResourceService=createNiceMock(MessageResourceService.class);
		applyTDValidateOperation.setMessageResourceService(messageResourceService);
	}

	  @Test
	  public void testGetSourceAccountDetailsOperation() {
  		  ApplyTDValidationOperationResponse applyTDValidationOperationResponse = new ApplyTDValidationOperationResponse();
  		  applyTDValidationOperationResponse.setSuccess(true);
 		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
 		  expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).anyTimes();
 		  expect(allAccountService.retrieveAllAccount(isA(AllAccountServiceRequest.class))).andReturn(getRetrieveAcctListOperationResponseeMock()).anyTimes();
          replay(allAccountService,systemParameterService,messageResourceService);
 		  ApplyTDValidationOperationRequest request = new ApplyTDValidationOperationRequest();
 		  setReqParameters(request);
		  request.setContext(getContextMock());
 		  ApplyTDValidationOperationResponse response = new ApplyTDValidationOperationResponse();
	 	  try {
 			 response = applyTDValidateOperation.getSourceAccountDetails(request);
		    } catch (RuntimeException e) {
		    	  
		  }
	      assertNotNull(response);
		  assertEquals(true,response.isSuccess());
		  assertNotNull(response.getSourceCustomerAccountDTO());
		  verify(allAccountService,systemParameterService,messageResourceService);
	  }

	  @Test
	  public void testGetSourceAccountDetailsOperationForError() {
  		  ApplyTDValidationOperationResponse applyTDValidationOperationResponse = new ApplyTDValidationOperationResponse();
  		  applyTDValidationOperationResponse.setSuccess(true);
 		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
 		  expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).anyTimes();
 		  expect(allAccountService.retrieveAllAccount(isA(AllAccountServiceRequest.class))).andReturn(getRetrieveAcctListOperationResponseeMockForError()).anyTimes();
          replay(allAccountService,systemParameterService,messageResourceService);
 		  ApplyTDValidationOperationRequest request = new ApplyTDValidationOperationRequest();
 		  setReqParameters(request);
		  request.setContext(getContextMock());
 		  ApplyTDValidationOperationResponse response = new ApplyTDValidationOperationResponse();
	 	  try {
 			 response = applyTDValidateOperation.getSourceAccountDetails(request);
		    } catch (RuntimeException e) {
		    	  
		  }
	      assertNotNull(response);
		  assertEquals(false,response.isSuccess());
		  assertEquals(null,response.getSourceCustomerAccountDTO());
		  verify(allAccountService,systemParameterService,messageResourceService);
	  }
	 private void setReqParameters(ApplyTDValidationOperationRequest request) {
		 request.setAcctNo("9000000008");
		 request.setDepositAmount("420");
		 request.setTenureMonths("10");
		 request.setTenureDays("5");
	}

	@After
	 public void tearDown(){
		 allAccountService = null;
		 applyTDValidateOperation = null;
 		 messageResourceService= null;
		 systemParameterService = null;
	 }



	  protected AllAccountServiceResponse getRetrieveAcctListOperationResponseeMock() {
		  AllAccountServiceResponse response = new AllAccountServiceResponse();
			response.setSuccess(true);
			CASAAccountDTO customerAccountDTO = new CASAAccountDTO();
			customerAccountDTO.setAccountNickName("PUNE_INDIA");
			customerAccountDTO.setAccountStatus("ENABLED");
			customerAccountDTO.setAccountNumber("9000000008");
			customerAccountDTO.setAsset(new BigDecimal(300));
			List<CustomerAccountDTO> acctList = new ArrayList<CustomerAccountDTO>();

			acctList.add(customerAccountDTO);
 			response.setAccountList(acctList);


		return response;
	}
	  protected AllAccountServiceResponse getRetrieveAcctListOperationResponseeMockForError() {
		  AllAccountServiceResponse response = new AllAccountServiceResponse();
			response.setSuccess(false);
			CASAAccountDTO customerAccountDTO = new CASAAccountDTO();
			customerAccountDTO.setAccountNickName(null);
			customerAccountDTO.setAccountStatus(null);
			customerAccountDTO.setAccountNumber(null);
			customerAccountDTO.setAsset(null);
			List<CustomerAccountDTO> acctList = new ArrayList<CustomerAccountDTO>();

			acctList.add(customerAccountDTO);
 			response.setAccountList(acctList);


		return response;
	}
	  protected ProductEligibilityListServiceResponse getProductEligibilityOperationResponseMock() {
		  ProductEligibilityListServiceResponse response = new ProductEligibilityListServiceResponse();
			response.setSuccess(true);
			List<CustomerAccountDTO> acctList = new ArrayList<CustomerAccountDTO>();
			CASAAccountDTO CustomerAccountDTO = new CASAAccountDTO();
			CustomerAccountDTO.setAccountNickName("PUNE_INDIA");
			CustomerAccountDTO.setAccountStatus("ENABLED");
			CustomerAccountDTO.setAccountNumber("9000000008");
			CustomerAccountDTO.setAsset(new BigDecimal(300));
			acctList.add(CustomerAccountDTO);

			response.setAccountList(acctList);


		return response;
	}
	  protected ProductEligibilityListServiceResponse getProductEligibilityOperationResponseMockForError() {
		  ProductEligibilityListServiceResponse response = new ProductEligibilityListServiceResponse();
			response.setSuccess(false);
			List<CustomerAccountDTO> acctList = new ArrayList<CustomerAccountDTO>();
			CASAAccountDTO CustomerAccountDTO = new CASAAccountDTO();
			CustomerAccountDTO.setAccountNickName(null);
			CustomerAccountDTO.setAccountStatus(null);
			CustomerAccountDTO.setAccountNumber("9000000008");
			CustomerAccountDTO.setAsset(null);
			acctList.add(CustomerAccountDTO);

			response.setAccountList(acctList);


		return response;
	}
	  protected AllAccountServiceResponse getRetrieveAllAcctListServiceResponseeMockForErro() {
		  AllAccountServiceResponse response = new AllAccountServiceResponse();
			response.setSuccess(true);
			List<? extends CustomerAccountDTO> acctList = new ArrayList<CustomerAccountDTO>();
			CASAAccountDTO CustomerAccountDTO = new CASAAccountDTO();
			CustomerAccountDTO.setAccountNickName(null);
			CustomerAccountDTO.setAccountStatus("ENABLED");
			CustomerAccountDTO.setAccountNumber(null);
			CustomerAccountDTO.setAsset(null);
			response.setAccountList(acctList);


		return response;
	}


}
