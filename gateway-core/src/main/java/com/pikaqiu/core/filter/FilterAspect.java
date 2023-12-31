/**
 * @projectName JianGateWay
 * @package com.pikaqiu.core.filter
 * @className com.pikaqiu.core.filter.FilterAspect
 */
package com.pikaqiu.core.filter;

import java.lang.annotation.*;

/**
 * FilterAspect
 * @description 过滤器注解类
 * @author SongJian
 * @date 2023/6/10 17:20
 * @version
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface FilterAspect {

    /**
     * 过滤器 id
     * @return
     */
    String id();

    /**
     * 过滤器名称
     * @return
     */
    String name() default "";

    /**
     * 排序
     * @return
     */
    int order() default 0;
}
