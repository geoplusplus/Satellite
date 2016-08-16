package com.showjoy.satellite.rpc.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RpcContext {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext() {

        String configPath = "classpath*:spring/root-context.xml";

        applicationContext = new ClassPathXmlApplicationContext(configPath);

    }

    public static Object getBean(String beanName) {

        return applicationContext.getBean(beanName);

    }

}
