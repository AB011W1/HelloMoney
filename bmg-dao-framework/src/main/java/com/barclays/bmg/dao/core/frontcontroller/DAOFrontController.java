package com.barclays.bmg.dao.core.frontcontroller;

import com.barclays.bmg.dao.core.context.WorkContext;

public interface DAOFrontController {


	Object execute(WorkContext workContext)throws Exception;

}
