package com.showjoy.satellite.registry;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.showjoy.satellite.common.config.RegistryConfig;
import com.showjoy.satellite.registry.listener.NameServerRegistryNotifyListener;
import com.showjoy.satellite.registry.listener.RegistryNotifyListener;

/**
 * 服务端启动时向注册中心进行注册服务
 * @author wuque
 * @version $Id: NameServerRegistry.java, v 0.1 2016年5月19日 下午3:21:04 wuque Exp $
 */
public class NameServerRegistry implements Registry {

    protected NameServerRegistry() {
    }

    /**
     * 注册服务
     */
    @Override
    public void register(Url url) {

        if(RegistryConfig.getInvokeServerMap().get(url.getName()) == null){
            Set<Url> urlSet = new HashSet<Url>();
            urlSet.add(url);
            RegistryConfig.getInvokeServerMap().put(url.getName(), urlSet);
        }else{
            RegistryConfig.getInvokeServerMap().get(url.getName()).add(url);
        }
    }

    /**
     * 取消某服务
     */
    @Override
    public void unregister(Url url) {

        if(RegistryConfig.getInvokeServerMap().get(url.getName()) != null){
            RegistryConfig.getInvokeServerMap().get(url.getName()).remove(url);
        }
    }

    @Override
    public void subscripe(List<String> serverNames, Url url,
                          RegistryNotifyListener registryNotifyListener) {
        //TODO 向namesever订阅地址及要订阅的服务
        String notifiedAddress = url.getAddress();

        if (registryNotifyListener instanceof NameServerRegistryNotifyListener) {

            registryNotifyListener.saveUrl(null);

        }

    }

    @Override
    public void unsubscripe(Url url, RegistryNotifyListener registryNotifyListener) {

        //TODO 向namesever取消订阅地址
        String notifiedAddress = url.getAddress();

        if (registryNotifyListener instanceof NameServerRegistryNotifyListener) {

            registryNotifyListener.removeUrl(null);

        }

    }

    private List<Url> getUrl(List<String> serverNames) {

        //TODO 通过servername向namesever得到服务地址
        return null;
    }

}
