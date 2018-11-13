package com.barclays.bmg.dao.core.processing;

import com.barclays.bmg.dao.core.context.WorkContext;


public interface Controller {

	Object handleRequest(WorkContext workContext) throws Exception;

}
