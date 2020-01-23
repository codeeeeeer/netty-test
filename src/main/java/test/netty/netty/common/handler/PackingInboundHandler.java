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
    private Integer expectedBytesInLastRound;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        storageByteBuf = ctx.alloc().buffer();
        expectedBytesInLastRound = 0;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf){
            ByteBuf inboundByteBuf = (ByteBuf) msg;
            if (inboundByteBuf.readableBytes() <= 0){
                return;
            }
            if (expectedBytesInLastRound > 0){
                int actualBytesInLastRound = storageByteBuf.readableBytes();
                int bytesThisRound = expectedBytesInLastRound - actualBytesInLastRound;
                inboundByteBuf.readBytes(storageByteBuf, bytesThisRound);
                ctx.fireChannelRead(storageByteBuf);

                storageByteBuf.clear();
                expectedBytesInLastRound = 0;
            }

            while (inboundByteBuf.readableBytes() >= 4){
                int expectedInboundByteCount = inboundByteBuf.readInt();
                if (expectedInboundByteCount <= 0){
                    return;
                }else if (inboundByteBuf.readableBytes() < expectedInboundByteCount){
                    expectedBytesInLastRound = expectedInboundByteCount;
                    inboundByteBuf.readBytes(storageByteBuf, inboundByteBuf.readableBytes());
                    return;
                }else {
                    ByteBuf originByteBuf = inboundByteBuf.readBytes(expectedInboundByteCount);
                    ctx.fireChannelRead(originByteBuf);
                }
            }
        }else {
            ctx.fireChannelRead(msg);
        }
    }
}
