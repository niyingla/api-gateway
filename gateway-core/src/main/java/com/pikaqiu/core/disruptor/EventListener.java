package com.pikaqiu.core.disruptor;

/**
 * Created by IntelliJ IDEA.
 * com.pikaqiu.core.disruptor
 *
 * @Author: SongJian
 * @Create: 2023/6/13 20:44
 * @Version:
 * @Describe: 监听接口
 */
public interface EventListener<E> {

    /**
     * 正常情况执行
     * @param event
     */
    void onEvent(E event);

    /**
     * 异常情况执行
     * @param ex
     * @param sequence
     * @param event
     */
    void onException(Throwable ex, long sequence, E event);
}
