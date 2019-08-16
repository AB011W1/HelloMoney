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
import com.barclays.bmg.operation.accountdetails.BondDetailsOperation;
import com.barclays.bmg.operation.accountdetails.request.BondDetailsOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.BondDetailsOperationResponse;
import com.barclays.bmg.utils.BMBCommonUtility;

public class BondDetailsController extends BMBAbstractController {

    private BondDetailsOperation bondDetailsOperation;
    private BMBJSONBuilder bmbJsonBuilder;

    public void setBondDetailsOperation(BondDetailsOperation bondDetailsOperation) {
	this.bondDetailsOperation = bondDetailsOperation;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.bmg.mvc.controller.auth.BMBAbstractController#handleRequestInternal1(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     * 
     * 1. Retrieve Bond Details List 2. make JSON response
     */
    @Override
    protected BMBBaseResponseModel handleRequestInternal1(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

	// ---- Making Operation Request object
	BondDetailsOperationRequest bondDetailsOperationRequest = makeRequest(httpRequest);

	bondDetailsOperationRequest.setAsssetClass(ActivityConstant.BOND);

	// ---- Making Operation Response Object
	BondDetailsOperationResponse bondDetailsOperationResponse = bondDetailsOperation.retrieveBondListByAssetClass(bondDetailsOperationRequest);

	return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(bondDetailsOperationResponse);

    }

    /**
     * make BondDetailsOperationRequest
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private BondDetailsOperationRequest makeRequest(HttpServletRequest request) {

	BondDetailsOperationRequest bondDetailsOperationRequest = new BondDetailsOperationRequest();

	Context context = createContext(request);

	context.setActivityId(ActivityConstant.ACT_INVEST_BONDS);

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

	bondDetailsOperationRequest.setContext(context);

	return bondDetailsOperationRequest;

    }

    public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
	this.bmbJsonBuilder = bmbJsonBuilder;
    }

    public BMBJSONBuilder getBmbJsonBuilder() {
	return bmbJsonBuilder;
    }

    @Override
    protected String getActivityId() {
	return ActivityConstant.ACT_INVEST_BONDS;
    }

}
