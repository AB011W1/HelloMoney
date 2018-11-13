package com.barclays.bmg.exception.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.barclays.bmg.constants.BMGConstants;
import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.constants.ResponseCodeConstants;
import com.barclays.bmg.constants.ResponseIdConstants;
import com.barclays.bmg.constants.ResponseModelConstant;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.BMBContextHolder;
import com.barclays.bmg.exception.BMBDataAccessException;
import com.barclays.bmg.exception.FeatureBlackoutException;
import com.barclays.bmg.exception.JailBrokenException;
import com.barclays.bmg.json.model.JSONResponseError;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.json.response.BaseJSONResponseModel;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.service.MessageResourceService;
import com.barclays.bmg.service.request.MessageResourceServiceRequest;
import com.barclays.bmg.service.response.MessageResourceServiceResponse;

public class BMBSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {

    MessageResourceService messageResourceService;
    private static final String BMB = "BMB";
    private static final String BEM = "BEM";

    @Override
    protected ModelAndView getModelAndView(String viewName, Exception exception, HttpServletRequest request) {

	BMBPayload payload = new BMBPayload();

	BMBPayloadHeader payHdr = new BMBPayloadHeader();

	payHdr.setResId(ResponseIdConstants.EXCEPTION_RESPONSE_ID);
	payHdr.setSerVer(ResponseIdConstants.RESPONSE_SERVICE_VERSION);
	payHdr.setResCde(ResponseCodeConstants.EXCEPTION_RES_CODE);

	String resMsg = getMessage(ResponseCodeConstants.EXCEPTION_RES_CODE);
	String errType = ErrorCodeConstant.UNRECOVERABLE_ERROR;
	if (exception instanceof BMBDataAccessException) {
	    BMBDataAccessException dae = (BMBDataAccessException) exception;
	    String bmbErrCode = dae.getErrorCode();
	    String bmbErrMsg = dae.getMessage();

	    if (bmbErrCode != null) {
		// String errCdeMsg = BMGConstants.EMPTYSTR;
		if (!bmbErrCode.isEmpty() && bmbErrCode.contains(BMB)) {
		    payHdr.setResCde(bmbErrCode);
		    if (bmbErrMsg != null) {
			payHdr.setResMsg(getMessage(bmbErrCode, bmbErrMsg));
		    } else {
			payHdr.setResMsg(getMessage(bmbErrCode));
		    }
		} else {
		    payHdr.setResCde(BEM + bmbErrCode);
		    payHdr.setResMsg(getMessage(ErrorCodeConstant.BEM_MESSAGE_PREFIX + bmbErrCode));
		}

	    } else {
		payHdr.setResCde(ResponseCodeConstants.EXCEPTION_RES_CODE);
		payHdr.setResMsg(resMsg);

	    }
	    errType = ErrorCodeConstant.UNRECOVERABLE_ERROR;

	} else if (exception instanceof FeatureBlackoutException) {
	    payHdr.setResCde(ResponseCodeConstants.FEATURE_BLACKOUT_RES_CODE);
	    errType = ErrorCodeConstant.UNRECOVERABLE_ERROR;
	    payHdr.setResMsg(getMessage(ResponseCodeConstants.FEATURE_BLACKOUT_RES_CODE));
	} else if (exception instanceof JailBrokenException) {
	    String errCode = ((JailBrokenException) exception).getErrorCode();
	    payHdr.setResCde(errCode);
	    errType = ErrorCodeConstant.UNRECOVERABLE_ERROR;
	    payHdr.setResMsg(getMessage(errCode));
	} else {
	   payHdr.setResMsg(resMsg);
	    payHdr.setExpTrace(null);
	    errType = ErrorCodeConstant.UNRECOVERABLE_ERROR;
	}
	payload.setPayHdr(payHdr);
	/**
	 * Service Versioning for release one.
	 */
	BMBBaseResponseModel responseModel = payload;
	HttpSession httpSession = request.getSession(false);
	if (httpSession != null) {
	    String serVersion = (String) httpSession.getAttribute(SessionConstant.SESSION_BMG_SERVICE_VERSION);
	    if (!StringUtils.isEmpty(serVersion) && SessionConstant.VERSION_RELEASE_ONE.equals(serVersion)) {
		responseModel = createRel1ErrorResponseModel(payHdr.getResMsg(), errType);
	    }
	}
	return new ModelAndView(viewName, ResponseModelConstant.RESPONSE_MODEL_FAILURE, responseModel);
    }

    protected BaseJSONResponseModel createRel1ErrorResponseModel(String errorMsg, String errType) {
	BaseJSONResponseModel responseModel = new BaseJSONResponseModel();
	responseModel.setSuccess(false);
	JSONResponseError error = new JSONResponseError();
	error.addMessage(errorMsg);
	error.setJSONErrorType(errType);
	responseModel.setError(error);
	return responseModel;
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