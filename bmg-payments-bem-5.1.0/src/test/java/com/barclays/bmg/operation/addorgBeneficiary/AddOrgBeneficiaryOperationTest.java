package  com.barclays.bmg.operation.addorgBeneficiary;



import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.barclays.bmg.operation.BaseClass;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.operation.beneficiary.AddOrgBeneficiaryOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.AddOrgBenefeciaryOperationRequest;
import com.barclays.bmg.operation.request.fundtransfer.external.TransactionLimitOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.TransactionLimitOperationResponse;
import com.barclays.bmg.service.AddOrganizationBeneficiaryService;
import com.barclays.bmg.service.BillerService;
import com.barclays.bmg.service.MessageResourceService;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.request.BillerServiceRequest;
import com.barclays.bmg.service.request.MessageResourceServiceRequest;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.response.BillerServiceResponse;

public class AddOrgBeneficiaryOperationTest extends BaseClass{


	private AddOrganizationBeneficiaryService addOrgBeneficiaryService;
	private AddOrgBeneficiaryOperation addOrgBeneficiaryOperation;
    private SystemParameterService systemParameterService;
 	private MessageResourceService messageResourceService;
	private BillerService billerService;

	@Before
	 public void setUp() throws Exception {
		  addOrgBeneficiaryOperation = new AddOrgBeneficiaryOperation();
		  addOrgBeneficiaryService = createMock(AddOrganizationBeneficiaryService.class);
		  addOrgBeneficiaryOperation.setAddOrgBeneficairyService(addOrgBeneficiaryService);
		  systemParameterService = createNiceMock(SystemParameterService.class);
		  addOrgBeneficiaryOperation.setSystemParameterService(systemParameterService);
		  billerService =createNiceMock(BillerService.class);
		  addOrgBeneficiaryOperation.setBillerService(billerService);
		  messageResourceService=createNiceMock(MessageResourceService.class);
		  addOrgBeneficiaryOperation.setMessageResourceService(messageResourceService);
	}


	  @Test
	  public void testGetBillPaymentsBillerList() {
		  TransactionLimitOperationResponse transLimitOperationRes = new TransactionLimitOperationResponse();
		  transLimitOperationRes.setSuccess(true);
		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		  expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).anyTimes();
	 	  expect(billerService.getBillPaymentsBillerList(isA(BillerServiceRequest.class))).andReturn(getBillerServiceResponseMock()).anyTimes();
          replay(billerService,systemParameterService,messageResourceService);

          TransactionLimitOperationRequest request = new TransactionLimitOperationRequest();
		  request.setContext(getContextMock());
 		  AddOrgBenefeciaryOperationRequest billerRegistrationOperationRequest =new AddOrgBenefeciaryOperationRequest();
		  List<BillerDTO> allBillerList = new ArrayList<BillerDTO>();
		  try {
			  allBillerList = addOrgBeneficiaryOperation.getBillPaymentsBillerList(billerRegistrationOperationRequest);
		    } catch (RuntimeException e) {
		    	  
		  }
	      assertNotNull(allBillerList);
		  assertEquals(true,allBillerList.size() > 0);
		  assertNotNull(allBillerList.get(0).getBillerAccountNumber());
		  verify(billerService,systemParameterService,messageResourceService);
	  }
	  @Test
	  public void testGetBillPaymentsBillerListForError() {
		  TransactionLimitOperationResponse transLimitOperationRes = new TransactionLimitOperationResponse();
		  transLimitOperationRes.setSuccess(true);
		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		  expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).anyTimes();
	 	  expect(billerService.getBillPaymentsBillerList(isA(BillerServiceRequest.class))).andReturn(getBillerServiceResponseMockForWrongBenfId()).anyTimes();
          replay(billerService,systemParameterService,messageResourceService);

          TransactionLimitOperationRequest request = new TransactionLimitOperationRequest();
		  request.setContext(getContextMock());
 		  AddOrgBenefeciaryOperationRequest billerRegistrationOperationRequest =new AddOrgBenefeciaryOperationRequest();
		  List<BillerDTO> allBillerList = new ArrayList<BillerDTO>();
		  try {
			  allBillerList = addOrgBeneficiaryOperation.getBillPaymentsBillerList(billerRegistrationOperationRequest);
		    } catch (RuntimeException e) {
		    	  
		  }

		 assertEquals(null,allBillerList);
/*		 assertEquals(null,allBillerList,allBillerList.get(0).getBillerAccountNumber());
*/		  verify(billerService,systemParameterService,messageResourceService);
	  }
	 @After
	 public void tearDown(){
		    addOrgBeneficiaryService = null;
			addOrgBeneficiaryOperation = null;
 		 	messageResourceService= null;
			systemParameterService = null;
	 }

	  protected BillerServiceResponse getBillerServiceResponseMock(){
		  BillerServiceResponse billerServiceResponse =new BillerServiceResponse();
	    	billerServiceResponse.setSuccess(true);
	    	billerServiceResponse.setBillerList(getBillerDTOListMock());
	    	return billerServiceResponse;
	  }
	  protected BillerServiceResponse getBillerServiceResponseMockForWrongBenfId(){
		  BillerServiceResponse billerServiceResponse =new BillerServiceResponse();
	    	billerServiceResponse.setSuccess(false);
	    	billerServiceResponse.setBillerList(null);
	    	return billerServiceResponse;
	  }

}
