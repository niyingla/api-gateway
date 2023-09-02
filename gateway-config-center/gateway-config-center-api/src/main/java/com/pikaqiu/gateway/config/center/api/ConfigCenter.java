/**
 * @projectName JianGateWay
 * @package com.pikaqiu.gateway.config.center.api
 * @className com.pikaqiu.gateway.config.center.api.ConfigCenter
 */
package com.pikaqiu.gateway.config.center.api;

/**
 * ConfigCenter
 * @description 配置中心接口
 * @author SongJian
 * @date 2023/6/10 13:44
 * @version
 */
public interface ConfigCenter {

    /**
     * 初始化方法
     * @param serverAddr
     * @param env
     */
    void init(String serverAddr, String env);

    /**
     * 订阅规则变更事件的方法
     */
    void subscribeRulesChange(RulesChangeListener listener);
}
