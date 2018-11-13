package com.barclays.bmg.json.model.builder;

import com.barclays.bmg.context.ResponseContext;

public abstract class BMBMultipleResponseJSONBuilder extends BMBCommonJSONBuilder implements BMBJSONBuilder {

    @Override
    public Object createJSONResponse(ResponseContext responseContext) {
	// TODO Auto-generated method stub
	return createMultipleJSONResponse(responseContext);
    }

    public abstract Object createMultipleJSONResponse(ResponseContext... responseContexts);

}
