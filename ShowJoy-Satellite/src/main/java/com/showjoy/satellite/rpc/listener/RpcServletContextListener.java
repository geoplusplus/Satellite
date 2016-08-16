package com.showjoy.satellite.rpc.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.showjoy.satellite.common.container.Starter;

/**
 * RPC启动监听类
 * 初始化RPC配置（包括启动对应的服务端及客户端）
 * 需在上层web.xml中配置才能启动
 * @author wuque
 * @version $Id: RpcServletContextListener.java, v 0.1 2016年5月9日 下午5:38:23 wuque Exp $
 * 
 */
public class RpcServletContextListener implements ServletContextListener {

    /*
     (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        Starter.init();

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}
