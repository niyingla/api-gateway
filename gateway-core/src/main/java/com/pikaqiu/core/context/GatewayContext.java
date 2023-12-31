/**
 * @projectName JianGateWay
 * @package com.pikaqiu.core.context
 * @className com.pikaqiu.core.context.GatewayContext
 */
package com.pikaqiu.core.context;

import com.pikaqiu.core.request.GatewayRequest;
import com.pikaqiu.core.response.GatewayResponse;
import io.micrometer.core.instrument.Timer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import lombok.Getter;
import lombok.Setter;
import com.pikaqiu.common.config.Rule;
import com.pikaqiu.common.utils.AssertUtil;


/**
 * GatewayContext
 * @description JianGateway上下文，对 BasicContext 进行扩展
 * @author SongJian
 * @date 2023/6/4 14:46
 * @version
 */
public class GatewayContext extends BasicContext{

    /**
     * 请求体
     */
    public GatewayRequest request;

    /**
     * 响应体
     */
    public GatewayResponse response;

    /**
     * 规则
     */
    public Rule rule;

    /**
     * 当前重试次数
     */
    private int currentRetryTimes;

    /**
     * 灰度标识
     */
    @Setter
    @Getter
    private boolean gray;

    @Setter
    @Getter
    private Timer.Sample timerSample;

    public GatewayContext(String protocol, ChannelHandlerContext nettyCtx,
                          boolean keepAlive, GatewayRequest request, Rule rule, int currentRetryTimes) {
        super(protocol, nettyCtx, keepAlive);
        this.request = request;
        this.rule = rule;
        this.currentRetryTimes = currentRetryTimes;
    }

    /**
     * 构造者模式
     */
    public static class Builder {
        private String protocol;
        private ChannelHandlerContext nettyCtx;
        private GatewayRequest request;
        private Rule rule;
        private boolean keepAlive;

        public Builder() {
        }

        public Builder setProtocol(String protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder setNettyCtx(ChannelHandlerContext nettyCtx) {
            this.nettyCtx = nettyCtx;
            return this;
        }

        public Builder setRequest(GatewayRequest request) {
            this.request = request;
            return this;
        }

        public Builder setRule(Rule rule) {
            this.rule = rule;
            return this;
        }

        public Builder setKeepAlive(boolean keepAlive) {
            this.keepAlive = keepAlive;
            return this;
        }

        public GatewayContext build () {
            AssertUtil.notNull (protocol, "protocol 不能为空！");
            AssertUtil.notNull (nettyCtx, "nettyCtx 不能为空！");
            AssertUtil.notNull (rule, "rule 不能为空！");
            AssertUtil.notNull (request, "request 不能为空！");
            // AssertUtil.notNull (keepAlive, "setKeepAlive 不能为空！");
            return new GatewayContext(protocol, nettyCtx, keepAlive, request, rule, 0);
        }
    }

    /**
     * 获取指定 key 的上下文参数，如果没有，则返回默认值
     * @param key
     * @return
     * @param <T>
     */
    public <T> T getRequireAttribute (String key, T defaultValue) {
        return (T) attributes.getOrDefault(key, defaultValue);
    }

    /**
     * 获取指定过滤器信息
     * @param filterId
     * @return
     */
    public Rule.FilterConfig getFilterConfig (String filterId) {
        return rule.getFilterConfig(filterId);
    }

    /**
     * 获取服务id
     * @return
     */
    public String getUniqueId() {
        return request.getUniqueId();
    }

    /**
     * 重写父类，释放资源
     */
    public boolean releaseRequest() {
        // 使用 CAS 判断
        if (requestReleased.compareAndSet(false, true)) {
            ReferenceCountUtil.release(request.getFullHttpRequest());
        }
        return true;
    }

    /**
     * 获取原始请求对象
     * @return
     */
    public GatewayRequest getOriginRequest() {
        return request;
    }

    @Override
    public GatewayRequest getRequest() {
        return request;
    }

    public void setRequest(GatewayRequest request) {
        this.request = request;
    }

    @Override
    public GatewayResponse getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = (GatewayResponse) response;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public int getCurrentRetryTimes() {
        return currentRetryTimes;
    }

    public void setCurrentRetryTimes(int currentRetryTimes) {
        this.currentRetryTimes = currentRetryTimes;
    }
}

