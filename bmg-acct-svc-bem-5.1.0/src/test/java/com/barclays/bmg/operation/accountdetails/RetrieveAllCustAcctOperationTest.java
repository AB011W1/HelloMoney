/*
 * Copyright (c) 2013 Barclays Bank Plc, All Rights Reserved.
 *
 * This code is confidential to Barclays Bank Plc and shall not be disclosed
 * outside the Bank without the prior written permission of the Director of
 * CIO
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favour of Barclays Bank
 * Plc.
 */
/**
 * Package name is com.barclays.bmg.operation.accountdetails
 * /
 */
package com.barclays.bmg.operation.accountdetails;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.CustomerAccountDTO;
import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.operation.request.RetrieveAllCustAcctOperationRequest;
import com.barclays.bmg.operation.response.RetrieveAllCustAcctOperationResponse;
import com.barclays.bmg.service.RetrieveAllCustAcctService;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.request.RetrieveAllCustAcctServiceRequest;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.RetrieveAllCustAcctServiceResponse;
import com.barclays.bmg.service.response.SystemParameterListServiceResponse;

/**
 * ****************** Revision History **********************
 * </br>
 * Project Name  is  <b>bmg-acct-svc-bem-5.1.0</b>
 * </br>
 * The file name is  <b>RetrieveAllCustAcctOperationTest.java</b>
 * </br>
 * Description  is  <b>Initial Version</b>
 * </br>
 * Updated Date  is  <b>May 16, 2013</b>
 * </br>
 * ******************************************************
 *
 * @author e20037686
 * </br>
 * * The Class RetrieveAllCustAcctOperationTest created using JRE 1.6.0_10
 */
public class RetrieveAllCustAcctOperationTest
{

	/**
	 * The instance variable named "retrieveAllCustAcctOperation" is created.
	 */
	private RetrieveAllCustAcctOperation retrieveAllCustAcctOperation;

	/**
	 * The instance variable named "systemParameterService" is created.
	 */
	private SystemParameterService systemParameterService;

	/**
	 * The instance variable named "retrieveAllCustAcctService" is created.
	 */
	private RetrieveAllCustAcctService retrieveAllCustAcctService;

	/**
	 * The method is written for Base setup.
	 */
	public static void baseSetup()
	{}

	/**
	 * The method is written for Base end.
	 */
	@AfterClass
	public static void baseEnd()
	{}

	/**
	 * The method is written for Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception
	{
		retrieveAllCustAcctOperation = new RetrieveAllCustAcctOperation();
		systemParameterService = createMock(SystemParameterService.class);
		retrieveAllCustAcctService = createMock(RetrieveAllCustAcctService.class);

		retrieveAllCustAcctOperation.setSystemParameterService(systemParameterService);
		retrieveAllCustAcctOperation.setRetrieveAllCustAcctService(retrieveAllCustAcctService);
	}

//	When operation works fine throught the flow.
	/**
 * The method is written to test Retrieve all cust account operation when the operation through the flow.
 */
@Test
	public void retrieveAllCustAccount()
	{
		RetrieveAllCustAcctOperationRequest request = new RetrieveAllCustAcctOperationRequest();
		request.setContext(getContextMock());

		RetrieveAllCustAcctServiceResponse serRes = retrieveAllCustActServResStub();
		serRes.setSuccess(true);
		serRes.setContext(getContextMock());

		expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).atLeastOnce();
		expect(retrieveAllCustAcctService.retrieveAllCustAccount(isA(RetrieveAllCustAcctServiceRequest.class))).andReturn(serRes).atLeastOnce();
		replay(systemParameterService, retrieveAllCustAcctService);

		RetrieveAllCustAcctOperationResponse response = retrieveAllCustAcctOperation.retrieveAllCustAccount(request);

		assertNotNull(serRes);
		assertNotNull(response);

		assertNotNull(response.isSuccess());
		assertTrue(response.isSuccess());
		assertEquals(true, response.isSuccess());

		verify(systemParameterService, retrieveAllCustAcctService);
	}
