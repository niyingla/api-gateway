/**
 * @projectName JianGateWay
 * @package com.pikaqiu.gateway.register.center.api
 * @className com.pikaqiu.gateway.register.center.api.RegisterCenterListener
 */
package com.pikaqiu.gateway.register.center.api;

import com.pikaqiu.common.config.ServiceDefinition;
import com.pikaqiu.common.config.ServiceInstance;

import java.util.Set;

/**
 * RegisterCenterListener
 * @description 监听器
 * @author SongJian
 * @date 2023/6/9 19:47
 * @version
 */
public interface RegisterCenterListener {

    /**
     * 发生变化后的逻辑
     * @param serviceDefinition
     * @param serviceInstanceSet
     */
    void onChange(ServiceDefinition serviceDefinition, Set<ServiceInstance> serviceInstanceSet);
}

