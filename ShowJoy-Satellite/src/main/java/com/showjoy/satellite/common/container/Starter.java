package com.showjoy.satellite.common.container;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.showjoy.satellite.common.config.RpcStartupConfig;
import com.showjoy.satellite.rpc.remoting.transport.netty.client.NettyClient;
import com.showjoy.satellite.rpc.remoting.transport.netty.server.NettyServer;

public class Starter {

    private static Logger logger = Logger.getLogger(Starter.class);

    public static void init() {

        RpcStartupConfig rpcConfig = RpcStartupConfig.getConfig();

        int threadCount = 0;//记录服务端跟客户端启动，需要的线程数

        NettyServer nettyServer = rpcConfig.getNettyServer();
        if (nettyServer != null) {
            threadCount++;
        }

        List<NettyClient> nettyClients = rpcConfig.getNettyClients();

        if (nettyClients != null && nettyClients.size() > 0) {

            threadCount = threadCount + nettyClients.size();
        }

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        //启动指定的netty服务端
        executorService.execute(nettyServer);

        //启动指定的netty客户端
        for (NettyClient nettyClient : nettyClients) {

            executorService.execute(nettyClient);

        }
        //TODO 启动监控数据推送计划
        // new MonitorStatisticsPushScheduler().enable();
    }

}
