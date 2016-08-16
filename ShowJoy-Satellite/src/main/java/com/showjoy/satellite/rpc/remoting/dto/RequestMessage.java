package com.showjoy.satellite.rpc.remoting.dto;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

import com.showjoy.satellite.common.enums.InvokeMode;

/**
 * 请求消息类
 * @author wuque
 * @version $Id: RequestMessage.java, v 0.1 2016年5月6日 下午1:42:54 wuque Exp $
 */
public class RequestMessage implements Serializable {

    /**  */
    private static final long       serialVersionUID = 5056310466927731250L;

    /**  */
    private static final AtomicLong ID               = new AtomicLong(0L);

    /** id */
    private long                    id;

    /** 接口全名 */
    private String                  service;

    /** 接口方法名 */
    private String                  method;

    /**版本名  */
    private String                  version;

    /**请求方式  同步或异步  */
    private InvokeMode              invokeMode;

    /**参数类型  */
    private Object[]                paramTypes;

    /**参数值  */
    private Object[]                paramValues;

    /** 时间点 */
    private long                    time             = System.currentTimeMillis();

    public RequestMessage() {

        setId();

    }

    public RequestMessage(String service, String method, String version, Object[] paramTypes,
                          Object[] paramValues, InvokeMode invokeMode) {

        this();

        this.service = service;
        this.method = method;
        this.version = version;
        this.paramTypes = paramTypes;
        this.paramValues = paramValues;
        this.invokeMode = invokeMode;

    }

    private void setId() {

        id = ID.getAndIncrement();

    }

    public String getService() {
        return service;
    }

    public RequestMessage setService(String service) {
        this.service = service;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public RequestMessage setMethod(String method) {
        this.method = method;

        return this;
    }

    public String getVersion() {
        return version;
    }

    public RequestMessage setVersion(String version) {
        this.version = version;

        return this;
    }

    public Object[] getParamTypes() {
        return paramTypes;
    }

    public RequestMessage setParamTypes(Object[] paramTypes) {
        this.paramTypes = paramTypes;

        return this;
    }

    public Object[] getParamValues() {
        return paramValues;
    }

    public RequestMessage setParamValues(Object[] paramValues) {
        this.paramValues = paramValues;

        return this;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public InvokeMode getInvokeMode() {
        return invokeMode;
    }

    public RequestMessage setInvokeMode(InvokeMode invokeMode) {
        this.invokeMode = invokeMode;

        return this;
    }

    public long getTime() {
        return time;
    }

    public RequestMessage setTime(long time) {
        this.time = time;

        return this;
    }

}
