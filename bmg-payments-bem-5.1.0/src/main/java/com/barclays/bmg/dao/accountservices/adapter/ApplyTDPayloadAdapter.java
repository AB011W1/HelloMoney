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
import com.barclays.bmg.service.request.ApplyTDServiceRequest;

public class ApplyTDPayloadAdapter {

    public CaseRequest adaptPayload(WorkContext workContext) {

	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	ApplyTDServiceRequest request = (ApplyTDServiceRequest) args[0];

	CaseRequest requestBody = mapDataInRequestBody(request);
	return requestBody;
    }

    private CaseRequest mapDataInRequestBody(ApplyTDServiceRequest request) {

	CaseRequest caseRequest = new CaseRequest();

	// BEMResHeader resHeader = request.getResponseHeader();

	Map<String, Object> sysMap = request.getContext().getContextMap();

	CaseEntity caseEntity = new CaseEntity();
	CaseEntity[] caseEntities = new CaseEntity[] { caseEntity };
	caseRequest.setEntities(caseEntities);

	AccountEntity account = new AccountEntity();
	account.setAccountNumber(request.getAccountNumber());
	account.setAccountType(request.getAccountType());

	caseRequest.getEntities()[0].setAccount(account);
	// caseRequest.getEntities()[0].setCreateBy("E20003107DEV");
	// caseRequest.getEntities()[0].setCreateOn(resHeader.getServiceContext().getServiceDateTime());

	CustomerEntity customer = new CustomerEntity();
	// customer.setCustomerType(sourceObj.getCustomerType());

	if (request.getContext().getPpMap() != null) {

	    customer.setCustomerNumber(request.getContext().getPpMap().get(ProductProcessorTypeCode._BK));
	}
	// "180006971");//request.getContext().getCustomerId());

	// Added by Qingming Liu, set the emails and full name to the add problem request
	/*
	 * customer.setEmailAddress(sourceObj.getEmailAddresses()); customer.setFullName(sourceObj.getFullName());
	 */

	caseRequest.getEntities()[0].setCustomerEntity(customer);
	caseRequest.getEntities()[0].setPriority((String) sysMap.get(SystemParameterConstant.ADD_PRO_PRIORITY));
	caseRequest.getEntities()[0].setBusinessArea((String) sysMap.get(SystemParameterConstant.ADD_PRO_BUSINESS_AREA));
	caseRequest.getEntities()[0].setOrigin((String) sysMap.get(SystemParameterConstant.ADD_PRO_ORIGIN));
	caseRequest.getEntities()[0].setServiceCategory((String) sysMap.get(SystemParameterConstant.ADD_PRO_SERVICE_CATEGORY));
	caseRequest.getEntities()[0].setCaseStatus((String) sysMap.get(SystemParameterConstant.ADD_PRO_CASES_TATUS));
	caseRequest.getEntities()[0].setDescription((String) sysMap.get(SystemParameterConstant.ADD_PRO_DESCRIPTION));
	// Added by Liu Qingming

	NoteEntity noteEntity = new NoteEntity();
	/* noteEntity.setNoteDescription(sourceObj.getAreaNote()); */
	noteEntity.setCreateBy((String) sysMap.get(SystemParameterConstant.ADD_PRO_STAFF_ID));
	// noteEntity.setCreateOn(resHeader.getServiceContext().getServiceDateTime());

	noteEntity.setNoteDescription(request.getNoteDescription());
	noteEntity.setNotesId(request.getNotesId());

	noteEntity.setObjectId(request.getObjectId());
	// acc num

	noteEntity.setSubject(request.getSubject());
	// TD
	caseRequest.getEntities()[0].setNotes(new NoteEntity[] { noteEntity });

	CallerInfo caller = new CallerInfo();
	caller.setBusinessId(request.getContext().getBusinessId());
	// caller.setServiceTimestamp(resHeader.getServiceContext().getServiceDateTime());
	caller.setSystemId("UB");
	caller.setTransactionReferenceNo(request.getContext().getActivityRefNo());
	// caller.setUserId("E20003107DEV");

	// caller.setUserId("BIR");

	if (request.getContext().getContextMap() != null
		&& request.getContext().getContextMap().containsKey(SystemParameterConstant.APPLY_TD_USER_ID)) {
	    caller.setUserId(request.getContext().getContextMap().get(SystemParameterConstant.APPLY_TD_USER_ID).toString());
	}

	caseRequest.setCaller(caller);

	IdValueEntity servType = new IdValueEntity();
	// servType.setId("ServiceType_293");//(String)sysMap.get(SystemParameterConstant.ADD_PRO_SERVICE_TYPE_ID));

	if (request.getContext().getContextMap() != null
		&& request.getContext().getContextMap().containsKey(SystemParameterConstant.APPLY_TD_SERVICE_TYPE)) {
	    servType.setId(request.getContext().getContextMap().get(SystemParameterConstant.APPLY_TD_SERVICE_TYPE).toString());
	}

	caseRequest.getEntities()[0].setServiceType(servType);

	// IdValueEntity departId = new IdValueEntity();
	// departId.setId("SDept_3");//(String)sysMap.get(SystemParameterConstant.ADD_PRO_DEPART_ID));
	// caseRequest.getEntities()[0].setAssignToDept(departId);

	// caseRequest.getEntities()[0].setCreateByDept(soureObj.getCreateByDept());

	return caseRequest;
    }

}
