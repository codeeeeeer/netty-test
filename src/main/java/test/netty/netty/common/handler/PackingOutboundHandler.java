package test.netty.netty.common.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.CharsetUtil;

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

            ByteBuf directBuffer = ctx.alloc().directBuffer(4 + byteBufMessage.readableBytes());
            directBuffer.writeInt(byteBufMessage.readableBytes());
            directBuffer.writeBytes(byteBufMessage);
            super.write(ctx, directBuffer, promise);
        }else if (msg instanceof String){
            String message = (String) msg;
            int length = message.length();
            ByteBuf directBuffer = ctx.alloc().buffer(4);
            directBuffer.writeInt(length);
            directBuffer.writeBytes(message.getBytes());

            CompositeByteBuf compositeByteBuf = ctx.alloc().compositeBuffer();
            compositeByteBuf.addComponents(directBuffer, Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));
            super.write(ctx, compositeByteBuf, promise);
        }else {
            super.write(ctx, msg, promise);
        }
    }
}
