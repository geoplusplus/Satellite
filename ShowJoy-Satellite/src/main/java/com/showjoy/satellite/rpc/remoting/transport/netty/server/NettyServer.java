package com.showjoy.satellite.rpc.remoting.transport.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.showjoy.satellite.registry.Url;
import com.showjoy.satellite.rpc.remoting.Server;

/**
 * netty服务端
 * @author wuque
 * @version $Id: NettyServer.java, v 0.1 2016年5月13日 下午5:44:31 wuque Exp $
 */
public class NettyServer implements Server, Runnable {

    private static final Logger         logger = Logger.getLogger(NettyServer.class);

    private Url url;

    /** 服务的业务处理线程池中的线程数 */
    private int                         threadCount;

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    /** 连接的所有channel */
    private static Map<String, Channel> channelMap;

    public NettyServer(Url url, int threadCount) {

        this.url = url;
        this.threadCount = threadCount;

    }

    @Override
    public void open() {

        if (logger.isInfoEnabled()) {

            logger.info("enter server run method");

        }

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .childHandler(new ServerInitChannelHandler(threadCount))
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = bootstrap.bind(this.url.getPort()).sync();
            System.out.println("服务端启动，启动端口" + this.url.getPort());
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("", e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }

    @Override
    public Set<Channel> getChannels() {

        if (channelMap == null || channelMap.size() <= 0) {

            return null;

        }

        Set<Channel> channels = new HashSet<Channel>();

        for (Channel channel : channelMap.values()) {

            channels.add(channel);

        }

        return channels;
    }

    @Override
    public void run() {

        open();
    }
    
}
