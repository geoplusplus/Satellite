package com.showjoy.satellite.common.config;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.showjoy.satellite.registry.Url;
import com.showjoy.satellite.rpc.remoting.transport.netty.client.NettyClient;
import com.showjoy.satellite.rpc.remoting.transport.netty.server.NettyServer;

/**
 * RPC启动配置类
 * @author wuque
 * @version $Id: RpcStartupConfig.java, v 0.1 2016年5月9日 下午3:25:28 wuque Exp $
 */
public class RpcStartupConfig {

    /** 单例 */
    private volatile static RpcStartupConfig rpcConfig;

    /** 服务端 */
    private static NettyServer               nettyServer;

    /**  客户端组*/
    private static List<NettyClient>         nettyClients;

    /**  是否开启监控数据推送*/
    private static boolean                   isOpenMonitorPush;

    /**  是否连接注册中心*/
    private static boolean                   isConnectRegistryCenter;

    /** 配置文件路径 */
    private static String                    DEFAULT_PATH = "/rpc.xml";

    private static final Logger              logger       = Logger
                                                              .getLogger(RpcStartupConfig.class);

    public static RpcStartupConfig getConfig() {
        if (rpcConfig == null) {
            synchronized (RpcStartupConfig.class) {
                if (rpcConfig == null) {
                    rpcConfig = new RpcStartupConfig();
                }
            }
        }
        return rpcConfig;
    }

    private RpcStartupConfig() {

        this(DEFAULT_PATH);

    }

    private RpcStartupConfig(String path) {

        SAXReader saxReader = new SAXReader();

        Document document = null;

        InputStream is = RpcStartupConfig.class.getResourceAsStream(path);

        try {
            document = saxReader.read(is);

        } catch (DocumentException e) {

            logger.error("", e);
        }

        //  根据rpc.xml文件初始化数据

        setNettyServer(document);

        setNettyClients(document);

    }

    /**
     * 封装客户端组
     * @param document
     */
    private void setNettyClients(Document document) {

        Element root = document.getRootElement();

        Element clientsElement = root.element("clients");

        Iterator<Element> iterator = clientsElement.elementIterator();

        List<NettyClient> nettyClients = new ArrayList<>();

        while (iterator.hasNext()) {
            Element e = iterator.next();

            String clientName = e.attributeValue("name");

            String host = e.attributeValue("host");

            String port = e.attributeValue("port");

            String threadCount = e.attributeValue("bussinessthreadcount");

            nettyClients.add(new NettyClient(clientName, host, Integer.valueOf(port), Integer
                .valueOf(threadCount)));

        }
        setNettyClients(nettyClients);

    }

    /**
     *封装服务端
     * @param document
     */
    private void setNettyServer(Document document) {

        Element root = document.getRootElement();

        Element serverElement = root.element("server");
        //获取其属性  
        String serverName = serverElement.attributeValue("name");

        String serverPort = serverElement.attributeValue("port");

        String threadCount = serverElement.attributeValue("bussinessthreadcount");

        Url url = new Url();
        try {
            url.setHost(InetAddress.getLocalHost().getHostAddress());
            url.setName(serverName);
            url.setPort(Integer.parseInt(serverPort));
        } catch (UnknownHostException e) {
            logger.error("", e);
        }
        setNettyServer(new NettyServer(url, Integer.valueOf(threadCount)));

    }

    public static RpcStartupConfig getConfig(String path) {
        if (rpcConfig == null) {
            synchronized (RpcStartupConfig.class) {
                if (rpcConfig == null) {
                    rpcConfig = new RpcStartupConfig(path);
                }
            }
        }
        return rpcConfig;
    }

    public NettyServer getNettyServer() {
        return nettyServer;
    }

    public void setNettyServer(NettyServer nettyServer) {
        RpcStartupConfig.nettyServer = nettyServer;
    }

    public List<NettyClient> getNettyClients() {
        return nettyClients;
    }

    public void setNettyClients(List<NettyClient> nettyClients) {

        RpcStartupConfig.nettyClients = nettyClients;
    }

}
