package test.netty.netty.common.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * 〈〉
 *
 * @author liujie
 * @create 2020/01/17 16:07
 */
@ChannelHandler.Sharable
public class PackingOutboundHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof ByteBuf){
            ByteBuf byteBufMessage = (ByteBuf) msg;

            int countBytes = byteBufMessage.readableBytes();
            ByteBuf countBuff = ctx.alloc().directBuffer(4);
            countBuff.writeInt(countBytes);

            CompositeByteBuf compositeByteBuf = ctx.alloc().compositeDirectBuffer();
            compositeByteBuf.addComponents(countBuff, byteBufMessage);
            super.write(ctx, compositeByteBuf, promise);
        }else {
            super.write(ctx, msg, promise);
        }
    }
}
