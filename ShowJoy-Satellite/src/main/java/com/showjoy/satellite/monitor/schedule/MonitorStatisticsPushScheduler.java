package com.showjoy.satellite.monitor.schedule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 *  监控数据推送计划类
 * @author wuque
 * @version $Id: MonitorStatisticsPushScheduler.java, v 0.1 2016年5月20日 下午2:34:19 wuque Exp $
 */
public class MonitorStatisticsPushScheduler {

    private ScheduledExecutorService schedulerservice;

    /** 周期 */
    private final static long        INTERNAL = 30;

    private final static Logger      logger   = Logger
                                                  .getLogger(MonitorStatisticsPushScheduler.class);

    public void enable() {

        this.schedulerservice = Executors.newSingleThreadScheduledExecutor();

        this.schedulerservice.schedule(new MonitorStatisticsPushThread(), INTERNAL,
            TimeUnit.SECONDS);

        logger.info("MonitorStatisticsPushScheduler enable!!!");
    }

    public void disable() {
        this.schedulerservice.shutdownNow();

        logger.info("MonitorStatisticsPushScheduler disable!!!");
    }

}
