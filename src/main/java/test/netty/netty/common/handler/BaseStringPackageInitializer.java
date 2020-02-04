package test.netty.netty.common.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 〈〉
 *
 * @author liujie
 * @create 2020/01/17 16:51
 */
public class BaseStringPackageInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("packing inbound handler", new PackingInboundHandler());
        pipeline.addLast("packing outbound handler", new PackingOutboundHandler());
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        addSpecialHandler(pipeline);
    }

    protected void addSpecialHandler(ChannelPipeline pipeline){
    }
}
