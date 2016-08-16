package com.showjoy.satellite.rpc;

import java.io.Serializable;

public class ServiceOutletException implements Serializable {

    /**  */
    private static final long   serialVersionUID = -7420724024802603623L;

    private static final String SPLIT_POINT      = ".";

    private String              name;

    private String              detailName;

    private String              message;

    public ServiceOutletException() {

    }

    public ServiceOutletException(Exception exception) {

        detailName = exception.toString();

        int beginIndex = detailName.lastIndexOf(SPLIT_POINT);

        name = detailName.substring(beginIndex + 1);

        message = exception.getMessage();
    }

    @Override
    public final String toString() {

        return detailName;

    }

    public String getMessage() {

        return message;

    }

}
