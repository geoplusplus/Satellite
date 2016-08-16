package com.showjoy.satellite.rpc.remoting.transport.netty.client;

import org.apache.log4j.Logger;

import com.showjoy.satellite.common.config.CustomerConfig;
import com.showjoy.satellite.common.enums.InvokeMode;
import com.showjoy.satellite.rpc.remoting.dto.ResponseMessage;
import com.showjoy.satellite.rpc.remoting.transport.netty.future.SyncResponseFuture;

/**
 * 客户端业务处理类
 * @author wuque
 * @version $Id: ClientBusinessThread.java, v 0.1 2016年5月13日 下午2:55:17 wuque Exp $
 */
public class ClientBusinessThread implements Runnable {

    private static final Logger logger = Logger.getLogger(ClientBusinessThread.class);

    private Object              msg;

    public ClientBusinessThread() {
    }

    public ClientBusinessThread(Object msg) {
        this.msg = msg;

    }

    @Override
    public void run() {

        if (logger.isInfoEnabled()) {

            logger.info("responsemsg:" + msg);

        }

        if (msg instanceof ResponseMessage) {

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

        }

    }

}
