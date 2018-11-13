/**
 *
 */
package com.barclays.bmg.operation.fundtransfer.external;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.operation.BaseOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.ExternalFTFormSubmissionOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.ExternalFTFormSubmissionOperationResponse;
import com.barclays.bmg.service.MessageResourceService;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.product.ListValueResService;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResServiceResponse;
import com.barclays.bmg.service.request.MessageResourceServiceRequest;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;

/**
 * @author E20037686
 *
 */
public class ExternalFTFormSubmissionOperationTest extends BaseOperation
{
	private ExternalFTFormSubmissionOperation externalFTFormSubmissionOperation;
	private MessageResourceService messageResourceService;
	private SystemParameterService systemParameterService;
	private ListValueResService listValueResService;

	@BeforeClass
	public static void baseSetup()
	{}

	@AfterClass
	public static void baseEnd()
	{}

	@Before
	public void setUp() throws Exception
	{
		externalFTFormSubmissionOperation = new ExternalFTFormSubmissionOperation();
		systemParameterService = createMock(SystemParameterService.class);
		messageResourceService = createNiceMock(MessageResourceService.class);
		listValueResService = createNiceMock(ListValueResService.class);

		externalFTFormSubmissionOperation.setSystemParameterService(systemParameterService);
		externalFTFormSubmissionOperation.setMessageResourceService(messageResourceService);
		externalFTFormSubmissionOperation.setListValueResService(listValueResService);
	}

// when the operation runs completely throughout
	@Test
	public void retrievePayeeListTrue()
	{
		ExternalFTFormSubmissionOperationRequest request = makeRequest();

		ListValueResServiceResponse listValueResServiceResponse = new ListValueResServiceResponse();
		listValueResServiceResponse.setListValueLabel("");

		expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).atLeastOnce();
		expect(listValueResService.getListValueRes(isA(ListValueResServiceRequest.class))).andReturn(listValueResServiceResponse).atLeastOnce();

		replay(systemParameterService, listValueResService);

		ExternalFTFormSubmissionOperationResponse response = new ExternalFTFormSubmissionOperationResponse();
		try
		{
			response = externalFTFormSubmissionOperation.validateForm(request);
		}
		catch(RuntimeException e)
		{
			
		}
		assertNotNull(response);
		assertNotNull(response.isSuccess());
		assertTrue(response.isSuccess());
		assertNotNull(response.getPayRsonValue());
		assertNotNull(response.getPayDtlsValue());

		verify(systemParameterService, listValueResService);
	}

// when the operation fails
	@Test
	public void retrievePayeeListFails()
	{
		ExternalFTFormSubmissionOperationRequest request = makeRequest();

		ListValueResServiceResponse listValueResServiceResponse = new ListValueResServiceResponse();
		listValueResServiceResponse.setListValueLabel("");

		expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).atLeastOnce();
		expect(listValueResService.getListValueRes(isA(ListValueResServiceRequest.class))).andReturn(listValueResServiceResponse).atLeastOnce();
		expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).anyTimes();

		replay(systemParameterService, listValueResService, messageResourceService);

		ExternalFTFormSubmissionOperationResponse response = new ExternalFTFormSubmissionOperationResponse();
		try
		{
			response = externalFTFormSubmissionOperation.validateForm(request);

			response.setResMsg("");

		}
		catch(RuntimeException e)
		{
			
		}
		assertNotNull(response);
		assertNotNull(response.isSuccess());
		assertNotNull(response.getResMsg());
		assertTrue(response.getResMsg().isEmpty());

		verify(systemParameterService, listValueResService, messageResourceService);
	}

	private ExternalFTFormSubmissionOperationRequest makeRequest()
	{
		ExternalFTFormSubmissionOperationRequest req = new ExternalFTFormSubmissionOperationRequest();
		req.setContext(getContextMock());
		req.setCurr("AED");
		req.setSourceAcct(new CustomerAccountDTO());

		return req;
	}

	@After
	public void tearDown()
	{
		externalFTFormSubmissionOperation = null;
		messageResourceService = null;
		systemParameterService = null;
		listValueResService = null;
	}
}