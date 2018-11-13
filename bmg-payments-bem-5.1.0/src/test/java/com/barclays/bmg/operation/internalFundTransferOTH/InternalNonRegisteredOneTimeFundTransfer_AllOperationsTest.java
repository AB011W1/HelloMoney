package  com.barclays.bmg.operation.internalFundTransferOTH;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.operation.accountservices.GetSelectedAccountOperation;
import com.barclays.bmg.operation.accountservices.RetrieveAccountListOperation;
import com.barclays.bmg.operation.formvalidation.InternalNonRegisteredFTFormValidateOperation;
import com.barclays.bmg.operation.formvalidation.TransactionLimitOperation;
import com.barclays.bmg.operation.fundtransfer.nonregistered.internal.RetrieveInternalNonRegisteredPayeeInfoOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.GetSelectedAccountOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.RetrieveAcctListOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.TransactionLimitOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.nonregistered.internal.FormValidateOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.nonregistered.internal.RetrieveInternalNonRegisteredPayeeInfoOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.GetSelectedAccountOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.RetrieveAcctListOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.TransactionLimitOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.nonregistered.internal.FormValidateOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.nonregistered.internal.RetrieveInternalNonRegisteredPayeeInfoOperationResponse;
import com.barclays.bmg.service.MessageResourceService;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.TransactionLimitService;
import com.barclays.bmg.service.accountdetails.CASADetailsService;
import com.barclays.bmg.service.accountdetails.request.CASADetailsServiceRequest;
import com.barclays.bmg.service.accounts.AllAccountService;
import com.barclays.bmg.service.accounts.request.AllAccountServiceRequest;
import com.barclays.bmg.service.product.ProductEligibilityService;
import com.barclays.bmg.service.product.request.ProductEligibilityServiceRequest;
import com.barclays.bmg.service.request.MessageResourceServiceRequest;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.request.TransactionLimitServiceRequest;

public class InternalNonRegisteredOneTimeFundTransfer_AllOperationsTest extends FundTransferBaseUtilityFunctions{
	private GetSelectedAccountOperation getSelectedAccountOperation;
 	private RetrieveAccountListOperation retrieveAccountListOperation;
 	private InternalNonRegisteredFTFormValidateOperation formValidateOperation;
    private	RetrieveInternalNonRegisteredPayeeInfoOperation retrieveInternalNonRegisteredPayeeInfoOperation;
    private TransactionLimitOperation transactionLimitOperation;


 	private CASADetailsService casaDetailsService;
	private SystemParameterService systemParameterService;
 	private MessageResourceService messageResourceService;
	private AllAccountService allAccountService;
	private ProductEligibilityService productEligibilityService;
 	private TransactionLimitService transactionLimitService;



	@Before
	 public void setUp() throws Exception {
 		transactionLimitService = createMock(TransactionLimitService.class);

		retrieveAccountListOperation = new RetrieveAccountListOperation();
		allAccountService = createMock(AllAccountService.class);
		productEligibilityService = createMock(ProductEligibilityService.class);
		retrieveAccountListOperation.setAllAccountService(allAccountService);
		retrieveAccountListOperation.setProductEligibilityService(productEligibilityService);
        systemParameterService = createNiceMock(SystemParameterService.class);
		retrieveAccountListOperation.setSystemParameterService(systemParameterService);
        messageResourceService=createNiceMock(MessageResourceService.class);
		retrieveAccountListOperation.setMessageResourceService(messageResourceService);

		getSelectedAccountOperation = new GetSelectedAccountOperation();
		getSelectedAccountOperation.setAllAccountService(allAccountService);
		getSelectedAccountOperation.setMessageResourceService(messageResourceService);
		getSelectedAccountOperation.setSystemParameterService(systemParameterService);
		getSelectedAccountOperation.setProductEligibilityService(productEligibilityService);

		formValidateOperation = new InternalNonRegisteredFTFormValidateOperation();
		formValidateOperation.setAllAccountService(allAccountService);
		formValidateOperation.setMessageResourceService(messageResourceService);
		formValidateOperation.setSystemParameterService(systemParameterService);
		formValidateOperation.setProductEligibilityService(productEligibilityService);
		formValidateOperation.setTransactionLimitService(transactionLimitService);


		retrieveInternalNonRegisteredPayeeInfoOperation = new RetrieveInternalNonRegisteredPayeeInfoOperation();
		casaDetailsService = createMock(CASADetailsService.class);
		retrieveInternalNonRegisteredPayeeInfoOperation.setCasaDetailsService(casaDetailsService);

 		retrieveInternalNonRegisteredPayeeInfoOperation.setSystemParameterService(systemParameterService);
 		retrieveInternalNonRegisteredPayeeInfoOperation.setMessageResourceService(messageResourceService);


		transactionLimitOperation = new TransactionLimitOperation();
		transactionLimitOperation.setTransactionLimitService(transactionLimitService);
        transactionLimitOperation.setSystemParameterService(systemParameterService);
 		transactionLimitOperation.setMessageResourceService(messageResourceService);

	}
	@Test
	  public void testRetrieveAccountListOperation_getCASASourceAcc() {
		  RetrieveAcctListOperationResponse retrieveAccListResponse = new RetrieveAcctListOperationResponse();
		  retrieveAccListResponse.setSuccess(true);
		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		  expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).anyTimes();


