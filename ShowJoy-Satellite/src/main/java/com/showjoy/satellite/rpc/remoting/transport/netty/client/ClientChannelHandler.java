package com.showjoy.satellite.rpc.remoting.transport.netty.client;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

/**
 * 客户端channel处理类
 * @author wuque
 * @version $Id: ClientChannelHandler.java, v 0.1 2016年5月13日 下午4:56:32 wuque Exp $
 */
public class ClientChannelHandler extends ChannelHandlerAdapter {

    private static final Logger         logger        = Logger
                                                          .getLogger(ClientChannelHandler.class);

    private ExecutorService             executorService;

    private static ThreadLocal<Boolean> isMasterLocal = new ThreadLocal<>();

    /** 服务的业务处理线程池中的线程数 */
    private int                         threadCount;

    public ClientChannelHandler(int threadCount) {

        this.threadCount = threadCount;

        logger.info("client bussiness pool thread count :" + threadCount);

        executorService = Executors.newFixedThreadPool(threadCount);

    }

    public static void main(String[] args) {
        System.out.println(isMasterLocal.get());
    }

    public static boolean isMaster() {

        if (isMasterLocal.get() == null) {
            return false;
        }
        return isMasterLocal.get();
    }

    public static void setMaster(boolean isMaster) {
        isMasterLocal.set(isMaster);
    }

    /*
     * channelAction 
     * channel 通道
     * action  活跃的
     * 
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     * 
     */

    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        if (logger.isInfoEnabled()) {

            logger.info("enter ClientChannelHandler channelActive method ");

            logger.info(new Date() + ";channel:" + ctx.channel() + ".");
        }

        //通知您已经链接上客户端
        ctx.writeAndFlush(new Date() + "您已经开启与服务端链接.");

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

        //注意此处已经不需要手工解码了

        if (logger.isInfoEnabled()) {

            logger.info(new Date() + "enter ClientChannelHandler channelRead method ");

        }

        executorService.execute(new ClientBusinessThread(msg));

        if (logger.isInfoEnabled()) {

            logger.info("responsemsg:" + msg);

        }

        /*   if (msg instanceof ResponseMessage) {

               ResponseMessage responseMessage = (ResponseMessage) msg;

               if (responseMessage.getInvokeMode() == InvokeMode.SYNCHRONY) {

                   long requestId = responseMessage.getRequestId();

                   //执行需要同步的响应操作,通知等待的业务线程

                   if (logger.isInfoEnabled()) {

                       logger.info("requestId:,execute   synchronized response  operation");
                   }

                   SyncResponseFuture syncResponseFuture = CustomerConfig
                       .getSyncResponseFuture(requestId);

                   if (syncResponseFuture != null) {

                       syncResponseFuture.setResponse(responseMessage);//设置响应，并通知原请求业务线程向下执行

                       //将这个syncResponseFuture从map中移除

                       CustomerConfig.removeSyncResponseFuture(requestId);

                   }

               }

           }*/

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

        logger.error("异常信息：\r\n" + cause.getMessage());

    }

}
