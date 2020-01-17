package test.netty.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import test.netty.constants.AddressConstants;
import test.netty.utils.NettyUtil;

/**
 * 〈测试TCP粘包和拆包的server〉
 *
 * @author liujie
 * @create 2020/01/17 15:44
 */
@Slf4j
public class TcpServer {
    public void start(){
        NioEventLoopGroup parentGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                .group(parentGroup, childGroup)
                .childHandler(null)
                .channel(NioServerSocketChannel.class);
            ChannelFuture channelFuture = bootstrap.bind(AddressConstants.PORT_TCP).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            NettyUtil.shutdown(childGroup);
            NettyUtil.shutdown(parentGroup);
        }
    }
}
