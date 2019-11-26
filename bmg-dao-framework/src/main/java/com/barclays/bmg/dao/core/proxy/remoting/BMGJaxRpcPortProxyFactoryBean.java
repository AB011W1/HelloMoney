package com.barclays.bmg.dao.core.proxy.remoting;

import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.JAXRPCException;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.Stub;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.soap.SOAPFaultException;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.remoting.RemoteLookupFailureException;
import org.springframework.remoting.RemoteProxyFailureException;
import org.springframework.remoting.jaxrpc.JaxRpcPortProxyFactoryBean;
import org.springframework.remoting.jaxrpc.JaxRpcSoapFaultException;
import org.springframework.remoting.rmi.RmiClientInterceptorUtils;
import org.springframework.util.ReflectionUtils;

import com.barclays.bmg.context.BMGContextHolder;
import com.barclays.bmg.dao.SystemParameterDAO;
import com.barclays.bmg.dao.core.proxy.handler.WebServiceLogHandler;
import com.barclays.bmg.dao.core.proxy.handler.WebServiceLogTimeHandler;
import com.barclays.bmg.helper.DisasterRecoveryHelper;

public class BMGJaxRpcPortProxyFactoryBean extends JaxRpcPortProxyFactoryBean implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    private static Map<String, String> endpointKeyMap = new HashMap<String, String>();
    private static Map<String, String> endpointMap = new HashMap<String, String>();
    private static Map<String, Remote> stubCache = new HashMap<String, Remote>();
    private static int timeout = Integer.MAX_VALUE;
    private boolean debug = true;
    private String daoBeanName;
    private String endpointAddressName;
    private String cacheKey;
    private SystemParameterDAO systemParameterDAO;
    public static final String DR_KEY_PREFIX = "DR_";
    public static final String SIT_KEY_PREFIX = "SIT_";
    private static final Logger LOGGER = Logger.getLogger(BMGJaxRpcPortProxyFactoryBean.class);

    public SystemParameterDAO getSystemParameterDAO() {
	return systemParameterDAO;
    }

    public void setSystemParameterDAO(SystemParameterDAO systemParameterDAO) {
	this.systemParameterDAO = systemParameterDAO;
    }

    @Override
    public void setApplicationContext(ApplicationContext appCtx) throws BeansException {
	this.applicationContext = appCtx;

    }

    public ApplicationContext getApplicationContext() {
	return applicationContext;
    }

    public static Map<String, String> getEndpointKeyMap() {
	return endpointKeyMap;
    }

    public static void setEndpointKeyMap(Map<String, String> endpointKeyMap) {
	BMGJaxRpcPortProxyFactoryBean.endpointKeyMap = endpointKeyMap;
    }

    public static Map<String, String> getEndpointMap() {
	return endpointMap;
    }

    public static void setEndpointMap(Map<String, String> endpointMap) {
	BMGJaxRpcPortProxyFactoryBean.endpointMap = endpointMap;
    }

    public static Map<String, Remote> getStubCache() {
	return stubCache;
    }

    public static void setStubCache(Map<String, Remote> stubCache) {
	BMGJaxRpcPortProxyFactoryBean.stubCache = stubCache;
    }

    public static int getTimeout() {
	return timeout;
    }

    public static void setTimeout(int timeout) {
	BMGJaxRpcPortProxyFactoryBean.timeout = timeout;
    }

    public boolean isDebug() {
	return debug;
    }

    public void setDebug(boolean debug) {
	this.debug = debug;
    }

    public String getDaoBeanName() {
	return daoBeanName;
    }

    public void setDaoBeanName(String daoBeanName) {
	this.daoBeanName = daoBeanName;
    }

    public String getEndpointAddressName() {
	return endpointAddressName;
    }

    public void setEndpointAddressName(String endpointAddressName) {
	this.endpointAddressName = endpointAddressName;
    }

    public String getCacheKey() {
	return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
	this.cacheKey = cacheKey;
    }

    @SuppressWarnings("unchecked")
    public void postProcessJaxRpcService(Service service) {

	super.postProcessJaxRpcService(service);

	QName port = new QName(this.getNamespaceUri(), this.getPortName());
	service.getHandlerRegistry().getHandlerChain(port).add(new HandlerInfo(WebServiceLogHandler.class, null, null));
	service.getHandlerRegistry().getHandlerChain(port).add(new HandlerInfo(WebServiceLogTimeHandler.class, null, null));
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
	if (AopUtils.isToStringMethod(invocation.getMethod())) {
	    return new StringBuilder().append("JAX-RPC proxy for port [").append(getPortName()).append("] of service [").append(getServiceName())
		    .append("]").toString();
	}
	return doInvoke(invocation);
    }

    @SuppressWarnings("deprecation")
	public Object doInvoke(MethodInvocation invocation) throws Throwable {
	Remote stub = getStub(invocation);
	try {
	    return doInvoke(invocation, stub);
	} catch (RemoteException ex) {
	    throw handleRemoteException(invocation.getMethod(), ex);
	} catch (SOAPFaultException ex) {
	    throw new JaxRpcSoapFaultException(ex);
	} catch (JAXRPCException ex) {
	    throw new RemoteProxyFailureException("Invalid JAX-RPC call configuration", ex);
	}

    }

    private Remote getStub(MethodInvocation invocation) {

	String request = invocation.getMethod().getName();
	String endpointKey = endpointKeyMap.get(request);
	LOGGER.debug("endpointKey before is null: " + endpointKey);

	if (endpointKey == null) {
		String paramId = this.endpointAddressName;
		
		
		if(DisasterRecoveryHelper.BEM_DR_FLAG) {
			if (paramId.startsWith(DisasterRecoveryHelper.DR_KEY_PREFIX)
					|| paramId.startsWith(DisasterRecoveryHelper.SIT_KEY_PREFIX)) {
				this.endpointAddressName = paramId;
			}
			else
			{
				this.endpointAddressName = DR_KEY_PREFIX + paramId;
			}
		}
		
		else if(DisasterRecoveryHelper.BEM_SIT_FLAG)
		{
			if (paramId.startsWith(DisasterRecoveryHelper.DR_KEY_PREFIX)
					|| paramId.startsWith(DisasterRecoveryHelper.SIT_KEY_PREFIX)) {
				this.endpointAddressName = paramId;
			}
			else
			{
				this.endpointAddressName = SIT_KEY_PREFIX + paramId;
			}
		}
		
	    /* ------------- Disaster Recovery Implementation START ---------------- */
	   /* String paramId = this.endpointAddressName;
	    String tempStr = paramId;
	    LOGGER.debug("paramId before handleDRParameter: " + paramId);
	    paramId = DisasterRecoveryHelper.handleDrParmeter(paramId);
	    LOGGER.debug("paramId post handleDRParameter: " + paramId);
	    if (null != tempStr && !tempStr.equals(paramId)) { // is DR
		LOGGER.debug("Overwrite the paramId " + paramId);
		this.endpointAddressName = paramId; // --- Overwrite the paramId, if DR is enable

	    } else {
		LOGGER.debug("null != tempStr && !tempStr.equals(paramId) is false");
		this.endpointAddressName = tempStr;
	    }*/

	    /* ------------- Disaster Recovery Implementation END ---------------- */

	    endpointKey = this.endpointAddressName;
	    LOGGER.debug("endpointKey: " + endpointKey);
	}
	String cachedKey = getCacheKey();

	Remote stub = stubCache.get(cachedKey);
	if (stub == null) {
	    synchronized (stubCache) {
		if (stubCache.get(cachedKey) == null) {
		    if (getPortName() == null) {
			throw new IllegalArgumentException("Property 'portName' is required");
		    }

		    QName portQName = getQName(getPortName());
		    try {
			Service service = createJaxRpcService();
			Class portInterface = getPortInterface();
			if (portInterface != null && !alwaysUseJaxRpcCall()) {
			    stub = service.getPort(portQName, portInterface);

			    if (!(stub instanceof Stub)) {
				throw new RemoteLookupFailureException(new StringBuilder().append("Port stub of class [").append(
					stub.getClass().getName()).append(
					"] is not a valid JAX-RPC stub: it does not implement interface [javax.xml.rpc.Stub]").toString());
			    }
			    Stub _stub = (Stub) stub;
			    _stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, findEndpointAddress());
			    setTimeOut(_stub);
			    stubCache.put(cachedKey, stub);
			}

		    } catch (ServiceException ex) {
			throw new RemoteLookupFailureException(new StringBuilder().append("Failed to initialize service for JAX-RPC port [").append(
				portQName).append("]").toString(), ex);
		    }
		}
	    }
	}
	return stub;
    }

    private void setTimeOut(Stub stub) {
	try {
	    com.ibm.ws.webservices.engine.client.Stub _stub = (com.ibm.ws.webservices.engine.client.Stub) stub;
	    _stub.setTimeout(timeout);
	} catch (Exception e) {

	}
    }

    private String findEndpointAddress() {
	return (String) BMGContextHolder.getLogContext().getContextMap().get(endpointAddressName);
    }

    public void prepare() throws RemoteLookupFailureException {

    }

    protected Throwable handleRemoteException(Method method, RemoteException ex) {
	boolean isConnectFailure = isConnectFailure(ex);
	Throwable cause = ex.getCause();
	if (cause != null && ReflectionUtils.declaresException(method, cause.getClass())) {
	    return ex.getCause();
	} else {
	    return RmiClientInterceptorUtils.convertRmiAccessException(method, ex, isConnectFailure, getPortName());
	}
    }

}
