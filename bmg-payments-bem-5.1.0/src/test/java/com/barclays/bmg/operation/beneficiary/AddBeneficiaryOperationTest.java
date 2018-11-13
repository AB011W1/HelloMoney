package com.barclays.bmg.operation.beneficiary;

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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.barclays.bmg.constants.BeneficiaryResponseCodeConstants;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.BranchLookUpDTO;
import com.barclays.bmg.operation.BaseClass;
import com.barclays.bmg.operation.request.fundtransfer.external.AddBeneficiaryOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.AddBeneficiaryFormSubmissionOperationResponse;
import com.barclays.bmg.operation.response.fundtransfer.external.AddBeneficiaryOperationResponse;
import com.barclays.bmg.service.AddBeneficiaryService;
import com.barclays.bmg.service.MessageResourceService;
import com.barclays.bmg.service.RetreivePayeeListService;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.accountdetails.CASADetailsService;
import com.barclays.bmg.service.accountdetails.request.CASADetailsServiceRequest;
import com.barclays.bmg.service.lookup.BranchLookUpService;
import com.barclays.bmg.service.product.ListValueResService;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;
import com.barclays.bmg.service.request.AddBeneficiaryServiceRequest;
import com.barclays.bmg.service.request.MessageResourceServiceRequest;
import com.barclays.bmg.service.request.RetreivePayeeListServiceRequest;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.request.lookup.BranchLookUpServiceRequest;
import com.barclays.bmg.service.response.AddBeneficiaryServiceResponse;
import com.barclays.bmg.service.response.RetreivePayeeListServiceResponse;
import com.barclays.bmg.service.response.lookup.BranchLookUpServiceResponse;


public class AddBeneficiaryOperationTest extends BaseClass {

	private AddBeneficiaryOperation addBeneficiaryOperation;
	private AddBeneficiaryService addBeneficiaryService;
	private SystemParameterService systemParameterService;
	private MessageResourceService messageResourceService;
	private CASADetailsService casaDetailsService;
	private BranchLookUpService branchLookUpService;
	private RetreivePayeeListService retreivePayeeListService;
	private ListValueResService listValueResService;


	  @Before
	  public void setUp() throws Exception {
		  addBeneficiaryOperation=new AddBeneficiaryOperation();
		  addBeneficiaryService = createMock(AddBeneficiaryService.class);
		  addBeneficiaryOperation.setAddBeneficiaryService(addBeneficiaryService);
		  systemParameterService = createNiceMock(SystemParameterService.class);
		  addBeneficiaryOperation.setSystemParameterService(systemParameterService);
		  messageResourceService=createNiceMock(MessageResourceService.class);
		  addBeneficiaryOperation.setMessageResourceService(messageResourceService);
		  casaDetailsService=createNiceMock(CASADetailsService.class);
		  addBeneficiaryOperation.setCasaDetailsService(casaDetailsService);
		  branchLookUpService=createNiceMock(BranchLookUpService.class);
		  addBeneficiaryOperation.setBranchLookUpService(branchLookUpService);
		  retreivePayeeListService=createNiceMock(RetreivePayeeListService.class);
		  addBeneficiaryOperation.setRetreivePayeeListService(retreivePayeeListService);
		  listValueResService=createNiceMock(ListValueResService.class);
		  addBeneficiaryOperation.setListValueResService(listValueResService);
	  }

	  @Test
	  public void testAddBeneficiary() {

		  AddBeneficiaryServiceResponse addBeneficiaryServiceResponse = new AddBeneficiaryServiceResponse();
		  addBeneficiaryServiceResponse.setSuccess(true);
		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		  expect(systemParameterService.getSystemParameter(isA(SystemParameterServiceRequest.class))).andReturn(getSysParameServiceRespSecAuthTypeMock()).anyTimes();
		  expect(addBeneficiaryService.addBeneficiary(isA(AddBeneficiaryServiceRequest.class))).andReturn(addBeneficiaryServiceResponse).anyTimes();
		  replay(systemParameterService,addBeneficiaryService);

		  AddBeneficiaryOperationRequest request=new AddBeneficiaryOperationRequest();
		  request.setContext(getContextMock());
		  request.setBeneficiaryDTO(getBeneficiaryDTOMock());
		  request.setScndLvlauthReq(true);
		  AddBeneficiaryOperationResponse response = new AddBeneficiaryOperationResponse();
		    try {
		    	response=addBeneficiaryOperation.addBeneficiary(request);

		      } catch (RuntimeException e) {
		    	  
		      }

		assertNotNull(response);
		//assertEquals(true,response.isScndLvlAuthReq());
		verify(systemParameterService,addBeneficiaryService);

	  }


