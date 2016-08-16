package com.showjoy.satellite.registry.listener;

import java.util.Set;

import com.showjoy.satellite.registry.Url;

/**
 * 
 * @author wuque
 * @version $Id: RegistryNotifyListener.java, v 0.1 2016年5月19日 下午3:50:24 wuque Exp $
 */
public interface RegistryNotifyListener {

    //TODO  比较并重启

    /**
     *
     * @param urls
     */
    public void saveUrl(Set<Url> urls);

    /**
     *
     * @param urls
     */
    public void removeUrl(Set<Url> urls);

}
