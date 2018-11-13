/**
 *
 */
package com.barclays.bmg.operation.formvalidation;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.barclays.bmg.operation.BaseOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.TransactionLimitOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.TransactionLimitOperationResponse;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.TransactionLimitService;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.request.TransactionLimitServiceRequest;
import com.barclays.bmg.service.response.SystemParameterServiceResponse;
import com.barclays.bmg.service.response.TransactionLimitServiceResponse;

/**
 * @author E20037686
 *
 */
public class TransactionLimitOperationTest extends BaseOperation
{
	private TransactionLimitOperation transactionLimitOperation;
	private TransactionLimitService transactionLimitService;
	private SystemParameterService systemParameterService;

	@BeforeClass
	public static void baseSetup()
	{}

	@AfterClass
	public static void baseEnd()
	{}

	@Before
	public void setUp() throws Exception
	{
		transactionLimitOperation = new TransactionLimitOperation();
		transactionLimitService = createNiceMock(TransactionLimitService.class);
		systemParameterService = createNiceMock(SystemParameterService.class);

		transactionLimitOperation.setTransactionLimitService(transactionLimitService);
		transactionLimitOperation.setSystemParameterService(systemParameterService);
	}

	@Test
	public void getAValidDailyLimit()
	{
		expect(transactionLimitService.getTransactionLimit(isA(TransactionLimitServiceRequest.class))).andReturn(getTransactionLimitServiceResponseStub()).atLeastOnce();

		replay(transactionLimitService);

		TransactionLimitOperationRequest request = new TransactionLimitOperationRequest();
		request.setContext(getContextMock());

		TransactionLimitOperationResponse response = new TransactionLimitOperationResponse();
		try
		{
			response = transactionLimitOperation.getAValidDailyLimit(request);
		}
		catch(RuntimeException e)
		{
			
		}
		assertNotNull(response);
		assertNotNull(response.getAValidDailyLimit());

		verify(transactionLimitService);
	}

	/**
	 * @param request
	 * @return
	 *
	 * Check whether Transaction limit is crossed depending on txnAmt in request.
	 * Also returns whether second level auth is required or Not.
	 */
	@Test
	public void checkTransactionLimit()
	{
		TransactionLimitServiceResponse transactionLimitServiceResponse = getTransactionLimitServiceResponseStub();
		transactionLimitServiceResponse.setAuthRequired(true);

		SystemParameterServiceResponse systemParameterServiceResponse = new SystemParameterServiceResponse();

		expect(transactionLimitService.checkTransactionLimit(isA(TransactionLimitServiceRequest.class))).andReturn(transactionLimitServiceResponse).atLeastOnce();
		expect(systemParameterService.getSystemParameter(isA(SystemParameterServiceRequest.class))).andReturn(systemParameterServiceResponse).atLeastOnce();

		replay(transactionLimitService, systemParameterService);

		TransactionLimitOperationRequest request = new TransactionLimitOperationRequest();
		request.setContext(getContextMock());

		TransactionLimitOperationResponse response = new TransactionLimitOperationResponse();
		try
		{
			response = transactionLimitOperation.checkTransactionLimit(request);
		}
		catch(RuntimeException e)
		{
			
		}
		assertNotNull(response);
		assertNotNull(response.isAuthRequired());
		assertTrue(response.isAuthRequired());
		assertEquals(true, response.isAuthRequired());
		assertNotNull(response.getAuthType());

		verify(transactionLimitService, systemParameterService);
	}

	/**
	 * @param request
	 * @return
	 *
	 * When checkTransactionLimit service fails
	 */
	@Test
	public void checkTransactionLimitFalse()
	{
		TransactionLimitServiceResponse transactionLimitServiceResponse = getTransactionLimitServiceResponseStub();
		transactionLimitServiceResponse.setSuccess(false);
		transactionLimitServiceResponse.setResCde("");
		transactionLimitServiceResponse.setResMsg("");
		transactionLimitServiceResponse.setErrTyp("");

		expect(transactionLimitService.checkTransactionLimit(isA(TransactionLimitServiceRequest.class))).andReturn(transactionLimitServiceResponse).atLeastOnce();

		replay(transactionLimitService);

		TransactionLimitOperationRequest request = new TransactionLimitOperationRequest();
		request.setContext(getContextMock());

		TransactionLimitOperationResponse response = new TransactionLimitOperationResponse();
		try
		{
			response = transactionLimitOperation.checkTransactionLimit(request);
		}
		catch(RuntimeException e)
		{
			
		}
		assertNotNull(transactionLimitServiceResponse);
		assertNotNull(transactionLimitServiceResponse.isSuccess());
		assertFalse(transactionLimitServiceResponse.isSuccess());
		assertNotNull(response);
		assertNotNull(response.getResCde());
		assertNotNull(response.getResMsg());
		assertNotNull(response.getErrTyp());

		verify(transactionLimitService);
	}

	private TransactionLimitServiceResponse getTransactionLimitServiceResponseStub()
	{
		TransactionLimitServiceResponse res = new TransactionLimitServiceResponse();
		res.setAuthRequired(false);
		res.setSuccess(true);
		res.setAValidDailyLimit(new BigDecimal(1));
		return res;
	}

	@After
	public void tearDown()
	{
		transactionLimitOperation = null;
		transactionLimitService = null;
	}
}