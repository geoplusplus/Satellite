package com.showjoy.satellite.common.container.spring;

import java.lang.reflect.Field;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import com.showjoy.satellite.common.annotations.RemoteService;
import com.showjoy.satellite.common.enums.InvokeMode;
import com.showjoy.satellite.rpc.proxy.ServiceProxyFactory;

/**
 *  spring初始化Bean时远程资源注入类
 * @author wuque
 * @version $Id: RemotingBeanPostProcessor.java, v 0.1 2016年5月26日 下午2:34:12 wuque Exp $
 */
@Component
public class RemotingBeanPostProcessor implements BeanPostProcessor, PriorityOrdered {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
                                                                               throws BeansException {
        Class<?> cls = bean.getClass();

        for (Field field : cls.getDeclaredFields()) {

            if (field.isAnnotationPresent(RemoteService.class)) {

                RemoteService remoteService = field.getAnnotation(RemoteService.class);
                String name = remoteService.name();

                field.setAccessible(true);

                Object service = ServiceProxyFactory.create(field.getType(), name,
                    InvokeMode.SYNCHRONY);

                try {
                    field.set(bean, service);

                } catch (Exception e) {

                    System.out.println("error");

                }

                System.out.println(1111);
            }
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
                                                                              throws BeansException {

        return bean;
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
