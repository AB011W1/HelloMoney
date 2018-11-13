package com.barclays.bmg.channel.resolver.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import com.barclays.bmg.channel.resolver.config.ControllerResolverConfig;
import com.barclays.bmg.channel.resolver.config.ServiceVersionControllerConfig;

/**
 * <controller-ref-resolver id="GHBBRBResolver"> <controller op-code="OP001" svc-ver="1.0" ref="loginController" default="true" /> <controller
 * op-code="OP001" svc-ver="2.0" ref="loginController" default="false"/> <controller op-code="OP001" svc-ver="3.0" ref="loginController"/> <controller
 * op-code="OP001" ref="loginController" /> </controller-ref-resolver>
 **/
public class ControllerResolverParser extends AbstractBeanDefinitionParser {

    @SuppressWarnings("unchecked")
    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext arg1) {

	List<Element> controllerElementList = DomUtils.getChildElementsByTagName(element, "controller");
	BeanDefinitionBuilder controlDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(ControllerResolverConfig.class);

	Map<String, ServiceVersionControllerConfig> seMap = new HashMap<String, ServiceVersionControllerConfig>();

	if (controllerElementList != null && controllerElementList.size() > 0) {
	    for (Element controllerElement : controllerElementList) {
		String opCode = controllerElement.getAttribute("op-code");
		String svcVern = controllerElement.getAttribute("svc-ver");
		String controllerRef = controllerElement.getAttribute("ref");
		String isDefault = controllerElement.getAttribute("default");

		ServiceVersionControllerConfig controllerResolverConfig = seMap.get(opCode);

		if (controllerResolverConfig == null) {
		    controllerResolverConfig = new ServiceVersionControllerConfig();
		    seMap.put(opCode, controllerResolverConfig);
		}

		controllerResolverConfig.addServiceControllerMapping(svcVern, controllerRef);

		if (isDefault != null && !"".equals(isDefault) && "true".equals(isDefault)) {
		    controllerResolverConfig.setDefaultControllerName(controllerRef);
		}

	    }
	}
	String parentResolver = element.getAttribute("extends");
	if (!StringUtils.isEmpty(parentResolver)) {
	    controlDefinitionBuilder.addPropertyValue("parentResolver", parentResolver);
	    controlDefinitionBuilder.addPropertyValue("hasParentResolver", Boolean.TRUE);
	} else {
	    controlDefinitionBuilder.addPropertyValue("hasParentResolver", Boolean.FALSE);
	}
	controlDefinitionBuilder.addPropertyValue("snvMapping", seMap);
	return controlDefinitionBuilder.getBeanDefinition();
    }

}
