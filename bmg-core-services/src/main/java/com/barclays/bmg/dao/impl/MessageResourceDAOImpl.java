package com.barclays.bmg.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.dao.MessageResourceDAO;
import com.barclays.bmg.dto.MessageResCacheDTO;
import com.barclays.bmg.service.request.MessageResourceServiceRequest;
import com.barclays.bmg.service.response.MessageResourceServiceResponse;

public class MessageResourceDAOImpl extends BaseDAOImpl implements MessageResourceDAO {

    private static final String BUSINESS_ID = "businessId";
    private static final String SYSTEM_ID = "systemId";
    private static final String MESSAGE_KEY = "messageKey";
    private static final String LANGUAGE_ID = "langId";
    private static final String GET_MESSAGE_BY_KEY = "findMessageResByKey";

    @Override
    public MessageResourceServiceResponse getMessageDescByKey(MessageResourceServiceRequest request) {

	Map<String, String> parameterMap = new HashMap<String, String>();
	parameterMap.put(BUSINESS_ID, request.getContext().getBusinessId());
	parameterMap.put(SYSTEM_ID, request.getContext().getSystemId());
	parameterMap.put(LANGUAGE_ID, request.getContext().getLanguageId());
	parameterMap.put(MESSAGE_KEY, request.getMessageKey());
	MessageResCacheDTO messageResCacheDTO = (MessageResCacheDTO) this.queryForObject(GET_MESSAGE_BY_KEY, parameterMap);

	MessageResourceServiceResponse response = null;
	if (messageResCacheDTO != null) {
	    response = new MessageResourceServiceResponse();
	    response.setMessageDesc(messageResCacheDTO.getMessageValue());

	    // if (messageResCacheDTO != null) {
	    String category = messageResCacheDTO.getCategory();
	    if (ErrorCodeConstant.MSG_CATEGORY_ERROR.equals(category)) {
		response.setErrTyp(ErrorCodeConstant.RECOVERABLE_ERROR);
	    } else if (ErrorCodeConstant.MSG_CATEGORY_WARN.equals(category)) {
		response.setErrTyp(ErrorCodeConstant.WARNING);
	    } else if (ErrorCodeConstant.MSG_CATEGORY_FATAL.equals(category)) {
		response.setErrTyp(ErrorCodeConstant.UNRECOVERABLE_ERROR);
	    } else if (ErrorCodeConstant.MSG_CATEGORY_INFO.equals(category)) {
		response.setErrTyp(ErrorCodeConstant.INFO);
	    } else {
		response.setErrTyp(ErrorCodeConstant.UNRECOVERABLE_ERROR);
	    }
	    // }
	}
	return response;
    }

}
