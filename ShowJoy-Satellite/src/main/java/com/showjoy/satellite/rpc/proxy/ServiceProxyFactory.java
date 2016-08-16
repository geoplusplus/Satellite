package com.showjoy.satellite.rpc.proxy;

import java.lang.reflect.Proxy;

import org.apache.log4j.Logger;

import com.showjoy.satellite.common.enums.InvokeMode;

/**
 * 接口代理工厂类
 * @author wuque
 * @version $Id: ServiceProxyFactory.java, v 0.1 2016年5月13日 下午4:44:27 wuque Exp $
 * 
 * @see InvokeMode
 */
public class ServiceProxyFactory {

    private static final Logger logger = Logger.getLogger(ServiceProxyFactory.class);

    @SuppressWarnings("unchecked")
    public static <T> T create(Class<?> interfaceClass, String serverName, InvokeMode invokeMode) {
        return (T) Proxy
            .newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[] { interfaceClass },
                new ServiceInvocationHandler(serverName, invokeMode));
    }

}
