/**
 *
 */
package com.barclays.bmg.operation.beneficiary;

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

import com.barclays.bmg.constants.BillPaymentConstants;
import com.barclays.bmg.constants.BillPaymentResponseCodeConstants;
import com.barclays.bmg.dto.BeneficiaryDTO;
import com.barclays.bmg.dto.CategorizedPayeeListDTO;
import com.barclays.bmg.operation.BaseOperation;
import com.barclays.bmg.operation.response.fundtransfer.external.FilterUrgentPayeeListOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.FilterUrgentPayeeListOperationResponse;
import com.barclays.bmg.service.MessageResourceService;
import com.barclays.bmg.service.request.MessageResourceServiceRequest;

/**
 * @author E20037686
 *
 */
public class FilterUrgentPayeeListOperationTest extends BaseOperation
{
	private FilterUrgentPayeeListOperation filterUrgentPayeeListOperation;
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
		filterUrgentPayeeListOperation = new FilterUrgentPayeeListOperation();
		messageResourceService = createNiceMock(MessageResourceService.class);

		filterUrgentPayeeListOperation.setMessageResourceService(messageResourceService);
	}

//	when the operation runs completely through.
	@Test
	public void filterUrgentPayees()
	{
		FilterUrgentPayeeListOperationRequest request = new FilterUrgentPayeeListOperationRequest();
		request.setContext(getContextMock());
		request.setCategorizedPayeeList(getCategorizedPayeeListDTOList());

		FilterUrgentPayeeListOperationResponse response = new FilterUrgentPayeeListOperationResponse();
		try
		{
			response = filterUrgentPayeeListOperation.filterUrgentPayees(request);
		}
		catch(RuntimeException e)
		{
			
		}
		assertNotNull(response);
		assertNotNull(response.isSuccess());
		assertTrue(response.isSuccess());
		assertNotNull(response.getCategorizedPayeeList());
		assertNotNull(response.getUrgentPayeeList());
	}

//	when the operation fails due to "NO_PAYEES_REGISTERED"
	@Test
	public void filterUrgentPayeesFails()
	{
		FilterUrgentPayeeListOperationRequest request = new FilterUrgentPayeeListOperationRequest();
		request.setContext(getContextMock());
		request.setUrgent(true);

		expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).atLeastOnce();
		replay(messageResourceService);

		FilterUrgentPayeeListOperationResponse response = new FilterUrgentPayeeListOperationResponse();
		try
		{
			response = filterUrgentPayeeListOperation.filterUrgentPayees(request);
		}
		catch(RuntimeException e)
		{
			
		}
		assertNotNull(response);
		assertNotNull(response.isSuccess());
		assertFalse(response.isSuccess());
		assertNotNull(response.getCategorizedPayeeList());
		assertNotNull(response.getUrgentPayeeList());
		assertNotNull(response.getResCde());
		assertEquals(BillPaymentResponseCodeConstants.NO_PAYEES_REGISTERED, response.getResCde());

		verify(messageResourceService);
	}

	private List<BeneficiaryDTO> getBeneficiaryDTOList()
	{
		List<BeneficiaryDTO> list = new ArrayList<BeneficiaryDTO>();
		BeneficiaryDTO dto = new BeneficiaryDTO();
		dto.setStatus(BillPaymentConstants.ACTIVE_STATUS);
		dto.setPayeeTypeCode("DAC");
		list.add(dto);
		return list;
	}

	private List<CategorizedPayeeListDTO> getCategorizedPayeeListDTOList()
	{
		List<CategorizedPayeeListDTO> list = new ArrayList<CategorizedPayeeListDTO>();
		CategorizedPayeeListDTO dto = new CategorizedPayeeListDTO();
		dto.setTransactionFacadeRoutineType("INTL");
		dto.setPayeeList(getBeneficiaryDTOList());
		list.add(dto);
		return list;
	}

	@After
	public void tearDown()
	{
		filterUrgentPayeeListOperation = null;
		messageResourceService = null;
	}
}
