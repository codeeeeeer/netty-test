package test.netty.netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 〈〉
 *
 * @author liujie
 * @create 2020/01/17 16:30
 */
@Slf4j
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 200; i++) {
            ctx.writeAndFlush("Hello, this is client - " + i);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("client receive : {}", msg);
    }
}
