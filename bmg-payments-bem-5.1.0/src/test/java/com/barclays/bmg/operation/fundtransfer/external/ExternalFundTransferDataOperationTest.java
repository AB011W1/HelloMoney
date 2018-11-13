/**
 *
 */
package com.barclays.bmg.operation.fundtransfer.external;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.operation.BaseOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.ExternalFundTransferDataOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.ExternalFundTransferDataOperationResponse;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.product.ListValueResService;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;

/**
 * @author E20037686
 *
 */
public class ExternalFundTransferDataOperationTest extends BaseOperation
{
	private ExternalFundTransferDataOperation externalFundTransferDataOperation;
	private Map<String, Boolean> currLstFilterMap;
	private SystemParameterService systemParameterService;
	protected ListValueResService listValueResService;

	@BeforeClass
	public static void baseSetup()
	{}

	@AfterClass
	public static void baseEnd()
	{}

	@Before
	public void setUp() throws Exception
	{
		externalFundTransferDataOperation = new ExternalFundTransferDataOperation();

		currLstFilterMap = createMock(HashMap.class);
		systemParameterService = createMock(SystemParameterService.class);
		listValueResService = createMock(ListValueResService.class);

		externalFundTransferDataOperation.setSystemParameterService(systemParameterService);
		externalFundTransferDataOperation.setCurrLstFilterMap(currLstFilterMap);
		externalFundTransferDataOperation.setListValueResService(listValueResService);
	}

//	when the operation runs completely through.
	@Test
	public void filterUrgentPayees()
	{
		ExternalFundTransferDataOperationRequest request = new ExternalFundTransferDataOperationRequest();
		request.setContext(getContextMock());
		BMBContextHolder.setContext(request.getContext());

		expect(systemParameterService.getSystemParameter(isA(SystemParameterServiceRequest.class))).andReturn(null).atLeastOnce();
		expect(listValueResService.getListValueByGroup(isA(ListValueResServiceRequest.class))).andReturn(null).atLeastOnce();

		replay(systemParameterService);

		ExternalFundTransferDataOperationResponse response = new ExternalFundTransferDataOperationResponse();
		try
		{
			response = externalFundTransferDataOperation.getExternalFundTransferData(request);
		}
		catch(RuntimeException e)
		{
			
		}
		assertNotNull(response);
		assertNotNull(response.getCurrLst());

		verify(systemParameterService);
	}

	@After
	public void tearDown()
	{
		systemParameterService = null;
		externalFundTransferDataOperation = null;
		listValueResService = null;
		currLstFilterMap = null;
	}
}