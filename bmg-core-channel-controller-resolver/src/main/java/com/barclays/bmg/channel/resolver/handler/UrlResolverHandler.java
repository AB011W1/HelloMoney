package com.barclays.bmg.channel.resolver.handler;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.barclays.bmg.channel.resolver.parser.ApplicationUrlResolverParser;

public class UrlResolverHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
	registerBeanDefinitionParser("application-url-resolver", new ApplicationUrlResolverParser());

    }

}
