package com.showjoy.satellite.rpc.remoting.transport.netty.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import org.apache.log4j.Logger;

/**
 * 客户端初始化处理类
 * @author wuque
 * @version $Id: ClientInitChannelHandler.java, v 0.1 2016年5月13日 下午5:04:30 wuque Exp $
 */
public class ClientInitChannelHandler extends ChannelInitializer<SocketChannel> {

    private Logger logger = Logger.getLogger(ClientInitChannelHandler.class);

    /** 服务的业务处理线程池中的线程数 */
    private int    threadCount;

    public ClientInitChannelHandler(int threadCount) {

        this.threadCount = threadCount;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        if (logger.isInfoEnabled()) {

            logger.info("enter ChildChannelHandler initChannel method");
        }

        //序列化、解码
        ch.pipeline()
            .addLast(
                new ObjectDecoder(1024, ClassResolvers.cacheDisabled(this.getClass()
                    .getClassLoader())));

        //序列化、编码
        ch.pipeline().addLast(new ObjectEncoder());

        ch.pipeline().addLast(new ClientChannelHandler(threadCount));
    }

}
