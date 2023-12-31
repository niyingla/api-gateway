/**
 * @projectName JianGateWay
 * @package com.pikaqiu.core.filter
 * @className com.pikaqiu.core.filter.Filter
 */
package com.pikaqiu.core.filter;

import com.pikaqiu.core.context.GatewayContext;

/**
 * Filter
 * @description 过滤器顶级接口
 * @author SongJian
 * @date 2023/6/10 17:19
 * @version
 */
public interface Filter {

    /**
     * 执行过滤器
     * @param ctx
     * @throws Exception
     */
    void doFilter(GatewayContext ctx) throws Exception;

    /**
     * 通过注解拿到排序
     * @return
     */
    default int getOrder() {
        FilterAspect annotation = this.getClass().getAnnotation(FilterAspect.class);
        if (annotation != null) {
            return annotation.order();
        }
        return Integer.MAX_VALUE;
    }
}
