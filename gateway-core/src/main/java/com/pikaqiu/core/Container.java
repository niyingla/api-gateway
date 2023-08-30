/**
 * @projectName JianGateWay
 * @package tech.songjian.core
 * @className tech.songjian.core.Container
 */
package com.pikaqiu.core;

import com.pikaqiu.core.netty.processor.DisruptorNettyCoreProcessor;
import com.pikaqiu.core.netty.processor.NettyProcessor;
import lombok.extern.slf4j.Slf4j;
import com.pikaqiu.core.netty.NettyHttpClient;
import com.pikaqiu.core.netty.NettyHttpServer;
import com.pikaqiu.core.netty.processor.NettyCoreProcessor;

import static com.pikaqiu.common.constants.GatewayConst.BUFFER_TYPE_PARALLEL;

/**
 * Container
 * @description 核心容器，用于整合Netty相关组件
 * @author SongJian
 * @date 2023/6/9 10:05
 * @version
 */
@Slf4j
public class Container implements LifeCycle {

    /**
     * 配置文件
     */
    private final Config config;

    private NettyHttpClient nettyHttpClient;

    private NettyHttpServer nettyHttpServer;

    private NettyProcessor nettyProcessor;

    public Container(Config config) {
        this.config = config;
        init();
    }

    @Override
    public void init() {
        NettyCoreProcessor nettyCoreProcessor = new NettyCoreProcessor();
        // 判断缓冲队列的类型
        if (BUFFER_TYPE_PARALLEL.equals(config.getBufferType())) {
            // 如果缓冲队列采用 parallel ，则使用 disruptor
            this.nettyProcessor = new DisruptorNettyCoreProcessor(config, nettyCoreProcessor);
        } else {
            this.nettyProcessor = nettyCoreProcessor;
        }

        this.nettyHttpServer = new NettyHttpServer(config, nettyProcessor);
        this.nettyHttpClient = new NettyHttpClient(config, nettyHttpServer.getWorkerEventLoopGroup());
    }

    @Override
    public void start() {
        nettyProcessor.start();
        nettyHttpServer.start();
        nettyHttpClient.start();
        log.info("【网关核心容器】启动完成！");
    }

    @Override
    public void shutdown() {
        nettyProcessor.shutdown();
        nettyHttpServer.shutdown();
        nettyHttpClient.shutdown();
        log.info("【网关核心容器】成功关闭！");
    }
}

