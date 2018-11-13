/*
 * Copyright (c) 2013 Barclays Bank Plc, All Rights Reserved.
 *
 * This code is confidential to Barclays Bank Plc and shall not be disclosed
 * outside the Bank without the prior written permission of the Director of
 * CIO
 *
 * In the event that such disclosure is permitted the code shall not be copied
 * or distributed other than on a need-to-know basis and any recipients may be
 * required to sign a confidentiality undertaking in favour of Barclays Bank
 * Plc.
 */
package com.barclays.bmg.operation.addprospect;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.dto.Address;
import com.barclays.bmg.dto.ProspectEntity;
import com.barclays.bmg.dto.ProspectInfoEntity;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.request.AddProspectOperationRequest;
import com.barclays.bmg.operation.response.AddProspectOperationResponse;
import com.barclays.bmg.service.AddProspectService;
import com.barclays.bmg.service.request.AddProspectServiceRequest;
import com.barclays.bmg.service.response.AddProspectServiceResponse;

public class AddProspectOperation extends BMBCommonOperation {

    private AddProspectService addProspectService;

    public AddProspectOperationResponse addProspect(AddProspectOperationRequest addProspectOperationRequest) {

	AddProspectOperationResponse addProspectOperationResponse = new AddProspectOperationResponse();

	Context context = addProspectOperationRequest.getContext();
	loadParameters(context, ActivityConstant.COMMON_ID, ActivityConstant.SEC_COMMON_ID, context.getActivityId());
	AddProspectServiceRequest addProspectServiceRequest = new AddProspectServiceRequest();
	addProspectServiceRequest.setContext(addProspectOperationRequest.getContext());
	addProspectServiceRequest.setProspectEntity(getProspectEntity());
	addProspectServiceRequest.setProspectInfoEntity(getProspectInfoEntity());
	AddProspectServiceResponse addProspectServiceResponse = addProspectService.addProspect(addProspectServiceRequest);

	if (addProspectServiceResponse != null) {
	    addProspectOperationResponse.setResCde(addProspectServiceResponse.getResCde());
	    addProspectOperationResponse.setResMsg(addProspectServiceResponse.getResMsg());

	    addProspectOperationResponse.setProspectId(addProspectServiceResponse.getProspectId());
	    addProspectOperationResponse.setAssignedTo(addProspectServiceResponse.getAssignedTo());
	    addProspectOperationResponse.setDuplicateFlag(addProspectServiceResponse.isDuplicateFlag());

	}
	return addProspectOperationResponse;
    }

    public AddProspectService getAddProspectService() {
	return addProspectService;
    }

    public void setAddProspectService(AddProspectService addProspectService) {
	this.addProspectService = addProspectService;
    }

    private ProspectEntity getProspectEntity() {
	ProspectEntity prospectEntity = new ProspectEntity();
	// prospectEntity.setTitle("Mr.");
	prospectEntity.setFirstName("RAKESH");
	prospectEntity.setLastName("RAVLEKAR");
	// prospectEntity.setMiddleName("N");
	// prospectEntity.setNationality("IN");
	// prospectEntity.setResidenceCountry("AE");
	// prospectEntity.setGender("M");
	// prospectEntity.setCustomerIdentifier("AE");
	// prospectEntity.setCustomerIdentifierType("I");
	// prospectEntity.setCustomerIdentifierExpDT("2015-08-14T23:00:00.000Z");
	// prospectEntity.setDob("1979-08-10T23:00:00.000Z");
	// prospectEntity.setMotherMaidenName("I");
	// prospectEntity.setMaritalStatus("2");
	prospectEntity.setMobileNo("8805110600");
	prospectEntity.setHomeNo("8805110600");
	// prospectEntity.setOfficeNo("8805110600");
	// prospectEntity.setAddress(getAddress());
	// prospectEntity.setEmail("v.d@gmail.com");
	// prospectEntity.setEmploymentType("Salaried");
	// prospectEntity.setEmployerName("ABC");
	// prospectEntity.setDesignation("SA");
	// prospectEntity.setProfession("99");
	// prospectEntity.setMonthlyIncome("100");
	// prospectEntity.setBizNature("0");
	// prospectEntity.setFtCountry1("AE");
	// prospectEntity.setFtCountry2("AR");
	// prospectEntity.setFtCountry3("AW");
	// prospectEntity.setExpectedCshGtSegLmt("Y");
	// prospectEntity.setSolicitationChannel("2");
	// prospectEntity.setInitDepAmtGtSegLmt("Y");
	// prospectEntity.setGeographicStatus("B");
	// prospectEntity.setPepStatus("N");
	prospectEntity.setRemarks("I am Interested in Home Loan...");
	// prospectEntity.setContactChannel("Phone");
	// prospectEntity.setProspectStatus("NEW");
	// prospectEntity.setAssignedTo("E20017209");
	// prospectEntity.setMobileNoISDCode("91");
	// prospectEntity.setHomeNoISDCode("91");
	return prospectEntity;
    }

    private Address getAddress() {
	Address address = new Address();
	address.setAddLine1("Add Line1");
	address.setAddLine2("Add Line2");
	address.setAddrType("002");
	address.setCity(new Address.City("PN"));
	address.setCountry(new Address.Country("IN"));
	address.setResidingSince("2001-07-31T23:00:00.000Z");
	address.setState(new Address.State("MH"));
	address.setZipCode("411041");
	return address;

    }

    private ProspectInfoEntity getProspectInfoEntity() {
	ProspectInfoEntity prospectInfoEntity = new ProspectInfoEntity();
	// prospectInfoEntity.setCreateBy("E20017209");
	// prospectInfoEntity.setCreateOn("2013-08-14T08:54:35.217Z");
	// prospectInfoEntity.setModifiedOn("2013-08-14T08:54:35.217Z");
	// prospectInfoEntity.setOwnerName("E20017209");
	return prospectInfoEntity;

    }
}