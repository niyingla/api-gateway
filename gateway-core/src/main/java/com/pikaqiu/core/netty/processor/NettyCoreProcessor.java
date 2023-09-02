/**
 * @projectName JianGateWay
 * @package com.pikaqiu.core.netty.processor
 * @className com.pikaqiu.core.netty.processor.NettyCoreProcess
 */
package com.pikaqiu.core.netty.processor;

import com.pikaqiu.core.filter.FilterFactory;
import com.pikaqiu.core.filter.GatewayFilterChainFactory;
import com.pikaqiu.core.helper.RequestHelper;
import com.pikaqiu.core.helper.ResponseHelper;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import com.pikaqiu.common.enums.ResponseCode;
import com.pikaqiu.common.exception.BaseException;
import com.pikaqiu.core.context.GatewayContext;
import com.pikaqiu.core.request.HttpRequestWrapper;

/**
 * NettyCoreProcess
 * @description 核心处理方法具体实现
 * @author SongJian
 * @date 2023/6/9 09:01
 * @version
 */
@Slf4j
public class NettyCoreProcessor implements NettyProcessor{

    private FilterFactory filterFactory = GatewayFilterChainFactory.getInstance();

    @Override
    public void process(HttpRequestWrapper httpRequestWrapper) {
        // 拿到具体的参数
        FullHttpRequest request = httpRequestWrapper.getRequest();
        ChannelHandlerContext ctx = httpRequestWrapper.getCtx();

        try {
            GatewayContext gatewayContext = RequestHelper.doContext(request, ctx);

            // 执行过滤器逻辑
            filterFactory.buildFilterChain(gatewayContext).doFilter(gatewayContext);

        } catch (BaseException e) {
            // 自定义异常
            log.error("process error {} {}", e.getCode(), e.getMessage());
            FullHttpResponse response = ResponseHelper.getHttpResponse(e.getCode());
            doWriteAndRelease(ctx, request, response);
        } catch (Throwable t) {
            log.error("process unknown error", t);
            FullHttpResponse response = ResponseHelper.getHttpResponse(ResponseCode.INTERNAL_ERROR);
            doWriteAndRelease(ctx, request, response);
        }

    }

    @Override
    public void start() {

    }

    @Override
    public void shutdown() {

    }

    /**
     * 回写数据并释放资源
     * @param ctx
     * @param request
     * @param response
     */
    private void doWriteAndRelease(ChannelHandlerContext ctx, FullHttpRequest request, FullHttpResponse response) {
        ctx.writeAndFlush(response)
                // 添加关闭 channel 的监听者，在释放资源后关闭 channel
                .addListener(ChannelFutureListener.CLOSE);
        ReferenceCountUtil.release(request);
    }

}

