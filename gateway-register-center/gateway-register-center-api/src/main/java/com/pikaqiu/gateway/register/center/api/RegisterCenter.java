/**
 * @projectName JianGateWay
 * @package com.pikaqiu.gateway.register.center.api
 * @className com.pikaqiu.gateway.register.center.api.RegisterCenter
 */
package com.pikaqiu.gateway.register.center.api;

import com.pikaqiu.common.config.ServiceDefinition;
import com.pikaqiu.common.config.ServiceInstance;

/**
 * RegisterCenter
 * @description 注册中心管理接口
 * @author SongJian
 * @date 2023/6/9 19:24
 * @version
 */
public interface RegisterCenter {

    /**
     * 初始化
     * @param regsiterAddress
     * @param env
     */
    void init(String regsiterAddress, String env);

    /**
     * 注册
     * @param serviceDefinition
     * @param serviceInstance
     */
    void register(ServiceDefinition serviceDefinition, ServiceInstance serviceInstance);

    /**
     * 注销
     * @param serviceDefinition
     * @param serviceInstance
     */
    void deregister(ServiceDefinition serviceDefinition, ServiceInstance serviceInstance);

    /**
     * 订阅所有服务的变更
     * @param registerCenterListener
     */
    void subscribeAllServices(RegisterCenterListener registerCenterListener);

}
