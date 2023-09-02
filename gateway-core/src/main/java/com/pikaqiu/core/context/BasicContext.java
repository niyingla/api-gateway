/**
 * @projectName JianGateWay
 * @package com.pikaqiu.core.context
 * @className com.pikaqiu.core.context.BasicContext
 */
package com.pikaqiu.core.context;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * BasicContext
 * @description
 * @author SongJian
 * @date 2023/6/4 14:27
 * @version
 */
public class BasicContext implements IContext{

    /**
     * 转发协议
     */
    protected final String protocol;

    /**
     * 网关状态
     * volatile：多线程情况下做到线程之间的可见性与防止指令重排
     */
    protected volatile int stats = Running;

    /**
     * Netty 上下文
     */
    protected final ChannelHandlerContext nettyCtx;

    /**
     * 上下文参数
     */
    protected final Map<String, Object> attributes = new HashMap<String, Object>();

    /**
     * 请求过程中发生的异常
     */
    protected Throwable throwable;

    /**
     * 是否保持长连接
     */
    protected final boolean keepAlive;

    /**
     * 存放回调函数集合
     */
    protected List<Consumer<IContext>> completedCallBacks;

    /**
     * 定义是否已经释放资源
     */
    protected final AtomicBoolean requestReleased = new AtomicBoolean(false);

    public BasicContext(String protocol, ChannelHandlerContext nettyCtx, boolean keepAlive) {
        this.protocol = protocol;
        this.nettyCtx = nettyCtx;
        this.keepAlive = keepAlive;
    }

    @Override
    public void setRunning() {
        stats = Running;
    }

    @Override
    public void setWritten() {
        stats = Written;
    }

    @Override
    public void setCompleted() {
        stats = Completed;
    }

    @Override
    public void setTerminated() {
        stats = Terminated;
    }

    @Override
    public boolean isRunning() {
        return stats == Running;
    }

    @Override
    public boolean isWritten() {
        return stats == Written;
    }

    @Override
    public boolean isCompleted() {
        return stats == Completed;
    }

    @Override
    public boolean isTerminated() {
        return stats == Terminated;
    }

    @Override
    public String getProtocol() {
        return this.protocol;
    }

    @Override
    public Object getRequest() {
        return null;
    }

    @Override
    public Object getResponse() {
        return null;
    }

    @Override
    public void setResponse(Object response) {

    }

    @Override
    public Throwable getThrowable() {
        return this.throwable;
    }

    @Override
    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public ChannelHandlerContext getNettyCtx() {
        return this.nettyCtx;
    }

    @Override
    public boolean isKeepAlive() {
        return this.keepAlive;
    }

    @Override
    public boolean releaseRequest() {
        return false;
    }

    @Override
    public void setCompletedCallBack(Consumer<IContext> consumer) {
        if (completedCallBacks == null) {
            completedCallBacks = new ArrayList<>();
        }
        completedCallBacks.add(consumer);
    }

    @Override
    public void invokeCompletedCallBack() {
        // 回调函数不为空，则遍历回调函数集合，并执行
        if (completedCallBacks != null) {
            completedCallBacks.forEach(call->call.accept(this));
        }
    }
}

