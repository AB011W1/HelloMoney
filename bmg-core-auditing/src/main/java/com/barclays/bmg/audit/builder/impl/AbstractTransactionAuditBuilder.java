package com.barclays.bmg.audit.builder.impl;

import com.barclays.bmg.audit.builder.BMGTransactionAuditBuilder;
import com.barclays.bmg.audit.dto.FieldDataDTO;
import com.barclays.bmg.audit.dto.ScreenDataDTO;
import com.barclays.bmg.constants.BMGConstants;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.service.MessageResourceService;
import com.barclays.bmg.service.request.MessageResourceServiceRequest;
import com.barclays.bmg.service.response.MessageResourceServiceResponse;

public abstract class AbstractTransactionAuditBuilder implements BMGTransactionAuditBuilder {

    private static final String SCREEN_ELEM = "screen";
    private static final String BMB = "BMB";
    private static final String BEM = "BEM";

    private MessageResourceService messageResourceService;

    private static final String FIELD_ELEM = "field";

    protected String buildData(ScreenDataDTO screenData) {
	StringBuffer buffer = new StringBuffer();
	if (screenData != null) {
	    buffer.append("<");
	    buffer.append(SCREEN_ELEM);
	    buffer.append(" id=\"");
	    buffer.append(screenData.getScreenId());
	    buffer.append("\">");
	    for (FieldDataDTO field : screenData.getFieldList()) {
		if (field.getValue() != null) {
		    buffer.append("<");
		    buffer.append(FIELD_ELEM);
		    buffer.append(" id=\"");
		    buffer.append(field.getFieldId());
		    buffer.append("\" value=\"");
		    buffer.append(field.getValue());
		    buffer.append("\"/>");
		}
	    }
	    buffer.append("</");
	    buffer.append(SCREEN_ELEM);
	    buffer.append(">");
	}

	return buffer.toString();
    }

    protected String getErrorMessage(String errorCode) {
	String errorMessage = "";

	String bmbErrCode = errorCode;

	if (bmbErrCode != null) {
	    if (!bmbErrCode.isEmpty() && bmbErrCode.contains(BMB)) {
		errorMessage = getMessage(bmbErrCode, errorCode);
	    } else {
		errorMessage = getMessage(bmbErrCode);
	    }
	} else {
	    errorMessage = getMessage(BEM);
	}

	return errorMessage;
    }

    private String getMessage(String resCde) {
	MessageResourceServiceRequest messageRequest = new MessageResourceServiceRequest();
	messageRequest.setContext(BMBContextHolder.getContext());
	messageRequest.setMessageKey(resCde);

	MessageResourceServiceResponse messageResponse = messageResourceService.getMessageDescByKey(messageRequest);
	return messageResponse.getMessageDesc();
    }

    private String getMessage(String resCde, String defMsg) {
	MessageResourceServiceRequest messageRequest = new MessageResourceServiceRequest();
	messageRequest.setContext(BMBContextHolder.getContext());
	messageRequest.setMessageKey(resCde);
	MessageResourceServiceResponse messageResponse = messageResourceService.getMessageDescByKey(messageRequest);
	if (messageResponse != null) {
	    if (!defMsg.equals(BMGConstants.EMPTYSTR)) {
		return messageResponse.getMessageDesc() + "(" + defMsg + ")";
	    } else {
		return messageResponse.getMessageDesc();
	    }
	} else {
	    return defMsg;
	}
    }

    public MessageResourceService getMessageResourceService() {
	return messageResourceService;
    }

    public void setMessageResourceService(MessageResourceService messageResourceService) {
	this.messageResourceService = messageResourceService;
    }
}
