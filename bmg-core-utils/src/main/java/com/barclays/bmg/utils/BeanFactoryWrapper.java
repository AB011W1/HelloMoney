package com.barclays.bmg.utils;



import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.barclays.bmg.context.Context;
import com.barclays.bmg.context.BMBContextHolder;





/**
 * Decorate BeanFactory.
 * @author
 */
public class BeanFactoryWrapper implements BeanFactory {

    private BeanFactory target;

    public BeanFactoryWrapper(BeanFactory target) {
        this.target = target;

    }

    /**
     * @param name
     * @return
     * @throws BeansException
     * @see org.springframework.beans.factory.BeanFactory#getBean(java.lang.String)
     */
    public Object getBean(String name) throws BeansException {
        Context context = BMBContextHolder.getContext();
        if (context != null && context.getBusinessId() != null) {
            try {
                String delegateName = context.getBusinessId().toUpperCase() + name;
                if (this.containsBean(delegateName)) {
                    return target.getBean(delegateName);
                }

            } catch (BeansException e) {

            }
        }

        return target.getBean(name);
    }

    /**
     * @param name
     * @param requiredType
     * @return
     * @throws BeansException
     * @see org.springframework.beans.factory.BeanFactory#getBean(java.lang.String,
     *      java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    public Object getBean(String name, Class requiredType) throws BeansException {
    	Context context = BMBContextHolder.getContext();

        if (context != null && context.getBusinessId() != null) {
            try {
                String delegateName = context.getBusinessId().toUpperCase() + name;
                if (this.containsBean(delegateName)) {
                    return target.getBean(delegateName, requiredType);
                }

            } catch (BeansException e) {

            }
        }
        return target.getBean(name, requiredType);
    }

    /**
     * @param name
     * @param args
     * @return
     * @throws BeansException
     * @see org.springframework.beans.factory.BeanFactory#getBean(java.lang.String,
     *      java.lang.Object[])
     */
    public Object getBean(String name, Object[] args) throws BeansException {
    	Context context = BMBContextHolder.getContext();

        if (context != null && context.getBusinessId() != null) {
            try {
                String delegateName = context.getBusinessId().toUpperCase() + name;
                if (this.containsBean(delegateName)) {
                    return target.getBean(delegateName, args);
                }

            } catch (BeansException e) {

            }
        }
        return target.getBean(name, args);
    }

    /**
     * @param name
     * @return
     * @see org.springframework.beans.factory.BeanFactory#containsBean(java.lang.String)
     */
    public boolean containsBean(String name) {
        return target.containsBean(name);
    }

    /**
     * @param name
     * @return
     * @see org.springframework.beans.factory.BeanFactory#getAliases(java.lang.String)
     */
    public String[] getAliases(String name) {
        return target.getAliases(name);
    }

    /**
     * @param name
     * @return
     * @throws NoSuchBeanDefinitionException
     * @see org.springframework.beans.factory.BeanFactory#getType(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public Class getType(String name) throws NoSuchBeanDefinitionException {
        return target.getType(name);
    }

    /**
     * @param name
     * @return
     * @throws NoSuchBeanDefinitionException
     * @see org.springframework.beans.factory.BeanFactory#isPrototype(java.lang.String)
     */
    public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
        return target.isPrototype(name);
    }

    /**
     * @param name
     * @return
     * @throws NoSuchBeanDefinitionException
     * @see org.springframework.beans.factory.BeanFactory#isSingleton(java.lang.String)
     */
    public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return target.isSingleton(name);
    }

    /**
     * @param name
     * @param targetType
     * @return
     * @throws NoSuchBeanDefinitionException
     * @see org.springframework.beans.factory.BeanFactory#isTypeMatch(java.lang.String,
     *      java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    public boolean isTypeMatch(String name, Class targetType) throws NoSuchBeanDefinitionException {
        return target.isTypeMatch(name, targetType);
    }

	@Override
	public <T> T getBean(Class<T> arg0) throws BeansException {
		// TODO Auto-generated method stub
		return null;
	}



}

