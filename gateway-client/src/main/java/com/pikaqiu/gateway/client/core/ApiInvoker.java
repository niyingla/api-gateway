/**
 * @projectName JianGateWay
 * @package com.pikaqiu.gateway.client.core
 * @className com.pikaqiu.gateway.client.core.ApiInvoker
 */
package com.pikaqiu.gateway.client.core;

import java.lang.annotation.*;

/**
 * ApiInvoker
 * @description 服务调用的注解，注解必须要在服务的方法上强制声明
 * @author SongJian
 * @date 2023/6/10 11:44
 * @version
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiInvoker {
    String path();
}
