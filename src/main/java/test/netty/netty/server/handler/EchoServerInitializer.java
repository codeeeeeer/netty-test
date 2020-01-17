package test.netty.netty.server.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import test.netty.netty.common.handler.PackingInboundHandler;
import test.netty.netty.common.handler.PackingOutboundHandler;

/**
 * 〈〉
 *
 * @author liujie
 * @create 2020/01/17 17:07
 */
public class EchoServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("packing inbound handler", new PackingInboundHandler());
        pipeline.addLast("packing outbound handler", new PackingOutboundHandler());
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        pipeline.addLast("write back handler", new EchoServerHandler());
    }
}
