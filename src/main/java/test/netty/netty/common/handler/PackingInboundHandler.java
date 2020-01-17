package test.netty.netty.common.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 〈〉
 *
 * @author liujie
 * @create 2020/01/17 16:17
 */
public class PackingInboundHandler extends ChannelInboundHandlerAdapter {
    private ByteBuf storageByteBuf;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        storageByteBuf = ctx.alloc().compositeHeapBuffer();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf){
            ByteBuf inboundByteBuf = (ByteBuf) msg;
            while (inboundByteBuf.readableBytes() >= 4){
                int expectedInboundByteCount = inboundByteBuf.readInt();
                ByteBuf originByteBuf = inboundByteBuf.readBytes(expectedInboundByteCount);
                ctx.fireChannelRead(originByteBuf);
            }
        }else {
            ctx.fireChannelRead(msg);
        }
    }
}
