package com.barclays.bmg.utils;



import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import org.springframework.web.jsf.FacesContextUtils;






/**
 * TODO Class/Interface description.
 * @author
 */
public class SpringBeansUtils {

    private static Map<String, Object> cachedBeans = new HashMap<String, Object>();

    private static <T> T getBeanFromCache(Class<T> type) {
        Object bean = cachedBeans.get(type.getCanonicalName());
        return (T)bean;
    }

    private static void putBeanIntoCache(Class type, Object bean) {
        cachedBeans.put(type.getCanonicalName(), bean);
    }

    public static <T> T getBeanForType(FacesContext context, Class<T> type) {
        Object bean = getBeanFromCache(type);
        if (bean == null) {
            WebApplicationContext appCtx =
                    FacesContextUtils.getRequiredWebApplicationContext(context);
            bean = getBeanForType(appCtx, type);
            putBeanIntoCache(type, bean);
        }
        return (T)bean;
    }

   /* public static <T> T getBeanForType(RequestContext context, Class<T> type) {
        Object bean = getBeanFromCache(type);
        if (bean == null) {
            ApplicationContext appCtx = context.getActiveFlow().getApplicationContext();
            if (appCtx.getBeanNamesForType(type).length == 0) {
                appCtx = appCtx.getParent();
            }
            bean = getBeanForType(appCtx, type);
            putBeanIntoCache(type, bean);
        }
        return (T)bean;
    }*/

    public static <T> T getBeanForType(ApplicationContext appCtx, Class<T> type) {
        // Class beanClass = this.getClass();
        Object bean = getBeanFromCache(type);
        if (bean == null) {
            String[] beansName = appCtx.getBeanNamesForType(type);

            bean = (T)appCtx.getBean(beansName[0]);
            putBeanIntoCache(type, bean);
        }
        return (T)bean;
    }

   /* public static Object getBeanForName(String beanName) {
        ApplicationContext appCtx =
                RequestContextHolder.getRequestContext().getActiveFlow().getApplicationContext();
        if (!appCtx.containsBean(beanName)) {
            appCtx = appCtx.getParent();
        }
        return appCtx.getBean(beanName);
    }*/
}

