package com.pikaqiu.core.filter.flowCtl;

import com.pikaqiu.common.config.Rule;

/**
 * Created by IntelliJ IDEA.
 * com.pikaqiu.core.filter.flowCtl
 *
 * @Author: SongJian
 * @Create: 2023/6/12 20:03
 * @Version:
 * @Describe: 执行限流的接口
 */
public interface IGatewayFlowCtlRule {

    /**
     * 执行限流的具体方式
     * @param ctx
     */
    void doFlowCtlFilter(Rule.FlowCtlConfig flowCtlConfig, String serviceId);
}
