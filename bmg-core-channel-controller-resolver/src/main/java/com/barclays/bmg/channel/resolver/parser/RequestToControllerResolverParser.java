package com.barclays.bmg.channel.resolver.parser;

import java.util.List;

import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import com.barclays.bmg.channel.resolver.config.RequestControllerMapperConfig;

/**
 * <request-to-controller-mapper> <resolver business-id="GHBRB" resolver-ref="GHBBRBResolver" /> <resolver business-id="BWBRB"
 * resolver-ref="BWBRBResolver" /> <resolver business-id="UAEBRB" resolver-ref="UAEBRBResolver" /> <resolver business-id="DEFAULT"
 * resolver-ref="DEFBRBResolver" /> </request-to-controller-mapper>
 * 
 * @author e20021898
 * 
 */
public class RequestToControllerResolverParser extends AbstractBeanDefinitionParser {

    @SuppressWarnings("unchecked")
    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext arg1) {

	List<Element> resolverElementList = DomUtils.getChildElementsByTagName(element, "resolver");
	BeanDefinitionBuilder controlDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(RequestControllerMapperConfig.class);

	ManagedMap seMap = new ManagedMap();

	if (resolverElementList != null && resolverElementList.size() > 0) {
	    for (Element resolverElement : resolverElementList) {
		String businessId = resolverElement.getAttribute("business-id");
		String resolverRef = resolverElement.getAttribute("resolver-ref");
		String isDefault = resolverElement.getAttribute("default");
		if (isDefault != null && !"".equals(isDefault) && "true".equals(isDefault)) {
		    seMap.put("DefaultResolver", new RuntimeBeanReference(resolverRef));
		}
		seMap.put(businessId, new RuntimeBeanReference(resolverRef));
	    }
	}
	controlDefinitionBuilder.addPropertyValue("conMap", seMap);
	return controlDefinitionBuilder.getBeanDefinition();
    }

    @Override
    protected boolean shouldGenerateIdAsFallback() {
	// TODO Auto-generated method stub
	return true;
    }

}
