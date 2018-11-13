package com.barclays.bmg.dao.accountservices.adapter;

import java.util.Map;

import com.barclays.bem.AddProblem.CaseRequest;
import com.barclays.bem.BEMBaseDataTypes.ProductProcessorTypeCode;
import com.barclays.bem.crmaccount.AccountEntity;
import com.barclays.bem.customer.CustomerEntity;
import com.barclays.bem.notes.NoteEntity;
import com.barclays.bem.problem.CaseEntity;
import com.barclays.bem.service.CallerInfo;
import com.barclays.bem.service.IdValueEntity;
import com.barclays.bmg.constants.SystemParameterConstant;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.ReportProblemServiceRequest;

public class ReportProblemPayloadAdapter {


	//private static final String SERVICE_TYPE = "LINK_CC_KEBRB";
	private static final String DEFAULT1_VALUES ="1";
	private static final String DEFAULT2_VALUES ="2";
	private static final String SERVICE_CATEGORY ="ServiceRequest";


	public CaseRequest adaptPayload(WorkContext workContext){

		DAOContext daoContext = (DAOContext)workContext;

		Object[] args = daoContext.getArguments();

		ReportProblemServiceRequest request=
			(ReportProblemServiceRequest)args[0];

		CaseRequest requestBody = mapDataInRequestBody(request);
		return requestBody;
	}
	private CaseRequest mapDataInRequestBody(ReportProblemServiceRequest request){
		String ENTITY_DESC = "Hello Money : Kindly help to link my Credit card; my Customer ID is ";
		String NOTE_DESC = "Kindly help to link my CC; my Customer ID is ";

		CaseRequest caseRequest = new CaseRequest();
		Map<String,Object> sysMap = request.getContext().getContextMap();

		CaseEntity caseEntity = new CaseEntity();
		CaseEntity[] caseEntities = new CaseEntity[]{caseEntity};
		caseRequest.setEntities(caseEntities);

		AccountEntity account = new AccountEntity();
		account.setAccountNumber(request.getAccountNumber());
		account.setAccountType(request.getAccountType());

		caseRequest.getEntities()[0].setAccount(account);


		CustomerEntity customer = new CustomerEntity();

		if (request.getContext().getPpMap() != null) {
			customer.setCustomerNumber(request.getContext().getPpMap().get(ProductProcessorTypeCode._BK));
		}
		caseRequest.getEntities()[0].setCustomerEntity(customer);

		if ((String) sysMap.get(SystemParameterConstant.ADD_PRO_PRIORITY) != null) {
			caseRequest.getEntities()[0].setPriority((String) sysMap
					.get(SystemParameterConstant.ADD_PRO_PRIORITY));
		} else{
			caseRequest.getEntities()[0].setPriority(DEFAULT1_VALUES);
		}

		if ((String) sysMap.get(SystemParameterConstant.ADD_PRO_BUSINESS_AREA) != null) {
			caseRequest.getEntities()[0].setBusinessArea((String) sysMap
					.get(SystemParameterConstant.ADD_PRO_BUSINESS_AREA));
		} else{
			caseRequest.getEntities()[0].setBusinessArea(DEFAULT1_VALUES);
		}

		if ((String) sysMap.get(SystemParameterConstant.ADD_PRO_ORIGIN) != null) {
			caseRequest.getEntities()[0].setOrigin((String) sysMap
					.get(SystemParameterConstant.ADD_PRO_ORIGIN));
		} else {
			caseRequest.getEntities()[0].setOrigin(DEFAULT2_VALUES);
		}

		if ((String) sysMap
				.get(SystemParameterConstant.ADD_PRO_SERVICE_CATEGORY) != null) {
			caseRequest.getEntities()[0].setServiceCategory((String) sysMap
					.get(SystemParameterConstant.ADD_PRO_SERVICE_CATEGORY));
		} else{
			caseRequest.getEntities()[0].setOrigin(SERVICE_CATEGORY);
		}

		if ((String) sysMap.get(SystemParameterConstant.ADD_PRO_CASES_TATUS) != null) {
			caseRequest.getEntities()[0].setCaseStatus((String) sysMap
					.get(SystemParameterConstant.ADD_PRO_CASES_TATUS));
		} else{
			caseRequest.getEntities()[0].setCaseStatus(DEFAULT1_VALUES);
		}

		if (request.getContext().getPpMap() != null) {
		caseRequest.getEntities()[0].setDescription(ENTITY_DESC + request.getContext().getPpMap().get(ProductProcessorTypeCode._BK));
		}
		NoteEntity noteEntity = new NoteEntity();

		//noteEntity.setCreateBy((String)sysMap.get(SystemParameterConstant.ADD_PRO_STAFF_ID));
		noteEntity.setCreateBy("SHM");
		if (request.getContext().getPpMap() != null) {
			noteEntity.setNoteDescription( NOTE_DESC + request.getContext().getPpMap().get(ProductProcessorTypeCode._BK) );
		}
		caseRequest.getEntities()[0].setNotes(new NoteEntity[]{noteEntity});

		CallerInfo caller = new CallerInfo();
		caller.setBusinessId(request.getContext().getBusinessId());

		caller.setSystemId(request.getContext().getSystemId());
		caller.setTransactionReferenceNo(request.getContext().getActivityRefNo());

		caller.setUserId(request.getContext().getUserId());
		caseRequest.setCaller(caller);

		IdValueEntity servType = new IdValueEntity();
		servType.setId("LINK_CC_"+request.getContext().getBusinessId());
		caseRequest.getEntities()[0].setServiceType(servType);

		//CR#83 Apply Product
		if(request.getContext().getOpCde().equalsIgnoreCase("OP0984")){
			if (request.getContext().getPpMap() != null && request.getProductName()!= null) {
				caseRequest.getEntities()[0].setDescription("The customer with Bank CIF # " + request.getContext().getPpMap().get(ProductProcessorTypeCode._BK)+
						" has shown interest in "+request.getProductName()+" "+request.getSubProductName()+" on SHM channel");

				noteEntity.setNoteDescription( "The customer with Bank CIF # " + request.getContext().getPpMap().get(ProductProcessorTypeCode._BK)+
						" has shown interest in "+request.getProductName()+" "+request.getSubProductName()+" on SHM channel");

				}
			servType.setId("CLM_SHM_"+request.getContext().getBusinessId());
			caseRequest.getEntities()[0].setServiceType(servType);
		}

		return caseRequest;
	}

}
