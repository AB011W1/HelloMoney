package com.barclays.bmg.operation.beneficiary;


import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.barclays.bmg.operation.BaseClass;
import com.barclays.bmg.operation.request.fundtransfer.external.DeleteBeneficiaryOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.DeleteBeneficiaryOperationResponse;
import com.barclays.bmg.service.DeleteBeneficiaryService;
import com.barclays.bmg.service.MessageResourceService;
import com.barclays.bmg.service.RetreiveBeneficiaryDetailsService;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.request.DeleteBeneficiaryServiceRequest;
import com.barclays.bmg.service.request.MessageResourceServiceRequest;
import com.barclays.bmg.service.request.RetreiveBeneficiaryDetailsServiceRequest;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.DeleteBeneficiaryServiceResponse;
import com.barclays.bmg.service.response.RetreiveBeneficiaryDetailsServiceResponse;
import com.barclays.bmg.service.response.SystemParameterListServiceResponse;


public class DeleteBeneficiaryOperationTest extends BaseClass {

	private DeleteBeneficiaryOperation deleteBeneficiaryOperation;
	private DeleteBeneficiaryService deleteBeneficiaryService;
	private SystemParameterService systemParameterService;
	private RetreiveBeneficiaryDetailsService retreiveBeneficiaryDetailsService;
	private MessageResourceService messageResourceService;

	  @Before
	  public void setUp() throws Exception {
		  deleteBeneficiaryOperation=new DeleteBeneficiaryOperation();
		  deleteBeneficiaryService = createMock(DeleteBeneficiaryService.class);
		  deleteBeneficiaryOperation.setDeleteBeneficiaryService(deleteBeneficiaryService);
		  systemParameterService = createNiceMock(SystemParameterService.class);
		  deleteBeneficiaryOperation.setSystemParameterService(systemParameterService);
		  retreiveBeneficiaryDetailsService=createNiceMock(RetreiveBeneficiaryDetailsService.class);
		  deleteBeneficiaryOperation.setRetreiveBeneficiaryDetailsService(retreiveBeneficiaryDetailsService);
		  messageResourceService=createNiceMock(MessageResourceService.class);
		  deleteBeneficiaryOperation.setMessageResourceService(messageResourceService);
	  }

	  @Test
	  public void testDeleteBeneficiary() {

		  DeleteBeneficiaryServiceResponse deleteBeneficiaryServiceResponse=new DeleteBeneficiaryServiceResponse();
		  deleteBeneficiaryServiceResponse.setSuccess(true);

		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		  expect(retreiveBeneficiaryDetailsService.retreiveBeneficiaryDetails(isA(RetreiveBeneficiaryDetailsServiceRequest.class))).andReturn(getRetreiveBeneficiaryDetailsServiceResponseMock()).anyTimes();
		  expect(deleteBeneficiaryService.deleteBeneficiary(isA(DeleteBeneficiaryServiceRequest.class))).andReturn(deleteBeneficiaryServiceResponse).atLeastOnce();

		  replay(deleteBeneficiaryService,systemParameterService,retreiveBeneficiaryDetailsService);

		  DeleteBeneficiaryOperationRequest request=new DeleteBeneficiaryOperationRequest();
		  request.setContext(getContextMock());
		  DeleteBeneficiaryOperationResponse response=new DeleteBeneficiaryOperationResponse();
		    try {
		    	response=deleteBeneficiaryOperation.deleteBeneficiary(request);

		      } catch (RuntimeException e) {
		    	  
		      }

		assertNotNull(response);
		assertEquals(true,response.isSuccess());
		assertNotNull(response.getBeneficiaryDTO());
		verify(systemParameterService,deleteBeneficiaryService,retreiveBeneficiaryDetailsService);
	  }

	  @Test
	  public void testDeleteBeneficiaryForWrongBenfId() {

		  DeleteBeneficiaryServiceResponse deleteBeneficiaryServiceResponse=new DeleteBeneficiaryServiceResponse();
		  deleteBeneficiaryServiceResponse.setSuccess(false);
		  SystemParameterListServiceResponse systemParameterListServiceResponse=getSystemParameterListServiceResponseMock();

		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(systemParameterListServiceResponse).anyTimes();
		  expect(retreiveBeneficiaryDetailsService.retreiveBeneficiaryDetails(isA(RetreiveBeneficiaryDetailsServiceRequest.class))).andReturn(getRetreiveBeneDetServiceResMockForWrongBenfId()).anyTimes();
		  expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).anyTimes();
		  expect(deleteBeneficiaryService.deleteBeneficiary(isA(DeleteBeneficiaryServiceRequest.class))).andReturn(deleteBeneficiaryServiceResponse).atLeastOnce();

		  replay(deleteBeneficiaryService,systemParameterService,retreiveBeneficiaryDetailsService,messageResourceService);

		  DeleteBeneficiaryOperationRequest request=new DeleteBeneficiaryOperationRequest();
		  request.setContext(getContextMock());
		  DeleteBeneficiaryOperationResponse response=new DeleteBeneficiaryOperationResponse();
		    try {
		    	response=deleteBeneficiaryOperation.deleteBeneficiary(request);

		      } catch (RuntimeException e) {
		    	  
		      }

		assertNotNull(response);
		assertEquals(false,response.isSuccess());
		assertEquals(null,response.getBeneficiaryDTO());

		 verify(systemParameterService,deleteBeneficiaryService,retreiveBeneficiaryDetailsService,messageResourceService);
	  }

	 @After
	 public void tearDown(){
			deleteBeneficiaryOperation = null;
			deleteBeneficiaryService = null;
			retreiveBeneficiaryDetailsService = null;
			systemParameterService = null;
	 }

	  protected RetreiveBeneficiaryDetailsServiceResponse getRetreiveBeneficiaryDetailsServiceResponseMock(){
	    	RetreiveBeneficiaryDetailsServiceResponse retreiveBeneficiaryDetailsServiceResponse =new RetreiveBeneficiaryDetailsServiceResponse();
	    	retreiveBeneficiaryDetailsServiceResponse.setSuccess(true);
	    	retreiveBeneficiaryDetailsServiceResponse.setBeneficiaryDTO(getBeneficiaryDTOMock());
	    	return retreiveBeneficiaryDetailsServiceResponse;
	  }

	  protected RetreiveBeneficiaryDetailsServiceResponse getRetreiveBeneDetServiceResMockForWrongBenfId(){
	    	RetreiveBeneficiaryDetailsServiceResponse retreiveBeneficiaryDetailsServiceResponse =new RetreiveBeneficiaryDetailsServiceResponse();
	    	retreiveBeneficiaryDetailsServiceResponse.setSuccess(false);
	    	retreiveBeneficiaryDetailsServiceResponse.setBeneficiaryDTO(null);
	    	return retreiveBeneficiaryDetailsServiceResponse;
	  }


}
