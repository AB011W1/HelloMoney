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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.barclays.bmg.constants.FundTransferConstants;
import com.barclays.bmg.constants.FundTransferResponseCodeConstants;
import com.barclays.bmg.dto.Amount;
import com.barclays.bmg.dto.Charge;
import com.barclays.bmg.dto.CreditCardAccountDTO;
import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.fxrate.service.request.FxRateServiceRequest;
import com.barclays.bmg.fxrate.service.response.FxRateServiceResponse;
import com.barclays.bmg.operation.BaseOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.FormValidateOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.FormValidateOperationResponse;
import com.barclays.bmg.service.FxRateService;
import com.barclays.bmg.service.MessageResourceService;
import com.barclays.bmg.service.RetreiveChargeDetailsService;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.TransactionLimitService;
import com.barclays.bmg.service.accountdetails.CreditCardDetailsService;
import com.barclays.bmg.service.accountdetails.request.CreditCardAccountDetailsServiceRequest;
import com.barclays.bmg.service.accountdetails.response.CreditCardAccountDetailsServiceResponse;
import com.barclays.bmg.service.product.ListValueResService;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;
import com.barclays.bmg.service.request.MessageResourceServiceRequest;
import com.barclays.bmg.service.request.RetreiveChargeDetailsServiceRequest;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.request.TransactionLimitServiceRequest;
import com.barclays.bmg.service.response.RetreiveChargeDetailsServiceResponse;
import com.barclays.bmg.service.response.SystemParameterListServiceResponse;
import com.barclays.bmg.service.response.TransactionLimitServiceResponse;

/**
 * @author E20037686
 *
 */
public class FormValidateOperationTest extends BaseOperation
{
	private FormValidateOperation formValidateOperation;
	private MessageResourceService messageResourceService;
	private SystemParameterService systemParameterService;
	private RetreiveChargeDetailsService retreiveChargeDetailsService;
	private FxRateService fxRateService;
	private CreditCardDetailsService creditCardDetailsService;
	private TransactionLimitService transactionLimitService;
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
		formValidateOperation = new FormValidateOperation();
		systemParameterService = createMock(SystemParameterService.class);
		messageResourceService = createNiceMock(MessageResourceService.class);
		retreiveChargeDetailsService = createNiceMock(RetreiveChargeDetailsService.class);
		fxRateService = createNiceMock(FxRateService.class);
		creditCardDetailsService = createNiceMock(CreditCardDetailsService.class);
		transactionLimitService = createNiceMock(TransactionLimitService.class);
		listValueResService = createNiceMock(ListValueResService.class);

		formValidateOperation.setSystemParameterService(systemParameterService);
		formValidateOperation.setMessageResourceService(messageResourceService);
		formValidateOperation.setRetreiveChargeDetailsService(retreiveChargeDetailsService);
		formValidateOperation.setFxRateService(fxRateService);
		formValidateOperation.setCreditCardDetailsService(creditCardDetailsService);
		formValidateOperation.setTransactionLimitService(transactionLimitService);
		formValidateOperation.setListValueResService(listValueResService);
	}

