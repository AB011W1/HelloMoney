package com.barclays.bmg.dao.core.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.builder.DAOWorkContextBuilder;
import com.barclays.bmg.dao.core.context.builder.impl.DAOWorkContextBuilderImpl;
import com.barclays.bmg.dao.core.frontcontroller.DAOFrontController;
import com.barclays.bmg.dao.core.frontcontroller.impl.DaoFrontControllerImpl;

public class DaoProxyFactoryBean implements MethodInterceptor,
		InitializingBean, FactoryBean {

	private String serviceInterface;
	private DAOFrontController daoFrontController = new DaoFrontControllerImpl();
	private DAOWorkContextBuilder workContextBuilder = new DAOWorkContextBuilderImpl();
	private Object daoProxy;

	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		Assert.notNull(workContextBuilder, "Will need a workcontext builder");

		WorkContext workContext = workContextBuilder
				.createWorkContext(methodInvocation);

		Assert.notNull(workContext,
		"No WorkContext is created");

		Assert.notNull(daoFrontController,
				"Frontcontroller needs to be configured");

		Object object = daoFrontController.execute(workContext);

		return object;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(serviceInterface, "Service Interface can not be null");
		daoProxy = ProxyFactory.getProxy(Class.forName(serviceInterface), this);
	}

	public Object getObject() throws Exception {
		// TODO Auto-generated method stub
		return daoProxy;
	}

	public Class getObjectType() {
		// TODO Auto-generated method stub
		return serviceInterface.getClass();
	}

	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getServiceInterface() {
		return serviceInterface;
	}

	public void setServiceInterface(String serviceInterface) {
		this.serviceInterface = serviceInterface;
	}

	public DAOFrontController getDaoFrontController() {
		return daoFrontController;
	}

	public void setDaoFrontController(DAOFrontController daoFrontController) {
		this.daoFrontController = daoFrontController;
	}

	public DAOWorkContextBuilder getWorkContextBuilder() {
		return workContextBuilder;
	}

	public void setWorkContextBuilder(DAOWorkContextBuilder workContextBuilder) {
		this.workContextBuilder = workContextBuilder;
	}

}
