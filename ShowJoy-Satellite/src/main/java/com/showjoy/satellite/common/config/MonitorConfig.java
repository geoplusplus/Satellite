package com.showjoy.satellite.common.config;

import io.netty.channel.Channel;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import com.showjoy.satellite.monitor.Statistics;

/**
 * 
 * @author wuque
 * @version $Id: MonitorConfig.java, v 0.1 2016年5月19日 下午3:41:57 wuque Exp $
 */
/**
 * 监控配置类
 * @author wuque
 * @version $Id: MonitorConfig.java, v 0.1 2016年5月20日 下午1:36:56 wuque Exp $
 */
public class MonitorConfig {

    private static final AtomicLong                      ID             = new AtomicLong(0L);

    /** 监控统计数据 */
    private final static ConcurrentMap<Long, Statistics> STATISTICS_MAP = new ConcurrentHashMap<Long, Statistics>();
    
    //监控服务通道
    public static final ConcurrentHashMap<String, Set<Channel>> SERVER_CHANNEL_MAP = new ConcurrentHashMap<String, Set<Channel>>();
    
    public static ConcurrentMap<Long, Statistics> getStatisticsMap() {
        return STATISTICS_MAP;
    }

    public static void collect(Statistics statistics) {

        long id = ID.getAndIncrement();

        STATISTICS_MAP.put(id, statistics);
    }

}