//	if TransactionLimitService fails
	@Test
	public void validateFormWhenTransactionLimitServiceFails()
	{
		FormValidateOperationRequest request = makeRequest();

		TransactionLimitServiceResponse transactionLimitServiceResponse = new TransactionLimitServiceResponse();
		transactionLimitServiceResponse.setSuccess(false);

		expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).atLeastOnce();
		expect(retreiveChargeDetailsService.retreiveChargeDetails(isA(RetreiveChargeDetailsServiceRequest.class))).andReturn(getRetreiveChargeDetailsServiceResponseStub()).anyTimes();
		expect(fxRateService.retreiveFxRate(isA(FxRateServiceRequest.class))).andReturn(getFxRateServiceResponseStub()).anyTimes();
		expect(creditCardDetailsService.retrieveCreditCardAccountDetails(isA(CreditCardAccountDetailsServiceRequest.class))).andReturn(getCreditCardAccountDetailsServiceResponseStub()).anyTimes();
		expect(transactionLimitService.checkTransactionLimit(isA(TransactionLimitServiceRequest.class))).andReturn(transactionLimitServiceResponse).anyTimes();
		expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).atLeastOnce();

		replay(systemParameterService, retreiveChargeDetailsService, fxRateService, creditCardDetailsService, transactionLimitService, messageResourceService);

		FormValidateOperationResponse response = new FormValidateOperationResponse();
		try
		{
			response = formValidateOperation.validateForm(request);
		}
		catch(RuntimeException e)
		{
			
		}
		assertNotNull(transactionLimitServiceResponse);
		assertNotNull(transactionLimitServiceResponse.isSuccess());
		assertFalse(transactionLimitServiceResponse.isSuccess());
		assertEquals(false, transactionLimitServiceResponse.isSuccess());

		assertNotNull(response);
		assertNotNull(response.isSuccess());
		assertFalse(response.isSuccess());
		assertEquals(false, response.isSuccess());

		verify(systemParameterService, retreiveChargeDetailsService, fxRateService, creditCardDetailsService, transactionLimitService, messageResourceService);
	}

//	if CreditCardDetailsService fails
	@Test
	public void validateFormWhenCreditCardDetailsServiceFails()
	{
		FormValidateOperationRequest request = makeRequest();

		CreditCardAccountDetailsServiceResponse creditCardAccountDetailsServiceResponse = null;//new CreditCardAccountDetailsServiceResponse();
//		creditCardAccountDetailsServiceResponse.setSuccess(false);

		expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).atLeastOnce();
		expect(retreiveChargeDetailsService.retreiveChargeDetails(isA(RetreiveChargeDetailsServiceRequest.class))).andReturn(getRetreiveChargeDetailsServiceResponseStub()).anyTimes();
		expect(fxRateService.retreiveFxRate(isA(FxRateServiceRequest.class))).andReturn(getFxRateServiceResponseStub()).anyTimes();
		expect(creditCardDetailsService.retrieveCreditCardAccountDetails(isA(CreditCardAccountDetailsServiceRequest.class))).andReturn(creditCardAccountDetailsServiceResponse).atLeastOnce();
		expect(transactionLimitService.checkTransactionLimit(isA(TransactionLimitServiceRequest.class))).andReturn(getTransactionLimitServiceResponseStub()).anyTimes();
		expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).atLeastOnce();

		replay(systemParameterService, retreiveChargeDetailsService, fxRateService, creditCardDetailsService, messageResourceService);

		FormValidateOperationResponse response = new FormValidateOperationResponse();
		try
		{
			response = formValidateOperation.validateForm(request);
		}
		catch(RuntimeException e)
		{
			
		}
		assertNull(creditCardAccountDetailsServiceResponse);

		assertNotNull(response);
		assertNotNull(response.isSuccess());
		assertFalse(response.isSuccess());
		assertEquals(false, response.isSuccess());

		verify(systemParameterService, retreiveChargeDetailsService, fxRateService, creditCardDetailsService, messageResourceService);
	}

//	CreditCardDetailsService will not be called if txnAmt less than Zero
	@Test
	public void validateFormWhenTxnAmtLessThanZeroFails()
	{
		FormValidateOperationRequest request = makeRequest();
		request.setTxnAmt(new Amount("RND", new BigDecimal(-1)));

		expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).atLeastOnce();
		expect(retreiveChargeDetailsService.retreiveChargeDetails(isA(RetreiveChargeDetailsServiceRequest.class))).andReturn(getRetreiveChargeDetailsServiceResponseStub()).anyTimes();
		expect(fxRateService.retreiveFxRate(isA(FxRateServiceRequest.class))).andReturn(getFxRateServiceResponseStub()).anyTimes();
		expect(transactionLimitService.checkTransactionLimit(isA(TransactionLimitServiceRequest.class))).andReturn(getTransactionLimitServiceResponseStub()).anyTimes();
		expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).atLeastOnce();

		replay(systemParameterService, retreiveChargeDetailsService, fxRateService, messageResourceService);

		FormValidateOperationResponse response = new FormValidateOperationResponse();
		try
		{
			response = formValidateOperation.validateForm(request);
		}
		catch(RuntimeException e)
		{
			
		}
		assertNotNull(response);
		assertNotNull(response.isSuccess());
		assertFalse(response.isSuccess());
		assertEquals(false, response.isSuccess());

		verify(systemParameterService, retreiveChargeDetailsService, fxRateService, messageResourceService);
	}

