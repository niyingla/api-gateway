/**
 * @projectName JianGateWay
 * @package com.pikaqiu.core.netty
 * @className com.pikaqiu.core.netty.processor.NettyProcessor
 */
package com.pikaqiu.core.netty.processor;

import com.pikaqiu.core.request.HttpRequestWrapper;

/**
 * NettyProcessor
 * @description 核心处理器
 * @author SongJian
 * @date 2023/6/9 08:53
 * @version
 */
public interface NettyProcessor {

    /**
     * 处理过程
     * @param httpRequestWrapper
     */
    void process(HttpRequestWrapper httpRequestWrapper);

    /**
     * 启动
     */
    void start();

    /**
     * 销毁
     */
    void shutdown();
}