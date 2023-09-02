/**
 * @projectName JianGateWay
 * @package com.pikaqiu.core.netty
 * @className com.pikaqiu.core.request.HttpRequestWrapper
 */
package com.pikaqiu.core.request;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.Data;

/**
 * HttpRequestWrapper
 * @description HTTP包装
 * @author SongJian
 * @date 2023/6/9 08:51
 * @version
 */
@Data
public class HttpRequestWrapper {
    private FullHttpRequest request;
    private ChannelHandlerContext ctx;
}

