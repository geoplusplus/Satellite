package com.showjoy.satellite.registry;

/**
 * 
 * @author wuque
 * @version $Id: Url.java, v 0.1 2016年5月19日 下午3:16:10 wuque Exp $
 */
public class Url {

    /** 名称  */
    private String name;

    /** 主机 */
    private String host;

    /** 端口  */
    private int    port;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public String getAddress() {
        return host + String.valueOf(port);
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
