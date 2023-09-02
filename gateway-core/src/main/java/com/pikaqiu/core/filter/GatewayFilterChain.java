/**
 * @projectName JianGateWay
 * @package com.pikaqiu.core.filter
 * @className com.pikaqiu.core.filter.GatewayFilterChain
 */
package com.pikaqiu.core.filter;

import com.pikaqiu.common.enums.ResponseCode;
import com.pikaqiu.common.exception.BaseException;
import com.pikaqiu.common.exception.ResponseException;
import com.pikaqiu.core.helper.ResponseHelper;
import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.extern.slf4j.Slf4j;
import com.pikaqiu.core.context.GatewayContext;

import java.util.ArrayList;
import java.util.List;

/**
 * GatewayFilterChain
 * @description 过滤器链条类
 * @author SongJian
 * @date 2023/6/10 22:10
 * @version
 */
@Slf4j
public class GatewayFilterChain {

    /**
     * 过滤器集合
     */
    private List<Filter> filterList = new ArrayList<>();

    /**
     * 向过滤器链中添加过滤器
     * @param filter
     * @return
     */
    public GatewayFilterChain addFilter(Filter filter) {
        filterList.add(filter);
        return this;
    }

    /**
     * 向过滤器链中添加过滤器
     * @param filters
     * @return
     */
    public GatewayFilterChain addFilterList (List<Filter> filters) {
        filterList.addAll(filters);
        return this;
    }

    /**
     * 过滤
     * @param ctx
     * @return
     * @throws Throwable
     */
    public GatewayContext doFilter(GatewayContext ctx) {
        if (filterList.isEmpty()) {
            return ctx;
        }
        try {
            for (Filter filter : filterList) {
                filter.doFilter(ctx);
            }
        } catch (BaseException e) {
            log.error("执行过滤器发生异常,异常信息：", e);
            throw new ResponseException(e.getCode());
        } catch (Exception e) {
            // 发生异常后处理
            log.error("执行过滤器发生异常,异常信息：", e);
            throw new ResponseException(ResponseCode.INTERNAL_ERROR);
        }
        return ctx;
    }
}

