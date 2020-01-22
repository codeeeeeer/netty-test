package test.netty.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import test.netty.constants.AddressConstants;
import test.netty.netty.client.handler.EchoClientInitializer;
import test.netty.utils.NettyUtil;

/**
 * 〈〉
 *
 * @author liujie
 * @create 2020/01/17 16:29
 */
@Slf4j
public class EchoClient {

    public void start(){
        NioEventLoopGroup clientGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap()
                .group(clientGroup)
                .channel(NioSocketChannel.class)
                .handler(new EchoClientInitializer());
        try {
            ChannelFuture channelFuture = bootstrap
                    .connect(AddressConstants.ADDRESS_COMMON, AddressConstants.PORT_TCP)
                    .sync();
            NettyUtil.sendInputMsgToChannel(channelFuture.channel());
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("error ", e);
        }finally {
            NettyUtil.shutdown(clientGroup);
        }
    }

    public static void main(String[] args) {
        new EchoClient().start();
    }
}
