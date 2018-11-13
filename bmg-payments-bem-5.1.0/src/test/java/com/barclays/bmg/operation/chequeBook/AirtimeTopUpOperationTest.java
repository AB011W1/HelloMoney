/*package com.barclays.bmg.operation.chequeBook;


import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.taskdefs.optional.junit.BaseTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.barclays.bmg.airTimeTopUp.response.AirTimeTopUpOperationResponse;
import com.barclays.bmg.airTimeTopUp.response.AirTimeTopUpValidateResponse;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.SystemParameterDTO;
import com.barclays.bmg.mvc.operation.lookup.BranchLookUpOperation;
import com.barclays.bmg.operation.accountservices.RetrieveAccountListOperation;
import com.barclays.bmg.operation.beneficiary.RetrievePayeeListOperation;
import com.barclays.bmg.service.BillerService;
import com.barclays.bmg.service.MessageResourceService;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.accountdetails.response.CASADetailsServiceResponse;
import com.barclays.bmg.service.accounts.AllAccountService;
import com.barclays.bmg.service.lookup.BranchLookUpService;
import com.barclays.bmg.service.product.ListValueResService;
import com.barclays.bmg.service.product.ProductEligibilityService;
import com.barclays.bmg.service.request.BillerServiceRequest;
import com.barclays.bmg.service.request.MessageResourceServiceRequest;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.BillerServiceResponse;
import com.barclays.bmg.service.response.MessageResourceServiceResponse;
import com.barclays.bmg.service.response.RetreiveBeneficiaryDetailsServiceResponse;
import com.barclays.bmg.service.response.SystemParameterListServiceResponse;
import com.barclays.bmg.service.response.SystemParameterServiceResponse;


public class AirtimeTopUpOperationTest extends BaseTest {

	private SystemParameterService systemParameterService;
	private MessageResourceService messageResourceService;
	private AirTimeTopUpOperationService airTimeTopUpOperationService;
	private ListValueResService listValueResService;
	private BranchLookUpService branchLookUpService;
	private AllAccountService allAccountService;
	private ProductEligibilityService productEligibilityService;
	private BillerService billerService;


	private RetrievePayeeListOperation retrievePayeeListOperation;//This operation is not used in Air time top service

	private RetrieveAccountListOperation retrieveAccountListOperation;//Test cases for this operation are already covered in other
	private BranchLookUpOperation branchLookUpOperation;

 	private AirTimeTopUpValidate airTimeTopUpValidate;



	  @Before
	  public void setUp() throws Exception {
		  retrievePayeeListOperation   = new RetrievePayeeListOperation();
		  retrieveAccountListOperation = new RetrieveAccountListOperation();
		  branchLookUpOperation        = new BranchLookUpOperation();
		  airTimeTopUpOperationService = new AirTimeTopUpOperationService();//createMock(AirTimeTopUpOperationService.class);
		  airTimeTopUpValidate = new AirTimeTopUpValidate();//createMock(AirTimeTopUpOperationService.class);

		  listValueResService          = createMock(ListValueResService.class);
		  branchLookUpService          = createMock(BranchLookUpService.class);
		  allAccountService            = createMock(AllAccountService.class);
		  productEligibilityService    = createMock(ProductEligibilityService.class);
		  billerService                = createMock(BillerService.class);

		  retrievePayeeListOperation.setListValueResService(listValueResService);
		  branchLookUpOperation.setBranchLookUpService(branchLookUpService);
		  retrieveAccountListOperation.setAllAccountService(allAccountService);
		  retrieveAccountListOperation.setProductEligibilityService(productEligibilityService);
		  airTimeTopUpOperationService.setBillerService(billerService);
		  airTimeTopUpValidate.setBillerService(billerService);
		  airTimeTopUpValidate.setAirTimeTopUpOperationService(airTimeTopUpOperationService);

 		  systemParameterService = createNiceMock(SystemParameterService.class);
 		  airTimeTopUpOperationService.setSystemParameterService(systemParameterService);
 		  airTimeTopUpValidate.setSystemParameterService(systemParameterService);

		  messageResourceService=createNiceMock(MessageResourceService.class);
		  airTimeTopUpOperationService.setMessageResourceService(messageResourceService);
		  airTimeTopUpValidate.setMessageResourceService(messageResourceService);


	  }

	  @Test
	  public void testValidateControllerOperation() {

		  AirTimeTopUpOperationResponse response1 = new AirTimeTopUpOperationResponse();
		  response1.setSuccess(true);

		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		  expect(billerService.getBillPaymentsBillerList(isA(BillerServiceRequest.class))).andReturn(getBillerServiceResponseMock()).anyTimes();
		  expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).anyTimes();

		  replay(billerService,systemParameterService,messageResourceService);

			AirTimeTopUpValidateRequest request = new AirTimeTopUpValidateRequest();
			request.setContext(getContextMock());
			setReqParama(request);
			AirTimeTopUpValidateResponse response= new AirTimeTopUpValidateResponse();

		  try {
 				  response = airTimeTopUpValidate.validateRequest(getContextMock(), request, response);

		      } catch (RuntimeException e) {
		    	  
		      }

		assertNotNull(response);
		assertEquals(true,response.isSuccess());
		assertNotNull(response.getBillerDTO());
		verify(billerService,systemParameterService,messageResourceService);
	  }

	  @Test
	  public void testValidateControllerOperationForErro() {

		  AirTimeTopUpOperationResponse response1 = new AirTimeTopUpOperationResponse();
		  response1.setSuccess(true);

		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		  expect(billerService.getBillPaymentsBillerList(isA(BillerServiceRequest.class))).andReturn(getBillerServiceResponseMockForError()).anyTimes();
		  expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).anyTimes();

		  replay(billerService,systemParameterService,messageResourceService);

			AirTimeTopUpValidateRequest request = new AirTimeTopUpValidateRequest();
			request.setContext(getContextMock());
			setReqParama(request);
			AirTimeTopUpValidateResponse response= new AirTimeTopUpValidateResponse();

		  try {
 				  response = airTimeTopUpValidate.validateRequest(getContextMock(), request, response);

		      } catch (RuntimeException e) {
		    	  
		      }

        assertNotNull(response);
		assertEquals(false,response.isSuccess());
		assertEquals(null,response.getBillerDTO());
		verify(billerService,systemParameterService,messageResourceService);
	  }


	  private void setReqParama(AirTimeTopUpValidateRequest request) {
		request.setActNo("01890");
		request.setPrvdAcctNmbr("12311");
		request.setAmt("901");



	}

	@Test
	  public void testInitControllerOperation() {

		  AirTimeTopUpOperationResponse response1 = new AirTimeTopUpOperationResponse();
		  response1.setSuccess(true);

		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		  expect(billerService.getBillPaymentsBillerList(isA(BillerServiceRequest.class))).andReturn(getBillerServiceResponseMock()).anyTimes();
		  expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).anyTimes();

		  replay(billerService,systemParameterService,messageResourceService);

    	  AirTimeTopUpOperationRequest airTimeTopUpOperationRequest = new AirTimeTopUpOperationRequest();
    	  airTimeTopUpOperationRequest.setContext(getContextMock());
		  AirTimeTopUpOperationResponse response = new AirTimeTopUpOperationResponse();

		  try {
				response = airTimeTopUpOperationService.getBillPaymentsBillerList(airTimeTopUpOperationRequest);
		      } catch (RuntimeException e) {
		    	  
		      }

		assertNotNull(response);
		assertEquals(true,response.isSuccess());
		assertNotNull(response.getBillerServiceResponse().getBillerList().get(0));
		verify(billerService,systemParameterService,messageResourceService);
	  }

	  @Test
	  public void testInitControllerOperationForError() {

		  AirTimeTopUpOperationResponse response1 = new AirTimeTopUpOperationResponse();
		  response1.setSuccess(true);

		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		  expect(billerService.getBillPaymentsBillerList(isA(BillerServiceRequest.class))).andReturn(getBillerServiceResponseMockForError()).anyTimes();
		  expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).anyTimes();

		  replay(billerService,systemParameterService,messageResourceService);

    	  AirTimeTopUpOperationRequest airTimeTopUpOperationRequest = new AirTimeTopUpOperationRequest();
    	  airTimeTopUpOperationRequest.setContext(getContextMock());
		  AirTimeTopUpOperationResponse response = new AirTimeTopUpOperationResponse();

		  try {
				response = airTimeTopUpOperationService.getBillPaymentsBillerList(airTimeTopUpOperationRequest);
		      } catch (RuntimeException e) {
		    	  
		      }

		assertNotNull(response);
		assertEquals(false,response.isSuccess());
		assertEquals(null,response.getBillerServiceResponse());
		verify(billerService,systemParameterService,messageResourceService);
	  }


	 @After
	 public void tearDown(){

			systemParameterService = null;
			messageResourceService=null;
	 }

	  protected BillerServiceResponse getBillerServiceResponseMock(){
		  BillerServiceResponse response =new BillerServiceResponse();
	    	response.setSuccess(true);
	    	BillerDTO billerDTO = new BillerDTO();
	    	billerDTO.setBillerAccountNumber("12311");
	    	billerDTO.setBillerId("420");
	    	billerDTO.setBillerName("ganganum");
			response.setBillerDTO(billerDTO);

			List<BillerDTO> billerList = new ArrayList<BillerDTO>();
	    	billerList.add(billerDTO);

			response.setBillerList(billerList);

	    	return response;
	  }
	  protected BillerServiceResponse getBillerServiceResponseMockForError(){
		  BillerServiceResponse response =new BillerServiceResponse();
	    	response.setSuccess(false);
	    	BillerDTO billerDTO = new BillerDTO();
	    	billerDTO.setBillerAccountNumber(null);
	    	billerDTO.setBillerId(null);
	    	billerDTO.setBillerName(null);
			response.setBillerDTO(null);
            List<BillerDTO> billerList = new ArrayList<BillerDTO>();

            billerList.add(billerDTO);
			response.setBillerList(billerList);

	    	return response;
	  }

	  protected RetreiveBeneficiaryDetailsServiceResponse getRetreiveBeneDetServiceResMockForWrongBenfId(){
	    	RetreiveBeneficiaryDetailsServiceResponse retreiveBeneficiaryDetailsServiceResponse =new RetreiveBeneficiaryDetailsServiceResponse();
	    	retreiveBeneficiaryDetailsServiceResponse.setSuccess(false);
	    	retreiveBeneficiaryDetailsServiceResponse.setBeneficiaryDTO(null);
	    	return retreiveBeneficiaryDetailsServiceResponse;
	  }
	  public static Context getContextMock(){

			Context context = new Context();
			//Map<String, String> map = new HashMap<String, String>();
			//map.put(ProductProcessorTypeCode._BK, "0001000000000000000782");
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

		  protected BeneficiaryDTO getBeneficiaryDTOMock(){
		    	BeneficiaryDTO beneficiaryDTO=new BeneficiaryDTO();
		    	beneficiaryDTO.setBeneficiaryName("BENF ABC");
		    	beneficiaryDTO.setPayeeId("BENF_0000ABC");
				return beneficiaryDTO;
		  }

		  protected SystemParameterDTO getSystemParameterDTOMock(){
				SystemParameterDTO systemParameterDTO = new SystemParameterDTO();
				systemParameterDTO.setActivityId("COMMON");
				systemParameterDTO.setParameterId("BeneficiaryManagement_EndPoint");
				systemParameterDTO.setParameterValue("http://mb4t.wload.global:7080/bem/bxx5_gateway");
				systemParameterDTO.setBusinessId("TZBRB");
				systemParameterDTO.setSystemId("UB");
				return systemParameterDTO;
		  }

		  protected SystemParameterListServiceResponse getSystemParameterListServiceResponseMock(){
				 SystemParameterListServiceResponse systemParameterListServiceResponse=new SystemParameterListServiceResponse();
				 List<SystemParameterDTO> sysParamDtoList=new ArrayList<SystemParameterDTO>();
				 sysParamDtoList.add(getSystemParameterDTOMock());
				 systemParameterListServiceResponse.setSystemParameterDTOList(sysParamDtoList);
				 systemParameterListServiceResponse.setSuccess(true);
				 return systemParameterListServiceResponse;
		  }

		  protected CASADetailsServiceResponse getCASADetailsServiceResponseMock(){
			  		CASADetailsServiceResponse casaDetailsServiceResponse =new CASADetailsServiceResponse();
			  		CASAAccountDTO acct=new CASAAccountDTO();
			  		acct.setAccountNumber("111111111111");
			  		casaDetailsServiceResponse.setCasaAccountDTO(acct);
				 return casaDetailsServiceResponse;
		  }

		  protected CASADetailsServiceResponse getCASADetailsServiceResponseEmptyMock(){
		  		CASADetailsServiceResponse casaDetailsServiceResponse =new CASADetailsServiceResponse();
		  		CASAAccountDTO acct=new CASAAccountDTO();
		  		casaDetailsServiceResponse.setCasaAccountDTO(acct);
			 return casaDetailsServiceResponse;
	}

		  protected SystemParameterServiceResponse getSysParameServiceRespSecAuthTypeMock(){
			    SystemParameterServiceResponse systemParameterServiceResponse=new SystemParameterServiceResponse();
				SystemParameterDTO systemParameterDTO = new SystemParameterDTO();
				systemParameterDTO.setActivityId("COMMON");
				systemParameterDTO.setParameterId(SystemParameterConstant.SECOND_AUTH_TYPE);
				systemParameterDTO.setParameterValue("OTP");
				systemParameterServiceResponse.setSystemParameterDTO(systemParameterDTO);
				 return systemParameterServiceResponse;
		  }

		  protected MessageResourceServiceResponse getMessageResourceServiceResponseMock(){
			  MessageResourceServiceResponse messageResourceServiceResponse=new MessageResourceServiceResponse();
			  messageResourceServiceResponse.setMessageDesc("Mssg Descr");
			  messageResourceServiceResponse.setErrTyp("errTyp");
			  return messageResourceServiceResponse;
		  }



}
*/