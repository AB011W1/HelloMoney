package com.barclays.bmg.operation.validation.fundtransfer.own;

/**
 *
 */

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.operation.BaseOperation;
import com.barclays.bmg.operation.request.fundtransfer.own.ValidateAccountListOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.own.ValidateAccountListOperationResponse;
import com.barclays.bmg.service.MessageResourceService;
import com.barclays.bmg.service.request.MessageResourceServiceRequest;

/**
 * @author E20037686
 *
 */
public class ValidateAccountListOperationTest extends BaseOperation
{
	private ValidateAccountListOperation validateAccountListOperation;
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
		validateAccountListOperation = new ValidateAccountListOperation();
		messageResourceService = createNiceMock(MessageResourceService.class);
		validateAccountListOperation.setMessageResourceService(messageResourceService);
	}

//	if validate false, when srcAcctLst and destAcctLst is not set to null and size of both equals to 1;
	@Test
	public void validateFalse()
	{
		expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).atLeastOnce();

		replay(messageResourceService);

		ValidateAccountListOperationRequest request = new ValidateAccountListOperationRequest();
		request.setContext(getContextMock());
		request.setDestAcctLst(getList());
		request.setSrcAcctLst(getList());

		ValidateAccountListOperationResponse response = new ValidateAccountListOperationResponse();
		try
		{
			response = validateAccountListOperation.validate(request);
		}
		catch(RuntimeException e)
		{
			
		}
		assertNotNull(response);
		assertNotNull(response.isSuccess());
		assertFalse(response.isSuccess());
		assertEquals(false, response.isSuccess());

		verify(messageResourceService);
	}

//	if validate true, when srcAcctLst and destAcctLst is set to null;
	@Test
	public void validateTrue()
	{
		ValidateAccountListOperationRequest request = new ValidateAccountListOperationRequest();
		request.setContext(getContextMock());

		ValidateAccountListOperationResponse response = new ValidateAccountListOperationResponse();
		try
		{
			response = validateAccountListOperation.validate(request);
		}
		catch(RuntimeException e)
		{
			
		}
		assertNotNull(response);
		assertNotNull(response.isSuccess());
		assertTrue(response.isSuccess());
		assertEquals(true, response.isSuccess());
	}

	private List<? extends CustomerAccountDTO> getList()
	{
		List list = new ArrayList();
		list.add(new CustomerAccountDTO());
		return list;
	}

	@After
	public void tearDown()
	{
		validateAccountListOperation = null;
		messageResourceService = null;
	}
}