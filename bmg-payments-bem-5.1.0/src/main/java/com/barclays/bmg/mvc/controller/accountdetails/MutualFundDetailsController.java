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
import com.barclays.bmg.operation.accountdetails.MutualFundDetailsOperation;
import com.barclays.bmg.operation.accountdetails.request.MutualFundDetailsOperationRequest;
import com.barclays.bmg.operation.accountdetails.response.MutualFundDetailsOperationResponse;
import com.barclays.bmg.utils.BMBCommonUtility;

public class MutualFundDetailsController extends BMBAbstractController {

    private MutualFundDetailsOperation mutualFundDetailsOperation;
    private BMBJSONBuilder bmbJsonBuilder;

    public void setMutualFundDetailsOperation(MutualFundDetailsOperation mutualFundDetailsOperation) {
	this.mutualFundDetailsOperation = mutualFundDetailsOperation;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.barclays.bmg.mvc.controller.auth.BMBAbstractController#handleRequestInternal1(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     * 
     * 1. Retrieve Mutual Fund list 2. make JSON response model
     */
    @Override
    protected BMBBaseResponseModel handleRequestInternal1(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {

	MutualFundDetailsOperationRequest mutualFundDetailsOperationRequest = makeRequest(httpRequest);

	mutualFundDetailsOperationRequest.setAsssetClass(ActivityConstant.MUTUAL_FUND);

	MutualFundDetailsOperationResponse mutualFundDetlOperationResp = mutualFundDetailsOperation
		.retrieveMutualFundListByAssetClass(mutualFundDetailsOperationRequest);

	return (BMBBaseResponseModel) bmbJsonBuilder.createJSONResponse(mutualFundDetlOperationResp);

    }

    /**
     * make MutualFundDetailsOperationRequest
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private MutualFundDetailsOperationRequest makeRequest(HttpServletRequest request) {

	MutualFundDetailsOperationRequest mutualFundDetailsOperationRequest = new MutualFundDetailsOperationRequest();

	Context context = createContext(request);

	context.setActivityId(ActivityConstant.ACT_INVEST_MUTUALFUNDS);

	Map<String, Object> userMap = getUserMapFromSession(request);

	Map<String, String> ppMap = (Map<String, String>) getSessionAttribute(request, SessionConstant.SESSION_PP_MAP);

	context.setBusinessId((String) userMap.get(SessionConstant.SESSION_BUSINESS_ID));
	context.setCountryCode((String) userMap.get(SessionConstant.SESSION_COUNTRY_CODE));
	context.setLanguageId((String) userMap.get(SessionConstant.SESSION_LANGUAGE_ID));
	context.setSystemId((String) userMap.get(SessionConstant.SESSION_SYSTEM_ID));

	context.setCustomerId((String) userMap.get(SessionConstant.SESSION_CUSTOMER_ID));
	context.setUserId((String) userMap.get(SessionConstant.SESSION_USER_ID));

	context.setActivityRefNo(BMBCommonUtility.generateRefNo());

	context.setPpMap(ppMap);

	mutualFundDetailsOperationRequest.setContext(context);

	return mutualFundDetailsOperationRequest;

    }

    public void setBmbJsonBuilder(BMBJSONBuilder bmbJsonBuilder) {
	this.bmbJsonBuilder = bmbJsonBuilder;
    }

    public MutualFundDetailsOperation getMutualFundDetailsOperation() {
	return mutualFundDetailsOperation;
    }

    public BMBJSONBuilder getBmbJsonBuilder() {
	return bmbJsonBuilder;
    }

    @Override
    protected String getActivityId() {

	return ActivityConstant.ACT_INVEST_MUTUALFUNDS;
    }

}
