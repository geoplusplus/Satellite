package com.showjoy.satellite.rpc.remoting.transport.netty.server;

import io.netty.channel.Channel;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.showjoy.satellite.common.config.MonitorConfig;
import com.showjoy.satellite.common.enums.InvokeMode;
import com.showjoy.satellite.monitor.Statistics;
import com.showjoy.satellite.rpc.ServiceOutletException;
import com.showjoy.satellite.rpc.context.RpcContext;
import com.showjoy.satellite.rpc.remoting.dto.RequestMessage;
import com.showjoy.satellite.rpc.remoting.dto.ResponseMessage;

/**
 * 服务端业务线程类
 * @author wuque
 * @version $Id: ServerBusinessThread.java, v 0.1 2016年5月13日 下午5:49:06 wuque Exp $
 */
public class ServerBusinessThread implements Runnable {

    private final static Logger logger = Logger.getLogger(ServerBusinessThread.class);

    private Object              msg;

    /** channel */
    private Channel             channel;

    public ServerBusinessThread() {
    }

    public ServerBusinessThread(Channel channel, Object msg) {
        this.msg = msg;
        this.channel = channel;
    }

    @Override
    public void run() {

        if (msg instanceof RequestMessage) {

            RequestMessage requestMessage = (RequestMessage) msg;

            if (logger.isInfoEnabled()) {

                logger.info("requestmsg:" + requestMessage.getId());

            }

            ResponseMessage responseMessage = getResponse(requestMessage);

            //添加监控
            addMonitor(requestMessage, responseMessage);

            channel.writeAndFlush(responseMessage);

            logger.info("invokeTime:" + (responseMessage.getTime() - requestMessage.getTime()));

        }

    }

    /**
     *  添加监控
     * @param requestMessage
     * @param responseMessage
     */
    private void addMonitor(RequestMessage requestMessage, ResponseMessage responseMessage) {
        boolean isSuccess = responseMessage.getIsSuccess();

        Statistics statistics = new Statistics(requestMessage.getService(),
            requestMessage.getMethod(), requestMessage.getVersion(), isSuccess, null, null);
        if (!isSuccess) {

            statistics.setException(responseMessage.getException());
        }

        MonitorConfig.collect(statistics);
    }

    /**
     *  得到响应
     * @param requestMessage
     * @return
     */
    private ResponseMessage getResponse(RequestMessage requestMessage) {

        String serviceName = requestMessage.getService();

        String methodName = requestMessage.getMethod();

        InvokeMode invokeMode = requestMessage.getInvokeMode();

        Class cls = null;

        ResponseMessage responseMessage = null;

        try {
            cls = Class.forName(serviceName);

            Object object = RpcContext.getBean(cls.getSimpleName());

            Class class1 = object.getClass();

            Class[] paramTypeArr = (Class[]) requestMessage.getParamTypes();

            Object[] paramValueArr = requestMessage.getParamValues();

            Method med = class1.getMethod(methodName, paramTypeArr);

            Object object2 = med.invoke(object, paramValueArr);

            responseMessage = new ResponseMessage(true, requestMessage.getId(), object2, invokeMode);

        } catch (Exception e) {

            ServiceOutletException serviceOutletException = new ServiceOutletException(e);

            responseMessage = new ResponseMessage(false, requestMessage.getId(), null, invokeMode);

            responseMessage.setException(serviceOutletException);

            logger.info("", e);

        }

        return responseMessage;

    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

}
