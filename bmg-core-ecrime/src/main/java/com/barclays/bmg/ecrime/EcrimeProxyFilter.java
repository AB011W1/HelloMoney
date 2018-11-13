package com.barclays.bmg.ecrime;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.barclays.bmg.utils.BeanFactoryWrapper;

/* *************************** Revision History *********************************
 * Version        Author          Date                     Description
 * 0.1            Tony Ni        9 Feb 2011                  Initial version
 *
 *
 ********************************************************************************/

/**
 * Proxy for a standard Servlet 2.3 Filter, delegating to a Spring-managed bean that implements the Filter interface. support business specific filter
 * 
 * @author Tony Ni
 */
public class EcrimeProxyFilter extends DelegatingFilterProxy {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
	WebApplicationContext wac = findWebApplicationContext();
	if (wac == null) {
	    throw new IllegalStateException("No WebApplicationContext found: no ContextLoaderListener registered?");
	}
	// Using Bean FactoryWrapper, enable supporting for business specific
	// filter by Name.
	BeanFactoryWrapper wrapper = new BeanFactoryWrapper(wac);
	Filter delegateToUse = (Filter) wrapper.getBean(getTargetBeanName(), Filter.class);

	// Let the delegate perform the actual doFilter operation.
	delegateToUse.doFilter(request, response, filterChain);
    }

}
