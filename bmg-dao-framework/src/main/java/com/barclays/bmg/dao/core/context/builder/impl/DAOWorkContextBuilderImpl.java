package com.barclays.bmg.dao.core.context.builder.impl;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.builder.DAOWorkContextBuilder;
import com.barclays.bmg.dao.core.context.impl.DAOContext;

public class DAOWorkContextBuilderImpl implements DAOWorkContextBuilder,
		ApplicationContextAware {

	private ApplicationContext applicationContext;

	public WorkContext createWorkContext(Object object) {

		DAOContext daoContext = new DAOContext();
		if (object instanceof MethodInvocation) {
			MethodInvocation mi = (MethodInvocation) object;
			daoContext.setApplicationContext(this.applicationContext);
			daoContext.setArguments(mi.getArguments());
			daoContext.setMethodName(mi.getMethod().getName());

		}
		return daoContext;
	}

	public void setApplicationContext(ApplicationContext apc)
			throws BeansException {
		this.applicationContext = apc;

	}

}
