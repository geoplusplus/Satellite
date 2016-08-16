package com.showjoy.satellite.rpc.remoting.transport.netty.future;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;

import com.showjoy.satellite.rpc.remoting.dto.ResponseMessage;

/**
 * TODO 同步响应类
 * @author wuque
 * @version $Id: SyncResponseFuture.java, v 0.1 2016年5月13日 下午5:12:36 wuque Exp $
 */
public class SyncResponseFuture implements ResponseFuture<ResponseMessage> {

    private CountDownLatch      latch  = new CountDownLatch(1);

    private ResponseMessage     responseMessage;

    private long                requestID;

    private static final Logger logger = Logger.getLogger(SyncResponseFuture.class);

    public SyncResponseFuture() {
    }

    public SyncResponseFuture(long requestID) {

        this.requestID = requestID;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public ResponseMessage get() throws InterruptedException, ExecutionException {

        try {

            get(10, TimeUnit.SECONDS);

        } catch (TimeoutException e) {

            logger.error("", e);
        }

        return responseMessage;
    }

    @Override
    public ResponseMessage get(long timeout, TimeUnit unit) throws InterruptedException,
                                                           ExecutionException, TimeoutException {
        if (latch.await(timeout, unit)) {
            return responseMessage;
        }
        return null;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public long getRequestID() {
        return requestID;
    }

    @Override
    public void setResponse(ResponseMessage response) {

        this.responseMessage = response;
        latch.countDown();
    }

    @Override
    public ResponseMessage getResponse() {
        return responseMessage;
    }

}
