package com.barclays.bmg.utils;



import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * Spring Bean Factory using by retrieving Bean from application Context in JAVA
 * Code.
 * @author
 */
public class SpringBeansFactory implements ApplicationContextAware {
    private BeanFactory beanFactory;

    /**
     * @param applicationContext
     * @throws BeansException
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.beanFactory = new BeanFactoryWrapper(applicationContext);

    }

    /**
     * TODO Method description, including pre/post conditions and invariants.
     * @param name
     * @return
     * @throws BeansException
     */
    public Object getBean(String name) throws BeansException {
        return beanFactory.getBean(name);
    }

    /**
     * TODO Method description, including pre/post conditions and invariants.
     * @param <T>
     * @param name
     * @param requiredType
     * @return
     * @throws BeansException
     */
    @SuppressWarnings("unchecked")
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T)beanFactory.getBean(name, requiredType);
    }

}

