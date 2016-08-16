package com.showjoy.satellite.rpc.remoting.dto;

import java.io.Serializable;

import com.showjoy.satellite.common.enums.InvokeMode;
import com.showjoy.satellite.rpc.ServiceOutletException;

/**
 * 响应消息类
 * @author wuque
 * @version $Id: ResponseMessage.java, v 0.1 2016年5月6日 下午1:42:54 wuque Exp $
 */
public class ResponseMessage implements Serializable {

    /** 序列化id */
    private static final long      serialVersionUID = 5056310466927731250L;

    /** 请求id */
    private long                   requestId;
    /** 响应信息 */
    private Object                 responseStr;
    /** 调用模式  同步或异步 */
    private InvokeMode             invokeMode;

    private boolean                isSuccess;

    private ServiceOutletException exception;

    /** 时间点 */
    private long                   time             = System.currentTimeMillis();

    public ResponseMessage() {

    }

    public ResponseMessage(boolean isSuccess, long requestId, Object responseStr,
                           InvokeMode invokeMode) {

        this.isSuccess = isSuccess;
        this.requestId = requestId;
        this.responseStr = responseStr;
        this.invokeMode = invokeMode;

    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public Object getResponseStr() {
        return responseStr;
    }

    public void setResponseStr(Object responseStr) {
        this.responseStr = responseStr;
    }

    public InvokeMode getInvokeMode() {
        return invokeMode;
    }

    public void setInvokeMode(InvokeMode invokeMode) {
        this.invokeMode = invokeMode;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public ServiceOutletException getException() {
        return exception;
    }

    public void setException(ServiceOutletException exception) {
        this.exception = exception;
    }

}
