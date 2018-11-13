/**
 *
 */
package com.barclays.bmg.operation.formvalidation;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.operation.BaseOperation;
import com.barclays.bmg.operation.request.fundtransfer.own.OwnFundTransferRel1ValidateOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.own.OwnFundTransferRel1ValidateOperationResponse;
import com.barclays.bmg.service.MessageResourceService;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.request.MessageResourceServiceRequest;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;

/**
 * @author E20037686
 *
 */
public class OwnFundTransferRel1ValidateOperationTest extends BaseOperation
{
	private OwnFundTransferRel1ValidateOperation ownFundTransferRel1ValidateOperation;
	private MessageResourceService messageResourceService;
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
		ownFundTransferRel1ValidateOperation = new OwnFundTransferRel1ValidateOperation();
		systemParameterService = createMock(SystemParameterService.class);
		messageResourceService = createNiceMock(MessageResourceService.class);

		ownFundTransferRel1ValidateOperation.setSystemParameterService(systemParameterService);
		ownFundTransferRel1ValidateOperation.setMessageResourceService(messageResourceService);
	}

//	When validation fails due to mismatch of currency with source and destination Account.
	@Test
	public void validateAccountsFalse()
	{
		expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).atLeastOnce();
		expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).atLeastOnce();

		replay(messageResourceService, systemParameterService);

		OwnFundTransferRel1ValidateOperationRequest request = new OwnFundTransferRel1ValidateOperationRequest();
		request.setContext(getContextMock());
		CustomerAccountDTO dto = new CustomerAccountDTO(), dto1 = new CustomerAccountDTO();
		dto.setCurrency("MUR");
		dto1.setCurrency("");

		request.setDestAcct(dto);
		request.setSrcAcct(dto1);

		OwnFundTransferRel1ValidateOperationResponse response = new OwnFundTransferRel1ValidateOperationResponse();
		try
		{
			response = ownFundTransferRel1ValidateOperation.validateAccounts(request);
		}
		catch(RuntimeException e)
		{
			
		}
		assertNotNull(response);
		assertNotNull(response.isSuccess());
		assertFalse(response.isSuccess());
		assertEquals(false, response.isSuccess());

		verify(messageResourceService, systemParameterService);
	}

//	if validate true
	@Test
	public void validateAccountsTrue()
	{
		expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).atLeastOnce();

		replay(systemParameterService);
		OwnFundTransferRel1ValidateOperationRequest request = new OwnFundTransferRel1ValidateOperationRequest();
		request.setContext(getContextMock());

		OwnFundTransferRel1ValidateOperationResponse response = new OwnFundTransferRel1ValidateOperationResponse();
		try
		{
			response = ownFundTransferRel1ValidateOperation.validateAccounts(request);
		}
		catch(RuntimeException e)
		{
			
		}
		assertNotNull(response);
		assertNotNull(response.isSuccess());
		assertTrue(response.isSuccess());
		assertEquals(true, response.isSuccess());

		verify(systemParameterService);
	}

	@After
	public void tearDown()
	{
		ownFundTransferRel1ValidateOperation = null;
		messageResourceService = null;
		systemParameterService = null;
	}
}