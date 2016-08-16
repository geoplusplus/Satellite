package com.showjoy.satellite.registry;

import java.util.List;

import com.showjoy.satellite.registry.listener.RegistryNotifyListener;

/**
 * 
 * @author wuque
 * @version $Id: Registry.java, v 0.1 2016年5月19日 下午3:17:11 wuque Exp $
 */
public interface Registry {

    /**
     * 向namesever注册服务地址
     * @param url
     */
    public void register(Url url);

    /**
     *  向namesever注销服务地址
     * @param url
     */
    public void unregister(Url url);

    /**
     * 向namesever订阅地址及要订阅的服务
     * @param serverNames
     * @param url
     * @param notifyListener
     */
    public void subscripe(List<String> serverNames, Url url, RegistryNotifyListener notifyListener);

    /**
     * 向namesever取消订阅地址
     * @param url
     * @param notifyListener
     */
    public void unsubscripe(Url url, RegistryNotifyListener notifyListener);

}
