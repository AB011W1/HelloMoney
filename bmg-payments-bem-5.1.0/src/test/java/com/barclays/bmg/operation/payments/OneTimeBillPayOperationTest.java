package com.barclays.bmg.operation.payments;

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

import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.operation.BaseClass;
import com.barclays.bmg.operation.request.billpayment.OneTimeBillPayOperationRequest;
import com.barclays.bmg.operation.response.billpayment.OneTimeBillPayOperationResponse;
import com.barclays.bmg.service.BillerService;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.request.BillerServiceRequest;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.BillerServiceResponse;


public class OneTimeBillPayOperationTest extends BaseClass {

	private OneTimeBillPayOperation oneTimeBillPayOperation;
	private BillerService billerService;
	private SystemParameterService systemParameterService;

	  @Before
	  public void setUp() throws Exception {
		  oneTimeBillPayOperation=new OneTimeBillPayOperation();
		  billerService = createMock(BillerService.class);
		  oneTimeBillPayOperation.setBillerService(billerService);
		  systemParameterService = createNiceMock(SystemParameterService.class);
		  oneTimeBillPayOperation.setSystemParameterService(systemParameterService);
	  }

	  @Test
	  public void testGetBillerDetails() {


		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		  expect(billerService.getBillerForBillerId(isA(BillerServiceRequest.class))).andReturn(getBillerServiceResponseMock()).atLeastOnce();

		  replay(systemParameterService,billerService);

		  OneTimeBillPayOperationRequest request=new OneTimeBillPayOperationRequest();
		  request.setContext(getContextMock());
		  OneTimeBillPayOperationResponse response=new OneTimeBillPayOperationResponse();
		    try {
		    	response=oneTimeBillPayOperation.getBillerDetails(request);

		      } catch (RuntimeException e) {
		    	  
		      }

		assertNotNull(response);
		assertEquals(true,response.isSuccess());
		assertNotNull(response.getBillerDTO());
		verify(systemParameterService,billerService);
	  }

	  @Test
	  public void testGetBillerDetailsFail() {


		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		  expect(billerService.getBillerForBillerId(isA(BillerServiceRequest.class))).andReturn(getBillerServiceResponseFailMock()).atLeastOnce();

		  replay(systemParameterService,billerService);

		  OneTimeBillPayOperationRequest request=new OneTimeBillPayOperationRequest();
		  request.setContext(getContextMock());
		  OneTimeBillPayOperationResponse response=new OneTimeBillPayOperationResponse();
		    try {
		    	response=oneTimeBillPayOperation.getBillerDetails(request);

		      } catch (RuntimeException e) {
		    	  
		      }

		assertNotNull(response);
		assertEquals(false,response.isSuccess());
		verify(systemParameterService,billerService);
	  }

	  @Test
	  public void testMergeBillerForOneTimePay() {

		  OneTimeBillPayOperationRequest request=new OneTimeBillPayOperationRequest();
		  request.setContext(getContextMock());

		  TransactionDTO transactionDTO=new TransactionDTO();
		    try {
		    	transactionDTO=oneTimeBillPayOperation.mergeBillerForOneTimePay(getBillerDTOMock(), request);

		      } catch (RuntimeException e) {
		    	  
		      }

		assertNotNull(transactionDTO);
		assertNotNull(transactionDTO.getBeneficiaryDTO());
	  }

	  @Test
	  public void testMergeBillerForOneTimePayForNoBiller() {

		  OneTimeBillPayOperationRequest request=new OneTimeBillPayOperationRequest();
		  request.setContext(getContextMock());

		  TransactionDTO transactionDTO=new TransactionDTO();
		  BillerDTO billerDTO=null;
		    try {
		    	transactionDTO=oneTimeBillPayOperation.mergeBillerForOneTimePay(billerDTO, request);

		      } catch (RuntimeException e) {
		    	  
		      }

		assertNotNull(transactionDTO);
		assertNotNull(transactionDTO.getBeneficiaryDTO());
	  }

	 @After
	 public void tearDown(){
		 	oneTimeBillPayOperation = null;
		 	billerService = null;
			systemParameterService = null;
	 }

	  protected BillerServiceResponse getBillerServiceResponseMock(){
		  BillerServiceResponse billerServiceResponse =new BillerServiceResponse();
		  billerServiceResponse.setSuccess(true);
		  billerServiceResponse.setBillerDTO(getBillerDTOMock());
	    	return billerServiceResponse;
	  }

	  protected BillerServiceResponse getBillerServiceResponseFailMock(){
		  BillerServiceResponse billerServiceResponse =new BillerServiceResponse();
		  billerServiceResponse.setSuccess(false);
	      return billerServiceResponse;
	  }

	  private BillerDTO getBillerDTOMock(){
		  BillerDTO billerDTO=new BillerDTO();
		  billerDTO.setBillerId("ABCD");
		  billerDTO.setBillerAccountNumber("11111111111");
		  billerDTO.setBillerCategoryName("AAAA");
		  return billerDTO;
	  }



}
