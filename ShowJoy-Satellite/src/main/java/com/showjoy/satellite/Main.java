package com.showjoy.satellite;

import org.apache.log4j.Logger;

import com.showjoy.satellite.common.container.Starter;
import com.showjoy.satellite.rpc.context.RpcContext;

/**
 * 
 * @author wuque
 * @version $Id: Main.java, v 0.1 2016年6月3日 上午11:07:07 wuque Exp $
 */
public class Main {

    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {

        RpcContext.setApplicationContext();

        Starter.init();

    }

}
