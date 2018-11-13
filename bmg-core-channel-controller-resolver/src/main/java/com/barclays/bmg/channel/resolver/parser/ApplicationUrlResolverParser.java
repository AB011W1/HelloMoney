package com.barclays.bmg.channel.resolver.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.barclays.bmg.channel.resolver.config.ApplicationUrlResolverConfig;
import com.barclays.bmg.channel.resolver.config.FunctionUrlResolverConfig;

/**
 * 
 * @author e20027734
 * 
 *        <cr:application-url-resolver id="1.0" >
 * 
 *         <cr:function-url-resolver id="login" opCde="OP0100"> <cr:parameter actual="username" change-to="usrNam" /> <cr:parameter actual="password"
 *         change-to="paswd" /> </cr:function-url-resolver> <cr:function-url-resolver id="login" svc-ver="1.0" opCde="OP0100"> <cr:parameter
 *         actual="username" change-to="usrNam" /> <cr:parameter actual="password" change-to="paswd" />
 * 
 *         </cr:function-url-resolver>
 * 
 *         <cr:function-url-resolver id="login" svc-ver="1.0" opCde="OP0100"> <cr:parameter actual="username" change-to="usrNam" /> <cr:parameter
 *         actual="password" change-to="paswd" /> </cr:function-url-resolver>
 * 
 *         </cr:application-url-resolver>
 */
public class ApplicationUrlResolverParser extends AbstractBeanDefinitionParser {

    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext arg1) {
	List<Element> controllerElementList = DomUtils.getChildElementsByTagName(element, "function-url-resolver");

	BeanDefinitionBuilder controlDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(ApplicationUrlResolverConfig.class);

	Map<String, FunctionUrlResolverConfig> fnMap = new HashMap<String, FunctionUrlResolverConfig>();

	if (controllerElementList != null && controllerElementList.size() > 0) {
	    for (Element controllerElement : controllerElementList) {
		String opCode = controllerElement.getAttribute("opCde");
		String id = controllerElement.getAttribute("id");
		NodeList nodes = controllerElement.getChildNodes();
		List<Element> parameterList = DomUtils.getChildElementsByTagName(controllerElement, "parameter-mapping");
		Map<String, String> attrMap = null;
		if (parameterList != null && parameterList.size() > 0) {
		    attrMap = new HashMap<String, String>();
		    for (Element parameter : parameterList) {
			String actual = parameter.getAttribute("actual");
			String change_to = parameter.getAttribute("change-to");
			attrMap.put(actual, change_to);
		    }

		}
		FunctionUrlResolverConfig functionUrlResolverConfig = new FunctionUrlResolverConfig();
		functionUrlResolverConfig.setId(id);
		functionUrlResolverConfig.setOpCde(opCode);
		functionUrlResolverConfig.setAttrMap(attrMap);
		fnMap.put(id, functionUrlResolverConfig);
	    }
	}
	controlDefinitionBuilder.addPropertyValue("functionMap", fnMap);
	return controlDefinitionBuilder.getBeanDefinition();
    }

}
