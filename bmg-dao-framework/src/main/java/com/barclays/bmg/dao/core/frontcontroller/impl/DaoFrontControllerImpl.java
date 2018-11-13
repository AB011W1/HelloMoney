package com.barclays.bmg.dao.core.frontcontroller.impl;

import java.util.Map;

import org.springframework.util.Assert;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dao.core.frontcontroller.DAOFrontController;
import com.barclays.bmg.dao.core.processing.Controller;

public class DaoFrontControllerImpl implements DAOFrontController {

	private Map<String, Controller> controllers;

	public Object execute(WorkContext workContext) throws Exception {

		Object returnObject = null;

		returnObject = getController(workContext).handleRequest(workContext);

		return returnObject;
	}

	private Controller getController(WorkContext workContext) {

		String methodName = ((DAOContext) workContext).getMethodName();

		Assert.notNull(controllers,
				"No Controller has been configured for the method "
						+ methodName);
		Controller controller = controllers.get(methodName.toUpperCase());
		Assert.notNull(controller, "No controllers configured for the method "
				+ methodName);
		return controller;
	}

	public Map<String, Controller> getControllers() {
		return controllers;
	}

	public void setControllers(Map<String, Controller> controllers) {
		this.controllers = controllers;
	}

}
