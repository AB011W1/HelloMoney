package com.barclays.bmg.channel.resolver.handler;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.barclays.bmg.channel.resolver.parser.ControllerResolverParser;
import com.barclays.bmg.channel.resolver.parser.RequestToControllerResolverParser;

public class ControllerResolverHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {

	registerBeanDefinitionParser("request-to-controller-mapper", new RequestToControllerResolverParser());
	registerBeanDefinitionParser("controller-ref-resolver", new ControllerResolverParser());
    }

}
