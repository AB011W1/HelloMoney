package com.barclays.bmg.dao.addprospect.adapter;

import com.barclays.bem.AddProspect.AddProspectRequestEntities;
import com.barclays.bem.ProspectEntity.ProspectEntity;
import com.barclays.bem.ProspectInfoEntity.ProspectInfoEntity;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.service.request.AddProspectServiceRequest;

public class AddProspectPayloadAdapter {

    public AddProspectRequestEntities[] adaptPayLoad(WorkContext workContext) {
	DAOContext daoContext = (DAOContext) workContext;

	Object[] args = daoContext.getArguments();

	AddProspectServiceRequest addProspectServiceRequest = (AddProspectServiceRequest) args[0];
	// Context context = serRequest.getContext();

	AddProspectRequestEntities[] arlProspectRequestEntities = new AddProspectRequestEntities[1];
	AddProspectRequestEntities addProspectRequestEntities = new AddProspectRequestEntities();
	addProspectRequestEntities.setProspectEntity(getProspectEntity(addProspectServiceRequest.getProspectEntity()));
	addProspectRequestEntities.setProspectInfoEntity(getProspectInfoEntity(addProspectServiceRequest.getProspectInfoEntity()));
	arlProspectRequestEntities[0] = addProspectRequestEntities;

	return arlProspectRequestEntities;
    }

    private ProspectEntity getProspectEntity(com.barclays.bmg.dto.ProspectEntity prospectEntityDTO) {
	ProspectEntity prospectEntity = new ProspectEntity();
	// /prospectEntity.setTitle(prospectEntityDTO.getTitle());
	prospectEntity.setFirstName(prospectEntityDTO.getFirstName());
	prospectEntity.setLastName(prospectEntityDTO.getLastName());
	// prospectEntity.setMiddleName(prospectEntityDTO.getMiddleName());
	// prospectEntity.setNationality(prospectEntityDTO.getNationality());
	// prospectEntity.setResidenceCountry(prospectEntityDTO.getResidenceCountry());
	// prospectEntity.setGender(prospectEntityDTO.getGender());
	// prospectEntity.setCustomerIdentifier(prospectEntityDTO.getCustomerIdentifier());
	// prospectEntity.setCustomerIdentifierType(prospectEntityDTO.getCustomerIdentifierType());
	// // prospectEntity.setCustomerIdentifierExpDT(prospectEntityDTO.getCustomerIdentifierExpDT());
	// // prospectEntity.setDob(prospectEntityDTO.getDob());
	// prospectEntity.setMotherMaidenName(prospectEntityDTO.getMotherMaidenName());
	// prospectEntity.setMaritalStatus(prospectEntityDTO.getMaritalStatus());
	prospectEntity.setMobileNo(prospectEntityDTO.getMobileNo());
	prospectEntity.setHomeNo(prospectEntityDTO.getHomeNo());
	// prospectEntity.setOfficeNo(prospectEntityDTO.getOfficeNo());
	// prospectEntity.setAddress(prospectEntityDTO.getAddress());
	// prospectEntity.setEmail(prospectEntityDTO.getEmail());
	// prospectEntity.setEmploymentType(prospectEntityDTO.getEmploymentType());
	// prospectEntity.setEmployerName(prospectEntityDTO.getEmployerName());
	// prospectEntity.setDesignation(prospectEntityDTO.getDesignation());
	// prospectEntity.setProfession(prospectEntityDTO.getProfession());
	// prospectEntity.setMonthlyIncome(prospectEntityDTO.getMonthlyIncome());
	// prospectEntity.setBizNature(prospectEntityDTO.getBizNature());
	// prospectEntity.setFtCountry1(prospectEntityDTO.getFtCountry1());
	// prospectEntity.setFtCountry2(prospectEntityDTO.getFtCountry2());
	// prospectEntity.setFtCountry3(prospectEntityDTO.getFtCountry3());
	// prospectEntity.setExpectedCshGtSegLmt(prospectEntityDTO.getExpectedCshGtSegLmt());
	// prospectEntity.setSolicitationChannel(prospectEntityDTO.getSolicitationChannel());
	// prospectEntity.setInitDepAmtGtSegLmt(prospectEntityDTO.getInitDepAmtGtSegLmt());
	// prospectEntity.setGeographicStatus(prospectEntityDTO.getGeographicStatus());
	// prospectEntity.setPepStatus(prospectEntityDTO.getPepStatus());
	prospectEntity.setRemarks(prospectEntityDTO.getRemarks());
	// prospectEntity.setContactChannel(prospectEntityDTO.getContactChannel());
	// prospectEntity.setProspectStatus(prospectEntityDTO.getProspectStatus());
	// prospectEntity.setAssignedTo(prospectEntityDTO.getAssignedTo());
	// prospectEntity.setMobileNoISDCode(prospectEntityDTO.getMobileNoISDCode());
	// prospectEntity.setHomeNoISDCode(prospectEntityDTO.getHomeNoISDCode());
	return prospectEntity;

    }

    private ProspectInfoEntity getProspectInfoEntity(com.barclays.bmg.dto.ProspectInfoEntity prospectInfoEntityDTO) {
	ProspectInfoEntity prospectInfoEntity = new ProspectInfoEntity();
	prospectInfoEntity.setCreateBy(prospectInfoEntityDTO.getCreateBy());
	// prospectInfoEntity.setCreateOn(prospectInfoEntityDTO.getCreateOn());
	// prospectInfoEntity.setModifiedOn(prospectInfoEntityDTO.getModifiedOn());
	prospectInfoEntity.setOwnerName(prospectInfoEntityDTO.getOwnerName());
	return prospectInfoEntity;

    }
}