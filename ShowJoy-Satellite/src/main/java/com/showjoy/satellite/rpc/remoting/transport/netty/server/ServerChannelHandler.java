package com.showjoy.satellite.rpc.remoting.transport.netty.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.showjoy.satellite.common.enums.InvokeMode;
import com.showjoy.satellite.rpc.ServiceOutletException;
import com.showjoy.satellite.rpc.context.RpcContext;
import com.showjoy.satellite.rpc.remoting.dto.RequestMessage;
import com.showjoy.satellite.rpc.remoting.dto.ResponseMessage;

/**
 * 服务channel处理类
 * @author wuque
 * @version $Id: ServerChannelHandler.java, v 0.1 2016年5月13日 下午5:52:42 wuque Exp $
 */
public class ServerChannelHandler extends ChannelHandlerAdapter {

    private static final Logger logger = Logger.getLogger(ServerChannelHandler.class);

    /** 服务的业务处理线程池中的线程数 */
    private int                 threadCount;

    private ExecutorService     executorService;

    public ServerChannelHandler(int threadCount) {

        this.threadCount = threadCount;

        logger.info("server bussiness pool thread count:" + threadCount);

        executorService = Executors.newFixedThreadPool(threadCount);

    }

    /*
     * channelAction 
     * 
     * channel 通道
     * action  活跃的
     * 
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     * 
     */

    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println(ctx.channel() + "enter serverChannelHandler " + " channelActive");

    }

    /*
     * channelInactive
     * 
     * channel  通道
     * Inactive 不活跃的
     * 
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     * 
     */
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        // MyChannelHandlerPool.channelGroup.remove(ctx.channel());

        System.out.println(ctx.channel().localAddress().toString() + " channelInactive");

    }

    /*
     * channelRead
     * 
     * channel 通道
     * Read    读
     * 
     * 简而言之就是从通道中读取数据，也就是服务端接收客户端发来的数据
     * 但是这个数据在不进行解码时它是ByteBuf类型的后面例子我们在介绍
     * 
     */
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //业务处理
        System.out.println("enter ServerChannelHandler channelRead method: " + msg);

        executorService.execute(new ServerBusinessThread(ctx.channel(), msg));

        /*  if (msg instanceof RequestMessage) {

              RequestMessage requestMessage = (RequestMessage) msg;

              if (logger.isInfoEnabled()) {

                  logger.info("requestmsg:" + requestMessage.getId());

              }

              ResponseMessage responseMessage = getResponse(requestMessage);

              //添加监控
              // addMonitor(requestMessage, responseMessage);

              ctx.channel().writeAndFlush(responseMessage);

              logger.info("invokeTime:" + (responseMessage.getTime() - requestMessage.getTime()));

          }*/

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

    /*
     * channelReadComplete
     * 
     * channel  通道
     * Read     读取
     * Complete 完成
     * 
     * 在通道读取完成后会在这个方法里通知，对应可以做刷新操作
     * ctx.flush()
     * 
     */
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /*
     * exceptionCaught
     * 
     * exception    异常
     * Caught       抓住
     * 
     * 抓住异常，当发生异常的时候，可以做一些相应的处理，比如打印日志、关闭链接
     * 
     */
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        System.out.println("异常信息：\r\n" + cause.getMessage());
    }

}