//	if validate true
	@Test
	public void validateFormTrue()
	{
		FormValidateOperationRequest request = makeRequest();

		FxRateServiceResponse fxRateServiceResponse = getFxRateServiceResponseStub();
		RetreiveChargeDetailsServiceResponse retreiveChargeDetailsServiceResponse = getRetreiveChargeDetailsServiceResponseStub();
		CreditCardAccountDetailsServiceResponse creditCardAccountDetailsServiceResponse = getCreditCardAccountDetailsServiceResponseStub();
		TransactionLimitServiceResponse transactionLimitServiceResponse = getTransactionLimitServiceResponseStub();

		expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).atLeastOnce();
		expect(retreiveChargeDetailsService.retreiveChargeDetails(isA(RetreiveChargeDetailsServiceRequest.class))).andReturn(retreiveChargeDetailsServiceResponse).atLeastOnce();
		expect(fxRateService.retreiveFxRate(isA(FxRateServiceRequest.class))).andReturn(fxRateServiceResponse).atLeastOnce();
		expect(creditCardDetailsService.retrieveCreditCardAccountDetails(isA(CreditCardAccountDetailsServiceRequest.class))).andReturn(creditCardAccountDetailsServiceResponse).atLeastOnce();
		expect(transactionLimitService.checkTransactionLimit(isA(TransactionLimitServiceRequest.class))).andReturn(transactionLimitServiceResponse).atLeastOnce();

		replay(systemParameterService, retreiveChargeDetailsService, fxRateService, creditCardDetailsService, transactionLimitService);

		FormValidateOperationResponse response = new FormValidateOperationResponse();
		try
		{
			response = formValidateOperation.validateForm(request);
		}
		catch(RuntimeException e)
		{
			
		}
		assertNotNull(fxRateServiceResponse);
		assertNotNull(fxRateServiceResponse.getEffFxRate());
		assertNotNull(fxRateServiceResponse.getEqAmt());

		assertNotNull(retreiveChargeDetailsServiceResponse);
		assertNotNull(retreiveChargeDetailsServiceResponse.getCharges());
		assertNotNull(retreiveChargeDetailsServiceResponse.getTotalFeeAmount());

		assertNotNull(creditCardAccountDetailsServiceResponse);
		assertNotNull(creditCardAccountDetailsServiceResponse.getCreditCardAccountDTO());
		assertNotNull(creditCardAccountDetailsServiceResponse.getCreditCardAccountDTO().getAvailableBalance());

		assertNotNull(transactionLimitServiceResponse);
		assertNotNull(transactionLimitServiceResponse.isSuccess());
		assertTrue(transactionLimitServiceResponse.isSuccess());
		assertEquals(true, transactionLimitServiceResponse.isSuccess());
		assertNotNull(transactionLimitServiceResponse.isAuthRequired());

		assertNotNull(response);
		assertNotNull(response.isSuccess());
		assertTrue(response.isSuccess());
		assertEquals(true, response.isSuccess());
		assertEquals(response.getTxnAmt(), request.getTxnAmt());

		verify(systemParameterService, retreiveChargeDetailsService, fxRateService, creditCardDetailsService, transactionLimitService);
	}

	@Test
	public void currencyValidation()
	{
		ListValueResByGroupServiceResponse listValueResByGroupServiceResponse = new ListValueResByGroupServiceResponse();

		expect(listValueResService.getListValueByGroup(isA(ListValueResServiceRequest.class))).andReturn(listValueResByGroupServiceResponse).atLeastOnce();
		expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).atLeastOnce();

		replay(listValueResService, messageResourceService);

		FormValidateOperationResponse formValidateOperationResponse = new FormValidateOperationResponse();
		formValidateOperationResponse.setContext(getContextMock());
