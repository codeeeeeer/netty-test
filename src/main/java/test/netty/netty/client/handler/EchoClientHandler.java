package test.netty.netty.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 〈〉
 *
 * @author liujie
 * @create 2020/01/17 16:30
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("Hello, this is client");
    }
}