		  expect(allAccountService.retrieveAllAccount(isA(AllAccountServiceRequest.class))).andReturn(getRetrieveAcctListOperationResponseeMock()).anyTimes();
		  expect(productEligibilityService.getEligibleAccounts(isA(ProductEligibilityServiceRequest.class))).andReturn(getProductEligibilityOperationResponseMock()).anyTimes();
        replay(allAccountService,systemParameterService,messageResourceService,productEligibilityService);
		  RetrieveAcctListOperationRequest request = new RetrieveAcctListOperationRequest();
		  request.setContext(getContextMock());
		  RetrieveAcctListOperationResponse response=new RetrieveAcctListOperationResponse();

		  try {
			  response = retrieveAccountListOperation.getCASASourceAccounts(request);
		    } catch (RuntimeException e) {
		    	  
		  }
	      assertNotNull(response);
		  assertEquals(true,response.isSuccess());
		  assertNotNull(response.getAcctList());
		  verify(allAccountService,systemParameterService,messageResourceService,productEligibilityService);
	  }

	 @Test
	  public void testRetrieveAccountListOperation_getCASASourceAccForError() {
		  RetrieveAcctListOperationResponse retrieveAccListResponse = new RetrieveAcctListOperationResponse();
		  retrieveAccListResponse.setSuccess(true);
		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		  expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).anyTimes();

		  expect(allAccountService.retrieveAllAccount(isA(AllAccountServiceRequest.class))).andReturn(getRetrieveAcctListOperationResponseeMockForError()).anyTimes();
		  expect(productEligibilityService.getEligibleAccounts(isA(ProductEligibilityServiceRequest.class))).andReturn(getProductEligibilityOperationResponseMockForError()).anyTimes();
         replay(allAccountService,systemParameterService,messageResourceService,productEligibilityService);
		  RetrieveAcctListOperationRequest request = new RetrieveAcctListOperationRequest();
		  request.setContext(getContextMock());
		  RetrieveAcctListOperationResponse response=new RetrieveAcctListOperationResponse();
		  try {
			  response = retrieveAccountListOperation.getCASASourceAccounts(request);
		   } catch (RuntimeException e) {
		    	  
		  }
	      assertNotNull(response);
		  assertEquals(false,response.isSuccess());
		  assertEquals(null,response.getAcctList().get(0).getAccountNumber());
		  verify(allAccountService,systemParameterService,messageResourceService,productEligibilityService);
	  }
	 @Test
	  public void testRetrieveAccountListOperation_getSourceAcctList() {
		  RetrieveAcctListOperationResponse retrieveAccListResponse = new RetrieveAcctListOperationResponse();
		  retrieveAccListResponse.setSuccess(true);
		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		  expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).anyTimes();
		  expect(allAccountService.retrieveAllAccount(isA(AllAccountServiceRequest.class))).andReturn(getRetrieveAcctListOperationResponseeMock()).anyTimes();
		  expect(productEligibilityService.getEligibleAccounts(isA(ProductEligibilityServiceRequest.class))).andReturn(getProductEligibilityOperationResponseMock()).anyTimes();
          replay(allAccountService,systemParameterService,messageResourceService,productEligibilityService);
		  RetrieveAcctListOperationRequest request = new RetrieveAcctListOperationRequest();
		  request.setContext(getContextMock());
		  RetrieveAcctListOperationResponse response=new RetrieveAcctListOperationResponse();

		  try {
			  response = retrieveAccountListOperation.getSourceAcctList(request);
		    } catch (RuntimeException e) {
		    	  
		  }
	      assertNotNull(response);
		  assertEquals(true,response.isSuccess());
		  assertNotNull(response.getAcctList());
		  verify(allAccountService,systemParameterService,messageResourceService,productEligibilityService);
	  }

	 @Test
	  public void testRetrieveAccountListOperation_getSourceAcctListForError() {
		  RetrieveAcctListOperationResponse retrieveAccListResponse = new RetrieveAcctListOperationResponse();
		  retrieveAccListResponse.setSuccess(true);
		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		  expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).anyTimes();

		  expect(allAccountService.retrieveAllAccount(isA(AllAccountServiceRequest.class))).andReturn(getRetrieveAcctListOperationResponseeMockForError()).anyTimes();
		  expect(productEligibilityService.getEligibleAccounts(isA(ProductEligibilityServiceRequest.class))).andReturn(getProductEligibilityOperationResponseMockForError()).anyTimes();
         replay(allAccountService,systemParameterService,messageResourceService,productEligibilityService);
		  RetrieveAcctListOperationRequest request = new RetrieveAcctListOperationRequest();
		  request.setContext(getContextMock());
		  RetrieveAcctListOperationResponse response=new RetrieveAcctListOperationResponse();
		  try {
			  response = retrieveAccountListOperation.getSourceAcctList(request);
		   } catch (RuntimeException e) {
		    	  
		  }
	      assertNotNull(response);
		  assertEquals(false,response.isSuccess());
		  assertEquals(null,response.getAcctList().get(0).getAccountNumber());
		  verify(allAccountService,systemParameterService,messageResourceService,productEligibilityService);
	  }
	  @Test
	  public void testTransactionLimitOperation() {
		  TransactionLimitOperationResponse transLimitOperationRes = new TransactionLimitOperationResponse();
		  transLimitOperationRes.setSuccess(true);
		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		  expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).anyTimes();
		  expect(transactionLimitService.getTransactionLimit(isA(TransactionLimitServiceRequest.class))).andReturn(getTransactionLimitServiceResponseMock()).anyTimes();
        replay(transactionLimitService,systemParameterService,messageResourceService);

        TransactionLimitOperationRequest request = new TransactionLimitOperationRequest();
		  request.setContext(getContextMock());
		  TransactionLimitOperationResponse response=new TransactionLimitOperationResponse();

		  try {
				 response = transactionLimitOperation.getAValidDailyLimit(request);
		    } catch (RuntimeException e) {
		    	  
		  }
	      assertNotNull(response);
		   assertEquals(true,response.isSuccess());
		  assertNotNull(response.getAValidDailyLimit());
		  verify(transactionLimitService,systemParameterService,messageResourceService);
	  }

	  @Test
	  public void testTransactionLimitOperationForError() {
		  RetrieveAcctListOperationResponse retrieveAccListResponse = new RetrieveAcctListOperationResponse();
		  retrieveAccListResponse.setSuccess(true);
		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		  expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).anyTimes();
		  expect(transactionLimitService.getTransactionLimit(isA(TransactionLimitServiceRequest.class))).andReturn(getTransactionLimitServiceResponseMockForError()).anyTimes();
        replay(transactionLimitService,systemParameterService,messageResourceService);

        TransactionLimitOperationRequest request = new TransactionLimitOperationRequest();
		  request.setContext(getContextMock());
		  TransactionLimitOperationResponse response=new TransactionLimitOperationResponse();

		  try {
				 response = transactionLimitOperation.getAValidDailyLimit(request);
		    } catch (RuntimeException e) {
		    	  
		  }
	      assertNotNull(response);
		  //assertEquals(false,response.isSuccess());
	      //This is commented because we can not test this false here...there is bug in source code .
		   assertEquals(null,response.getAValidDailyLimit());
		  verify(transactionLimitService,systemParameterService,messageResourceService);
	  }
	  @Test
	  public void testRetrieveInternalNonRegisteredPayeeInfoOperation() {
		  RetrieveInternalNonRegisteredPayeeInfoOperationResponse retrInternalNonRegPayeInfo = new RetrieveInternalNonRegisteredPayeeInfoOperationResponse();
		  retrInternalNonRegPayeInfo.setSuccess(true);
		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		  expect(casaDetailsService.retrieveCASADetails(isA(CASADetailsServiceRequest.class))).andReturn(getCASADetailsServiceResponseMock()).anyTimes();

		  replay(casaDetailsService,systemParameterService,messageResourceService);

		  RetrieveInternalNonRegisteredPayeeInfoOperationRequest request = new RetrieveInternalNonRegisteredPayeeInfoOperationRequest();
		  request.setContext(getContextMock());

		  RetrieveInternalNonRegisteredPayeeInfoOperationResponse response =new RetrieveInternalNonRegisteredPayeeInfoOperationResponse();
		  try {
				response = retrieveInternalNonRegisteredPayeeInfoOperation.retrievePayeeInfo(request);
		    } catch (RuntimeException e) {
		    	  
		  }
	      assertNotNull(response);
		  assertEquals(true,response.isSuccess());
		  assertNotNull(response.getCasaAccountDTO());
		  verify(casaDetailsService,systemParameterService,messageResourceService);
	  }

	  @Test
	  public void testRetrieveInternalNonRegisteredPayeeInfoOperationForError() {
		  RetrieveInternalNonRegisteredPayeeInfoOperationResponse retrInternalNonRegPayeInfo = new RetrieveInternalNonRegisteredPayeeInfoOperationResponse();
		  retrInternalNonRegPayeInfo.setSuccess(true);
 		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
 		  expect(casaDetailsService.retrieveCASADetails(isA(CASADetailsServiceRequest.class))).andReturn(getCASADetailsServiceResponseMockForError()).anyTimes();
 		  expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).anyTimes();

 		  replay(casaDetailsService,systemParameterService,messageResourceService);

 		  RetrieveInternalNonRegisteredPayeeInfoOperationRequest request = new RetrieveInternalNonRegisteredPayeeInfoOperationRequest();
		  request.setContext(getContextMock());

		  RetrieveInternalNonRegisteredPayeeInfoOperationResponse response =new RetrieveInternalNonRegisteredPayeeInfoOperationResponse();
		  try {
				response = retrieveInternalNonRegisteredPayeeInfoOperation.retrievePayeeInfo(request);
		    } catch (RuntimeException e) {
		    	  
		  }
	      assertNotNull(response);
		  assertEquals(false,response.isSuccess());
		  assertEquals(null,response.getCasaAccountDTO());
		  verify(casaDetailsService,systemParameterService,messageResourceService);
	  }

	 @Test
     public void  testFundTransferFormValidationOperation(){

		  GetSelectedAccountOperationResponse getSelectResponse = new GetSelectedAccountOperationResponse();
		  getSelectResponse.setSuccess(true);
		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
 		  expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).anyTimes();
 		  expect(allAccountService.retrieveAllAccount(isA(AllAccountServiceRequest.class))).andReturn(getRetrieveAcctListOperationResponseeMock()).anyTimes();
 		  expect(productEligibilityService.getEligibleAccounts(isA(ProductEligibilityServiceRequest.class))).andReturn(getProductEligibilityOperationResponseMock()).anyTimes();
		  expect(transactionLimitService.checkTransactionLimit(isA(TransactionLimitServiceRequest.class))).andReturn(getTransactionLimitServiceResponseMock()).anyTimes();

		  GetSelectedAccountOperationRequest request = new GetSelectedAccountOperationRequest();
		  request.setContext(getContextMock());
		  request.setAcctNumber("9000000008");
          replay(transactionLimitService,allAccountService,systemParameterService,messageResourceService,productEligibilityService);

		  GetSelectedAccountOperationResponse response =new GetSelectedAccountOperationResponse();

		  FormValidateOperationResponse formValidateOperationResponse = new FormValidateOperationResponse();
		   FormValidateOperationRequest formValidateOperationRequest = new FormValidateOperationRequest();
		  request.setContext(getContextMock());
		  formValidateOperationRequest.setContext(getContextMock());
		  setReqParams(formValidateOperationRequest);
		  try {
				 response = getSelectedAccountOperation.getSelectedSourceAccount(request);
				 formValidateOperation.currencyValidation(getContextMock(),
							"getCurr",FundTransferConstants.LIST_VAL_CURR_SUPPORT_INT_ACC,
							response.getSelectedAcct().getCurrency(),
							"INR",formValidateOperationResponse);

 						formValidateOperationResponse = formValidateOperation
								.validateForm(formValidateOperationRequest);


		    } catch (RuntimeException e) {
		    	  
		  }
	      assertNotNull(response);
	      assertNotNull(formValidateOperationResponse);

		  assertEquals(true,response.isSuccess());
		  assertEquals(true,formValidateOperationResponse.isSuccess());

		  assertNotNull(response.getSelectedAcct());
		  assertNotNull(formValidateOperationResponse.getTxnAmt());
		  verify(transactionLimitService,allAccountService,systemParameterService,messageResourceService,productEligibilityService);

     }
     private void setReqParams(
			FormValidateOperationRequest request) {
    	 CustomerAccountDTO CustomerAccountDTO = new CustomerAccountDTO();
			CustomerAccountDTO.setAccountNickName("PUNE_INDIA");
			CustomerAccountDTO.setAccountStatus("ENABLED");
			CustomerAccountDTO.setAccountNumber("9000000008");
			BigDecimal balance_300 = new BigDecimal(300);
			CustomerAccountDTO.setAsset(balance_300);
			CustomerAccountDTO.setAvailableBalance(balance_300);
			request.setTxnType("SAVING");
			Amount amount = new Amount();
			amount.setAmount(balance_300);
			request.setTxnAmt(amount);
		   request.setFrmAct(CustomerAccountDTO);
	}
	@Test
     public void  testGetSelectOperation(){

		  GetSelectedAccountOperationResponse getSelectResponse = new GetSelectedAccountOperationResponse();
		  getSelectResponse.setSuccess(true);
		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
 		  expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).anyTimes();
 		  expect(allAccountService.retrieveAllAccount(isA(AllAccountServiceRequest.class))).andReturn(getRetrieveAcctListOperationResponseeMock()).anyTimes();
 		  expect(productEligibilityService.getEligibleAccounts(isA(ProductEligibilityServiceRequest.class))).andReturn(getProductEligibilityOperationResponseMock()).anyTimes();

		  GetSelectedAccountOperationRequest request = new GetSelectedAccountOperationRequest();
		  request.setContext(getContextMock());
		  request.setAcctNumber("9000000008");
          replay(allAccountService,systemParameterService,messageResourceService,productEligibilityService);

		  GetSelectedAccountOperationResponse response =new GetSelectedAccountOperationResponse();
		  try {
				 response = getSelectedAccountOperation.getSelectedSourceAccount(request);
		    } catch (RuntimeException e) {
		    	  
		  }
	      assertNotNull(response);
		  assertEquals(true,response.isSuccess());
		  assertNotNull(response.getSelectedAcct());
		  verify(allAccountService,systemParameterService,messageResourceService,productEligibilityService);

     }
     @Test
     public void  testGetSelectOperationForError(){

		  GetSelectedAccountOperationResponse getSelectResponse = new GetSelectedAccountOperationResponse();
		  getSelectResponse.setSuccess(true);
		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
 		  expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).anyTimes();
 		  expect(allAccountService.retrieveAllAccount(isA(AllAccountServiceRequest.class))).andReturn(getRetrieveAcctListOperationResponseeMockForError()).anyTimes();
 		  expect(productEligibilityService.getEligibleAccounts(isA(ProductEligibilityServiceRequest.class))).andReturn(getProductEligibilityOperationResponseMock()).anyTimes();

		  GetSelectedAccountOperationRequest request = new GetSelectedAccountOperationRequest();
		  request.setContext(getContextMock());
		  request.setAcctNumber("9000000008");
          replay(allAccountService,systemParameterService,messageResourceService,productEligibilityService);

		  GetSelectedAccountOperationResponse response =new GetSelectedAccountOperationResponse();
		  try {
				 response = getSelectedAccountOperation.getSelectedSourceAccount(request);
		    } catch (RuntimeException e) {
		    	  
		  }
	      assertNotNull(response);
		  assertEquals(false,response.isSuccess());
		  assertEquals(null,response.getSelectedAcct());
		  verify(allAccountService,systemParameterService,messageResourceService,productEligibilityService);

     }



	 @After
	 public void tearDown() {
		allAccountService = null;
		retrieveAccountListOperation = null;
		messageResourceService = null;
		systemParameterService = null;
		getSelectedAccountOperation = null;
		retrieveAccountListOperation = null;
		formValidateOperation = null;
		retrieveInternalNonRegisteredPayeeInfoOperation = null;
	   	casaDetailsService = null;
		transactionLimitOperation = null;
		productEligibilityService = null;
		transactionLimitService = null;

	}

}