	  @Test
	  public void testAddBeneficiaryAfterSecLvlAuthDone() {

		  AddBeneficiaryServiceResponse addBeneficiaryServiceResponse = new AddBeneficiaryServiceResponse();
		  addBeneficiaryServiceResponse.setSuccess(true);
		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		  expect(systemParameterService.getSystemParameter(isA(SystemParameterServiceRequest.class))).andReturn(getSysParameServiceRespSecAuthTypeMock()).anyTimes();
		  expect(listValueResService.getListValueByGroup(isA(ListValueResServiceRequest.class))).andReturn(getListValueResByGroupServiceResponseEmptyMock()).anyTimes();
		  expect(addBeneficiaryService.addBeneficiary(isA(AddBeneficiaryServiceRequest.class))).andReturn(addBeneficiaryServiceResponse).atLeastOnce();
		  replay(systemParameterService,listValueResService,addBeneficiaryService);

		  AddBeneficiaryOperationRequest request=new AddBeneficiaryOperationRequest();
		  request.setContext(getContextMock());
		  request.setBeneficiaryDTO(getBeneficiaryDTOMock());
		  request.setScndLvlauthReq(false);
		  AddBeneficiaryOperationResponse response = new AddBeneficiaryOperationResponse();
		    try {
		    	response=addBeneficiaryOperation.addBeneficiary(request);

		      } catch (RuntimeException e) {
		    	  
		      }

		assertNotNull(response);
		assertNotNull(response.getBeneficiaryDTO());

		verify(systemParameterService,listValueResService,addBeneficiaryService);

	  }

	  @Test
	  public void testValidateFormForInvalidIACAcctNumber() {

		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		  expect(casaDetailsService.retrieveCASADetails(isA(CASADetailsServiceRequest.class))).andReturn(getCASADetailsServiceResponseEmptyMock()).anyTimes();
		  expect(branchLookUpService.getAllBranches(isA(BranchLookUpServiceRequest.class))).andReturn(getBranchLookUpServiceResponseMock()).anyTimes();
		  expect(retreivePayeeListService.retreivePayeeList(isA(RetreivePayeeListServiceRequest.class))).andReturn(getRetreivePayeeListServiceResponseDACMock()).anyTimes();
		  expect(listValueResService.getListValueByGroup(isA(ListValueResServiceRequest.class))).andReturn(getListValueResByGroupServiceResponseEmptyMock()).anyTimes();

		  replay(systemParameterService,listValueResService,casaDetailsService,branchLookUpService,retreivePayeeListService);

		  AddBeneficiaryOperationRequest request=new AddBeneficiaryOperationRequest();
		  request.setContext(getContextMock());
		  request.setPayeeType("IAC");
		  request.setBranchCode("014");
		  request.setAccountNumber("123456");

		  AddBeneficiaryFormSubmissionOperationResponse response=new AddBeneficiaryFormSubmissionOperationResponse();
		    try {
		    	response=addBeneficiaryOperation.validateForm(request);

		      } catch (RuntimeException e) {
		    	  
		      }

		assertNotNull(response);
		assertEquals(false,response.isSuccess());
		assertEquals(BeneficiaryResponseCodeConstants.INVALID_BENEFICIARY,response.getResCde());
		verify(systemParameterService,listValueResService,casaDetailsService,branchLookUpService,retreivePayeeListService);
	  }

	  @Test
	  public void testValidateFormForNoPayeeReg() {

		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		  expect(casaDetailsService.retrieveCASADetails(isA(CASADetailsServiceRequest.class))).andReturn(getCASADetailsServiceResponseMock()).anyTimes();
		  expect(branchLookUpService.getAllBranches(isA(BranchLookUpServiceRequest.class))).andReturn(getBranchLookUpServiceResponseMock()).anyTimes();
		  expect(retreivePayeeListService.retreivePayeeList(isA(RetreivePayeeListServiceRequest.class))).andReturn(getRetreivePayeeListServiceResponseEmptyMock()).anyTimes();
		  expect(listValueResService.getListValueByGroup(isA(ListValueResServiceRequest.class))).andReturn(getListValueResByGroupServiceResponseEmptyMock()).anyTimes();

		  replay(systemParameterService,listValueResService,casaDetailsService,branchLookUpService,retreivePayeeListService);

		  AddBeneficiaryOperationRequest request=new AddBeneficiaryOperationRequest();
		  request.setContext(getContextMock());
		  request.setPayeeType("IAC");
		  request.setBranchCode("014");
		  request.setAccountNumber("123456");

		  AddBeneficiaryFormSubmissionOperationResponse response=new AddBeneficiaryFormSubmissionOperationResponse();
		    try {
		    	response=addBeneficiaryOperation.validateForm(request);

		      } catch (RuntimeException e) {
		    	  
		      }

		assertNotNull(response);
		assertEquals(true,response.isSuccess());
		assertEquals(BeneficiaryResponseCodeConstants.NO_PAYEES_REGISTERED,response.getResCde());
		verify(systemParameterService,listValueResService,casaDetailsService,branchLookUpService,retreivePayeeListService);
	  }


