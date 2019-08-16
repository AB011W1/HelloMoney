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
/**
 * Package name is com.barclays.bmg.dao.operation.accountservices
 * /
 */
package com.barclays.bmg.dao.operation.addprospect;

import com.barclays.bem.AddProspect.AddProspectRequest;
import com.barclays.bmg.dao.addprospect.adapter.AddProspectHeaderAdapter;
import com.barclays.bmg.dao.addprospect.adapter.AddProspectPayloadAdapter;
import com.barclays.bmg.dao.core.context.WorkContext;

public class AddProspectReqAdptOperation {

    private AddProspectHeaderAdapter addProspectHeaderAdapter;

    private AddProspectPayloadAdapter addProspectPayloadAdapter;

    public AddProspectRequest adaptRequest(WorkContext workContext) {
	AddProspectRequest request = new AddProspectRequest();

	request.setRequestHeader(addProspectHeaderAdapter.buildBemReqHeader(workContext));
	request.setAddProspectEntities(addProspectPayloadAdapter.adaptPayLoad(workContext));

	return request;
    }

    public AddProspectHeaderAdapter getAddProspectHeaderAdapter() {
	return addProspectHeaderAdapter;
    }

    public void setAddProspectHeaderAdapter(AddProspectHeaderAdapter addProspectHeaderAdapter) {
	this.addProspectHeaderAdapter = addProspectHeaderAdapter;
    }

    public AddProspectPayloadAdapter getAddProspectPayloadAdapter() {
	return addProspectPayloadAdapter;
    }

    public void setAddProspectPayloadAdapter(AddProspectPayloadAdapter addProspectPayloadAdapter) {
	this.addProspectPayloadAdapter = addProspectPayloadAdapter;
    }

}