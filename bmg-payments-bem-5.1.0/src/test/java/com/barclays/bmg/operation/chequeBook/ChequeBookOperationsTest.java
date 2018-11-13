package  com.barclays.bmg.operation.chequeBook;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.barclays.bmg.chequebook.dao.ChequeBookRequestExecuteDAO;
import com.barclays.bmg.chequebook.operation.ChequeBookRequestExecuteOperation;
import com.barclays.bmg.chequebook.operation.request.ChequeBookExecuteOperationRequest;
import com.barclays.bmg.chequebook.operation.response.ChequeBookExecuteOperationResponse;
import com.barclays.bmg.chequebook.service.ChequeBookRequestExecuteService;
import com.barclays.bmg.chequebook.service.request.ChequeBookExecuteServiceRequest;
import com.barclays.bmg.chequebook.service.response.ChequeBookExecuteServiceResponse;
import com.barclays.bmg.dto.CASAAccountDTO;
import com.barclays.bmg.dto.ChequeBookRequestDTO;
import com.barclays.bmg.operation.BaseClass;
import com.barclays.bmg.service.MessageResourceService;
import com.barclays.bmg.service.SystemParameterService;
import com.barclays.bmg.service.request.MessageResourceServiceRequest;
import com.barclays.bmg.service.request.SystemParameterServiceRequest;

public class ChequeBookOperationsTest extends BaseClass{
	private SystemParameterService systemParameterService;
 	private MessageResourceService messageResourceService;
	private ChequeBookRequestExecuteOperation chequeBookRequestExecuteOperation;
	private ChequeBookRequestExecuteService chequeBookRequestExecuteService;
	private ChequeBookRequestExecuteDAO chequeBookRequestExecuteDAO;

	public ChequeBookRequestExecuteDAO getChequeBookRequestExecuteDAO() {
		return chequeBookRequestExecuteDAO;
	}

	public void setChequeBookRequestExecuteDAO(
			ChequeBookRequestExecuteDAO chequeBookRequestExecuteDAO) {
		this.chequeBookRequestExecuteDAO = chequeBookRequestExecuteDAO;
	}

	@Before
	 public void setUp() throws Exception {
		chequeBookRequestExecuteOperation = new ChequeBookRequestExecuteOperation();
        systemParameterService = createNiceMock(SystemParameterService.class);
        chequeBookRequestExecuteService = createNiceMock(ChequeBookRequestExecuteService.class);
        messageResourceService=createNiceMock(MessageResourceService.class);

        chequeBookRequestExecuteOperation.setSystemParameterService(systemParameterService);
        chequeBookRequestExecuteOperation.setMessageResourceService(messageResourceService);
        chequeBookRequestExecuteOperation.setChequeBookRequestExecuteService(chequeBookRequestExecuteService);
	}

	  @Test
	  public void testChequeBookRequestOperation() {
		  ChequeBookExecuteOperationResponse cheqBookRespons = new ChequeBookExecuteOperationResponse();
		  cheqBookRespons.setSuccess(true);
		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		  expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).anyTimes();
		  expect(chequeBookRequestExecuteService.executeChequeBookRequest(isA(ChequeBookExecuteServiceRequest.class))).andReturn(getChequeBookExecuteServiceResponseMock()).anyTimes();
          replay(chequeBookRequestExecuteService,systemParameterService,messageResourceService);

          ChequeBookExecuteOperationRequest request = new ChequeBookExecuteOperationRequest();
          setReParams(request);
          request.setContext(getContextMock());
		  ChequeBookExecuteOperationResponse response=new ChequeBookExecuteOperationResponse();

		  try {
 				response =chequeBookRequestExecuteOperation.chequeBookExecute(request);

		    } catch (RuntimeException e) {
		    	  
		  }
	      assertNotNull(response);
		   assertEquals(true,response.isSuccess());
		  assertNotNull(response.getTxnDt());
		  verify(chequeBookRequestExecuteService,systemParameterService,messageResourceService);
	  }


	  @Test
	  public void testChequeBookRequestOperationForErro() {
		  ChequeBookExecuteOperationResponse cheqBookRespons = new ChequeBookExecuteOperationResponse();
		  cheqBookRespons.setSuccess(true);
		  expect(systemParameterService.getSysParamByActivityId(isA(SystemParameterServiceRequest.class))).andReturn(getSystemParameterListServiceResponseMock()).anyTimes();
		  expect(messageResourceService.getMessageDescByKey(isA(MessageResourceServiceRequest.class))).andReturn(getMessageResourceServiceResponseMock()).anyTimes();
		  expect(chequeBookRequestExecuteService.executeChequeBookRequest(isA(ChequeBookExecuteServiceRequest.class))).andReturn(getChequeBookExecuteServiceResponseMockForError()).anyTimes();
          replay(chequeBookRequestExecuteService,systemParameterService,messageResourceService);

          ChequeBookExecuteOperationRequest request = new ChequeBookExecuteOperationRequest();
          setReParams(request);
          request.setContext(getContextMock());
		  ChequeBookExecuteOperationResponse response=new ChequeBookExecuteOperationResponse();

		  try {
 				response =chequeBookRequestExecuteOperation.chequeBookExecute(request);

		    } catch (RuntimeException e) {
		    	  
		  }
	      assertNotNull(response);
		  assertEquals(false,response.isSuccess());
		  assertEquals(null,response.getTxnDt());
		  verify(chequeBookRequestExecuteService,systemParameterService,messageResourceService);
	  }


	 private void setReParams(ChequeBookExecuteOperationRequest request) {
		 ChequeBookRequestDTO chequeBookRequestDTO = new ChequeBookRequestDTO();
		 CASAAccountDTO account = new CASAAccountDTO();
		 account.setAccountNumber("180909096454546");
		 account.setAccountNickName("barclay ");
		chequeBookRequestDTO.setAccount(account);

		chequeBookRequestDTO.setBookSize("500");
		 chequeBookRequestDTO.setBranchName("branchName");

		request.setChequeBookRequestDTO(chequeBookRequestDTO );

	}

	private ChequeBookExecuteServiceResponse getChequeBookExecuteServiceResponseMock() {
		 ChequeBookExecuteServiceResponse response = new ChequeBookExecuteServiceResponse();
		 response.setSuccess(true);
		 response.setTxnRefNo("10000809178");
		 response.setTxnDt(new Date());
		 response.setTxnTyp("chequeRequet");
		 response.setResCde("");
		 return response;
	}
	private ChequeBookExecuteServiceResponse getChequeBookExecuteServiceResponseMockForError() {
		 ChequeBookExecuteServiceResponse response = new ChequeBookExecuteServiceResponse();
		 response.setSuccess(false);
		 response.setTxnRefNo(null);
		 response.setTxnDt(null);
		 response.setTxnTyp(null);
		 return response;
	}

	@After
	 public void tearDown() {
		chequeBookRequestExecuteService = null;
		chequeBookRequestExecuteOperation = null;
		messageResourceService = null;
		systemParameterService = null;


	}

}
