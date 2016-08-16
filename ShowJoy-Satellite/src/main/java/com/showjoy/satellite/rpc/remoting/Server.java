package com.showjoy.satellite.rpc.remoting;

import io.netty.channel.Channel;

import java.util.Collection;

/**
 * 
 * @author wuque
 * @version $Id: Server.java, v 0.1 2016年5月16日 上午11:00:32 wuque Exp $
 */
public interface Server extends Bootable {

    public Collection<Channel> getChannels();

}
