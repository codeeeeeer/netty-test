package test.netty.netty.client.handler;

import io.netty.channel.ChannelPipeline;
import test.netty.netty.common.handler.BaseStringPackageInitializer;

/**
 * 〈〉
 *
 * @author liujie
 * @create 2020/01/17 16:51
 */
public class EchoClientInitializer extends BaseStringPackageInitializer {

    @Override
    protected void addSpecialHandler(ChannelPipeline pipeline) {
        pipeline.addLast("echo handler", new EchoClientHandler());
    }
}
