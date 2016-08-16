package com.showjoy.satellite.common.config;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.showjoy.satellite.rpc.remoting.transport.netty.future.SyncResponseFuture;

/**
 *   消费者配置类
 * @author wuque
 * @version $Id: CustomerConfig.java, v 0.1 2016年5月8日 下午4:00:22 wuque Exp $
 */
public class CustomerConfig {

    /** 存放所有的消费者channel的Map */
    private final static ConcurrentMap<String, Channel>          ALL_CHANNEL_MAP       = new ConcurrentHashMap<String, Channel>();

    /** 存放所有同步请求的id及相应的SyncResponseFuture的Map */
    private final static ConcurrentMap<Long, SyncResponseFuture> ALL_SYNC_RESPONSE_MAP = new ConcurrentHashMap<Long, SyncResponseFuture>();

    /**
     *  添加消费者channel
     * @param cumtomerName
     * @param channel
     * @see Channel
     */
    public static void addChannel(String cumtomerName, Channel channel) {

        ALL_CHANNEL_MAP.put(cumtomerName, channel);

    }

    /**
     * 通过消费者名得到channel
     * @param cumtomerName
     * @return Channel 
     * @see Channel
     */
    public static Channel getChannel(String cumtomerName) {

        return ALL_CHANNEL_MAP.get(cumtomerName);

    }

    /**
     *  通过请求id得到SyncResponseFuture
     * @param requestId
     * @see SyncResponseFuture
     * @return SyncResponseFuture
     */
    public static SyncResponseFuture getSyncResponseFuture(long requestId) {

        return ALL_SYNC_RESPONSE_MAP.get(requestId);

    }

    /**
     * 添加 requestId及syncResponseFuture的记录
     * @param requestId
     * @param syncResponseFuture
     * @see SyncResponseFuture
     */
    public static void addSyncResponseFuture(long requestId, SyncResponseFuture syncResponseFuture) {

        ALL_SYNC_RESPONSE_MAP.put(requestId, syncResponseFuture);

    }

    /**
     * 移除SyncResponseFuture
     * @param requestId
     */
    public static void removeSyncResponseFuture(long requestId) {

        ALL_SYNC_RESPONSE_MAP.remove(requestId);
    }

}
