package com.showjoy.satellite.rpc.remoting.transport.netty.future;

import java.util.concurrent.Future;

/**
 * 
 * @author wuque
 * @version $Id: ResponseFuture.java, v 0.1 2016年5月13日 下午5:12:27 wuque Exp $
 */
public interface ResponseFuture<T> extends Future<T> {

    /**
     * 获取请求的ID
     */
    long getRequestID();

    /**
     * 设置响应
     */
    void setResponse(T response);

    /**
     * 返回服务端响应
     */
    T getResponse();

}
