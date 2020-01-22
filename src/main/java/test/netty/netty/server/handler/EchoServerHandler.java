package test.netty.netty.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 〈〉
 *
 * @author liujie
 * @create 2020/01/17 17:00
 */
@Slf4j
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("server receive : {}", msg);
        System.out.println("server receive message");
        ctx.writeAndFlush(msg);
    }
}
