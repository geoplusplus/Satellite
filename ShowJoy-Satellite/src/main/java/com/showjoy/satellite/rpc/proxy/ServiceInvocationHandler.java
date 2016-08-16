package com.showjoy.satellite.rpc.proxy;

import io.netty.channel.Channel;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;

import com.showjoy.satellite.common.config.CustomerConfig;
import com.showjoy.satellite.common.enums.InvokeMode;
import com.showjoy.satellite.rpc.remoting.dto.RequestMessage;
import com.showjoy.satellite.rpc.remoting.dto.ResponseMessage;
import com.showjoy.satellite.rpc.remoting.transport.netty.future.SyncResponseFuture;

/**
 * 接口调用处理类
 * @author wuque
 * @version $Id: ServiceInvocationHandler.java, v 0.1 2016年5月18日 下午3:17:42 wuque Exp $
 */
public class ServiceInvocationHandler implements InvocationHandler {

    private static final Logger logger = Logger.getLogger(ServiceInvocationHandler.class);

    /** 要调用的服务名称  */
    private String              serverName;

    /** 调用模式 */
    private InvokeMode          invokeMode;

    public ServiceInvocationHandler(String serverName, InvokeMode invokeMode) {

        this.serverName = serverName;

        this.invokeMode = invokeMode;

    }

    @Override
    public Object invoke(Object object, Method method, Object[] objectArr) throws Throwable {

        RequestMessage requestMessage = new RequestMessage();

        requestMessage.setService(method.getDeclaringClass().getName()).setMethod(method.getName())
            .setParamTypes(method.getParameterTypes()).setParamValues(objectArr)
            .setInvokeMode(invokeMode);

        Class returnType = method.getReturnType();

        return execute(serverName, requestMessage, invokeMode, returnType);
    }

    /**
     *  处理
     * @param channelName
     * @param requestMessage
     * @param requestMode
     * @param returnType
     * @return
     */
    private Object execute(String channelName, RequestMessage requestMessage,
                           InvokeMode requestMode, Class returnType) {

        Object object = null;

        switch (requestMode) {
            case ASYNCHRONY:

                object = doAsynchrony(channelName, requestMessage);

                break;

            case SYNCHRONY:

                object = doSynchrony(channelName, requestMessage, returnType);

                break;

            default:
                break;
        }

        return object;

    }

    /**
     *  同步执行
     * @param channelName
     * @param requestMessage
     * @param returnType
     * @return
     */
    private Object doSynchrony(String channelName, RequestMessage requestMessage, Class returnType) {

        Channel channel = CustomerConfig.getChannel(channelName);

        SyncResponseFuture syncResponseFuture = new SyncResponseFuture(requestMessage.getId());

        CustomerConfig.addSyncResponseFuture(requestMessage.getId(), syncResponseFuture);

        channel.writeAndFlush(requestMessage);

        ResponseMessage responseMessage = null;
        try {
            responseMessage = syncResponseFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("cuoshu");
        }

        Object object = null;

        if (responseMessage != null) {

            try {
                object = returnType.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {

                logger.error("", e);
            }

            object = responseMessage.getResponseStr();
        }

        return object;

    }

    /**
     * 异步执行
     * @param channelName
     * @param requestMessage
     * @return
     */
    private Object doAsynchrony(String channelName, RequestMessage requestMessage) {

        Channel channel = CustomerConfig.getChannel(channelName);
        channel.writeAndFlush(requestMessage);

        return null;
    }

}
