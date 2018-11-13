package com.barclays.bmg.operation.payments;

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

import com.barclays.bmg.dto.BillPaymentHistory;
import com.barclays.bmg.dto.BillerDTO;
import com.barclays.bmg.operation.BaseClass;
import com.barclays.bmg.operation.request.billpayment.ViewTxnHistoryDetailsOperationRequest;
import com.barclays.bmg.operation.response.billpayment.ViewTxnHistoryDetailsOperationResponse;
import com.barclays.bmg.service.BillerService;
import com.barclays.bmg.service.MessageResourceService;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.ViewTxnHistoryDetailsService;
import com.barclays.bmg.service.request.BillerServiceRequest;
import com.barclays.bmg.service.request.MessageResourceServiceRequest;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;
import com.barclays.bmg.service.request.ViewTxnHistoryDetailsServiceRequest;
import com.barclays.bmg.service.response.BillerServiceResponse;
import com.barclays.bmg.service.response.ViewTxnHistoryDetailsServiceResponse;

public class ViewBillPaymentDetailsOperationTest extends BaseClass {

	private ViewTxnHistoryDetailsOperation viewTxnHistoryDetailsOperation;
	private ViewTxnHistoryDetailsService viewTxnHistoryDetailsService;
	private SystemParameterService systemParameterService;
	private MessageResourceService messageResourceService;
	private BillerService billerService;

	@Before
	public void setUp() throws Exception {
		viewTxnHistoryDetailsOperation = new ViewBillPaymentDetailsOperation();
		viewTxnHistoryDetailsService = createNiceMock(ViewTxnHistoryDetailsService.class);
		viewTxnHistoryDetailsOperation
				.setViewTxnHistoryDetailsService(viewTxnHistoryDetailsService);
		systemParameterService = createNiceMock(SystemParameterService.class);
		viewTxnHistoryDetailsOperation
				.setSystemParameterService(systemParameterService);
		messageResourceService = createNiceMock(MessageResourceService.class);
		viewTxnHistoryDetailsOperation
				.setMessageResourceService(messageResourceService);
		billerService=createNiceMock(BillerService.class);
		viewTxnHistoryDetailsOperation.setBillerService(billerService);
	}

	@Test
	public void testViewTxnHistoryDetails() {


		expect(
				systemParameterService
						.getSysParamByActivityId(isA(SystemParameterServiceRequest.class)))
				.andReturn(getSystemParameterListServiceResponseMock())
				.anyTimes();

		expect(
				viewTxnHistoryDetailsService
						.viewBillPaymentDetails(isA(ViewTxnHistoryDetailsServiceRequest.class)))
				.andReturn(getViewTxnHistoryDetailsServiceResponseMock())
				.anyTimes();

		expect(
				billerService.getAllBillerList(isA(BillerServiceRequest.class)))
				.andReturn(getBillerServiceResponseMock())
				.anyTimes();

		replay(systemParameterService,viewTxnHistoryDetailsService,billerService);

		ViewTxnHistoryDetailsOperationRequest request = new ViewTxnHistoryDetailsOperationRequest();
		request.setContext(getContextMock());
		ViewTxnHistoryDetailsOperationResponse response = new ViewTxnHistoryDetailsOperationResponse();
		try {
			response = viewTxnHistoryDetailsOperation.viewTxnHistoryDetails(request);

		} catch (RuntimeException e) {
			
		}

		assertNotNull(response);
		assertEquals(true, response.isSuccess());
		assertNotNull(response.getTransactionHistoryDTO());

		verify(systemParameterService,viewTxnHistoryDetailsService,billerService);
	}

	@Test
	public void testViewTxnHistoryDetailsFail() {


		expect(
				systemParameterService
						.getSysParamByActivityId(isA(SystemParameterServiceRequest.class)))
				.andReturn(getSystemParameterListServiceResponseMock())
				.anyTimes();
		expect(
				messageResourceService
						.getMessageDescByKey(isA(MessageResourceServiceRequest.class)))
				.andReturn(getMessageResourceServiceResponseMock()).anyTimes();

		expect(
				viewTxnHistoryDetailsService
						.viewBillPaymentDetails(isA(ViewTxnHistoryDetailsServiceRequest.class)))
				.andReturn(getViewTxnHistoryDetailsServiceResponseFailMock())
				.anyTimes();

		replay(systemParameterService,viewTxnHistoryDetailsService,messageResourceService);

		ViewTxnHistoryDetailsOperationRequest request = new ViewTxnHistoryDetailsOperationRequest();
		request.setContext(getContextMock());
		ViewTxnHistoryDetailsOperationResponse response = new ViewTxnHistoryDetailsOperationResponse();
		try {
			response = viewTxnHistoryDetailsOperation.viewTxnHistoryDetails(request);

		} catch (RuntimeException e) {
			
		}

		assertNotNull(response);
		assertEquals(false, response.isSuccess());

		verify(systemParameterService,viewTxnHistoryDetailsService,messageResourceService);
	}

	@After
	public void tearDown() {
		viewTxnHistoryDetailsOperation = null;
		viewTxnHistoryDetailsService = null;
		systemParameterService = null;
		messageResourceService = null;
	}

	  protected ViewTxnHistoryDetailsServiceResponse getViewTxnHistoryDetailsServiceResponseMock(){
			ViewTxnHistoryDetailsServiceResponse viewTxnHistoryDetailsServiceResponse = new ViewTxnHistoryDetailsServiceResponse();
			viewTxnHistoryDetailsServiceResponse.setSuccess(true);
			BillPaymentHistory billPaymentHistory=new BillPaymentHistory();
			billPaymentHistory.setBillerId("1111111111");
			viewTxnHistoryDetailsServiceResponse.setBillPaymentHistory(billPaymentHistory);
		    return viewTxnHistoryDetailsServiceResponse;
	  }

	  protected ViewTxnHistoryDetailsServiceResponse getViewTxnHistoryDetailsServiceResponseFailMock(){
			ViewTxnHistoryDetailsServiceResponse viewTxnHistoryDetailsServiceResponse = new ViewTxnHistoryDetailsServiceResponse();
			viewTxnHistoryDetailsServiceResponse.setSuccess(false);
		    return viewTxnHistoryDetailsServiceResponse;
	  }

	  protected BillerServiceResponse getBillerServiceResponseMock(){
		  BillerServiceResponse billerServiceResponse = new BillerServiceResponse();
		  BillerDTO billerDTO=new BillerDTO();
		  billerDTO.setBillerId("11111111111");
		  List<BillerDTO> billerList=new ArrayList<BillerDTO>();
		  billerList.add(billerDTO);
		  billerServiceResponse.setBillerList(billerList);
		  return billerServiceResponse;
	  }

}
