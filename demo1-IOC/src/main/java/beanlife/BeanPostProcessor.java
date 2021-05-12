package beanlife;

import org.springframework.beans.BeansException;

/**
 * @author: wangzg
 * @date 2021/5/11 23:39
 * modified by: wangzg
 */
public class BeanPostProcessor implements org.springframework.beans.factory.config.BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("在bean的初始化方法执行前");
        return org.springframework.beans.factory.config.BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("在bean的初始化方法执行后");
        return org.springframework.beans.factory.config.BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
