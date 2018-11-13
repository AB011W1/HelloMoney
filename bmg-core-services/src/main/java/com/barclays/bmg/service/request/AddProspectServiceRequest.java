package com.barclays.bmg.service.request;

import com.barclays.bmg.context.RequestContext;
import com.barclays.bmg.dto.ProspectEntity;
import com.barclays.bmg.dto.ProspectInfoEntity;

public class AddProspectServiceRequest extends RequestContext {

    private static final long serialVersionUID = 8275286881812542291L;

    private ProspectInfoEntity prospectInfoEntity;

    private ProspectEntity prospectEntity;

    public ProspectInfoEntity getProspectInfoEntity() {
	return prospectInfoEntity;
    }

    public void setProspectInfoEntity(ProspectInfoEntity prospectInfoEntity) {
	this.prospectInfoEntity = prospectInfoEntity;
    }

    public ProspectEntity getProspectEntity() {
	return prospectEntity;
    }

    public void setProspectEntity(ProspectEntity prospectEntity) {
	this.prospectEntity = prospectEntity;
    }

}
