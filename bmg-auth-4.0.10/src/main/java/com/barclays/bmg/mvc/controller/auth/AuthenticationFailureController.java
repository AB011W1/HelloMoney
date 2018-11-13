package com.barclays.bmg.mvc.controller.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.barclays.bmg.constants.ErrorCodeConstant;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.json.response.BMBPayload;
import com.barclays.bmg.json.response.BMBPayloadHeader;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;

public class AuthenticationFailureController extends BMBAbstractController {

    private boolean serVerOne = false;

    @Override
    protected BMBBaseResponseModel handleRequestInternal1(HttpServletRequest request, HttpServletResponse response) throws Exception {

	HttpSession session = request.getSession(false);
	String serVersion = (String) session.getAttribute(SessionConstant.SESSION_BMG_SERVICE_VERSION);
	if (!StringUtils.isEmpty(serVersion)) {
	    if ("1.0".equals(serVersion.trim())) {
		serVerOne = true;
	    } else {
		serVerOne = false;
	    }
	}
	BMBPayload returnBMBPayloadResponse = new BMBPayload();

	BMBPayloadHeader bmbPayloadHeader = new BMBPayloadHeader();
	bmbPayloadHeader.setResId("RES0199");
	bmbPayloadHeader.setResCde("BMB0199");
	bmbPayloadHeader.setResMsg("Your session has expired, Please login first");
	bmbPayloadHeader.setSerVer("2.0");

	returnBMBPayloadResponse.setPayHdr(bmbPayloadHeader);
	returnBMBPayloadResponse.setPayData(null);

	BMBBaseResponseModel responseModel = returnBMBPayloadResponse;
	if (isSerVerOne()) {
	    responseModel = createRel1ErrorResponseModel(bmbPayloadHeader.getResMsg(), ErrorCodeConstant.RELOGIN_NEEDED_ERROR);
	}
	return responseModel;

    }

    @Override
    protected String getActivityId() {
	// TODO Auto-generated method stub
	return null;
    }

    public boolean isSerVerOne() {
	return serVerOne;
    }

    public void setSerVerOne(boolean serVerOne) {
	this.serVerOne = serVerOne;
    }

}
