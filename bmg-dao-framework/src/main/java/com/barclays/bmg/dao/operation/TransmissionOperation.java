package com.barclays.bmg.dao.operation;

import com.barclays.bmg.dao.service.TransmissionService;

public class TransmissionOperation {

    private TransmissionService transmissionService;

    public Object sendAndReceive(Object object) throws Exception {
	return transmissionService.sendAndReceive(object);
    }

    public TransmissionService getTransmissionService() {
	return transmissionService;
    }

    public void setTransmissionService(TransmissionService transmissionService) {
	this.transmissionService = transmissionService;
    }

}
