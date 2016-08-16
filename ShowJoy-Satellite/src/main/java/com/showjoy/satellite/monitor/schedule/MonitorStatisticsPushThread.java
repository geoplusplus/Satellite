package com.showjoy.satellite.monitor.schedule;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

import com.showjoy.satellite.common.config.MonitorConfig;
import com.showjoy.satellite.common.config.RegistryConfig;
import com.showjoy.satellite.registry.Url;

/**
 *  监控数据推送线程
 * @author wuque
 * @version $Id: MonitorStatisticsPushThread.java, v 0.1 2016年5月20日 下午2:32:27 wuque Exp $
 */
public class MonitorStatisticsPushThread implements Runnable {

    @Override
    public void run() {

        //监控注册服务的相关状态,监控的内容从注册中心拿取
        Set<String> serverKeySet = RegistryConfig.getInvokeServerMap().keySet();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        for (String string : serverKeySet) {
            if (MonitorConfig.SERVER_CHANNEL_MAP.get(string) == null) {
                Set<Url> urlSet = (Set<Url>) RegistryConfig.getInvokeServerMap().get(string);
                //初始化连接通道进行监听
                Set<Channel> channelSet = new HashSet<Channel>();
                String serverName = null;
                for (Url url2 : urlSet) {
                    Channel channel  = null;
                    channel = initConnectChannel(workerGroup, url2);
                    channelSet.add(channel);
                    serverName = url2.getName();
                }
                MonitorConfig.SERVER_CHANNEL_MAP.put(serverName, channelSet);
            }else{
              for (Channel channel : MonitorConfig.SERVER_CHANNEL_MAP.get(string)) {
                  Channel serverChannel = channel.parent();
                  InetSocketAddress socketAddress = (InetSocketAddress) serverChannel.localAddress();
                  if(serverChannel.isActive()){
                      System.out.println("服务端地址："+socketAddress.getHostString() +",端口:"+socketAddress.getPort()+",状态:活跃");
                  }else{
                      //从注册中心移出,通道移出监听
                      System.out.println("服务端地址："+socketAddress.getHostString() +",端口:"+socketAddress.getPort()+",状态:不活跃");
                      for (Url url : RegistryConfig.getInvokeServerMap().get(string)) {
                        if(url.getHost().equalsIgnoreCase(socketAddress.getHostString())){
                            RegistryConfig.getInvokeServerMap().get(string).remove(url);
                            MonitorConfig.SERVER_CHANNEL_MAP.get(string).remove(channel);
                        }
                    }
                  }
              }
            }
        }
    }

    private Channel initConnectChannel(EventLoopGroup workerGroup, Url url) {

        Channel channel = null;
        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.TCP_NODELAY, true).handler(
                new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                    }
                });
            ChannelFuture f = b.connect(url.getHost(), url.getPort()).sync(); // (5)
            channel = f.channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } 
        return channel;
    }

    public static void main(String[] args) {
//        MonitorStatisticsPushThread thread = new MonitorStatisticsPushThread();
//        Url url = new Url();
//        url.setHost("10.1.1.63");
//        url.setPort(8007);
//        Channel channel = thread.initConnectChannel(new NioEventLoopGroup(), url);
//        System.out.println(channel);
    }
}
