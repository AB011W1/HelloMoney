package  com.barclays.bmg.operation.internalFundTransferOTH;

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

import com.barclays.bmg.operation.payments.DomesticFundTransferExecuteOperation;
import com.barclays.bmg.operation.request.fundtransfer.DomesticFundTransferExecuteOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.DomesticFundTransferExecuteOperationResponse;
import com.barclays.bmg.service.MessageResourceService;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.TransactionAbilityService;
import com.barclays.bmg.service.TransactionLimitService;
import com.barclays.bmg.service.fundtransfer.DomesticFundTransferService;
import com.barclays.bmg.service.request.DomesticFundTransferServiceRequest;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.request.TransactionAbilityRequest;
import com.barclays.bmg.service.request.TransactionLimitServiceRequest;
import com.barclays.bmg.service.request.UpgradeTransactionLimitServiceRequest;
import com.barclays.bmg.service.response.DomesticFundTransferServiceResponse;

public class InternalNonRegisteredOneTimeFundTransfer_ExecutionOperationsTest extends FundTransferBaseUtilityFunctions{
    private SystemParameterService systemParameterService;
 	private MessageResourceService messageResourceService;
 	private DomesticFundTransferExecuteOperation domesticFundTransferExecuteOperation;
	private DomesticFundTransferService domesticFundTransferService;
	private TransactionLimitService transactionLimitService;
	private TransactionAbilityService transactionAbilityService;

	@Before
	 public void setUp() throws Exception {
		domesticFundTransferExecuteOperation = new DomesticFundTransferExecuteOperation();
		domesticFundTransferService = createMock(DomesticFundTransferService.class);
		domesticFundTransferExecuteOperation.setDomesticFundTransferService(domesticFundTransferService);

		transactionLimitService = createMock(TransactionLimitService.class);
		domesticFundTransferExecuteOperation.setTransactionLimitService(transactionLimitService);

		transactionAbilityService = createMock(TransactionAbilityService.class);
		domesticFundTransferExecuteOperation.setTransactionAbilityService(transactionAbilityService);

		systemParameterService = createNiceMock(SystemParameterService.class);
		domesticFundTransferExecuteOperation.setSystemParameterService(systemParameterService);
		messageResourceService=createNiceMock(MessageResourceService.class);
		domesticFundTransferExecuteOperation.setMessageResourceService(messageResourceService);
	}

	  @Test
	  public void testFundTranasferOTHUnregistered() {
		  DomesticFundTransferServiceResponse domesticFundTransService = new DomesticFundTransferServiceResponse();
		  domesticFundTransService.setSuccess(true);
 		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
 		  expect(domesticFundTransferService.makeDomesticFundTransfer(isA(DomesticFundTransferServiceRequest.class))).andReturn(getDomesticFundTransferServiceResponseMock()).anyTimes();
 		  expect(transactionLimitService.checkTransactionLimit(isA(TransactionLimitServiceRequest.class))).andReturn(getTransactionLimitServiceResponseMock()).anyTimes();
 		  expect(transactionAbilityService.checkTransactionAbility(isA(TransactionAbilityRequest.class))).andReturn(getTransactionAbilityServiceResponseMock()).anyTimes();
 		  expect(transactionLimitService.upgradeTransactionLimit(isA(UpgradeTransactionLimitServiceRequest.class))).andReturn(getUpgradeTransactionLimitServiceResponseMock()).anyTimes();
 		  replay(domesticFundTransferService,systemParameterService,messageResourceService,transactionLimitService,transactionAbilityService);
 		  DomesticFundTransferExecuteOperationRequest request = new 	DomesticFundTransferExecuteOperationRequest();
		  request.setContext(getContextMock());
		  settransactionDTO(request);
		  DomesticFundTransferExecuteOperationResponse response=new DomesticFundTransferExecuteOperationResponse();
		  try {
			  response = domesticFundTransferExecuteOperation.makeDomesticFundTransfer(request);
		    } catch (RuntimeException e) {
		    	  
		  }
	      assertNotNull(response);
		  assertEquals(true,response.isSuccess());
		  assertNotNull(response);
		  verify(domesticFundTransferService,systemParameterService,messageResourceService,transactionLimitService,transactionAbilityService);
	  }
	@Test
	  public void testFundTranasferOTHUnregisteredForError() {
		  //DomesticFundTransferServiceResponse domesticFundTransService = new DomesticFundTransferServiceResponse();
		//  domesticFundTransService.setSuccess(true);
 		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
 		  expect(domesticFundTransferService.makeDomesticFundTransfer(isA(DomesticFundTransferServiceRequest.class))).andReturn(getDomesticFundTransferServiceResponseMockForWrongUser()).anyTimes();
 		  expect(transactionLimitService.checkTransactionLimit(isA(TransactionLimitServiceRequest.class))).andReturn(getTransactionLimitServiceResponseMock()).anyTimes();
 		  expect(transactionAbilityService.checkTransactionAbility(isA(TransactionAbilityRequest.class))).andReturn(getTransactionAbilityServiceResponseMockFor()).anyTimes();
 		  expect(transactionLimitService.upgradeTransactionLimit(isA(UpgradeTransactionLimitServiceRequest.class))).andReturn(getUpgradeTransactionLimitServiceResponseMock()).anyTimes();
 		  replay(domesticFundTransferService,systemParameterService,messageResourceService,transactionLimitService,transactionAbilityService);
 		  DomesticFundTransferExecuteOperationRequest request = new 	DomesticFundTransferExecuteOperationRequest();
		  request.setContext(getContextMock());
		  settransactionDTO(request);
		  DomesticFundTransferExecuteOperationResponse response=new DomesticFundTransferExecuteOperationResponse();
		  try {
			  response = domesticFundTransferExecuteOperation.makeDomesticFundTransfer(request);
		    } catch (RuntimeException e) {
		    	  
		  }
	      assertNotNull(response);
		  assertEquals(false,response.isSuccess());
		  assertEquals(null,response.getTrnRef());
		  verify(domesticFundTransferService,systemParameterService,messageResourceService,transactionLimitService,transactionAbilityService);
	  }

	 @After
	 public void tearDown(){
		 domesticFundTransferService = null;
		 domesticFundTransferExecuteOperation = null;
 		 messageResourceService= null;
		 systemParameterService = null;
	 }


}