//	When operation fails due to service fails
	/**
 * The method is written to test Retrieve all cust account operation when the operation fails during the flow.
 */
@Test
	public void retrieveAllCustAccountFails()
	{
		RetrieveAllCustAcctOperationRequest request = new RetrieveAllCustAcctOperationRequest();
		request.setContext(getContextMock());

		RetrieveAllCustAcctServiceResponse serRes = retrieveAllCustActServResStub();
		serRes.setSuccess(false);
		serRes.setContext(getContextMock());

		expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).atLeastOnce();
		expect(retrieveAllCustAcctService.retrieveAllCustAccount(isA(RetrieveAllCustAcctServiceRequest.class))).andReturn(serRes).atLeastOnce();
		replay(systemParameterService, retrieveAllCustAcctService);

		RetrieveAllCustAcctOperationResponse response = retrieveAllCustAcctOperation.retrieveAllCustAccount(request);

		assertNotNull(serRes);
		assertNotNull(response);

		assertNotNull(response.isSuccess());
		assertFalse(response.isSuccess());
		assertEquals(false, response.isSuccess());

		verify(systemParameterService, retrieveAllCustAcctService);
	}

	/**
	 * The method is written for Retrieve all cust act serv res stub.
	 *
	 * @return the RetrieveAllCustAcctServiceResponse's object
	 */
	private RetrieveAllCustAcctServiceResponse retrieveAllCustActServResStub()
	{
		RetrieveAllCustAcctServiceResponse response = new RetrieveAllCustAcctServiceResponse();
		List<? extends CustomerAccountDTO> accountList = Collections.emptyList();
		response.setAccountList(accountList);
		response.setResCde("00000");
		response.setResMsg("");
		return response;
	}

	/**
	 * Gets the system parameter list service response mock.
	 *
	 * @return the SystemParameterListServiceResponseMock
	 */
	private SystemParameterListServiceResponse getSystemParameterListServiceResponseMock()
	{
		SystemParameterListServiceResponse systemParameterListServiceResponse=new SystemParameterListServiceResponse();
		List<SystemParameterDTO> sysParamDtoList=new ArrayList<SystemParameterDTO>();
		sysParamDtoList.add(getSystemParameterDTOMock());
		systemParameterListServiceResponse.setSystemParameterDTOList(sysParamDtoList);
		systemParameterListServiceResponse.setSuccess(true);
		return systemParameterListServiceResponse;
	}

	/**
	 * Gets the context mock.
	 *
	 * @return the ContextMock
	 */
	private Context getContextMock()
	{
		Context context = new Context();
		context.setBusinessId("TZBRB");
		context.setCountryCode("TZ");
		context.setLanguageId("EN");
		context.setSystemId("UB");
		context.setCustomerId("07995733");
		context.setLoginId("BIRTZ001");
		context.setLocalCurrency("TZS");
		context.setTimezoneOffset("3");
		context.setSessionId("A009F6935024429917046897359A248C");
		context.setServiceVersion("2.0");
		context.setActivityRefNo("13642082008013");
		context.setOrgRefNo("13642082014262");
		return context;
	}

	/**
	 * Gets the system parameter dto mock.
	 *
	 * @return the SystemParameterDTOMock
	 */
	private SystemParameterDTO getSystemParameterDTOMock()
	{
		SystemParameterDTO systemParameterDTO = new SystemParameterDTO();
		systemParameterDTO.setActivityId("COMMON");
		systemParameterDTO.setParameterId("BeneficiaryManagement_EndPoint");
		systemParameterDTO.setParameterValue("http://mb4t.wload.global:7080/bem/bxx5_gateway");
		systemParameterDTO.setBusinessId("TZBRB");
		systemParameterDTO.setSystemId("UB");
		return systemParameterDTO;
	}

	/**
	 * The method is written for Tear down.
	 */
	@After
	public void tearDown()
	{
		retrieveAllCustAcctOperation = null;
		systemParameterService = null;
		retrieveAllCustAcctService = null;
	}
}