	  @Test
	  public void testValidateFormIACAlreadyRegistered() {

		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		  expect(casaDetailsService.retrieveCASADetails(isA(CASADetailsServiceRequest.class))).andReturn(getCASADetailsServiceResponseMock()).anyTimes();
		  expect(branchLookUpService.getAllBranches(isA(BranchLookUpServiceRequest.class))).andReturn(getBranchLookUpServiceResponseMock()).anyTimes();
		  expect(retreivePayeeListService.retreivePayeeList(isA(RetreivePayeeListServiceRequest.class))).andReturn(getRetreivePayeeListServiceResponseIACMock()).anyTimes();
		  expect(listValueResService.getListValueByGroup(isA(ListValueResServiceRequest.class))).andReturn(getListValueResByGroupServiceResponseEmptyMock()).anyTimes();

		  replay(systemParameterService,listValueResService,casaDetailsService,branchLookUpService,retreivePayeeListService);

		  AddBeneficiaryOperationRequest request=new AddBeneficiaryOperationRequest();
		  request.setContext(getContextMock());
		  request.setPayeeType("IAC");
		  request.setBranchCode("014");
		  request.setAccountNumber("111111111111");

		  AddBeneficiaryFormSubmissionOperationResponse response=new AddBeneficiaryFormSubmissionOperationResponse();
		    try {
		    	response=addBeneficiaryOperation.validateForm(request);

		      } catch (RuntimeException e) {
		    	  
		      }

		assertNotNull(response);
		assertEquals(false,response.isSuccess());
		assertEquals(BeneficiaryResponseCodeConstants.PAYEE_ALREADY_REGISTERED,response.getResCde());
		verify(systemParameterService,listValueResService,casaDetailsService,branchLookUpService,retreivePayeeListService);
	  }

	  @Test
	  public void testValidateFormDACAlreadyRegistered() {

		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		  expect(casaDetailsService.retrieveCASADetails(isA(CASADetailsServiceRequest.class))).andReturn(getCASADetailsServiceResponseMock()).anyTimes();
		  expect(branchLookUpService.getAllBranches(isA(BranchLookUpServiceRequest.class))).andReturn(getBranchLookUpServiceResponseMock()).anyTimes();
		  expect(retreivePayeeListService.retreivePayeeList(isA(RetreivePayeeListServiceRequest.class))).andReturn(getRetreivePayeeListServiceResponseDACMock()).anyTimes();
		  expect(listValueResService.getListValueByGroup(isA(ListValueResServiceRequest.class))).andReturn(getListValueResByGroupServiceResponseEmptyMock()).anyTimes();

		  replay(systemParameterService,listValueResService,casaDetailsService,branchLookUpService,retreivePayeeListService);

		  AddBeneficiaryOperationRequest request=new AddBeneficiaryOperationRequest();
		  request.setContext(getContextMock());
		  request.setPayeeType("DAC");
		  request.setBankCode("020");
		  request.setBranchCode("014");
		  request.setAccountNumber("123456");

		  AddBeneficiaryFormSubmissionOperationResponse response=new AddBeneficiaryFormSubmissionOperationResponse();
		    try {
		    	response=addBeneficiaryOperation.validateForm(request);

		      } catch (RuntimeException e) {
		    	  
		      }

		assertNotNull(response);
		assertEquals(false,response.isSuccess());
		assertEquals(BeneficiaryResponseCodeConstants.PAYEE_ALREADY_REGISTERED,response.getResCde());
		verify(systemParameterService,listValueResService,casaDetailsService,branchLookUpService,retreivePayeeListService);
	  }

	  @Test
	  public void testAddBeneficiaryFail() {

		  AddBeneficiaryServiceResponse addBeneficiaryServiceResponse = new AddBeneficiaryServiceResponse();
		  addBeneficiaryServiceResponse.setSuccess(false);
		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		  expect(systemParameterService.getSystemParameter(isA(SystemParameterServiceRequest.class))).andReturn(getSysParameServiceRespSecAuthTypeMock()).anyTimes();
		  expect(addBeneficiaryService.addBeneficiary(isA(AddBeneficiaryServiceRequest.class))).andReturn(addBeneficiaryServiceResponse).anyTimes();
		  expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).anyTimes();
		  expect(listValueResService.getListValueByGroup(isA(ListValueResServiceRequest.class))).andReturn(getListValueResByGroupServiceResponseEmptyMock()).anyTimes();

