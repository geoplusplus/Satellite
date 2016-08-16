package com.showjoy.satellite.rpc.remoting.transport.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import org.apache.log4j.Logger;

/**
 * 服务初始化channel处理类
 * @author wuque
 * @version $Id: ServerInitChannelHandler.java, v 0.1 2016年5月13日 下午5:54:36 wuque Exp $
 */
public class ServerInitChannelHandler extends ChannelInitializer<SocketChannel> {

    /** 服务的业务处理线程池中的线程数 */
    private int                 threadCount;

    private static final Logger logger = Logger.getLogger(ServerInitChannelHandler.class);

    public ServerInitChannelHandler(int threadCount) {

        this.threadCount = threadCount;

    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        if (logger.isInfoEnabled()) {

            logger.info("enter ChildChannelHandler initChannel method");

        }

        ch.pipeline().addLast(
            new ObjectDecoder(1024 * 1024, ClassResolvers.weakCachingConcurrentResolver(this
                .getClass().getClassLoader())));

        //序列化、编码
        ch.pipeline().addLast(new ObjectEncoder());

        ch.pipeline().addLast(new ServerChannelHandler(threadCount));
    }

}
