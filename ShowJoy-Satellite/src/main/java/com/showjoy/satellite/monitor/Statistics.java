package com.showjoy.satellite.monitor;

import java.io.Serializable;
import java.sql.Timestamp;

import com.showjoy.satellite.rpc.ServiceOutletException;

public class Statistics implements Serializable {

    /**  */
    private static final long      serialVersionUID = 4876435334669545948L;

    private String                 service;
    private String                 method;
    private String                 version;
    private boolean                isSuccess;
    private String                 clientAddress;
    private String                 serverAddress;

    private Timestamp              createTime;

    private ServiceOutletException exception;

    public Statistics(String service, String method, String version, boolean isSuccess,
                      String clientAddress, String serverAddress) {
        super();
        this.service = service;
        this.method = method;
        this.version = version;
        this.isSuccess = isSuccess;
        this.clientAddress = clientAddress;
        this.serverAddress = serverAddress;
        this.createTime = new Timestamp(System.currentTimeMillis());
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
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

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

}
