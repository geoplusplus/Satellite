package com.showjoy.satellite.common.config;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 *   提供者配置
 * @author wuque
 * @version $Id: ProviderConfig.java, v 0.1 2016年5月8日 下午4:00:22 wuque Exp $
 */
public class ProviderConfig {

    /** 存放服务方的channel*/
    private final static ConcurrentMap<String, Channel> ALL_CHANNEL_MAP = new ConcurrentHashMap<String, Channel>();

}
