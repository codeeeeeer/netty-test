package test.netty.netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * 〈〉
 *
 * @author liujie
 * @create 2020/01/17 16:30
 */
@Slf4j
public class RpcClientHandler extends ChannelInboundHandlerAdapter implements Callable<String> {
    private ChannelHandlerContext channelHandlerContext;
    private String msg;
    private String param;

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.channelHandlerContext = ctx;
    }

    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        this.msg = (String) msg;
        notify();
    }

    @Override
    public synchronized String call() throws Exception {
        channelHandlerContext.writeAndFlush(param);
        wait();
        return msg;
    }
}
