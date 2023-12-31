/**
 * @projectName JianGateWay
 * @package com.pikaqiu.core.filter
 * @className com.pikaqiu.core.filter.loadbalance.LoadBalanceFilter
 */
package com.pikaqiu.core.filter.loadbalance;


import com.alibaba.fastjson.JSON;
import com.pikaqiu.common.constants.FilterConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import com.pikaqiu.common.config.Rule;
import com.pikaqiu.common.config.ServiceInstance;
import com.pikaqiu.common.enums.ResponseCode;
import com.pikaqiu.common.exception.NotFoundException;
import com.pikaqiu.core.context.GatewayContext;
import com.pikaqiu.core.filter.Filter;
import com.pikaqiu.core.filter.FilterAspect;
import com.pikaqiu.core.request.GatewayRequest;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.pikaqiu.common.constants.BasicConst.COLON_SEPARATOR;

/**
 * LoadBalanceFilter
 * @description 负载均衡过滤器
 * @author SongJian
 * @date 2023/6/10 23:47
 * @version
 */
@Slf4j
@FilterAspect(id = FilterConst.LOAD_BALANCE_FILTER_ID, name = FilterConst.LOAD_BALANCE_FILTER_NAME, order = FilterConst.LOAD_BALANCE_FILTER_ORDER)
public class LoadBalanceFilter implements Filter {

    @Override
    public void doFilter(GatewayContext ctx) throws Exception {
        String serviceId = ctx.getUniqueId();
        IGatewayLoadBalanceRule gatewayLoadBalanceRule = getLoadBalanceRule(ctx);
        // 根据负载均衡算法选择一个服务实例
        ServiceInstance serviceInstance = gatewayLoadBalanceRule.choose(serviceId, ctx.isGray());
        GatewayRequest request = ctx.getRequest();
        if (serviceId != null && request != null) {
            String host = serviceInstance.getIp() + COLON_SEPARATOR + serviceInstance.getPort();
            request.setModifyHost(host);
        } else {
            log.warn("No instance available for : {}", serviceId);
            throw new NotFoundException(ResponseCode.SERVICE_DEFINITION_NOT_FOUND);
        }
    }

    /**
     * 根据上下文获取对应的负载均衡算法
     * @param ctx
     * @return
     */
    private IGatewayLoadBalanceRule getLoadBalanceRule(GatewayContext ctx) {
        IGatewayLoadBalanceRule loadBalanceRule = null;
        Rule configRule = ctx.getRule();
        if (configRule != null) {
            Set<Rule.FilterConfig> filterConfigs = configRule.getFilterConfigs();
            Iterator<Rule.FilterConfig> iterator = filterConfigs.iterator();
            Rule.FilterConfig filterConfig;
            while (iterator.hasNext()) {
                filterConfig = iterator.next();
                if (filterConfig == null) {
                    continue;
                }
                String filterId = filterConfig.getId();
                if (FilterConst.LOAD_BALANCE_FILTER_ID.equals(filterId)) {
                    // 如果匹配上，直接拿
                    String config = filterConfig.getConfig();
                    // 默认是随机
                    String strategy = FilterConst.LOAD_BALANCE_STRATEGY_RANDOM;
                    if (StringUtils.isNotEmpty(config)) {
                        Map<String, String> mapTypeMap = JSON.parseObject(config, Map.class);
                        strategy = mapTypeMap.get(FilterConst.LOAD_BALANCE_KEY);
                    }
                    switch (strategy) {
                        case FilterConst.LOAD_BALANCE_STRATEGY_RANDOM:
                            loadBalanceRule = RandomLoadBalanceRule.getInstance(configRule.getServiceId());
                            break;
                        case FilterConst.LOAD_BALANCE_STRATEGY_ROUND_ROBIN:
                            loadBalanceRule = RoundRobinLoadBalanceRule.getInstance(configRule.getServiceId());
                            break;
                        default:
                            log.warn("No loadBalance strategy for service: {}", strategy);
                            loadBalanceRule = RandomLoadBalanceRule.getInstance(configRule.getServiceId());
                            break;
                    }
                }
            }
        }
        return loadBalanceRule;
    }
}

