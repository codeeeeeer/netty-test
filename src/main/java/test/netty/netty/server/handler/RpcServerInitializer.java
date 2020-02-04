package test.netty.netty.server.handler;

import io.netty.channel.ChannelPipeline;
import test.netty.netty.common.handler.BaseStringPackageInitializer;

/**
 * 〈〉
 *
 * @author liujie
 * @create 2020/01/17 17:07
 */
public class RpcServerInitializer extends BaseStringPackageInitializer {

    @Override
    protected void addSpecialHandler(ChannelPipeline pipeline) {
        pipeline.addLast("dispatcher handler", new RpcDispatcherHandler());
    }
}