//		formValidateOperationResponse.setSuccess(true);

		formValidateOperation.currencyValidation(getContextMock(), "DESTCurr", FundTransferConstants.LIST_VAL_CURR_SUPPORT_OWN_ACC,
				"SRCCurr", "DESTCurr", formValidateOperationResponse);

		assertNotNull(formValidateOperationResponse);
		assertNotNull(formValidateOperationResponse.isSuccess());
		assertFalse(formValidateOperationResponse.isSuccess());
		assertEquals(FundTransferResponseCodeConstants.PMT_FT_INVALID_CURRENCY_TRANSACTION, formValidateOperationResponse.getResCde());

		verify(listValueResService, messageResourceService);
	}

	private TransactionLimitServiceResponse getTransactionLimitServiceResponseStub()
	{
		TransactionLimitServiceResponse response = new TransactionLimitServiceResponse();
		response.setAuthRequired(false);
		response.setSuccess(true);
		return response;
	}

	private CreditCardAccountDetailsServiceResponse getCreditCardAccountDetailsServiceResponseStub()
	{
		CreditCardAccountDetailsServiceResponse response = new CreditCardAccountDetailsServiceResponse();
		CreditCardAccountDTO dto = new CreditCardAccountDTO();
		dto.setAvailableBalance(new BigDecimal(100));
		response.setCreditCardAccountDTO(dto);
		return response;
	}

	private FormValidateOperationRequest makeRequest()
	{
		FormValidateOperationRequest request = new FormValidateOperationRequest();
		request.setContext(getContextMock());
		request.setTxnAmt(new Amount("RND", new BigDecimal(10)));
		CreditCardAccountDTO fromAcct = new CreditCardAccountDTO();
		fromAcct.setAccountNumber("1");
		fromAcct.setCurrency("MUR");
		fromAcct.setBranchCode("123456");
		fromAcct.setProductCode("12345");
		request.setFrmAct(fromAcct);
		return request;
	}

	public SystemParameterListServiceResponse getSystemParameterListServiceResponseMock()
	{
		SystemParameterListServiceResponse systemParameterListServiceResponse=new SystemParameterListServiceResponse();
		List<SystemParameterDTO> sysParamDtoList=new ArrayList<SystemParameterDTO>();
		sysParamDtoList.add(getSystemParameterDTOMock());
		SystemParameterDTO systemParameterDTO = new SystemParameterDTO();
		systemParameterDTO.setActivityId("COMMON");
		systemParameterDTO.setParameterId("chargeDetailTaskCode");
		systemParameterDTO.setParameterValue("taskCode");
		systemParameterDTO.setBusinessId("TZBRB");
		systemParameterDTO.setSystemId("UB");
		sysParamDtoList.add(systemParameterDTO);
		systemParameterListServiceResponse.setSystemParameterDTOList(sysParamDtoList);
		systemParameterListServiceResponse.setSuccess(true);
		return systemParameterListServiceResponse;
	}

	private FxRateServiceResponse getFxRateServiceResponseStub()
	{
		FxRateServiceResponse response = new FxRateServiceResponse();
		response.setEffFxRate(new BigDecimal(1));
		response.setEqAmt(new BigDecimal(1));
		return response;
	}
	private RetreiveChargeDetailsServiceResponse getRetreiveChargeDetailsServiceResponseStub()
	{
		RetreiveChargeDetailsServiceResponse res = new RetreiveChargeDetailsServiceResponse();
		res.setTotalFeeAmount(new Amount("MUR", new BigDecimal(1)));
		res.setCharges(new ArrayList<Charge>());
		return res;
	}

	@After
	public void tearDown()
	{
		formValidateOperation = null;
		messageResourceService = null;
		systemParameterService = null;
		retreiveChargeDetailsService = null;
		fxRateService = null;
		creditCardDetailsService = null;
		transactionLimitService = null;
		listValueResService = null;
	}
}