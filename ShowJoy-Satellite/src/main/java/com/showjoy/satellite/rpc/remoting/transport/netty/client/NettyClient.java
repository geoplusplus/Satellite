package com.showjoy.satellite.rpc.remoting.transport.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import org.apache.log4j.Logger;

import com.showjoy.satellite.common.config.CustomerConfig;
import com.showjoy.satellite.rpc.remoting.Client;

/**
 * netty客户端
 * @author wuque
 * @version $Id: NettyClient.java, v 0.1 2016年5月13日 下午5:08:07 wuque Exp $
 */
public class NettyClient implements Client, Runnable {

    private Logger logger = Logger.getLogger(NettyClient.class);

    /** 要连接server的端口 */
    private int    port;

    /** 名称 */
    private String name;

    /** 要连接server的主机地址 */
    private String host;

    /** 服务的业务处理线程池中的线程数 */
    private int    threadCount;


    public NettyClient(String name, String host, int port, int threadCount) {

        this.name = name;
        this.host = host;
        this.port = port;
        this.threadCount = threadCount;

    }

    @Override
    public void open() {

        run();
    }

    /*
     (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.TCP_NODELAY, true); // (4)
            b.handler(new ClientInitChannelHandler(threadCount));

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync(); // (5)
            Channel channel = f.channel();
            //把channel持久化
            CustomerConfig.addChannel(name, channel);

            // Wait until the connection is closed.
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("", e);
        } finally {
            workerGroup.shutdownGracefully();
        }

    }

    protected String getName() {
        return name;
    }

    protected int getPort() {
        return port;
    }

}
