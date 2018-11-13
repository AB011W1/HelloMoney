/**
 *
 */
package com.barclays.bmg.operation.payments;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.TransactionDTO;
import com.barclays.bmg.operation.BaseOperation;
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
import com.barclays.bmg.service.response.DomesticFundTransferServiceResponse;
import com.barclays.bmg.service.response.TransactionAbilityResponse;

/**
 * @author E20037686
 *
 */
public class DomesticFundTransferExecuteOperationTest extends BaseOperation
{
	private DomesticFundTransferExecuteOperation domesticFundTransferExecuteOperation;
	private SystemParameterService systemParameterService;
	private DomesticFundTransferService domesticFundTransferService;
	private TransactionAbilityService transactionAbilityService;
	private TransactionLimitService transactionLimitService;
	private MessageResourceService messageResourceService;

	@BeforeClass
	public static void baseSetup()
	{}

	@AfterClass
	public static void baseEnd()
	{}

	@Before
	public void setUp() throws Exception
	{
		domesticFundTransferExecuteOperation = new DomesticFundTransferExecuteOperation();
		systemParameterService = createMock(SystemParameterService.class);
		domesticFundTransferService = createNiceMock(DomesticFundTransferService.class);
		transactionAbilityService = createNiceMock(TransactionAbilityService.class);
		messageResourceService = createNiceMock(MessageResourceService.class);
		transactionLimitService = createNiceMock(TransactionLimitService.class);

		domesticFundTransferExecuteOperation.setSystemParameterService(systemParameterService);
		domesticFundTransferExecuteOperation.setDomesticFundTransferService(domesticFundTransferService);
		domesticFundTransferExecuteOperation.setTransactionAbilityService(transactionAbilityService);
		domesticFundTransferExecuteOperation.setMessageResourceService(messageResourceService);
		domesticFundTransferExecuteOperation.setTransactionLimitService(transactionLimitService);
	}

//	When transaction is available through "TransactionAbilityService"
	@Test
	public void makeDomesticFundTransfer()
	{
		DomesticFundTransferServiceResponse serviceResponse = new DomesticFundTransferServiceResponse();
		serviceResponse.setSuccess(true);
		serviceResponse.setTrnDate(new Date());
		serviceResponse.setTrnRef("txnrefno");
		serviceResponse.setTxnMsg("txnMsg");
		serviceResponse.setTxnTyp("txnType");

		expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		expect(transactionAbilityService.checkTransactionAbility(isA(TransactionAbilityRequest.class))).andReturn(getTransactionAbilityResponseMock(true)).anyTimes();
		expect(domesticFundTransferService.makeDomesticFundTransfer(isA(DomesticFundTransferServiceRequest.class))).andReturn(serviceResponse).atLeastOnce();

		replay(systemParameterService, domesticFundTransferService, transactionAbilityService);

		DomesticFundTransferExecuteOperationRequest request = new DomesticFundTransferExecuteOperationRequest();
		request.setTransactionDTO(getTransactionDTO());
		request.setContext(getContextMock());

		DomesticFundTransferExecuteOperationResponse response = new DomesticFundTransferExecuteOperationResponse();
		try
		{
			response = domesticFundTransferExecuteOperation.makeDomesticFundTransfer(request);
		}
		catch(RuntimeException e)
		{
			
		}
		assertNotNull(response);
		assertTrue(response.isSuccess());
		assertNotNull(response.getTrnDate());
		assertNotNull(response.getTrnRef());
		assertNotNull(response.getTxnMsg());
		assertNotNull(response.isScndLvlAuthReq());
		verify(systemParameterService, domesticFundTransferService, transactionAbilityService);
	}

//	when transaction is not available through "TransactionAbilityService"
	@Test
	public void makeDomesticFundTransferIsTransactionableFalse()
	{
		DomesticFundTransferServiceResponse serviceResponse = new DomesticFundTransferServiceResponse();

		expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		expect(transactionAbilityService.checkTransactionAbility(isA(TransactionAbilityRequest.class))).andReturn(getTransactionAbilityResponseMock(false)).anyTimes();
		expect(domesticFundTransferService.makeDomesticFundTransfer(isA(DomesticFundTransferServiceRequest.class))).andReturn(serviceResponse).anyTimes();

		replay(systemParameterService, transactionAbilityService, domesticFundTransferService);

		DomesticFundTransferExecuteOperationRequest request = new DomesticFundTransferExecuteOperationRequest();
		request.setTransactionDTO(getTransactionDTO());
		request.setContext(getContextMock());

		DomesticFundTransferExecuteOperationResponse response = new DomesticFundTransferExecuteOperationResponse();
		try
		{
			response = domesticFundTransferExecuteOperation.makeDomesticFundTransfer(request);
		}
		catch(RuntimeException e)
		{
			
		}
		assertNotNull(response);
		assertFalse(response.isSuccess());
		assertNotNull(response.isScndLvlAuthReq());

		verify(systemParameterService, transactionAbilityService, domesticFundTransferService);
	}

//	when second level authentication is set to false
	@Test
	public void makeDomesticFundTransferIsScndLvlauthReqFalse()
	{
		DomesticFundTransferServiceResponse serviceResponse = new DomesticFundTransferServiceResponse();

		expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		expect(transactionAbilityService.checkTransactionAbility(isA(TransactionAbilityRequest.class))).andReturn(getTransactionAbilityResponseMock(true)).anyTimes();
		expect(domesticFundTransferService.makeDomesticFundTransfer(isA(DomesticFundTransferServiceRequest.class))).andReturn(serviceResponse).anyTimes();

		replay(systemParameterService, transactionAbilityService, domesticFundTransferService);

		DomesticFundTransferExecuteOperationRequest request = new DomesticFundTransferExecuteOperationRequest();
		request.setTransactionDTO(getTransactionDTO());
		request.getTransactionDTO().setScndLvlauthReq(true);
		request.setContext(getContextMock());

		DomesticFundTransferExecuteOperationResponse response = new DomesticFundTransferExecuteOperationResponse();
		try
		{
			response = domesticFundTransferExecuteOperation.makeDomesticFundTransfer(request);
		}
		catch(RuntimeException e)
		{
			
		}
		assertNotNull(response);
		assertNotNull(response.isSuccess());
		assertTrue(response.isSuccess());
		assertTrue(response.isScndLvlAuthReq());

		verify(systemParameterService, transactionAbilityService, domesticFundTransferService);
	}

	public TransactionDTO getTransactionDTO()
	{
		TransactionDTO dto = new TransactionDTO();
		dto.setScndLvlauthReq(false);
		dto.setScndLvlAuthTyp("");
		dto.setTxnAmt(new Amount("MUR", new BigDecimal(1)));
		return dto;
	}

	private TransactionAbilityResponse getTransactionAbilityResponseMock(Boolean isTransactional)
	{
		TransactionAbilityResponse response = new TransactionAbilityResponse();
		response.setContext(getContextMock());
		response.setTransactionable(isTransactional);
		return response;
	}

	@After
	public void tearDown()
	{
		domesticFundTransferExecuteOperation = null;
		domesticFundTransferService = null;
		transactionAbilityService = null;
		systemParameterService = null;
		transactionLimitService = null;
		messageResourceService = null;
	}
}