		  replay(systemParameterService,listValueResService,addBeneficiaryService,messageResourceService);

		  AddBeneficiaryOperationRequest request=new AddBeneficiaryOperationRequest();
		  request.setContext(getContextMock());
		  request.setBeneficiaryDTO(getBeneficiaryDTOMock());
		  request.setScndLvlauthReq(false);
		  AddBeneficiaryOperationResponse response = new AddBeneficiaryOperationResponse();
		    try {
		    	response=addBeneficiaryOperation.addBeneficiary(request);

		      } catch (RuntimeException e) {
		    	  
		      }

		assertNotNull(response);
		assertEquals(false,response.isSuccess());
		verify(systemParameterService,listValueResService,addBeneficiaryService,messageResourceService);

	  }

	 @After
	 public void tearDown(){
		 	addBeneficiaryOperation = null;
		 	addBeneficiaryService = null;
			systemParameterService = null;
	 }

	  protected BranchLookUpServiceResponse getBranchLookUpServiceResponseMock(){
		  BranchLookUpServiceResponse branchLookUpServiceResponse =new BranchLookUpServiceResponse();
		  BranchLookUpDTO branch=new BranchLookUpDTO();
		  branch.setBankName("ABC");
		  branch.setBranchCode("014");
		  branch.setBankCode("020");
		  List<BranchLookUpDTO> branchList=new ArrayList<BranchLookUpDTO>();
		  branchList.add(branch);
		  branchLookUpServiceResponse.setBranchList(branchList);
		  return branchLookUpServiceResponse;
	  }

	  protected RetreivePayeeListServiceResponse getRetreivePayeeListServiceResponseIACMock(){
		    RetreivePayeeListServiceResponse retreivePayeeListServiceResponse=new RetreivePayeeListServiceResponse();
		    BeneficiaryDTO beneficiaryDTO=new BeneficiaryDTO();
		    beneficiaryDTO.setPayeeTypeCode("IAC");
		    beneficiaryDTO.setPayeeId("BNF0000IAC");
		    beneficiaryDTO.setDestinationAccountNumber("111111111111");
		    beneficiaryDTO.setDestinationBranchCode("014");
		    beneficiaryDTO.setStatus("ACTIVE");
		    List<BeneficiaryDTO> payeeList=new ArrayList<BeneficiaryDTO>();
		    payeeList.add(beneficiaryDTO);
		    retreivePayeeListServiceResponse.setPayeeList(payeeList);
		    return retreivePayeeListServiceResponse;
	  }

	  protected RetreivePayeeListServiceResponse getRetreivePayeeListServiceResponseDACMock(){
		    RetreivePayeeListServiceResponse retreivePayeeListServiceResponse=new RetreivePayeeListServiceResponse();
		    BeneficiaryDTO beneficiaryDTO=new BeneficiaryDTO();
		    beneficiaryDTO.setPayeeTypeCode("DAC");
		    beneficiaryDTO.setPayeeId("BNF0000DAC");
		    beneficiaryDTO.setDestinationAccountNumber("123456");
		    beneficiaryDTO.setDestinationBranchCode("014");
		    beneficiaryDTO.setDestinationBankCode("020");
		    beneficiaryDTO.setStatus("ACTIVE");
		    List<BeneficiaryDTO> payeeList=new ArrayList<BeneficiaryDTO>();
		    payeeList.add(beneficiaryDTO);
		    retreivePayeeListServiceResponse.setPayeeList(payeeList);
		    return retreivePayeeListServiceResponse;
	  }

	  protected RetreivePayeeListServiceResponse getRetreivePayeeListServiceResponseEmptyMock(){
		    RetreivePayeeListServiceResponse retreivePayeeListServiceResponse=new RetreivePayeeListServiceResponse();
		    retreivePayeeListServiceResponse.setPayeeList(new ArrayList<BeneficiaryDTO>());
		    return retreivePayeeListServiceResponse;
	  }

	  protected ListValueResByGroupServiceResponse getListValueResByGroupServiceResponseEmptyMock(){
		  ListValueResByGroupServiceResponse listValueResByGroupServiceResponse=new ListValueResByGroupServiceResponse();
		    return listValueResByGroupServiceResponse;
	  }


}
