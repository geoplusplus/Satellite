package com.showjoy.satellite.common.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 远程接口注解
 * @author wuque
 * @version $Id: RemoteService.java, v 0.1 2016年5月26日 下午2:38:00 wuque Exp $
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface RemoteService {

    String name();

}
