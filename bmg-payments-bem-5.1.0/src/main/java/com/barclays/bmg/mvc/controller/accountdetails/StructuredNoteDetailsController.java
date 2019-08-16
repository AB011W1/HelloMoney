package com.barclays.bmg.mvc.controller.accountdetails;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.barclays.bmg.constants.ActivityConstant;
import com.barclays.bmg.constants.SessionConstant;
import com.barclays.bmg.context.Context;
import com.barclays.bmg.json.model.builder.BMBJSONBuilder;
import com.barclays.bmg.json.response.model.BMBBaseResponseModel;
import com.barclays.bmg.mvc.controller.auth.BMBAbstractController;
import com.barclays.bmg.operation.accountdetails.StructuredNoteDetailsOperation;
import com.barclays.bmg.operation.accountdetails.request.StructuredNoteDetailsOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.StructuredNoteDetailsOperationResponse;
import com.barclays.bmg.utils.BMBCommonUtility;

public class StructuredNoteDetailsController extends BMBAbstractController {

    private StructuredNoteDetailsOperation structuredNoteDetailsOperation;
    private BMBJSONBuilder bmbJsonBuilder;

    public void setStructuredNoteDetailsOperation(StructuredNoteDetailsOperation structuredNoteDetailsOperation) {
	this.structuredNoteDetailsOperation = structuredNoteDetailsOperation;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.bmg.mvc.controller.auth.BMBAbstractController#handleRequestInternal1(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     * 
     * 1. Retrieve Structured Notes List 2. make JSON response
     */
    @Override
    protected BMBBaseResponseModel handleRequestInternal1(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

	// ---- Making Operation Request object
	StructuredNoteDetailsOperationRequest strutNoteDetailsOperationRequest = makeRequest(httpRequest);

	strutNoteDetailsOperationRequest.setAsssetClass(ActivityConstant.STRUCTURED_NOTE);

	// ---- Making Operation Response Object
	StructuredNoteDetailsOperationResponse strutNoteDetailsOperationResp = structuredNoteDetailsOperation
		.retrieveStructureNoteListByAssetClass(strutNoteDetailsOperationRequest);

	return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(strutNoteDetailsOperationResp);

    }

    /**
     * make StructuredNoteDetailsOperationRequest
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private StructuredNoteDetailsOperationRequest makeRequest(HttpServletRequest request) {

	StructuredNoteDetailsOperationRequest strutNoteDetailsOperationRequest = new StructuredNoteDetailsOperationRequest();

	Context context = createContext(request);

	context.setActivityId(ActivityConstant.ACT_INVEST_STRUCTUREDNOTES);

	Map<String, Object> userMap = getUserMapFromSession(request);

	Map<String, String> ppMap = (Map<String, String>) getSessionAttribute(request, SessionConstant.SESSION_PP_MAP);

	context.setPpMap(ppMap);

	context.setBusinessId((String) userMap.get(SessionConstant.SESSION_BUSINESS_ID));
	context.setCountryCode((String) userMap.get(SessionConstant.SESSION_COUNTRY_CODE));
	context.setLanguageId((String) userMap.get(SessionConstant.SESSION_LANGUAGE_ID));
	context.setSystemId((String) userMap.get(SessionConstant.SESSION_SYSTEM_ID));

	context.setCustomerId((String) userMap.get(SessionConstant.SESSION_CUSTOMER_ID));
	context.setUserId((String) userMap.get(SessionConstant.SESSION_USER_ID));

	context.setActivityRefNo(BMBCommonUtility.generateRefNo());

	strutNoteDetailsOperationRequest.setContext(context);

	return strutNoteDetailsOperationRequest;

    }

    public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
	this.bmbJsonBuilder = bmbJsonBuilder;
    }

    public BMBJSONBuilder getBmbJsonBuilder() {
	return bmbJsonBuilder;
    }

    @Override
    protected String getActivityId() {

	return ActivityConstant.ACT_INVEST_STRUCTUREDNOTES;
    }

}
