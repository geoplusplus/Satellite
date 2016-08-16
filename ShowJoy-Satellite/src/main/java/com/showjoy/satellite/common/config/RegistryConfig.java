package com.showjoy.satellite.common.config;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.showjoy.satellite.registry.Url;

/**
 * 
 * @author wuque
 * @version $Id: RegistryConfig.java, v 0.1 2016年5月19日 下午3:41:57 wuque Exp $
 */
public class RegistryConfig {

    /** 所有要调用的各服务地址 */
    private final static ConcurrentMap<String,Set<Url>> ALL_INVOKE_SERVER_ADDRESS = new ConcurrentHashMap<String,Set<Url>>();

    public static ConcurrentMap<String,Set<Url>> getInvokeServerMap() {

        return ALL_INVOKE_SERVER_ADDRESS;

    }
}
