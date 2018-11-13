package com.barclays.bmg.service.impl;

import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.dao.MessageResourceDAO;
import com.barclays.bmg.service.MessageResourceService;
import com.barclays.bmg.service.request.MessageResourceServiceRequest;
import com.barclays.bmg.service.response.MessageResourceServiceResponse;

public class MessageResourceServiceImpl implements MessageResourceService {

    private MessageResourceDAO messageResourceDAO;

    @Override
    public MessageResourceServiceResponse getMessageDescByKey(MessageResourceServiceRequest request) {
	MessageResourceServiceResponse response = messageResourceDAO.getMessageDescByKey(request);
	if (response == null) {
	    response = getDefaultMessageResponse(request);
	}
	return response;
    }

    public MessageResourceDAO getMessageResourceDAO() {
	return messageResourceDAO;
    }

    public void setMessageResourceDAO(MessageResourceDAO messageResourceDAO) {
	this.messageResourceDAO = messageResourceDAO;
    }

    private MessageResourceServiceResponse getDefaultMessageResponse(MessageResourceServiceRequest request) {
	request.setMessageKey(ResponseCodeConstants.EXCEPTION_RES_CODE);
	MessageResourceServiceResponse response = messageResourceDAO.getMessageDescByKey(request);

	if (response == null) {
	    response = new MessageResourceServiceResponse();
	    response.setMessageDesc("NO MESSAGE FOUND FOR THIS KEY Or Values are not properly set in context");
	}
	return response;
    }

}
